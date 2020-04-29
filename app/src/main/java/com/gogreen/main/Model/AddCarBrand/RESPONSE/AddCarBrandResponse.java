package com.gogreen.main.Model.AddCarBrand.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddCarBrandResponse extends BaseResponse {

    public List<AddCarBrandResponseResult> getResult() {
        return result;
    }

    public void setResult(List<AddCarBrandResponseResult> result) {
        this.result = result;
    }

    @SerializedName("result")
    List<AddCarBrandResponseResult> result;
}
