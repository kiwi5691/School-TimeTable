package com.ma.frontend.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.*;
import com.ma.frontend.R;
import com.ma.frontend.Vo.ResultVo;
import com.ma.frontend.Vo.StudentInfoVo;
import com.ma.frontend.Vo.TeacherInfoVo;
import com.ma.frontend.activities.person.LookupAcivity;
import com.ma.frontend.activities.person.SettingAcivity;
import com.ma.frontend.activities.person.UpdateAcivity;
import com.ma.frontend.config.HttpConstant;
import com.ma.frontend.db.dao.CourseInfoDao;
import com.ma.frontend.db.dao.GlobalInfoDao;
import com.ma.frontend.db.dao.UserCourseDao;
import com.ma.frontend.db.dao.UserInfoDao;
import com.ma.frontend.domain.GlobalInfo;
import com.ma.frontend.domain.UserInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Auther:kiwi
 * @Date: 2019/5/20 19:40
 */
public class PersonActivity extends AppCompatActivity implements View.OnClickListener{

    private static Context context;

    private int uid;

    public StudentInfoVo studentInfoVo;
    public TeacherInfoVo teacherInfoVo;
    private Button mSearch;
    private Button mCourse;
 //   private RadioButton mPerson;

    protected TextView dateTextView;
    protected ImageView headshotView;
    protected TextView nameTextView;
    protected TextView insTextView;
    protected TextView majorTextView;
    protected Button logoutButton;
    protected Button updateButton;
    protected Button settingButton;
    protected Button lookupButton;


    /**
     *@Auther kiwi
    */
    String root= HttpConstant.OriginAddress;
    private String originAddress = root + "/user/showStudentInfo";
    private String originAddressTeacher = root +"/user/showTeacherInfo";
    private String logoutAddress = root + "/user/logout";


    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15,TimeUnit.SECONDS)
            .writeTimeout(15,TimeUnit.SECONDS)
            .build();


    /**
     * Dao成员变量
     */
    GlobalInfoDao gInfoDao;
    UserInfoDao uInfoDao;

    CourseInfoDao cInfoDao;
    UserCourseDao uCourseDao;

    GlobalInfo gInfo;//需要isFirstUse和activeUserUid
    UserInfo uInfo;//需要username昵称,gender，phone，headshot，institute，major，year


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);
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

        initView();
        initEvent();
        initDate();
        intInfoRequest();

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
        //    Log.i("data is ",data);
            if (code==200){
                result = "获取信息成功";

                if(data.isEmpty()){
                    insTextView.setText("请填写信息");
                }else {
                    Context ctx = PersonActivity.this;
                    SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);
                    SharedPreferences.Editor editor =sp.edit();
                    if(sp.getString("rid","none").equals("1")) {
                        studentInfoVo = new Gson().fromJson(data, StudentInfoVo.class);
                        Log.i("info json ==", studentInfoVo.toString());


                        //////////div头像的的变量初始化
                        if (studentInfoVo.getGender().equals("2")) {
                            headshotView.setImageResource(R.drawable.nav_icon_female);
                        } else {
                            headshotView.setImageResource(R.drawable.nav_icon_male);
                        }
                        nameTextView.setText(studentInfoVo.getNickName() + "，你好");

                        if (studentInfoVo.getInstitute() != null) {
                            insTextView.setText(studentInfoVo.getInstitute());
                            majorTextView.setText(studentInfoVo.getMajor());
                        } else {
                            insTextView.setText("请填写信息");
                        }
                    }else {
                        teacherInfoVo = new Gson().fromJson(data, TeacherInfoVo.class);
                        Log.i("info json ==", teacherInfoVo.toString());


                        //////////div头像的的变量初始化
                        if (teacherInfoVo.getGender().equals("2")) {
                            headshotView.setImageResource(R.drawable.nav_icon_female);
                        } else {
                            headshotView.setImageResource(R.drawable.nav_icon_male);
                        }
                        nameTextView.setText(teacherInfoVo.getNickName() + "老师，你好");

                        insTextView.setVisibility(View.INVISIBLE);
                        majorTextView.setVisibility(View.INVISIBLE);


                    }

                    }

            }else if (code==400){
                result = "信息获取失败";
            }
            Toast.makeText(PersonActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };



    //用于退出的的Handler
    private Handler logoutHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            String result = "";
            String ReturnMessage = (String) msg.obj;

            Log.i(ReturnMessage,"----me is");
            final ResultVo showresult = new Gson().fromJson(ReturnMessage, ResultVo.class);
            final int code = showresult.getCode();
            final String message = showresult.getMessage();

            if (code==200){
                result = message;
                Intent intent=new Intent();
                intent.setClass(PersonActivity.this, LoginActivity.class);
                startActivity(intent);

            }else if (code==400){
                result = "退出成功";

                Intent intent=new Intent();
                intent.setClass(PersonActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            Toast.makeText(PersonActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };


    private void initView() {
        mSearch = (Button) findViewById(R.id.rb_search);
    //    mPerson = (RadioButton) findViewById(R.id.rb_person);
        mCourse = (Button) findViewById(R.id.rb_course);

        //////////div头像的的变量初始化
        headshotView = (ImageView)findViewById(R.id.icon_image);
        if(uInfo.getGender().equals("女")){
            headshotView.setImageResource(R.drawable.nav_icon_female);
        }
        else{
            headshotView.setImageResource(R.drawable.nav_icon_male);
        }
        nameTextView = (TextView)findViewById(R.id.Menu_main_name);
        nameTextView.setText(uInfo.getUsername()+"，你好");
        insTextView = (TextView)findViewById(R.id.Menu_main_institute);
        insTextView.setText(uInfo.getInstitute());
        majorTextView = (TextView)findViewById(R.id.Menu_main_major);
        majorTextView.setText(uInfo.getMajor());


        dateTextView = (TextView)findViewById(R.id.Menu_main_textDate);
        logoutButton = (Button)findViewById(R.id.rb_logout);
        updateButton = (Button)findViewById(R.id.rb_update);
        settingButton = (Button)findViewById(R.id.rb_setting);
        lookupButton = (Button)findViewById(R.id.rb_lookup);
    }

    @SuppressLint("SimpleDateFormat")
    private void initDate() {
        Date currentTime = new Date();
        String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTime);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)  w = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日  ");
        String dateString = formatter.format(currentTime);
        Log.i("date is",dateString + "星期" + weekDays[w]);
        dateTextView.setText(dateString + "星期" + weekDays[w]);
    }


    /**
     *@Auther kiwi
     *@Data 2019/5/19
     */
    private void intInfoRequest()  {



        Context ctx = PersonActivity.this;
        SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);

        SharedPreferences.Editor editor =sp.edit();


        String tempAddress="";
