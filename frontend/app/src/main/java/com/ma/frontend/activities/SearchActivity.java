package com.ma.frontend.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ma.frontend.R;
import com.ma.frontend.activities.person.UpdateAcivity;
import com.ma.frontend.activities.search.*;
import okhttp3.RequestBody;

/**
 * @Auther:kiwi
 * @Date: 2019/5/20 19:40
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    //private Button mSearch;
    private Button mCourse;
    private Button mPerson;

    private TextView tGrade;
    private TextView tHomworksetting;
    private LinearLayout tCourse;

    protected LinearLayout mAddcourse;
    protected LinearLayout mCheckgrade;
    protected LinearLayout mCheckTeacher;
    protected LinearLayout mCourseEvatue;
    protected LinearLayout mHomeWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initView();
        initEvent();
    }
    private void initView() {
      // mSearch = (Button) findViewById(R.id.rb_search);
       mPerson = (Button) findViewById(R.id.rb_person);
       mCourse = (Button) findViewById(R.id.rb_course);

       mAddcourse = (LinearLayout) findViewById(R.id.add_course);
       mCheckgrade = (LinearLayout) findViewById(R.id.check_grade);
       mCheckTeacher = (LinearLayout) findViewById(R.id.check_teacher);
       mHomeWork = (LinearLayout) findViewById(R.id.home_work);
       mCourseEvatue = (LinearLayout) findViewById(R.id.course_evatue);


       ////////
        tCourse =(LinearLayout) findViewById(R.id.course);
        tGrade =(TextView)findViewById(R.id.searchgrade);
        tHomworksetting=(TextView)findViewById(R.id.searchgrade);


        Context ctx = SearchActivity.this;
        SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        if(sp.getString("rid","none").equals("2")){
          tCourse.setVisibility(View.INVISIBLE);
          tGrade.setText("设置平时成绩");
          tHomworksetting.setText("设置出勤和作业");
        }
    }

    private void initEvent() {
     //  mSearch.setOnClickListener(this);
       mPerson.setOnClickListener(this);
       mCourse.setOnClickListener(this);


       mAddcourse.setOnClickListener(this);
       mCheckgrade.setOnClickListener(this);
       mCheckTeacher.setOnClickListener(this);
       mCourseEvatue.setOnClickListener(this);
       mHomeWork.setOnClickListener(this);
    }

   // private void Search(){
   //     Intent intent=new Intent();
   //     intent.setClass(SearchActivity.this, PersonActivity.class);
   //     startActivity(intent);
  //  }
    private void Course(){
        Intent intent=new Intent();
        intent.setClass(SearchActivity.this, CourseActivity.class);
        startActivity(intent);
           }
    private void Person(){
        Intent intent=new Intent();
        intent.setClass(SearchActivity.this, PersonActivity.class);
        startActivity(intent);
    }
    private void CheckGrade(){
        Intent intent=new Intent();
        intent.setClass(SearchActivity.this, CheckGradeAcivity.class);
        startActivity(intent);
    }
    private void AddCourse(){
        Intent intent=new Intent();
        intent.setClass(SearchActivity.this, AddCourseAcitvity.class);
        startActivity(intent);
    }
    private void CheckTeacher(){
        Intent intent=new Intent();
        intent.setClass(SearchActivity.this, CheckTeacherAcivity.class);
        startActivity(intent);
    }
    private void HomeWork(){
        Intent intent=new Intent();
        intent.setClass(SearchActivity.this, HomeWorkAcivity.class);
        startActivity(intent);
    }
    private void CourseEvatue(){
        Intent intent=new Intent();
        intent.setClass(SearchActivity.this, CourseEvatueAcivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
          //  case R.id.rb_search:
        //        Search();
        //        break;
            case R.id.rb_course:
                Course();
                break;
            case R.id.rb_person:
                Person();
                break;
            case R.id.add_course:
                AddCourse();
                break;
            case R.id.check_grade:
                CheckGrade();
                break;
            case R.id.check_teacher:
                CheckTeacher();
                break;
            case R.id.course_evatue:
                CourseEvatue();
                break;
            case R.id.home_work:
                HomeWork();
                break;
        }
    }


}

