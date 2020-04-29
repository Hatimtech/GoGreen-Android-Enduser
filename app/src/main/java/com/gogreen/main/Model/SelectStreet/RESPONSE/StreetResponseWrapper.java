package com.gogreen.main.Model.SelectStreet.RESPONSE;

import com.gogreen.main.Model.SelectCity.RESPONSE.CityListResponse;
import com.google.gson.annotations.SerializedName;

public class StreetResponseWrapper {

    @SerializedName("data")
    StreetListResponse response;

    public StreetListResponse getResponse() {
        return response;
    }

    public void setResponse(StreetListResponse response) {
        this.response = response;
    }
}
