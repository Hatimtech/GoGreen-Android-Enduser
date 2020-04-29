package com.gogreen.main.Screens;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gogreen.main.Model.ForgetPassword.REQUEST.ForgetPasswordRequest;
import com.gogreen.main.Model.ForgetPassword.RESPONSE.FgPasswordResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Volley.APIUtility;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText userEmail;
    private Button send;
    private Toolbar toolbar;
    private AppCompatTextView title;
    private ImageView back;
    APIUtility APIUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        APIUtility = new APIUtility(ForgetPasswordActivity.this);
        InitViews();
    }


    public void InitViews() {

        userEmail = (EditText) findViewById(R.id.user_email);
        send = (Button) findViewById(R.id.send);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText(getString(R.string.toolbar));
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        send.setOnClickListener(this);
        back.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.send:
                hideKeyboard();
                validation();
                break;

            case R.id.back:
                finish();
                break;
        }
    }


    public void validation() {

        if (!TextUtils.isEmpty(userEmail.getText().toString().trim())) {
            if (CommonUtils.isEmailValid(userEmail.getText().toString().trim())) {
                forgetPassword(userEmail.getText().toString().trim());

            } else {
                userEmail.setError(getString(R.string.alert_email_valid));

            }
        } else {
            userEmail.setError(getString(R.string.alert_email));
        }
    }


    public void forgetPassword(String email) {

        ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest();
        forgetPasswordRequest.setMethod("forget_password");
        forgetPasswordRequest.setApp_key(Constants.APP_KEY);
        forgetPasswordRequest.setEmail(email);


        APIUtility.set_fgPassword(ForgetPasswordActivity.this, forgetPasswordRequest, true, new APIUtility.APIResponseListener<FgPasswordResponseWrapper>() {
            @Override
            public void onReceiveResponse(FgPasswordResponseWrapper response) {
                if (response != null) {
                    CommonUtils.alert(ForgetPasswordActivity.this, response.getResponse().getMessage());
                    userEmail.setText("");
                }

            }


            @Override
            public void onResponseFailed() {
                CommonUtils.alert(ForgetPasswordActivity.this, getString(R.string.VolleyError));
                userEmail.setText("");
            }


            @Override
            public void onStatusFalse(FgPasswordResponseWrapper response) {
                CommonUtils.alert(ForgetPasswordActivity.this, response.getResponse().getMessage());
                userEmail.setText("");
            }


        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}