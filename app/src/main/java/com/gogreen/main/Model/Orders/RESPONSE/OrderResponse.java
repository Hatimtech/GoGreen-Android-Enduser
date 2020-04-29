package com.gogreen.main.Model.Orders.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse extends BaseResponse {

    @SerializedName("result")
    private OrderResponseResult result;

    public OrderResponseResult getResult() {
        return result;
    }

    public void setResult(OrderResponseResult result) {
        this.result = result;
    }
}
