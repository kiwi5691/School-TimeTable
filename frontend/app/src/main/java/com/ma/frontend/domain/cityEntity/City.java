package com.ma.frontend.domain.cityEntity;

/**
 * @Auther:kiwi
 * @Date: 2019/6/1 14:05
 */

import java.util.List;

//城市的bean
public class City {//城市类
    private String id;
    private String name;
    private List<District> districts;
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


    public List<District> getDistricts() {
        return districts;
    }
    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
    @Override
    public String toString() {
        return name;
    }


}