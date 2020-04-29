package com.gogreen.main.Interfaces;

import com.gogreen.main.Model.FrequencyList;
import com.gogreen.main.Model.SelectPackage.RESPONSE.SelectPackageResponseWrapper;
import com.gogreen.main.Model.ServiceDays;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectedCarListArray implements Serializable {

    String carModel;
    String carPlateNumber;
    String carPackage = "monthly";
    String carParkingNumber;
    String servicingType;
    String carId;
    String packageName;
    String no_of_months="0";


    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    List<ServiceDays> days = new ArrayList<>();
    List<FrequencyList> frequency = new ArrayList<>();
    String Date = "/    /";
    String color;
    String type;
    String LocalityId;
    boolean interiorSelected = false;
    boolean exteriorSelected = true;
    boolean isAgreed = false;
    int selectedFrequency = 0;
    int selectedMonthlyFrequency = 0;
    SelectPackageResponseWrapper response = null;
    String totalAmount="0" ;
    String InteriorAmount="0";
    boolean expanded = false;

    public int getSelectedMonthlyFrequency() {
        return selectedMonthlyFrequency;
    }

    public void setSelectedMonthlyFrequency(int selectedMonthlyFrequency) {
        this.selectedMonthlyFrequency = selectedMonthlyFrequency;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getInteriorAmount() {
        return InteriorAmount;
    }

    public void setInteriorAmount(String interiorAmount) {
        InteriorAmount = interiorAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public SelectPackageResponseWrapper getResponse() {
        return response;
    }

    public void setResponse(SelectPackageResponseWrapper response) {
        this.response = response;
    }

    public void resetDays() {
        days = getDaysList();
    }

    public boolean isAgreed() {
        return isAgreed;
    }

    public void setAgreed(boolean agreed) {
        isAgreed = agreed;
    }

    public int getSelectedFrequency() {
        return selectedFrequency;
    }

    public void setSelectedFrequency(int selectedFrequency) {
        this.selectedFrequency = selectedFrequency;
    }

    public List<FrequencyList> getFrequency() {
        return frequency;
    }

    public void setFrequency(List<FrequencyList> frequency) {
        this.frequency = frequency;
    }

    public List<ServiceDays> getDays() {
        return days;
    }

    public void setDays(List<ServiceDays> days) {
        this.days = days;
    }

    public boolean isInteriorSelected() {
        return interiorSelected;
    }

    public void setInteriorSelected(boolean interiorSelected) {
        this.interiorSelected = interiorSelected;
    }

    public boolean isExteriorSelected() {
        return exteriorSelected;
    }

    public void setExteriorSelected(boolean exteriorSelected) {
        this.exteriorSelected = exteriorSelected;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocalityId() {
        return LocalityId;
    }

    public void setLocalityId(String localityId) {
        LocalityId = localityId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getServicingType() {
        return servicingType;
    }

    public void setServicingType(String servicingType) {
        this.servicingType = servicingType;
    }

    public String getCarParkingNumber() {
        return carParkingNumber;
    }

    public void setCarParkingNumber(String carParkingNumber) {
        this.carParkingNumber = carParkingNumber;
    }

    public String getCarPackage() {
        return carPackage;
    }

    public void setCarPackage(String carPackage) {
        this.carPackage = carPackage;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public String getNo_of_months() {
        return no_of_months;
    }

    public void setNo_of_months(String no_of_months) {
        this.no_of_months = no_of_months;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    @Override
    public String toString() {
        return super.toString();
    }


    List<ServiceDays> getDaysList() {
        List<ServiceDays> list = new ArrayList<>();
        list.add(new ServiceDays("Sun"));
        list.add(new ServiceDays("Mon"));
        list.add(new ServiceDays("Tue"));
        list.add(new ServiceDays("Wed"));
        list.add(new ServiceDays("Thu"));
        list.add(new ServiceDays("Sat"));

        return list;
    }


}
