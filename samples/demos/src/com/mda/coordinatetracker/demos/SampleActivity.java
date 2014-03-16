package com.mda.coordinatetracker.demos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.mda.coordinatetracker.receivers.*;
import com.mda.coordinatetracker.CoordinateService;
import com.mda.coordinatetracker.demos.utils.SimpleMessage;

public class SampleActivity extends Activity{
    private static final String TAG = SampleActivity.class.getName();

    private LocationByAddressReceiver mLocationByAddressReceiver;
    private CurrentLocationReceiver mCurrentLocationReceiver;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mLocationByAddressReceiver = new LocationByAddressReceiver(new AbstractLocationByAddressListener() {
            @Override
            public void onLocationRetrieved(double lat, double lng) {
                super.onLocationRetrieved(lat, lng);
                SimpleMessage.show(SampleActivity.this, "Location: lat = " + lat + ", lng = " + lng);
                CoordinateService.stopTrackAddress(SampleActivity.this, mLocationByAddressReceiver);
            }

            @Override
            public void onReceivingError() {

            }
        });

        mCurrentLocationReceiver = new CurrentLocationReceiver(new AbstractCurrentLocationListener() {
            @Override
            public void onLocationRetrieved(double lat, double lng) {
                super.onLocationRetrieved(lat, lng);
                SimpleMessage.show(SampleActivity.this, "Location: lat = " + lat + ", lng = " + lng);
                CoordinateService.stopTrackAddress(SampleActivity.this, mCurrentLocationReceiver);
            }
        });

        final EditText requestedAddress = (EditText)findViewById(R.id.request_address_edit_text);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id){
                    case R.id.request_location:
                        CoordinateService.requestLocation(SampleActivity.this, mLocationByAddressReceiver, requestedAddress.getText().toString());
                        break;
                    case R.id.get_my_current_location:
                        CoordinateService.requestCurrentLocation(SampleActivity.this, mCurrentLocationReceiver);
                        break;
                }
            }
        };

//        findViewById(R.id.start_track_location).setOnClickListener(listener);
//        findViewById(R.id.stop_track_location).setOnClickListener(listener);
        findViewById(R.id.request_location).setOnClickListener(listener);
        findViewById(R.id.get_my_current_location).setOnClickListener(listener);
    }
}
