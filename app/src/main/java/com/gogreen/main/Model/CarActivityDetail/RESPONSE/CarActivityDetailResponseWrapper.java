package com.gogreen.main.Model.CarActivityDetail.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class CarActivityDetailResponseWrapper {

    @SerializedName("data")
    CarActivityDetailResponse response;

    public CarActivityDetailResponse getResponse() {
        return response;
    }

    public void setResponse(CarActivityDetailResponse response) {
        this.response = response;
    }
}
