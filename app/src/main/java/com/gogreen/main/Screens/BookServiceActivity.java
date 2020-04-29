package com.gogreen.main.Screens;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gogreen.main.Adapter.CarListAdapter;
import com.gogreen.main.Interfaces.CarDataRepository;
import com.gogreen.main.Interfaces.SelectedCarListArray;
import com.gogreen.main.Interfaces.SwipeControllerActions;
import com.gogreen.main.Interfaces.SwipeControllers;
import com.gogreen.main.Model.BookAService.REQUEST.BookAServiceRequest;
import com.gogreen.main.Model.BookAService.RESPONSE.BookAServiceResponseResult;
import com.gogreen.main.Model.BookAService.RESPONSE.BookAServiceResponseWrapper;
import com.gogreen.main.Model.DeleteCarList.REQUEST.DeleteCarListRequest;
import com.gogreen.main.Model.DeleteCarList.RESPONSE.DeleteCarListResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BookServiceActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView carListView;
    private CarListAdapter carListAdapter;
    private List<BookAServiceResponseResult> carList;
    private Toolbar toolbar;
    private ImageView addCar;
    LinearLayout nocarImag;
    private AppCompatTextView title;
    private Button proceed;
    SwipeRefreshLayout refreshLayout;
    APIUtility apiUtility;
    SwipeControllers swipeController = null;
    boolean isCar=false;


    private Observable carDataRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);
        apiUtility = new APIUtility(this);
        carDataRepo = CarDataRepository.getInstance();
        InitView();
    }


    private void InitView() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        proceed = (Button) findViewById(R.id.proceed);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText(R.string.book_a_Service);
        addCar = (ImageView) findViewById(R.id.bookcar);
        nocarImag = (LinearLayout) findViewById(R.id.no_car);
        addCar.setOnClickListener(this);
        proceed.setOnClickListener(this);
        carListView = (RecyclerView) findViewById(R.id.car_list);
        carListView.setNestedScrollingEnabled(false);
        carList = new ArrayList<>();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        carListView.setLayoutManager(linearLayoutManager);
        carListAdapter = new CarListAdapter(carList, this);
        carListView.setAdapter(carListAdapter);
        getCarList(true);
        refreshList();
        setupRecyclerView();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bookcar:
                if (CommonUtils.isNetworkAvailable(BookServiceActivity.this)) {
                    Intent intent = new Intent(getApplicationContext(), EnterCarDetailActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    CommonUtils.displayNetworkAlert(BookServiceActivity.this, false);
                }


                break;

            case R.id.proceed:
                List<SelectedCarListArray> list = new ArrayList<>();
                list = carListAdapter.getSelectedCars();
                if (list.size() > 0) {
                    CarDataRepository.getInstance().setArrayList(list);
                    BookServiceFormActivity.selectedCarPosition = 0;
                    Intent intent1 = new Intent(BookServiceActivity.this, BookServiceFormActivity.class);
                    startActivity(intent1);
                } else {
                    CommonUtils.alert(BookServiceActivity.this, getString(R.string.alert_one_car));
                }
                break;
        }


    }


    public void getCarList(boolean isProgress) {
        BookAServiceRequest bookAServiceRequest = new BookAServiceRequest();
        bookAServiceRequest.setMethod("car_list");
        bookAServiceRequest.setAppKey(Constants.APP_KEY);
        bookAServiceRequest.setUserId(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));


        apiUtility.bookAService(BookServiceActivity.this, bookAServiceRequest, isProgress, new APIUtility.APIResponseListener<BookAServiceResponseWrapper>() {
            @Override
            public void onReceiveResponse(BookAServiceResponseWrapper response) {
                refreshLayout.setRefreshing(false);
                if (response != null) {
                    if (response.getResponse().getResult().size() > 0) {
                        nocarImag.setVisibility(View.GONE);
                        carListView.setVisibility(View.VISIBLE);
                        carList.clear();
                        carList.addAll(response.getResponse().getResult());
                        carListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onResponseFailed() {
                refreshLayout.setRefreshing(false);
                nocarImag.setVisibility(View.VISIBLE);
                carListView.setVisibility(View.GONE);
                CommonUtils.alert(BookServiceActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(BookAServiceResponseWrapper response) {
                refreshLayout.setRefreshing(false);
                carList.clear();
                carListAdapter.notifyDataSetChanged();
                nocarImag.setVisibility(View.VISIBLE);
                carListView.setVisibility(View.GONE);
                if(!isCar) {
                    Intent intent = new Intent(BookServiceActivity.this, EnterCarDetailActivity.class);
                    startActivity(intent);
                    isCar=true;
                }

            }
        });

    }

    void refreshList() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                if (CommonUtils.isNetworkAvailable(BookServiceActivity.this)) {
                    getCarList(false);
                } else {
                    refreshLayout.setRefreshing(false);
                    CommonUtils.displayNetworkAlert(BookServiceActivity.this, false);
                }
            }
        });

        refreshLayout.setColorSchemeColors(Color.GREEN, Color.BLUE, Color.RED, Color.CYAN, Color.GRAY);
        refreshLayout.setDistanceToTriggerSync(100);// in dips
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

    }

    private void setupRecyclerView() {
        swipeController = new SwipeControllers(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                super.onLeftClicked(position);
                if (CommonUtils.isNetworkAvailable(BookServiceActivity.this)) {
                    Intent intent = new Intent(BookServiceActivity.this, EditCarDetailActivity.class);
                    intent.putExtra("carId", carList.get(position).getCarId());
                    intent.putExtra("plateNo", carList.get(position).getPlateNumber());
                    intent.putExtra("parkingNumber", carList.get(position).getParkingNumber());
                    intent.putExtra("CarType", carList.get(position).getCarType());
                    intent.putExtra("CarModel", carList.get(position).getCarModel());
                    intent.putExtra("CarBrand", carList.get(position).getCarBrand());
                    intent.putExtra("CarModelID", carList.get(position).getModel_id());
                    intent.putExtra("CarBrandID", carList.get(position).getBrand_id());
                    intent.putExtra("CarColor", carList.get(position).getCarColor());




                    if(carList.get(position).getIs_package().equals("2")){
                        intent.putExtra("active", "1");
                    }
                    startActivity(intent);
                    finish();

                } else {
                    CommonUtils.displayNetworkAlert(BookServiceActivity.this, false);
                }
            }

            @Override
            public void onRightClicked(int position) {
                if (carList.get(position).getIs_package().equals("2")) {
                    CommonUtils.alert(BookServiceActivity.this, getString(R.string.alert_package_exist));
                } else {
                    deleteCar(carList.get(position).getCarId());
                    carListAdapter.notifyItemRemoved(position);
                    carListAdapter.notifyItemChanged(position);
                }
            }

        });


        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(carListView);

        carListView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }


    void deleteCar(String carId) {
        DeleteCarListRequest deleteCarListRequest = new DeleteCarListRequest();
        deleteCarListRequest.setAppKey(Constants.APP_KEY);
        deleteCarListRequest.setMethod("remove_car");
        deleteCarListRequest.setCarId(carId);
        deleteCarListRequest.setUserId(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));

        apiUtility.deleteCarDetail(BookServiceActivity.this, deleteCarListRequest, true, new APIUtility.APIResponseListener<DeleteCarListResponseWrapper>() {
            @Override
            public void onReceiveResponse(DeleteCarListResponseWrapper response) {
                if (response != null) {
                    getCarList(true);
                }

            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(BookServiceActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(DeleteCarListResponseWrapper response) {
                CommonUtils.alert(BookServiceActivity.this, response.getResponse().getMessage());
            }
        });
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

