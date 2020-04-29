package com.gogreen.main.Model.DeleteCarList.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeleteCarListResponse extends BaseResponse {


    @SerializedName("result")
    List<DeleteCarListResponseResult> result;

    public List<DeleteCarListResponseResult> getResult() {
        return result;
    }

    public void setResult(List<DeleteCarListResponseResult> result) {
        this.result = result;
    }


}
