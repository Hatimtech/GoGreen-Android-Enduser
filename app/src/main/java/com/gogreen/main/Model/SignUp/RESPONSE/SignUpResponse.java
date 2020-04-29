package com.gogreen.main.Model.SignUp.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignUpResponse extends BaseResponse {

    @SerializedName("result")
   List<SignUpResponseResult>  result;


    public List<SignUpResponseResult> getResult() {
        return result;
    }

    public void setResult(List<SignUpResponseResult> result) {
        this.result = result;
    }
}
