package com.mda.coordinatetracker.receivers;

import android.util.Log;

/**
 * User: mda
 * Date: 4/3/13
 * Time: 10:21 AM
 */
public abstract class AbstractAddressListener extends AbstractErrorListener implements AddressReceiver.AddressListener {
    public void onAddressRetrieved(String address) {
        Log.e(TAG, "Address: " + address);
    }
}
