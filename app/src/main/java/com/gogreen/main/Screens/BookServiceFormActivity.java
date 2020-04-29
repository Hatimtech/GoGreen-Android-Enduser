package com.gogreen.main.Screens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.gogreen.main.Adapter.CarListAdapter;
import com.gogreen.main.Adapter.SelectedCarListSpinnerAdapter;
import com.gogreen.main.Adapter.UnfilledCarListAdapter;
import com.gogreen.main.Fragment.MonthlyPackageFragment;
import com.gogreen.main.Fragment.OneTimePackageFragment;
import com.gogreen.main.Interfaces.CarDataRepository;
import com.gogreen.main.Interfaces.SelectedCarListArray;

import com.gogreen.main.Model.SelectPackage.REQUEST.SelectPackageRequest;
import com.gogreen.main.Model.SelectPackage.RESPONSE.SelectPackageResponseResult;
import com.gogreen.main.Model.SelectPackage.RESPONSE.SelectPackageResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BookServiceFormActivity extends BaseActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener, Observer {
    private Button next;
    Fragment oneTimePackageFragment, monthlyPackageFragment;
    private TabLayout tabLayout;
    FrameLayout frameLayout;
    private TextView packageMoney, servingTime, termsConditions, terms1;
    private Spinner selectedCarListSpiner;
    private ImageView interior, exterior, termAndCondition;
    private Toolbar toolbar;
    private AppCompatTextView title;
    APIUtility apiUtility;
    List<SelectedCarListArray> selectedCarListArrays;
    SelectedCarListSpinnerAdapter selectedCarListSpinnerAdapter;
    UnfilledCarListAdapter unfilledCarListAdapter;
    private ScrollView scrollView;
    boolean hasPageChanged = false;
    public static int selectedCarPosition = 0;
    private Observable carDataObserver;
    public int selectedIndex = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service_form);
        apiUtility = new APIUtility(BookServiceFormActivity.this);
        carDataObserver = CarDataRepository.getInstance();
        carDataObserver.addObserver(this);
        InitView();

    }

//    define IDS

    private void InitView() {
        termsConditions = (TextView) findViewById(R.id.termscond);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText(R.string.book_a_Service);
        exterior = (ImageView) findViewById(R.id.exterior);
        interior = (ImageView) findViewById(R.id.enterior);
        selectedCarListSpiner = (Spinner) findViewById(R.id.selected_car_list);
        scrollView = (ScrollView) findViewById(R.id.scrollview);
        termAndCondition = (ImageView) findViewById(R.id.terms);
        packageMoney = (TextView) findViewById(R.id.moneypackage);
        servingTime = (TextView) findViewById(R.id.servingTime);
        servingTime.setText(Preferences.getPreference(getApplicationContext(), PrefEntity.START_SERVICING_TIME) + " - " + Preferences.getPreference(getApplicationContext(), PrefEntity.END_SERVICING_TIME));
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        next = (Button) findViewById(R.id.next);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        terms1 = (TextView) findViewById(R.id.terms1);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.oneTime));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.monthly), true);

        oneTimePackageFragment = new OneTimePackageFragment();
        monthlyPackageFragment = new MonthlyPackageFragment();


//        replace  fragment
        replaceFragment(monthlyPackageFragment);

        selectedCarListArrays = new ArrayList<>();
        selectedCarListSpinnerAdapter = new SelectedCarListSpinnerAdapter(this);
        selectedCarListSpiner.setAdapter(selectedCarListSpinnerAdapter);

//        spinner item selection


        selectedCarListSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hasPageChanged = true;
                selectedCarPosition = position;
                CarDataRepository.getInstance().refreshCarSelection();
                checkPackage();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        tabLayout.addOnTabSelectedListener(this);


