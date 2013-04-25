package com.mda.coordinatetracker.receivers;

import android.content.Context;
import android.content.Intent;
import com.mda.coordinatetracker.CoordinateService;
import com.mda.coordinatetracker.geocoder.dto.Location;

/**
 * User: mda
 * Date: 3/29/13
 * Time: 5:18 PM
 */
public class LocationByAddressReceiver extends ErrorReceiver{
    public static interface LocationByAddressListener extends ErrorReceiver.ErrorListener{
        public void onLocationRetrieved(double lat, double lng);
    }

    private final LocationByAddressListener mLocationByAddressListener;

    public LocationByAddressReceiver(LocationByAddressListener locationByAddressListener) {
        super(locationByAddressListener);
        mLocationByAddressListener = locationByAddressListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        int status = intent.getIntExtra(CoordinateService.PARAM_STATUS, 0);
        switch (status){
            case CoordinateService.STATUS_LOCATION_RETRIEVED:
                Location location = (Location)intent.getParcelableExtra(CoordinateService.PARAM_RESULT);
                if(location != null){
                    mLocationByAddressListener.onLocationRetrieved(location.getLat(), location.getLng());
                }
                break;
        }
    }
}
