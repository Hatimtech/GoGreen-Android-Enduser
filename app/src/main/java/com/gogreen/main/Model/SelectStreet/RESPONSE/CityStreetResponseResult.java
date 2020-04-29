package com.gogreen.main.Model.SelectStreet.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CityStreetResponseResult implements Serializable {

    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String id;
    @SerializedName("payment_type")
    private String payment_type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }
}
