package com.gogreen.main.Model.ChangePassword.REQUEST;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {

    @SerializedName("method")
    private String method;
    @SerializedName("app_key")
    private String appKey;
    @SerializedName("user_id")
    private String userID;
    @SerializedName("old_password")
    private String oldPassword;
    @SerializedName("new_password")
    private String newPassword;


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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
