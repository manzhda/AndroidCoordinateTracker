package com.mda.coordinatetracker.demos;

import android.app.Application;

public class SampleApplication extends Application {
    private static SampleApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static SampleApplication getInstance() {
        return mInstance;
    }
}
