package com.gogreen.main.Model.SelectCity.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class CityResponseWrapper {

    @SerializedName("data")
    CityListResponse response;

    public CityListResponse getResponse() {
        return response;
    }

    public void setResponse(CityListResponse response) {
        this.response = response;
    }
}
