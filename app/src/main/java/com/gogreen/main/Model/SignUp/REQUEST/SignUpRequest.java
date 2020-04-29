package com.gogreen.main.Model.SignUp.REQUEST;

import com.google.gson.annotations.SerializedName;

public class SignUpRequest {


    @SerializedName("method")
    private String method;
    @SerializedName("app_key")
    private String appKey;
    @SerializedName("login_type")
    private String loginType;
    @SerializedName("social_id")
    private String socialId;
    @SerializedName("device_type")
    private String deviceType;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("device_token")
    private String deviceToken;
    @SerializedName("password")
    private String password;


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getSocailId() {
        return socialId;
    }

    public void setSocailId(String socailId) {
        this.socialId = socailId;
    }

    public String getDevice_type() {
        return deviceType;
    }

    public void setDevice_type(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
