package com.gogreen.main.Model.CrewDetail.RESPONSE;


import com.google.gson.annotations.SerializedName;

public class CrewDetailResponseResult {

    @SerializedName("team_id")
    private String teamID;
    @SerializedName("first_name")
    private String clName;
    @SerializedName("phone_number")
    private String clPhone;
    @SerializedName("service")
    private String servicingTime;
    @SerializedName("rating")
    private String rating;
    @SerializedName("count_who_rated")
    private String count_who_rated;
    @SerializedName("last_name")
    private String clLastName;


 public String getClLastName() {
        return clLastName;
    }

    public void setClLastName(String clLastName) {
        this.clLastName = clLastName;
    }

    public String getCount_who_rated() {
        return count_who_rated;
    }

    public void setCount_who_rated(String count_who_rated) {
        this.count_who_rated = count_who_rated;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getClName() {
        return clName;
    }

    public void setClName(String clName) {
        this.clName = clName;
    }

    public String getClPhone() {
        return clPhone;
    }

    public void setClPhone(String clPhone) {
        this.clPhone = clPhone;
    }

    public String getServicingTime() {
        return servicingTime;
    }

    public void setServicingTime(String servicingTime) {
        this.servicingTime = servicingTime;
    }
}
