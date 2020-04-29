package com.gogreen.main.Screens;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gogreen.main.Fragment.ActivityFragment;
import com.gogreen.main.Fragment.CrewFragment;
import com.gogreen.main.Fragment.PackageFragment;
import com.gogreen.main.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView packge, crew, activity,carModel,parkingNumber;
    private FrameLayout frameLayout;
    private LinearLayout packageLayout, crewLayout, activityLayout;
    private Toolbar toolbar;
    private AppCompatTextView title;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        InitViews();
    }

    private void InitViews() {
        parkingNumber=(TextView)findViewById(R.id.parking_bay);
        carModel=(TextView)findViewById(R.id.carbrand);
        back=(ImageView)findViewById(R.id.back);
        packge = (TextView) findViewById(R.id.package1);
        crew = (TextView) findViewById(R.id.crew);
        activity = (TextView) findViewById(R.id.active);
        activityLayout = (LinearLayout) findViewById(R.id.active_layout);
        crewLayout = (LinearLayout) findViewById(R.id.crew_layout);
        packageLayout = (LinearLayout) findViewById(R.id.package_layout);
        frameLayout = (FrameLayout) findViewById(R.id.container);
        title = (AppCompatTextView) findViewById(R.id.title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title.setText(getString(R.string.order_detail));
        carModel.setText(getIntent().getStringExtra("carModel"));
        parkingNumber.setText(getIntent().getStringExtra("parking"));
        activityLayout.setOnClickListener(this);
        crewLayout.setOnClickListener(this);
        packageLayout.setOnClickListener(this);
        replaceFragment(new PackageFragment());
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.package_layout:

                packageLayout.setBackgroundResource(R.drawable.myorderdrawlablegreen);
                packge.setTextColor(getResources().getColor(R.color.white));
                replaceFragment(new PackageFragment());
                crewLayout.setBackgroundResource(R.drawable.myorderdrawale);
                activityLayout.setBackgroundResource(R.drawable.myorderdrawale);
                crew.setTextColor(getResources().getColor(R.color.textcolor));
                activity.setTextColor(getResources().getColor(R.color.textcolor));

                break;


            case R.id.crew_layout:

                crew.setTextColor(getResources().getColor(R.color.white));
                activity.setTextColor(getResources().getColor(R.color.textcolor));
                packge.setTextColor(getResources().getColor(R.color.textcolor));
                crewLayout.setBackgroundResource(R.drawable.myorderdrawlablegreen);
                packageLayout.setBackgroundResource(R.drawable.myorderdrawale);
                activityLayout.setBackgroundResource(R.drawable.myorderdrawale);
                replaceFragment(new CrewFragment());
                break;


            case R.id.active_layout:

                packge.setTextColor(getResources().getColor(R.color.textcolor));
                activity.setTextColor(getResources().getColor(R.color.white));
                crew.setTextColor(getResources().getColor(R.color.textcolor));
                activityLayout.setBackgroundResource(R.drawable.myorderdrawlablegreen);
                packageLayout.setBackgroundResource(R.drawable.myorderdrawale);
                crewLayout.setBackgroundResource(R.drawable.myorderdrawale);
                replaceFragment(new ActivityFragment());
                break;

            case R.id.back:

                finish();
                break;
        }

    }

    public void replaceFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("package", getIntent().getStringExtra("package"));
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        fragment.setArguments(bundle);
        ft.replace(R.id.container, fragment);
        ft.commit();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
