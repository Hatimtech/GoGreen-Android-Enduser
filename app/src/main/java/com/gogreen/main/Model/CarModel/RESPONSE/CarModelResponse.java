package com.gogreen.main.Model.CarModel.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarModelResponse extends BaseResponse {
    public void setResult(List<CarModelResponseResult> result) {
        this.result = result;
    }

    public List<CarModelResponseResult> getResult() {
        return result;
    }

    @SerializedName("result")
    List<CarModelResponseResult> result;
}
