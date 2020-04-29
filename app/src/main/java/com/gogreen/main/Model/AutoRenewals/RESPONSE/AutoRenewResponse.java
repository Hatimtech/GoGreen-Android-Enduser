package com.gogreen.main.Model.AutoRenewals.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AutoRenewResponse extends BaseResponse {


    @SerializedName("result")
    AutoRenewResponseResult result;

    public AutoRenewResponseResult getResult() {
        return result;
    }

    public void setResult(AutoRenewResponseResult result) {
        this.result = result;
    }
}
