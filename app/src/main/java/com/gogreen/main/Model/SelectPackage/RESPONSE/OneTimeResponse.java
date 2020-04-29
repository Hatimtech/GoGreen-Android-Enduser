package com.gogreen.main.Model.SelectPackage.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class OneTimeResponse {


    @SerializedName("id")
    String id;

    @SerializedName("price_interior")
    String priceInterior;

    @SerializedName("price_exterior")
    String priceExterior;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPriceInterior() {
        return priceInterior;
    }

    public void setPriceInterior(String priceInterior) {
        this.priceInterior = priceInterior;
    }

    public String getPriceExterior() {
        return priceExterior;
    }

    public void setPriceExterior(String priceExterior) {
        this.priceExterior = priceExterior;
    }
}
