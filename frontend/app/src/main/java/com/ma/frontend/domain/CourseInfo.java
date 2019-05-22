package com.ma.frontend.domain;

import java.io.Serializable;

/**
 * @Auther:kiwi
 * @Date: 2019/5/22 10:18
 */
public class CourseInfo extends BaseInfo implements Serializable {


    private static final long serialVersionUID = 3147864707226402208L;
    private int cid;		// 课程标记id
    private String coursename; //课程名
    private String teacher;		// 教师

    public int getCid() {
        return cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }
    public String getCoursename() {
        return coursename;
    }
    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
    public String getTeacher() {
        return teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

}