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
import android.widget.Toast;
import com.google.gson.*;
import com.ma.frontend.R;
import com.ma.frontend.Vo.CourseDataVo;
import com.ma.frontend.Vo.ResultVo;
import com.ma.frontend.Vo.StudentInfoVo;
import com.ma.frontend.Vo.TeacherAllVo;
import com.ma.frontend.activities.PersonActivity;
import com.ma.frontend.config.HttpConstant;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther:kiwi
 * @Date: 2019/5/23 11:27
 */
public class CheckTeacherAcivity extends AppCompatActivity implements View.OnClickListener{

    private static Context context;

    public List<TeacherAllVo> teacherAllVos = new ArrayList<TeacherAllVo>();

    /**
     *@Auther kiwi
     */
    String root= HttpConstant.OriginAddress;
    private String originAddress = root + "/user/course/getAllteacher";
    private String getALLcourseAddress = root + "/user/course/getAllcourseName";


    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15,TimeUnit.SECONDS)
            .writeTimeout(15,TimeUnit.SECONDS)
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_checkteacher);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        context = getApplicationContext();
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

            if (code==200){
                result = "获取信息成功";

                TeacherAllVo[] array = new Gson().fromJson(data, TeacherAllVo[].class);
                teacherAllVos= Arrays.asList(array);


                Log.i("info json ==",teacherAllVos.toString());


            }else if (code==400){
                result = "信息获取失败";
            }
            Toast.makeText(CheckTeacherAcivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };


    public void initView(){

    }
    private void intInfoRequest()  {



        Context ctx = CheckTeacherAcivity.this;
        SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);

        SharedPreferences.Editor editor =sp.edit();

//        originAddress = originAddress + "?UserId=kiwi";
        originAddress = originAddress + "?UserId="+sp.getString("userName","none");
        Log.i("url is------",originAddress);
        //发起请求
        final Request request = new Request.Builder()
                .url(originAddress)
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
