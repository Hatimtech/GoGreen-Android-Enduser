package com.gogreen.main.Model.AddCarBrand.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class AddCarBrandResponseWrapper {

    @SerializedName("data")
    AddCarBrandResponse response;

    public AddCarBrandResponse getResponse() {
        return response;
    }

    public void setResponse(AddCarBrandResponse response) {
        this.response = response;
    }


}
