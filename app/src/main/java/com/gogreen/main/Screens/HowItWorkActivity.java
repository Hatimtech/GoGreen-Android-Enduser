package com.gogreen.main.Screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gogreen.main.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class HowItWorkActivity extends BaseActivity implements View.OnClickListener {


    private Toolbar toolbar;
    private AppCompatTextView title;
    private Button subscribeNow;
    private LinearLayout Addcity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_work);
        InitView();

    }

    private void InitView() {


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        subscribeNow = (Button) findViewById(R.id.subscribe);
        Addcity=(LinearLayout)findViewById(R.id.AddCity);
        title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText(R.string.How_it_Work);
        setSupportActionBar(toolbar);
        subscribeNow.setOnClickListener(this);
        Addcity.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.subscribe:
                nextActivity();

                break;

            case R.id.AddCity:
                nextActivity();

                break;

        }
    }

    private void nextActivity() {
        Intent intent = new Intent(HowItWorkActivity.this, CityActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
