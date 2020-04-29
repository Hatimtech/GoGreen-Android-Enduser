package com.gogreen.main.Fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.gogreen.main.Adapter.LocalityListAdapter;
import com.gogreen.main.Model.SelectLocality.REQUEST.CityLocalityRequest;
import com.gogreen.main.Model.SelectLocality.RESPONSE.CityLocalityResponseResult;
import com.gogreen.main.Model.SelectLocality.RESPONSE.LocalityResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Screens.CityActivity;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SelectLocalityFragment extends Fragment implements LocalityListAdapter.LocalityAdapterCallback {

    private RecyclerView cityListView;
    private List<CityLocalityResponseResult> localityList;
    public static LocalityListAdapter localityListAdapter;
    private TextView CityName;
    private SelectLocalityCallback selectLocalityCallback;
    APIUtility apiUtility;
    private SwipeRefreshLayout swipeView;
    CityActivity activity = (CityActivity) getActivity();
    boolean isAttached = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.city_locatiy_selection_fragment, container, false);
        swipeView = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        cityListView = (RecyclerView) view.findViewById(R.id.citylist);
        ((CityActivity) getActivity()).setTitle(" Locations in" + " " + getArguments().getString("name"));
        apiUtility = new APIUtility(getActivity());
        localityList = (List<CityLocalityResponseResult>) getArguments().getSerializable("list");
        InitViews();
        return view;
    }


    public void InitViews() {
        String reciverName = getArguments().getString("name");
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cityListView.setLayoutManager(layoutManager);
        localityListAdapter = new LocalityListAdapter(localityList, this);
        cityListView.setAdapter(localityListAdapter);
        }


    @Override
    public void itemClick(CityLocalityResponseResult result, int pos) {
        selectLocalityCallback.onFragmentItemClickLocality(result, pos);
    }


    public interface SelectLocalityCallback {
        void attachSelectLocalityFragment();

        void detachSelectLocatityFragment();

        void onFragmentItemClickLocality(CityLocalityResponseResult result, int pos);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isAttached = true;
        this.selectLocalityCallback = ((SelectLocalityCallback) (context));
        selectLocalityCallback.attachSelectLocalityFragment();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
        this.selectLocalityCallback = ((SelectLocalityCallback) (getActivity()));
        selectLocalityCallback.detachSelectLocatityFragment();
    }
}