//        click action

        exterior.setOnClickListener(this);
        interior.setOnClickListener(this);
        termAndCondition.setOnClickListener(this);
        next.setOnClickListener(this);
        termsConditions.setOnClickListener(this);
        terms1.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next:
                Log.d("Hi", new Gson().toJson(CarDataRepository.getInstance().getArrayList()));
                List<String> validate2 = new ArrayList<>();
                validate2 = CarDataRepository.getInstance().ValidateData();

                if (CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).getInteriorAmount().equals("0") && CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).getServicingType().equals("Exterior,Interior"))

                    CommonUtils.alert(BookServiceFormActivity.this, CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).getCarPackage() + getString(R.string.alert_interior_package));

                else {
                    if (validate2.size() > 0) {
                        if (CarDataRepository.getInstance().getArrayList().size() > selectedCarPosition + 1) {
                            hasPageChanged = true;
                            selectedCarPosition = selectedCarPosition + 1;
                            selectedCarListSpiner.setSelection(selectedCarPosition);
                            CarDataRepository.getInstance().refreshCarSelection();
                            scrollView.fullScroll(ScrollView.FOCUS_UP);
                            checkPackage();
                        } else {
                            showDialog(validate2);
                        }

                    } else {
// Event Log for facebook event

                        AppEventsLogger logger = AppEventsLogger.newLogger(BookServiceFormActivity.this);
                        logger.logEvent("Car Package");
                        logAddToCartEvent("Go Green Car wash", "GOGREEN-5544", "product", "AED", 10.34);
                        startActivity(new Intent(BookServiceFormActivity.this, OrderConfirmationActivity.class));
                    }
                }
                break;

            case R.id.enterior:
                CarDataRepository.getInstance().toggleInterior(selectedCarPosition);
                break;

            case R.id.exterior:
                CarDataRepository.getInstance().toggleExterior(selectedCarPosition);
                break;
            case R.id.terms:
                CarDataRepository.getInstance().toggleAgreement(selectedCarPosition);
                break;

            case R.id.termscond:
                Intent intent = new Intent(BookServiceFormActivity.this, TermsConditionsActivity.class);
                startActivity(intent);
                break;

            case R.id.terms1:
                Intent intent1 = new Intent(BookServiceFormActivity.this, TermsConditionsActivity.class);
                startActivity(intent1);
                break;
        }
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setCurrentTabFragment(tab.getPosition());
        if (tab.getPosition() == 0) {
            CarDataRepository.getInstance().setTabSelection(selectedCarPosition, "once");
        } else {
            CarDataRepository.getInstance().setTabSelection(selectedCarPosition, "monthly");
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void setCurrentTabFragment(int tabPosition) {

        switch (tabPosition) {
            case 0:
                replaceFragment(new OneTimePackageFragment());
                break;
            case 1:
                replaceFragment(new MonthlyPackageFragment());
                break;
        }
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


//    Observer Pattern

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CarDataRepository) {
            CarDataRepository carDataRepository = (CarDataRepository) o;

            // UPDATE TABLAYOUT
            if (carDataRepository.getArrayList().get(selectedCarPosition).getCarPackage().equals("once")) {
                tabLayout.getTabAt(0).select();
                if (getSupportFragmentManager().findFragmentById(R.id.frameLayout) instanceof MonthlyPackageFragment || hasPageChanged)
                    replaceFragment(new OneTimePackageFragment());
            } else {
                tabLayout.getTabAt(1).select();
                if (getSupportFragmentManager().findFragmentById(R.id.frameLayout) instanceof OneTimePackageFragment || hasPageChanged)
                    replaceFragment(new MonthlyPackageFragment());
            }

            if (hasPageChanged)
                hasPageChanged = false;
            //UPDATE BUTTONS
            if (carDataRepository.getArrayList().get(selectedCarPosition).isExteriorSelected()) {
                exterior.setBackgroundResource(R.drawable.check_box_drawable);
                exterior.setImageResource(R.mipmap.select_exterior_icon);

            } else {
                exterior.setBackgroundResource(R.drawable.unchecked_box_drawable);
                exterior.setImageResource(R.mipmap.unselect_exterior_icon);

            }

            if (carDataRepository.getArrayList().get(selectedCarPosition).isInteriorSelected()) {

                interior.setBackgroundResource(R.drawable.check_box_drawable);
                interior.setImageResource(R.mipmap.select_interior_icon);


            } else {
                interior.setBackgroundResource(R.drawable.unchecked_box_drawable);
                interior.setImageResource(R.mipmap.unselect_interior_icon);


            }

            //UPDATE AGREEMENT
            if (CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).isAgreed()) {
                termAndCondition.setImageResource(R.mipmap.select_check_box_icon);
            } else {
                termAndCondition.setImageResource(R.mipmap.unselect_check_box_icon);
            }

            setAmaount(CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).getResponse());

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


