package com.ma.frontend.Vo;

import java.io.Serializable;

public class StudentVo implements Serializable {
    private String Username;
    private String Password;
    public static String USERNAME ="Username";
    public static String PASSWORD ="Password";

    public void setPassword(String password) {
        Password = password;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public String getUsername() {
        return Username;
    }
}
