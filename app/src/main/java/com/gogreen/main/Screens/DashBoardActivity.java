package com.gogreen.main.Screens;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.gogreen.main.Fragment.MainFragment;
import com.gogreen.main.Model.ChangePassword.RESPONSE.ChangePasswordResponseWrapper;
import com.gogreen.main.Model.CheckVersion.REQUEST.CheckVersionRequest;
import com.gogreen.main.Model.CheckVersion.RESPONSE.CheckVersionResponseWrapper;
import com.gogreen.main.Model.UpdateToken.REQUEST.UpdateTokenRequest;
import com.gogreen.main.Model.UpdateToken.RESPONSE.UpdateTokenResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Utils.SearchViewFormatter;
import com.gogreen.main.Volley.APIUtility;

import java.math.BigDecimal;
import java.util.Currency;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DashBoardActivity extends BaseActivity implements DuoMenuView.OnMenuClickListener, View.OnClickListener {

    private ViewHolder mViewHolder;
    private ImageView signOut, home, myCar, myOrder, term_condition, rateUs, changePassword, inviteFriends, aboutUs, contactUs;
    private TextView userName, UserPhone, userEmail;
    private LinearLayout editProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        InitView();
        if (getIntent() != null) {
            if (getIntent().hasExtra("done")) {
                AppEventsLogger logger = AppEventsLogger.newLogger(DashBoardActivity.this);
                logger.logEvent("Package Activate");
                logger.logPurchase(BigDecimal.valueOf(10.34), Currency.getInstance("AED"));
                CommonUtils.alert(DashBoardActivity.this, getString(R.string.done));
            }
        }

        String version = "";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
            Log.d("VERSION NUMBER", version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

            checkForUpdates();

    }

    private void InitView() {

        signOut = (ImageView) findViewById(R.id.logout);
        editProfile = (LinearLayout) findViewById(R.id.editProfile);
        aboutUs = (ImageView) findViewById(R.id.about_us);
        contactUs = (ImageView) findViewById(R.id.contact_us);
        home = (ImageView) findViewById(R.id.Home);
        myCar = (ImageView) findViewById(R.id.Mycar);
        myOrder = (ImageView) findViewById(R.id.Myorder);
        term_condition = (ImageView) findViewById(R.id.terms_and_Condition);
        rateUs = (ImageView) findViewById(R.id.rate);
        changePassword = (ImageView) findViewById(R.id.changepassword);
        inviteFriends = (ImageView) findViewById(R.id.invtFriends);
        userEmail = (TextView) findViewById(R.id.user_email);
        userName = (TextView) findViewById(R.id.user_name);
        UserPhone = (TextView) findViewById(R.id.userNumber);

        userName.setText(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_NAME));
        userEmail.setText(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_EMAIL));
        UserPhone.setText(Preferences.getPreference(getApplicationContext(), PrefEntity.PHONE_NUMBER));
        signOut.setOnClickListener(this);
        rateUs.setOnClickListener(this);
        term_condition.setOnClickListener(this);
        myOrder.setOnClickListener(this);
        myCar.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        home.setOnClickListener(this);
        inviteFriends.setOnClickListener(this);
        contactUs.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        editProfile.setOnClickListener(this);


        mViewHolder = new ViewHolder();
        handleToolbar();
        handleDrawer();
        goToFragment(new MainFragment(), false);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.Home:
                mViewHolder = new ViewHolder();
                handleToolbar();
                handleDrawer();
                mViewHolder.mDuoDrawerLayout.closeDrawer();
                goToFragment(new MainFragment(), false);
                break;

            case R.id.logout:
                updateToken();

                break;

            case R.id.Mycar:

                mViewHolder.mDuoDrawerLayout.closeDrawer();
                if (Preferences.getPreference(getApplicationContext(), PrefEntity.USER_STREET_ID).equals("")) {
                    Intent intent2 = new Intent(DashBoardActivity.this, CityActivity.class);
                    intent2.putExtra("hi", "hi2");
                    startActivity(intent2);

                } else {
                    Intent intent2 = new Intent(DashBoardActivity.this, BookServiceActivity.class);
                    startActivity(intent2);
                }

                break;

            case R.id.Myorder:
                mViewHolder.mDuoDrawerLayout.closeDrawer();
                Intent intent2 = new Intent(DashBoardActivity.this, MyOrderActivity.class);
                startActivity(intent2);
                break;

            case R.id.terms_and_Condition:
                mViewHolder.mDuoDrawerLayout.closeDrawer();
                Intent intent3 = new Intent(DashBoardActivity.this, FAQActivity.class);
                startActivity(intent3);
                break;

            case R.id.rate:
                mViewHolder.mDuoDrawerLayout.closeDrawer();
                SearchViewFormatter.rateTheApp(DashBoardActivity.this);
                break;

            case R.id.about_us:
                mViewHolder.mDuoDrawerLayout.closeDrawer();
                Intent intent4 = new Intent(DashBoardActivity.this, TermsConditionsActivity.class);
                startActivity(intent4);
                break;

            case R.id.editProfile:
                mViewHolder.mDuoDrawerLayout.closeDrawer();
                Intent intent5 = new Intent(DashBoardActivity.this, EditProfileActivity.class);
                startActivity(intent5);
                break;

            case R.id.contact_us:
                mViewHolder.mDuoDrawerLayout.closeDrawer();
                Intent i1 = new Intent(Intent.ACTION_DIAL);
                String p = "tel:" + "971545866100";
                i1.setData(Uri.parse(p));
                startActivity(i1);
                break;

            case R.id.changepassword:
                mViewHolder.mDuoDrawerLayout.closeDrawer();
                Intent intent1 = new Intent(DashBoardActivity.this, ChangePasswordActivity.class);
                startActivity(intent1);
                break;


            case R.id.invtFriends:
                mViewHolder.mDuoDrawerLayout.closeDrawer();

                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Go Green ");
                    String sAux = "\n Go Green – A convenient car wash solution at your doorstep!\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=com.gogreen.main \n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Share"));
                } catch (Exception e) {

                }

                break;


        }
    }


    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

    }


    @Override
    public void onFooterClicked() {
        Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeaderClicked() {
        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
    }

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.replace(R.id.container, fragment).commit();
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {

        switch (position) {
            default:
                goToFragment(new MainFragment(), false);
                break;
        }

        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }


    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private Toolbar mToolbar;
        private AppCompatTextView title;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            title = (AppCompatTextView) mToolbar.findViewById(R.id.title);
            title.setText(R.string.home);
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onResume() {
        super.onResume();
     /* try {
          NotificationManager notifManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
          notifManager.cancelAll();
      } catch (Exception e) {
          e.printStackTrace();
      }*/
    }


    void updateToken() {
        UpdateTokenRequest request = new UpdateTokenRequest();
        request.setAppKey(Constants.APP_KEY);
        request.setD_type("Android");
        request.setMethod("update_device_token");
        request.setToken("");
        request.setUser_id(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));

        new APIUtility(this).updateToken(DashBoardActivity.this, request, true, new APIUtility.APIResponseListener<UpdateTokenResponseWrapper>() {
                    @Override
                    public void onReceiveResponse(UpdateTokenResponseWrapper response) {
                        ClearUserDetails();

                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut();
                        }
                        Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onResponseFailed() {

                    }

                    @Override
                    public void onStatusFalse(UpdateTokenResponseWrapper response) {

                    }
                }
        );


    }




    void checkForUpdates() {
        String version = "";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
            Log.d("VERSION NUMBER", version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        CheckVersionRequest request= new CheckVersionRequest();
        request.setAppKey(Constants.APP_KEY);
        request.setMethod("check_app_compatiblity");
        request.setDevice_type("android");
        request.setVersion(version);

       new APIUtility(this).checkVersion(this, request, false, new APIUtility.APIResponseListener<CheckVersionResponseWrapper>() {
           @Override
           public void onReceiveResponse(CheckVersionResponseWrapper response) {
               if(response!=null){

               }
           }

           @Override
           public void onResponseFailed() {

           }

           @Override
           public void onStatusFalse(CheckVersionResponseWrapper response) {
               showUpdateDialog();
           }
       });



    }




        void showUpdateDialog (){

            AlertDialog.Builder ad = new AlertDialog.Builder(DashBoardActivity.this);
            ad.setTitle(getString(R.string.app_name));
            ad.setMessage("A new version of app is available");
            ad.setCancelable(false);

            ad.setPositiveButton("Update App", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.gogreen.main"));
                    startActivity(i);
                }
            });

                ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finishAffinity();
                    }
                });

               ad.create();
               ad.show();

        }

    @Override
    protected void onRestart() {
        checkForUpdates();
        super.onRestart();
    }
}




