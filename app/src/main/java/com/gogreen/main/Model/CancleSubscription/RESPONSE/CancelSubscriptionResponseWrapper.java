package com.gogreen.main.Model.CancleSubscription.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class CancelSubscriptionResponseWrapper {

    @SerializedName("data")
    CancelSubscriptionResponse response;

    public CancelSubscriptionResponse getResponse() {
        return response;
    }

    public void setResponse(CancelSubscriptionResponse response) {
        this.response = response;
    }


}
