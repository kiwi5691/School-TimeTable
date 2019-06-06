package com.ma.frontend.activities.person;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.google.gson.*;
import com.ma.frontend.R;
import com.ma.frontend.Vo.ResultVo;
import com.ma.frontend.Vo.StudentInfoVo;
import com.ma.frontend.activities.InitActivity;
import com.ma.frontend.activities.LoginActivity;
import com.ma.frontend.config.GolabConstant;
import com.ma.frontend.config.HttpConstant;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    private TextView mBirthday;
    private TextView mLastLogin;

    public StudentInfoVo studentInfoVo;
    /**
     *@Auther kiwi
     *@Data 2019/6/2
     *  url源
     */
    String root= HttpConstant.OriginAddress;
    private String originAddress = root + "/user/showStudentInfo";
    private String originAddressTeacher = root +"/user/showTeacherInfo";


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
        setContentView(R.layout.person_lookup);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initView();
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
            Log.i("data is ",data);
            if (code==200){
                result = "获取信息成功";


                studentInfoVo = new Gson().fromJson(data,StudentInfoVo.class);
                Log.i("info json ==",studentInfoVo.toString());



                mUserId.setText(studentInfoVo.getUserId());
                String genderText=" ";
                //  mInstitute.setText(studentInfoVo.getInstitute());
                mPhone.setText(studentInfoVo.getPhone());
                mNickname.setText(studentInfoVo.getNickName());
                if(studentInfoVo.getGender()==1){
                    genderText="男";
                }
                else{
                    genderText="女";
                }
                sex.setText(genderText);
                mYearin.setText(studentInfoVo.getYear());
                mBirthday.setText(studentInfoVo.getBirthday().toString());
                mLocal.setText(studentInfoVo.getProvince()+studentInfoVo.getCity()+studentInfoVo.getArea());
                mLastLogin.setText(studentInfoVo.getLastLoginTime().toString());
              //  Intent intent=new Intent();
             //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
             //   intent.setClass(LookupAcivity.this, InitActivity.class);
             //   startActivity(intent);
            }else if (code==400){
                result = "信息获取失败";
            }
            Toast.makeText(LookupAcivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };



    private void initView() {
    //    mInstitute = (TextView) findViewById(R.id.institute_d);
        mPhone = (TextView) findViewById(R.id.user_phone_d);
       // mMajor = (TextView) findViewById(R.id.major);
        mYearin= (TextView) findViewById(R.id.year_in);
        mNickname = (TextView) findViewById(R.id.user_name_input);
        sex = (TextView)findViewById(R.id.user_gender);
        mLocal =(TextView)findViewById(R.id.user_local);
        mBirthday =(TextView)findViewById(R.id.user_birthday);
        mLastLogin =(TextView)findViewById(R.id.user_lastlogintime);
        mUserId =(TextView)findViewById(R.id.user_id);

    }




    /**
     *@Auther kiwi
     *@Data 2019/5/19
     */
    private void intInfoRequest()  {



        Context ctx = LookupAcivity.this;
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
}
