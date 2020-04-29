package com.gogreen.main.Model.ChangePassword.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordResponseWrapper {

    @SerializedName("data")
    ChangePasswordResponse response;

    public ChangePasswordResponse getResponse() {
        return response;
    }

    public void setResponse(ChangePasswordResponse response) {
        this.response = response;
    }
}
