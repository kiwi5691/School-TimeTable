package com.ma.frontend.activities.search;

import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.gson.Gson;
import com.ma.frontend.R;
import com.ma.frontend.Vo.ResultVo;
import com.ma.frontend.activities.InitActivity;
import com.ma.frontend.activities.LoginActivity;
import com.ma.frontend.activities.PersonActivity;
import com.ma.frontend.activities.SearchActivity;
import com.ma.frontend.config.GolabConstant;
import com.ma.frontend.config.HttpConstant;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Auther:kiwi
 * @Date: 2019/6/6 14:28
 */
public class AddEvatueAcivity  extends AppCompatActivity implements View.OnClickListener {


    private static Context context;
    private EditText mInfo;
    private EditText mGrade;
    private Button bAdd;
    private TextView mCourseName;


    String root= HttpConstant.OriginAddress;
    private String originAddress = root + "/user/search/updateClassEvate?";

    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15,TimeUnit.SECONDS)
            .writeTimeout(15,TimeUnit.SECONDS)
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_addevatue);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        context = getApplicationContext();
        initView();
        intEvent();


    }
    public void initView(){
        mInfo =(EditText)findViewById(R.id.institute_d);
        mCourseName=(TextView)findViewById(R.id.year_in_d);
        mGrade =(EditText)findViewById(R.id.major);
        bAdd=(Button) findViewById(R.id.update_button);

        Intent intent =getIntent();
        mCourseName.setText(intent.getStringExtra("courseName"));
    }

    public void intEvent(){
        bAdd.setOnClickListener(this);
    }




    //用于处理消息的Handler
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = "";
            String ReturnMessage = (String) msg.obj;
            final ResultVo showresult = new Gson().fromJson(ReturnMessage, ResultVo.class);
            final int code = showresult.getCode();
            final String message = showresult.getMessage();
            Log.i("code is",String.valueOf(code));

            if (code==200){
                result = "添加成功";

                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(AddEvatueAcivity.this, SearchActivity.class);
                startActivity(intent);
            }else if (code==400){
                result = "添加失败";
            }
            Toast.makeText(AddEvatueAcivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };




    private boolean isInputValid() {

        if (mGrade.getText().toString().isEmpty()||mInfo.getText().toString().isEmpty()) {
            Toast.makeText(AddEvatueAcivity.this, "不能为空！", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }


    public void update(){


        Context ctx = AddEvatueAcivity.this;
        SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);

        SharedPreferences.Editor editor =sp.edit();

        //建立请求表单，添加上传服务器的参数
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("courseName",mCourseName.getText().toString().trim())
                .addFormDataPart("evaluationScore",mGrade.getText().toString().trim())
                .addFormDataPart("evaluationInfo",mInfo.getText().toString().trim())
                .addFormDataPart("UserId",sp.getString("userName","none"))
                .build();
        //发起请求
        final Request request = new Request.Builder()
                .url(originAddress)
                .post(formBody)
                .build();
        Log.i("url",originAddress);
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
                    //      Toast.makeText(LoginActivity.this, "连接不上服务器，请检查网络", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }).start();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_button:
                update();
                break;
        }
    }

}
