package com.ma.frontend.utils;

import android.annotation.SuppressLint;
import com.ma.frontend.domain.BaseInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Auther:kiwi
 * @Date: 2019/5/22 16:18
 */

public class TimetableUtil{

    static public int time() {
        return (int)(System.currentTimeMillis()/1000);
    }

    /**
     * 计算当前教学周
     * @param termBegin 开学的日期
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    static public int getWeeks(String termBegin) {
        try {
            Date currentTime = new Date();

            SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dFormat.parse(termBegin);

            Calendar calendar = new GregorianCalendar();
            calendar.setFirstDayOfWeek(Calendar.SUNDAY);  		//将星期天作为一个星期的开始

            calendar.setTime(date);
            int weeks2 = calendar.get(Calendar.WEEK_OF_YEAR);	// 开学星期数

            calendar.setTime(currentTime);
            int weeks1 = calendar.get(Calendar.WEEK_OF_YEAR);	// 当前星期数

            if (date.after(currentTime)) {
                return 0;
            }
            else {
                int n = (weeks1-weeks2>0)?(weeks1-weeks2+1):(weeks1-weeks2+53);
                return n;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 计算当前时间是周几
     * @param
     * @return
     */
    static public int getWeek(){
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        if(i==1){
            return 7;
        }
        else{
            return i-1;
        }

    }

    /**
     * 计算当前日期是教学周第几周
     * @param termBegin 开学的日期
     * @param currDate 当前日期
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    static public int getWeeksByDates(String termBegin, String currDate) {
        try {
            SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date bDate = dFormat.parse(termBegin);
            Date cDate = dFormat.parse(currDate);

            Calendar calendar = new GregorianCalendar();
            calendar.setFirstDayOfWeek(Calendar.SUNDAY);  		//将星期天作为一个星期的开始

            calendar.setTime(bDate);
            int weeks2 = calendar.get(Calendar.WEEK_OF_YEAR);	// 开学星期数

            calendar.setTime(cDate);
            int weeks1 = calendar.get(Calendar.WEEK_OF_YEAR);	// 当前日期对应星期数


            if (bDate.after(cDate)) {
                return 0;
            }
            else {
                int n = (weeks1-weeks2>0)?(weeks1-weeks2+1):(weeks1-weeks2+53);
                return n;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 计算当前时间是周几
     * @param
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    static public int getWeekByDate(String currDate){
        try {
            SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date cDate = dFormat.parse(currDate);

            Calendar calendar = new GregorianCalendar();
            calendar.setFirstDayOfWeek(Calendar.SUNDAY);  		//将星期天作为一个星期的开始
            calendar.setTime(cDate);
            int i = calendar.get(Calendar.DAY_OF_WEEK);	// 当前日期是星期几

            if(i==1){
                return 7;
            }
            else{
                return i-1;
            }
        } catch (Exception e) {
            return 0;
        }


    }

    /**
     * 计算cInfoTmp是否在当前周上课
     * @param
     * @return
     */
    static public boolean isCurrWeek(BaseInfo cInfoTmp, int currentWeek) {
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

    /**
     * 根据节次和校区返回课程开始或结束的时间
     * @param campus 校区
     * @param lesson 节次
     * @param isFrom 是否是课开始时间
     * @return 课程时间
     */
    static public String campusTime(String campus, int lesson, Boolean isFrom) {
        String timeString = new String();
        if (campus==null) {
            return "";
        }
        if (campus.equals("江安")||campus.equals("")) {
            if (isFrom) {
                switch (lesson) {
                    case 1:
                        timeString = "08:15";
                        break;
                    case 2:
                        timeString = "09:10";
                        break;
                    case 3:
                        timeString = "10:15";
                        break;
                    case 4:
                        timeString = "11:10";
                        break;
                    case 5:
                        timeString = "13:50";
                        break;
                    case 6:
                        timeString = "14:45";
                        break;
                    case 7:
                        timeString = "15:40";
                        break;
                    case 8:
                        timeString = "16:45";
                        break;
                    default:
                        timeString = "";
                        break;
                }
            }
            else {//课结束时间
                switch (lesson) {
                    case 1:
                        timeString = "09:00";
                        break;
                    case 2:
                        timeString = "09:55";
                        break;
                    case 3:
                        timeString = "11:00";
                        break;
                    case 4:
                        timeString = "11:55";
                        break;
                    case 5:
                        timeString = "14:35";
                        break;
                    case 6:
                        timeString = "15:30";
                        break;
                    case 7:
                        timeString = "16:25";
                        break;
                    case 8:
                        timeString = "17:30";
                        break;
                    default:
                        timeString = "";
                        break;
                }
            }
        }
        else if(campus.equals("望江")||campus.equals("华西")){
            if (isFrom) {
                switch (lesson) {
                    case 1:
                        timeString = "08:00";
                        break;
                    case 2:
                        timeString = "08:55";
                        break;
                    case 3:
                        timeString = "10:00";
                        break;
                    case 4:
                        timeString = "10:55";
                        break;
                    case 5:
                        timeString = "14:00";
                        break;
                    case 6:
                        timeString = "14:55";
                        break;
                    case 7:
                        timeString = "15:50";
                        break;
                    case 8:
                        timeString = "16:55";
                        break;
                    default:
                        timeString = "";
                        break;
                }
            }
            else {
                switch (lesson) {
                    case 1:
                        timeString = "08:45";
                        break;
                    case 2:
                        timeString = "09:40";
                        break;
                    case 3:
                        timeString = "10:45";
                        break;
                    case 4:
                        timeString = "11:40";
                        break;
                    case 5:
                        timeString = "14:45";
                        break;
                    case 6:
                        timeString = "15:40";
                        break;
                    case 7:
                        timeString = "16:35";
                        break;
                    case 8:
                        timeString = "17:40";
                        break;
                    default:
                        timeString = "";
                        break;
                }
            }
        }
        return timeString;
    }


    /**
     * 根据课程时间计算当前是否正在上课
     * @param courseStart 课程开始时间
     * @param courseEnd 结束小节
     * @return 是否正在上课
     */
    static public Boolean isInClass(String courseStart, String courseEnd) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);        //当前小时
        int minute = cal.get(Calendar.MINUTE);        //当前分

