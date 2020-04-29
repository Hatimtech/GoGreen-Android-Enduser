package com.gogreen.main.Screens;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gogreen.main.Model.Login.REQUEST.LoginRequest;
import com.gogreen.main.Model.Login.RESPONSE.LoginResponseWrapper;
import com.gogreen.main.Model.SignUp.REQUEST.SignUpRequest;
import com.gogreen.main.Model.SignUp.RESPONSE.SignUpResponseWrapper;
import com.gogreen.main.Model.UpdateToken.REQUEST.UpdateTokenRequest;
import com.gogreen.main.Model.UpdateToken.RESPONSE.UpdateTokenResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hbb20.CountryCodePicker;

import org.json.JSONObject;

import java.util.Arrays;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends BaseActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private EditText userEmail, userPassword;
    private TextView fgPassword, userSignup;
    private ImageView fbButton, gplusButton;
    private Button loginButton;
    private static final int RC_SIGN_IN = 9001;
    private LoginButton fb_login_btn;
    private SignInButton gmail_signin_Button;
    private CallbackManager callbackManager;
    APIUtility APIUtility;
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Preferences.getPreference_int(getApplicationContext(), PrefEntity.IS_LOGIN) == 1) {
            if (Preferences.getPreference(getApplicationContext(), PrefEntity.IS_PHONE_VERIFIED).equals("1")) {
                if (Preferences.getPreference(getApplicationContext(), PrefEntity.ISPAYMENT).equals("2")) {
                    Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Preferences.setPreference(getApplicationContext(), PrefEntity.WELCOME, "1");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LoginActivity.this, CityActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            } else {
                Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        }

        setContentView(R.layout.activity_login);


        APIUtility = new APIUtility(LoginActivity.this);
        InitViews();
        facebookInit();
        googleInit();

    }

    private void InitViews() {
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        userSignup = findViewById(R.id.user_signup);
        fgPassword = findViewById(R.id.fgPassword);
        loginButton = findViewById(R.id.user_login);
        loginButton.setOnClickListener(this);
        fgPassword.setOnClickListener(this);
        userSignup.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_login:
                hideKeyboard();
                validation();
                break;
            case R.id.user_signup:
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;

            case R.id.fbButton:


                if (CommonUtils.isNetworkAvailable(LoginActivity.this)) {
                    fb_login_btn.performClick();
                } else {
                    CommonUtils.displayNetworkAlert(LoginActivity.this, false);
                }
                break;

            case R.id.gButton:
                if (CommonUtils.isNetworkAvailable(LoginActivity.this)) {
                    gmail_signin_Button.performClick();
                    signIn();
                } else {
                    CommonUtils.displayNetworkAlert(LoginActivity.this, false);
                }
                break;

            case R.id.fgPassword:
                Intent intent1 = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent1);
                break;

        }

    }


    public void validation() {
        if (!TextUtils.isEmpty(userEmail.getText().toString().trim())) {
            if (Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString().trim()).matches()) {
//            if (CommonUtils.isEmailValid(userEmail.getText().toString().trim())) {
                if (!TextUtils.isEmpty(userPassword.getText().toString().trim())) {
                    if (userPassword.getText().toString().trim().length() >= 6) {

                        userLogin(userEmail.getText().toString().trim(), userPassword.getText().toString().trim());

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
    }


    void userLogin(String email, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setMethod("login");
        loginRequest.setApp_key(Constants.APP_KEY);
        loginRequest.setLoginType("EM");
        loginRequest.setSocailId("");
        loginRequest.setDeviceType("Android");
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);


        APIUtility.userLogin(LoginActivity.this, loginRequest, true, new APIUtility.APIResponseListener<LoginResponseWrapper>() {
            @Override
            public void onReceiveResponse(LoginResponseWrapper response) {
                if (response != null) {


                    SetUserDetails(response.getResponse().getResult().get(0).getEmail(), response.getResponse().getResult().get(0).getPhone_number(), response.getResponse().getResult().get(0).getName()
                            , response.getResponse().getResult().get(0).getId(), response.getResponse().getResult().get(0).getIs_phone_verified());

                    Preferences.setPreference(getApplicationContext(), PrefEntity.ISPAYMENT, response.getResponse().getResult().get(0).getIs_payment());

                    updateToken(response.getResponse().getResult().get(0).getId());


                    if (Preferences.getPreference(getApplicationContext(), PrefEntity.IS_PHONE_VERIFIED).equals("1")) {
                        if (response.getResponse().getResult().get(0).getIs_payment().equals("2")) {
                            Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                            Preferences.setPreference(getApplicationContext(), PrefEntity.WELCOME, "1");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(LoginActivity.this, CityActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                    } else {
                        Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                }
            }


            @Override
            public void onResponseFailed() {

                CommonUtils.alert(LoginActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(LoginResponseWrapper response) {
                CommonUtils.alert(LoginActivity.this, response.getResponse().getMessage());
            }

        });

    }


    void facebookInit() {
        fbButton = findViewById(R.id.fbButton);
        fbButton.setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        fb_login_btn = (LoginButton) findViewById(R.id.loginFB);
        fb_login_btn.setReadPermissions(Arrays.asList("public_profile", "email"));
        fb_login_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                CommonUtils.log(getApplicationContext(), "Facebook ID", loginResult.getAccessToken().getUserId());
                getGraphData(loginResult.getAccessToken().getUserId());
                Log.d("RESPONSE FACEBOOK : ", loginResult.toString());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("RESPONSE FACEBOOK EXP: ", exception.toString());
                if (exception instanceof FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    }
                }
                exception.printStackTrace();
            }
        });

    }


    private void getGraphData(final String userID) {
        final GraphRequest request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + userID, null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            String id = "", email = "", firstname = "", lastname = "", country = "";
                            JSONObject object = response.getJSONObject();
                            Log.d("OBJECT", object.toString());
                            if (object.has("id")) {
                                id = object.getString("id");
                            }
                            if (object.has("first_name")) {
                                firstname = object.getString("first_name");
                            }
                            if (object.has("last_name")) {
                                lastname = object.getString("last_name");
                            }
                            if (object.has("email")) {
                                email = object.getString("email");
                            }


                            if (CommonUtils.isEmailValid(email)) {


                                LoginRequest loginRequest = new LoginRequest();
                                loginRequest.setMethod("login");
                                loginRequest.setApp_key(Constants.APP_KEY);
                                loginRequest.setLoginType("FB");
                                loginRequest.setSocailId(id);


                                final String finalEmail = email;
                                final String finalFirstname = firstname + " " + lastname;
                                final String finalId = id;
                                APIUtility.userLogin(LoginActivity.this, loginRequest, true, new APIUtility.APIResponseListener<LoginResponseWrapper>() {
                                    @Override
                                    public void onReceiveResponse(LoginResponseWrapper response) {
                                        if (response != null) {

                                            SetUserDetails(response.getResponse().getResult().get(0).getEmail(), response.getResponse().getResult().get(0).getPhone_number(), response.getResponse().getResult().get(0).getName()
                                                    , response.getResponse().getResult().get(0).getId(), response.getResponse().getResult().get(0).getIs_phone_verified());
                                            Preferences.setPreference(getApplicationContext(), PrefEntity.ISPAYMENT, response.getResponse().getResult().get(0).getIs_payment());
                                            updateToken(response.getResponse().getResult().get(0).getId());


                                            if (Preferences.getPreference(getApplicationContext(), PrefEntity.IS_PHONE_VERIFIED).equals("1")) {
                                                if (response.getResponse().getResult().get(0).getIs_payment().equals("2")) {
                                                    Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                                                    Preferences.setPreference(getApplicationContext(), PrefEntity.WELCOME, "1");
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                } else {
                                                    Intent intent = new Intent(LoginActivity.this, CityActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                }

                                            } else {
                                                Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }

                                        }
                                    }


                                    @Override
                                    public void onResponseFailed() {

                                        Logout();
                                        CommonUtils.alert(LoginActivity.this, getString(R.string.VolleyError));
                                    }

                                    @Override
                                    public void onStatusFalse(LoginResponseWrapper response) {

                                        if (response.getResponse().getResCode() == 6 || response.getResponse().getStatus() == 0) {

                                            showDialog(finalEmail, finalFirstname, "", userID);
                                        } else {
                                            Logout();
                                            CommonUtils.alert(LoginActivity.this, response.getResponse().getMessage());
                                        }

                                    }
                                });

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            CommonUtils.alert(LoginActivity.this, getString(R.string.facebook_error));
                            Log.d("RESPONSEFACEBOOKGRAPH: ", e.toString());
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }


    void showDialog(final String emailAdd, final String name, String mobile, final String socialid) {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.custom_phone_email_dailog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText phone = dialog.findViewById(R.id.user_mobile);
        final EditText email = dialog.findViewById(R.id.user_email);
        ImageView close = dialog.findViewById(R.id.cancle);
        final CountryCodePicker ccp = (CountryCodePicker) dialog.findViewById(R.id.ccp);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (AccessToken.getCurrentAccessToken() != null)
                    LoginManager.getInstance().logOut();

            }
        });


        if (emailAdd != null) {
            email.setText(emailAdd);
        } else {
            email.setText("");
        }


        final Button dialogButton = dialog.findViewById(R.id.dialog_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(phone.getText().toString().trim())) {
                    if (CommonUtils.isValidMobile(phone)) {
                        if (Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString().trim()).matches()) {

//                            if (CommonUtils.isEmailValid(email.getText().toString().trim())) {
                            if (!TextUtils.isEmpty(email.getText().toString().trim())) {


                                SignUpRequest signUpRequest = new SignUpRequest();
                                signUpRequest.setMethod("sign_up");
                                signUpRequest.setAppKey(Constants.APP_KEY);
                                signUpRequest.setLoginType("FB");
                                signUpRequest.setSocailId(socialid);
                                signUpRequest.setEmail(email.getText().toString());
                                signUpRequest.setName(name);
                                signUpRequest.setPhoneNumber("+" + ccp.getSelectedCountryCode() + phone.getText().toString());
                                signUpRequest.setPassword("");
                                signUpRequest.setDeviceToken(Preferences.getPreference(getApplicationContext(), PrefEntity.DEVICETOKEN));
                                signUpRequest.setDevice_type("Android");


                                APIUtility.userSignUp(LoginActivity.this, signUpRequest, true, new APIUtility.APIResponseListener<SignUpResponseWrapper>() {
                                    @Override
                                    public void onReceiveResponse(SignUpResponseWrapper response) {
                                        if (response != null) {


                                            SetUserDetails(response.getResponse().getResult().get(0).getEmail(), response.getResponse().getResult().get(0).getPhone_number(), response.getResponse().getResult().get(0).getName()
                                                    , response.getResponse().getResult().get(0).getId(), response.getResponse().getResult().get(0).getIs_phone_verified());


                                            if (Preferences.getPreference(getApplicationContext(), PrefEntity.IS_PHONE_VERIFIED).equals("1")) {

                                                Intent intent = new Intent(LoginActivity.this, CityActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);


                                            } else {
                                                Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }

                                        }
                                    }


                                    @Override
                                    public void onResponseFailed() {

                                        CommonUtils.alert(LoginActivity.this, getString(R.string.VolleyError));
                                        Logout();
                                    }

                                    @Override
                                    public void onStatusFalse(SignUpResponseWrapper response) {
                                        CommonUtils.alert(LoginActivity.this, response.getResponse().getMessage());
                                        Logout();
                                    }

                                });


                            } else {
                                email.setError(getString(R.string.alert_email_valid));
                            }
                        } else {
                            email.setError(getString(R.string.alert_email));
                        }
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


    void googleInit() {

        gplusButton = findViewById(R.id.gButton);
        gplusButton.setOnClickListener(this);
        gmail_signin_Button = findViewById(R.id.sign_in_button);
        gmail_signin_Button.setOnClickListener(this);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
    }

    private void signIn() {
        APIUtility.showDialog(LoginActivity.this, true);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    private void handleResult(GoogleSignInResult result) {

        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            final String id = account.getId();


            if (CommonUtils.isEmailValid(email)) {


                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setMethod("login");
                loginRequest.setApp_key(Constants.APP_KEY);
                loginRequest.setLoginType("GL");
                loginRequest.setSocailId(id);


                final String finalEmail = email;
                final String finalFirstname = name;
                final String finalId = id;
                APIUtility.userLogin(LoginActivity.this, loginRequest, true, new APIUtility.APIResponseListener<LoginResponseWrapper>() {
                    @Override
                    public void onReceiveResponse(LoginResponseWrapper response) {
                        if (response != null) {

                            SetUserDetails(response.getResponse().getResult().get(0).getEmail(), response.getResponse().getResult().get(0).getPhone_number(), response.getResponse().getResult().get(0).getName()
                                    , response.getResponse().getResult().get(0).getId(), response.getResponse().getResult().get(0).getIs_phone_verified());

                            Preferences.setPreference(getApplicationContext(), PrefEntity.ISPAYMENT, response.getResponse().getResult().get(0).getIs_payment());
                            updateToken(response.getResponse().getResult().get(0).getId());

                            if (Preferences.getPreference(getApplicationContext(), PrefEntity.IS_PHONE_VERIFIED).equals("1")) {
                                if (response.getResponse().getResult().get(0).getIs_payment().equals("2")) {
                                    Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                                    Preferences.setPreference(getApplicationContext(), PrefEntity.WELCOME, "1");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, CityActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }

                            } else {
                                Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                        }
                    }


                    @Override
                    public void onResponseFailed() {

                        Logout();
                        CommonUtils.alert(LoginActivity.this, getString(R.string.VolleyError));
                    }

                    @Override
                    public void onStatusFalse(LoginResponseWrapper response) {

                        if (response.getResponse().getResCode() == 6 || response.getResponse().getStatus() == 0) {

                            showDialogGoogle(finalEmail, finalFirstname, "", id);
                        } else {
                            Logout();
                            CommonUtils.alert(LoginActivity.this, response.getResponse().getMessage());
                        }

                    }
                });


            }
        } else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        }
        APIUtility.dismissDialog(true);
        super.onActivityResult(requestCode, resultCode, data);
    }




    void showDialogGoogle(final String emailAdd, final String name, String mobile, final String socialid) {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.custom_phone_email_dailog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText phone = dialog.findViewById(R.id.user_mobile);
        final EditText email = dialog.findViewById(R.id.user_email);
        final CountryCodePicker ccp = (CountryCodePicker) dialog.findViewById(R.id.ccp);
        ImageView close = dialog.findViewById(R.id.cancle);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (AccessToken.getCurrentAccessToken() != null)
                    LoginManager.getInstance().logOut();

            }
        });


        try {
            email.setText(emailAdd);
        } catch (Exception e) {
            email.setText("");
        }


        final Button dialogButton = dialog.findViewById(R.id.dialog_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(phone.getText().toString().trim())) {
                    if (CommonUtils.isValidMobile(phone)) {
                        if (CommonUtils.isEmailValid(email.getText().toString().trim())) {
                            if (!TextUtils.isEmpty(email.getText().toString().trim())) {


                                SignUpRequest signUpRequest = new SignUpRequest();
                                signUpRequest.setMethod("sign_up");
                                signUpRequest.setAppKey(Constants.APP_KEY);
                                signUpRequest.setLoginType("GL");
                                signUpRequest.setSocailId(socialid);
                                signUpRequest.setEmail(email.getText().toString());
                                signUpRequest.setName(name);
                                signUpRequest.setPhoneNumber("+" + ccp.getSelectedCountryCode() + phone.getText().toString());
                                signUpRequest.setPassword("");
                                signUpRequest.setDeviceToken(Preferences.getPreference(getApplicationContext(), PrefEntity.DEVICETOKEN));
                                signUpRequest.setDevice_type("Android");


                                APIUtility.userSignUp(LoginActivity.this, signUpRequest, true, new APIUtility.APIResponseListener<SignUpResponseWrapper>() {
                                    @Override
                                    public void onReceiveResponse(SignUpResponseWrapper response) {
                                        if (response != null) {


                                            SetUserDetails(response.getResponse().getResult().get(0).getEmail(), response.getResponse().getResult().get(0).getPhone_number(), response.getResponse().getResult().get(0).getName()
                                                    , response.getResponse().getResult().get(0).getId(), response.getResponse().getResult().get(0).getIs_phone_verified());


                                            if (Preferences.getPreference(getApplicationContext(), PrefEntity.IS_PHONE_VERIFIED).equals("1")) {

                                                Intent intent = new Intent(LoginActivity.this, CityActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);


                                            } else {
                                                Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }

                                        }

                                    }


                                    @Override
                                    public void onResponseFailed() {

                                        CommonUtils.alert(LoginActivity.this, getString(R.string.VolleyError));
                                    }

                                    @Override
                                    public void onStatusFalse(SignUpResponseWrapper response) {
                                        CommonUtils.alert(LoginActivity.this, response.getResponse().getMessage());
                                    }

                                });


                            } else {
                                email.setError(getString(R.string.alert_email_valid));
                            }
                        } else {
                            email.setError(getString(R.string.alert_email));
                        }
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


    void Logout() {
        if (AccessToken.getCurrentAccessToken() != null)
            LoginManager.getInstance().logOut();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    void updateToken(String userID) {
        UpdateTokenRequest request = new UpdateTokenRequest();
        request.setAppKey(Constants.APP_KEY);
        request.setD_type("Android");
        request.setMethod("update_device_token");
        request.setToken(Preferences.getPreference(getApplicationContext(), PrefEntity.DEVICETOKEN));
        request.setUser_id(userID);

        APIUtility.updateToken(LoginActivity.this, request, false, new APIUtility.APIResponseListener<UpdateTokenResponseWrapper>() {
                    @Override
                    public void onReceiveResponse(UpdateTokenResponseWrapper response) {

                    }

                    @Override
                    public void onResponseFailed() {

                    }

                    @Override
                    public void onStatusFalse(UpdateTokenResponseWrapper response) {

                    }
                }
        );


    }
}



