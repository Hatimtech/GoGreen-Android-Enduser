package com.gogreen.main.Model.GetOrderDetailFormNotification.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class GetOrderDetailFormNotificationResponseWrapper {

    @SerializedName("data")
    GetOrderDetailFormNotificationResponse response;

    public GetOrderDetailFormNotificationResponse getResponse() {
        return response;
    }

    public void setResponse(GetOrderDetailFormNotificationResponse response) {
        this.response = response;
    }
}
