package com.ma.frontend.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ma.frontend.R;
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
     *TODO 服务端分配学生，教师初始化。josn传值
    */
    String root= HttpConstant.OriginAddress;
    private String originAddress = root + "/user/login?";

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

    }
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
                         Intent intent=new Intent();
                         intent.setClass(PersonActivity.this, LoginActivity.class);
                         startActivity(intent);
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

