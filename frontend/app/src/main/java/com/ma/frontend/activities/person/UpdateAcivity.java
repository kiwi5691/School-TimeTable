package com.ma.frontend.activities.person;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.gson.*;
import com.ma.frontend.R;
import com.ma.frontend.Vo.ResultVo;
import com.ma.frontend.Vo.StudentInfoVo;
import com.ma.frontend.activities.InitActivity;
import com.ma.frontend.activities.LoginActivity;
import com.ma.frontend.activities.PersonActivity;
import com.ma.frontend.config.GolabConstant;
import com.ma.frontend.config.HttpConstant;
import com.ma.frontend.domain.city.City;
import com.ma.frontend.domain.city.District;
import com.ma.frontend.domain.city.Province;
import okhttp3.*;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther:kiwi
 * @Date: 2019/5/22 20:11
 */
public class UpdateAcivity extends AppCompatActivity implements View.OnClickListener{

    private Button rb_update;
    private TextView rb_return;

    private TextView mBirthday;
    private EditText mNickname;
    private EditText mYearin;
    private EditText mMajor;
    private EditText mInstitute;
    private EditText sex;
    private EditText mPhone;

    public String sexText=" ";


    //////  地区的spinner------------因为真的不会写ui，但是又要用到xml，我真的尽力做ui了
    private Spinner spinner1,spinner2,spinner3;
    private Province province = null;
    private List<Province> list = new ArrayList<Province>();
    ArrayAdapter<Province> arrayAdapter1;
    ArrayAdapter<City> arrayAdapter2;
    ArrayAdapter<District>arrayAdapter3;
    //////



    public StudentInfoVo studentInfoVo;
    /**
     *@Auther kiwi
     *@Data 2019/6/2
     *  url源
     */
    String root= HttpConstant.OriginAddress;
    private String originAddressShowStu = root + "/user/showStudentInfo";
    private String originAddressShowTea = root + "/user/showTeacherInfo";
    private String originAddressUpdateStu = root + "/user/UpdateStudentInfo";
    private String originAddressUpdateTea = root + "/user/UpdateTeacherInfo";

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
        setContentView(R.layout.person_update);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        /////////////////地区
        spinner3 = (Spinner)findViewById(R.id.s3);
        spinner2 = (Spinner)findViewById(R.id.s2);
        spinner1 = (Spinner)findViewById(R.id.s1);
        list= parser();
        arrayAdapter1 = new ArrayAdapter<Province>(UpdateAcivity.this,R.layout.support_simple_spinner_dropdown_item,list);
        arrayAdapter2 = new ArrayAdapter<City>(UpdateAcivity.this,R.layout.support_simple_spinner_dropdown_item,list.get(0).getCitys());
        arrayAdapter3 = new ArrayAdapter<District>(UpdateAcivity.this,R.layout.support_simple_spinner_dropdown_item,list.get(0).getCitys().get(0).getDistricts());
        spinner1.setAdapter(arrayAdapter1);
        spinner1.setSelection(0, true);
        spinner2.setAdapter(arrayAdapter2);
        spinner2.setSelection(0, true);
        spinner3.setAdapter(arrayAdapter3);
        spinner3.setSelection(0, true);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                province = list.get(position);
                arrayAdapter2 = new ArrayAdapter<City>(UpdateAcivity.this, R.layout.support_simple_spinner_dropdown_item, list.get(position).getCitys());
                spinner2.setAdapter(arrayAdapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                arrayAdapter3 = new ArrayAdapter<District>(UpdateAcivity.this,R.layout.support_simple_spinner_dropdown_item,province.getCitys().get(position).getDistricts());
                spinner3.setAdapter(arrayAdapter3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        initView();
        initEvent();
        intInfoRequest();
    }


    //用于获取信息的Handler
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

                mInstitute.setText(studentInfoVo.getInstitute());
                mMajor.setText(studentInfoVo.getMajor());
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
                //  Intent intent=new Intent();
                //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                //   intent.setClass(LookupAcivity.this, InitActivity.class);
                //   startActivity(intent);
            }else if (code==400){
                result = "信息获取失败";
            }
            Toast.makeText(UpdateAcivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };




    //用于update消息的Handler
    private Handler uHandler = new Handler(){
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
                result = "保存成功";

            }else if (code==400){
                result = "保存失败";
            }
            Toast.makeText(UpdateAcivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };



    private void initView() {
        mInstitute = (EditText) findViewById(R.id.institute_d);
        mBirthday =(TextView)findViewById(R.id.user_birthday);
        mPhone = (EditText) findViewById(R.id.user_phone);
        mMajor = (EditText) findViewById(R.id.major);
        mYearin= (EditText) findViewById(R.id.year_in_d);
        mNickname = (EditText) findViewById(R.id.user_name);
        rb_update =(Button)findViewById(R.id.update_button);
        sex = (EditText)findViewById(R.id.user_gender_d);
        rb_return =(TextView)findViewById(R.id.rb_return);
    }

    private void initEvent() {
       rb_update.setOnClickListener(this);
       rb_return.setOnClickListener(this);
    }

    public void passDate(View v){
        CharSequence charSequence1 =(CharSequence)spinner1.getSelectedItem().toString();
        CharSequence charSequence2 =(CharSequence)spinner2.getSelectedItem().toString();
        CharSequence charSequence3 =(CharSequence)spinner3.getSelectedItem().toString();

    }

