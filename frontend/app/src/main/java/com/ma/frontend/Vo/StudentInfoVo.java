package com.ma.frontend.Vo;

import java.util.Date;

/**
 * @Auther:kiwi
 * @Date: 2019/5/22 15:36
 */

public class StudentInfoVo {
    private Integer id;

    private String nickName;

    private String userId;

    private String phone;

    private String major;

    private String year;

    private String institute;

    private String province;

    private String city;

    private String area;

    private Byte gender;

    private String birthday;

    private String lastLoginTime;

    private String addTime;

    private String updateTime;

    private String headshot;

    public StudentInfoVo(Integer id, String nickName, String userId, String phone, String major, String year, String institute, String province, String city, String area, Byte gender, String birthday, String lastLoginTime, String addTime, String updateTime, String headshot) {
        this.id = id;
        this.nickName = nickName;
        this.userId = userId;
        this.phone = phone;
        this.major = major;
        this.year = year;
        this.institute = institute;
        this.province = province;
        this.city = city;
        this.area = area;
        this.gender = gender;
        this.birthday = birthday;
        this.lastLoginTime = lastLoginTime;
        this.addTime = addTime;
        this.updateTime = updateTime;
        this.headshot = headshot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute == null ? null : institute.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot == null ? null : headshot.trim();
    }

    @Override
    public String toString() {
        return "StudentInfoVo{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", userId='" + userId + '\'' +
                ", phone='" + phone + '\'' +
                ", major='" + major + '\'' +
                ", year='" + year + '\'' +
                ", institute='" + institute + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", lastLoginTime=" + lastLoginTime +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", headshot='" + headshot + '\'' +
                '}';
    }
}