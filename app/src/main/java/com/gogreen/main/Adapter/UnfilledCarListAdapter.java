package com.gogreen.main.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gogreen.main.Interfaces.CarDataRepository;
import com.gogreen.main.R;

import java.util.List;

public class UnfilledCarListAdapter extends RecyclerView.Adapter<UnfilledCarListAdapter.ViewHolder>  {


    private List<String> cityList;
   Context mContext;

    public UnfilledCarListAdapter(List<String> cityList, Context mContext) {
        this.mContext = mContext;
        this.cityList = cityList;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.unfilled_car_list, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.list.setText(cityList.get(position));


    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView list;
        RelativeLayout citylistview;


        ViewHolder(View itemView) {
            super(itemView);
            list = (TextView) itemView.findViewById(R.id.unfilledcarList);

        }


    }






}
