package com.ma.frontend.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.google.gson.*;
import com.ma.frontend.R;
import com.ma.frontend.Vo.CourseDataVo;
import com.ma.frontend.Vo.ResultVo;
import com.ma.frontend.Vo.StudentInfoVo;
import com.ma.frontend.activities.person.LookupAcivity;
import com.ma.frontend.adapter.CourseInfoAdapter;
import com.ma.frontend.adapter.InfoGallery;
import com.ma.frontend.config.HttpConstant;
import com.ma.frontend.db.dao.CourseInfoDao;
import com.ma.frontend.db.dao.GlobalInfoDao;
import com.ma.frontend.db.dao.UserCourseDao;
import com.ma.frontend.db.dao.UserInfoDao;
import android.app.AlertDialog;
import android.app.Dialog;
import com.ma.frontend.domain.CourseInfo;
import com.ma.frontend.domain.GlobalInfo;
import com.ma.frontend.domain.UserCourse;
import com.ma.frontend.domain.UserInfo;
import com.ma.frontend.utils.TimetableUtil;
import com.ma.frontend.widget.BorderTextView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Auther:kiwi
 * @Date: 2019/5/20 19:40
 */
public class CourseActivity extends AppCompatActivity implements View.OnClickListener{


    //vo课程变量
    public List<CourseDataVo> courseDataVos = new ArrayList<CourseDataVo>();

    private static Context context;

    // 每天的课程数
    private final int dayCourseNum = 8;


    /**
     * UI相关成员变量
     */
    private DrawerLayout mDrawerLayout;
    private ProgressDialog progressDialog;


    /**
     * 底部button跳转变量
    */
    private Button mSearch;
    // private RadioButton mCourse;
    private Button mPerson;

    /**
     * View相关成员变量
     */
    protected View refreshView;

    protected TextView dateTextView;//当前日期
    protected TextView weekTextView;//标题栏周数
    protected ListView weekListView;//显示周数的ListView
    protected PopupWindow weekListWindow;//选择周数弹出窗口
    protected View popupWindowLayout;//选择周数弹出窗口Layout

    private TextView weekDaysTextView[];
    protected TextView empty;//第一个无内容的格子,用于定位
    protected RelativeLayout table_layout;//课程表body部分布局

    /**
     * Dao成员变量
     */
    GlobalInfoDao gInfoDao;
    UserInfoDao uInfoDao;

    CourseInfoDao cInfoDao;
    UserCourseDao uCourseDao;

    /**
     * 数据模型变量
     */
    GlobalInfo gInfo;//需要isFirstUse和activeUserUid
    UserInfo uInfo;//需要username昵称,gender，phone，headshot，institute，major，year

    /**
     *@Auther kiwi
     *@Data 2019/6/2
     *  url源
     */
    String root= HttpConstant.OriginAddress;
    private String originAddress = root + "/user/course/get";



