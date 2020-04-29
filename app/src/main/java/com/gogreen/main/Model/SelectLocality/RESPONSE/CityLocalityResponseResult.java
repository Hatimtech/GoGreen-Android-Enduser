package com.gogreen.main.Model.SelectLocality.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CityLocalityResponseResult implements Serializable {

    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String id;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


}
