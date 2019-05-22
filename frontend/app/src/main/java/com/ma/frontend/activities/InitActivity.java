package com.ma.frontend.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.ma.frontend.R;
import com.ma.frontend.db.dao.GlobalInfoDao;
import com.ma.frontend.db.dao.UserInfoDao;
import com.ma.frontend.domain.GlobalInfo;
import com.ma.frontend.domain.UserInfo;

import java.util.Calendar;

/**
 * @Auther:kiwi
 * @Date: 2019/5/22 16:26
 */
public class InitActivity extends Activity {
    /**
     *  静态成员变量
     */
    private static Context context;
    private final int SPLASH_DISPLAY_LENGHT = 0; //--- 改为延迟的毫秒数2000

    /**
     * Dao成员变量
     */
    private GlobalInfoDao gInfoDao;
    private UserInfoDao uInfoDao; //测试:归置后端处理，所以安卓端不需要dao,

    /**
     * 数据模型变量
     */
    private GlobalInfo gInfo;
    private UserInfo uInfo;

    /**
     * 临时变量
     */
    private long timeStart;
    private int uid;

    /**
     * Activity回调函数
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {

        timeStart = System.currentTimeMillis();

        // 继承父类方法，绑定View
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // 初始化context
        context = getApplicationContext();

        // 初始化Dao成员变量
        gInfoDao = new GlobalInfoDao(context);
        uInfoDao = new UserInfoDao(context);

        // 初始化数据模型变量
        gInfo = gInfoDao.query();
        uInfo = new UserInfo();

        // 自定义函数
        initGInfo(context);

        // 如果初始化消耗的时间小于预定时间
        long timeInit = System.currentTimeMillis()-timeStart;
        if (timeInit < SPLASH_DISPLAY_LENGHT) {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    jumpToMain();
                }
            }, SPLASH_DISPLAY_LENGHT-timeInit);
        }
        else {
            jumpToMain();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 线程对象
     */


    /**
     * 自定义成员对象
     */


    /**
     * 自定义方法
     */

    private void initGInfo(Context context) {
        if (gInfo == null) {//第一次使用app
            int version = 0;
            String vsersionStr = "";
            try {
                PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                version = pi.versionCode;
                vsersionStr = pi.versionName;
            } catch (Exception e) {
                version = 1;
                vsersionStr = "1.0";
            }

            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH)+1;
            int year = calendar.get(Calendar.YEAR);

            gInfo = new GlobalInfo();
            gInfo.setVersion(version);
            gInfo.setVersionStr(vsersionStr);

            // 初始化时默认的开学时间，后面可以加上修改时间模块
            gInfo.setTermBegin("2017-02-26");

            // 下半学期
            if (month < 8) {
                gInfo.setYearFrom(year-1);
                gInfo.setYearTo(year);
                gInfo.setTerm(2);
            }
            // 上半学期
            else {
                gInfo.setYearFrom(year);
                gInfo.setYearTo(year+1);
                gInfo.setTerm(1);
            }
            gInfo.setFirstUse(1);//设为1则为是第一次使用

            //注：此demo省略注册登录模块，无服务器端分配的uid，所以此处简化为一个用户uid=1
            uid = 1;
            gInfo.setActiveUserUid(uid);

            uInfo.setUid(uid);
            uInfo.setUsername("小明");
            uInfo.setGender("女");
            uInfo.setPhone("18706003020");
//           uInfo.setHeadshot(json.getString("headshot"));//默认头像地址，暂时不放
            uInfo.setInstitute("计算机学院");
            uInfo.setMajor("计算机工程");
            uInfo.setYear("2017");

            gInfoDao.insert(gInfo); //插入global_info表
            uInfoDao.insert(uInfo); //插入user_info表
            Log.v("StartActivity",String.valueOf(uid));

        }
    }

    private void jumpToMain() {
        Intent intent = new Intent(InitActivity.this, CourseActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        finish();
    }
}

