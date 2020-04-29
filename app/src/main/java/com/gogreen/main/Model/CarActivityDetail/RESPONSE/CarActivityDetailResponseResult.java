package com.gogreen.main.Model.CarActivityDetail.RESPONSE;


import com.google.gson.annotations.SerializedName;

public class CarActivityDetailResponseResult {

    @SerializedName("job_done_date")
    private String job_done_date;
    @SerializedName("attendent")
    private String attendent;
    @SerializedName("cleaner_id")
    private String cleaner_id;
    @SerializedName("first_name")
    private String cleaner_first_name;
    @SerializedName("last_name")
    private String cleaner_lastName;
    @SerializedName("image_string")
    private String image_string;
    @SerializedName("id")
    private String orderID;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCleaner_first_name() {
        return cleaner_first_name;
    }

    public void setCleaner_first_name(String cleaner_first_name) {
        this.cleaner_first_name = cleaner_first_name;
    }

    public String getCleaner_lastName() {
        return cleaner_lastName;
    }

    public void setCleaner_lastName(String cleaner_lastName) {
        this.cleaner_lastName = cleaner_lastName;
    }

    public String getImage_string() {
        return image_string;
    }

    public void setImage_string(String image_string) {
        this.image_string = image_string;
    }

    public String getCleaner_id() {
        return cleaner_id;
    }

    public void setCleaner_id(String cleaner_id) {
        this.cleaner_id = cleaner_id;
    }

    public String getJob_done_date() {
        return job_done_date;
    }

    public void setJob_done_date(String job_done_date) {
        this.job_done_date = job_done_date;
    }

    public String getAttendent() {
        return attendent;
    }

    public void setAttendent(String attendent) {
        this.attendent = attendent;
    }
}
