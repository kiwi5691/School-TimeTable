package com.ma.frontend.Vo;

/**
 * @Auther:kiwi
 * @Date: 2019/6/5 17:03
 */
public class RegularGradeVo {
    private String courseName;
    private String regularGrade;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRegularGrade() {
        return regularGrade;
    }

    public void setRegularGrade(String regularGrade) {
        this.regularGrade = regularGrade;
    }

    @Override
    public String toString() {
        return "RegularGradeVo{" +
                "courseName='" + courseName + '\'' +
                ", regularGrade='" + regularGrade + '\'' +
                '}';
    }
}
