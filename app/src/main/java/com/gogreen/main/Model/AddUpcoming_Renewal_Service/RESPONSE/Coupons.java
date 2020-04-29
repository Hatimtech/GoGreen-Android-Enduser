package com.gogreen.main.Model.AddUpcoming_Renewal_Service.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class Coupons {

    @SerializedName("id")
    String id;
    @SerializedName("offer_name")
    String offerName;
    @SerializedName("coupan_code")
    String CouponsCode;
    @SerializedName("img_name")
    String imageName;
    @SerializedName("img_path")
    String imgPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getCouponsCode() {
        return CouponsCode;
    }

    public void setCouponsCode(String couponsCode) {
        CouponsCode = couponsCode;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
