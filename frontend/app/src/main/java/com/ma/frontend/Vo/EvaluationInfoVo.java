package com.ma.frontend.Vo;

/**
 * @Auther:kiwi
 * @Date: 2019/6/5 17:02
 */
public class EvaluationInfoVo {
    private String courseName;
    private Integer evaluationScore;        //课堂评价
    private String evaluationInfo;      //评价信息

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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
        this.evaluationInfo = evaluationInfo;
    }

    @Override
    public String toString() {
        return "EvaluationInfoVo{" +
                "courseName='" + courseName + '\'' +
                ", evaluationScore=" + evaluationScore +
                ", evaluationInfo='" + evaluationInfo + '\'' +
                '}';
    }
}
