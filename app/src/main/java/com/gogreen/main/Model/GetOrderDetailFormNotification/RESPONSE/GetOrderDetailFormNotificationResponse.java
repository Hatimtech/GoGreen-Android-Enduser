package com.gogreen.main.Model.GetOrderDetailFormNotification.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOrderDetailFormNotificationResponse extends BaseResponse {

    @SerializedName("result")
    List<GetOrderDetailFormNotificationResponseResult> result;

    public List<GetOrderDetailFormNotificationResponseResult> getResult() {
        return result;
    }

    public void setResult(List<GetOrderDetailFormNotificationResponseResult> result) {
        this.result = result;
    }
}
