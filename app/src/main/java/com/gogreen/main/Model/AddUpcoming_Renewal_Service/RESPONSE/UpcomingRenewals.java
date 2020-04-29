package com.gogreen.main.Model.AddUpcoming_Renewal_Service.RESPONSE;


import com.google.gson.annotations.SerializedName;

public class UpcomingRenewals {

    @SerializedName("user_id")
    String userID;
    @SerializedName("expiry_date")
    String expiry_date;
    @SerializedName("days")
    String days;
    @SerializedName("package_type")
    String package_type;
    @SerializedName("parking_number")
    String parking_number;
    @SerializedName("type")
    String carType;
    @SerializedName("model")
    String model;
    @SerializedName("brand")
    String brand;
    @SerializedName("amount")
    String amount;
    @SerializedName("one_time_service_date")
    String one_time_service_date;
    @SerializedName("services")
    String services;
    @SerializedName("frequency")
    String frequency;
    @SerializedName("car_id")
    String car_id;
    @SerializedName("reg_no")
    String reg_no;
    @SerializedName("package_name")
    String package_name;
    @SerializedName("no_of_months")
    String no_of_months="0";

    public String getNo_of_months() {
        return no_of_months;
    }

    public void setNo_of_months(String no_of_months) {
        this.no_of_months = no_of_months;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getOne_time_service_date() {
        return one_time_service_date;
    }

    public void setOne_time_service_date(String one_time_service_date) {
        this.one_time_service_date = one_time_service_date;
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

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getPackage_type() {
        return package_type;
    }

    public void setPackage_type(String package_type) {
        this.package_type = package_type;
    }

    public String getParking_number() {
        return parking_number;
    }

    public void setParking_number(String parking_number) {
        this.parking_number = parking_number;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
