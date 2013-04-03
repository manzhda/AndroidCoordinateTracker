package com.mda.coordinatetracker.geocoder;

import android.os.AsyncTask;
import com.mda.coordinatetracker.geocoder.dto.Location;

/**
 * User: mda
 * Date: 4/2/13
 * Time: 4:43 PM
 */
public class GeocoderAsyncTask extends AsyncTask<String, Void, Location> {
    @Override
    protected Location doInBackground(String... params) {

        Location location = GoogleGeocoderApi.directGeocode(params[0]);
        return location;
    }
}
