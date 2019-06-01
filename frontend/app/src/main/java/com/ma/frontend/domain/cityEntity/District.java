package com.ma.frontend.domain.cityEntity;

/**
 * @Auther:kiwi
 * @Date: 2019/6/1 14:06
 */

//区域的bean
public class District {//地区类
    private String id;
    private String name;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }


}