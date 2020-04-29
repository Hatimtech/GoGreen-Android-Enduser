package com.gogreen.main.Model.CarBrand.RESPONSE;


import com.google.gson.annotations.SerializedName;

public class CarBrandResponseResult {

    @SerializedName("id")
    private String brandId;


    @SerializedName("name")
    private String carBrandName;

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCarBrandName() {
        return carBrandName;
    }

    public void setCarBrandName(String carBrandName) {
        this.carBrandName = carBrandName;
    }


}
