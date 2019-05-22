package com.ma.frontend.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ma.frontend.R;
import com.ma.frontend.domain.CourseInfo;

import java.util.List;

/**
 * @Auther:kiwi
 * @Date: 2019/5/22 17:57
 */

public class CourseInfoAdapter extends BaseAdapter {
    private Context context;
    private TextView[] courseTextViewList;
    private int screenWidth;
    private int currentWeek;
    public CourseInfoAdapter(Context context, List<CourseInfo> courseList, int width, int currentWeek) {
        super();
        this.screenWidth = width;
        this.context = context;
        this.currentWeek = currentWeek;
        createGalleryWithCourseList(courseList);
    }

    //gallery的设置和内容
    private void createGalleryWithCourseList(List<CourseInfo> courseList){
        //五种颜色的背景
        int[] background = {R.drawable.main_course1, R.drawable.main_course2,
                R.drawable.main_course3, R.drawable.main_course4,
                R.drawable.main_course5};
        this.courseTextViewList = new TextView[courseList.size()];
        for(int i = 0; i < courseList.size(); i ++)
        {
            final CourseInfo course = courseList.get(i);
            TextView textView = new TextView(context);
            textView.setText(course.getCoursename() + "@" + course.getPlace());
            textView.setLayoutParams(new InfoGallery.LayoutParams((screenWidth / 6) *3, (screenWidth / 6) *3));
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(10, 0, 0, 0);
            //选择一个颜色背景
            int colorIndex = ((course.getLessonfrom() - 1) * 8 + course.getDay()) % (background.length - 1);
            textView.setBackgroundResource(background[colorIndex]);
            textView.getBackground().setAlpha(222);

            //注：adapter只负责显示内容，不负责处理触发事件

            this.courseTextViewList[i] = textView;
        }
    }
    @Override
    public int getCount() {

        return courseTextViewList.length;
    }

    @Override
    public Object getItem(int index) {

        return courseTextViewList[index];
    }

    @Override
    public long getItemId(int arg0) {

        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return courseTextViewList[position];
    }

    public float getScale(boolean focused, int offset) {
        return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
    }

    private boolean isCurrWeek(CourseInfo cInfoTmp) {
        // 全周
        if (cInfoTmp.getWeektype() == 1) {
            if ((cInfoTmp.getWeekfrom() <= currentWeek) && (currentWeek <= cInfoTmp.getWeekto())) {
                return true;
            }
            else {
                return false;
            }
        }
        // 单周
        else if (cInfoTmp.getWeektype() == 2) {
            if (currentWeek%2 == 1) {
                return true;
            }
            else {
                return false;
            }
        }
        // 双周
        else {
            if (currentWeek%2 == 0) {
                return true;
            }
            else {
                return false;
            }
        }
    }
}
