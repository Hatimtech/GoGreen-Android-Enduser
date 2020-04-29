package com.gogreen.main.Model.CrewDetail.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class CrewDetailResponseWrapper {

    @SerializedName("data")
    CrewDetailResponse response;

    public CrewDetailResponse getResponse() {
        return response;
    }

    public void setResponse(CrewDetailResponse response) {
        this.response = response;
    }
}
