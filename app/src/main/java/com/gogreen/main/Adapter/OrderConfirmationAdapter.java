package com.gogreen.main.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gogreen.main.Interfaces.SelectedCarListArray;
import com.gogreen.main.R;

import java.util.List;

public class OrderConfirmationAdapter extends RecyclerView.Adapter<OrderConfirmationAdapter.ViewHolder> {
    private List<SelectedCarListArray> carList;
    Context context;
    private boolean isMore = false;

    public OrderConfirmationAdapter(List<SelectedCarListArray> carList, Context context) {
        this.carList = carList;
        this.context = context;
    }

    @Override
    public OrderConfirmationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_confirmation_list, parent, false));

    }

    @Override
    public void onBindViewHolder(final OrderConfirmationAdapter.ViewHolder holder,final int position) {

        holder.carModelName.setText(carList.get(position).getCarModel());
        holder.carColor.setText(carList.get(position).getColor());
        holder.carColor.setText(carList.get(position).getColor());
        holder.carType.setText(carList.get(position).getType());
        holder.carServicingtype.setText(carList.get(position).getServicingType());
        holder.money.setText("AED" + " " + carList.get(position).getTotalAmount());
        String days = "";
        for (int j = 0; j < carList.get(position).getDays().size(); j++) {
            if (carList.get(position).getDays().get(j).isSelected()) {

                days = days + carList.get(position).getDays().get(j).getName() + " ";
            }
        }

        if (carList.get(position).getCarPackage().equals("monthly"))
            holder.days.setText(days);

        else
            holder.days.setText(carList.get(position).getDate());


        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carList.get(position).isExpanded()) {
                    carList.get(position).setExpanded(false);
                    notifyItemChanged(position);
                } else {
                    carList.get(position).setExpanded(true);
                    notifyItemChanged(position);

                }
            }
        });



                if (carList.get(position).isExpanded()){
                    holder.carTypeLayout.setVisibility(View.VISIBLE);
                    holder.calenderLayout.setVisibility(View.VISIBLE);
                    holder.viewMore.setText(R.string.viewLess);


                }else{
                    holder.carTypeLayout.setVisibility(View.GONE);
                    holder.calenderLayout.setVisibility(View.GONE);
                    holder.viewMore.setText(R.string.viewMore);

                }


    }


    @Override
    public int getItemCount() {
        return carList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView carType, carModelName, carColor, days, carServicingtype, money, viewMore;
        LinearLayout orderviewList, calenderLayout, carTypeLayout;


        ViewHolder(View itemView) {
            super(itemView);
            calenderLayout = (LinearLayout) itemView.findViewById(R.id.calender_layout);
            carTypeLayout = (LinearLayout) itemView.findViewById(R.id.car_type_layout);
            orderviewList = (LinearLayout) itemView.findViewById(R.id.confirm_order_list);
            carModelName = (TextView) itemView.findViewById(R.id.car_brand_name);
            carType = (TextView) itemView.findViewById(R.id.car_type_name);
            carServicingtype = (TextView) itemView.findViewById(R.id.mentain_type);
            carColor = (TextView) itemView.findViewById(R.id.car_color_name);
            days = (TextView) itemView.findViewById(R.id.calendr);
            money = (TextView) itemView.findViewById(R.id.payment);
            viewMore = (TextView) itemView.findViewById(R.id.view_more);

        }


    }
}