package com.gogreen.main.Model.ChangePhoneNumber.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class ChangePhoneNumberResponseWrapper {

    @SerializedName("data")
    ChangePhoneNumberResponse response;

    public ChangePhoneNumberResponse getResponse() {
        return response;
    }

    public void setResponse(ChangePhoneNumberResponse response) {
        this.response = response;
    }
}