        int cStartHour = Integer.parseInt(courseStart.substring(0,2));
        int cStartMin = Integer.parseInt(courseStart.substring(3,5));

        int cEndHour = Integer.parseInt(courseEnd.substring(0,2));
        int cEndMin = Integer.parseInt(courseEnd.substring(3,5));


        if(hour >= cStartHour && hour <= cEndHour) {
            // 当前小时数在开始时间和结束时间小时数之间
            if(hour > cStartHour && hour < cEndHour){
                return true;
            }
            // 当前时间小时数等于开始时间小时数等于结束时间小时数，分钟数在开始和结束之间
            else if(hour == cStartHour && hour == cEndHour && minute >= cStartMin && minute <=cEndMin){
                return true;
            }
            // 当前时间小时数等于开始时间小时数小于结束时间小时数
            else if(hour == cStartHour && hour < cEndHour && minute >=cStartMin){
                return true;
            }
            // 当前时间小时数等于结束时间小时数，分钟数小等于结束时间分钟数
            else if(hour >= cStartHour && hour == cEndHour && minute <=cEndMin){
                return true;
            }
            // 当前时间小时数等于结束时间小时数，分钟数小等于结束时间分钟数
            else if(hour == cEndHour && minute >= cStartMin && minute <=cEndMin){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    /**
     * 根据课程时间计算当前是否要课前提醒
     * @param courseStart 课程开始时间
     * @param timeBefore 在之前多久提醒(5分钟，10分钟，15分钟，20分钟，30分钟)
     * @return 是否要课前提醒
     */
    static public Boolean isBeforeClass(String courseStart, int timeBefore) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);        //当前小时
        int minute = cal.get(Calendar.MINUTE);        //当前分

        int cStartHour = Integer.parseInt(courseStart.substring(0,2));
        int cStartMin = Integer.parseInt(courseStart.substring(3,5));

        int reminderHour;
        int reminderMin ;
        if(cStartMin - timeBefore < 0){
            reminderHour = cStartHour - 1;//不会有在00：00分的所以不用再添加条件了
            reminderMin = cStartMin + 60 - timeBefore;
        }
        else{
            reminderHour = cStartHour;
            reminderMin = cStartMin - timeBefore;
        }

        if(hour == reminderHour && minute == reminderMin) {
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * 根据当前时间计算当前规划是否已经开始
     * @param courseEnd 规划结束时间
     * @return 是否要更改规划背景颜色
     */
/*	static public Boolean isStart(String courseEnd) {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);        //当前小时
		int minute = cal.get(Calendar.MINUTE);        //当前分

		int cStartHour = Integer.parseInt(courseEnd.substring(0,2));
		int cStartMin = Integer.parseInt(courseEnd.substring(3,5));

		if(hour < cStartHour || (hour == cStartHour && minute < cStartMin)) {
			return true;
		}
		else{
			return false;
		}
	}
*/

    /**
     * 针对plan与inv,weekfrom=weekto,weektype=1，计算bInfoTmp是否还未开始
     */
    static public boolean isNotStart(BaseInfo bInfoTmp, int currentWeek) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);        //当前小时
        int minute = cal.get(Calendar.MINUTE);        //当前分

        String start = TimetableUtil.campusTime(bInfoTmp.getPlace().substring(0,2),bInfoTmp.getLessonfrom(),true);
        int startHour = Integer.parseInt(start.substring(0,2));
        int startMin = Integer.parseInt(start.substring(3,5));

        int currentDay = TimetableUtil.getWeek();
        if(currentDay==7){
            currentDay=0;
        }

        if (bInfoTmp.getWeekfrom() > currentWeek || (bInfoTmp.getWeekfrom() == currentWeek && bInfoTmp.getDay() > currentDay)
                || (bInfoTmp.getWeekfrom() == currentWeek && bInfoTmp.getDay() == currentDay && startHour > hour)
                || (bInfoTmp.getWeekfrom() == currentWeek && bInfoTmp.getDay() == currentDay && hour == startHour && startMin > minute)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 针对plan与inv,weekfrom=weekto,weektype=1，计算bInfoTmp是否已经结束
     */
    static public boolean isEnd(BaseInfo bInfoTmp, int currentWeek) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);        //当前小时
        int minute = cal.get(Calendar.MINUTE);        //当前分

        String end = TimetableUtil.campusTime(bInfoTmp.getPlace().substring(0,2),bInfoTmp.getLessonto(),false);
        int endHour = Integer.parseInt(end.substring(0,2));
        int endMin = Integer.parseInt(end.substring(3,5));

        int currentDay = TimetableUtil.getWeek();
        if(currentDay==7){
            currentDay=0;
        }

        if (bInfoTmp.getWeekfrom() < currentWeek || (bInfoTmp.getWeekfrom() == currentWeek && bInfoTmp.getDay() < currentDay)
                || (bInfoTmp.getWeekfrom() == currentWeek && bInfoTmp.getDay() == currentDay && endHour < hour)
                || (bInfoTmp.getWeekfrom() == currentWeek && bInfoTmp.getDay() == currentDay && hour == endHour && endMin < minute)) {
            return true;
        }
        else {
            return false;
        }
    }

    static public String getDayStr(int day) {//由数字星期得出文字星期
        String dayStr;
        switch (day){
            case 1:
                dayStr = "一";
                break;
            case 2:
                dayStr = "二";
                break;
            case 3:
                dayStr = "三";
                break;
            case 4:
                dayStr = "四";
                break;
            case 5:
                dayStr = "五";
                break;
            case 6:
                dayStr = "六";
                break;
            case 7:
                dayStr = "日";
                break;
            default:
                dayStr = "";
                break;
        }
        return dayStr;
    }
}

