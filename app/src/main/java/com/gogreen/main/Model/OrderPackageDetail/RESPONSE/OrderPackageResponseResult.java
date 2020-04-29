package com.gogreen.main.Model.OrderPackageDetail.RESPONSE;


import com.google.gson.annotations.SerializedName;

public class OrderPackageResponseResult {

    @SerializedName("id")
    private String id;
    @SerializedName("one_time_service_date")
    private String date;
    @SerializedName("days")
    private String days;
    @SerializedName("expiry_date")
    private String expireDate;
    @SerializedName("package_type")
    private String packageType;
    @SerializedName("amount")
    private String amount;
    @SerializedName("is_off")
    private String is_off;
    @SerializedName("subscription")
    private String subscription;

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getIs_off() {
        return is_off;
    }

    public void setIs_off(String is_off) {
        this.is_off = is_off;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }
}
