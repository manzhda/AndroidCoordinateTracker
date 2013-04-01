package com.mda.coordinatetracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

/**
 * User:
 * Date: 11/24/12
 * Time: 2:16 AM
 */
public class CoordinateService extends Service {
    public static final String ACTION_START = "com.mda.coordinatetracker.START";
    public static final String ACTION_STOP = "com.mda.coordinatetracker.STOP";

    public static final String FILTER_ACTION = "com.mda.coordinatetracker.FILTER_ACTION";

    public static final String PARAM_STATUS = "com.mda.coordinatetracker.PARAM_STATUS";
    public static final String PARAM_ADDRESS = "com.mda.coordinatetracker.PARAM_ADDRESS";

    public static final int STATUS_ADDRESS_RETRIEVED = 1001;
    public static final int STATUS_LOCATION_ERROR = 1002;
    public static final int STATUS_NETWORK_ERROR = 1003;

    private CoordinateManager mCoordManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mCoordManager = new CoordinateManager(getBaseContext(), new CoordinateManager.CoordinateListener() {
            @Override
            public void onAddressRetrieved(String address, CoordinateManager coordinateManager) {
                onResult(STATUS_ADDRESS_RETRIEVED, address);
            }

            @Override
            public void onLocationError(CoordinateManager coordinateManager) {
                onResult(STATUS_LOCATION_ERROR);
            }

            @Override
            public void onNetworkError(CoordinateManager coordinateManager) {
                onResult(STATUS_NETWORK_ERROR);
            }
        });
    }

    private void onResult(int status){
        Intent intent = new Intent(FILTER_ACTION);
        intent.putExtra(PARAM_STATUS, status);
        sendBroadcast(intent);
    }

    private void onResult(int status, String address){
        Intent intent = new Intent(FILTER_ACTION);
        intent.putExtra(PARAM_STATUS, status);
        intent.putExtra(PARAM_ADDRESS, address);
        sendBroadcast(intent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action.equals(ACTION_START)) {
            mCoordManager.setup();
        }
        else if (action.equals(ACTION_STOP)) {
            mCoordManager.stop();
            stopSelf();
        }

        return START_NOT_STICKY;
    }

    public static void startTrackLocation(Context context, CoordinateReceiver coordinateReceiver){
        IntentFilter locationIntentFilter = new IntentFilter(CoordinateService.FILTER_ACTION);

        context.registerReceiver(coordinateReceiver, locationIntentFilter);
        context.startService(new Intent(CoordinateService.ACTION_START));
    }

    public static void stopTrackLocation(Context context, CoordinateReceiver coordinateReceiver){
        context.unregisterReceiver(coordinateReceiver);
        context.startService(new Intent(CoordinateService.ACTION_STOP));
    }
}
