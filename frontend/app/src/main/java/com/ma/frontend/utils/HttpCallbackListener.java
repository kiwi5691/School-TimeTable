package com.ma.frontend.utils;

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}
