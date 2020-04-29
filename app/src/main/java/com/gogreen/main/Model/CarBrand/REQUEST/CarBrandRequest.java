package com.gogreen.main.Model.CarBrand.REQUEST;

import com.google.gson.annotations.SerializedName;

public class CarBrandRequest{

        @SerializedName("method")
        private String method;
        @SerializedName("app_key")
        private String appKey;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
