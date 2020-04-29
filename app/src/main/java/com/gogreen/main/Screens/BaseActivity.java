package com.gogreen.main.Screens;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.AppController;
import com.google.firebase.analytics.FirebaseAnalytics;

public class BaseActivity extends AppCompatActivity {

    public static Context mContext;
    private ProgressDialog dialog;

    public android.support.v4.app.FragmentManager fragManager;
    public FragmentTransaction fragTransaction;
    // variable to track event time
    public long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseActivity.this;

        fragManager = getSupportFragmentManager();
        fragTransaction = fragManager.beginTransaction();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(getApplicationContext(),"onpause",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getApplicationContext(),"onresume",Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog() {
        dialog = new ProgressDialog(BaseActivity.this);
        dialog.setMessage(getString(R.string.prg_dial_pleaseWait));
        dialog.setCancelable(false);
        dialog.show();
    }

    public void showProgressDialog(String Message) {
        dialog = new ProgressDialog(BaseActivity.this);
        dialog.setMessage(Message);
        dialog.setCancelable(false);
        dialog.show();
    }

    //This function is cancel progress dialog
    public void cancelProgressDialog() {
        dialog.dismiss();
    }

    // Avoid Multiple click
    public boolean issingleClick() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return false;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        return true;
    }

    public void hideKeyboard() {

        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm.isAcceptingText()) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {

        }
    }

    public void callAlertDialog(int message, Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(getResources().getString(R.string.app_name))
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void callAlertDialog(String message, Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(getResources().getString(R.string.app_name))
                .setCancelable(false);

        AlertDialog alert = builder.create();
        alert.show();
    }


    public void showSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public void SetUserDetails(String email, String phone, String name, String userID, String is_Phone_Verified) {
        CommonUtils.log("ALIEN", " " + email);
        CommonUtils.log("ALIEN", " " + phone);
        CommonUtils.log("ALIEN", " " + name);
        CommonUtils.log("ALIEN", " " + userID);
        Preferences.setPreference(getApplicationContext(), PrefEntity.USER_EMAIL, email);
        Preferences.setPreference(getApplicationContext(), PrefEntity.USER_NAME, name);
        Preferences.setPreference(getApplicationContext(), PrefEntity.PHONE_NUMBER, phone);
        Preferences.setPreference(getApplicationContext(), PrefEntity.USERID, userID);
        Preferences.setPreference(getApplicationContext(), PrefEntity.IS_PHONE_VERIFIED, is_Phone_Verified);
        Preferences.setPreference_int(getApplicationContext(), PrefEntity.IS_LOGIN, 1);

        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
        if (firebaseAnalytics != null){
            firebaseAnalytics.setUserId(userID);
            firebaseAnalytics.setUserProperty("Phone",phone);
            firebaseAnalytics.setUserProperty("Name",name);
            firebaseAnalytics.setUserProperty("Email",email);
        }


    }

    public void ClearUserDetails() {

        Preferences.removePreference(getApplicationContext(), PrefEntity.USER_EMAIL);
        Preferences.removePreference(getApplicationContext(), PrefEntity.USER_NAME);
        Preferences.removePreference(getApplicationContext(), PrefEntity.PHONE_NUMBER);
        Preferences.removePreference(getApplicationContext(), PrefEntity.USERID);
        Preferences.removePreference(getApplicationContext(), PrefEntity.IS_LOGIN);
        Preferences.removePreference(getApplicationContext(), PrefEntity.ISPAYMENT);
        Preferences.removePreference(getApplicationContext(), PrefEntity.IS_PHONE_VERIFIED);
        Preferences.removePreference(getApplicationContext(), PrefEntity.USER_LOCALITY_ID);
        Preferences.removePreference(getApplicationContext(), PrefEntity.TOTALMONEY);
        Preferences.removePreference(getApplicationContext(), PrefEntity.APARTMENTNUMBER);
        Preferences.removePreference(getApplicationContext(), PrefEntity.END_SERVICING_TIME);
        Preferences.removePreference(getApplicationContext(), PrefEntity.START_SERVICING_TIME);
        Preferences.removePreference(getApplicationContext(), PrefEntity.USER_CITY);
        Preferences.removePreference(getApplicationContext(), PrefEntity.USER_LOCALITY);
        Preferences.removePreference(getApplicationContext(), PrefEntity.USER_CITY_ID);
        Preferences.removePreference(getApplicationContext(), PrefEntity.USER_STREET_ID);
        Preferences.removePreference(getApplicationContext(), PrefEntity.TOKEN);
        Preferences.removePreference(getApplicationContext(), PrefEntity.PAYMENTABLE_MONEY);
        Preferences.removePreference(getApplicationContext(), PrefEntity.DISCOUNTAMOUNT);
        Preferences.removePreference(getApplicationContext(), PrefEntity.TOTALMONEY);
        Preferences.removePreference(getApplicationContext(), PrefEntity.CARID);
        Preferences.removePreference(getApplicationContext(), PrefEntity.ORDERID);
        Preferences.removePreference(getApplicationContext(), PrefEntity.RESCODE);
        Preferences.removePreference(getApplicationContext(), PrefEntity.WELCOME);
        Preferences.removePreference(getApplicationContext(), PrefEntity.CITY_ID);
        Preferences.removePreference(getApplicationContext(), PrefEntity.LOCALITY_ID);
        Preferences.removePreference(getApplicationContext(), PrefEntity.LOCALITY);
        Preferences.removePreference(getApplicationContext(), PrefEntity.CITY);
        Preferences.removePreference(getApplicationContext(),PrefEntity.PAYMENTTYPE);
        Preferences.removePreference(getApplicationContext(),PrefEntity.OTPFIRST);

        }


}
