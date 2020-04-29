package com.gogreen.main.Model.ChangePhoneNumber.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChangePhoneNumberResponse extends BaseResponse {

    @SerializedName("result")
   List<ChangePhoneNumberResponseResult>  result;


    public List<ChangePhoneNumberResponseResult> getResult() {
        return result;
    }

    public void setResult(List<ChangePhoneNumberResponseResult> result) {
        this.result = result;
    }
}
