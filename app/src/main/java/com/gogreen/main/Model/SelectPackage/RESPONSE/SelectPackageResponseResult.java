package com.gogreen.main.Model.SelectPackage.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SelectPackageResponseResult {
    @SerializedName("name")
    String packageName;
    @SerializedName("once")
    OneTimeResponse oneTimeResponse;
    @SerializedName("monthly")
    MonthlyResponse monthlyResponse;
    @SerializedName("percentage")
    PercentageDiscount percentageDiscount;

    public PercentageDiscount getPercentageDiscount() {
        return percentageDiscount;
    }

    public void setPercentageDiscount(PercentageDiscount percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public OneTimeResponse getOneTimeResponse() {
        return oneTimeResponse;
    }

    public void setOneTimeResponse(OneTimeResponse oneTimeResponse) {
        this.oneTimeResponse = oneTimeResponse;
    }

    public MonthlyResponse getMonthlyResponse() {
        return monthlyResponse;
    }

    public void setMonthlyResponse(MonthlyResponse monthlyResponse) {
        this.monthlyResponse = monthlyResponse;
    }
}
