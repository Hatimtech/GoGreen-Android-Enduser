package com.gogreen.main.Screens;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gogreen.main.Fragment.SelectCityFragment;
import com.gogreen.main.Fragment.SelectLocalityFragment;
import com.gogreen.main.Fragment.SelectStreetFragment;
import com.gogreen.main.Model.SelectCity.RESPONSE.CityResponseResult;
import com.gogreen.main.Model.SelectLocality.REQUEST.CityLocalityRequest;
import com.gogreen.main.Model.SelectLocality.RESPONSE.CityLocalityResponseResult;
import com.gogreen.main.Model.SelectLocality.RESPONSE.LocalityResponseWrapper;
import com.gogreen.main.Model.SelectStreet.REQUEST.CityStreetRequest;
import com.gogreen.main.Model.SelectStreet.RESPONSE.CityStreetResponseResult;
import com.gogreen.main.Model.SelectStreet.RESPONSE.StreetResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;
import com.google.gson.Gson;

import java.io.Serializable;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.facebook.FacebookSdk.getApplicationContext;


public class CityActivity extends BaseActivity implements View.OnClickListener, SelectLocalityFragment.SelectLocalityCallback, SelectStreetFragment.SelectStreet,
        SelectCityFragment.SelectCityCallback {
    private Toolbar toolbar;
    private AppCompatTextView title;
    private TextView can_not_find, click;
    private ImageView back;
    SearchView searchView;
    APIUtility apiUtility;
    int fragment = 0;
    String city, localty, cityId, LocalityID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_list_activity);
        apiUtility = new APIUtility(CityActivity.this);
        InitView();
        setFragment();
        back.setVisibility(View.GONE);

    }


    public void setFragment() {


        android.support.v4.app.FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_to_right, R.anim.exit_to_left);
        transaction.add(R.id.city_fragment_container, new SelectCityFragment());
        transaction.commit();
    }


    public void InitView() {

        can_not_find = (TextView) findViewById(R.id.cant_find);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        back = (ImageView) findViewById(R.id.back);
        click = (TextView) findViewById(R.id.click);
        title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText(R.string.city);
        title.setTextColor(getResources().getColor(R.color.white));
        back.setOnClickListener(this);
        can_not_find.setOnClickListener(this);
        click.setOnClickListener(this);
        setSupportActionBar(toolbar);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        searchView = findViewById(R.id.action_filter_search);
        final MenuItem myActionMenuItem = menu.findItem(R.id.action_filter_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setBackgroundResource(R.drawable.search_bar_drawlae);
        searchView.setMaxWidth(Integer.MAX_VALUE);


        final SearchView finalSearchView = searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!finalSearchView.isIconified()) {
                    finalSearchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (SelectCityFragment.cityListAdapter != null)
                    SelectCityFragment.cityListAdapter.getFilter().filter(s);

                if (SelectLocalityFragment.localityListAdapter != null)
                    SelectLocalityFragment.localityListAdapter.getFilter().filter(s);

                if (SelectStreetFragment.streetListAdapter != null)
                    SelectStreetFragment.streetListAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }


    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getSupportFragmentManager().popBackStackImmediate();
                break;

            case R.id.click:
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@gogreen-uae.com"});
                if (fragment == 0)
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Request: Add City");
                if (fragment == 1)
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Request: Add Location");
                if (fragment == 2)
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Request: Add Street");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                intent.setPackage("com.google.android.gm");
                startActivity(intent);
                break;
        }


    }


    @Override
    public void onFragmentItemClick(CityResponseResult result, int pos) {
        localityList(result.getId(), result.getName());
        city = result.getName();
        cityId = result.getId();
        Preferences.setPreference(getApplicationContext(), PrefEntity.CITY_ID, result.getId());
        Preferences.setPreference(getApplicationContext(), PrefEntity.CITY, result.getName());
    }


    @Override
    public void attachSelectLocalityFragment() {
        fragment = 1;
        back.setVisibility(View.VISIBLE);
        can_not_find.setText(getString(R.string.can_not_find_locality));
    }

    @Override
    public void detachSelectLocatityFragment() {
        fragment = 0;
        back.setVisibility(View.GONE);
        can_not_find.setText(getString(R.string.can_not_find));
        title.setText(getString(R.string.select_city));
    }

    @Override
    public void onFragmentItemClickLocality(CityLocalityResponseResult result, int pos) {
        getStreet(result.getId(), result.getName());
        localty = result.getName();
        LocalityID = result.getId();
        Preferences.setPreference(getApplicationContext(), PrefEntity.LOCALITY_ID, result.getId());
        Preferences.setPreference(getApplicationContext(), PrefEntity.LOCALITY, result.getName());

    }


    @Override
    public void attachSelectStreetFragment() {
        can_not_find.setText(getString(R.string.can_not_find_street));
        back.setVisibility(View.VISIBLE);
        fragment = 2;
    }


    @Override
    public void detachSelectStreetFragment() {
        can_not_find.setText(getString(R.string.can_not_find_locality));
        title.setText(R.string.aelect_locality);
        back.setVisibility(View.VISIBLE);
        fragment = 1;
    }


    @Override
    public void onFragmentItemClickStreet(CityStreetResponseResult result, int pos) {
        Preferences.setPreference(getApplicationContext(), PrefEntity.USER_STREET_ID, result.getId());
        Preferences.setPreference(getApplicationContext(), PrefEntity.USER_STREET, result.getName());
        Preferences.setPreference(getApplicationContext(), PrefEntity.USER_CITY_ID, cityId);
        Preferences.setPreference(getApplicationContext(), PrefEntity.USER_LOCALITY_ID, LocalityID);
        Preferences.setPreference(getApplicationContext(), PrefEntity.USER_LOCALITY, localty);
        Preferences.setPreference(getApplicationContext(), PrefEntity.USER_CITY, city);
        Preferences.setPreference(getApplicationContext(), PrefEntity.PAYMENTTYPE, result.getPayment_type());
        showDialog("");
    }


    void showDialog(final String ApartmentNumber) {
        final Dialog dialog = new Dialog(CityActivity.this);
        dialog.setContentView(R.layout.apartment_number);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText apartNumber = dialog.findViewById(R.id.user_mobile);
        ImageView close = dialog.findViewById(R.id.cancle);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });


        final Button dialogButton = dialog.findViewById(R.id.dialog_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(apartNumber.getText().toString().trim())) {
                    dialog.dismiss();

                    if (getIntent() != null) {
                        if (getIntent().hasExtra(getIntent().getStringExtra("hi"))) {
                            if (getIntent().getStringExtra("hi").equals("hi")) {
                                Intent intent = new Intent(CityActivity.this, DashBoardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(CityActivity.this, BookServiceActivity.class);
                                startActivity(intent);
                            }
                        }else{
                            Intent intent = new Intent(CityActivity.this, BookServiceActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(CityActivity.this, BookServiceActivity.class);
                        startActivity(intent);
                    }

                    Preferences.setPreference(getApplicationContext(), PrefEntity.APARTMENTNUMBER, apartNumber.getText().toString());


                } else {
                    apartNumber.setError(getString(R.string.apartment_alert));
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }


    public void localityList(final String id, final String name) {
        CityLocalityRequest localityRequest = new CityLocalityRequest();
        localityRequest.setApp_key(Constants.APP_KEY);
        localityRequest.setMethod("get_locality");
        localityRequest.setCityId(id);


        apiUtility.getCityLocality(CityActivity.this, localityRequest, true, new APIUtility.APIResponseListener<LocalityResponseWrapper>() {
            @Override
            public void onReceiveResponse(LocalityResponseWrapper response) {
                if (response != null) {
                    if (response.getResponse().getResult().size() > 0) {
                        resetSearch();
                        Preferences.setPreference(getApplicationContext(), PrefEntity.END_SERVICING_TIME, response.getResponse().getResult().get(0).getEndTime());
                        Preferences.setPreference(getApplicationContext(), PrefEntity.START_SERVICING_TIME, response.getResponse().getResult().get(0).getStartTime());
                        SelectLocalityFragment nextFrag = new SelectLocalityFragment();
                        android.support.v4.app.FragmentTransaction transaction;
                        transaction = getSupportFragmentManager().beginTransaction();
                        android.support.transition.Slide slideReturn = new android.support.transition.Slide();
                        slideReturn.setDuration(300);
                        slideReturn.setSlideEdge(Gravity.RIGHT);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", id);
                        bundle.putString("name", name);
                        bundle.putSerializable("list", (Serializable) response.getResponse().getResult());
                        nextFrag.setArguments(bundle);
                        nextFrag.setEnterTransition(slideReturn);
                        transaction.add(R.id.city_fragment_container, nextFrag);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    } else {
                        CommonUtils.alert(CityActivity.this, getString(R.string.no_data));
                    }

                } else {
                    CommonUtils.alert(CityActivity.this, getString(R.string.no_data));
                }


            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(CityActivity.this, getString(R.string.VolleyError));
            }


            @Override
            public void onStatusFalse(LocalityResponseWrapper response) {
                CommonUtils.alert(CityActivity.this, response.getResponse().getMessage());
            }


        });
    }


    public void getStreet(final String id, final String name) {
        CityStreetRequest streetRequest = new CityStreetRequest();
        streetRequest.setApp_key(Constants.APP_KEY);
        streetRequest.setMethod("get_street");
        streetRequest.setLocalityId(id);


        apiUtility.getCityStreet(CityActivity.this, streetRequest, true, new APIUtility.APIResponseListener<StreetResponseWrapper>() {
            @Override
            public void onReceiveResponse(StreetResponseWrapper response) {
                if (response != null) {
                    if (response.getResponse().getResult().size() > 0) {
                        resetSearch();

                        SelectStreetFragment fragment = new SelectStreetFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction;
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        android.support.transition.Slide slideReturn = new android.support.transition.Slide();
                        slideReturn.setDuration(300);
                        slideReturn.setSlideEdge(Gravity.RIGHT);

                        CommonUtils.log("Alien", new Gson().toJson(response.getResponse().getResult()));
                        Bundle bundle = new Bundle();
                        bundle.putString("id", id);
                        bundle.putString("name", name);
                        bundle.putSerializable("list", (Serializable) response.getResponse().getResult());
                        fragment.setArguments(bundle);
                        fragment.setEnterTransition(slideReturn);
                        fragmentTransaction.add(R.id.city_fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }

                }
            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(CityActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(StreetResponseWrapper response) {
                CommonUtils.alert(CityActivity.this, response.getResponse().getMessage());
            }


        });
    }

    void resetSearch() {
        searchView.setQuery("", false);

        searchView.clearFocus();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

