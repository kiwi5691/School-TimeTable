package com.ma.frontend.activities.person;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.ma.frontend.R;

/**
 * @Auther:kiwi
 * @Date: 2019/5/22 20:11
 */
public class LookupAcivity extends AppCompatActivity {

    private TextView mNickname;
    private TextView mYearin;
    private TextView mMajor;
    private TextView mInstitute;
    private TextView sex;
    private TextView mPhone;
    private TextView mLocal;
    private TextView mUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_lookup);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initView();
        initEvent();
    }

    private void initView() {
        mInstitute = (EditText) findViewById(R.id.institute_d);
        mPhone = (EditText) findViewById(R.id.user_phone);
        mMajor = (EditText) findViewById(R.id.major);
        mYearin= (EditText) findViewById(R.id.year_in_d);
        mNickname = (EditText) findViewById(R.id.user_name);
        sex = (EditText)findViewById(R.id.user_gender_d);
    }

    private void initEvent() {

    }

}
