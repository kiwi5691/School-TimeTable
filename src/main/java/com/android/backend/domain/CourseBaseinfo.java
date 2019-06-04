package com.android.backend.domain;

public class CourseBaseinfo {
    private Integer id;

    private Integer cid;

    private Integer weekfrom;

    private Integer weekto;

    private Integer weektype;

    private String day;

    private Integer lessonfrom;

    private Integer lessonto;

    private String place;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getWeekfrom() {
        return weekfrom;
    }

    public void setWeekfrom(Integer weekfrom) {
        this.weekfrom = weekfrom;
    }

    public Integer getWeekto() {
        return weekto;
    }

    public void setWeekto(Integer weekto) {
        this.weekto = weekto;
    }

    public Integer getWeektype() {
        return weektype;
    }

    public void setWeektype(Integer weektype) {
        this.weektype = weektype;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    public Integer getLessonfrom() {
        return lessonfrom;
    }

    public void setLessonfrom(Integer lessonfrom) {
        this.lessonfrom = lessonfrom;
    }

    public Integer getLessonto() {
        return lessonto;
    }

    public void setLessonto(Integer lessonto) {
        this.lessonto = lessonto;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }
}