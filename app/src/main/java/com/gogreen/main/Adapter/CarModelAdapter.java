package com.gogreen.main.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gogreen.main.Model.CarModel.RESPONSE.CarModelResponseResult;
import com.gogreen.main.R;

import java.util.ArrayList;
import java.util.List;


public class CarModelAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<CarModelResponseResult> carModelList = new ArrayList<>();
    Context context;


    public CarModelAdapter(Context context, List<CarModelResponseResult> objects, boolean shouldAddOther) {
      CarModelResponseResult carModelResponseResult=new CarModelResponseResult();

      carModelResponseResult.setCarModelName("Select your car model");
      carModelResponseResult.setModelId("99");

        this.carModelList=objects;
        this.context=context;

        carModelList.add(0,carModelResponseResult);
        if (shouldAddOther){
           /* CarModelResponseResult carModelResponseResult1=new CarModelResponseResult();
            carModelResponseResult1.setCarModelName("Other");
            carModelResponseResult1.setModelId("999");
            carModelList.add(carModelResponseResult1);*/
        }
        Log.d("size", String.valueOf(carModelList.size()));
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return carModelList.size();
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

        carBrand.setText(carModelList.get(i).getCarModelName());

            return view;
        }


    }
