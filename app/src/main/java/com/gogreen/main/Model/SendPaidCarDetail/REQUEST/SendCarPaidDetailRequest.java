package com.gogreen.main.Model.SendPaidCarDetail.REQUEST;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SendCarPaidDetailRequest {


    @SerializedName("method")
    private String method;
    @SerializedName("app_key")
    private String appKey;
    @SerializedName("auto_renewal")
    private String auto_renewal;

    @SerializedName("transaction_id")
    private String transactionId;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("net_paid")
    private String totalPaid;
    @SerializedName("cars")
    List<CarList> carLists;
    @SerializedName("payment_type")
    private String paymentType;
    @SerializedName("actual_payment")
    private String actual_payment;
    @SerializedName("coupan_applied")
    private String coupan_applied;
    @SerializedName("city_id")
    private String  cityID;
    @SerializedName("locality_id")
    private String locality_id;
    @SerializedName("street_id")
    private String street_id;
    @SerializedName("pt_token")
    private String  pt_token;
    @SerializedName("pt_email")
    private String pt_email;
    @SerializedName("pt_password")
    private String pt_password;

    public String getAuto_renewal() {
        return auto_renewal;
    }

    public void setAuto_renewal(String auto_renewal) {
        this.auto_renewal = auto_renewal;
    }

    public String getPt_token() {
        return pt_token;
    }

    public void setPt_token(String pt_token) {
        this.pt_token = pt_token;
    }

    public String getPt_email() {
        return pt_email;
    }

    public void setPt_email(String pt_email) {
        this.pt_email = pt_email;
    }

    public String getPt_password() {
        return pt_password;
    }

    public void setPt_password(String pt_password) {
        this.pt_password = pt_password;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getLocality_id() {
        return locality_id;
    }

    public void setLocality_id(String locality_id) {
        this.locality_id = locality_id;
    }

    public String getStreet_id() {
        return street_id;
    }

    public void setStreet_id(String street_id) {
        this.street_id = street_id;
    }

    public String getCoupan_applied() {
        return coupan_applied;
    }

    public void setCoupan_applied(String coupan_applied) {
        this.coupan_applied = coupan_applied;
    }

    public String getActual_payment() {
        return actual_payment;
    }

    public void setActual_payment(String actual_payment) {
        this.actual_payment = actual_payment;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        this.totalPaid = totalPaid;
    }

    public List<CarList> getCarLists() {
        return carLists;
    }

    public void setCarLists(List<CarList> carLists) {
        this.carLists = carLists;
    }
}
