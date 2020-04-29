package com.gogreen.main.Model.GetOrderDetailFormNotification.RESPONSE;


import com.google.gson.annotations.SerializedName;

public class GetOrderDetailFormNotificationResponseResult {

    @SerializedName("car_id")
    private String car_id;
    @SerializedName("city_id")
    private String city_id;
    @SerializedName("street_id")
    private String street_id;
    @SerializedName("locality_id")
    private String locality_id;
    @SerializedName("package_name")
    private String package_name;
    @SerializedName("package_type")
    private String package_type;
    @SerializedName("services")
    private String services;
    @SerializedName("days")
    private String days;
    @SerializedName("frequency")
    private String frequency="0";
    @SerializedName("coupan_applied")
    private String coupan_applied="";
    @SerializedName("actual_payment")
    private String actual_payment="0";
    @SerializedName("no_of_months")
    private String no_of_month="0";
    @SerializedName("remaining_days")
    private String remaining_days="0";
    @SerializedName("expiry_date")
    private String expiry_date;

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getRemaining_days() {
        return remaining_days;
    }

    public void setRemaining_days(String remaining_days) {
        this.remaining_days = remaining_days;
    }

    public String getNo_of_month() {
        return no_of_month;
    }

    public void setNo_of_month(String no_of_month) {
        this.no_of_month = no_of_month;
    }

    public String getActual_payment() {
        return actual_payment;
    }

    public void setActual_payment(String actual_payment) {
        this.actual_payment = actual_payment;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getStreet_id() {
        return street_id;
    }

    public void setStreet_id(String street_id) {
        this.street_id = street_id;
    }

    public String getLocality_id() {
        return locality_id;
    }

    public void setLocality_id(String locality_id) {
        this.locality_id = locality_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_type() {
        return package_type;
    }

    public void setPackage_type(String package_type) {
        this.package_type = package_type;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getCoupan_applied() {
        return coupan_applied;
    }

    public void setCoupan_applied(String coupan_applied) {
        this.coupan_applied = coupan_applied;
    }
}
