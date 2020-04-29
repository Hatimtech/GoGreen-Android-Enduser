package com.gogreen.main.Model.CheckVersion.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckVersionResponse extends BaseResponse {

    @SerializedName("result")
    List<CheckVersionResponseResult> result;

    public List<CheckVersionResponseResult> getResult() {
        return result;
    }

    public void setResult(List<CheckVersionResponseResult> result) {
        this.result = result;
    }
}
