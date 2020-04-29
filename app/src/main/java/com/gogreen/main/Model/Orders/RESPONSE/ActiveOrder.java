package com.gogreen.main.Model.Orders.RESPONSE;

import com.google.gson.annotations.SerializedName;


public class ActiveOrder {

    @SerializedName("id")
    private String id;
    @SerializedName("expiry_date")
    private String expiry_date;
    @SerializedName("purchase_date")
    private String purchaseDate;
    @SerializedName("package_type")
    private String package_type;
    @SerializedName("parking_number")
    private String parking_number;
    @SerializedName("reg_no")
    private String reg_no;
    @SerializedName("model")
    private String model;
    @SerializedName("brand")
    private String brand;
    @SerializedName("services")
    private String services;
    @SerializedName("orders_id")
    private String ordersId;
    @SerializedName("car_id")
    private String carID;
    @SerializedName("payment_key")
    private String payment_key;

    public String getPayment_key() {
        return payment_key;
    }

    public void setPayment_key(String payment_key) {
        this.payment_key = payment_key;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
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

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
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

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }
}
