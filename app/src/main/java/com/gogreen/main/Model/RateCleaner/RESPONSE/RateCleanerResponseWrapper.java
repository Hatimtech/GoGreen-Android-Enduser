package com.gogreen.main.Model.RateCleaner.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class RateCleanerResponseWrapper {

    @SerializedName("data")
    RateCleanerResponse response;

    public RateCleanerResponse getResponse() {
        return response;
    }

    public void setResponse(RateCleanerResponse response) {
        this.response = response;
    }
}