    /**
     *@Auther kiwi
     *@Data 2019/6/2
     * okhttp声明
     */
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15,TimeUnit.SECONDS)
            .writeTimeout(15,TimeUnit.SECONDS)
            .build();



    /**
     * 数据存储变量
     */
    private LinkedList<CourseInfo> courseInfoList;//课程信息链表，存储有包括cid在内的完整信息
    private Map<String, List<CourseInfo>> courseInfoMap;//课程信息，key为星期几，value是这一天的课程信息

    private List<TextView> courseTextViewList;//保存显示课程信息的TextView
    private Map<Integer, List<CourseInfo>> textviewCourseInfoMap;//保存每个textview对应的课程信息 map,key为哪一天（如星期一则key为1）

    /**
     * 临时变量
     */
    private int uid;
    private SharedPreferences courseSettings; //课程信息设置
    private int cw;//存储当前选择的周数currentWeek
    private int currWeek;//储存当前周

    protected int aveWidth;//课程格子平均宽度
    protected int screenWidth;//屏幕宽度
    protected int gridHeight = 80;//格子高度



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        context = getApplicationContext();

        // 初始化Dao成员变量
        gInfoDao = new GlobalInfoDao(context);
        uInfoDao = new UserInfoDao(context);

        cInfoDao = new CourseInfoDao(context);
        uCourseDao = new UserCourseDao(context);


        // 初始化数据模型变量
        gInfo = gInfoDao.query();
        uid = gInfo.getActiveUserUid();
        uInfo = uInfoDao.query(uid);

        // 初始化数据存储变量
        courseInfoList = new LinkedList<CourseInfo>();
        courseTextViewList = new ArrayList<TextView>();
        textviewCourseInfoMap = new HashMap<Integer, List<CourseInfo>>();

        // 初始化临时变量
        currWeek = TimetableUtil.getWeeks(gInfo.getTermBegin());
        cw = TimetableUtil.getWeeks(gInfo.getTermBegin());

        //获取课表配置信息
        courseSettings = getSharedPreferences("course_setting", MODE_PRIVATE);


        //initDate();//显示在menu中的当前日期
        initView();
        initEvent();
        intInfoRequest();
        initTable();//初始化课表
        refresh();//刷新课表信息
    }



    //用于处理消息的Handler
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            GsonBuilder builder = new GsonBuilder();

            // Register an adapter to manage the date types as long values
            builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            });

            Gson gson = builder.create();

            super.handleMessage(msg);
            String result = "";
            String ReturnMessage = (String) msg.obj;


            final ResultVo showresult = new Gson().fromJson(ReturnMessage, ResultVo.class);
            final int code = showresult.getCode();
            final String message = showresult.getMessage();
            final String data = (String) showresult.getData();

            Log.i("resultvo data is",ReturnMessage);
            Log.i("--------------","-----------");
            Log.i("data is ",data);
            if (code==200){
                result = "获取信息成功";


                CourseDataVo[] array = new Gson().fromJson(data, CourseDataVo[].class);
                courseDataVos= Arrays.asList(array);

            }else if (code==400){
                result = "信息获取失败";
            }
            Toast.makeText(CourseActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };



    private void intInfoRequest()  {



        Context ctx = CourseActivity.this;
        SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);

        SharedPreferences.Editor editor =sp.edit();

