package com.ma.frontend.Vo;

/**
 * @Auther:kiwi
 * @Date: 2019/6/7 14:07
 */
public class StudentDataVo {
    private String userId;
    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    @Override
    public String toString() {
        return "StudentDataVo{" +
                "userId='" + userId + '\'' +
                ", Name='" + name + '\'' +
                '}';
    }
}
