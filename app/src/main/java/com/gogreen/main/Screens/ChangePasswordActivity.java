package com.gogreen.main.Screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gogreen.main.Model.ChangePassword.REQUEST.ChangePasswordRequest;
import com.gogreen.main.Model.ChangePassword.RESPONSE.ChangePasswordResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText oldPassword, newPassword, confirmPassword;
    private Button save;
    private Toolbar toolbar;
    private AppCompatTextView title;
    private ImageView back;
    APIUtility apiUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        apiUtility= new APIUtility(ChangePasswordActivity.this);

        InitView();
    }

    private void InitView() {

        oldPassword = (EditText) findViewById(R.id.old_password);
        newPassword = (EditText) findViewById(R.id.new_password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        save = (Button) findViewById(R.id.submit);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) findViewById(R.id.title);
        title.setText(R.string.change_password);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        save.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                validation();
                break;
        }

    }

    void validation() {

        if (!TextUtils.isEmpty(oldPassword.getText().toString().trim())) {

            if (oldPassword.getText().toString().trim().length() >= 6 && oldPassword.getText().toString().trim().length() <= 16) {

                if (!TextUtils.isEmpty(newPassword.getText().toString().trim())) {

                    if (newPassword.getText().toString().trim().length() >= 6 && newPassword.getText().toString().trim().length() <= 16) {

                        if (!TextUtils.isEmpty(confirmPassword.getText().toString().trim())) {

                            if (confirmPassword.getText().toString().trim().length() >= 6 && confirmPassword.getText().toString().trim().length() <= 16) {

                                if (newPassword.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {

                                    changePassword(confirmPassword.getText().toString(),oldPassword.getText().toString());


                                } else {
                                    confirmPassword.setError(getString(R.string.samePasswordAlert));
                                }

                            } else {
                                confirmPassword.setError(getString(R.string.password_length));
                            }
                        } else {
                            confirmPassword.setError(getString(R.string.alert_password));
                        }

                    } else {
                        newPassword.setError(getString(R.string.password_length));
                    }
                } else {
                    newPassword.setError(getString(R.string.alert_password));
                }

            } else {
                oldPassword.setError(getString(R.string.password_length));
            }
        } else {
            oldPassword.setError(getString(R.string.alert_password));
        }

    }


    void changePassword(String confirmPassword,String oldPassword){
        ChangePasswordRequest changePasswordRequest=new ChangePasswordRequest();
        changePasswordRequest.setAppKey(Constants.APP_KEY);
        changePasswordRequest.setNewPassword(confirmPassword);
        changePasswordRequest.setOldPassword(oldPassword);
        changePasswordRequest.setUserID(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));
        changePasswordRequest.setMethod("change_password");

        apiUtility.changePassword(ChangePasswordActivity.this, changePasswordRequest, true, new APIUtility.APIResponseListener<ChangePasswordResponseWrapper>() {
            @Override
            public void onReceiveResponse(ChangePasswordResponseWrapper response) {
                if(response!=null){
                    Intent intent= new Intent(ChangePasswordActivity.this,LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(ChangePasswordActivity.this,getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(ChangePasswordResponseWrapper response) {
                CommonUtils.alert(ChangePasswordActivity.this,response.getResponse().getMessage());

            }
        });

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