//    call package Api

    void getPackageDetail() {
        final SelectPackageRequest selectPackageRequest = new SelectPackageRequest();
        selectPackageRequest.setAppKey(Constants.APP_KEY);
        selectPackageRequest.setMethod("package");
        selectPackageRequest.setCarType(CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).getType());
        selectPackageRequest.setLocalityId(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_LOCALITY_ID));

        apiUtility.select_Package(BookServiceFormActivity.this, selectPackageRequest, true, new APIUtility.APIResponseListener<SelectPackageResponseWrapper>() {
            @Override
            public void onReceiveResponse(SelectPackageResponseWrapper response) {
                if (response != null) {
                    CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).setResponse(response);
                    CarDataRepository.getInstance().refreshCarSelection();
                    CarDataRepository.getInstance().setService(selectedCarPosition);
//                    CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).setPackageName(response.getResponse().getResult().get(selectedCarPosition).getPackageName());


                }


            }

            @Override
            public void onResponseFailed() {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BookServiceFormActivity.this);
                alertDialogBuilder.setTitle("Go Green").setMessage(getString(R.string.VolleyError));
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                alertDialogBuilder.create();
                alertDialogBuilder.show();


            }

            @Override
            public void onStatusFalse(SelectPackageResponseWrapper response) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BookServiceFormActivity.this);
                alertDialogBuilder.setTitle("Go Green").setMessage(response.getResponse().getMessage());
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                alertDialogBuilder.create();
                alertDialogBuilder.show();
            }
        });


    }


//    set Amount for package

    void setAmaount(SelectPackageResponseWrapper response) {
        if (response != null) {
            int amount = 0;
            int interiorAmount = 0;
            switch (CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).getCarPackage()) {

                case "once":
                    if (CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).isInteriorSelected()) {
                        amount = amount + Integer.parseInt(response.getResponse().getResult().get(0).getOneTimeResponse().getPriceInterior());
                        packageMoney.setText(String.valueOf(amount));
                        interiorAmount = amount;
                    }

                    if (CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).isExteriorSelected()) {
                        amount = amount + Integer.parseInt(response.getResponse().getResult().get(0).getOneTimeResponse().getPriceExterior());
                        packageMoney.setText(String.valueOf(amount));
                    }


                    break;
                case "monthly":

                    if (CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).isInteriorSelected()) {
                        switch (CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).getSelectedFrequency()) {
                            case 0:
//                                amount = amount + Integer.parseInt(response.getResponse().getResult().get(0).getMonthlyResponse().getInteriorOnes());
                                amount = amount + calculateAmount(Integer.parseInt(response.getResponse().getResult().get(0).getMonthlyResponse().getInteriorOnes()), CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).getSelectedMonthlyFrequency(), response.getResponse().getResult().get(0));
                                packageMoney.setText(String.valueOf(amount));
                                interiorAmount = amount;
                                break;

                            case 1:
//                                amount = amount + Integer.parseInt(response.getResponse().getResult().get(0).getMonthlyResponse().getInterRiorThrice());
                                amount = amount + calculateAmount(Integer.parseInt(response.getResponse().getResult().get(0).getMonthlyResponse().getInterRiorThrice()), CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).getSelectedMonthlyFrequency(), response.getResponse().getResult().get(0));
                                packageMoney.setText(String.valueOf(amount));
                                interiorAmount = amount;
                                break;
                            case 2:
//                                amount = amount + Integer.parseInt(response.getResponse().getResult().get(0).getMonthlyResponse().getInteriorFive());
                                amount = amount + calculateAmount(Integer.parseInt(response.getResponse().getResult().get(0).getMonthlyResponse().getInteriorFive()), CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).getSelectedMonthlyFrequency(), response.getResponse().getResult().get(0));
                                packageMoney.setText(String.valueOf(amount));
                                interiorAmount = amount;
                                break;
                        }
                    }

                    if (CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).isExteriorSelected()) {
                        switch (CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).getSelectedFrequency()) {
                            case 0:
//                                amount = amount + Integer.parseInt(response.getResponse().getResult().get(0).getMonthlyResponse().getExteriorOnes());
                                amount = amount + calculateAmount(Integer.parseInt(response.getResponse().getResult().get(0).getMonthlyResponse().getExteriorOnes()), CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).getSelectedMonthlyFrequency(), response.getResponse().getResult().get(0));
                                packageMoney.setText(String.valueOf(amount));
                                break;
                            case 1:
