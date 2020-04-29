package com.gogreen.main.Model.Coupon.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CouponResponse extends BaseResponse {

    @SerializedName("result")
    List<CouponResponseResult> result;

    public List<CouponResponseResult> getResult() {
        return result;
    }

    public void setResult(List<CouponResponseResult> result) {
        this.result = result;
    }
}
