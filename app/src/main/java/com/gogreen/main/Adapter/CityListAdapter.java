package com.gogreen.main.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gogreen.main.Fragment.SelectCityFragment;
import com.gogreen.main.Model.SelectCity.RESPONSE.CityResponseResult;
import com.gogreen.main.R;

import java.util.ArrayList;
import java.util.List;


public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> implements Filterable {

    private SelectCityFragment mContext;
    private List<CityResponseResult> cityList;
    private List<CityResponseResult> mFilterList;
    private CityAdapterCallback callback;
    ValueFilter valueFilter;

    public CityListAdapter(List<CityResponseResult> cityList, SelectCityFragment mContext) {
        this.mContext = mContext;
        callback = (CityAdapterCallback) (mContext);
        this.cityList = cityList;
        this.mFilterList = cityList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_city_list, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.list.setText(cityList.get(holder.getAdapterPosition()).getName());
        holder.serving_number.setText(cityList.get(holder.getAdapterPosition()).getServingNumber());
        holder.citylistview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callback.itemClick(cityList.get(position), position);


            }
        });

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView list, serving_number;
        RelativeLayout citylistview;


        ViewHolder(View itemView) {
            super(itemView);
            list = (TextView) itemView.findViewById(R.id.city_name);
            serving_number = (TextView) itemView.findViewById(R.id.serving_no);
            citylistview = (RelativeLayout) itemView.findViewById(R.id.citylist_view);
        }


    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            CityResponseResult filterHash;

            if (constraint != null && constraint.length() > 0) {
                List<CityResponseResult> filterList = new ArrayList<CityResponseResult>();

                for (int i = 0; i < mFilterList.size(); i++) {
                    if (mFilterList.get(i).getName().toLowerCase().contains(constraint)) {
                        filterHash = mFilterList.get(i);
                        filterList.add(filterHash);
                    }

                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mFilterList.size();
                results.values = mFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            cityList = (ArrayList<CityResponseResult>) results.values;
            notifyDataSetChanged();
        }
    }

    public interface CityAdapterCallback {
        void itemClick(CityResponseResult result, int pos);
    }


}

