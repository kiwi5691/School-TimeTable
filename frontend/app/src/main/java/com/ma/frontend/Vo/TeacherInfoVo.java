package com.ma.frontend.Vo;

import java.util.Date;

/**
 * @Auther:kiwi
 * @Date: 2019/5/22 15:36
 */

public class TeacherInfoVo {
    private Integer id;

    private String userId;

    private String nickName;

    private String province;

    private String city;

    private String area;

    private Byte gender;

    private String birthday;

    private String lastLoginTime;

    private String addTime;

    private String updateTime;

    private String headshot;

    public TeacherInfoVo(Integer id, String userId, String nickName, String province, String city, String area, Byte gender, String birthday, String lastLoginTime, String addTime, String updateTime, String headshot) {
        this.id = id;
        this.userId = userId;
        this.nickName = nickName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
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
        return "TeacherInfoVo{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
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