package com.gogreen.main.Model.AutoRenewals.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class AutoRenewResponseWrapper {

    @SerializedName("data")
    AutoRenewResponse response;

    public AutoRenewResponse getResponse() {
        return response;
    }

    public void setResponse(AutoRenewResponse response) {
        this.response = response;
    }


}
