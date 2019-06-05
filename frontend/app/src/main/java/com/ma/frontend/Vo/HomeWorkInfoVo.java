package com.ma.frontend.Vo;

/**
 * @Auther:kiwi
 * @Date: 2019/6/5 17:03
 */
public class HomeWorkInfoVo {
    private String courseName;

    private String homeworkGrade; //作业成绩

    private Integer participation;    // '出勤, 0未到，1有到',

    private Integer day; //第{}节课'

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getHomeworkGrade() {
        return homeworkGrade;
    }

    public void setHomeworkGrade(String homeworkGrade) {
        this.homeworkGrade = homeworkGrade;
    }

    public Integer getParticipation() {
        return participation;
    }

    public void setParticipation(Integer participation) {
        this.participation = participation;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "HomeWorkInfoVo{" +
                "courseName='" + courseName + '\'' +
                ", homeworkGrade='" + homeworkGrade + '\'' +
                ", participation=" + participation +
                ", day=" + day +
                '}';
    }
}
