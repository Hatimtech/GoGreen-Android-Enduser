package com.gogreen.main.Model.StopPackage.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class StopPackageResponseWrapper {

    @SerializedName("data")
    StopPackageResponse response;

    public StopPackageResponse getResponse() {
        return response;
    }

    public void setResponse(StopPackageResponse response) {
        this.response = response;
    }
}
