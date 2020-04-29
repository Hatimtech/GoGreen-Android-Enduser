package com.gogreen.main.Model.Coupon.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class CouponResponseResult {

    @SerializedName("coupan_code")
    private String couponCode;
    @SerializedName("discount")
    private String discount;

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
