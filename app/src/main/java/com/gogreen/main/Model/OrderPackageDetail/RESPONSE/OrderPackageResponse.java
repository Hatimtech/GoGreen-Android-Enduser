package com.gogreen.main.Model.OrderPackageDetail.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderPackageResponse extends BaseResponse {

    @SerializedName("result")
    List<OrderPackageResponseResult> result;

    public List<OrderPackageResponseResult> getResult() {
        return result;
    }

    public void setResult(List<OrderPackageResponseResult> result) {
        this.result = result;
    }
}
