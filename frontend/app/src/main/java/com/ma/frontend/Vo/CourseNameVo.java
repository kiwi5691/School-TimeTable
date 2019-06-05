package com.ma.frontend.Vo;

/**
 * @Auther:kiwi
 * @Date: 2019/6/5 17:31
 */
public class CourseNameVo {
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "CourseNameVo{" +
                "Name='" + Name + '\'' +
                '}';
    }
}
