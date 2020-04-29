package com.gogreen.main.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gogreen.main.Interfaces.CarDataRepository;
import com.gogreen.main.Interfaces.SelectedCarListArray;
import com.gogreen.main.R;
import com.gogreen.main.Screens.BookServiceFormActivity;

import java.util.List;

public class SelectedCarListSpinnerAdapter extends BaseAdapter {


    Context context;
    LayoutInflater inflater;


    public SelectedCarListSpinnerAdapter(Context context) {

        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {

        return CarDataRepository.getInstance().getArrayList().size();

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


        view = inflater.inflate(R.layout.selected_car_list_adapter, null);
        TextView parking_Number = (TextView) view.findViewById(R.id.carbrand);
        TextView model = (TextView) view.findViewById(R.id.carmodel);
        parking_Number.setText(CarDataRepository.getInstance().getArrayList().get(i).getCarModel());
        model.setText(CarDataRepository.getInstance().getArrayList().get(i).getCarPlateNumber());
        return view;
    }

}