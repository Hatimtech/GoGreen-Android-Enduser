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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.gogreen.main.Adapter.CarBrandAdapter;
import com.gogreen.main.Adapter.CarModelAdapter;
import com.gogreen.main.Adapter.ColorAdapter;
import com.gogreen.main.Model.AddCarBrand.REQUEST.AddCarBrandRequest;
import com.gogreen.main.Model.AddCarBrand.RESPONSE.AddCarBrandResponseWrapper;
import com.gogreen.main.Model.AddCarModel.REQUEST.AddCarModelRequest;
import com.gogreen.main.Model.AddCarModel.RESPONSE.AddCarModelResponseWrapper;
import com.gogreen.main.Model.AutoRenewals.REQUEST.AutoRenewRequest;
import com.gogreen.main.Model.AutoRenewals.RESPONSE.AutoRenewResponseWrapper;
import com.gogreen.main.Model.CarBrand.REQUEST.CarBrandRequest;
import com.gogreen.main.Model.CarBrand.RESPONSE.CarBrandResponseResult;
import com.gogreen.main.Model.CarBrand.RESPONSE.CarBrandResponseWrapper;
import com.gogreen.main.Model.CarModel.REQUEST.CarModelRequest;
import com.gogreen.main.Model.CarModel.RESPONSE.CarModelResponseResult;
import com.gogreen.main.Model.CarModel.RESPONSE.CarModelResponseWrapper;
import com.gogreen.main.Model.EditCarDetail.REQUEST.EditCarDetailRequest;
import com.gogreen.main.Model.EditCarDetail.RESPONSE.EditCarDetailResponseWrapper;
import com.gogreen.main.Model.EnterCarDetail.REQUEST.EnterCarDetailRequest;
import com.gogreen.main.Model.EnterCarDetail.RESPONSE.EnterCarDetailResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditCarDetailActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner car_colorSpinner, car_brandSpinner, car_modelSpinner;
    private EditText car_plate_no, parking_bay_no;
    private Toolbar toolbar;
    private AppCompatTextView title;
    private ImageView back, car_icon, autoRenewImage;
    private Button proceed;
    private RadioButton suv, saloon;
    APIUtility apiUtility;
    String carColorName;
    String carmodelName;
    String carbrandName;
    String brandid;
    LinearLayout autoRenew;
    boolean isAutoRenewal = false;
    List<CarBrandResponseResult> carBrandList = new ArrayList<>();
    List<CarModelResponseResult> carModelList = new ArrayList<>();
    String[] colorList = {"CAR COLOUR", "Black", "Blue", "Brown", "Burgundy", "Gold", "Gray", "Orange", "Green", "Purple", "Red", "Silver", "White", "Tan", "Yellow", "Other Color","a"};
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
      if(getIntent().hasExtra("active")) {
          getSetAutoRenewal("3");
          autoRenew.setVisibility(View.VISIBLE);
      }else{
          autoRenew.setVisibility(View.GONE);
      }


    }

    private void InitView() {
        autoRenew = (LinearLayout) findViewById(R.id.autoRenewals);
        autoRenew.setVisibility(View.VISIBLE);
        autoRenewImage = (ImageView) findViewById(R.id.autoRenewalsImg);

        suv = (RadioButton) findViewById(R.id.suv);
        saloon = (RadioButton) findViewById(R.id.saloon);
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
        car_plate_no.setText(getIntent().getStringExtra("plateNo"));
        parking_bay_no.setText(getIntent().getStringExtra("parkingNumber"));
        if (getIntent().getStringExtra("CarType").equals("SUV")) {
            car_icon.setImageResource(R.mipmap.suv_image);
            suv.setChecked(true);
            saloon.setClickable(false);
        } else {
            car_icon.setImageResource(R.mipmap.saloon_image);
            saloon.setChecked(true);
            suv.setClickable(false);
        }
        back.setOnClickListener(this);
        proceed.setOnClickListener(this);
        autoRenew.setOnClickListener(this);
        colorList[16]=getIntent().getStringExtra("CarColor");
        colorAdapter = new ColorAdapter(this, colorList);
        car_colorSpinner.setAdapter(colorAdapter);
        car_brandSpinner.setOnItemSelectedListener(this);
        car_modelSpinner.setOnItemSelectedListener(this);
        car_colorSpinner.setOnItemSelectedListener(this);
        car_colorSpinner.setSelection(16);

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
                    if (carModelList.size() != 2)
                        showDialog1("", brandid);
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
                startActivity(new Intent(EditCarDetailActivity.this, BookServiceActivity.class));
                finish();
                break;

            case R.id.user_proceed:
                validation();
                break;

            case R.id.autoRenewals:
                if (isAutoRenewal) {
                   getSetAutoRenewal("1");
                } else {
                   getSetAutoRenewal("2");
                }
                break;
        }

    }


    public void validation() {
        if (!carbrandName.equals("99")) {

            if (!carmodelName.equals("99")) {

                if (!carColorName.equals(colorList[0])) {

                    if (!TextUtils.isEmpty(car_plate_no.getText().toString().trim())) {

                        if (CommonUtils.isPatternValid(car_plate_no.getText().toString().trim())) {

                            editCarDetail(car_plate_no.getText().toString().replace(" ", "").trim(), parking_bay_no.getText().toString().trim(), carColorName, carbrandName, carmodelName);

                        } else {

                            CommonUtils.alert(EditCarDetailActivity.this, "Plate number should be alphanumeric.");

                        }
                    } else {


                        CommonUtils.alert(EditCarDetailActivity.this, getString(R.string.carPlateNumber_alert));
                    }
                } else {
                    CommonUtils.alert(EditCarDetailActivity.this, getString(R.string.carColor_alert));
                }
            } else {
                CommonUtils.alert(EditCarDetailActivity.this, getString(R.string.carModel_alert));
            }
        } else {
            CommonUtils.alert(EditCarDetailActivity.this, getString(R.string.carBrand_alert));
        }
    }


    public void onRadioButtonClicked(View v) {

    }


    public void editCarDetail(String carPlateNo, String parkingNumber, String carColor, String carBrand, String carModel) {
        EditCarDetailRequest enterCarDetailRequest = new EditCarDetailRequest();
        enterCarDetailRequest.setAppKey(Constants.APP_KEY);
        enterCarDetailRequest.setMethod("edit_car_detail");
        enterCarDetailRequest.setApartmentNumber(Preferences.getPreference(getApplicationContext(), PrefEntity.APARTMENTNUMBER));
        enterCarDetailRequest.setCarBrand(carBrand);
        enterCarDetailRequest.setCarModel(carModel);
        enterCarDetailRequest.setCarID(getIntent().getStringExtra("carId"));
        enterCarDetailRequest.setStreetId(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_STREET_ID));
        enterCarDetailRequest.setUserId(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));
        enterCarDetailRequest.setLocalityId(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_LOCALITY_ID));
        enterCarDetailRequest.setCityId(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_CITY_ID));
        enterCarDetailRequest.setCarColor(carColor);
        enterCarDetailRequest.setPlateNumber(carPlateNo);
        enterCarDetailRequest.setParkingNumber(parkingNumber);


        apiUtility.editCarDetail(EditCarDetailActivity.this, enterCarDetailRequest, true, new APIUtility.APIResponseListener<EditCarDetailResponseWrapper>() {
            @Override
            public void onReceiveResponse(EditCarDetailResponseWrapper response) {
                if (response != null) {

                    startActivity(new Intent(EditCarDetailActivity.this, BookServiceActivity.class));
                    finish();
                }
            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(EditCarDetailActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(EditCarDetailResponseWrapper response) {
                CommonUtils.alert(EditCarDetailActivity.this, response.getResponse().getMessage());

            }
        });

    }


    public void getCarModelList(String id) {
        if (!id.equals("99") && !id.equals("999")) {
            final CarModelRequest carModelRequest = new CarModelRequest();
            carModelRequest.setMethod("get_car_model");
            carModelRequest.setBrandId(id);
            carModelRequest.setAppKey(Constants.APP_KEY);

            apiUtility.getCarModel(EditCarDetailActivity.this, carModelRequest, true, new APIUtility.APIResponseListener<CarModelResponseWrapper>() {
                @Override
                public void onReceiveResponse(CarModelResponseWrapper response) {

                    if (response != null) {
                        carModelList.clear();
                        CarModelResponseResult result= new CarModelResponseResult();
                        result.setModelId(getIntent().getStringExtra("CarModelID"));
                        result.setCarModelName(getIntent().getStringExtra("CarModel"));
                        carModelList.add(0,result);
                        carModelList.addAll(response.getResponse().getResult());
                        carModelAdapter = new CarModelAdapter(EditCarDetailActivity.this, carModelList, true);
                        car_modelSpinner.setAdapter(carModelAdapter);
                        car_modelSpinner.setSelection(1);



                    }
                }

                @Override
                public void onResponseFailed() {

                    CommonUtils.alert(EditCarDetailActivity.this, getString(R.string.VolleyError));
                }

                @Override
                public void onStatusFalse(CarModelResponseWrapper response) {
                    carModelList.clear();
                    carModelAdapter = new CarModelAdapter(EditCarDetailActivity.this, carModelList, true);
                    car_modelSpinner.setAdapter(carModelAdapter);
                    CommonUtils.alert(EditCarDetailActivity.this, response.getResponse().getMessage());

                }
            });
        } else if (id.equals("999")) {
            showDialog("", "");
        } else {
            CarModelResponseResult carModelResponseResult = new CarModelResponseResult();
            carModelList.clear();
            carModelAdapter = new CarModelAdapter(EditCarDetailActivity.this, carModelList, false);
            car_modelSpinner.setAdapter(carModelAdapter);
        }
    }


    public void getCarBrandList() {
        final CarBrandRequest carBrandRequest = new CarBrandRequest();
        carBrandRequest.setMethod("get_car_brand");
        carBrandRequest.setAppKey(Constants.APP_KEY);

        apiUtility.getCarBrand(EditCarDetailActivity.this, carBrandRequest, true, new APIUtility.APIResponseListener<CarBrandResponseWrapper>() {
            @Override
            public void onReceiveResponse(CarBrandResponseWrapper response) {
                if (response != null) {
                    carBrandList.clear();
                    CarBrandResponseResult result= new CarBrandResponseResult();
                    result.setBrandId(getIntent().getStringExtra("CarBrandID"));
                    result.setCarBrandName(getIntent().getStringExtra("CarBrand"));
                    carBrandList.add(0,result);
                    carBrandList.addAll(response.getResponse().getResult());
                    carBrandAdapter = new CarBrandAdapter(EditCarDetailActivity.this, carBrandList);
                    car_brandSpinner.setAdapter(carBrandAdapter);
                    car_brandSpinner.setSelection(1);


                }

            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(EditCarDetailActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(CarBrandResponseWrapper response) {
                CommonUtils.alert(EditCarDetailActivity.this, response.getResponse().getMessage());

            }
        });

    }


    void showDialog(final String carBrand, final String carModel) {
        final Dialog dialog = new Dialog(EditCarDetailActivity.this);
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

                        apiUtility.addCarBrand(EditCarDetailActivity.this, addCarBrandRequest, true, new APIUtility.APIResponseListener<AddCarBrandResponseWrapper>() {
                            @Override
                            public void onReceiveResponse(AddCarBrandResponseWrapper response) {
                                if (response != null) {
                                    dialog.dismiss();
                                    getCarBrandList();
                                }
                            }

                            @Override
                            public void onResponseFailed() {
                                CommonUtils.alert(EditCarDetailActivity.this, getString(R.string.VolleyError));
                            }

                            @Override
                            public void onStatusFalse(AddCarBrandResponseWrapper response) {
                                CommonUtils.alert(EditCarDetailActivity.this, response.getResponse().getMessage());

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
        final Dialog dialog = new Dialog(EditCarDetailActivity.this);
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

                    apiUtility.addCarModel(EditCarDetailActivity.this, addCarModelRequest, true, new APIUtility.APIResponseListener<AddCarModelResponseWrapper>() {
                        @Override
                        public void onReceiveResponse(AddCarModelResponseWrapper response) {
                            if (response != null) {
                                dialog.dismiss();
                                getCarModelList(CarBrandId);
                            }
                        }

                        @Override
                        public void onResponseFailed() {

                            CommonUtils.alert(EditCarDetailActivity.this, getString(R.string.VolleyError));
                        }

                        @Override
                        public void onStatusFalse(AddCarModelResponseWrapper response) {

                            CommonUtils.alert(EditCarDetailActivity.this, response.getResponse().getMessage());

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
        startActivity(new Intent(EditCarDetailActivity.this, BookServiceActivity.class));
        finish();
    }


    void getSetAutoRenewal(String mode) {
        AutoRenewRequest request = new AutoRenewRequest();
        request.setAppKey(Constants.APP_KEY);
        request.setCar_id(getIntent().getStringExtra("carId"));
        request.setMethod("change_renewal_mode");
        request.setMode(mode);


        apiUtility.getAndSetAutoRenewals(EditCarDetailActivity.this, request, true, new APIUtility.APIResponseListener<AutoRenewResponseWrapper>() {
            @Override
            public void onReceiveResponse(AutoRenewResponseWrapper response) {
                if (response != null) {
                    autoRenew.setVisibility(View.VISIBLE);
                    if (response.getResponse().getResult().getAuto_renewal().equals("1")) {
                        isAutoRenewal=false;
                                    autoRenewImage.setImageResource(R.mipmap.unselect_check_box_icon);
                    } else {
                        autoRenewImage.setImageResource(R.mipmap.select_check_box_icon);
                        isAutoRenewal=true;

                    }
                }
            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(EditCarDetailActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(AutoRenewResponseWrapper response) {
                autoRenew.setVisibility(View.GONE);
//                CommonUtils.alert(EditCarDetailActivity.this, response.getResponse().getMessage());
            }
        });


    }
}