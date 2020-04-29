package com.gogreen.main.Model.AddCarModel.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class AddCarModelResponseWrapper {

    @SerializedName("data")
    AddCarModelResponse response;

    public AddCarModelResponse getResponse() {
        return response;
    }

    public void setResponse(AddCarModelResponse response) {
        this.response = response;
    }


}
