package com.ma.frontend.Vo;

/**
 * @Auther:kiwi
 * @Date: 2019/6/5 15:20
 */
public class CourseDataVo {

    private int cid;		// 课程标记id
    private String courseName; //课程名
    private String teacher;		// 教师
    private int weekfrom;	// 起始周
    private int weekto;		// 结束周
    private int weektype;	// 周类型：1普通；2单周；3双周
    private String  day;			// 星期几上课
    private int lessonfrom;	// 开始节次
    private int lessonto;	// 结束节次
    private String place;	//地点

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getWeekfrom() {
        return weekfrom;
    }

    public void setWeekfrom(int weekfrom) {
        this.weekfrom = weekfrom;
    }

    public int getWeekto() {
        return weekto;
    }

    public void setWeekto(int weekto) {
        this.weekto = weekto;
    }

    public int getWeektype() {
        return weektype;
    }

    public void setWeektype(int weektype) {
        this.weektype = weektype;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getLessonfrom() {
        return lessonfrom;
    }

    public void setLessonfrom(int lessonfrom) {
        this.lessonfrom = lessonfrom;
    }

    public int getLessonto() {
        return lessonto;
    }

    public void setLessonto(int lessonto) {
        this.lessonto = lessonto;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "CourseDataVo{" +
                "cid=" + cid +
                ", courseName='" + courseName + '\'' +
                ", teacher='" + teacher + '\'' +
                ", weekfrom=" + weekfrom +
                ", weekto=" + weekto +
                ", weektype=" + weektype +
                ", day='" + day + '\'' +
                ", lessonfrom=" + lessonfrom +
                ", lessonto=" + lessonto +
                ", place='" + place + '\'' +
                '}';
    }
}
