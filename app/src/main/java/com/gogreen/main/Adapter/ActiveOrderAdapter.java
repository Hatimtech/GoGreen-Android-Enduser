package com.gogreen.main.Adapter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gogreen.main.Fragment.ActiveMyOrderFragment;
import com.gogreen.main.Model.Orders.RESPONSE.ActiveOrder;
import com.gogreen.main.Model.Orders.RESPONSE.ExpiredOrder;
import com.gogreen.main.R;
import com.gogreen.main.Screens.OrderDetailActivity;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;

import java.util.List;

public class ActiveOrderAdapter extends RecyclerView.Adapter<ActiveOrderAdapter.ViewHolder> {


    private List<ActiveOrder> activeOrderList;
    ActiveMyOrderFragment mContext;

    public ActiveOrderAdapter(List<ActiveOrder> activeOrderList, ActiveMyOrderFragment mContext) {
        this.mContext = mContext;
        this.activeOrderList = activeOrderList;

    }


    @Override
    public ActiveOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActiveOrderAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.active_order_list, parent, false));
    }


    @Override
    public void onBindViewHolder(final ActiveOrderAdapter.ViewHolder holder, final int position) {

        holder.carModel.setText(activeOrderList.get(position).getBrand() + ", " + activeOrderList.get(position).getModel());
        holder.orderID.setText(activeOrderList.get(position).getOrdersId());
        holder.carParkingNumber.setText(activeOrderList.get(position).getReg_no());
        if (activeOrderList.get(position).getServices().equals("2")) {
            holder.servicingType.setText("Exterior");
        } else {
            holder.servicingType.setText("Exterior, Interior");
        }
        holder.expireDate.setText(activeOrderList.get(position).getExpiry_date());

        holder.activeOrderlistview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getContext(), OrderDetailActivity.class);
                intent.putExtra("package","1");
                intent.putExtra("carModel", activeOrderList.get(position).getBrand() + ", " + activeOrderList.get(position).getModel());
                intent.putExtra("carId", activeOrderList.get(position).getCarID());
                intent.putExtra("parking",activeOrderList.get(position).getReg_no());
                intent.putExtra("orderID",activeOrderList.get(position).getId());
                Preferences.setPreference(mContext.getContext(), PrefEntity.ORDERID,activeOrderList.get(position).getPayment_key());
                Preferences.setPreference(mContext.getContext(), PrefEntity.CARID,activeOrderList.get(position).getCarID());

                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return activeOrderList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView carModel, carParkingNumber, servicingType, expireDate, orderID;
        RelativeLayout activeOrderlistview;


        ViewHolder(View itemView) {
            super(itemView);
            orderID = (TextView) itemView.findViewById(R.id.car_orderId);
            carModel = (TextView) itemView.findViewById(R.id.car_model);
            carParkingNumber = (TextView) itemView.findViewById(R.id.car_parking_No);
            servicingType = (TextView) itemView.findViewById(R.id.maintainType);
            expireDate = (TextView) itemView.findViewById(R.id.date);
            activeOrderlistview = (RelativeLayout) itemView.findViewById(R.id.active_order_list);

        }
    }


}



