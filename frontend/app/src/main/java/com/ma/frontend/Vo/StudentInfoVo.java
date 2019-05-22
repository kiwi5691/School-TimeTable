package com.ma.frontend.Vo;

/**
 * @Auther:kiwi
 * @Date: 2019/5/22 15:36
 */
public class StudentInfoVo {
    private int uid;
    private String username;
    private String gender;
    private String phone;
    private String headshot;     //头像
    private String institute;    //学院
    private String major;        //专业
    private String year;         //入学年份

    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getHeadshot() {
        return headshot;
    }
    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }
    public String getInstitute() {
        return institute;
    }
    public void setInstitute(String institute) {
        this.institute = institute;
    }
    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
}