//        originAddress = originAddress + "?UserId=kiwi";
       if(sp.getString("rid","none").equals("1")) {
           tempAddress = originAddress + "?UserId=" + sp.getString("userName", "none");
           Log.i("url is------", tempAddress);
       }
       else {
           tempAddress = originAddressTeacher + "?UserId=" + sp.getString("userName", "none");
           Log.i("url is------", tempAddress);
       }
       //发起请求
        final Request request = new Request.Builder()
                .url(tempAddress)
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



    private void initEvent() {
        mSearch.setOnClickListener(this);
      //  mPerson.setOnClickListener(this);
        mCourse.setOnClickListener(this);
        //////////4个框
        lookupButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        settingButton.setOnClickListener(this);
    }

    private void Search(){
        Intent intent=new Intent();
        intent.setClass(PersonActivity.this, SearchActivity.class);
        startActivity(intent);
    }
     private void Course(){
          Intent intent=new Intent();
          intent.setClass(PersonActivity.this, CourseActivity.class);
          startActivity(intent);
       }

     private void Updates(){
         Intent intent=new Intent();
         intent.setClass(PersonActivity.this, UpdateAcivity.class);
         startActivity(intent);
     }
     private void Lookup(){
         Intent intent=new Intent();
         intent.setClass(PersonActivity.this, LookupAcivity.class);
         startActivity(intent);
     }

     
     /**
      *@Auther kiwi
      *@Data 2019/5/24
      @param  * @param
      *@reutn void
     */
     private void Logout(){
         AlertDialog alertDialog = new AlertDialog.Builder(PersonActivity.this)
                 .setTitle("登出")
                 .setMessage("是否登出")
                 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {



                         Context ctx = PersonActivity.this;
                         SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);

                         SharedPreferences.Editor editor =sp.edit();

//        originAddress = originAddress + "?UserId=kiwi";
                         logoutAddress = logoutAddress + "?UserId="+sp.getString("userName","none")+"&rid="+sp.getString("rid","none");
                         Log.i("url is------",logoutAddress);
                         //发起请求
                         final Request request = new Request.Builder()
                                 .url(logoutAddress)
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
                                         logoutHandler.obtainMessage(2, response.body().string()).sendToTarget();
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
                 })
                 .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         return;
                     }
                 }).create();
         alertDialog.show();



     }
     private void Setting(){
         Intent intent=new Intent();
         intent.setClass(PersonActivity.this, SettingAcivity.class);
         startActivity(intent);
     }
   // private void Person(){
   //     Intent intent=new Intent();
   //     intent.setClass(PersonActivity.this, PersonActivity.class);
   //     startActivity(intent);
  //  }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_search:
                Search();
                break;
            case R.id.rb_course:
                Course();
                break;
            case R.id.rb_lookup:
                Lookup();
                break;
            case R.id.rb_setting:
                Setting();
                break;
            case R.id.rb_logout:
                Logout();
                break;
            case R.id.rb_update:
                Updates();
                break;
           // case R.id.rb_person:
          //      Person();
          //      break;
        }
    }


}

