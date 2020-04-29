package com.gogreen.main.Model.CheckVersion.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class CheckVersionResponseWrapper {

    @SerializedName("data")
    CheckVersionResponse response;

    public CheckVersionResponse getResponse() {
        return response;
    }

    public void setResponse(CheckVersionResponse response) {
        this.response = response;
    }
}
