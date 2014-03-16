package com.mda.coordinatetracker.receivers;

import android.location.Location;
import android.util.Log;

/**
 * User: mda
 * Date: 4/3/13
 * Time: 10:21 AM
 */
public class AddressListenerImpl extends AbstractErrorListener implements AddressReceiver.AddressListener {
    @Override
    public void onAddressRetrieved(String address, Location location) {
        Log.d(TAG, "Address: " + address + " Location: lat = " + location.getLatitude() + ", lng = " + location.getLongitude());
    }
}
