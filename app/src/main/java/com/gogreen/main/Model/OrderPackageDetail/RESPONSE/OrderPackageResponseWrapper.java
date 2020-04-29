package com.gogreen.main.Model.OrderPackageDetail.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class OrderPackageResponseWrapper {

    @SerializedName("data")
    OrderPackageResponse response;

    public OrderPackageResponse getResponse() {
        return response;
    }

    public void setResponse(OrderPackageResponse response) {
        this.response = response;
    }
}
