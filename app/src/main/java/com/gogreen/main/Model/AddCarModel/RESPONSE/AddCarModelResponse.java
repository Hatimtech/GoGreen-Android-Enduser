package com.gogreen.main.Model.AddCarModel.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddCarModelResponse extends BaseResponse {

    public List<AddCarModelResponseResult> getResult() {
        return result;
    }

    public void setResult(List<AddCarModelResponseResult> result) {
        this.result = result;
    }

    @SerializedName("result")
    List<AddCarModelResponseResult> result;
}
