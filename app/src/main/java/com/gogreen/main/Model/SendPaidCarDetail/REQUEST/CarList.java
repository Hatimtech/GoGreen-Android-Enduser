package com.gogreen.main.Model.SendPaidCarDetail.REQUEST;

import com.google.gson.annotations.SerializedName;

public class CarList {


    @SerializedName("car_id")
    private String car_id;
    @SerializedName("package_type")
    private String package_type;
    @SerializedName("purchase_date")
    private String purchase_date;
    @SerializedName("services")
    private String services;
    @SerializedName("frequency")
    private String frequency;
    @SerializedName("amount")
    private String amount;
    @SerializedName("days")
    private String days;
    @SerializedName("package_name")
    private String name;
    @SerializedName("one_time_service_date")
    private String OneTimeServiceDate;
    @SerializedName("no_of_months")
    private String no_of_months;

    public String getNo_of_months() {
        return no_of_months;
    }

    public void setNo_of_months(String no_of_months) {
        this.no_of_months = no_of_months;
    }

    public String getOneTimeServiceDate() {
        return OneTimeServiceDate;
    }

    public void setOneTimeServiceDate(String oneTimeServiceDate) {
        OneTimeServiceDate = oneTimeServiceDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
     public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getPackage_type() {
        return package_type;
    }

    public void setPackage_type(String package_type) {
        this.package_type = package_type;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
