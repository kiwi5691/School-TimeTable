package com.ma.frontend.Vo;

/**
 * @Auther:kiwi
 * @Date: 2019/6/5 17:04
 */
public class TeacherAllVo {
    private String teacherName;
    private String courseName;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "TeacherAllVo{" +
                "teacherName='" + teacherName + '\'' +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}
