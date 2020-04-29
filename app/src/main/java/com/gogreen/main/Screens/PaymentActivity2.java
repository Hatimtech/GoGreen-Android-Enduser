package com.gogreen.main.Screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gogreen.main.Model.SendPaidCarDetail.REQUEST.CarList;
import com.gogreen.main.Model.SendPaidCarDetail.REQUEST.SendCarPaidDetailRequest;
import com.gogreen.main.Model.SendPaidCarDetail.RESPONSE.SendPaidCarDetailResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity;
import com.paytabs.paytabs_sdk.utils.PaymentParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PaymentActivity2 extends BaseActivity implements View.OnClickListener {


    APIUtility apiUtility;
    String pt_transaction_id;
    private Toolbar toolbar;
    private AppCompatTextView title;
    private ImageView back,autoRenewalImage;
    private CardView online, cash;
    String s;
    String  token="";
    String  pt_email="";
    String  pt_password="";
    int i;
    LinearLayout autoRenew;
    boolean isAutoRenewal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiUtility = new APIUtility(this);
        setContentView(R.layout.activity_payment_type);
        initViews();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText(R.string.payments);
        online = (CardView) findViewById(R.id.online);
        cash = (CardView) findViewById(R.id.cod);
        back = (ImageView) findViewById(R.id.back);
        online.setOnClickListener(this);
        cash.setOnClickListener(this);
        back.setOnClickListener(this);
        autoRenewalImage = (ImageView) findViewById(R.id.autoRenewalsImg);
        autoRenew = (LinearLayout) findViewById(R.id.autoRenewals);
        autoRenew.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.autoRenewals:
                if(isAutoRenewal){
                    isAutoRenewal=false;
                    autoRenewalImage.setImageResource(R.mipmap.unselect_check_box_icon);
                }else{
                    isAutoRenewal=true;
                    autoRenewalImage.setImageResource(R.mipmap.select_check_box_icon);
                }
                break;


            case R.id.online:
                if (CommonUtils.isNetworkAvailable(PaymentActivity2.this)) {

                   showDailogOnline();
                } else

                    CommonUtils.alert(PaymentActivity2.this, getString(R.string.alert_network));
                break;

            case R.id.cod:
                if (CommonUtils.isNetworkAvailable(PaymentActivity2.this)) {
                     showDailogOffline("COD","1");

                } else

                    CommonUtils.alert(PaymentActivity2.this, getString(R.string.alert_network));

                break;

        }
    }


    void makePayment() {


        Intent in = new Intent(PaymentActivity2.this, PayTabActivity.class);
        // Merchant
      /*  if (Preferences.getPreference(getApplicationContext(), PrefEntity.PAYMENTTYPE).equals("1")) {
            in.putExtra(PaymentParams.MERCHANT_EMAIL, "karan@ripenapps.com"); //this a demo account for testing the sdk
            in.putExtra(PaymentParams.SECRET_KEY, "nCqyPKUQExNhDKiQqZpRF4Bp9dNFH875KyEzizqX7eeKYxBpw1gc5SCe5pUNx1TizxSS7iPew4ZvCAV8BbkH4WWamQYUSRcrp4kw");//Add your Secret Key Here

        } else {
            in.putExtra(PaymentParams.MERCHANT_EMAIL, "abhishek@ripenapps.com"); //this a demo account for testing the sdk
            in.putExtra(PaymentParams.SECRET_KEY, "PqoVISuqaN6DXhiWB7oV4cgSpJvII2tytRubKZCUMJxJ1PFCX4y1hOUGxpogsSOVJi7tiKo4tVOuLt7IyFKQtEw14VhwN6sxpA0H");//Add your Secret Key Here
        }*/
//        in.putExtra(PaymentParams.MERCHANT_EMAIL, "akashshukla.sln@gmail.com");
//        in.putExtra(PaymentParams.SECRET_KEY, "pIHBPORX6VyM6p4prWdcTfSUDI88LAnbj5N9TE6ZAEMG4R5vOjIHyABcjJk5kIhDN4MGJSGkxt0Ck678pQQXrs9KcuPwc7aZcWFV"); //Add your Secret Key Here
        in.putExtra(PaymentParams.MERCHANT_EMAIL, "karan@ripenapps.com"); //this a demo account for testing the sdk
        in.putExtra(PaymentParams.SECRET_KEY, "nCqyPKUQExNhDKiQqZpRF4Bp9dNFH875KyEzizqX7eeKYxBpw1gc5SCe5pUNx1TizxSS7iPew4ZvCAV8BbkH4WWamQYUSRcrp4kw");//Add your Secret Key Here

        in.putExtra(PaymentParams.LANGUAGE, PaymentParams.ENGLISH);
        in.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#70ab0d");
        in.putExtra(PaymentParams.THEME, PaymentParams.THEME_LIGHT);
        // Transaction
        in.putExtra(PaymentParams.TRANSACTION_TITLE,  Preferences.getPreference(getApplicationContext(), PrefEntity.USER_NAME));
        in.putExtra(PaymentParams.PRODUCT_NAME, "Car Wash Service");
        in.putExtra(PaymentParams.AMOUNT, Double.parseDouble(getIntent().getStringExtra("amount")));
        in.putExtra(PaymentParams.CURRENCY_CODE, "AED");
        in.putExtra(PaymentParams.ORDER_ID, String.valueOf(System.currentTimeMillis()));
        // Customer
        in.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, Preferences.getPreference(getApplicationContext(), PrefEntity.PHONE_NUMBER));
        in.putExtra(PaymentParams.CUSTOMER_EMAIL, Preferences.getPreference(getApplicationContext(), PrefEntity.USER_EMAIL));
        // Billing Address
        in.putExtra(PaymentParams.ADDRESS_BILLING,  Preferences.getPreference(getApplicationContext(), PrefEntity.APARTMENTNUMBER) + " " + Preferences.getPreference(getApplicationContext(), PrefEntity.USER_STREET));
        in.putExtra(PaymentParams.CITY_BILLING, Preferences.getPreference(getApplicationContext(), PrefEntity.USER_LOCALITY));
        in.putExtra(PaymentParams.STATE_BILLING, Preferences.getPreference(getApplicationContext(), PrefEntity.USER_CITY));
        in.putExtra(PaymentParams.COUNTRY_BILLING, "ARE");
        in.putExtra(PaymentParams.POSTAL_CODE_BILLING, "00971"); //Put Country Phone code if Postal code not available '00973'
        // Shipping Address
        in.putExtra(PaymentParams.ADDRESS_SHIPPING, Preferences.getPreference(getApplicationContext(), PrefEntity.APARTMENTNUMBER) + " " + Preferences.getPreference(getApplicationContext(), PrefEntity.USER_STREET));
        in.putExtra(PaymentParams.CITY_SHIPPING, Preferences.getPreference(getApplicationContext(), PrefEntity.USER_LOCALITY));
        in.putExtra(PaymentParams.STATE_SHIPPING,  Preferences.getPreference(getApplicationContext(), PrefEntity.USER_CITY));
        in.putExtra(PaymentParams.COUNTRY_SHIPPING, "ARE");
        in.putExtra(PaymentParams.POSTAL_CODE_SHIPPING, "00971"); //Put Country Phone code if Postal code not available '00973'
        //Tokenization
        startActivityForResult(in, PaymentParams.PAYMENT_REQUEST_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE){
            if (data.getStringExtra(PaymentParams.RESPONSE_CODE).equals("100")){
                pt_transaction_id=data.getStringExtra(PaymentParams.TRANSACTION_ID);
                sendPaidCarDetail(data.getStringExtra(PaymentParams.TRANSACTION_ID), "2");
                token=data.getStringExtra(PaymentParams.TOKEN);
                pt_email=data.getStringExtra(PaymentParams.CUSTOMER_EMAIL);
                pt_password=data.getStringExtra(PaymentParams.CUSTOMER_PASSWORD);
            }
        }
    }


    public void sendPaidCarDetail(final String pt_transaction_id, String paymentType) {
        SendCarPaidDetailRequest sendCarPaidDetailRequest = new SendCarPaidDetailRequest();
        sendCarPaidDetailRequest.setMethod("insert_booked_services");
        sendCarPaidDetailRequest.setPt_token(token);
        sendCarPaidDetailRequest.setPt_email(pt_email);
        sendCarPaidDetailRequest.setPt_password(pt_password);
        sendCarPaidDetailRequest.setAppKey(Constants.APP_KEY);
        sendCarPaidDetailRequest.setCityID(Preferences.getPreference(getApplicationContext(),PrefEntity.USER_CITY_ID));
        sendCarPaidDetailRequest.setLocality_id(Preferences.getPreference(getApplicationContext(),PrefEntity.USER_LOCALITY_ID));
        sendCarPaidDetailRequest.setStreet_id(Preferences.getPreference(getApplicationContext(),PrefEntity.USER_STREET_ID));
        sendCarPaidDetailRequest.setTransactionId(pt_transaction_id);
        sendCarPaidDetailRequest.setUserId(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));
        sendCarPaidDetailRequest.setTotalPaid(getIntent().getStringExtra("amount"));
        sendCarPaidDetailRequest.setCarLists(getCarList());
        sendCarPaidDetailRequest.setActual_payment(getIntent().getStringExtra("amount"));
        sendCarPaidDetailRequest.setPaymentType(paymentType);
        if(isAutoRenewal){
            sendCarPaidDetailRequest.setAuto_renewal("2");

        }else{
            sendCarPaidDetailRequest.setAuto_renewal("1");

        }        if (Preferences.getPreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT).equals("")) {
            sendCarPaidDetailRequest.setCoupan_applied("NO");

        } else {
            sendCarPaidDetailRequest.setCoupan_applied(Preferences.getPreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT));
        }

        apiUtility.sendPaidCarDetail(PaymentActivity2.this, sendCarPaidDetailRequest, true, new APIUtility.APIResponseListener<SendPaidCarDetailResponseWrapper>() {
            @Override
            public void onReceiveResponse(SendPaidCarDetailResponseWrapper response) {
                if (response != null) {
                    Preferences.setPreference(getApplicationContext(), PrefEntity.ISPAYMENT, "2");
                    Preferences.removePreference(getApplicationContext(), PrefEntity.TOKEN);
                    Intent intent = new Intent(PaymentActivity2.this, DashBoardActivity.class);
                    intent.putExtra("done","done");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Preferences.removePreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT);

                }
            }


            @Override
            public void onResponseFailed() {
                showRetryDailog();
            }


            @Override
            public void onStatusFalse(SendPaidCarDetailResponseWrapper response) {
                CommonUtils.alert(PaymentActivity2.this, response.getResponse().getMessage());
            }

        });
    }

    private void showRetryDailog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PaymentActivity2.this);
        alertDialogBuilder.setTitle("Go Green").setMessage(getString(R.string.VolleyError));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendPaidCarDetail(pt_transaction_id, "2");
            }
        });

        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }


    public void sendCODCarDetail(final String pt_transaction_id, String paymentType) {
        SendCarPaidDetailRequest sendCarPaidDetailRequest = new SendCarPaidDetailRequest();
        sendCarPaidDetailRequest.setMethod("insert_booked_services");
        sendCarPaidDetailRequest.setAppKey(Constants.APP_KEY);
        sendCarPaidDetailRequest.setPt_token(token);
        sendCarPaidDetailRequest.setPt_email(pt_email);
        sendCarPaidDetailRequest.setPt_password(pt_password);
        if(isAutoRenewal){
            sendCarPaidDetailRequest.setAuto_renewal("2");

        }else{
            sendCarPaidDetailRequest.setAuto_renewal("1");

        }        sendCarPaidDetailRequest.setCityID(Preferences.getPreference(getApplicationContext(),PrefEntity.USER_CITY_ID));
        sendCarPaidDetailRequest.setLocality_id(Preferences.getPreference(getApplicationContext(),PrefEntity.USER_LOCALITY_ID));
        sendCarPaidDetailRequest.setStreet_id(Preferences.getPreference(getApplicationContext(),PrefEntity.USER_STREET_ID));
        sendCarPaidDetailRequest.setTransactionId(pt_transaction_id);
        sendCarPaidDetailRequest.setUserId(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));
        sendCarPaidDetailRequest.setActual_payment(getIntent().getStringExtra("amount"));
        sendCarPaidDetailRequest.setCarLists(getCarList());
        s=getIntent().getStringExtra("amount");
        i=Integer.parseInt(s)+5;
        sendCarPaidDetailRequest.setTotalPaid(String.valueOf(i));
        sendCarPaidDetailRequest.setPaymentType(paymentType);
        if (Preferences.getPreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT).equals("")) {
            sendCarPaidDetailRequest.setCoupan_applied("NO");

        } else {
            sendCarPaidDetailRequest.setCoupan_applied(Preferences.getPreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT));
        }

        apiUtility.sendPaidCarDetail(PaymentActivity2.this, sendCarPaidDetailRequest, true, new APIUtility.APIResponseListener<SendPaidCarDetailResponseWrapper>() {
            @Override
            public void onReceiveResponse(SendPaidCarDetailResponseWrapper response) {
                if (response != null) {
                    Preferences.setPreference(getApplicationContext(), PrefEntity.ISPAYMENT, "2");
                    Preferences.removePreference(getApplicationContext(), PrefEntity.TOKEN);
                    Intent intent = new Intent(PaymentActivity2.this, DashBoardActivity.class);
                    intent.putExtra("done","done");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Preferences.removePreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT);
                }
            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(PaymentActivity2.this, getString(R.string.VolleyError));
            }


            @Override
            public void onStatusFalse(SendPaidCarDetailResponseWrapper response) {
                CommonUtils.alert(PaymentActivity2.this, response.getResponse().getMessage());
            }

        });
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    List<CarList> getCarList() {

        List<CarList> carListArrays = new ArrayList<CarList>();

        if (getIntent() != null) {


            carListArrays.add(new CarList());
            carListArrays.get(0).setOneTimeServiceDate(getIntent().getStringExtra("onceDate"));
            carListArrays.get(0).setServices(getIntent().getStringExtra("services"));
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = sdf.format(c.getTime());
            carListArrays.get(0).setPurchase_date(strDate);
            carListArrays.get(0).setPackage_type(getIntent().getStringExtra("package_type"));
            carListArrays.get(0).setFrequency(getIntent().getStringExtra("frequency"));
            carListArrays.get(0).setAmount(getIntent().getStringExtra("amount"));
            carListArrays.get(0).setCar_id(getIntent().getStringExtra("car_id"));
            carListArrays.get(0).setDays(getIntent().getStringExtra("days"));
            carListArrays.get(0).setName(getIntent().getStringExtra("package_name"));
            carListArrays.get(0).setNo_of_months(getIntent().getStringExtra("no_of_month"));

        }


        return carListArrays;
    }

    void showDailogOffline(final String pt_transaction_id, final String payment){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.offline_payment));
        builder.setTitle(R.string.app_name);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendCODCarDetail(pt_transaction_id,"1");
            }
        });
        builder.create();
        builder.show();
        builder.setCancelable(false);
    }


    void showDailogOnline(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.online_payment));
        builder.setTitle(R.string.app_name);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               makePayment();
            }
        });
        builder.create();
        builder.show();
        builder.setCancelable(false);
    }
}
