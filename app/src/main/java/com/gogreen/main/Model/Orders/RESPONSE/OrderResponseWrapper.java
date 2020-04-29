package com.gogreen.main.Model.Orders.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class OrderResponseWrapper {

    @SerializedName("data")
    OrderResponse response;

    public OrderResponse getResponse() {
        return response;
    }

    public void setResponse(OrderResponse response) {
        this.response = response;
    }
}