//                                amount = amount + Integer.parseInt(response.getResponse().getResult().get(0).getMonthlyResponse().getExteriorThrices());
                                amount = amount + calculateAmount(Integer.parseInt(response.getResponse().getResult().get(0).getMonthlyResponse().getExteriorThrices()), CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).getSelectedMonthlyFrequency(), response.getResponse().getResult().get(0));
                                packageMoney.setText(String.valueOf(amount));
                                break;
                            case 2:
//                                amount = amount + Integer.parseInt(response.getResponse().getResult().get(0).getMonthlyResponse().getExteriorFive());
                                amount = amount + calculateAmount(Integer.parseInt(response.getResponse().getResult().get(0).getMonthlyResponse().getExteriorFive()), CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).getSelectedMonthlyFrequency(), response.getResponse().getResult().get(0));
                                packageMoney.setText(String.valueOf(amount));
                                break;
                        }


                    }
            }
            CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).setTotalAmount(String.valueOf(amount));
            CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).setInteriorAmount(String.valueOf(interiorAmount));

        }
    }

    void checkPackage() {
        if (CarDataRepository.getInstance().getArrayList().get(selectedCarPosition).getResponse() == null) {
            getPackageDetail();
        }
    }


//    dailog

    void showDialog(List<String> validate) {
        final Dialog dialog = new Dialog(BookServiceFormActivity.this);
        dialog.setContentView(R.layout.dailog_car_list);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.car_list_recycle);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookServiceFormActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        unfilledCarListAdapter = new UnfilledCarListAdapter(validate, this);
        recyclerView.setAdapter(unfilledCarListAdapter);


        final TextView dialogButton = dialog.findViewById(R.id.dialog_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }

//    font implementation

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

// calculate Amount method


    int calculateAmount(int amount, int index, SelectPackageResponseResult result) {
        int money = 0;
        money = amount;
        switch (index) {
            case 0:
                money = (money * 1) - ((money * 1 * Integer.parseInt(result.getPercentageDiscount().getMonth_1())) / 100);
                break;
            case 1:
                money = (money * 2) - ((money * 2 * Integer.parseInt(result.getPercentageDiscount().getMonth_2())) / 100);
                break;
            case 2:
                money = (money * 3) - ((money * 3 * Integer.parseInt(result.getPercentageDiscount().getMonth_3())) / 100);
                break;
            case 3:
                money = (money * 4) - ((money * 4 * Integer.parseInt(result.getPercentageDiscount().getMonth_4())) / 100);
                break;
            case 4:
                money = (money * 5) - ((money * Integer.parseInt(result.getPercentageDiscount().getMonth_5()) * 5) / 100);
                break;
            case 5:
                money = (money * 6) - ((money * 6 * Integer.parseInt(result.getPercentageDiscount().getMonth_6())) / 100);
                break;
            case 6:
                money = (money * 7) - ((money * 7 * Integer.parseInt(result.getPercentageDiscount().getMonth_7())) / 100);

                break;
            case 7:
                money = (money * 8) - ((money * 8 * Integer.parseInt(result.getPercentageDiscount().getMonth_8())) / 100);
                break;
            case 8:
                money = (money * 9) - ((money * 9 * Integer.parseInt(result.getPercentageDiscount().getMonth_9())) / 100);
                break;
            case 9:
                money = (money * 10) - ((money * 10 * Integer.parseInt(result.getPercentageDiscount().getMonth_10())) / 100);
                break;
            case 10:
                money = (money * 11) - ((money * 11 * Integer.parseInt(result.getPercentageDiscount().getMonth_11())) / 100);
                break;
            case 11:
                money = (money * 12) - ((money * 12 * Integer.parseInt(result.getPercentageDiscount().getMonth_12())) / 100);
                break;
        }

        return money;

    }


//         Event Logger for Facebook Adds

    public void logAddToCartEvent(String contentData, String contentId, String contentType, String currency, double price) {
        Bundle params = new Bundle();
        AppEventsLogger logger = AppEventsLogger.newLogger(BookServiceFormActivity.this);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT, contentData);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, contentType);
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_CART, price, params);
    }
}


