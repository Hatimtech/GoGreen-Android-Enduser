package com.gogreen.main.Model.RateCleaner.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RateCleanerResponse extends BaseResponse {

    @SerializedName("result")
    List<RateCleanerResponseResult> result;

    public List<RateCleanerResponseResult> getResult() {
        return result;
    }

    public void setResult(List<RateCleanerResponseResult> result) {
        this.result = result;
    }
}
