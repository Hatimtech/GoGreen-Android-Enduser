package com.gogreen.main.Model.StopPackage.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StopPackageResponse extends BaseResponse {

    @SerializedName("result")
   List<StopPackageResponseResult>  result;


    public List<StopPackageResponseResult> getResult() {
        return result;
    }

    public void setResult(List<StopPackageResponseResult> result) {
        this.result = result;
    }
}
