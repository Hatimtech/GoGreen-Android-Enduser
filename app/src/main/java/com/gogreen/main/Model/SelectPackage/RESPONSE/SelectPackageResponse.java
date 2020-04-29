package com.gogreen.main.Model.SelectPackage.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SelectPackageResponse extends BaseResponse {

    @SerializedName("result")
    List<SelectPackageResponseResult> result;

    public List<SelectPackageResponseResult> getResult() {
        return result;
    }

    public void setResult(List<SelectPackageResponseResult> result) {
        this.result = result;
    }
}
