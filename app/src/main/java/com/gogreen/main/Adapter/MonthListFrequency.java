package com.gogreen.main.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gogreen.main.Interfaces.CarDataRepository;
import com.gogreen.main.Model.FrequencyList;
import com.gogreen.main.R;
import com.gogreen.main.Screens.BookServiceFormActivity;

import java.util.ArrayList;
import java.util.List;

public class MonthListFrequency extends BaseAdapter {
    LayoutInflater inflater;
    List<String> frequencyList = new ArrayList<>();
    Context context;

    public MonthListFrequency(Context context,List<String> month){
        this.context=context;
        this.frequencyList=month;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return 12;
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
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.custom_brand_model_car_spinner, null, true);
        TextView carBrand = (TextView) view.findViewById(R.id.car_model_brand);
        carBrand.setText(frequencyList.get(position).toString());

//        carBrand.setText(CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getFrequency().get(position).getLabel());

        return view;
    }
}

