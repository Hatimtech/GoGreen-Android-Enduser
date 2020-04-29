package com.gogreen.main.Screens;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.facebook.AccessToken;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.gogreen.main.Model.ChangePhoneNumber.REQUEST.ChangePhoneNumberRequest;
import com.gogreen.main.Model.ChangePhoneNumber.RESPONSE.ChangePhoneNumberResponseWrapper;
import com.gogreen.main.Model.GetOTP.REQUEST.GetOTPRequest;
import com.gogreen.main.Model.GetOTP.RESPONSE.GetOTPResponseWrapper;
import com.gogreen.main.Model.PhoneVerified.REQUEST.PhoneVerifiedRequest;
import com.gogreen.main.Model.PhoneVerified.RESPONSE.PhoneVerifiedResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;
import com.hbb20.CountryCodePicker;
import com.sinch.verification.Verification;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OTPActivity extends BaseActivity implements View.OnClickListener {
    private Button virefy;
    private TextView resend_OTP, phone_number, changeNumber;
    private Toolbar toolbar;
    private AppCompatTextView title;
    APIUtility apiUtility;
    private PinView pinView;
    private ImageView back;
    private final String APPLICATION_KEY = "43b7c518-5060-4118-9348-f8316f9be5e0";
    Verification verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_screen_activity);
        apiUtility = new APIUtility(OTPActivity.this);
        InitViews();
        getOTP();
