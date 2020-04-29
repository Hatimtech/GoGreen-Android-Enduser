package com.gogreen.main.Model.CrewDetail.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CrewDetailResponse extends BaseResponse {

    @SerializedName("result")
    List<CrewDetailResponseResult> result;

    public List<CrewDetailResponseResult> getResult() {
        return result;
    }

    public void setResult(List<CrewDetailResponseResult> result) {
        this.result = result;
    }
}
