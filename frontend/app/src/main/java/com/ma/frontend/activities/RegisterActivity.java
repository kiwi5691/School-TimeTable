package com.ma.frontend.activities;

import android.content.Intent;
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
import com.ma.frontend.config.HttpConstant;
import com.ma.frontend.utils.HttpCallbackListener;
import com.ma.frontend.utils.HttpUtil;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Auther:kiwi
 * @Date: 2019/5/19 23:58
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mUsernameEditText;
    private EditText mPassWordEditText;
    private Button mRegistButton;
    private EditText mNicknameEditText;
    private TextView  mLoginButton;
    private EditText mPwdCheckText;
    private RadioButton mTeacher;
    private RadioButton mStudent;

    //用于接收Http请求的servlet的URL地址
    // private String originAddress = getResources().getString(R.string.url_root) +"/user_student/login";

    public String name = " ";
    public String pwd = " ";
    public String nikname=" ";
    public String pwdCheck=" ";
    public String Ridt ="1";

    /**
     *@Auther kiwi
     *@Data 2019/6/2
     *  url源
    */
    String root= HttpConstant.OriginAddress;
    private String originAddress = root + "/user/register?";


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initView();
        initEvent();
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
                result = "注册成功";

                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(RegisterActivity.this, InitActivity.class);
                startActivity(intent);
            }else if (code==400){
                result = message;
            }
            Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };


    private void initView() {
        mNicknameEditText = (EditText) findViewById(R.id.user_name_input);
        mPassWordEditText = (EditText) findViewById(R.id.user_password_input);
        mPwdCheckText = (EditText) findViewById(R.id.user_password_input_d);
        mUsernameEditText = (EditText) findViewById(R.id.user_phone_input);
        mRegistButton = (Button) findViewById(R.id.register_button);
        mLoginButton = (TextView) findViewById(R.id.login_text);
        mTeacher = (RadioButton) findViewById(R.id.ck_teacher);
        mStudent = (RadioButton) findViewById(R.id.ck_student);
    }

    private void initEvent() {
        mLoginButton.setOnClickListener(this);
        mRegistButton.setOnClickListener(this);
        mTeacher.setOnClickListener(this);
        mStudent.setOnClickListener(this);
    }


    /**
     *@Auther kiwi
     *@Data 2019/5/19
     @param  * @param username
      * @param password
     *@reutn void
     */
    private void LoginRequest(String username,String password,String nikName)  {

        if (!isInputValid()){
            return;
        }
        name = mUsernameEditText.getText().toString().trim();
        pwd = mPassWordEditText.getText().toString().trim();
        nikname =mNicknameEditText.getText().toString().trim();

        //建立请求表单，添加上传服务器的参数
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("UserName",name)
                .addFormDataPart("UserPassword",pwd)
                .addFormDataPart("Nickname",nikname)
                .addFormDataPart("rid","1")
                .build();
        //发起请求
        final Request request = new Request.Builder()
                .url(originAddress)
                .post(formBody)
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

    private void ToLogin(){
        Intent intent =new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    private boolean isInputValid() {
        name = mUsernameEditText.getText().toString().trim();
        pwd = mPassWordEditText.getText().toString().trim();
        nikname =mNicknameEditText.getText().toString().trim();
        pwdCheck =mPwdCheckText.getText().toString().trim();

        Log.i("pwd",pwd);
        Log.i("checkpwd",pwdCheck);
        if (pwd.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_LONG).show();
            return false;
        }else if (name.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "账号不能为空！", Toast.LENGTH_LONG).show();
            return false;
        }else if (!pwdCheck.equals(pwd)) {
            Toast.makeText(RegisterActivity.this, "密码不相同！", Toast.LENGTH_LONG).show();
            return false;
        }else if (nikname.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "名字不能为空！", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }

    @Override
    public void onClick(View v) {
        name = mUsernameEditText.getText().toString().trim();
        pwd = mPassWordEditText.getText().toString().trim();
        nikname =mNicknameEditText.getText().toString().trim();

        switch (v.getId()){
            case R.id.register_button:
                LoginRequest(name,pwd,nikname);
                break;
            case R.id.login_text:
                ToLogin();
                break;
            case R.id.ck_student:
                Ridt="1";
                break;
            case R.id.ck_teacher:
                Ridt="2";
                break;
        }
    }
}