//        SendOTP(Preferences.getPreference(getApplicationContext(), PrefEntity.PHONE_NUMBER));

    }


    public void InitViews() {
        back = (ImageView) findViewById(R.id.back);
        pinView = (PinView) findViewById(R.id.pinview);
        changeNumber = (TextView) findViewById(R.id.chagenumber);
        resend_OTP = (TextView) findViewById(R.id.resendOTP);
        virefy = (Button) findViewById(R.id.verifyButton);
        phone_number = (TextView) findViewById(R.id.phone_number);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText(" ");
        phone_number.setText(Preferences.getPreference(getApplicationContext(), PrefEntity.PHONE_NUMBER));
        setSupportActionBar(toolbar);
        back.setOnClickListener(this);
        virefy.setOnClickListener(this);
        resend_OTP.setOnClickListener(this);
        changeNumber.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.verifyButton:

                if (!pinView.getText().toString().trim().equals("")) {
                    phoneVerified(pinView.getText().toString().trim());
                } else {
                    Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }

//                verification.verify(pinView.getText().toString().trim());

                break;

            case R.id.resendOTP:
                getOTP();
//                SendOTP(Preferences.getPreference(getApplicationContext(), PrefEntity.PHONE_NUMBER));
                CommonUtils.alert(OTPActivity.this, "You will receive an OTP in 10 second");
                break;

            case R.id.chagenumber:
                showDialog("");
                break;

            case R.id.back:
                finish();
                break;
        }
    }


    public void phoneVerified(String otp) {

        PhoneVerifiedRequest phoneVerifiedRequest = new PhoneVerifiedRequest();
        phoneVerifiedRequest.setAppKey(Constants.APP_KEY);
        phoneVerifiedRequest.setMethod("update_verification_key");
        phoneVerifiedRequest.setUserID(Preferences.getPreference(OTPActivity.this, PrefEntity.USERID));
        phoneVerifiedRequest.setOtp(otp);

        apiUtility.getPhoneVerified(OTPActivity.this, phoneVerifiedRequest, true, new APIUtility.APIResponseListener<PhoneVerifiedResponseWrapper>() {
            @Override
            public void onReceiveResponse(PhoneVerifiedResponseWrapper response) {
                if (response != null) {
                    logCompleteRegistrationEvent("Complete Registration");
                    Preferences.setPreference(getApplicationContext(), PrefEntity.IS_PHONE_VERIFIED, "1");
                    Intent intent = new Intent(getApplicationContext(), HowItWorkActivity.class);
                    startActivity(intent);
                    finish();

                }


            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(OTPActivity.this, getString(R.string.VolleyError));

            }

            @Override
            public void onStatusFalse(PhoneVerifiedResponseWrapper response) {
                Toast.makeText(OTPActivity.this, "" + response.getResponse().getMessage(), Toast.LENGTH_SHORT).show();

//                CommonUtils.alert(OTPActivity.this, response.getResponse().getMessage());
            }
        });

    }


    void showDialog(final String mobile) {
        final String[] selectedCode = new String[1];
        final Dialog dialog = new Dialog(OTPActivity.this);
        dialog.setContentView(R.layout.change_phone_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText phone = dialog.findViewById(R.id.user_mobile);
        ImageView close = dialog.findViewById(R.id.cancle);
        final CountryCodePicker ccp = (CountryCodePicker) dialog.findViewById(R.id.ccp);
        selectedCode[0] = ccp.getSelectedCountryCode();

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                selectedCode[0] = ccp.getSelectedCountryCode();

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (AccessToken.getCurrentAccessToken() != null)
                    LoginManager.getInstance().logOut();

            }
        });


        final Button dialogButton = dialog.findViewById(R.id.dialog_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(phone.getText().toString().trim())) {
                    if (CommonUtils.isValidMobile(phone)) {
                        ChangePhoneNumberRequest changePhoneNumberRequest = new ChangePhoneNumberRequest();
                        changePhoneNumberRequest.setAppKey(Constants.APP_KEY);
                        changePhoneNumberRequest.setMethod("update_phone_number");
                        changePhoneNumberRequest.setPhoneNumber("+" + selectedCode[0] + phone.getText().toString());
                        changePhoneNumberRequest.setUserID(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));


                        apiUtility.changePhoneNumber(OTPActivity.this, changePhoneNumberRequest, true, new APIUtility.APIResponseListener<ChangePhoneNumberResponseWrapper>() {
                            @Override
                            public void onReceiveResponse(ChangePhoneNumberResponseWrapper response) {
                                if (response != null) {
                                    phone_number.setText("+" + selectedCode[0] + phone.getText().toString().trim() + ".");
                                    dialog.dismiss();
                                    Preferences.setPreference(getApplicationContext(), PrefEntity.PHONE_NUMBER, "+" + selectedCode[0] + phone.getText().toString().trim());
                                    getOTP();
                                }

                            }

                            @Override
                            public void onResponseFailed() {

                                CommonUtils.alert(OTPActivity.this, getString(R.string.VolleyError));
                            }

                            @Override
                            public void onStatusFalse(ChangePhoneNumberResponseWrapper response) {
                                CommonUtils.alert(OTPActivity.this, response.getResponse().getMessage());
                            }

                        });

                    } else {
                        phone.setError(getString(R.string.alert_mobile_valid));
                    }
                } else {
                    phone.setError(getString(R.string.alert_mobile));
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }

  /*  void SendOTP(final String phoneNumberString) {
        apiUtility.showDialog(OTPActivity.this, true);
        Config config = SinchVerification.config().applicationKey(APPLICATION_KEY).context(getApplicationContext()).build();

        VerificationListener listener = new VerificationListener() {
            @Override
            public void onInitiated(InitiationResult initiationResult) {
                apiUtility.dismissDialog(true);
                Preferences.setPreference(getApplicationContext(), PrefEntity.OTPFIRST, "1");
            }

            @Override
            public void onInitiationFailed(Exception e) {

                apiUtility.dismissDialog(true);
            }

            @Override
            public void onVerified() {

                phoneVerified();

            }

            @Override
            public void onVerificationFailed(Exception e) {
                if (Preferences.getPreference(getApplicationContext(), PrefEntity.OTPFIRST).equals("1")) {
                    Preferences.removePreference(getApplicationContext(), PrefEntity.OTPFIRST);
                } else {
                    CommonUtils.alert(OTPActivity.this, getString(R.string.enter_valid_otp));
                }

            }

            @Override
            public void onVerificationFallback() {

            }
        };

        verification = SinchVerification.createSmsVerification(config, phoneNumberString, listener);
        verification.initiate();


    }*/

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void logCompleteRegistrationEvent(String registrationMethod) {
        AppEventsLogger logger = AppEventsLogger.newLogger(OTPActivity.this);
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_REGISTRATION_METHOD, registrationMethod);
        logger.logEvent(AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION, params);
    }


    void getOTP() {
        GetOTPRequest phoneVerifiedRequest = new GetOTPRequest();
        phoneVerifiedRequest.setAppKey(Constants.APP_KEY);
        phoneVerifiedRequest.setMethod("phone_varification");
        phoneVerifiedRequest.setUserID(Preferences.getPreference(OTPActivity.this, PrefEntity.USERID));

        apiUtility.sendOTP(this, phoneVerifiedRequest, true, new APIUtility.APIResponseListener<GetOTPResponseWrapper>() {
            @Override
            public void onReceiveResponse(GetOTPResponseWrapper response) {
                if (response != null) {

                }
            }

            @Override
            public void onResponseFailed() {
                getOTP();
            }

            @Override
            public void onStatusFalse(GetOTPResponseWrapper response) {

            }
        });

    }

}
