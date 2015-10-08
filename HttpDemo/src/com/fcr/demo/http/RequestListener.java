package com.fcr.demo.http;

public interface RequestListener {
    public void onSuccessBack(String type, MeitianHttpBody Body, MeitianHttpHeader header);
}
