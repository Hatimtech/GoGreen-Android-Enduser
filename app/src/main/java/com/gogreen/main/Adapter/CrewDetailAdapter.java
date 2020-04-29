package com.gogreen.main.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gogreen.main.Fragment.ActivityFragment;
import com.gogreen.main.Fragment.CrewFragment;
import com.gogreen.main.Model.CrewDetail.RESPONSE.CrewDetailResponseResult;
import com.gogreen.main.R;

import java.util.List;

public class CrewDetailAdapter extends RecyclerView.Adapter<CrewDetailAdapter.ViewHolder> {


    private List<CrewDetailResponseResult> crewList;
    CrewFragment mContext;

    public CrewDetailAdapter(List<CrewDetailResponseResult> crewList, CrewFragment mContext) {
        this.mContext = mContext;
        this.crewList = crewList;

    }


    @Override
    public CrewDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CrewDetailAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.crew_list, parent, false));
    }


    @Override
    public void onBindViewHolder(final CrewDetailAdapter.ViewHolder holder, final int position) {
         holder.cleanerName.setText(crewList.get(position).getClName()+ " " +crewList.get(position).getClLastName());
         try {
             float rate = Float.valueOf(crewList.get(position).getRating())/Integer.parseInt(crewList.get(position).getCount_who_rated());
            if(crewList.get(position).getRating().equals("0")){
                 holder.rating.setText("0.0 star Rating");
             }else {
                 holder.rating.setText(String.valueOf(rate) + "  star  Rating");
             }
         }catch (Exception e){
             holder.rating.setText("0.0 star Rating");
         }

         holder.clrNumber.setText(crewList.get(position).getClPhone());
         holder.servicingTime.setText(crewList.get(position).getServicingTime());

    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cleanerName, rating, servicingTime, clrNumber;


        ViewHolder(View itemView) {
            super(itemView);
            cleanerName = (TextView) itemView.findViewById(R.id.crew_name);
            rating = (TextView) itemView.findViewById(R.id.rating);
            servicingTime = (TextView) itemView.findViewById(R.id.servingTime);
            clrNumber = (TextView) itemView.findViewById(R.id.clr_phone);


        }
    }
}
