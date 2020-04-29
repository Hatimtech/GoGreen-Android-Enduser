package com.gogreen.main.Interfaces;


import android.util.Log;

import com.gogreen.main.Utils.CommonUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class CarDataRepository extends Observable {

    List<SelectedCarListArray> arrayList = new ArrayList<>();
    private static CarDataRepository INSTANCE = null;



    private CarDataRepository() {

    }


    public static CarDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CarDataRepository();
        }
        return INSTANCE;
    }


    public List<SelectedCarListArray> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<SelectedCarListArray> arrayList) {
        this.arrayList.clear();
        this.arrayList = arrayList;
    }

    public void logCarList() {
        CommonUtils.log("ALIEN", new Gson().toJson(arrayList));
    }

    public void refreshCarSelection() {
        setChanged();
        notifyObservers();
    }

    public void toggleExterior(int position) {
        arrayList.get(position).setExteriorSelected(true);
        if (arrayList.get(position).isExteriorSelected()) {
            arrayList.get(position).setExteriorSelected(false);

        } else {
            arrayList.get(position).setExteriorSelected(true);


        }
        setService(position);
        setChanged();
        notifyObservers();
    }

    public void toggleInterior(int position) {
        if (arrayList.get(position).isInteriorSelected()) {
            arrayList.get(position).setInteriorSelected(false);

        } else {
            arrayList.get(position).setInteriorSelected(true);


        }
        setService(position);
        setChanged();
        notifyObservers();
    }

    public void setTabSelection(int position, String selection) {
        arrayList.get(position).setCarPackage(selection);

        setChanged();
        notifyObservers();

    }

    public void toggleAgreement(int position) {
        if (arrayList.get(position).isAgreed()) {
            arrayList.get(position).setAgreed(false);
        } else {
            arrayList.get(position).setAgreed(true);
        }
        setChanged();
        notifyObservers();
    }

    public List<String> ValidateData() {
        List<String> cars = new ArrayList<>();
        Log.d("ALIEN", new Gson().toJson(arrayList));
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getCarPackage().equals("once")) {
                Log.d("ALIEN", String.valueOf(i));
                //One Time Validation
                if (arrayList.get(i).getDate() == "/    /" || !arrayList.get(i).isAgreed() || !arrayList.get(i).isInteriorSelected() && !arrayList.get(i).isExteriorSelected()) {
                    cars.add(arrayList.get(i).getCarModel());
                }
            } else {
                Log.d("ALIEN", String.valueOf(i));
                //Monthly Validation
                if (arrayList.get(i).getFrequency().get(arrayList.get(i).getSelectedFrequency()).getValue() != getselectedDaysCount(i) || !arrayList.get(i).isAgreed() || !arrayList.get(i).isInteriorSelected() && !arrayList.get(i).isExteriorSelected()) {
                    cars.add(arrayList.get(i).getCarModel());
                   /* CommonUtils.log("ALIEN","Frequency : " + String.valueOf((arrayList.get(i).getSelectedFrequency())
                     + " Count : " + String.valueOf(getselectedDaysCount(i))));*/
                }
                //  arrayList.get(i).getFrequency().get(arrayList.get(i).getSelectedFrequency()).getValue()!=getselectedDaysCount(i)
            }
        }
        return cars;
    }

    int getselectedDaysCount(int i) {
        int count = 0;
        for (int k = 0; k < arrayList.get(i).getDays().size(); k++) {
            if (arrayList.get(i).getDays().get(k).isSelected())
                count = count + 1;
        }
        return count;
    }

    public void setService(int pos) {
        arrayList.get(pos).setExteriorSelected(true);



            if (arrayList.get(pos).isExteriorSelected()) {
                arrayList.get(pos).setServicingType("Exterior");
            }

            if (arrayList.get(pos).isExteriorSelected() && arrayList.get(pos).isInteriorSelected()) {
                arrayList.get(pos).setServicingType("Exterior" + "," + "Interior" );
            }



    }
}
