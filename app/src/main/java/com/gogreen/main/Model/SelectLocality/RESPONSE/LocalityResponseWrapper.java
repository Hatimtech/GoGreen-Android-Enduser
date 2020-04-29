package com.gogreen.main.Model.SelectLocality.RESPONSE;

import com.gogreen.main.Model.SelectCity.RESPONSE.CityListResponse;
import com.google.gson.annotations.SerializedName;

public class LocalityResponseWrapper {

    @SerializedName("data")
    LocalityListResponse response;

    public LocalityListResponse getResponse() {
        return response;
    }

    public void setResponse(LocalityListResponse response) {
        this.response = response;
    }
}
