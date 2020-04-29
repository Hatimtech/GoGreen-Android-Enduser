package com.gogreen.main.Model.EditCarDetail.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class EditCarDetailResponseWrapper {


    @SerializedName("data")
    EditCarDetailResponse response;

    public EditCarDetailResponse getResponse() {
        return response;
    }

    public void setResponse(EditCarDetailResponse response) {
        this.response = response;
    }


}
