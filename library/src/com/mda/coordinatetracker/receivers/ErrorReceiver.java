package com.mda.coordinatetracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.mda.coordinatetracker.CoordinateService;

/**
 * User: mda
 * Date: 3/29/13
 * Time: 5:18 PM
 */
public class ErrorReceiver extends BroadcastReceiver{
    public static interface ErrorListener {
        public void onLocationError();
        public void onNetworkError();
    }

    private final ErrorListener mErrorListener;

    public ErrorReceiver(ErrorListener coordinateListener) {
        mErrorListener = coordinateListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(CoordinateService.PARAM_STATUS, 0);
        switch (status){
            case CoordinateService.STATUS_LOCATION_ERROR:
                mErrorListener.onLocationError();
                break;
            case CoordinateService.STATUS_NETWORK_ERROR:
                mErrorListener.onNetworkError();
                break;
        }
    }
}
