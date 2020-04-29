package com.gogreen.main.Screens;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
import com.gogreen.main.R;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by AKASH SHUKLA on 28-May-18.
 */
public class SplashActivity extends BaseActivity {
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PackageInfo pinfo = null;
        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionNumber = pinfo.versionCode;
            String versionName = pinfo.versionName;
            version = findViewById(R.id.version);
            version.setText("Version " +versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.gogreen.main",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
//        AppEventsLogger logger = AppEventsLogger.newLogger(SplashActivity.this);
//        logger.logEvent("App Launched");
        startActivity();

    }


    private void startActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Preferences.getPreference(getApplicationContext(), PrefEntity.IsIntro).equals("true")) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                    finish();
                }
            }
        }, 3000);
    }


//       if (!TextUtils.isEmpty(Preferences.getPreference(SplashActivity.this, PrefEntity.USERID)))
//    //                {
//    startActivity(new Intent(SplashActivity.this,IntroActivity.class));
//    finish();
////                }
//
////                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
////                finish();
////            }
////*/
//}
//        }, 2500);
//                }

    //This function is create HashKey of Application
   /* public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));
                //Log.e("Key Hash=", key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }
*/
    @Override
    protected void onResume() {
        super.onResume();
        try {
            NotificationManager notifManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notifManager.cancelAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
