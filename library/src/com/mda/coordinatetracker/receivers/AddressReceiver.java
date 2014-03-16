package com.mda.coordinatetracker.receivers;

import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.mda.coordinatetracker.CoordinateService;

/**
 * User: mda
 * Date: 3/29/13
 * Time: 5:18 PM
 */
public class AddressReceiver extends ErrorReceiver{
    public static interface AddressListener extends ErrorListener{
        public void onAddressRetrieved(String address, Location location);
    }

    private final AddressListener mAddressListener;

    public AddressReceiver(AddressListener addressListener) {
        super(addressListener);
        mAddressListener = addressListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        int status = intent.getIntExtra(CoordinateService.PARAM_STATUS, -1);
        switch (status){
            case CoordinateService.STATUS_ADDRESS_RETRIEVED:
                String address = intent.getStringExtra(CoordinateService.PARAM_RESULT);
                Location location = (Location) intent.getParcelableExtra(CoordinateService.PARAM_RESULT_SECOND);
                if (location != null && address != null) {
                    mAddressListener.onAddressRetrieved(address, location);
                } else {
                    mAddressListener.onLocationError();
                }
                break;
        }
    }
}
