package com.mda.coordinatetracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * User: mda
 * Date: 3/29/13
 * Time: 5:18 PM
 */
public class CoordinateReceiver extends BroadcastReceiver{
    public static interface CoordinateListener{
        public void onCoordinateRetrieved(String coordinate);
        public void onLocationError();
        public void onNetworkError();
    }

    private final CoordinateListener mCoordinateListener;

    public CoordinateReceiver(CoordinateListener coordinateListener) {
        mCoordinateListener = coordinateListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(CoordinateService.PARAM_STATUS, 0);
        switch (status){
            case CoordinateService.STATUS_ADDRESS_RETRIEVED:
                String address = intent.getStringExtra(CoordinateService.PARAM_ADDRESS);
                mCoordinateListener.onCoordinateRetrieved(address);
                break;
            case CoordinateService.STATUS_LOCATION_ERROR:
                mCoordinateListener.onLocationError();
                break;
            case CoordinateService.STATUS_NETWORK_ERROR:
                mCoordinateListener.onNetworkError();
                break;
        }
    }
}
