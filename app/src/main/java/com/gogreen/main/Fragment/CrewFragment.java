package com.gogreen.main.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gogreen.main.Adapter.CrewDetailAdapter;
import com.gogreen.main.Model.CrewDetail.REQUEST.CrewDetailRequest;
import com.gogreen.main.Model.CrewDetail.RESPONSE.CrewDetailResponseResult;
import com.gogreen.main.Model.CrewDetail.RESPONSE.CrewDetailResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;

import java.util.ArrayList;
import java.util.List;

public class CrewFragment extends Fragment {

    List<CrewDetailResponseResult> crewList;
    private RecyclerView crewListView;
    CrewDetailAdapter adapter;
    LinearLayout noCrew;
    SwipeRefreshLayout refreshLayout;
    APIUtility apiUtility;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.active_expired_order_fragment, container, false);
        crewListView = (RecyclerView) view.findViewById(R.id.active_order_list);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        noCrew =(LinearLayout)view.findViewById(R.id.cartEmpty);
        apiUtility = new APIUtility(getActivity());
        InitView();
        return view;
    }

    private void InitView() {
        crewList=new ArrayList<>();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        crewListView.setLayoutManager(layoutManager);
        adapter = new CrewDetailAdapter(crewList, this);
        crewListView.setAdapter(adapter);
        getCrewDetail(true);
        refreshList();
    }

    void refreshList() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                if (CommonUtils.isNetworkAvailable(getActivity())) {
                    getCrewDetail(false);
                } else {
                    refreshLayout.setRefreshing(false);
                    CommonUtils.displayNetworkAlert(getActivity(), false);
                }
            }
        });

        refreshLayout.setColorSchemeColors(Color.GREEN, Color.BLUE, Color.RED, Color.CYAN, Color.GRAY);
        refreshLayout.setDistanceToTriggerSync(100);// in dips
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
    }

    void getCrewDetail(boolean isProgress) {
        CrewDetailRequest crewDetailRequest = new CrewDetailRequest();
        crewDetailRequest.setApp_key(Constants.APP_KEY);
        crewDetailRequest.setMethod("get_crew_detail");
        crewDetailRequest.setUserID(Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.USERID));
        crewDetailRequest.setCar_id(Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.CARID));
        crewDetailRequest.setOrderID(Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.ORDERID));
        apiUtility.getCrewDetail(getActivity(), crewDetailRequest, isProgress, new APIUtility.APIResponseListener<CrewDetailResponseWrapper>() {
            @Override
            public void onReceiveResponse(CrewDetailResponseWrapper response) {
                refreshLayout.setRefreshing(false);
                if (response != null) {
                    if (response.getResponse().getResult().size() > 0) {
                        crewListView.setVisibility(View.VISIBLE);
                        noCrew.setVisibility(View.GONE);
                        crewList.clear();
                        crewList.addAll(response.getResponse().getResult());
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onResponseFailed() {
                refreshLayout.setRefreshing(false);
                CommonUtils.alert(getActivity(), getString(R.string.VolleyError));
                crewListView.setVisibility(View.GONE);
                noCrew.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStatusFalse(CrewDetailResponseWrapper response) {
                refreshLayout.setRefreshing(false);
                crewListView.setVisibility(View.GONE);
                noCrew.setVisibility(View.VISIBLE);


            }
        });
    }
}