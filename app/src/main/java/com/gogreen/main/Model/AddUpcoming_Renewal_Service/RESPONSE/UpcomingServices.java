package com.gogreen.main.Model.AddUpcoming_Renewal_Service.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class UpcomingServices {


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
    @SerializedName("team_name")
    String team_name;
    @SerializedName("reg_no")
    String reg_no;

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
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
