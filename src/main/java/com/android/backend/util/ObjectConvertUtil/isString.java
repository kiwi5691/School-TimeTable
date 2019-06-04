package com.android.backend.util.ObjectConvertUtil;

/**
 * @Auther:kiwi
 * @Date: 2019/6/4 14:36
 */
public class isString {

    public boolean isString(String str) {

        if (str instanceof String) {
            return true;
        }
        else {
            return false;
        }
    }
}
