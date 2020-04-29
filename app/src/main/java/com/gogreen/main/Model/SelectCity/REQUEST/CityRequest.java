package com.gogreen.main.Model.SelectCity.REQUEST;

import com.google.gson.annotations.SerializedName;

public class CityRequest {


    @SerializedName("method")
    private String method;
    @SerializedName("app_key")
    private String appKey;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApp_key() {
        return appKey;
    }

    public void setApp_key(String appKey) {
        this.appKey = appKey;
    }

}
