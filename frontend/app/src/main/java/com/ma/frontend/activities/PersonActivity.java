package com.ma.frontend.activities;

import android.app.Activity;
import android.app.Person;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import com.ma.frontend.R;

/**
 * @Auther:kiwi
 * @Date: 2019/5/20 19:40
 */
public class PersonActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mSearch;
    private Button mCourse;
 //   private RadioButton mPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initView();
        initEvent();
    }
    private void initView() {
        mSearch = (Button) findViewById(R.id.rb_search);
    //    mPerson = (RadioButton) findViewById(R.id.rb_person);
        mCourse = (Button) findViewById(R.id.rb_course);
    }

    private void initEvent() {
        mSearch.setOnClickListener(this);
      //  mPerson.setOnClickListener(this);
        mCourse.setOnClickListener(this);
    }

    private void Search(){
        Intent intent=new Intent();
        intent.setClass(PersonActivity.this, PersonActivity.class);
        startActivity(intent);
    }
     private void Course(){
          Intent intent=new Intent();
          intent.setClass(PersonActivity.this, CourseActivity.class);
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
           // case R.id.rb_person:
          //      Person();
          //      break;
        }
    }


}

