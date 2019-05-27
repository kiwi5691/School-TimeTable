package com.android.backend.domain;

public class ClassRoomOnDuty {
    private Integer id;

    private String studentName;

    private String courseId;

    private String homeworkGrade;

    private Integer participation;

    private Integer day;

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