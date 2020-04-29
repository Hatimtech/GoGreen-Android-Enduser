package com.gogreen.main.Model.SelectStreet.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.gogreen.main.Model.SelectCity.RESPONSE.CityResponseResult;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StreetListResponse extends BaseResponse {

    @SerializedName("result")
    List<CityStreetResponseResult> result;

    public List<CityStreetResponseResult> getResult() {
        return result;
    }

    public void setResult(List<CityStreetResponseResult> result) {
        this.result = result;
    }
}
