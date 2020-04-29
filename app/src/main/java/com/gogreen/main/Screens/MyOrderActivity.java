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
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gogreen.main.Fragment.ActiveMyOrderFragment;
import com.gogreen.main.Fragment.ExpiredOrderFragment;
import com.gogreen.main.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MyOrderActivity extends BaseActivity implements View.OnClickListener {

    private TextView active, expired;
    private FrameLayout frameLayout;
    private LinearLayout activeLayout, expiredLayout;
    private Toolbar toolbar;
    private AppCompatTextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        InitViews();

    }

    private void InitViews() {


        active = (TextView) findViewById(R.id.active);
        expired = (TextView) findViewById(R.id.expired);
        activeLayout = (LinearLayout) findViewById(R.id.active_layout);
        expiredLayout = (LinearLayout) findViewById(R.id.expired_layout);
        frameLayout = (FrameLayout) findViewById(R.id.container);
        title = (AppCompatTextView) findViewById(R.id.title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title.setText(getString(R.string.my_orders));
        activeLayout.setOnClickListener(this);
        expiredLayout.setOnClickListener(this);
        replaceFragment(new ActiveMyOrderFragment());

    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.active_layout:

                activeLayout.setBackgroundResource(R.drawable.myorderdrawlablegreen);
                active.setTextColor(getResources().getColor(R.color.white));
                replaceFragment(new ActiveMyOrderFragment());
                expiredLayout.setBackgroundResource(R.drawable.myorderdrawale);
                expired.setTextColor(getResources().getColor(R.color.textcolor));
                break;


            case R.id.expired_layout:

                expired.setTextColor(getResources().getColor(R.color.white));
                active.setTextColor(getResources().getColor(R.color.textcolor));
                activeLayout.setBackgroundResource(R.drawable.myorderdrawale);
                expiredLayout.setBackgroundResource(R.drawable.myorderdrawlablegreen);
                replaceFragment(new ExpiredOrderFragment());

                break;
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
