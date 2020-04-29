package com.gogreen.main.Model.GetOTP.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class GetOTPResponseWrapper {

    @SerializedName("data")
    GetOTPResponse response;

    public GetOTPResponse getResponse() {
        return response;
    }

    public void setResponse(GetOTPResponse response) {
        this.response = response;
    }
}
