package com.gogreen.main.Model.OTP.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class OTPVerificationResponseWrapper {

    @SerializedName("data")
    OTPVerificationResponse response;

    public OTPVerificationResponse getResponse() {
        return response;
    }

    public void setResponse(OTPVerificationResponse response) {
        this.response = response;
    }


}
