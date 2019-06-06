package com.ma.frontend.utils;

/**
 * @Auther:kiwi
 * @Date: 2019/6/6 13:58
 */
public class PartUtil {
    public static String checkIn(String string){
        if(string.equals("1")){
            return "有到";
        }
        else {
            return "未到";
        }
    }
}
