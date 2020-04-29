package com.gogreen.main.Model.OrderPackageDetail.REQUEST;

import com.google.gson.annotations.SerializedName;

public  class OrderPackageRequest {

    @SerializedName("method")
    private String method;
    @SerializedName("app_key")
    private String appKey;
    @SerializedName("user_id")
    private String userID;
    @SerializedName("order_id")
    private String orderID;
    @SerializedName("car_id")
    private String car_id;


    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }
}
