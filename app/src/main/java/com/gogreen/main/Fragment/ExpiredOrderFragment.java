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

import com.gogreen.main.Adapter.ActiveOrderAdapter;
import com.gogreen.main.Adapter.ExpiredOrderAdapter;
import com.gogreen.main.Model.Orders.REQUEST.OrderRequest;
import com.gogreen.main.Model.Orders.RESPONSE.ExpiredOrder;
import com.gogreen.main.Model.Orders.RESPONSE.OrderResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Screens.BookServiceActivity;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;

import java.util.ArrayList;
import java.util.List;

public class ExpiredOrderFragment extends Fragment {

    List<ExpiredOrder> expiredOrderList;
    RecyclerView expiredOrderListView;
    ExpiredOrderAdapter adapter;
    APIUtility apiUtility;
    LinearLayout cartEmpty;
    SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.active_expired_order_fragment, container, false);
        expiredOrderListView = (RecyclerView) view.findViewById(R.id.active_order_list);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        cartEmpty = (LinearLayout) view.findViewById(R.id.cartEmpty);
        apiUtility = new APIUtility(getActivity());
        InitView();

        return view;
    }

    private void InitView() {
        expiredOrderList = new ArrayList<>();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        expiredOrderListView.setLayoutManager(layoutManager);
        adapter = new ExpiredOrderAdapter(expiredOrderList, this);
        expiredOrderListView.setAdapter(adapter);
        getMyActiveOrder(true);
        refreshList();

    }

    void getMyActiveOrder(boolean isProgress) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setAppKey(Constants.APP_KEY);
        orderRequest.setMethod("get_orders");
        orderRequest.setUserId(Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.USERID));

        apiUtility.getMyOrder(getActivity(), orderRequest, isProgress, new APIUtility.APIResponseListener<OrderResponseWrapper>() {
            @Override
            public void onReceiveResponse(OrderResponseWrapper response) {

                refreshLayout.setRefreshing(false);
                if (response != null) {
                    if (response.getResponse().getResult().getExpiredOrders().size() > 0) {
                        cartEmpty.setVisibility(View.GONE);
                        expiredOrderListView.setVisibility(View.VISIBLE);
                        expiredOrderList.clear();
                        expiredOrderList.addAll(response.getResponse().getResult().getExpiredOrders());
                        adapter.notifyDataSetChanged();
                    } else {
                        cartEmpty.setVisibility(View.VISIBLE);
                        expiredOrderListView.setVisibility(View.GONE);
                        }
                }
            }

            @Override
            public void onResponseFailed() {
                refreshLayout.setRefreshing(false);
                cartEmpty.setVisibility(View.VISIBLE);
                expiredOrderListView.setVisibility(View.GONE);
                CommonUtils.alert(getActivity(), getString(R.string.VolleyError));

            }

            @Override
            public void onStatusFalse(OrderResponseWrapper response) {
                refreshLayout.setRefreshing(false);
                CommonUtils.alert(getActivity(), response.getResponse().getMessage());
            }
        });


    }

    void refreshList() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                if (CommonUtils.isNetworkAvailable(getActivity())) {
                    getMyActiveOrder(false);
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

}
