package com.gogreen.main.Volley;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.crashlytics.android.Crashlytics;
import com.gogreen.main.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import java.io.File;

public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    FirebaseAnalytics mFirebaseAnalytics;
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
               .setDefaultFontPath("fonts/Montserrat_Light.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = com.android.volley.toolbox.Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void clearApplicationData() {
        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }
    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }
}
