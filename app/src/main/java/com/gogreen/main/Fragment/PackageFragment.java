package com.gogreen.main.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gogreen.main.Model.CancleSubscription.REQUEST.CancelSubscriptionRequest;
import com.gogreen.main.Model.CancleSubscription.RESPONSE.CancelSubscriptionResponseWrapper;
import com.gogreen.main.Model.OrderPackageDetail.REQUEST.OrderPackageRequest;
import com.gogreen.main.Model.OrderPackageDetail.RESPONSE.OrderPackageResponseWrapper;
import com.gogreen.main.Model.SelectPackage.RESPONSE.SelectPackageResponseWrapper;
import com.gogreen.main.Model.StopPackage.REQUEST.StopPackageRequest;
import com.gogreen.main.R;
import com.gogreen.main.Screens.DashBoardActivity;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PackageFragment extends Fragment implements View.OnClickListener {

    private TextView packageType, serviceDate, expiredDate, amountPaid, serviceType;
    private Button stopPackage, cancleSub;
    APIUtility apiUtility;
    Context context;
    String id, mode1;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.package_list, container, false);
        apiUtility = new APIUtility(getActivity());
        context = getActivity();
        packageType = (TextView) view.findViewById(R.id.package_Type);
        serviceDate = (TextView) view.findViewById(R.id.service_date);
        expiredDate = (TextView) view.findViewById(R.id.expiredDate);
        amountPaid = (TextView) view.findViewById(R.id.paidamount);
        serviceType = (TextView) view.findViewById(R.id.serviceType);
        stopPackage = (Button) view.findViewById(R.id.stopPackage);
        cancleSub = (Button) view.findViewById(R.id.cancleSub);
        stopPackage.setOnClickListener(this);
        cancleSub.setOnClickListener(this);
        getPackageDetail();

        if (getArguments().getString("package").equals("1")) {
            stopPackage.setVisibility(View.VISIBLE);
        } else {
            stopPackage.setVisibility(View.GONE);
        }

        return view;
    }


    void getPackageDetail() {
        OrderPackageRequest packageRequest = new OrderPackageRequest();
        packageRequest.setApp_key(Constants.APP_KEY);
        packageRequest.setMethod("get_order_package_detail");
        packageRequest.setCar_id(Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.CARID));
        packageRequest.setOrderID(Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.ORDERID));
        packageRequest.setUserID(Preferences.getPreference(getActivity().getApplicationContext(), PrefEntity.USERID));

        apiUtility.getOrderPackage(getActivity(), packageRequest, true, new APIUtility.APIResponseListener<OrderPackageResponseWrapper>() {
            @Override
            public void onReceiveResponse(OrderPackageResponseWrapper response) {
                if (response != null) {
                    id = response.getResponse().getResult().get(0).getId();
                    mode1 = response.getResponse().getResult().get(0).getIs_off();


                    if (mode1.equals("1")) {
                        mode1 = "2";
                        stopPackage.setText("Pause Package");
                    } else {
                        mode1 = "1";
                        stopPackage.setText("Resume Package");
                    }


                    if (response.getResponse().getResult().get(0).getSubscription().equals("1")) {
                        if (getArguments().getString("package").equals("1")) {
                            cancleSub.setVisibility(View.VISIBLE);
                            stopPackage.setVisibility(View.VISIBLE);
                        } else {
                            cancleSub.setVisibility(View.GONE);
                            stopPackage.setVisibility(View.GONE);
                        }


                    } else {
                        cancleSub.setVisibility(View.GONE);
                        stopPackage.setVisibility(View.GONE);

                    }

                    packageType.setText(response.getResponse().getResult().get(0).getPackageType());
                    expiredDate.setText(response.getResponse().getResult().get(0).getExpireDate());
                    amountPaid.setText(response.getResponse().getResult().get(0).getAmount());
                    if (response.getResponse().getResult().get(0).getPackageType().equals("once")) {
                        serviceDate.setText(response.getResponse().getResult().get(0).getDate());
                        serviceType.setText(getString(R.string.ServiceType));
                    } else {
                        serviceDate.setText(response.getResponse().getResult().get(0).getDays());
                        serviceType.setText("Service Days");
                    }
                }
            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(getActivity(), getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(OrderPackageResponseWrapper response) {
                CommonUtils.alert(getActivity(), response.getResponse().getMessage()
                );
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stopPackage:
                if(mode1.equals("2")){
                    showUpdateDialog("Are you sure you want to pause your package ?",2);

                }else if(mode1.equals("1")){
                    showUpdateDialog("Are you sure you want to Resume your Package ?",3);

                }
//                stopPackage(id, mode1);
                break;

            case R.id.cancleSub:
                showUpdateDialog("Are you sure you want to Cancel your subscription ?",1);
                break;
        }

    }

    private void cancleSub() {
        CancelSubscriptionRequest request = new CancelSubscriptionRequest();
        request.setAppKey(Constants.APP_KEY);
        request.setMethod("Cancell_subscription");
        request.setUserID(Preferences.getPreference(getActivity(), PrefEntity.USERID));
        apiUtility.cancleSubs(context, request, true, new APIUtility.APIResponseListener<CancelSubscriptionResponseWrapper>() {
            @Override
            public void onReceiveResponse(CancelSubscriptionResponseWrapper response) {
                if (response != null) {
                    getPackageDetail();
                }
            }

            @Override
            public void onResponseFailed() {

            }

            @Override
            public void onStatusFalse(CancelSubscriptionResponseWrapper response) {

            }
        });

    }

    private void stopPackage(final String id, final String mode) {
        StopPackageRequest request = new StopPackageRequest();
        request.setAppKey(Constants.APP_KEY);
        request.setMethod("stop_package");
        request.setMode(mode);
        request.setPackage_id(id);
        request.setUser_id(Preferences.getPreference(context, PrefEntity.USERID));

        apiUtility.startAndStopPackage(context, request, true, new APIUtility.APIResponseListener<SelectPackageResponseWrapper>() {
            @Override
            public void onReceiveResponse(SelectPackageResponseWrapper response) {
                if (response != null) {
                    if (mode.equals("2")) {
                        mode1 = "1";
                        stopPackage.setText("Resume Package");
                    } else {
                        mode1 = "2";
                        stopPackage.setText("Pause Package");
                    }
                }
            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(context, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(SelectPackageResponseWrapper response) {
                CommonUtils.alert(context, response.getResponse().getMessage());

            }
        });


    }

    void showUpdateDialog(String meassage, final int type) {


        final AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle(getString(R.string.app_name));
        ad.setMessage(meassage);
        ad.setCancelable(false);

        ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (type == 1) {
                    cancleSub();
                } else if (type == 2) {
                    stopPackage(id, mode1);
                } else if(type==3){
                    stopPackage(id, mode1);
                }
            }

        });

        ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        ad.create();
        ad.show();

    }
}
