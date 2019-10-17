package com.htmitech.emportal.push;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * Created by heyang on 2017-2-24.
 */
public class TestActivityManager {

    private static TestActivityManager sInstance = new TestActivityManager();
    private WeakReference<Activity> sCurrentActivityWeakRef;


    private TestActivityManager() {

    }

    public static TestActivityManager getInstance() {
        return sInstance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }
}
