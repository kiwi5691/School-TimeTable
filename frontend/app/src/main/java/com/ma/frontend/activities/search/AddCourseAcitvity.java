package com.ma.frontend.activities.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.*;
import com.ma.frontend.R;
import com.ma.frontend.Vo.CourseDataVo;
import com.ma.frontend.Vo.ResultVo;
import com.ma.frontend.Vo.TeacherAllVo;
import com.ma.frontend.config.HttpConstant;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther:kiwi
 * @Date: 2019/5/23 20:54
 */
public class AddCourseAcitvity extends AppCompatActivity implements View.OnClickListener{
    private static Context context;


    private Button rb_addCourse;



    private EditText mCoursename;
    private EditText mTeachername;
    private EditText mCourselocal;
    private EditText mStarweek;
    private EditText mEndweek;
    private EditText mCoursetime;
    private EditText mLessonto;
    private EditText mLessionfrom;
    private EditText mWeektye;



    /**
     *@Auther kiwi
     */
    String root= HttpConstant.OriginAddress;
    private String originAddress = root + "/user/course/save";


    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15,TimeUnit.SECONDS)
            .writeTimeout(15,TimeUnit.SECONDS)
            .build();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_addcourse);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        context = getApplicationContext();
        initView();
        intInfoRequest();
        initEvent();

    }

    private void initView() {
        mTeachername = (EditText) findViewById(R.id.user_name_input);
        mStarweek = (EditText) findViewById(R.id.user_password_input);
        mEndweek = (EditText) findViewById(R.id.user_password_input_d);
        mCoursetime= (EditText) findViewById(R.id.course_day);
        mCoursename = (EditText) findViewById(R.id.user_phone_input);
        mCourselocal = (EditText)findViewById(R.id.look_up);
        mWeektye = (EditText)findViewById(R.id.week_type);
        mLessionfrom = (EditText)findViewById(R.id.lesson_from);
        mLessonto = (EditText)findViewById(R.id.lesson_to);


        rb_addCourse=(Button)findViewById(R.id.register_button);
    }

    private void initEvent() {
        rb_addCourse.setOnClickListener(this);

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

            if (code==200){
                result = "课程上传成功";



            }else if (code==400){
                result = "课程上传失败";
            }
            Toast.makeText(AddCourseAcitvity.this, result, Toast.LENGTH_SHORT).show();
        }
    };


    private void intInfoRequest()  {



        Context ctx = AddCourseAcitvity.this;
        SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);

        SharedPreferences.Editor editor =sp.edit();

//        originAddress = originAddress + "?UserId=kiwi";
        originAddress = originAddress + "?UserId="+sp.getString("userName","none");
        Log.i("url is------",originAddress);
        //发起请求


        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("courseName",mCoursename.getText().toString().trim())
                .addFormDataPart("teacher",mTeachername.getText().toString().trim())
                .addFormDataPart("weekfrom",mStarweek.getText().toString().trim())
                .addFormDataPart("weekto",mEndweek.getText().toString().trim())
                .addFormDataPart("weektype",mWeektye.getText().toString().trim())
                .addFormDataPart("day",mCoursetime.getText().toString().trim())
                .addFormDataPart("lessonfrom",mLessionfrom.getText().toString().trim())
                .addFormDataPart("lessonto",mLessonto.getText().toString().trim())
                .addFormDataPart("place",mCourselocal.getText().toString().trim())
                .addFormDataPart("UserId",sp.getString("userName","none"))
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



    @Override
    public void onClick(View v) {

    }
}


