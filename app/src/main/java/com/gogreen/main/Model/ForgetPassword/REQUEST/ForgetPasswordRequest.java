package com.gogreen.main.Model.ForgetPassword.REQUEST;

import com.google.gson.annotations.SerializedName;

public  class ForgetPasswordRequest {

    @SerializedName("method")
    private String method;
    @SerializedName("app_key")
    private String appKey;
    @SerializedName("email")
    private String email;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
