package com.gogreen.main.Screens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.gogreen.main.Adapter.OrderConfirmationAdapter;
import com.gogreen.main.Interfaces.CarDataRepository;
import com.gogreen.main.Interfaces.SelectedCarListArray;
import com.gogreen.main.Model.Coupon.REQUEST.CouponRequest;
import com.gogreen.main.Model.Coupon.RESPONSE.CouponResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;


import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class OrderConfirmationActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView orderConfirmationList;
    private OrderConfirmationAdapter carListAdapter;
    private List<SelectedCarListArray> carList;
    private Toolbar toolbar;
    private TextView proceed, totalMoney, haveCoupon, totalMoneyAmount, DiscountAmount, Coupon;
    private ImageView back, cancle;
    private AppCompatTextView title;
    private LinearLayout haveCouponLayout, PaymentLayout;
    APIUtility apiUtility;
    String Total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        InitView();
        apiUtility = new APIUtility(OrderConfirmationActivity.this);
    }


    private void InitView() {
        cancle = (ImageView) findViewById(R.id.cancle_button);
        Coupon = (TextView) findViewById(R.id.coupon_detail);
        totalMoneyAmount = (TextView) findViewById(R.id.totalAmt);
        DiscountAmount = (TextView) findViewById(R.id.discount);
        haveCouponLayout = (LinearLayout) findViewById(R.id.have_coupon_layout);
        PaymentLayout = (LinearLayout) findViewById(R.id.price_layout);
        haveCoupon = (TextView) findViewById(R.id.have_coupon);
        proceed = (TextView) findViewById(R.id.proceed);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText(R.string.order_confirmation);
        back = (ImageView) findViewById(R.id.back);
        totalMoney = (TextView) findViewById(R.id.money);
        orderConfirmationList = (RecyclerView) findViewById(R.id.confirmation_list);

        carList = new ArrayList<>();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        orderConfirmationList.setLayoutManager(linearLayoutManager);
        carList.addAll(CarDataRepository.getInstance().getArrayList());
        carListAdapter = new OrderConfirmationAdapter(carList, this);
        orderConfirmationList.setAdapter(carListAdapter);
        carListAdapter.notifyDataSetChanged();

        proceed.setOnClickListener(this);
        back.setOnClickListener(this);
        haveCoupon.setOnClickListener(this);
        cancle.setOnClickListener(this);
        totalAmount();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.proceed:
                if(CommonUtils.isNetworkAvailable(OrderConfirmationActivity.this)) {
                    logInitiateCheckoutEvent("Go Green Car wash","GOGREEN-5544","product",1,false,"AED",10.34);
                    Intent intent = new Intent(OrderConfirmationActivity.this, PaymentTypeActivity.class);
                    startActivity(intent);
                }else{
                    CommonUtils.displayNetworkAlert(OrderConfirmationActivity.this,false);
                }

                break;

            case R.id.back:
                finish();
                break;

            case R.id.have_coupon:
                showCouponDailog("");
                break;
            case R.id.cancle_button:
                PaymentLayout.setVisibility(View.GONE);
                haveCoupon.setVisibility(View.VISIBLE);
                totalMoney.setText(Total);
                Preferences.setPreference(getApplicationContext(), PrefEntity.PAYMENTABLE_MONEY, Total);
                Preferences.removePreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT);
                break;
        }

    }


    void totalAmount() {
        int money = 0;
        for (int i = 0; i < carList.size(); i++) {

            money = money + Integer.parseInt(CarDataRepository.getInstance().getArrayList().get(i).getTotalAmount());
            Total = String.valueOf(money);
            totalMoney.setText(Total);
            totalMoneyAmount.setText(Total);
            Preferences.setPreference(getApplicationContext(), PrefEntity.TOTALMONEY, Total);
            Preferences.setPreference(getApplicationContext(), PrefEntity.PAYMENTABLE_MONEY, Total);

        }
    }


    private void showCouponDailog(final String couponcode) {

        final Dialog dialog = new Dialog(OrderConfirmationActivity.this);
        dialog.setContentView(R.layout.coupon_dailog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText coupon = dialog.findViewById(R.id.enter_coupon);
        final Button dialogButton = dialog.findViewById(R.id.dialog_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(coupon.getText().toString().trim())) {
                    dialog.dismiss();

                    CouponRequest couponRequest = new CouponRequest();
                    couponRequest.setAmount(Preferences.getPreference(getApplicationContext(), PrefEntity.TOTALMONEY));
                    couponRequest.setCouponCode(coupon.getText().toString());
                    couponRequest.setAppKey(Constants.APP_KEY);
                    couponRequest.setUserID(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));
                    couponRequest.setMethod("is_valid_coupan");
                    apiUtility.getCoupon(OrderConfirmationActivity.this, couponRequest, true, new APIUtility.APIResponseListener<CouponResponseWrapper>() {
                        @Override
                        public void onReceiveResponse(CouponResponseWrapper response) {

                            if (response != null) {
                                PaymentLayout.setVisibility(View.VISIBLE);
                                haveCoupon.setVisibility(View.GONE);
                                Coupon.setText("( " + response.getResponse().getResult().get(0).getCouponCode() + " )");
                                String discount = response.getResponse().getResult().get(0).getDiscount();
                                int discountAmount = Integer.parseInt(discount) * Integer.parseInt(Preferences.getPreference(getApplicationContext(), PrefEntity.TOTALMONEY)) / 100;
                                DiscountAmount.setText(String.valueOf(discountAmount));
                                int PaymentAmount = Integer.parseInt(Preferences.getPreference(getApplicationContext(), PrefEntity.TOTALMONEY)) - discountAmount;
                                totalMoney.setText(String.valueOf(PaymentAmount));
                                Preferences.setPreference(getApplicationContext(), PrefEntity.PAYMENTABLE_MONEY, String.valueOf(PaymentAmount));
                                Preferences.setPreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT, String.valueOf(discountAmount));

                            }
                        }

                        @Override
                        public void onResponseFailed() {
                            CommonUtils.alert(OrderConfirmationActivity.this, getString(R.string.VolleyError));

                        }

                        @Override
                        public void onStatusFalse(CouponResponseWrapper response) {
                            CommonUtils.alert(OrderConfirmationActivity.this, response.getResponse().getMessage());
                        }
                    });


                } else {
                    coupon.setError(getString(R.string.coupon_alert));
                }
            }
        });
        dialog.setCancelable(true);
        dialog.show();

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void logInitiateCheckoutEvent (String contentData, String contentId, String contentType, int numItems, boolean paymentInfoAvailable, String currency, double totalPrice) {
        Bundle params = new Bundle();
        AppEventsLogger logger = AppEventsLogger.newLogger(OrderConfirmationActivity.this);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT, contentData);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, contentType);
        params.putInt(AppEventsConstants.EVENT_PARAM_NUM_ITEMS, numItems);
        params.putInt(AppEventsConstants.EVENT_PARAM_PAYMENT_INFO_AVAILABLE, paymentInfoAvailable ? 1 : 0);
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency);
        logger.logEvent(AppEventsConstants.EVENT_NAME_INITIATED_CHECKOUT, totalPrice, params);
    }
}



