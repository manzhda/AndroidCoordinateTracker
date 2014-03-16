package com.mda.coordinatetracker.receivers;

import android.util.Log;

/**
 * User: mda
 * Date: 4/3/13
 * Time: 10:21 AM
 */
public abstract class AbstractCurrentLocationListener extends AbstractErrorListener implements CurrentLocationReceiver.CurrentLocationListener {
    @Override
    public void onLocationRetrieved(double lat, double lng) {
        Log.d(TAG, "Location: lat = " + lat + ", lng = " + lng);
    }
}
