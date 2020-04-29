package com.gogreen.main.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gogreen.main.Fragment.SelectStreetFragment;
import com.gogreen.main.Model.SelectStreet.RESPONSE.CityStreetResponseResult;
import com.gogreen.main.R;


import java.util.ArrayList;
import java.util.List;



public class StreetListAdapter extends RecyclerView.Adapter<StreetListAdapter.ViewHolder> implements Filterable {

    private SelectStreetFragment mContext;
    ValueFilter valueFilter;
    private List<CityStreetResponseResult> streetList;
    private List<CityStreetResponseResult> mFilterList;
    StreetAdapterCallback streetAdapterCallback;


    public StreetListAdapter(List<CityStreetResponseResult> streetList, SelectStreetFragment mContext) {
        this.mContext = mContext;
        this.streetList = streetList;
        this.mFilterList = streetList;
        this.streetAdapterCallback = (StreetAdapterCallback) (mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_locality_street_list, parent, false));
    }


    @Override
    public void onBindViewHolder(final StreetListAdapter.ViewHolder holder, final int position) {

        holder.list.setText(streetList.get(holder.getAdapterPosition()).getName());
        holder.citylistview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                streetAdapterCallback.itemClick(streetList.get(position), position);


            }
        });
    }



    @Override
    public int getItemCount() {
        return streetList.size();
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

  public   interface StreetAdapterCallback {
        void itemClick(CityStreetResponseResult result,int Pos);
    }


    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            CityStreetResponseResult filterHash;

            if (constraint != null && constraint.length() > 0) {
                List<CityStreetResponseResult> filterList = new ArrayList<CityStreetResponseResult>();

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
            streetList = (ArrayList<CityStreetResponseResult>) results.values;
            notifyDataSetChanged();
        }
    }





}


