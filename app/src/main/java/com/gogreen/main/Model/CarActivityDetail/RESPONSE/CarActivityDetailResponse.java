package com.gogreen.main.Model.CarActivityDetail.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarActivityDetailResponse extends BaseResponse {

    @SerializedName("result")
   List<CarActivityDetailResponseResult>  result;


    public List<CarActivityDetailResponseResult> getResult() {
        return result;
    }

    public void setResult(List<CarActivityDetailResponseResult> result) {
        this.result = result;
    }
}
