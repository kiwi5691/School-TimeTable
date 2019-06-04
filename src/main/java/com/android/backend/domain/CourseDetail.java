package com.android.backend.domain;

public class CourseDetail {
    private Integer id;

    private String studentName;

    private String courseId;

    private String regularGrade; //平时成绩

    private Integer evaluationScore;        //课堂评价

    private String evaluationInfo;      //评价信息

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

    public String getRegularGrade() {
        return regularGrade;
    }

    public void setRegularGrade(String regularGrade) {
        this.regularGrade = regularGrade == null ? null : regularGrade.trim();
    }

    public Integer getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(Integer evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public String getEvaluationInfo() {
        return evaluationInfo;
    }

    public void setEvaluationInfo(String evaluationInfo) {
        this.evaluationInfo = evaluationInfo == null ? null : evaluationInfo.trim();
    }
}