//        originAddress = originAddress + "?UserId=kiwi";
        originAddress = originAddress + "?UserId="+sp.getString("userName","none");
        Log.i("url is------",originAddress);
        //发起请求
        final Request request = new Request.Builder()
                .url(originAddress)
                .build();
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    //回调
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        mHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (IOException e) {
                    // Toast.makeText(RegisterActivity.this, "连接不上服务器，请检查网络", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }).start();

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        super.onBackPressed();
    }


    private void initView() {
       mSearch = (Button) findViewById(R.id.rb_search);
       mPerson = (Button) findViewById(R.id.rb_person);
      // mCourse = (RadioButton) findViewById(R.id.rb_course);

        ///////////////////////////////---------------课程表的设计
        //    menuView = findViewById(R.id.Btn_Course_Menu);
        refreshView = findViewById(R.id.Btn_Course_Refresh);
        //设置标题栏周数样式
        weekTextView = (TextView)findViewById(R.id.Menu_main_textWeeks);
        weekTextView.setTextSize(20);
        weekTextView.setPadding(15,2,15,2);
        //右边白色倒三角
        Drawable down = getResources().getDrawable(R.drawable.title_down);
        down.setBounds(0,0,down.getMinimumWidth(),down.getMinimumHeight());
        weekTextView.setCompoundDrawables(null,null,down,null);
        weekTextView.setCompoundDrawablePadding(2);
        //计算并显示上周数
        weekTextView.setText("第" + TimetableUtil.getWeeks(gInfo.getTermBegin()) + "周(本周)");

        weekDaysTextView = new TextView[7];
        weekDaysTextView[0] = (TextView) findViewById(R.id.Text_Course_Subhead_Mon);
        weekDaysTextView[1] = (TextView) findViewById(R.id.Text_Course_Subhead_Tue);
        weekDaysTextView[2] = (TextView) findViewById(R.id.Text_Course_Subhead_Wed);
        weekDaysTextView[3] = (TextView) findViewById(R.id.Text_Course_Subhead_Thu);
        weekDaysTextView[4] = (TextView) findViewById(R.id.Text_Course_Subhead_Fri);
        weekDaysTextView[5] = (TextView) findViewById(R.id.Text_Course_Subhead_Sat);
        weekDaysTextView[6] = (TextView) findViewById(R.id.Text_Course_Subhead_Sun);

        empty = (TextView) this.findViewById(R.id.test_empty);
        empty.getBackground().setAlpha(0);//0~255透明度值;
    }

    //初始化课程表格
    private void initTable() {
        // 列表布局文件
        table_layout = (RelativeLayout) this.findViewById(R.id.test_course_rl);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //屏幕宽度
        int width = dm.widthPixels;
        //平均宽度
        int aveWidth = width / 8;
        //给列头设置宽度
        this.screenWidth = width;
        this.aveWidth = aveWidth;

        //屏幕高度
        int height = dm.heightPixels;
        gridHeight = height / dayCourseNum;

        //设置课表界面，动态生成8 * dayCourseNum个textview
        for (int i = 1; i <= dayCourseNum; i++) {

            for (int j = 1; j <= 8; j++) {
                BorderTextView tx = new BorderTextView(this);
                tx.setId((i - 1) * 8 + j);
                //相对布局参数
                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
                        aveWidth * 33 / 32 + 1,
                        gridHeight);
                //文字对齐方式
                tx.setGravity(Gravity.CENTER);
                //字体样式
                tx.setTextAppearance(this, R.style.courseTableText);
                //如果是第一列，需要设置课的序号（1 到 12）
                if (j == 1) {
                    tx.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_table_first_colum));
                    tx.setText(String.valueOf(i));
                    rp.width = aveWidth * 3 / 4;
                    //设置他们的相对位置
                    if (i == 1)
                        rp.addRule(RelativeLayout.BELOW, empty.getId());
                    else
                        rp.addRule(RelativeLayout.BELOW, (i - 1) * 8);
                } else {
                    rp.addRule(RelativeLayout.RIGHT_OF, (i - 1) * 8 + j - 1);
                    rp.addRule(RelativeLayout.ALIGN_TOP, (i - 1) * 8 + j - 1);
                    tx.setText("");
                }

                tx.setLayoutParams(rp);
                table_layout.addView(tx);
            }
        }

    }

    private void initEvent() {
       mSearch.setOnClickListener(this);
       mPerson.setOnClickListener(this);
     //  mCourse.setOnClickListener(this);
       refreshView.setOnClickListener(this);
       weekTextView.setOnClickListener(this);
    }

    private void Search(){
        Intent intent=new Intent();
        intent.setClass(CourseActivity.this, SearchActivity.class);
        startActivity(intent);
    }
   // private void Course(){
  //      Intent intent=new Intent();
  //      intent.setClass(CourseActivity.this, PersonActivity.class);
  //      startActivity(intent);
 //   }
    private void Person(){
        Intent intent=new Intent();
        intent.setClass(CourseActivity.this, PersonActivity.class);
        startActivity(intent);
    }

    private void aReFresh(){
        courseSettings.edit().putBoolean("needRefresh_" + uid, true).commit();//设置信息需要从服务器获取的标志
        courseInfoList.clear();
        //删掉textView，清空信息，再次添加
        Log.v("refresh:", "清空信息，再次添加");
        for(TextView tx : courseTextViewList) {
            table_layout.removeView(tx);
        }
        courseTextViewList.clear();

        cw= TimetableUtil.getWeeks(gInfo.getTermBegin());
        //计算并显示上周数
        weekTextView.setText("第" + TimetableUtil.getWeeks(gInfo.getTermBegin()) + "周(本周)");
        refresh();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_search:
                Search();
                break;
         //   case R.id.rb_course:
        //        ToRegister();
        //        break;
            case R.id.rb_person:
                Person();
                break;
            case R.id.Btn_Course_Refresh:
                aReFresh();
                break;
            case R.id.Menu_main_textWeeks:
                showWeekListWindow(weekTextView);
                break;
        }
    }


    private void refresh() {
        // 显示状态对话框
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.loading_tip));
        progressDialog.setCancelable(true);
        progressDialog.show();

        getFromLocal(cw);
        for(CourseDataVo courseDataVo:courseDataVos) {
            //模拟从服务器获取的效果
            Log.i("couisisisi ",courseDataVo.toString());
            CourseInfo cInfo1 = new CourseInfo();
            cInfo1.setCid(courseDataVo.getCid());
            cInfo1.setWeekfrom(courseDataVo.getWeekfrom());
            cInfo1.setWeekto(courseDataVo.getWeekto());
            cInfo1.setWeektype(courseDataVo.getWeektype());
            cInfo1.setDay(Integer.parseInt(courseDataVo.getDay()));
            cInfo1.setLessonfrom(courseDataVo.getLessonfrom());
            cInfo1.setLessonto(courseDataVo.getLessonto());
            cInfo1.setCoursename(courseDataVo.getCourseName());
            cInfo1.setTeacher(courseDataVo.getTeacher());
            cInfo1.setPlace(courseDataVo.getPlace());

            courseInfoList.add(cInfo1);
        }


        //如果从服务器获取成功，则插入数据库
        saveCourse();

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        //初始化课表
        initCourse();
        //显示课表内容
        initCourseTableBody(cw);

    }

    //直接从本地数据库缓存提取数据显示
    private void getFromLocal(int cur){
        courseInfoList = uCourseDao.query(uid);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        //初始化课表
        initCourse();
        //显示课表内容
        initCourseTableBody(cur);
    }

    //本地测试:后期从服务器端获取课表
    private void getCourseFromServer(int userid){

        for(CourseDataVo courseDataVo:courseDataVos) {
            //模拟从服务器获取的效果
            Log.i("couisisisi ",courseDataVo.toString());
            CourseInfo cInfo1 = new CourseInfo();
            cInfo1.setCid(courseDataVo.getCid());
            cInfo1.setWeekfrom(courseDataVo.getWeekfrom());
            cInfo1.setWeekto(courseDataVo.getWeekto());
            cInfo1.setWeektype(courseDataVo.getWeektype());
            cInfo1.setDay(Integer.parseInt(courseDataVo.getDay()));
            cInfo1.setLessonfrom(courseDataVo.getLessonfrom());
            cInfo1.setLessonto(courseDataVo.getLessonto());
            cInfo1.setCoursename(courseDataVo.getCourseName());
            cInfo1.setTeacher(courseDataVo.getTeacher());
            cInfo1.setPlace(courseDataVo.getPlace());

            courseInfoList.add(cInfo1);
        }


        //如果从服务器获取成功，则插入数据库
        saveCourse();

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        //初始化课表
        initCourse();
        //显示课表内容
        initCourseTableBody(cw);

    }

    //将课程列表存入数据库
    private boolean saveCourse() {
        if (uCourseDao.clear(uid)) {
            for (CourseInfo cInfo : courseInfoList) {
//                int cid = cInfoDao.insert(cInfo);
                cInfoDao.insert(cInfo);
                int cid = cInfo.getCid();
                if (cid == 0) {
                    return false;
                }
                UserCourse uCourse = new UserCourse();
                uCourse.setUid(uid);
                uCourse.setCid(cid);
                if(!uCourseDao.insert(uCourse)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    //初始化课表，分配空间，将courseInfoList中的课程放入courseInfoMap中
    private void initCourse() {
        courseInfoMap = new HashMap<String, List<CourseInfo>>();
        for (int i =1 ; i <= 7; i++) {
            LinkedList<CourseInfo> dayCourses = new LinkedList<CourseInfo>();
            for (CourseInfo courseInfo : courseInfoList) {
                int day = courseInfo.getDay();
                if(day==i) {
                    dayCourses.add(courseInfo);
                }
            }
            courseInfoMap.put(String.valueOf(i),dayCourses);
        }
    }

    private void initCourseTableBody(int currentWeek){
        for(Map.Entry<String, List<CourseInfo>> entry: courseInfoMap.entrySet())
        {
            //查找出最顶层的课程信息（顶层课程信息即显示在最上层的课程，最顶层的课程信息满足两个条件 1、当前周数在该课程的周数范围内 2、该课程的节数跨度最大
            CourseInfo upperCourse = null;
            //list里保存的是一周内某 一天的课程
            final List<CourseInfo> list = new ArrayList<CourseInfo>(entry.getValue());
            //按开始的时间（哪一节）进行排序
            Collections.sort(list, new Comparator<CourseInfo>(){
                @Override
                public int compare(CourseInfo arg0, CourseInfo arg1) {

                    if(arg0.getLessonfrom() < arg1.getLessonfrom())
                        return -1;
                    else
                        return 1;
                }

            });
            int lastListSize;
            do {

                lastListSize = list.size();
                Iterator<CourseInfo> iter = list.iterator();
                //先查找出第一个在周数范围内的课
                while(iter.hasNext())
                {
                    CourseInfo c = iter.next();
                    if(((c.getWeekfrom() <= currentWeek && c.getWeekto() >= currentWeek) || currentWeek == -1) && c.getLessonto() <= 12)
                    {
                        //判断当前周是否要放置该课程（该课程是否符合当前周单双周上课要求）
                        if(TimetableUtil.isCurrWeek(c,currentWeek)) {
                            //从list中移除该项，并设置这节课为顶层课
                            iter.remove();
                            upperCourse = c;
                            break;
                        }
                    }
                }
                if(upperCourse != null)
                {
                    List<CourseInfo> cInfoList = new ArrayList<CourseInfo>();
                    cInfoList.add(upperCourse);
                    int index = 0;
                    iter = list.iterator();
                    //查找这一天有哪些课与刚刚查找出来的顶层课相交
                    while(iter.hasNext())
                    {
                        CourseInfo c = iter.next();
                        //先判断该课程与upperCourse是否相交，如果相交加入cInfoList中
                        if((c.getLessonfrom() <= upperCourse.getLessonfrom()
                                &&upperCourse.getLessonfrom() < c.getLessonto())
                                ||(upperCourse.getLessonfrom() <= c.getLessonfrom()
                                && c.getLessonfrom() < upperCourse.getLessonto()))
                        {
                            cInfoList.add(c);
                            iter.remove();
                            //在判断哪个跨度大，跨度大的为顶层课程信息
                            if((c.getLessonto() - c.getLessonto()) > (upperCourse.getLessonto() - upperCourse.getLessonfrom())
                                    && ((c.getWeekfrom() <= currentWeek && c.getWeekto() >= currentWeek) || currentWeek == -1))
                            {
                                upperCourse = c;
                                index ++;
                            }

                        }

                    }

                    //五种颜色的背景
                    int[] background = {R.drawable.main_course1, R.drawable.main_course2,
                            R.drawable.main_course3, R.drawable.main_course4,
                            R.drawable.main_course5};
                    //记录顶层课程在cInfoList中的索引位置
                    final int upperCourseIndex = index;
                    // 动态生成课程信息TextView
                    TextView courseInfo = new TextView(this);
                    courseInfo.setId(1000 + upperCourse.getDay() * 100 + upperCourse.getLessonfrom() * 10 + upperCourse.getCid());//设置id区分不同课程
                    int id = courseInfo.getId();
                    textviewCourseInfoMap.put(id, cInfoList);
                    courseInfo.setText(upperCourse.getCoursename() + "\n@" + upperCourse.getPlace());
                    //该textview的高度根据其节数的跨度来设置
                    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                            aveWidth * 31 / 32,
                            (gridHeight - 5) * 2 + (upperCourse.getLessonto() - upperCourse.getLessonfrom() - 1) * gridHeight);
                    //textview的位置由课程开始节数和上课的时间（day of week）确定
                    rlp.topMargin = 5 + (upperCourse.getLessonfrom() - 1) * gridHeight;
                    rlp.leftMargin = 1;
                    // 前面生成格子时的ID就是根据Day来设置的，偏移由这节课是星期几决定
                    rlp.addRule(RelativeLayout.RIGHT_OF, upperCourse.getDay());
                    //字体居中中
                    courseInfo.setGravity(Gravity.CENTER);
                    //选择一个颜色背景
                    int colorIndex = ((upperCourse.getLessonfrom() - 1) * 8 + upperCourse.getDay()) % (background.length - 1);
                    courseInfo.setBackgroundResource(background[colorIndex]);
                    courseInfo.setTextSize(12);
                    courseInfo.setLayoutParams(rlp);
                    courseInfo.setTextColor(Color.WHITE);
                    //设置不透明度
                    courseInfo.getBackground().setAlpha(200);
                    // 设置监听事件
                    courseInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            Log.v("text_view", String.valueOf(arg0.getId()));
                            Map<Integer, List<CourseInfo>> map = textviewCourseInfoMap;
                            final List<CourseInfo> tempList = map.get(arg0.getId());
                            if(tempList.size() > 1)
                            {
                                //如果有多个课程，则设置点击弹出gallery 3d 对话框
                                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View galleryView = layoutInflater.inflate(R.layout.info_gallery_layout, null);
                                final Dialog coursePopupDialog = new AlertDialog.Builder(CourseActivity.this).create();
                                coursePopupDialog.setCanceledOnTouchOutside(true);
                                coursePopupDialog.setCancelable(true);
                                coursePopupDialog.show();
                                WindowManager.LayoutParams params = coursePopupDialog.getWindow().getAttributes();
                                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                                coursePopupDialog.getWindow().setAttributes(params);
                                CourseInfoAdapter adapter = new CourseInfoAdapter(CourseActivity.this, tempList, screenWidth, cw);
                                InfoGallery gallery = (InfoGallery) galleryView.findViewById(R.id.info_gallery);
                                gallery.setSpacing(10);
                                gallery.setAdapter(adapter);
                                gallery.setSelection(upperCourseIndex);
                                gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(
                                            AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {
                                        CourseInfo courseInfo = tempList.get(arg2);
                                        Intent intent = new Intent();
                                        Bundle mBundle = new Bundle();
                                        mBundle.putSerializable("courseInfo", courseInfo);
                                        intent.putExtras(mBundle);
                                        intent.setClass(CourseActivity.this, CourseDetailInfoActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                                        coursePopupDialog.dismiss();
                                        finish();
                                    }
                                });
                                coursePopupDialog.setContentView(galleryView);
                            }
                            else
                            {
                                Intent intent = new Intent();
                                Bundle mBundle = new Bundle();
                                mBundle.putSerializable("courseInfo", tempList.get(0));
                                intent.putExtras(mBundle);
                                intent.setClass(CourseActivity.this, CourseDetailInfoActivity.class);
                                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                                startActivity(intent);
                                finish();
                            }

                        }

                    });
                    table_layout.addView(courseInfo);
                    courseTextViewList.add(courseInfo);

                    upperCourse = null;
                }
            } while(list.size() < lastListSize && list.size() != 0);
        }

    }



    /**
     * 显示周数下拉列表悬浮窗
     * @param parent
     */
    private void showWeekListWindow(View parent){

        if(weekListWindow == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //获取layout
            popupWindowLayout = layoutInflater.inflate(R.layout.week_list_layout, null);
            popupWindowLayout.setBackgroundColor(Color.rgb(216,216,216));
            weekListView = (ListView) popupWindowLayout.findViewById(R.id.week_list_view_body);

            List<Map<String, Object>> weekList = new ArrayList<Map<String, Object>>();
            //默认25周
            for(int i = 1; i <= 25; i ++)
            {
                Map<String, Object> rowData = new HashMap<String, Object>();
                rowData.put("week_index", "第" + i + "周");
                weekList.add(rowData);
            }

            //设置listview的adpter
            SimpleAdapter listAdapter = new SimpleAdapter(this,
                    weekList, R.layout.week_list_item_layout,
                    new String[]{"week_index"},
                    new int[]{R.id.week_list_item});

            //设置recyclerview类型的listview的adpter()
//            WeekAdapter listAdapter = new WeekAdapter(weekList);
            weekListView.setAdapter(listAdapter);
            weekListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adpater, View arg1,
                                        int arg2, long arg3) {
                    int index = 0;
                    String indexStr = weekTextView.getText().toString();
                    indexStr = indexStr.replace("第", "").replace("周(本周)", "");
                    indexStr = indexStr.replace("周(非本周)", "");
                    if(!indexStr.equals("全部"))//没啥用
                        index = Integer.parseInt(indexStr);
                    if(currWeek == (arg2 + 1)){
                        weekTextView.setText("第" + (arg2 + 1) + "周(本周)");
                    }
                    else{
                        weekTextView.setText("第" + (arg2 + 1) + "周(非本周)");
                    }
                    weekListWindow.dismiss();
                    if((arg2 + 1) != index)
                    {
                        cw = arg2+1;
                        Log.v("courseActivity", "cw值改变："+ cw);
                        Log.v("courseActivity", "清空当前课程信息");
                        for(TextView tx : courseTextViewList)
                        {
                            table_layout.removeView(tx);
                        }
                        courseTextViewList.clear();
                        //重新设置课程信息
                        initCourse();
                        initCourseTableBody(cw);

                    }
                }
            });
            int width = weekTextView.getWidth();
            //实例化一个popupwindow
            weekListWindow = new PopupWindow(popupWindowLayout, width + 100, width + 120);

        }

        weekListWindow.setFocusable(true);
        //设置点击外部可消失
        weekListWindow.setOutsideTouchable(true);
        weekListWindow.setBackgroundDrawable(new BitmapDrawable());
        //消失的时候恢复按钮的背景（消除"按下去"的样式）
        weekListWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                weekTextView.setBackgroundDrawable(null);
            }
        });
        weekListWindow.showAsDropDown(parent, -50, 0);
    }


    public static Context getContext() {
        return context;
    }



}

