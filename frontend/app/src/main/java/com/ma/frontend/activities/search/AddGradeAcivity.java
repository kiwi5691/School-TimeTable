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
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.ma.frontend.R;
import com.ma.frontend.Vo.ResultVo;
import com.ma.frontend.Vo.StudentDataVo;
import com.ma.frontend.Vo.TeacherAllVo;
import com.ma.frontend.activities.InitActivity;
import com.ma.frontend.activities.LoginActivity;
import com.ma.frontend.activities.PersonActivity;
import com.ma.frontend.activities.SearchActivity;
import com.ma.frontend.config.GolabConstant;
import com.ma.frontend.config.HttpConstant;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Auther:kiwi
 * @Date: 2019/6/6 14:28
 */
public class AddGradeAcivity  extends AppCompatActivity implements View.OnClickListener {


    public List<StudentDataVo> studentDataVos = new ArrayList<StudentDataVo>();
    private static Context context;
    private EditText mInfo;
    private TextView mGrade;
    private Button bAdd;
    private TextView mCourseName;

    public Map<String, String> studentMap = new HashMap<String, String>();
    private String str;
    private Spinner sCourses;
    private ArrayAdapter<String> arr_adapter;

    String root= HttpConstant.OriginAddress;
    private String originAddress = root + "/user/search/updateGrade?";
    private String getStudentAddress = root + "/user/search/getStudent";
    private String getCourseName = root + "/user/search/getTeacherCourseName";


    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15,TimeUnit.SECONDS)
            .writeTimeout(15,TimeUnit.SECONDS)
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_teacher_addgrade);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        context = getApplicationContext();
        initView();
        intEvent();
        intCourseName();
        getAllStudents();
        //        intInfoRequest();
        str = (String) sCourses.getSelectedItem();
        sCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                str="";
                //拿到被选择项的值
                str = (String) sCourses.getSelectedItem();
                //把该值传给 TextView
                //  tv.setText(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    //用于获取所有信息的Handler
    private Handler cHandler = new Handler(){
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

                Log.i("star get gson","fuck");

                StudentDataVo[] array = new Gson().fromJson(data, StudentDataVo[].class);
                studentDataVos= Arrays.asList(array);
                Log.i("student vo is",studentDataVos.toString());



                List<String> dataa = new ArrayList<String>();
                for(StudentDataVo studentDataVo:studentDataVos){
                    String c_n ="";
                    String id ="";
                    c_n=studentDataVo.getName();
                    id=studentDataVo.getUserId();

                    studentMap.put(c_n,id);

                    dataa.add(c_n);
                }

                Log.i("dataa is",dataa.get(0));
                arr_adapter= new ArrayAdapter<String>(AddGradeAcivity.this, android.R.layout.simple_spinner_item,dataa);
                //设置样式
                arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //加载适配器
                sCourses.setAdapter(arr_adapter);

            }else if (code==400){
                result = "信息获取失败";
            }
            Toast.makeText(AddGradeAcivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };


    public void getAllStudents(){


        Context ctx = AddGradeAcivity.this;
        SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);

        SharedPreferences.Editor editor =sp.edit();

        String tempAddress="";
//        originAddress = originAddress + "?UserId=kiwi";
        tempAddress = getStudentAddress + "?UserId="+sp.getString("userName","none");
        Log.i("url is------",tempAddress);
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



    //用于获取所有信息的Handler
    private Handler fHandler = new Handler(){
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

                mGrade.setText(data);

            }else if (code==400){
                result = "信息获取失败";
            }
            Toast.makeText(AddGradeAcivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };


    public void intCourseName(){


        Context ctx = AddGradeAcivity.this;
        SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);

        SharedPreferences.Editor editor =sp.edit();

        String tempAddress="";
//        originAddress = originAddress + "?UserId=kiwi";
        tempAddress = getCourseName + "?UserId="+sp.getString("userName","none");
        Log.i("url is------",tempAddress);
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
                        fHandler.obtainMessage(1, response.body().string()).sendToTarget();
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
    public void initView(){
        mInfo =(EditText)findViewById(R.id.institute_d); //grade
        mGrade =(TextView)findViewById(R.id.major); // courseName
        bAdd=(Button) findViewById(R.id.update_button);

        sCourses =(Spinner) findViewById(R.id.spinner);

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
                intent.setClass(AddGradeAcivity.this, SearchActivity.class);
                startActivity(intent);
            }else if (code==400){
                result = "添加失败";
            }
            Toast.makeText(AddGradeAcivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };





    public void update(){


        Context ctx = AddGradeAcivity.this;
        SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);

        SharedPreferences.Editor editor =sp.edit();

        String tempAddress="";
        tempAddress=originAddress;
        //建立请求表单，添加上传服务器的参数
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("grade",mInfo.getText().toString().trim())
                .addFormDataPart("UserId",studentMap.get(str).trim())
                .addFormDataPart("courseName",mGrade.getText().toString().trim())
                .build();
        //发起请求
        final Request request = new Request.Builder()
                .url(tempAddress)
                .post(formBody)
                .build();
        Log.i("url",tempAddress);
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
