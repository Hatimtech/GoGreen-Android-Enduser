package com.gogreen.main.Model.OTP.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OTPVerificationResponse extends BaseResponse {


    @SerializedName("result")
    List<OTPVerificationResponseResult> result;


    public List<OTPVerificationResponseResult> getResult() {
        return result;
    }

    public void setResult(List<OTPVerificationResponseResult> result) {
        this.result = result;
    }

}
