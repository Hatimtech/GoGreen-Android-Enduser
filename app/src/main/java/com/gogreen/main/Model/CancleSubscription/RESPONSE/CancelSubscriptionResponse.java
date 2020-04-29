package com.gogreen.main.Model.CancleSubscription.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CancelSubscriptionResponse extends BaseResponse {

    public List<CancelSubscriptionResponseResult> getResult() {
        return result;
    }

    public void setResult(List<CancelSubscriptionResponseResult> result) {
        this.result = result;
    }

    @SerializedName("result")
    List<CancelSubscriptionResponseResult> result;
}
