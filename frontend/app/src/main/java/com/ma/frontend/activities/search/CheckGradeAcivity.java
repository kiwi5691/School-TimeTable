package com.ma.frontend.activities.search;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.*;
import com.ma.frontend.R;
import com.ma.frontend.Vo.RegularGradeVo;
import com.ma.frontend.Vo.ResultVo;
import com.ma.frontend.Vo.TeacherAllVo;
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
 * @Date: 2019/5/23 11:31
 */
public class CheckGradeAcivity extends AppCompatActivity implements View.OnClickListener{


    public List<RegularGradeVo> regularGradeVos = new ArrayList<RegularGradeVo>();
    private static Context context;



    private TextView mAdd;
    /**
     *@Auther kiwi
     */
    String root= HttpConstant.OriginAddress;
    private String originAddress = root + "/user/search/checkgrade";
    private String getALLcourseAddress = root + "/user/course/getAllcourseName";



    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15,TimeUnit.SECONDS)
            .writeTimeout(15,TimeUnit.SECONDS)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_checkgrade);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        context = getApplicationContext();
        initView();
        intEvent();

        Context ctx = CheckGradeAcivity.this;
        SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);

        SharedPreferences.Editor editor =sp.edit();

        if(sp.getString("rid","none").equals("1")){
            mAdd.setVisibility(View.INVISIBLE);
        }else {
            mAdd.setText("点击设置学生成绩");
            ListView listView=(ListView)findViewById(R.id.name);
            listView.setAdapter(null);


        }

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
                RegularGradeVo[] array = new Gson().fromJson(data, RegularGradeVo[].class);
                regularGradeVos= Arrays.asList(array);




                Log.i("info json ==",regularGradeVos.toString());


                List<String> dataa = new ArrayList<String>();
                for(RegularGradeVo regularGradeVo:regularGradeVos){
                    String c_n ="";
                    c_n="           "+regularGradeVo.getRegularGrade()+"                                     "+regularGradeVo.getCourseName();


                    dataa.add(c_n);
//                    Log.i("data is ",dataa.get(0));
//                    Log.i("teacherinfo is ",teacherAllVo.getCourseName());
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(CheckGradeAcivity.this,android.R.layout.simple_list_item_1,dataa);
                ListView listView=(ListView)findViewById(R.id.name);
                listView.setAdapter(adapter);


            }else if (code==400){
                result = "信息获取失败";
            }
            Toast.makeText(CheckGradeAcivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };


    public void initView(){

        mAdd = (TextView)findViewById(R.id.addgrade);
    }
    public void intEvent(){
        mAdd.setOnClickListener(this);
    }
    private void intInfoRequest()  {



        Context ctx = CheckGradeAcivity.this;
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


    public void toAdd(){
        Intent intent = new Intent();
        intent.setClass(CheckGradeAcivity.this, AddGradeAcivity.class);
        startActivity(intent);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addgrade:
                toAdd();
                break;
        }

    }
}
