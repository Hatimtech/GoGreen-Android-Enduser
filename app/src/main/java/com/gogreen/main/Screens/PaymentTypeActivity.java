package com.gogreen.main.Screens;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gogreen.main.Interfaces.CarDataRepository;
import com.gogreen.main.Interfaces.SelectedCarListArray;
import com.gogreen.main.Model.GetOrderDetailFormNotification.REQUEST.GetOrderDetailFormNotificationRequest;
import com.gogreen.main.Model.GetOrderDetailFormNotification.RESPONSE.GetOrderDetailFormNotificationResponseResult;
import com.gogreen.main.Model.GetOrderDetailFormNotification.RESPONSE.GetOrderDetailFormNotificationResponseWrapper;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PaymentTypeActivity extends BaseActivity implements View.OnClickListener {

    APIUtility apiUtility;
    String pt_transaction_id;
    private Toolbar toolbar;
    private AppCompatTextView title;
    private ImageView back, autoRenewalImage;
    private CardView online, cash;
    GetOrderDetailFormNotificationResponseWrapper result;
    int paidAmountForRenemal = 0;
    String token = "";
    String pt_email = "";
    String pt_password = "";
    LinearLayout autoRenew;
    boolean isAutoRenewal = true;
    String payTYPE, remainingDays = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_type);
        apiUtility = new APIUtility(PaymentTypeActivity.this);
        InitViews();
        if (getIntent().hasExtra("notification")) {
            getDataFromNotifiaction(getIntent().getStringExtra("notification"));
        }
    }

    private void InitViews() {
        autoRenew = (LinearLayout) findViewById(R.id.autoRenewals);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText(R.string.payments);
        setSupportActionBar(toolbar);
        online = (CardView) findViewById(R.id.online);
        cash = (CardView) findViewById(R.id.cod);
        back = (ImageView) findViewById(R.id.back);
        autoRenewalImage = (ImageView) findViewById(R.id.autoRenewalsImg);
        online.setOnClickListener(this);
        cash.setOnClickListener(this);
        back.setOnClickListener(this);
        autoRenew.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.autoRenewals:
                if (isAutoRenewal) {
                    isAutoRenewal = false;
                    autoRenewalImage.setImageResource(R.mipmap.unselect_check_box_icon);
                } else {
                    isAutoRenewal = true;
                    autoRenewalImage.setImageResource(R.mipmap.select_check_box_icon);
                }
                break;

            case R.id.online:
                if (CommonUtils.isNetworkAvailable(PaymentTypeActivity.this)) {
                    payTYPE = "2";
                    showDailogOnline();
                } else

                    CommonUtils.alert(PaymentTypeActivity.this, getString(R.string.alert_network));
                break;

            case R.id.cod:
                if (CommonUtils.isNetworkAvailable(PaymentTypeActivity.this)) {
                    payTYPE = "1";
                    showDailogOffline();
                } else

                    CommonUtils.alert(PaymentTypeActivity.this, getString(R.string.alert_network));

                break;

        }

    }


    void makePayment() {
        String amount = "0";
        if (getIntent().hasExtra("notification")) {
            amount = String.valueOf(paidAmountForRenemal);

        } else {
            amount = Preferences.getPreference(getApplicationContext(), PrefEntity.PAYMENTABLE_MONEY);
        }


        System.out.print(Float.parseFloat(amount));
        DecimalFormat df = new DecimalFormat("0.000");
        df.setMaximumFractionDigits(3);
        Intent in = new Intent(PaymentTypeActivity.this, PayTabActivity.class);
        // Merchant

       /* if (Preferences.getPreference(getApplicationContext(), PrefEntity.PAYMENTTYPE).equals("1")) {
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
        in.putExtra(PaymentParams.TRANSACTION_TITLE, Preferences.getPreference(getApplicationContext(), PrefEntity.USER_NAME));
        in.putExtra(PaymentParams.PRODUCT_NAME, "Car Wash Service");
        in.putExtra(PaymentParams.AMOUNT, Double.parseDouble(amount));
//        in.putExtra(PaymentParams.AMOUNT, Double.parseDouble("1"));
        in.putExtra(PaymentParams.CURRENCY_CODE, "AED");
        in.putExtra(PaymentParams.ORDER_ID, String.valueOf(System.currentTimeMillis()));
        // Customer
        in.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, Preferences.getPreference(getApplicationContext(), PrefEntity.PHONE_NUMBER));
        in.putExtra(PaymentParams.CUSTOMER_EMAIL, Preferences.getPreference(getApplicationContext(), PrefEntity.USER_EMAIL));
//        in.putExtra(PaymentParams.CUSTOMER_EMAIL, "abc@accept.com");

        // Billing Address
        in.putExtra(PaymentParams.ADDRESS_BILLING, Preferences.getPreference(getApplicationContext(), PrefEntity.APARTMENTNUMBER) + " " + Preferences.getPreference(getApplicationContext(), PrefEntity.USER_STREET));
        in.putExtra(PaymentParams.CITY_BILLING, Preferences.getPreference(getApplicationContext(), PrefEntity.USER_LOCALITY));
        in.putExtra(PaymentParams.STATE_BILLING, Preferences.getPreference(getApplicationContext(), PrefEntity.USER_CITY));
        in.putExtra(PaymentParams.COUNTRY_BILLING, "ARE");
        in.putExtra(PaymentParams.POSTAL_CODE_BILLING, "00971"); //Put Country Phone code if Postal code not available '00973'
        // Shipping Address
        in.putExtra(PaymentParams.ADDRESS_SHIPPING, Preferences.getPreference(getApplicationContext(), PrefEntity.APARTMENTNUMBER) + " " + Preferences.getPreference(getApplicationContext(), PrefEntity.USER_STREET));
        in.putExtra(PaymentParams.CITY_SHIPPING, Preferences.getPreference(getApplicationContext(), PrefEntity.USER_LOCALITY));
        in.putExtra(PaymentParams.STATE_SHIPPING, Preferences.getPreference(getApplicationContext(), PrefEntity.USER_CITY));
        in.putExtra(PaymentParams.COUNTRY_SHIPPING, "ARE");
        in.putExtra(PaymentParams.POSTAL_CODE_SHIPPING, "00971"); //Put Country Phone code if Postal code not available '00973'
        //Tokenization
        in.putExtra(PaymentParams.IS_TOKENIZATION, true);
        startActivityForResult(in, PaymentParams.PAYMENT_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            if (data.getStringExtra(PaymentParams.RESPONSE_CODE).equals("100")) {
                pt_transaction_id = data.getStringExtra(PaymentParams.TRANSACTION_ID);
                token = data.getStringExtra(PaymentParams.TOKEN);
                pt_email = data.getStringExtra(PaymentParams.CUSTOMER_EMAIL);
                pt_password = data.getStringExtra(PaymentParams.CUSTOMER_PASSWORD);
                if (getIntent().hasExtra("notification")) {
                    sendPaidCarDetailForRenew(data.getStringExtra(PaymentParams.TRANSACTION_ID), "2");

                } else {
                    sendPaidCarDetail(data.getStringExtra(PaymentParams.TRANSACTION_ID), "2");
                }


            }
        }
    }


    public void sendPaidCarDetail(final String pt_transaction_id, String paymentType) {
        SendCarPaidDetailRequest sendCarPaidDetailRequest = new SendCarPaidDetailRequest();

        if (isAutoRenewal) {
            sendCarPaidDetailRequest.setAuto_renewal("2");

        } else {
            sendCarPaidDetailRequest.setAuto_renewal("1");

        }

        sendCarPaidDetailRequest.setAppKey(Constants.APP_KEY);
        sendCarPaidDetailRequest.setMethod("insert_booked_services");

        sendCarPaidDetailRequest.setTransactionId(pt_transaction_id);
        sendCarPaidDetailRequest.setPt_token(token);
        sendCarPaidDetailRequest.setPt_email(pt_email);
        sendCarPaidDetailRequest.setPt_password(pt_password);
        sendCarPaidDetailRequest.setCityID(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_CITY_ID));
        sendCarPaidDetailRequest.setLocality_id(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_LOCALITY_ID));
        sendCarPaidDetailRequest.setStreet_id(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_STREET_ID));
        sendCarPaidDetailRequest.setUserId(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));
        sendCarPaidDetailRequest.setTotalPaid(Preferences.getPreference(getApplicationContext(), PrefEntity.PAYMENTABLE_MONEY));
        sendCarPaidDetailRequest.setCarLists(getCarList());
        sendCarPaidDetailRequest.setActual_payment(Preferences.getPreference(getApplicationContext(), PrefEntity.TOTALMONEY));
        sendCarPaidDetailRequest.setPaymentType(paymentType);
        if (Preferences.getPreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT).equals("")) {
            sendCarPaidDetailRequest.setCoupan_applied("NO");

        } else {
            sendCarPaidDetailRequest.setCoupan_applied(Preferences.getPreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT));
        }

        apiUtility.sendPaidCarDetail(PaymentTypeActivity.this, sendCarPaidDetailRequest, true, new APIUtility.APIResponseListener<SendPaidCarDetailResponseWrapper>() {
            @Override
            public void onReceiveResponse(SendPaidCarDetailResponseWrapper response) {
                if (response != null) {
                    Preferences.setPreference(getApplicationContext(), PrefEntity.ISPAYMENT, "2");
                    Preferences.removePreference(getApplicationContext(), PrefEntity.TOKEN);
                    Intent intent = new Intent(PaymentTypeActivity.this, DashBoardActivity.class);
                    intent.putExtra("done", "done");
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
                CommonUtils.alert(PaymentTypeActivity.this, response.getResponse().getMessage());
            }

        });
    }


    private void showRetryDailog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PaymentTypeActivity.this);
        alertDialogBuilder.setTitle("Go Green").setMessage(getString(R.string.VolleyError));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (getIntent().hasExtra("notification")) {
                    if (payTYPE.equals("2")) {
                        sendPaidCarDetailForRenew(pt_transaction_id, "2");
                    } else {
                        sendPaidCarDetailForRenew("COD", "1");

                    }

                } else {
                    sendPaidCarDetail(pt_transaction_id, "2");
                }


            }
        });

        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }


    List<CarList> getCarList() {
        List<SelectedCarListArray> cars = CarDataRepository.getInstance().getArrayList();
        List<CarList> carListArrays = new ArrayList<CarList>();
        String days = "";
        int count = 0;
        for (int i = 0; i < cars.size(); i++) {
            CarList car = new CarList();
            car.setCar_id(cars.get(i).getCarId());
            car.setAmount(cars.get(i).getTotalAmount());
            car.setPackage_type(cars.get(i).getCarPackage());
            car.setName("Offers");

            if (CarDataRepository.getInstance().getArrayList().get(i).getServicingType().equals("Exterior")) {
                car.setServices("2");
            } else {
                car.setServices("3");
            }


            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = sdf.format(c.getTime());
            car.setPurchase_date(strDate);


            if (cars.get(i).getCarPackage().equals("once")) {
                car.setOneTimeServiceDate(cars.get(i).getDate());
            } else {

                car.setOneTimeServiceDate("Null");
            }

            days = "";
            for (int j = 0; j < cars.get(i).getDays().size(); j++) {
                if (cars.get(i).getDays().get(j).isSelected()) {
                    count++;
                    days = days + cars.get(i).getDays().get(j).getName() + ",";

                }
            }

            if (cars.get(i).getCarPackage().equals("once")) {
                car.setDays("");
                car.setNo_of_months("0");
            } else {
                car.setDays(days);
                car.setNo_of_months(cars.get(i).getNo_of_months());
            }

            car.setFrequency(String.valueOf(count));


            carListArrays.add(car);
        }
        return carListArrays;
    }


    public void sendCODCarDetail(final String pt_transaction_id, String paymentType) {
        SendCarPaidDetailRequest sendCarPaidDetailRequest = new SendCarPaidDetailRequest();
        sendCarPaidDetailRequest.setMethod("insert_booked_services");
        sendCarPaidDetailRequest.setAppKey(Constants.APP_KEY);
        sendCarPaidDetailRequest.setPt_token(token);
        if (isAutoRenewal) {
            sendCarPaidDetailRequest.setAuto_renewal("2");

        } else {
            sendCarPaidDetailRequest.setAuto_renewal("1");

        }
        sendCarPaidDetailRequest.setPt_email(pt_email);
        sendCarPaidDetailRequest.setPt_password(pt_password);
        sendCarPaidDetailRequest.setCityID(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_CITY_ID));
        sendCarPaidDetailRequest.setLocality_id(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_LOCALITY_ID));
        sendCarPaidDetailRequest.setStreet_id(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_STREET_ID));
        sendCarPaidDetailRequest.setTransactionId(pt_transaction_id);
        sendCarPaidDetailRequest.setUserId(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));
        int i = Integer.parseInt(Preferences.getPreference(getApplicationContext(), PrefEntity.PAYMENTABLE_MONEY)) + 5;
        sendCarPaidDetailRequest.setTotalPaid(String.valueOf(i));
        sendCarPaidDetailRequest.setCarLists(getCarList());
        sendCarPaidDetailRequest.setActual_payment(Preferences.getPreference(getApplicationContext(), PrefEntity.TOTALMONEY));
        sendCarPaidDetailRequest.setPaymentType(paymentType);
        if (Preferences.getPreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT).equals("")) {
            sendCarPaidDetailRequest.setCoupan_applied("NO");

        } else {
            sendCarPaidDetailRequest.setCoupan_applied(Preferences.getPreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT));
        }

        apiUtility.sendPaidCarDetail(PaymentTypeActivity.this, sendCarPaidDetailRequest, true, new APIUtility.APIResponseListener<SendPaidCarDetailResponseWrapper>() {
            @Override
            public void onReceiveResponse(SendPaidCarDetailResponseWrapper response) {
                if (response != null) {
                    Preferences.setPreference(getApplicationContext(), PrefEntity.ISPAYMENT, "2");
                    Preferences.removePreference(getApplicationContext(), PrefEntity.TOKEN);
                    Intent intent = new Intent(PaymentTypeActivity.this, DashBoardActivity.class);
                    intent.putExtra("done", "done");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Preferences.removePreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT);

                }
            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(PaymentTypeActivity.this, getString(R.string.VolleyError));
            }


            @Override
            public void onStatusFalse(SendPaidCarDetailResponseWrapper response) {
                CommonUtils.alert(PaymentTypeActivity.this, response.getResponse().getMessage());
            }

        });
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    void showDailogOffline() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                if (getIntent().hasExtra("notification")) {
                    sendPaidCarDetailForRenew("COD", "1");

                } else {
                    sendCODCarDetail("COD", "1");
                }
            }
        });
        builder.create();
        builder.show();
        builder.setCancelable(false);
    }


    void showDailogOnline() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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


    void getDataFromNotifiaction(String orderID) {
        GetOrderDetailFormNotificationRequest request = new GetOrderDetailFormNotificationRequest();
        request.setApp_key(Constants.APP_KEY);
        request.setMethod("get_all_details_using_order_id");
        request.setOrder_id(orderID);

        apiUtility.getDataFromNotification(PaymentTypeActivity.this, request, false, new APIUtility.APIResponseListener<GetOrderDetailFormNotificationResponseWrapper>() {
            @Override
            public void onReceiveResponse(GetOrderDetailFormNotificationResponseWrapper response) {

                if (response.getResponse().getResult().size() > 0) {
                    result = response;
                    paidAmountForRenemal = 0;
                    for (int i = 0; i < response.getResponse().getResult().size(); i++) {
                        if (response.getResponse().getResult().get(i).getActual_payment() != null) {
                            paidAmountForRenemal = paidAmountForRenemal + Integer.parseInt(response.getResponse().getResult().get(i).getActual_payment());

                        }
                    }
                }

            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(PaymentTypeActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(GetOrderDetailFormNotificationResponseWrapper response) {
                CommonUtils.alert(PaymentTypeActivity.this, response.getResponse().getMessage());
            }
        });
    }


    List<CarList> setCarList() {
        List<GetOrderDetailFormNotificationResponseResult> cars = new ArrayList<>();
        cars.addAll(result.getResponse().getResult());
        List<CarList> carListArrays = new ArrayList<CarList>();
        for (int i = 0; i < cars.size(); i++) {
            CarList car = new CarList();
            car.setCar_id(cars.get(i).getCar_id());
            car.setAmount(cars.get(i).getActual_payment());
            car.setPackage_type(cars.get(i).getPackage_type());
            car.setName(cars.get(i).getPackage_name());

            if (cars.get(i).getServices().equals("2")) {
                car.setServices("2");
            } else {
                car.setServices("3");
            }


            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(cars.get(i).getRemaining_days()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = sdf.format(c.getTime());
            car.setPurchase_date(strDate);


            if (cars.get(i).getPackage_type().equals("once")) {
                car.setOneTimeServiceDate(cars.get(i).getExpiry_date());
            } else {

                car.setOneTimeServiceDate("Null");
            }


            if (cars.get(i).getPackage_type().equals("once")) {
                car.setDays("");
            } else {
                car.setDays(cars.get(i).getDays());
            }

            car.setFrequency(String.valueOf(cars.get(i).getFrequency()));
            car.setNo_of_months(cars.get(i).getNo_of_month());
            carListArrays.add(car);
        }
        return carListArrays;
    }


    public void sendPaidCarDetailForRenew(final String pt_transaction_id, String paymentType) {
        SendCarPaidDetailRequest sendCarPaidDetailRequest = new SendCarPaidDetailRequest();
        sendCarPaidDetailRequest.setMethod("insert_booked_services");
        sendCarPaidDetailRequest.setAppKey(Constants.APP_KEY);
        sendCarPaidDetailRequest.setPt_token(token);
        if (isAutoRenewal) {
            sendCarPaidDetailRequest.setAuto_renewal("2");

        } else {
            sendCarPaidDetailRequest.setAuto_renewal("1");

        }
        sendCarPaidDetailRequest.setPt_email(pt_email);
        sendCarPaidDetailRequest.setPt_password(pt_password);
        sendCarPaidDetailRequest.setTransactionId(pt_transaction_id);
        sendCarPaidDetailRequest.setCityID(result.getResponse().getResult().get(0).getCity_id());
        sendCarPaidDetailRequest.setLocality_id(result.getResponse().getResult().get(0).getLocality_id());
        sendCarPaidDetailRequest.setStreet_id(result.getResponse().getResult().get(0).getStreet_id());
        sendCarPaidDetailRequest.setUserId(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));
        if (paymentType.equals("1")) {
            sendCarPaidDetailRequest.setActual_payment(String.valueOf(paidAmountForRenemal));
            paidAmountForRenemal = paidAmountForRenemal + 5;
            sendCarPaidDetailRequest.setTotalPaid(String.valueOf(paidAmountForRenemal));

        } else {
            sendCarPaidDetailRequest.setActual_payment(String.valueOf(paidAmountForRenemal));
            sendCarPaidDetailRequest.setTotalPaid(String.valueOf(paidAmountForRenemal));
        }

        sendCarPaidDetailRequest.setCarLists(setCarList());
        sendCarPaidDetailRequest.setPaymentType(paymentType);
//        if (Preferences.getPreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT).equals("")) {
        sendCarPaidDetailRequest.setCoupan_applied("NO");

       /* } else {
            sendCarPaidDetailRequest.setCoupan_applied(Preferences.getPreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT));
        }*/

        apiUtility.sendPaidCarDetail(PaymentTypeActivity.this, sendCarPaidDetailRequest, true, new APIUtility.APIResponseListener<SendPaidCarDetailResponseWrapper>() {
            @Override
            public void onReceiveResponse(SendPaidCarDetailResponseWrapper response) {
                if (response != null) {


                    Preferences.setPreference(getApplicationContext(), PrefEntity.ISPAYMENT, "2");
                    Preferences.removePreference(getApplicationContext(), PrefEntity.TOKEN);
                    Intent intent = new Intent(PaymentTypeActivity.this, DashBoardActivity.class);
                    intent.putExtra("done", "done");
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
                CommonUtils.alert(PaymentTypeActivity.this, response.getResponse().getMessage());
            }

        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            NotificationManager notifManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notifManager.cancelAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
