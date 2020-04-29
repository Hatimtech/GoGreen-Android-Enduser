package com.gogreen.main.Model.SelectCity.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityListResponse extends BaseResponse {

    @SerializedName("result")
    List<CityResponseResult> result;

    public List<CityResponseResult> getResult() {
        return result;
    }

    public void setResult(List<CityResponseResult> result) {
        this.result = result;
    }
}
