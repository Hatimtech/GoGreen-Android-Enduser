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

public class TermsConditionsActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private AppCompatTextView title;
    private ImageView back;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);
        InitView();
    }

    void InitView() {
        webView = (WebView) findViewById(R.id.webview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText(R.string.term_condition);
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
      if(  CommonUtils.isNetworkAvailable(TermsConditionsActivity.this))
          webView.loadUrl("http://13.126.37.218/gogreen/terms-of-use1.html");
      else{
          CommonUtils.displayNetworkAlert(TermsConditionsActivity.this,false);
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
