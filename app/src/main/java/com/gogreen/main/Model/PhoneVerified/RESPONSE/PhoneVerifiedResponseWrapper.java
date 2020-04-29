package com.gogreen.main.Model.PhoneVerified.RESPONSE;

import com.gogreen.main.Model.SignUp.RESPONSE.SignUpResponse;
import com.google.gson.annotations.SerializedName;

public class PhoneVerifiedResponseWrapper {

    @SerializedName("data")
    PhoneVerifiedResponse response;

    public PhoneVerifiedResponse getResponse() {
        return response;
    }

    public void setResponse(PhoneVerifiedResponse response) {
        this.response = response;
    }
}
