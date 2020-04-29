package com.gogreen.main.Screens;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FAQActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private AppCompatTextView title;
    private ImageView back;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText("FAQs");
        webView=(WebView)findViewById(R.id.webview);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        if(  CommonUtils.isNetworkAvailable(FAQActivity.this))
            webView.loadUrl("http://13.126.37.218/gogreen/gogreen-info.html");
        else{
            CommonUtils.displayNetworkAlert(FAQActivity.this,false);
        }

    }

    @Override
    public void onClick(View v) {

        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
