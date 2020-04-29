package com.gogreen.main.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gogreen.main.Fragment.MonthlyPackageFragment;
import com.gogreen.main.Interfaces.CarDataRepository;
import com.gogreen.main.R;
import com.gogreen.main.Screens.BookServiceFormActivity;

import java.util.List;

public class DaylistAdapter extends RecyclerView.Adapter<DaylistAdapter.ViewHolder> {


    Context mcontext;
    int max;

    public DaylistAdapter(Context mContext, int maxCount) {
        this.mcontext = mContext;
        max = maxCount;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dayslist, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getDays().get(position).isSelected()) {
            holder.day.setImageResource(R.mipmap.select_check_done);
        } else {
            holder.day.setImageResource(R.drawable.unchecked_box_drawable);
        }


        holder.day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getDays().get(position).isSelected()) {
                    holder.day.setImageResource(R.drawable.unchecked_box_drawable);
                    CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getDays().get(position).setSelected(false);
                } else {
                    if (getMaxCount() < max) {
                        holder.day.setImageResource(R.mipmap.select_check_done);

                        CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getDays().get(position).setSelected(true);
                    }
                    if(getMaxCount() == max) {
                        CarDataRepository.getInstance().refreshCarSelection();

                    }

                }


            }
        });

        holder.dayName.setText(CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getDays().get(position).getName());
    }

    @Override
    public int getItemCount() {
        return CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getDays().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dayName;
        ImageView day;

        ViewHolder(View itemView) {
            super(itemView);
            day = (ImageView) itemView.findViewById(R.id.daysSelected);
            dayName = (TextView) itemView.findViewById(R.id.dayname);

        }
    }

    public int getMaxCount() {
        int count = 0;
        for (int i = 0; i < CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getDays().size() ; i++) {
            if (CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getDays().get(i).isSelected()) {
                count = count + 1;

            }
        }

        Log.d("ALIEN",String.valueOf(count));
        return count;
    }
}
