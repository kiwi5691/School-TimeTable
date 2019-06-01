package com.ma.frontend.activities.person;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.ma.frontend.R;
import com.ma.frontend.activities.PersonActivity;
import com.ma.frontend.domain.city.City;
import com.ma.frontend.domain.city.District;
import com.ma.frontend.domain.city.Province;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:kiwi
 * @Date: 2019/5/22 20:11
 */
public class UpdateAcivity extends AppCompatActivity implements View.OnClickListener{

    private Button rb_update;
    private TextView rb_return;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_update);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        initEvent();
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
    }


    private void initView() {
        mInstitute = (EditText) findViewById(R.id.institute_d);
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
}
