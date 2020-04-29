package com.gogreen.main.Model.CarModel.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class CarModelResponseWrapper {

    @SerializedName("data")
    CarModelResponse response;

    public CarModelResponse getResponse() {
        return response;
    }

    public void setResponse(CarModelResponse response) {
        this.response = response;
    }
}
