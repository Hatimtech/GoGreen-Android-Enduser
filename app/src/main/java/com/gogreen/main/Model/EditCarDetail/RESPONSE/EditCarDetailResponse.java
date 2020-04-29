package com.gogreen.main.Model.EditCarDetail.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EditCarDetailResponse extends BaseResponse{

    @SerializedName("result")
    List<EditCarDetailResponseResult> result;

    public List<EditCarDetailResponseResult> getResult() {
        return result;
    }

    public void setResult(List<EditCarDetailResponseResult> result) {
        this.result = result;
    }


}
