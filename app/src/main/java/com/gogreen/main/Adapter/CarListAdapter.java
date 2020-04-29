package com.gogreen.main.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gogreen.main.Interfaces.SelectedCarListArray;
import com.gogreen.main.Model.BookAService.RESPONSE.BookAServiceResponseResult;
import com.gogreen.main.Model.FrequencyList;
import com.gogreen.main.Model.ServiceDays;
import com.gogreen.main.R;
import com.gogreen.main.Screens.BookServiceActivity;
import com.gogreen.main.Utils.CommonUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {
    private List<BookAServiceResponseResult> carList;
    List<SelectedCarListArray> selectedCarListArrays;
    Context context;

    public CarListAdapter(List<BookAServiceResponseResult> carList, Context context) {
        this.carList = carList;
        this.context = context;
    }

    @Override
    public CarListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list, parent, false));

    }

    @Override
    public void onBindViewHolder(final CarListAdapter.ViewHolder holder, final int position) {
        holder.parkingBay.setText(carList.get(holder.getAdapterPosition()).getParkingNumber());
        holder.carPlateNumber.setText(carList.get(holder.getAdapterPosition()).getPlateNumber());
        holder.carColor.setText(carList.get(holder.getAdapterPosition()).getCarColor());
        holder.carModelName.setText(carList.get(holder.getAdapterPosition()).getCarBrand() + "," + " " + carList.get(holder.getAdapterPosition()).getCarModel());
        if (carList.get(holder.getAdapterPosition()).getCarType().equals("Saloon")) {
            holder.carIcon.setImageResource(R.mipmap.small_saloon_image);
        } else {
            holder.carIcon.setImageResource(R.mipmap.small_suv_image);
        }

        if (carList.get(holder.getAdapterPosition()).getIs_package().equals("2")) {
            holder.packageExist.setVisibility(View.VISIBLE);
        } else {
            holder.packageExist.setVisibility(View.GONE);
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carList.get(holder.getAdapterPosition()).getIs_package().equals("2")) {
                    CommonUtils.alert(context, context.getString(R.string.package_active));
                } else {


                    if (carList.get(position).isSelected()) {
                        carList.get(position).setSelected(false);
                        holder.checkedIcon.setImageResource(R.mipmap.unselect_check_done);

                    } else {
                        carList.get(position).setSelected(true);
                        holder.checkedIcon.setImageResource(R.mipmap.select_check_done);
                    }
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return carList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView parkingBay, carModelName, carColor, carPlateNumber;
        ImageView carIcon, checkedIcon;
        LinearLayout packageExist;
        public CardView cardView;


        ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            parkingBay = (TextView) itemView.findViewById(R.id.parking_bay);
            carModelName = (TextView) itemView.findViewById(R.id.car_model_name);
            carColor = (TextView) itemView.findViewById(R.id.car_color);
            carPlateNumber = (TextView) itemView.findViewById(R.id.car_plate_Number);
            carIcon = (ImageView) itemView.findViewById(R.id.car_icon);
            checkedIcon = (ImageView) itemView.findViewById(R.id.checked);
            packageExist = (LinearLayout) itemView.findViewById(R.id.package_exist);
        }


    }

    public List<SelectedCarListArray> getSelectedCars() {
        List<SelectedCarListArray> list = new ArrayList<>();


        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).isSelected()) {
                SelectedCarListArray selectedCar = new SelectedCarListArray();
                selectedCar.setCarModel(carList.get(i).getCarBrand() + "," + " " + carList.get(i).getCarModel());
                selectedCar.setCarParkingNumber(carList.get(i).getParkingNumber());
                selectedCar.setColor(carList.get(i).getCarColor());
                selectedCar.setCarPlateNumber(carList.get(i).getPlateNumber());
                selectedCar.setType(carList.get(i).getCarType());
                selectedCar.setDays(getDaysList());
                selectedCar.setFrequency(getFrequency());
                selectedCar.setResponse(null);
                selectedCar.setExteriorSelected(true);
                selectedCar.setCarId(carList.get(i).getCarId());
                list.add(selectedCar);
            }
        }

        return list;
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

    List<FrequencyList> getFrequency() {
        List<FrequencyList> list = new ArrayList<>();
        list.add(new FrequencyList("Twice a week", 2));
        list.add(new FrequencyList("Thrice a week", 3));
        list.add(new FrequencyList("Daily", 6));
        return list;


    }
}
