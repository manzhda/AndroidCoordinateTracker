package com.mda.coordinatetracker.receivers;

import android.util.Log;

/**
 * User: mda
 * Date: 4/3/13
 * Time: 10:21 AM
 */
public abstract class AbstractLocationByAddressListener extends AbstractErrorListener implements LocationByAddressReceiver.LocationByAddressListener {
    @Override
    public void onLocationRetrieved(double lat, double lng) {
        Log.e(TAG, "Location: lat = " + lat + ", lng = " + lng);
    }
}
