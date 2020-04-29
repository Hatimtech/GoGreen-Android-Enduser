package com.gogreen.main.Model.GetOrderDetailFormNotification.REQUEST;

import com.google.gson.annotations.SerializedName;

public  class GetOrderDetailFormNotificationRequest {

    @SerializedName("method")
    private String method;
    @SerializedName("app_key")
    private String appKey;
    @SerializedName("order_id")
    private String order_id;



    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApp_key() {
        return appKey;
    }

    public void setApp_key(String appKey) {
        this.appKey = appKey;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
