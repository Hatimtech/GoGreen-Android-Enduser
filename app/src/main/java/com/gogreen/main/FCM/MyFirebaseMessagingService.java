package com.gogreen.main.FCM;

/**
 * Created by abc on 4/9/2018.
 */

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gogreen.main.R;


import com.gogreen.main.Screens.DashBoardActivity;
import com.gogreen.main.Screens.PaymentTypeActivity;
import com.gogreen.main.Screens.SplashActivity;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;


import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null) {
            Log.w("data", new Gson().toJson(remoteMessage.getNotification()
            ));

            Log.w("data", new Gson().toJson(remoteMessage.getData()));
            Log.w("data", new Gson().toJson(remoteMessage.getData().get("title")));
            Log.w("data", new Gson().toJson(remoteMessage.getData().get("order_id")));

            String message = "";
            String title = "";
            String id = "";
            if (remoteMessage.getData().get("message") != null) {
                message = remoteMessage.getData().get("message").trim();
                title = remoteMessage.getData().get("title").trim();
                id = remoteMessage.getData().get("order_id").trim();

            } else {
                title = remoteMessage.getNotification().getTitle();
                message = remoteMessage.getNotification().getBody();
            }

            sendNotification(title, message, id);
        }

    }


    private void sendNotification(String title, String message, String id) {

        Intent intent = null;
        if (Preferences.getPreference_int(getApplicationContext(), PrefEntity.IS_LOGIN) == 1) {
            if (id != null && id != "") {
                intent = new Intent(getBaseContext(), PaymentTypeActivity.class);
                intent.putExtra("notification", id);
            } else {
                intent = new Intent(getBaseContext(), DashBoardActivity.class);
            }
        } else {
            Log.e("NOT", "LOGIN");
            intent = new Intent(getBaseContext(), SplashActivity.class);
        }


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        int notifyID = 1;
        String channelId = "channel-09876544";
        String channelName = "GoGreenAPP";
        int importance = NotificationManager.IMPORTANCE_HIGH;


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            mChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(mChannel);
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        notificationBuilder.setSmallIcon(R.drawable.app_icon);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        notificationBuilder.setContentText(message);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setChannelId(channelId);
        notificationBuilder.setContentIntent(pendingIntent);


        try {
            notificationManager.notify(getRequestCode(), notificationBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getRequestCode() {
        Random rnd = new Random();
        return 100 + rnd.nextInt(900000);
    }

}
