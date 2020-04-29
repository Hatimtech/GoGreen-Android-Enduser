package com.gogreen.main.Fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.gogreen.main.Adapter.CityListAdapter;
import com.gogreen.main.Model.SelectCity.REQUEST.CityRequest;
import com.gogreen.main.Model.SelectCity.RESPONSE.CityResponseResult;
import com.gogreen.main.Model.SelectCity.RESPONSE.CityResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Screens.CityActivity;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;

import java.util.ArrayList;
import java.util.List;


public class SelectCityFragment extends Fragment implements CityListAdapter.CityAdapterCallback {
    private static final String TAG = "SelectCityFragment";

    private RecyclerView cityListView;
    private List<CityResponseResult> cityList;
    public static CityListAdapter cityListAdapter;
    SelectCityCallback callback;
    APIUtility apiUtility;
    private SwipeRefreshLayout swipeView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.city_selection_fragment, container, false);
        cityListView = (RecyclerView) view.findViewById(R.id.citylist);
        swipeView = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        ((CityActivity) getActivity()).setTitle(getString(R.string.select_city));
        apiUtility = new APIUtility(getActivity());
        InitViews();
        return view;
    }


    public void InitViews() {

        cityList = new ArrayList<>();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cityListView.setLayoutManager(linearLayoutManager);
        cityListAdapter = new CityListAdapter(cityList, this);
        cityListView.setAdapter(cityListAdapter);
        cityList(true);
        refreshList();
    }


    void cityList(boolean isProgress) {

        CityRequest cityRequest = new CityRequest();
        cityRequest.setApp_key(Constants.APP_KEY);
        cityRequest.setMethod("get_city");

        apiUtility.getCity(getActivity(), cityRequest, isProgress, new APIUtility.APIResponseListener<CityResponseWrapper>() {
            @Override
            public void onReceiveResponse(CityResponseWrapper response) {
                swipeView.setRefreshing(false);
                if (response != null) {

                    if (response.getResponse().getResult().size() > 0) {
                        cityList.clear();
                        cityList.addAll(response.getResponse().getResult());
                        cityListAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onResponseFailed() {
                swipeView.setRefreshing(false);
                CommonUtils.alert(getActivity(), getString(R.string.VolleyError));

            }

            @Override
            public void onStatusFalse(CityResponseWrapper response) {
                swipeView.setRefreshing(false);
                CommonUtils.alert(getActivity(), response.getResponse().getMessage());
            }
        });
    }


    @Override
    public void itemClick(CityResponseResult result, int pos) {
        callback.onFragmentItemClick(result, pos);
    }

    public interface SelectCityCallback {
        void onFragmentItemClick(CityResponseResult result, int pos);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (SelectCityCallback) (context);
    }

    void refreshList() {

        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                if (CommonUtils.isNetworkAvailable(getActivity())) {
                    cityList(false);
                } else {
                    swipeView.setRefreshing(false);
                    CommonUtils.displayNetworkAlert(getActivity(), false);
                }
            }
        });

        swipeView.setColorSchemeColors( Color.GREEN, Color.BLUE, Color.RED, Color.CYAN,Color.GRAY);
        swipeView.setDistanceToTriggerSync(10);// in dips
        swipeView.setSize(SwipeRefreshLayout.DEFAULT);

    }

}






