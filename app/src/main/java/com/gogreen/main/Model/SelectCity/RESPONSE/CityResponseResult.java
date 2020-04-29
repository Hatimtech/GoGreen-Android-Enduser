package com.gogreen.main.Model.SelectCity.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class CityResponseResult {
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String id;
    @SerializedName("serving")
    private String serving_no;


    public String getServingNumber() { return serving_no; }

    public void setServingNumber(String serving_no) { this.serving_no = serving_no; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
