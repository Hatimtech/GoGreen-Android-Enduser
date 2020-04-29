package com.gogreen.main.Model.CarModel.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class CarModelResponseResult {

    @SerializedName("id")
    private String modelId;


    @SerializedName("name")
    private String carModelName;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }
}
