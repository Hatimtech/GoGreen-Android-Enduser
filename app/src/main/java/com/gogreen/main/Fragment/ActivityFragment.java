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

import com.gogreen.main.Adapter.ActivityOrderDetailAdapter;
import com.gogreen.main.Model.CarActivityDetail.REQUEST.CarActivityDetailRequest;
import com.gogreen.main.Model.CarActivityDetail.RESPONSE.CarActivityDetailResponseResult;
import com.gogreen.main.Model.CarActivityDetail.RESPONSE.CarActivityDetailResponseWrapper;
import com.gogreen.main.Model.RateCleaner.REQUEST.RateCleanerRequest;
import com.gogreen.main.Model.RateCleaner.RESPONSE.RateCleanerResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;

import java.util.ArrayList;
import java.util.List;

public class ActivityFragment extends Fragment {
    private RecyclerView taskListView;
    ActivityOrderDetailAdapter adapter;
    List<CarActivityDetailResponseResult> taskList;
    SwipeRefreshLayout refreshLayout;
    LinearLayout noActivity;
    APIUtility apiUtility;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.active_expired_order_fragment, container, false);
        taskListView = (RecyclerView) view.findViewById(R.id.active_order_list);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        noActivity=(LinearLayout)view.findViewById(R.id.cartEmpty);
        apiUtility = new APIUtility(getActivity());
        InitView();
        getActivityDetail(true);
        return view;
    }

    private void InitView() {
        taskList = new ArrayList<>();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        taskListView.setLayoutManager(layoutManager);
        adapter = new ActivityOrderDetailAdapter(taskList, this);
        taskListView.setAdapter(adapter);
        refreshList();
    }

    void refreshList() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                if (CommonUtils.isNetworkAvailable(getActivity())) {
                    getActivityDetail(false);
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


    void getActivityDetail(boolean isProgress) {
        CarActivityDetailRequest activityDetailRequest = new CarActivityDetailRequest();
        activityDetailRequest.setAppKey(Constants.APP_KEY);
        activityDetailRequest.setCarID(Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.CARID));
        activityDetailRequest.setMethod("get_car_activity");
        activityDetailRequest.setOrder_id(Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.ORDERID));

        apiUtility.getCarActivityDetail(getActivity(), activityDetailRequest, isProgress, new APIUtility.APIResponseListener<CarActivityDetailResponseWrapper>() {
            @Override
            public void onReceiveResponse(CarActivityDetailResponseWrapper response) {
                refreshLayout.setRefreshing(false);
                if (response != null) {
                    noActivity.setVisibility(View.GONE);
                    taskListView.setVisibility(View.VISIBLE);
                    taskList.clear();
                    taskList.addAll(response.getResponse().getResult());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onResponseFailed() {
                refreshLayout.setRefreshing(false);
                noActivity.setVisibility(View.VISIBLE);
                taskListView.setVisibility(View.GONE);
                CommonUtils.alert(getActivity(), getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(CarActivityDetailResponseWrapper response) {
                refreshLayout.setRefreshing(false);
                noActivity.setVisibility(View.VISIBLE);
               taskListView.setVisibility(View.GONE);

            }
        });
    }
}
