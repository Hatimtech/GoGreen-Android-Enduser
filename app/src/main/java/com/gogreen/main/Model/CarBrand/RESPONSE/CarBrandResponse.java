package com.gogreen.main.Model.CarBrand.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarBrandResponse extends BaseResponse {

    public List<CarBrandResponseResult> getResult() {
        return result;
    }

    public void setResult(List<CarBrandResponseResult> result) {
        this.result = result;
    }

    @SerializedName("result")
    List<CarBrandResponseResult> result;
}
