package com.gogreen.main.Screens;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gogreen.main.Model.Login.RESPONSE.LoginResponseWrapper;
import com.gogreen.main.Model.SignUp.RESPONSE.SignUpResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Model.SignUp.REQUEST.SignUpRequest;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;
import com.hbb20.CountryCodePicker;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    private EditText userEmail, userPassword, userMobile, userName;
    private Button userSignupButton;
    private APIUtility apiUtility;
    private CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        apiUtility = new APIUtility(SignUpActivity.this);
        InitViews();
    }

    public void InitViews() {

        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        userSignupButton = findViewById(R.id.user_signup);
        userName = findViewById(R.id.user_name);
        userMobile = findViewById(R.id.user_mobile);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        userSignupButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.user_signup:
                userInputValidation();
                break;
        }
    }

    public void userInputValidation() {

        if (!TextUtils.isEmpty(userName.getText().toString().trim())) {
            if (userName.getText().toString().trim().length() >= 3) {
                if (!TextUtils.isEmpty(userMobile.getText().toString().trim())) {
                    if (CommonUtils.isValidMobile(userMobile)) {
                        if (!TextUtils.isEmpty(userEmail.getText().toString().trim())) {
                            if(Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString().trim()).matches()){

//                                if (CommonUtils.isEmailValid(userEmail.getText().toString().trim())) {
                                if (!TextUtils.isEmpty(userPassword.getText().toString().trim())) {
                                    if (userPassword.getText().toString().trim().length() >= 6) {

                                        userSignUp(userName.getText().toString().trim(), userMobile.getText().toString().trim(), userEmail.getText().toString().trim(), userPassword.getText().toString().trim());
                                    } else {
                                        userPassword.setError(getString(R.string.password_length));

                                    }
                                } else {
                                    userPassword.setError(getString(R.string.alert_password));

                                }
                            } else {
                                userEmail.setError(getString(R.string.alert_email_valid));

                            }
                        } else {
                            userEmail.setError(getString(R.string.alert_email));

                        }
                    } else {
                        userMobile.setError(getString(R.string.alert_mobile_valid));

                    }
                } else {
                    userMobile.setError(getString(R.string.alert_mobile));

                }
            } else {
                userName.setError(getString(R.string.alert_name_size));

            }
        } else {
            userName.setError(getString(R.string.alert_name));

        }
    }

    public void userSignUp(String name, String phoneNumber, String email, String password) {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setMethod("sign_up");
        signUpRequest.setAppKey(Constants.APP_KEY);
        signUpRequest.setLoginType("EM");
        signUpRequest.setSocailId("");
        signUpRequest.setEmail(email);
        signUpRequest.setPassword(password);
        signUpRequest.setName(name);
        signUpRequest.setDeviceToken(Preferences.getPreference(getApplicationContext(),PrefEntity.DEVICETOKEN));
        signUpRequest.setDevice_type("Android");
        signUpRequest.setPhoneNumber("+"+ccp.getSelectedCountryCode() + phoneNumber);


        apiUtility.userSignUp(SignUpActivity.this, signUpRequest, true, new APIUtility.APIResponseListener<SignUpResponseWrapper>() {
            @Override
            public void onReceiveResponse(SignUpResponseWrapper response) {
                if (response != null) {


                    SetUserDetails(response.getResponse().getResult().get(0).getEmail(), response.getResponse().getResult().get(0).getPhone_number(), response.getResponse().getResult().get(0).getName(), response.getResponse().getResult().get(0).getId(), response.getResponse().getResult().get(0).getIs_phone_verified());

                    if (response.getResponse().getResult().get(0).getIs_phone_verified().equals("0")) {
                        Intent intent = new Intent(SignUpActivity.this, OTPActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SignUpActivity.this, CityActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                }
            }

            @Override
            public void onResponseFailed() {

                CommonUtils.alert(SignUpActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(SignUpResponseWrapper response) {
                CommonUtils.alert(SignUpActivity.this, response.getResponse().getMessage());
            }

        });

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}

