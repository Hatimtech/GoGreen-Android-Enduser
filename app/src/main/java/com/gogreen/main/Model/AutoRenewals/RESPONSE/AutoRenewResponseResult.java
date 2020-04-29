package com.gogreen.main.Model.AutoRenewals.RESPONSE;


import com.google.gson.annotations.SerializedName;

public class AutoRenewResponseResult {
    @SerializedName("id")
    String id;

    @SerializedName("auto_renewal")
    String auto_renewal;

    public String getAuto_renewal() {
        return auto_renewal;
    }

    public void setAuto_renewal(String auto_renewal) {
        this.auto_renewal = auto_renewal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
