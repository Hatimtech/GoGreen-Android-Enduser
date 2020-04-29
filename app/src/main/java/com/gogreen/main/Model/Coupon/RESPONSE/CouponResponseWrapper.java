package com.gogreen.main.Model.Coupon.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class CouponResponseWrapper {

    @SerializedName("data")
    CouponResponse response;

    public CouponResponse getResponse() {
        return response;
    }

    public void setResponse(CouponResponse response) {
        this.response = response;
    }
}
