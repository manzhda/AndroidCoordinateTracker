package com.mda.coordinatetracker;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Parcelable;
import android.widget.Toast;
import com.mda.coordinatetracker.geocoder.GeocoderAsyncTask;
import com.mda.coordinatetracker.geocoder.dto.Location;
import com.mda.coordinatetracker.receivers.AddressReceiver;
import com.mda.coordinatetracker.receivers.CurrentLocationReceiver;
import com.mda.coordinatetracker.receivers.LocationByAddressReceiver;

/**
 * User:
 * Date: 11/24/12
 * Time: 2:16 AM
 */
public class CoordinateService extends Service {
    public static final String ACTION_START = "com.mda.coordinatetracker.START";
    public static final String ACTION_STOP = "com.mda.coordinatetracker.STOP";

    public static final String ACTION_DIRECT_GEOCODE = "com.mda.coordinatetracker.ACTION_DIRECT_GEOCODE";
    public static final String ACTION_GET_CURRENT_LOCATION = "com.mda.coordinatetracker.ACTION_GET_CURRENT_LOCATION";

    public static final String FILTER_ACTION = "com.mda.coordinatetracker.FILTER_ACTION";

    public static final String PARAM_STATUS = "com.mda.coordinatetracker.PARAM_STATUS";
    public static final String PARAM_RESULT = "com.mda.coordinatetracker.PARAM_RESULT";
    public static final String PARAM_REQUESTED_ADDRESS = "com.mda.coordinatetracker.PARAM_REQUESTED_ADDRESS";

    public static final int STATUS_ADDRESS_RETRIEVED = 1001;
    public static final int STATUS_LOCATION_RETRIEVED = 1002;
    public static final int STATUS_LOCATION_ERROR = 1003;
    public static final int STATUS_NETWORK_ERROR = 1004;

    private CoordinateManager mCoordManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mCoordManager = new CoordinateManager(getBaseContext(), new CoordinateManager.CoordinateListener() {
//            @Override
//            public void onAddressRetrieved(String address, CoordinateManager coordinateManager) {
//                onResult(STATUS_ADDRESS_RETRIEVED, address);
//            }

            @Override
            public void onLocationRetrieved(android.location.Location location, CoordinateManager coordinateManager) {
                onResult(STATUS_LOCATION_RETRIEVED, location);
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

    private void onResult(int status, Parcelable result){
        Intent intent = new Intent(FILTER_ACTION);
        intent.putExtra(PARAM_STATUS, status);
        intent.putExtra(PARAM_RESULT, result);
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
        else if (action.equals(ACTION_DIRECT_GEOCODE)) {
            String address = intent.getExtras().getString(PARAM_REQUESTED_ADDRESS);
            new GeocoderAsyncTask(){
                @Override
                protected void onPostExecute(Location location) {
                    super.onPostExecute(location);
                    onResult(STATUS_LOCATION_RETRIEVED, location);
                }
            }.execute(address);
        } else if (action.equals(ACTION_GET_CURRENT_LOCATION)) {
            mCoordManager.setup();
        }

        return START_NOT_STICKY;
    }

    public static void requestCurrentLocation(Context context, CurrentLocationReceiver currentLocationReceiver){
        register(context, currentLocationReceiver);
        Intent intent = createIntent(context, CoordinateService.ACTION_GET_CURRENT_LOCATION);
        context.startService(intent);
    }

    public static void requestLocation(Context context, LocationByAddressReceiver locationByAddressReceiver, String address){
        register(context, locationByAddressReceiver);
        Intent intent = createIntent(context, CoordinateService.ACTION_DIRECT_GEOCODE);
        intent.putExtra(PARAM_REQUESTED_ADDRESS, address);
        context.startService(intent);
    }

    public static void startTrackAddress(Context context, AddressReceiver coordinateReceiver){
        register(context, coordinateReceiver);
        Intent intent = createIntent(context, CoordinateService.ACTION_START);
        context.startService(intent);
    }

    public static void stopTrackAddress(Context context, BroadcastReceiver receiver){
        try {
            context.unregisterReceiver(receiver);
        } catch (IllegalArgumentException ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        Intent intent = createIntent(context, CoordinateService.ACTION_STOP);
        context.startService(intent);
    }

    private static void register(Context context, BroadcastReceiver broadcast){
        IntentFilter locationIntentFilter = new IntentFilter(CoordinateService.FILTER_ACTION);
        context.registerReceiver(broadcast, locationIntentFilter);
    }

    private static Intent createIntent(Context context, String action){
        Intent intent = new Intent();
        intent.setAction(action);
        intent.setPackage(context.getPackageName());
        return intent;
    }
}
