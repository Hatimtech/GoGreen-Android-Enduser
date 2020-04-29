package com.gogreen.main.Model.RateCleaner.REQUEST;

import com.google.gson.annotations.SerializedName;

public  class RateCleanerRequest {

    @SerializedName("method")
    private String method;
    @SerializedName("app_key")
    private String appKey;
    @SerializedName("user_id")
    private String userID;
    @SerializedName("cleaner_id")
    private String cleanerID;
    @SerializedName("rating")
    private String rating;
    @SerializedName("car_id")
    private String car_id;
    @SerializedName("feedback")
    private String feedback;
    @SerializedName("order_id")
    private String order_id;
    @SerializedName("activity_id")
    private String activityID;

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public String getCleanerID() {
        return cleanerID;
    }

    public void setCleanerID(String cleanerID) {
        this.cleanerID = cleanerID;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }
}
