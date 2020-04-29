package com.gogreen.main.Model.SignUp.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class SignUpResponseWrapper {

    @SerializedName("data")
    SignUpResponse response;

    public SignUpResponse getResponse() {
        return response;
    }

    public void setResponse(SignUpResponse response) {
        this.response = response;
    }
}
