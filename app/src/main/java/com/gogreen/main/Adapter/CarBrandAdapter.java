package com.gogreen.main.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gogreen.main.Model.CarBrand.RESPONSE.CarBrandResponseResult;
import com.gogreen.main.R;

import java.util.List;


public class CarBrandAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<CarBrandResponseResult> carBrandList;
    Context context;




    public CarBrandAdapter(Context context, List<CarBrandResponseResult> objects) {
       CarBrandResponseResult carModelResponseResult=new CarBrandResponseResult();
//        CarBrandResponseResult carModelResponseResult1=new CarBrandResponseResult();
        carModelResponseResult.setCarBrandName("Select your car brand");
        carModelResponseResult.setBrandId("99");
        /*carModelResponseResult1.setCarBrandName("Other");
        carModelResponseResult1.setBrandId("999");*/
        this.carBrandList=objects;
        this.context=context;
        carBrandList.add(0,carModelResponseResult);
//        carBrandList.add(carModelResponseResult1);
        Log.d("size", String.valueOf(carBrandList.size()));
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return carBrandList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.custom_brand_model_car_spinner, null, true);
        TextView carBrand = (TextView) view.findViewById(R.id.car_model_brand);

        carBrand.setText(carBrandList.get(i).getCarBrandName());

        return view;
    }


}
