package com.gogreen.main.Model.OTP.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class OTPVerificationResponseResult {

    @SerializedName("id")
    String id;

    @SerializedName("otp")
    String otp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
