package com.ma.frontend.activities.search;

import android.annotation.SuppressLint;
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
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.ma.frontend.R;
import com.ma.frontend.Vo.*;
import com.ma.frontend.activities.PersonActivity;
import com.ma.frontend.activities.person.LookupAcivity;
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
 * @Date: 2019/5/23 11:28
 */
public class CourseEvatueAcivity extends AppCompatActivity implements View.OnClickListener {
    private static Context context;
    public List<CourseNameVo> allCourseName = new ArrayList<CourseNameVo>();

    public List<EvaluationInfoVo> evaluationInfoVos = new ArrayList<EvaluationInfoVo>();

    public List<String> data_list;

    private TextView upd;
    // private TextView tv;
    private String str;
    /**
     * @Auther kiwi
     */
    String root = HttpConstant.OriginAddress;
    private String originAddress = root + "/user/search/checkClassEvate";
    private String getALLcourseAddress = root + "/user/course/getAllcourseName";

    private Spinner sCourses;
    private ArrayAdapter<String> arr_adapter;


    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_coursevaute);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        context = getApplicationContext();
        initView();
        intEvent();
        getAllCourse();
        upd.setVisibility(View.INVISIBLE);
//        tv = (TextView) findViewById(R.id.txt01);
        str = (String) sCourses.getSelectedItem();
        sCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                str = "";
                //拿到被选择项的值
                str = (String) sCourses.getSelectedItem();
                //把该值传给 TextView
                //  tv.setText(str);
                intInfoRequest(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    //用于处理消息的Handler
    private Handler mHandler = new Handler() {
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

            if (code == 200) {
                result = "获取信息成功";


                String checkEvaluation = "";

                Log.i("info json ==", evaluationInfoVos.toString());


                List<String> dataa = new ArrayList<String>();
                EvaluationInfoVo evaluationInfoVo = new EvaluationInfoVo();
                evaluationInfoVo = new Gson().fromJson(data, EvaluationInfoVo.class);

                String c_n = "";
                c_n = "  评分:" + evaluationInfoVo.getEvaluationScore() + "                                     评价:" + evaluationInfoVo.getEvaluationInfo();

                checkEvaluation = evaluationInfoVo.getEvaluationScore() + evaluationInfoVo.getEvaluationInfo();
                dataa.add(c_n);
                //                    Log.i("data is ",dataa.get(0));
//                    Log.i("teacherinfo is ",teacherAllVo.getCourseName());


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CourseEvatueAcivity.this, android.R.layout.simple_list_item_1, dataa);
                ListView listView = (ListView) findViewById(R.id.listc);
                listView.setAdapter(null);
                if (c_n.equals("  评分:null"  + "                                     评价:null" )) {
                    String tempData = "     请添加课堂评价,点击添加  ";
                    upd.setVisibility(View.VISIBLE);
                    upd.setText(tempData);
                } else {
                    listView.setAdapter(adapter);
                }


            } else if (code == 400) {
                result = "信息获取失败";
            }
            Toast.makeText(CourseEvatueAcivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };

    //用于获取课程信息的Handler
    private Handler cHandler = new Handler() {
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

            if (code == 200) {
                result = "获取信息成功";

                List<String> ccou = new ArrayList<String>();

                ccou = new Gson().fromJson(data, new TypeToken<List<Object>>() {
                }.getType());
                Log.i("String is ", ccou.get(0));


                arr_adapter = new ArrayAdapter<String>(CourseEvatueAcivity.this, android.R.layout.simple_spinner_item, ccou);
                //设置样式
                arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //加载适配器
                sCourses.setAdapter(arr_adapter);
                Log.i("info json ==", ccou.toString());


            } else if (code == 400) {
                result = "信息获取失败";
            }
            Toast.makeText(CourseEvatueAcivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };

    private void getAllCourse() {

        Context ctx = CourseEvatueAcivity.this;
        SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

//        originAddress = originAddress + "?UserId=kiwi";
        getALLcourseAddress = getALLcourseAddress + "?UserId=" + sp.getString("userName", "none");
        Log.i("url is------", getALLcourseAddress);
        //发起请求
        final Request request = new Request.Builder()
                .url(getALLcourseAddress)
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
                        cHandler.obtainMessage(1, response.body().string()).sendToTarget();
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

    public void initView() {

        sCourses = (Spinner) findViewById(R.id.spinner);
        upd = (TextView) findViewById(R.id.update_null);
    }

    public void intEvent() {
        upd.setOnClickListener(this);
    }

    private void intInfoRequest(String courseName) {


        Context ctx = CourseEvatueAcivity.this;
        SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        String tempAddress = "";
//        originAddress = originAddress + "?UserId=kiwi";
        tempAddress = originAddress + "?UserId=" + sp.getString("userName", "none") + "&CourseName=" + courseName;
        Log.i("url is------", tempAddress);
        //发起请求
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


    public void toAdd() {
        Intent intent = new Intent();
        intent.setClass(CourseEvatueAcivity.this, AddEvatueAcivity.class);
        intent.putExtra("courseName",str);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_null:
                toAdd();
                break;
        }
    }
}
