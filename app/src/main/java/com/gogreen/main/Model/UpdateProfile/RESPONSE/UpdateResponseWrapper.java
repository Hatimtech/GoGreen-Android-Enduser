package com.gogreen.main.Model.UpdateProfile.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class UpdateResponseWrapper {

    @SerializedName("data")
    UpdateResponse response;

    public UpdateResponse getResponse() {
        return response;
    }

    public void setResponse(UpdateResponse response) {
        this.response = response;
    }
}
