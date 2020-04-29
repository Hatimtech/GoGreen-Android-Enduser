package com.gogreen.main.Model.EnterCarDetail.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class EnterCarDetailResponseWrapper {


    @SerializedName("data")
    EnterCarDetailResponse response;

    public EnterCarDetailResponse getResponse() {
        return response;
    }

    public void setResponse(EnterCarDetailResponse response) {
        this.response = response;
    }


}
