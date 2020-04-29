package com.gogreen.main.Model.GetOTP.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOTPResponse extends BaseResponse {

    @SerializedName("result")
   List<GetOTPResponseResult>  result;


    public List<GetOTPResponseResult> getResult() {
        return result;
    }

    public void setResult(List<GetOTPResponseResult> result) {
        this.result = result;
    }
}
