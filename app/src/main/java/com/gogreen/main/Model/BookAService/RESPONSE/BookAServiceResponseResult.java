package com.gogreen.main.Model.BookAService.RESPONSE;


import com.google.gson.annotations.SerializedName;


public class BookAServiceResponseResult {

    @SerializedName("id")
    private String carId;
    @SerializedName("brand")
    private String carBrand;
    @SerializedName("reg_no")
    private String plateNumber;
    @SerializedName("model")
    private String carModel;
    @SerializedName("type")
    private String carType;
    @SerializedName("color")
    private String carColor;
    @SerializedName("parking_number")
    private String parkingNumber;
    @SerializedName("is_package")
    private String is_package;
    @SerializedName("brand_id")
    private String brand_id;
    @SerializedName("model_id")
    private String model_id;


    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getIs_package() {
        return is_package;
    }

    public void setIs_package(String is_package) {
        this.is_package = is_package;
    }

    public String getParkingNumber() {
        return parkingNumber;
    }

    public void setParkingNumber(String parkingNumber) {
        this.parkingNumber = parkingNumber;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    boolean isSelected = false;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }


}
