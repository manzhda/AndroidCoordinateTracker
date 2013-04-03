package com.mda.coordinatetracker.receivers;

import android.util.Log;

/**
 * User: mda
 * Date: 4/3/13
 * Time: 10:21 AM
 */
public abstract class AbstractErrorListener implements ErrorReceiver.ErrorListener {
    public static String TAG = AbstractErrorListener.class.getName();
    @Override
    public void onLocationError() {
        Log.e(TAG, "LocationError");
    }

    @Override
    public void onNetworkError() {
        Log.e(TAG, "NetworkError");
    }
}
