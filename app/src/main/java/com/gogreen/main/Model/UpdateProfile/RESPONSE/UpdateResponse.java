package com.gogreen.main.Model.UpdateProfile.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateResponse extends BaseResponse {

    @SerializedName("result")
   List<UpdateResponseResult>  result;


    public List<UpdateResponseResult> getResult() {
        return result;
    }

    public void setResult(List<UpdateResponseResult> result) {
        this.result = result;
    }
}
