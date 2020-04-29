package com.gogreen.main.Model.ForgetPassword.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FgPasswordResponse extends BaseResponse {

    @SerializedName("result")
    List<FgPasswordResponseResult> result;

    public List<FgPasswordResponseResult> getResult() {
        return result;
    }

    public void setResult(List<FgPasswordResponseResult> result) {
        this.result = result;
    }
}
