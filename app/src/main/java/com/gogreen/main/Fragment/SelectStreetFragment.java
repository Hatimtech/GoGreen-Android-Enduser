package com.gogreen.main.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gogreen.main.Adapter.StreetListAdapter;
import com.gogreen.main.Model.SelectStreet.REQUEST.CityStreetRequest;
import com.gogreen.main.Model.SelectStreet.RESPONSE.CityStreetResponseResult;
import com.gogreen.main.Model.SelectStreet.RESPONSE.StreetResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Screens.CityActivity;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Volley.APIUtility;

import java.util.ArrayList;
import java.util.List;


public class SelectStreetFragment extends Fragment implements StreetListAdapter.StreetAdapterCallback{
    private RecyclerView cityListView;

    private List<CityStreetResponseResult> streetList;
    public static StreetListAdapter streetListAdapter;
    APIUtility apiUtility;
    private TextView cityName, localityName;
    private SelectStreet selectStreet;
    boolean isAttached = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.city_street_fragment, container, false);
        cityListView = (RecyclerView) view.findViewById(R.id.citylist);
        ((CityActivity) getActivity()).setTitle(" Streets in" + " " + getArguments().getString("name"));
        apiUtility = new APIUtility(getActivity());
        InitViews();
        return view;
    }


    public void InitViews() {
        streetList = (List<CityStreetResponseResult>) getArguments().getSerializable("list");
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cityListView.setLayoutManager(linearLayoutManager);
        streetListAdapter = new StreetListAdapter(streetList, this);
        cityListView.setAdapter(streetListAdapter);
        streetListAdapter.notifyDataSetChanged();


    }

    @Override
    public void itemClick(CityStreetResponseResult result, int Pos) {
             selectStreet.onFragmentItemClickStreet(result,Pos);
    }


    public interface SelectStreet {
        void attachSelectStreetFragment();
        void detachSelectStreetFragment();
        void onFragmentItemClickStreet(CityStreetResponseResult  result, int pos);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isAttached = true;
        this.selectStreet = ((SelectStreet) (context));
        selectStreet.attachSelectStreetFragment();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
        this.selectStreet = ((SelectStreet) (getActivity()));
        selectStreet.detachSelectStreetFragment();
    }
}
