package com.gogreen.main.Model.PhoneVerified.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.gogreen.main.Model.SignUp.RESPONSE.SignUpResponseResult;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhoneVerifiedResponse extends BaseResponse {

    @SerializedName("result")
   List<PhoneVerifiedResponseResult>  result;


    public List<PhoneVerifiedResponseResult> getResult() {
        return result;
    }

    public void setResult(List<PhoneVerifiedResponseResult> result) {
        this.result = result;
    }
}
