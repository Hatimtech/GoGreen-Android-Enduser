package com.gogreen.main.FCM;

import android.util.Log;

import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;




public class MyFCMtokenService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("TOKEN->", refreshedToken);
        Preferences.setPreference(MyFCMtokenService.this, PrefEntity.DEVICETOKEN,refreshedToken);
    }
}
