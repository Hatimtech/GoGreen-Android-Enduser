package com.gogreen.main.Model.SelectPackage.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class MonthlyResponse {


    @SerializedName("id")
    String id;

    @SerializedName("interior_once")
    String interiorOnes;

    @SerializedName("exterior_once")
    String exteriorOnes;

    @SerializedName("interior_thrice")
    String interRiorThrice;

    @SerializedName("exterior_thrice")
    String exteriorThrices;

    @SerializedName("exterior_five")
    String exteriorFive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInteriorOnes() {
        return interiorOnes;
    }

    public void setInteriorOnes(String interiorOnes) {
        this.interiorOnes = interiorOnes;
    }

    public String getExteriorOnes() {
        return exteriorOnes;
    }

    public void setExteriorOnes(String exteriorOnes) {
        this.exteriorOnes = exteriorOnes;
    }

    public String getInterRiorThrice() {
        return interRiorThrice;
    }

    public void setInterRiorThrice(String interRiorThrice) {
        this.interRiorThrice = interRiorThrice;
    }

    public String getExteriorThrices() {
        return exteriorThrices;
    }

    public void setExteriorThrices(String exteriorThrices) {
        this.exteriorThrices = exteriorThrices;
    }

    public String getExteriorFive() {
        return exteriorFive;
    }

    public void setExteriorFive(String exteriorFive) {
        this.exteriorFive = exteriorFive;
    }

    public String getInteriorFive() {
        return interiorFive;
    }

    public void setInteriorFive(String interiorFive) {
        this.interiorFive = interiorFive;
    }

    @SerializedName("interior_five")
    String interiorFive;


}
