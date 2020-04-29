package com.gogreen.main.Model.CarBrand.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class CarBrandResponseWrapper {

    @SerializedName("data")
    CarBrandResponse response;

    public CarBrandResponse getResponse() {
        return response;
    }

    public void setResponse(CarBrandResponse response) {
        this.response = response;
    }


}
