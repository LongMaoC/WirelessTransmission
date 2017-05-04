package com.myfinal.cxy.wirelesstransmission;

import android.app.Application;
import android.content.Context;

/**
 * Created by CXY on 2017/3/1.
 */

public class MyApplication extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

    }

    public static Context getAppContext() {
        return appContext;
    }
}
