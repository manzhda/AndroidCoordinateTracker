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
public class CurrentLocationReceiver extends ErrorReceiver{
    public static interface CurrentLocationListener extends ErrorListener{
        public void onLocationRetrieved(double lat, double lng);
    }

    private final CurrentLocationListener mCurrentLocationListener;

    public CurrentLocationReceiver(CurrentLocationListener currentLocationListener) {
        super(currentLocationListener);
        mCurrentLocationListener = currentLocationListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        int status = intent.getIntExtra(CoordinateService.PARAM_STATUS, 0);
        switch (status){
            case CoordinateService.STATUS_LOCATION_RETRIEVED:
                Location location = (Location)intent.getParcelableExtra(CoordinateService.PARAM_RESULT);
                if(location != null){
                    mCurrentLocationListener.onLocationRetrieved(location.getLatitude(), location.getLongitude());
                }
                break;
        }
    }
}