    public List<Province> parser(){
        List<Province>list =null;
        Province province = null;

        List<City>cities = null;
        City city = null;

        List<District>districts = null;
        District district = null;
        //   WebView mWebView;
        //       mWebView.loadUrl("file:///android_asset/nations.xml");
        // InputStream is = getAssets().open("xml/nations.xml");

        // 创建解析器，并制定解析的xml文件
        XmlResourceParser parser = getResources().getXml(R.xml.nations);
        try{
            int type = parser.getEventType();
            while(type!=1) {
                String tag = parser.getName();//获得标签名
                switch (type) {
                    case XmlResourceParser.START_DOCUMENT:
                        list = new ArrayList<Province>();
                        break;
                    case XmlResourceParser.START_TAG:
                        if ("p".equals(tag)) {
                            province=new Province();
                            cities = new ArrayList<City>();
                            int n =parser.getAttributeCount();
                            for(int i=0 ;i<n;i++){
                                //获得属性的名和值
                                String name = parser.getAttributeName(i);
                                String value = parser.getAttributeValue(i);
                                if("p_id".equals(name)){
                                    province.setId(value);
                                }
                            }
                        }
                        if ("pn".equals(tag)){//省名字
                            province.setName(parser.nextText());
                        }
                        if ("c".equals(tag)){//城市
                            city = new City();
                            districts = new ArrayList<District>();
                            int n =parser.getAttributeCount();
                            for(int i=0 ;i<n;i++){
                                String name = parser.getAttributeName(i);
                                String value = parser.getAttributeValue(i);
                                if("c_id".equals(name)){
                                    city.setId(value);
                                }
                            }
                        }
                        if ("cn".equals(tag)){
                            city.setName(parser.nextText());
                        }
                        if ("d".equals(tag)){
                            district = new District();
                            int n =parser.getAttributeCount();
                            for(int i=0 ;i<n;i++){
                                String name = parser.getAttributeName(i);
                                String value = parser.getAttributeValue(i);
                                if("d_id".equals(name)){
                                    district.setId(value);
                                }
                            }
                            district.setName(parser.nextText());
                            districts.add(district);
                        }
                        break;
                    case XmlResourceParser.END_TAG:
                        if ("c".equals(tag)){
                            city.setDistricts(districts);
                            cities.add(city);
                        }
                        if("p".equals(tag)){
                            province.setCitys(cities);
                            list.add(province);
                        }
                        break;
                    default:
                        break;
                }
                type = parser.next();
            }
        }catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } */
        catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    private boolean isInputValid() {
        sexText = sex.getText().toString().trim();

        if (!sexText.equals("男")||!sexText.equals("女")) {
            Toast.makeText(UpdateAcivity.this, "性别只能填写男or女", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }


    private void update(){


        int sext= 1;
        if(sex.getText().toString().trim()=="男"){
            sext=1;
        }
        else {
            sext=2;
        }


        CharSequence charSequence1 =(CharSequence)spinner1.getSelectedItem().toString();
        CharSequence charSequence2 =(CharSequence)spinner2.getSelectedItem().toString();
        CharSequence charSequence3 =(CharSequence)spinner3.getSelectedItem().toString();

        Context ctx = UpdateAcivity.this;
        SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);

        SharedPreferences.Editor editor =sp.edit();

        String tempAddress="";
        RequestBody formBody ;
//        originAddress = originAddress + "?UserId=kiwi";
        if(sp.getString("rid","none").equals("1")) {
            tempAddress = originAddressUpdateStu + "?UserId="+sp.getString("userName","none");
            Log.i("update url is------",tempAddress);
            //发起请求
             formBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("nickName",mNickname.getText().toString().trim())
                    .addFormDataPart("phone",mPhone.getText().toString().trim())
                    .addFormDataPart("major",mMajor.getText().toString().trim())
                    .addFormDataPart("year",mYearin.getText().toString().trim())
                    .addFormDataPart("institute",mInstitute.getText().toString().trim())
                    .addFormDataPart("province",charSequence1.toString())
                    .addFormDataPart("city",charSequence2.toString())
                    .addFormDataPart("area",charSequence3.toString())
                    .addFormDataPart("gender",String.valueOf(sext))
                    .addFormDataPart("birthday",mBirthday.getText().toString().trim())
                    .build();
          }
        else {
            tempAddress = originAddressUpdateTea + "?UserId="+sp.getString("userName","none");
            Log.i("update url is------",tempAddress);
            //发起请求
             formBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("nickName",mNickname.getText().toString().trim())
                    .addFormDataPart("phone",mPhone.getText().toString().trim())
                    .addFormDataPart("province",charSequence1.toString())
                    .addFormDataPart("city",charSequence2.toString())
                    .addFormDataPart("area",charSequence3.toString())
                    .addFormDataPart("gender",String.valueOf(sext))
                    .addFormDataPart("birthday",mBirthday.getText().toString().trim())
                    .build();
          }
        //发起请求
        final Request request = new Request.Builder()
                .url(tempAddress)
                .post(formBody)
                .build();


//        originAddress = originAddress + "?UserId=kiwi";
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
                        uHandler.obtainMessage(1, response.body().string()).sendToTarget();
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

    private void returnback(){
        Intent intent =new Intent(UpdateAcivity.this, PersonActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.update_button:
                update();
                break;
            case R.id.rb_return:
                returnback();
                break;
        }
    }



    /**
     *@Auther kiwi
     *@Data 2019/5/19
     */
    private void intInfoRequest()  {



        Context ctx = UpdateAcivity.this;
        SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);

        SharedPreferences.Editor editor =sp.edit();

        String tempAddress="";
//        originAddress = originAddress + "?UserId=kiwi";
        if(sp.getString("rid","none").equals("1")) {
            tempAddress = originAddressShowStu + "?UserId=" + sp.getString("userName", "none");
            Log.i("url is------", tempAddress);
        }
        else {
            tempAddress = originAddressShowTea + "?UserId=" + sp.getString("userName", "none");
            Log.i("url is------", tempAddress);
        }
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
}

