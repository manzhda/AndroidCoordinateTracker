package com.mda.coordinatetracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.mda.coordinatetracker.CoordinateService;
import com.mda.coordinatetracker.geocoder.dto.Location;

/**
 * User: mda
 * Date: 3/29/13
 * Time: 5:18 PM
 */
class CoordinateReceiver extends BroadcastReceiver{
    public static interface CoordinateListener{
        public void onAddressRetrieved(String address);
        public void onLocationRetrieved(Double lat, Double lng);
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
                String address = intent.getStringExtra(CoordinateService.PARAM_RESULT);
                mCoordinateListener.onAddressRetrieved(address);
                break;
            case CoordinateService.STATUS_LOCATION_RETRIEVED:
                Location location = (Location)intent.getSerializableExtra(CoordinateService.PARAM_RESULT);
                if(location != null){
                    mCoordinateListener.onLocationRetrieved(location.getLat(), location.getLng());
                }
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
