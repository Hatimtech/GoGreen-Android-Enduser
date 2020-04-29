package com.gogreen.main.Model.SendPaidCarDetail.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SendPaidCarDetailResponse  extends BaseResponse{

    @SerializedName("result")
    List<SendPaidCarDetailResponseResult> result;

    public List<SendPaidCarDetailResponseResult> getResult() {
        return result;
    }

    public void setResult(List<SendPaidCarDetailResponseResult> result) {
        this.result = result;
    }
}
