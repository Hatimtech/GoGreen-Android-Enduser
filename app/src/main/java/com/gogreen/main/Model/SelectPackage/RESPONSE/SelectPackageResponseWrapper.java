package com.gogreen.main.Model.SelectPackage.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class SelectPackageResponseWrapper {

    @SerializedName("data")
    SelectPackageResponse response;

    public SelectPackageResponse getResponse() {
        return response;
    }

    public void setResponse(SelectPackageResponse response) {
        this.response = response;
    }
}
