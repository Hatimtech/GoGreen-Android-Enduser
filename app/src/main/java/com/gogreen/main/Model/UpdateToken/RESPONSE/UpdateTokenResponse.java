package com.gogreen.main.Model.UpdateToken.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateTokenResponse extends BaseResponse {

    @SerializedName("result")
   List<UpdateTokenResponseResult>  result;


    public List<UpdateTokenResponseResult> getResult() {
        return result;
    }

    public void setResult(List<UpdateTokenResponseResult> result) {
        this.result = result;
    }
}
