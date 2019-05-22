package com.ma.frontend.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.ma.frontend.R;
import com.ma.frontend.db.dao.GlobalInfoDao;
import com.ma.frontend.db.dao.UserCourseDao;
import com.ma.frontend.domain.CourseInfo;
import com.ma.frontend.domain.GlobalInfo;
import com.ma.frontend.utils.TimetableUtil;

/**
 * @Auther:kiwi
 * @Date: 2019/5/22 17:58
 */
public class  CourseDetailInfoActivity extends FragmentActivity {

    /**
     *  静态成员变量
     */
    private static Context context;

    /**
     * UI相关成员变量
     */
    private ProgressDialog progressDialog;

    /**
     * View相关成员变量
     */
    protected View backView;
    protected TextView titleView;

    protected TextView courseNameView;//课程名
    protected TextView courseTeacherView;//任课老师
    protected TextView courseTimeView;//上课时间
    protected TextView courseWeekView;//上课周数
    protected TextView courseSpaceView;//上课地点

    protected Button deleteButton;//删除课程

    //Dao成员变量
    GlobalInfoDao gInfoDao;
    UserCourseDao uCourseDao;

    //数据模型变量
    GlobalInfo gInfo;//需要isFirstUse和activeUserUid

    //数据存储变量
    CourseInfo cInfo;

    //临时变量
    private int uid,cid;

    /**
     * Activity回调函数
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 继承父类方法，绑定View
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        // 初始化context
        context = getApplicationContext();

        // 初始化Dao成员变量
        gInfoDao = new GlobalInfoDao(context);
        uCourseDao = new UserCourseDao(context);

        // 初始化数据模型变量
        gInfo = gInfoDao.query();
        uid = gInfo.getActiveUserUid();
        cInfo = new CourseInfo();
        cInfo = (CourseInfo) getIntent().getSerializableExtra("courseInfo");//从Intent中取回Serializable的course_info内容
        cid = cInfo.getCid();

        // 初始化临时变量

        // 自定义函数
        initView();//初始化CourseActivity界面
        initListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void initView() {
        backView = findViewById(R.id.Btn_Course_Back);
        //设置标题栏
        titleView = (TextView) findViewById(R.id.Text_Course_Title);
        titleView.setTextSize(20);
        titleView.setPadding(15, 2, 15, 2);

        courseNameView = (TextView) findViewById(R.id.Detail_Course_Name);
        courseTeacherView = (TextView) findViewById(R.id.Detail_Course_Teacher);
        courseTimeView = (TextView) findViewById(R.id.Detail_Course_Time);
        courseWeekView = (TextView) findViewById(R.id.Detail_Course_Week);
        courseSpaceView = (TextView) findViewById(R.id.Detail_Course_Space);

        courseNameView.setText("课程名：" + cInfo.getCoursename());
        courseTeacherView.setText("任课教师：" + cInfo.getTeacher());
        courseTimeView.setText("时间：星期" + TimetableUtil.getDayStr(cInfo.getDay()) + " 第" + cInfo.getLessonfrom() + "节 - 第" + cInfo.getLessonto() + "节");
        if(cInfo.getWeektype()==1) {
            courseWeekView.setText("周数：第" +  cInfo.getWeekfrom() + "周 - 第" + cInfo.getWeekto() + "周");
        }else if(cInfo.getWeektype()==2) {
            courseWeekView.setText("周数：第" +  cInfo.getWeekfrom() + "节 - 第" + cInfo.getWeekto()+ "周 单周");
        }else if(cInfo.getWeektype()==3) {
            courseWeekView.setText("周数：第" +  cInfo.getWeekfrom() + "节 - 第" + cInfo.getWeekto()+ "周 双周");
        }
        courseSpaceView.setText("地点：" + cInfo.getPlace());

        deleteButton = (Button) findViewById(R.id.Btn_Delete);
    }

    private void initListener() {
        backView.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                jumpToCourse();
            }
        });

        deleteButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                deleteCourse();
            }
        });

    }


    private void deleteCourse(){
        // 显示状态对话框
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.loading_tip));
        progressDialog.setCancelable(true);
        progressDialog.show();

        handleDeleteCourse(uid, cid);
    }

    /**
     * 删除课程子线程,删除uc表项，选项2为删除课程
     */
    private void handleDeleteCourse(int uid,int cid) {

    }

    private void jumpToCourse() {//返回课程界面
        Intent intent = new Intent();
        intent.setClass(this, CourseActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        finish();
    }




}
