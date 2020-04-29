package com.gogreen.main.Model.EnterCarDetail.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EnterCarDetailResponse extends BaseResponse{

    @SerializedName("result")
    List<EnterCarDetailResponseResult> result;

    public List<EnterCarDetailResponseResult> getResult() {
        return result;
    }

    public void setResult(List<EnterCarDetailResponseResult> result) {
        this.result = result;
    }


}
