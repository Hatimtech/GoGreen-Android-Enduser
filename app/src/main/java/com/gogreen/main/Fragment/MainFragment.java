package com.gogreen.main.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gogreen.main.Adapter.CouponAdapter;
import com.gogreen.main.Adapter.PendingListAdapter;
import com.gogreen.main.Adapter.UpcomingServicesListAdapter;
import com.gogreen.main.Model.AddUpcoming_Renewal_Service.REQUEST.AddUpcomingRenewalServiceRequest;
import com.gogreen.main.Model.AddUpcoming_Renewal_Service.RESPONSE.AddUpcomingRenewalServiceResponseWrapper;
import com.gogreen.main.Model.AddUpcoming_Renewal_Service.RESPONSE.Coupons;
import com.gogreen.main.Model.AddUpcoming_Renewal_Service.RESPONSE.UpcomingRenewals;
import com.gogreen.main.Model.AddUpcoming_Renewal_Service.RESPONSE.UpcomingServices;
import com.gogreen.main.R;
import com.gogreen.main.Screens.CityActivity;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainFragment extends Fragment implements View.OnClickListener {

    CouponAdapter couponAdapter;
    private ViewPager mViewPager;
    private ImageView edit_location;
    APIUtility apiUtility;
    SnapHelper snapHelper1, snapHelper;
    private RecyclerView upcomingRenewal, upcomingServices;
    UpcomingServicesListAdapter upcomingServicesListAdapter;
    PendingListAdapter pendingListAdapter;
    List<UpcomingServices> upcomingServicesList;
    List<UpcomingRenewals> upcomingRenewalList;
    List<Coupons> couponslist;
    private CardView noRenewals, noServices;
    private TextView location,welcome,UserName;
    private SwipeRefreshLayout swipeView;
    int CurrentPage;
    Timer timer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_bar_dash_board, container, false);
        edit_location = (ImageView) view.findViewById(R.id.edit_image_location);
        mViewPager = (ViewPager) view.findViewById(R.id.coupons);
        upcomingRenewal = (RecyclerView) view.findViewById(R.id.upcoming_renewals_list);
        upcomingServices = (RecyclerView) view.findViewById(R.id.upcoming_services_list);
        noRenewals = (CardView) view.findViewById(R.id.card_view_No_renewals);
        noServices = (CardView) view.findViewById(R.id.card_view_No_services);
        location = (TextView) view.findViewById(R.id.selected_car_Location);
        swipeView=(SwipeRefreshLayout)view.findViewById(R.id.refresh);
        welcome=(TextView)view.findViewById(R.id.welcome);
        UserName=(TextView)view.findViewById(R.id.useName);
        UserName.setText(Preferences.getPreference(getActivity().getApplicationContext(),PrefEntity.USER_NAME));
        if(Preferences.getPreference(getActivity().getApplicationContext(),PrefEntity.WELCOME).equals("1")){
            UserName.setVisibility(View.VISIBLE);
            welcome.setVisibility(View.VISIBLE);
        }


        if (Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.USER_STREET_ID).equals("")) {
            location.setText(getString(R.string.choose_location));
        } else {

            location.setText(Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.USER_CITY) + "," + Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.USER_LOCALITY));
        }
        apiUtility = new APIUtility(getActivity());
        addUpcomingServices(true);
        InitView();
        return view;
    }


    private void InitView() {
        upcomingRenewalList = new ArrayList<>();
        upcomingServicesList = new ArrayList<>();
        couponslist= new ArrayList<>();
        snapHelper = new PagerSnapHelper();
        snapHelper1 = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(upcomingRenewal);
        snapHelper1.attachToRecyclerView(upcomingServices);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        upcomingRenewal.setLayoutManager(linearLayoutManager);
        final LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        upcomingServices.setLayoutManager(linearLayoutManager1);
        pendingListAdapter = new PendingListAdapter(upcomingRenewalList, this);
        upcomingRenewal.setAdapter(pendingListAdapter);
        upcomingServicesListAdapter = new UpcomingServicesListAdapter(upcomingServicesList, this);
        upcomingServices.setAdapter(upcomingServicesListAdapter);

        edit_location.setOnClickListener(this);
        refreshList();

    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity().getApplicationContext(), CityActivity.class);
        intent.putExtra("hi", "hi");
        startActivity(intent);

    }


    @Override
    public void onStart() {
        if (Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.USER_STREET_ID).equals("")) {
            location.setText("Choose Your Location");
        } else {
            location.setText(Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.USER_CITY) + "," + Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.USER_LOCALITY));
        }

        timer=new Timer();
        if(CommonUtils.isNetworkAvailable(getActivity())) {
            timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
        }else{
//            CommonUtils.displayNetworkAlert(getActivity(),false);
        }
        super.onStart();
    }

    void addUpcomingServices(boolean isProgress) {

        AddUpcomingRenewalServiceRequest addUpcomingRenewalServiceRequest = new AddUpcomingRenewalServiceRequest();
        addUpcomingRenewalServiceRequest.setAppKey(Constants.APP_KEY);
        addUpcomingRenewalServiceRequest.setMethod("upcoming_renewals");
        addUpcomingRenewalServiceRequest.setUserID(Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.USERID));

        apiUtility.addUpcomingServices(getActivity(), addUpcomingRenewalServiceRequest, isProgress, new APIUtility.APIResponseListener<AddUpcomingRenewalServiceResponseWrapper>() {
            @Override
            public void onReceiveResponse(AddUpcomingRenewalServiceResponseWrapper response) {
                swipeView.setRefreshing(false);
                if (response != null) {

                    if (response.getResponse().getResults().getUpcomingServices().size() > 0) {
                        upcomingServices.setVisibility(View.VISIBLE);
                        noServices.setVisibility(View.GONE);
                        upcomingServicesList.clear();
                        upcomingServicesList.addAll(response.getResponse().getResults().getUpcomingServices());
                        upcomingServicesListAdapter.notifyDataSetChanged();

                    } else {
                        upcomingServices.setVisibility(View.GONE);
                        noServices.setVisibility(View.VISIBLE);
                    }

                    if (response.getResponse().getResults().getUpcomingRenewals().size() != 0) {
                        upcomingRenewal.setVisibility(View.VISIBLE);
                        noRenewals.setVisibility(View.GONE);
                        upcomingRenewalList.clear();
                        upcomingRenewalList.addAll(response.getResponse().getResults().getUpcomingRenewals());
                        pendingListAdapter.notifyDataSetChanged();

                    } else {
                        upcomingRenewal.setVisibility(View.GONE);
                        noRenewals.setVisibility(View.VISIBLE);
                    }

                    if (response.getResponse().getResults().getCoupons().size() > 0){
                        couponslist.clear();
                        couponslist.addAll(response.getResponse().getResults().getCoupons());
                        mViewPager.setVisibility(View.VISIBLE);
                        couponAdapter = new CouponAdapter(getActivity(),couponslist);
                        mViewPager.setAdapter(couponAdapter);

                    }
                    else {
                        mViewPager.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onResponseFailed() {
                swipeView.setRefreshing(false);
                CommonUtils.alert(getActivity(),getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(AddUpcomingRenewalServiceResponseWrapper response) {
                swipeView.setRefreshing(false);
                CommonUtils.alert(getActivity(),response.getResponse().getMessage());
            }
        });




    }

    void refreshList() {

        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                if (CommonUtils.isNetworkAvailable(getActivity())) {
                     addUpcomingServices(false);
                } else {
                    swipeView.setRefreshing(false);
                    CommonUtils.displayNetworkAlert(getActivity(), false);

                }
            }
        });

        swipeView.setColorSchemeColors(Color.GREEN, Color.BLUE, Color.RED, Color.CYAN,Color.GRAY);
        swipeView.setDistanceToTriggerSync(100);// in dips
        swipeView.setSize(SwipeRefreshLayout.DEFAULT);
    }


    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (CurrentPage+1 > couponslist.size()) {
                        CurrentPage=0;
                    }
                     else{
                        CurrentPage++;
                    }
                        mViewPager.setCurrentItem(CurrentPage,true);

                }
            });
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
    }
}
