package com.bdwater.waterservice;

import android.app.Application;

/**
 * Created by hegang on 18/4/13.
 */

public class CustomApplication extends Application {
    private static MainActivity mainActivity = null;

    public static void setMainActivity(MainActivity activity) {
        CustomApplication.mainActivity = activity;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }
}
