package com.gogreen.main.Model.DeleteCarList.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class DeleteCarListResponseWrapper {

    @SerializedName("data")
    DeleteCarListResponse response;

    public DeleteCarListResponse getResponse() {
        return response;
    }

    public void setResponse(DeleteCarListResponse response) {
        this.response = response;
    }


}
