package com.ma.frontend.activities.search;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.ma.frontend.R;

/**
 * @Auther:kiwi
 * @Date: 2019/5/23 20:54
 */
public class AddCourseAcitvity extends AppCompatActivity implements View.OnClickListener{


    private Button rb_addCourse;


    private EditText mCoursename;
    private EditText mTeachername;
    private EditText mCourselocal;
    private EditText mStarweek;
    private EditText mEndweek;
    private EditText mCoursetime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_addcourse);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initView();
        initEvent();

    }

    private void initView() {
        mTeachername = (EditText) findViewById(R.id.user_name_input);
        mStarweek = (EditText) findViewById(R.id.user_password_input);
        mEndweek = (EditText) findViewById(R.id.user_password_input_d);
        mCoursetime= (EditText) findViewById(R.id.course_day);
        mCoursename = (EditText) findViewById(R.id.user_phone_input);
        mCourselocal = (EditText)findViewById(R.id.look_up);

        rb_addCourse=(Button)findViewById(R.id.register_button);
    }

    private void initEvent() {
        rb_addCourse.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
