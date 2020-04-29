package com.gogreen.main.Model.UpdateToken.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class UpdateTokenResponseWrapper {

    @SerializedName("data")
    UpdateTokenResponse response;

    public UpdateTokenResponse getResponse() {
        return response;
    }

    public void setResponse(UpdateTokenResponse response) {
        this.response = response;
    }
}
