package com.gogreen.main.Model.SendPaidCarDetail.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class SendPaidCarDetailResponseWrapper {


    @SerializedName("data")
    SendPaidCarDetailResponse response;

    public SendPaidCarDetailResponse getResponse() {
        return response;
    }

    public void setResponse(SendPaidCarDetailResponse response) {
        this.response = response;
    }
}
