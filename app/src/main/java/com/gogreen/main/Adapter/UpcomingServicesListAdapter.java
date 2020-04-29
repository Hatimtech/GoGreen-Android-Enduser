package com.gogreen.main.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gogreen.main.Fragment.MainFragment;
import com.gogreen.main.Model.AddUpcoming_Renewal_Service.RESPONSE.UpcomingServices;
import com.gogreen.main.R;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;

import java.util.List;

public class UpcomingServicesListAdapter extends RecyclerView.Adapter<UpcomingServicesListAdapter.ViewHolder> {


    private MainFragment mContext;
    private List<UpcomingServices> upcoming_Servicing_list;


    public UpcomingServicesListAdapter(List<UpcomingServices> upcoming_Servicing_list, MainFragment mContext) {
        this.mContext = mContext;
        this.upcoming_Servicing_list = upcoming_Servicing_list;

    }


    @Override
    public UpcomingServicesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UpcomingServicesListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_services_list, parent, false));

    }

    @Override
    public void onBindViewHolder(UpcomingServicesListAdapter.ViewHolder holder, int position) {
      holder.userPhone.setText(upcoming_Servicing_list.get(position).getExpiry_date());
      holder.userName.setText(upcoming_Servicing_list.get(position).getReg_no());
      holder.parkingNumber.setText(upcoming_Servicing_list.get(position).getCarType());
      if(upcoming_Servicing_list.size()>1)
      holder.totalNumber.setText(String.valueOf(position+1)+" / "+ String.valueOf(upcoming_Servicing_list.size()));
      holder.carModel.setText(upcoming_Servicing_list.get(position).getBrand() + ", " + upcoming_Servicing_list.get(position).getModel());

    }

    @Override
    public int getItemCount() {
        return upcoming_Servicing_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView carModel,userName,userPhone,parkingNumber,totalNumber;



        ViewHolder(View itemView) {
            super(itemView);
            totalNumber=(TextView)itemView.findViewById(R.id.totalNumber);
            carModel=(TextView)itemView.findViewById(R.id.car_name);
            userName=(TextView)itemView.findViewById(R.id.user_name);
            userPhone=(TextView)itemView.findViewById(R.id.user_mobile);
            parkingNumber=(TextView)itemView.findViewById(R.id.parking_bay);

            }

    }
}
