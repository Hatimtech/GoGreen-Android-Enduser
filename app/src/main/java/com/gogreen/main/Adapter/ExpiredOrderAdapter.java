package com.gogreen.main.Adapter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gogreen.main.Fragment.ExpiredOrderFragment;
import com.gogreen.main.Model.Orders.RESPONSE.ExpiredOrder;
import com.gogreen.main.R;
import com.gogreen.main.Screens.OrderDetailActivity;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;

import java.util.List;

public class ExpiredOrderAdapter extends RecyclerView.Adapter<ExpiredOrderAdapter.ViewHolder> {


    private List<ExpiredOrder> expiredOrderlist;
    ExpiredOrderFragment mContext;

    public ExpiredOrderAdapter(List<ExpiredOrder> expiredOrderlist, ExpiredOrderFragment mContext) {
        this.mContext = mContext;
        this.expiredOrderlist = expiredOrderlist;

    }


    @Override
    public ExpiredOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExpiredOrderAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.expired_order_list, parent, false));
    }


    @Override
    public void onBindViewHolder(final ExpiredOrderAdapter.ViewHolder holder, final int position) {

        holder.carModel.setText(expiredOrderlist.get(position).getBrand() + ", " + expiredOrderlist.get(position).getModel());
        holder.orderID.setText(expiredOrderlist.get(position).getOrdersId());
        holder.carParkingNumber.setText(expiredOrderlist.get(position).getReg_no());
        if (expiredOrderlist.get(position).getServices().equals("2")) {
            holder.servicingType.setText("Exterior");
        } else {
            holder.servicingType.setText("Exterior, Interior");
        }

        holder.expiredListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getContext(), OrderDetailActivity.class);
                intent.putExtra("package","0");
                intent.putExtra("carModel", expiredOrderlist.get(position).getBrand() + ", " + expiredOrderlist.get(position).getModel());
                intent.putExtra("carId", expiredOrderlist.get(position).getCarID());
                intent.putExtra("parking",expiredOrderlist.get(position).getReg_no());
                intent.putExtra("orderID",expiredOrderlist.get(position).getOrdersId());
                Preferences.setPreference(mContext.getContext(), PrefEntity.ORDERID,expiredOrderlist.get(position).getPayment_key());
                Preferences.setPreference(mContext.getContext(), PrefEntity.CARID,expiredOrderlist.get(position).getCarID());

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return expiredOrderlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView carModel, carParkingNumber, servicingType,orderID;
        RelativeLayout expiredListView;


        ViewHolder(View itemView) {
            super(itemView);
            carModel = (TextView) itemView.findViewById(R.id.car_model);
            carParkingNumber = (TextView) itemView.findViewById(R.id.car_parking_No);
            servicingType = (TextView) itemView.findViewById(R.id.maintainType);
            expiredListView = (RelativeLayout) itemView.findViewById(R.id.confirm_order_list);
            orderID=(TextView)itemView.findViewById(R.id.car_orderId);

        }
    }

}
