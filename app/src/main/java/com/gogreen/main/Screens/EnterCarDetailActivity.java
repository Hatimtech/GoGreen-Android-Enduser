package com.gogreen.main.Screens;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;


import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.gogreen.main.Adapter.CarBrandAdapter;
import com.gogreen.main.Adapter.CarModelAdapter;
import com.gogreen.main.Adapter.ColorAdapter;
import com.gogreen.main.Model.AddCarBrand.REQUEST.AddCarBrandRequest;
import com.gogreen.main.Model.AddCarBrand.RESPONSE.AddCarBrandResponseWrapper;
import com.gogreen.main.Model.AddCarModel.REQUEST.AddCarModelRequest;
import com.gogreen.main.Model.AddCarModel.RESPONSE.AddCarModelResponseWrapper;
import com.gogreen.main.Model.CarBrand.REQUEST.CarBrandRequest;
import com.gogreen.main.Model.CarBrand.RESPONSE.CarBrandResponseResult;
import com.gogreen.main.Model.CarBrand.RESPONSE.CarBrandResponseWrapper;
import com.gogreen.main.Model.CarModel.REQUEST.CarModelRequest;
import com.gogreen.main.Model.CarModel.RESPONSE.CarModelResponse;
import com.gogreen.main.Model.CarModel.RESPONSE.CarModelResponseResult;
import com.gogreen.main.Model.CarModel.RESPONSE.CarModelResponseWrapper;
import com.gogreen.main.Model.EnterCarDetail.REQUEST.EnterCarDetailRequest;
import com.gogreen.main.Model.EnterCarDetail.RESPONSE.EnterCarDetailResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EnterCarDetailActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner car_colorSpinner, car_brandSpinner, car_modelSpinner;
    private EditText car_plate_no, parking_bay_no;
    private Toolbar toolbar;
    private AppCompatTextView title;
    private ImageView back, car_icon;
    private Button proceed;
    private RadioButton suv, saloon;
    APIUtility apiUtility;
    String car_type = "SUV";
    String carColorName;
    String carmodelName;
    String carbrandName;
    String brandid;

    List<CarBrandResponseResult> carBrandList = new ArrayList<>();
    List<CarModelResponseResult> carModelList = new ArrayList<>();
    String[] colorList = {"CAR COLOUR", "Black", "Blue", "Brown", "Burgundy", "Gold", "Gray", "Orange", "Green", "Purple", "Red", "Silver", "White", "Tan", "Yellow", "Other Color"};
    ColorAdapter colorAdapter;
    CarBrandAdapter carBrandAdapter;
    CarModelAdapter carModelAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_car_detail);
        apiUtility = new APIUtility(this);
        InitView();
        getCarBrandList();

    }

    private void InitView() {
        car_plate_no = (EditText) findViewById(R.id.plate_no);
        parking_bay_no = (EditText) findViewById(R.id.parking_bay);
        car_icon = (ImageView) findViewById(R.id.car_Icon);
        car_brandSpinner = (Spinner) findViewById(R.id.car_brand);
        car_colorSpinner = (Spinner) findViewById(R.id.color);
        car_modelSpinner = (Spinner) findViewById(R.id.car_model);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        back = (ImageView) findViewById(R.id.back);
        proceed = (Button) findViewById(R.id.user_proceed);
        title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText(R.string.Enter_your_car_detail);
        back.setOnClickListener(this);
        proceed.setOnClickListener(this);
        colorAdapter = new ColorAdapter(this, colorList);
        car_colorSpinner.setAdapter(colorAdapter);
        car_brandSpinner.setOnItemSelectedListener(this);
        car_modelSpinner.setOnItemSelectedListener(this);
        car_colorSpinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.color:
                carColorName = colorList[position];
                break;

            case R.id.car_brand:
                carbrandName = carBrandList.get(position).getBrandId();
                getCarModelList(carbrandName);
                brandid = carbrandName;
                break;

            case R.id.car_model:
                carmodelName = carModelList.get(position).getModelId();
                if (carmodelName.equals("999")) {
//                    if (carModelList.size() != 2)
//                        showDialog1("", brandid);
                }

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                startActivity(new Intent(EnterCarDetailActivity.this, BookServiceActivity.class));
                finish();
                break;

            case R.id.user_proceed:
                validation();
                break;
        }

    }


    public void validation() {
        if (!carbrandName.equals("99")) {

            if (!carmodelName.equals("99")) {

                if (!carColorName.equals(colorList[0])) {

                    if (!TextUtils.isEmpty(car_plate_no.getText().toString().trim())) {

                        if (CommonUtils.isPatternValid(car_plate_no.getText().toString().trim())) {

                            enterCarDetail(car_plate_no.getText().toString().replace(" ", "").trim(), parking_bay_no.getText().toString().trim(), carColorName, car_type, carbrandName, carmodelName);

                        } else {
                            CommonUtils.alert(EnterCarDetailActivity.this, "Plate number should be alphanumeric");
                        }

                    } else {

                        CommonUtils.alert(EnterCarDetailActivity.this, getString(R.string.carPlateNumber_alert));
                    }
                } else {
                    CommonUtils.alert(EnterCarDetailActivity.this, getString(R.string.carColor_alert));
                }
            } else {
                CommonUtils.alert(EnterCarDetailActivity.this, getString(R.string.carModel_alert));
            }
        } else {
            CommonUtils.alert(EnterCarDetailActivity.this, getString(R.string.carBrand_alert));
        }
    }


    public void onRadioButtonClicked(View v) {

        suv = (RadioButton) findViewById(R.id.suv);
        saloon = (RadioButton) findViewById(R.id.saloon);
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()) {

            case R.id.suv:
                if (checked)
                    car_icon.setImageResource(R.mipmap.suv_image);
                car_type = "SUV";
                break;

            case R.id.saloon:
                if (checked)
                    car_icon.setImageResource(R.mipmap.saloon_image);
                car_type = "Saloon";
                break;


        }
    }


    public void enterCarDetail(String carPlateNo, String parkingNumber, String carColor, String carType, String carBrand, String carModel) {
        EnterCarDetailRequest enterCarDetailRequest = new EnterCarDetailRequest();
        enterCarDetailRequest.setAppKey(Constants.APP_KEY);
        enterCarDetailRequest.setMethod("insert_car_detail");
        enterCarDetailRequest.setCarType(carType);
        enterCarDetailRequest.setApartmentNumber(Preferences.getPreference(getApplicationContext(), PrefEntity.APARTMENTNUMBER));
        enterCarDetailRequest.setCarBrand(carBrand);
        enterCarDetailRequest.setCarModel(carModel);
        enterCarDetailRequest.setStreetId(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_STREET_ID));
        enterCarDetailRequest.setUserId(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));
        enterCarDetailRequest.setLocalityId(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_LOCALITY_ID));
        enterCarDetailRequest.setCityId(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_CITY_ID));
        enterCarDetailRequest.setCarColor(carColor);
        enterCarDetailRequest.setPlateNumber(carPlateNo);
        enterCarDetailRequest.setParkingNumber(parkingNumber);


        apiUtility.enterCarDetail(EnterCarDetailActivity.this, enterCarDetailRequest, true, new APIUtility.APIResponseListener<EnterCarDetailResponseWrapper>() {
            @Override
            public void onReceiveResponse(EnterCarDetailResponseWrapper response) {
                if (response != null) {

                    AppEventsLogger logger = AppEventsLogger.newLogger(EnterCarDetailActivity.this);
                    logger.logEvent("Car Registration");
                    logAddToWishlistEvent("Go Green Car wash","GOGREEN-5544","product","AED",10.34);

                    Intent intent = new Intent(EnterCarDetailActivity.this, BookServiceActivity.class);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(EnterCarDetailActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(EnterCarDetailResponseWrapper response) {
                CommonUtils.alert(EnterCarDetailActivity.this, response.getResponse().getMessage());

            }
        });

    }


    public void getCarModelList(String id) {
        if (!id.equals("99") && !id.equals("999")) {
            CarModelRequest carModelRequest = new CarModelRequest();
            carModelRequest.setMethod("get_car_model");
            carModelRequest.setBrandId(id);
            carModelRequest.setAppKey(Constants.APP_KEY);

            apiUtility.getCarModel(EnterCarDetailActivity.this, carModelRequest, true, new APIUtility.APIResponseListener<CarModelResponseWrapper>() {
                @Override
                public void onReceiveResponse(CarModelResponseWrapper response) {

                    if (response != null) {
                        carModelList.clear();
                        carModelList.addAll(response.getResponse().getResult());
                        carModelAdapter = new CarModelAdapter(EnterCarDetailActivity.this, carModelList, true);
                        car_modelSpinner.setAdapter(carModelAdapter);

                    }
                }

                @Override
                public void onResponseFailed() {
                    CommonUtils.alert(EnterCarDetailActivity.this, getString(R.string.VolleyError));
                }

                @Override
                public void onStatusFalse(CarModelResponseWrapper response) {
                    carModelList.clear();
                    carModelAdapter = new CarModelAdapter(EnterCarDetailActivity.this, carModelList, true);
                    car_modelSpinner.setAdapter(carModelAdapter);
                    CommonUtils.alert(EnterCarDetailActivity.this, response.getResponse().getMessage());

                }
            });
        } else if (id.equals("999")) {
//            showDialog("", "");
        } else {
            CarModelResponseResult carModelResponseResult = new CarModelResponseResult();
            carModelList.clear();
            carModelAdapter = new CarModelAdapter(EnterCarDetailActivity.this, carModelList, false);
            car_modelSpinner.setAdapter(carModelAdapter);
        }
    }


    public void getCarBrandList() {
        CarBrandRequest carBrandRequest = new CarBrandRequest();
        carBrandRequest.setMethod("get_car_brand");
        carBrandRequest.setAppKey(Constants.APP_KEY);

        apiUtility.getCarBrand(EnterCarDetailActivity.this, carBrandRequest, true, new APIUtility.APIResponseListener<CarBrandResponseWrapper>() {
            @Override
            public void onReceiveResponse(CarBrandResponseWrapper response) {
                if (response != null) {
                    carBrandList.clear();
                    carBrandList.addAll(response.getResponse().getResult());
                    carBrandAdapter = new CarBrandAdapter(EnterCarDetailActivity.this, carBrandList);
                    car_brandSpinner.setAdapter(carBrandAdapter);

                }

            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(EnterCarDetailActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(CarBrandResponseWrapper response) {
                CommonUtils.alert(EnterCarDetailActivity.this, response.getResponse().getMessage());

            }
        });

    }


    void showDialog(final String carBrand, final String carModel) {
        final Dialog dialog = new Dialog(EnterCarDetailActivity.this);
        dialog.setContentView(R.layout.add_model_brand_dailog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText CarBrand = dialog.findViewById(R.id.user_email);
        final EditText CarModel = dialog.findViewById(R.id.user_mobile);
        ImageView close = dialog.findViewById(R.id.cancle);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getCarBrandList();

            }
        });


        final Button dialogButton = dialog.findViewById(R.id.dialog_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(CarBrand.getText().toString().trim())) {
                    if (!TextUtils.isEmpty(CarModel.getText().toString().trim())) {
                        AddCarBrandRequest addCarBrandRequest = new AddCarBrandRequest();
                        addCarBrandRequest.setAppKey(Constants.APP_KEY);
                        addCarBrandRequest.setBrandName(CarBrand.getText().toString());
                        addCarBrandRequest.setModelName(CarModel.getText().toString());
                        addCarBrandRequest.setMethod("add_brand");
                        addCarBrandRequest.setType("user");

                        apiUtility.addCarBrand(EnterCarDetailActivity.this, addCarBrandRequest, true, new APIUtility.APIResponseListener<AddCarBrandResponseWrapper>() {
                            @Override
                            public void onReceiveResponse(AddCarBrandResponseWrapper response) {
                                if (response != null) {
                                    dialog.dismiss();
                                    getCarBrandList();
                                }
                            }

                            @Override
                            public void onResponseFailed() {
                                CommonUtils.alert(EnterCarDetailActivity.this, getString(R.string.VolleyError));
                            }

                            @Override
                            public void onStatusFalse(AddCarBrandResponseWrapper response) {
                                CommonUtils.alert(EnterCarDetailActivity.this, response.getResponse().getMessage());

                            }
                        });

                    } else {
                        CarModel.setError(getString(R.string.carModel_alert));
                    }

                } else {
                    CarBrand.setError(getString(R.string.carBrand_alert));

                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }


    void showDialog1(final String CarModel, final String CarBrandId) {
        final Dialog dialog = new Dialog(EnterCarDetailActivity.this);
        dialog.setContentView(R.layout.add_model_name);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText carModel = dialog.findViewById(R.id.user_mobile);
        ImageView close = dialog.findViewById(R.id.cancle);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getCarModelList(CarBrandId);

            }
        });


        final Button dialogButton = dialog.findViewById(R.id.dialog_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(carModel.getText().toString().trim())) {

                    AddCarModelRequest addCarModelRequest = new AddCarModelRequest();
                    addCarModelRequest.setAppKey(Constants.APP_KEY);
                    addCarModelRequest.setBrandId(CarBrandId);
                    addCarModelRequest.setModelName(carModel.getText().toString());
                    addCarModelRequest.setMethod("add_model");
                    addCarModelRequest.setType("user");

                    apiUtility.addCarModel(EnterCarDetailActivity.this, addCarModelRequest, true, new APIUtility.APIResponseListener<AddCarModelResponseWrapper>() {
                        @Override
                        public void onReceiveResponse(AddCarModelResponseWrapper response) {
                            if (response != null) {
                                dialog.dismiss();
                                getCarModelList(CarBrandId);
                            }
                        }

                        @Override
                        public void onResponseFailed() {

                            CommonUtils.alert(EnterCarDetailActivity.this, getString(R.string.VolleyError));
                        }

                        @Override
                        public void onStatusFalse(AddCarModelResponseWrapper response) {

                            CommonUtils.alert(EnterCarDetailActivity.this, response.getResponse().getMessage());

                        }
                    });
                } else {
                    carModel.setError(getString(R.string.carModel_alert));
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EnterCarDetailActivity.this, BookServiceActivity.class));
        finish();

    }

    public void logAddToWishlistEvent (String contentData, String contentId, String contentType, String currency, double price) {
        Bundle params = new Bundle();
        AppEventsLogger logger = AppEventsLogger.newLogger(EnterCarDetailActivity.this);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT, contentData);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, contentType);
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_WISHLIST, price, params);
    }




}
