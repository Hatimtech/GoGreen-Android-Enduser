package com.gogreen.main.Screens;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Intent;

import com.gogreen.main.Model.UpdateProfile.REQUEST.UpdateRequest;
import com.gogreen.main.Model.UpdateProfile.RESPONSE.UpdateResponseWrapper;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Utils.Preferences.PrefEntity;

import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Volley.APIUtility;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener {


    private Toolbar toolbar;
    private AppCompatTextView title;
    private TextView location;
    private ImageView back, edit_location;
    private EditText userEmail, userName;
    private Button update;
    APIUtility apiUtility;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        apiUtility = new APIUtility(EditProfileActivity.this);
        initViews();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        edit_location = (ImageView) findViewById(R.id.edit_image_location);
        title.setText("Edit Profile");
        userEmail = findViewById(R.id.user_password);
        update = findViewById(R.id.update);
        userName = findViewById(R.id.user_name);
        update.setOnClickListener(this);
        location = (TextView) findViewById(R.id.selected_car_Location);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        userName.setText(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_NAME));
        userEmail.setText(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_EMAIL));
        if (Preferences.getPreference(getApplicationContext(), PrefEntity.USER_STREET_ID).equals("")) {
            location.setText(getString(R.string.choose_location));
        } else {

            location.setText(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_CITY) + "," + Preferences.getPreference(getApplicationContext(), PrefEntity.USER_LOCALITY));
        }

        edit_location.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back:
                finish();
                break;


            case R.id.update:
                validation();
                break;

            case R.id.edit_image_location:
                Intent intent = new Intent(EditProfileActivity.this, CityActivity.class);
                intent.putExtra("hi", "hi");
                startActivity(intent);
                break;
        }

    }

    @Override
    protected void onRestart() {
        if (Preferences.getPreference(getApplicationContext(), PrefEntity.USER_STREET_ID).equals("")) {
            location.setText("Choose Your Location");
        } else {
            location.setText(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_CITY) + "," + Preferences.getPreference(getApplicationContext(), PrefEntity.USER_LOCALITY));
        }
        super.onRestart();
    }


    private void validation() {

        if (!TextUtils.isEmpty(userName.getText().toString().trim())) {

            if (userName.getText().toString().trim().length() >= 3) {

                if (!TextUtils.isEmpty(userEmail.getText().toString().trim())) {

                    if(Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString().trim()).matches()){

//                        if (CommonUtils.isEmailValid(userEmail.getText().toString().trim())) {

                        profileUpdate(userName.getText().toString().trim(), userEmail.getText().toString().trim());
                    } else {
                        userEmail.setError(getString(R.string.alert_email_valid));

                    }
                } else {
                    userEmail.setError(getString(R.string.alert_email));

                }

            } else {
                userName.setError(getString(R.string.alert_name_size));

            }
        } else {
            userName.setError(getString(R.string.alert_name));

        }
    }

    void profileUpdate(final String name, final String Email) {
        UpdateRequest request = new UpdateRequest();
        request.setAppKey(Constants.APP_KEY);
        request.setMethod("update_profile");
        request.setEmail(Email);
        request.setName(name);
        request.setUser_id(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));

        apiUtility.updateProfile(EditProfileActivity.this, request, true, new APIUtility.APIResponseListener<UpdateResponseWrapper>() {
            @Override
            public void onReceiveResponse(UpdateResponseWrapper response) {
                Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                Preferences.setPreference(getApplicationContext(), PrefEntity.USER_EMAIL, Email);
                Preferences.setPreference(getApplicationContext(), PrefEntity.USER_NAME, name);
                Intent intent = new Intent(EditProfileActivity.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(EditProfileActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(UpdateResponseWrapper response) {
                CommonUtils.alert(EditProfileActivity.this, response.getResponse().getMessage());
            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
