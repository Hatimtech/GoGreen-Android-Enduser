package com.gogreen.main.Model.SelectLocality.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.gogreen.main.Model.SelectCity.RESPONSE.CityResponseResult;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocalityListResponse extends BaseResponse {

    @SerializedName("result")
    List<CityLocalityResponseResult> result;

    public List<CityLocalityResponseResult> getResult() {
        return result;
    }

    public void setResult(List<CityLocalityResponseResult> result) {
        this.result = result;
    }
}
