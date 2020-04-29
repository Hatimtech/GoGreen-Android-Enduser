package com.gogreen.main.Adapter;


import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gogreen.main.Fragment.SelectLocalityFragment;
import com.gogreen.main.Model.SelectLocality.RESPONSE.CityLocalityResponseResult;
import com.gogreen.main.R;

import java.util.ArrayList;
import java.util.List;

public class LocalityListAdapter extends RecyclerView.Adapter<LocalityListAdapter.ViewHolder> implements Filterable {

    private SelectLocalityFragment mContext;
    private List<CityLocalityResponseResult> localityList;
    private List<CityLocalityResponseResult> mFilterList;
    ValueFilter valueFilter;
    private LocalityAdapterCallback callback;


    public LocalityListAdapter(List<CityLocalityResponseResult> localityList, SelectLocalityFragment mContext) {
        this.mContext = mContext;
        this.localityList = localityList;
        this.mFilterList = localityList;
        this.callback = (LocalityAdapterCallback) (mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocalityListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_locality_street_list, parent, false));
    }


    @Override
    public void onBindViewHolder(final LocalityListAdapter.ViewHolder holder, final int position) {

        holder.list.setText(localityList.get(holder.getAdapterPosition()).getName());
        holder.citylistview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callback.itemClick(localityList.get(position), position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return localityList.size();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView list;
        RelativeLayout citylistview;


        ViewHolder(View itemView) {
            super(itemView);
            list = (TextView) itemView.findViewById(R.id.city_name);
            citylistview = (RelativeLayout) itemView.findViewById(R.id.citylist_view);
        }


    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            CityLocalityResponseResult filterHash;

            if (constraint != null && constraint.length() > 0) {
                List<CityLocalityResponseResult> filterList = new ArrayList<CityLocalityResponseResult>();

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
            localityList = (ArrayList<CityLocalityResponseResult>) results.values;
            notifyDataSetChanged();
        }
    }

    public interface LocalityAdapterCallback {
        void itemClick(CityLocalityResponseResult result, int pos);
    }


}



