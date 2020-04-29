package com.gogreen.main.Model.Login.REQUEST;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("method")
    private String method;
    @SerializedName("app_key")
    private String appKey;
    @SerializedName("login_type")
    private String loginType;
    @SerializedName("social_id")
    private String socailId;
    @SerializedName("device_type")
    private String deviceType;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;


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

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }


    public String getSocailId() {
        return socailId;
    }

    public void setSocailId(String socailId) {
        this.socailId = socailId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}