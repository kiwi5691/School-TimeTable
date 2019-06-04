package com.android.backend.domain;


//学生 课堂成绩，出勤
public class ClassRoomOnDuty {
    private Integer id;

    private String studentName;

    private String courseId;

    private String homeworkGrade; //作业成绩

    private Integer participation;    // '出勤, 0未到，1有到',

    private Integer day; //第{}节课'

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName == null ? null : studentName.trim();
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId == null ? null : courseId.trim();
    }

    public String getHomeworkGrade() {
        return homeworkGrade;
    }

    public void setHomeworkGrade(String homeworkGrade) {
        this.homeworkGrade = homeworkGrade == null ? null : homeworkGrade.trim();
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
}