package com.mda.coordinatetracker;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;

import com.mda.coordinatetracker.geocoder.GoogleGeocoderApi;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * User:
 * Date: 11/24/12
 * Time: 2:16 AM
 */
public class CoordinateManager {
    public interface CoordinateListener {
        public void onAddressRetrieved(String address, Location location, CoordinateManager coordinateManager);
        public void onLocationRetrieved(Location location, CoordinateManager coordinateManager);
        public void onLocationError(CoordinateManager coordinateManager);
        public void onNetworkError(CoordinateManager coordinateManager);
    }

    private LocationManager mLocationManager;
    private Context mContext;
    private final Handler mHandler;
    private final Runnable mTimeoutErrorTask;

    // UI handler codes.
    private static final int UPDATE_ADDRESS = 1;
    private static final int UPDATE_LOCATION = 2;
    private static final int LOCATION_ERROR = 3;
    private static final int NETWORK_ERROR = 4;

    private static final int TEN_SECONDS = 10000;
    private static final int TEN_METERS = 10;
    private static final int TWO_MINUTES = 1000 * 60 * 2;


    private final LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // A new location update is received.  Do something useful with it.  Update the UI with
            // the location update.
            mHandler.removeCallbacks(mTimeoutErrorTask);
            updateLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) { }
        @Override
        public void onProviderEnabled(String provider) { }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }
    };

    public CoordinateManager(Context context, final CoordinateListener coordinateListener) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mContext = context;

        // Handler for updating text fields on the UI like the lat/long and results.
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_ADDRESS:
                        AddressHolder addressHolder = (AddressHolder) msg.obj;
                        coordinateListener.onAddressRetrieved(addressHolder.mAddress, addressHolder.mLocation, CoordinateManager.this);
                        break;
                    case UPDATE_LOCATION:
                        coordinateListener.onLocationRetrieved((Location) msg.obj, CoordinateManager.this);
                        break;
                    case LOCATION_ERROR:
                        coordinateListener.onLocationError(CoordinateManager.this);
                        break;
                    case NETWORK_ERROR:
                        coordinateListener.onNetworkError(CoordinateManager.this);
                        break;
                }
            }
        };

        mTimeoutErrorTask = new Runnable() {
            @Override
            public void run() {
                Message.obtain(mHandler, LOCATION_ERROR, null).sendToTarget();
                mHandler.removeCallbacks(this);
            }
        };
    }

    public static boolean isNetworkPresent(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        return (!(i == null || !i.isConnected() || !i.isAvailable()));
    }

    // Set up fine and/or coarse location providers
    public void setup() {
        Location gpsLocation = null;
        Location networkLocation = null;
        mLocationManager.removeUpdates(listener);

        boolean isGpsProviderEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkProviderEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isNetworkPresent = isNetworkPresent(mContext);

        if( !isGpsProviderEnabled && !isNetworkProviderEnabled){
            Message.obtain(mHandler, LOCATION_ERROR, null).sendToTarget();
            return;
        }

        if(!isNetworkPresent){
            Message.obtain(mHandler, NETWORK_ERROR, null).sendToTarget();
//            return;
        }

        mHandler.postDelayed(mTimeoutErrorTask, TEN_SECONDS * 3);

        // Request updates from both fine (gps) and coarse (network) providers.
        gpsLocation = requestUpdatesFromProvider(LocationManager.GPS_PROVIDER);
        networkLocation = requestUpdatesFromProvider(LocationManager.NETWORK_PROVIDER);

        // If both providers return last known locations, compare the two and use the better
        // one to update the UI.  If only one provider returns a location, use it.
        if (gpsLocation != null && networkLocation != null) {
            updateLocation(getBetterLocation(gpsLocation, networkLocation));
        } else if (gpsLocation != null) {
            updateLocation(gpsLocation);
        } else if (networkLocation != null) {
            updateLocation(networkLocation);
        }
    }

    public void stop(){
        mLocationManager.removeUpdates(listener);
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void doReverseGeocoding(Location location) {
        // Since the geocoding API is synchronous and may take a while.  You don't want to lock
        // up the UI thread.  Invoking reverse geocoding in an AsyncTask.
        (new ReverseGeocodingTask(mContext)).execute(new Location[] {location});
    }

    private void updateLocation(Location location) {
//        Message.obtain(mHandler, UPDATE_LOCATION, location).sendToTarget();

        if(isNetworkPresent(mContext)){
//            Bypass reverse-geocoding only if the internet is presented.
            doReverseGeocoding(location);
        } else {
            sendAddressHolder(location, "");
//            Message.obtain(mHandler, NETWORK_ERROR, null).sendToTarget();
        }
    }

    /** Determines whether one Location reading is better than the current Location fix.
     * Code taken from
     * http://developer.android.com/guide/topics/location/obtaining-user-location.html
     *
     * @param newLocation  The new Location that you want to evaluate
     * @param currentBestLocation  The current Location fix, to which you want to compare the new
     *        one
     * @return The better Location object based on recency and accuracy.
     */
    protected Location getBetterLocation(Location newLocation, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return newLocation;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved.
        if (isSignificantlyNewer) {
            return newLocation;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return currentBestLocation;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(newLocation.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return newLocation;
        } else if (isNewer && !isLessAccurate) {
            return newLocation;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return newLocation;
        }
        return currentBestLocation;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    /**
     * Method to register location updates with a desired location provider.  If the requested
     * provider is not available on the device, the app displays a Toast with a message referenced
     * by a resource id.
     *
     * @param provider Name of the requested provider.
     * @return A previously returned {@link android.location.Location} from the requested provider,
     *         if exists.
     */
    private Location requestUpdatesFromProvider(final String provider) {
        Location location = null;
        if (mLocationManager.isProviderEnabled(provider)) {
            mLocationManager.requestLocationUpdates(provider, TEN_SECONDS, TEN_METERS, listener);
            location = mLocationManager.getLastKnownLocation(provider);
        }

        return location;
    }

    // AsyncTask encapsulating the reverse-geocoding API.  Since the geocoder API is blocked,
    // we do not want to invoke it from the UI thread.
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private class ReverseGeocodingTask extends AsyncTask<Location, Void, Void> {
        Context mContext;

        public ReverseGeocodingTask(Context context) {
            super();
            mContext = context;
        }

        @Override
        protected Void doInBackground(Location... params) {
            Location loc = params[0];
            byGoogleGeocodingApi(loc);
//            byGeocoder(loc);
            return null;
        }

        private void byGoogleGeocodingApi(Location loc){
            String localityName = GoogleGeocoderApi.reverseGeocode(loc);
            sendAddressHolder(loc, localityName);
        }

        private void byGeocoder(Location loc){
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
                // Update results field with the exception.
                sendAddressHolder(loc, "");
            }
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                // Format the first line of results (if available), city, and country name.
                String addressText = String.format("%s, %s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getLocality(),
                        address.getCountryName());
                // Update results field on UI.
                sendAddressHolder(loc, addressText);
            }
        }
    }

    private void sendAddressHolder(Location location, String localityName) {

        Message.obtain(mHandler, UPDATE_ADDRESS, new AddressHolder(location, localityName)).sendToTarget();
    }

    private String getDefaultAddress(Location location){
        return "Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude() + " ";
    }

    static class AddressHolder implements Parcelable {
        Location mLocation;
        String mAddress;

        AddressHolder(Location location, String address) {
            mLocation = location;
            this.mAddress = address;
        }

        AddressHolder(Parcel in) {
            mLocation = in.readParcelable(Location.class.getClassLoader());
            mAddress = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(mLocation, flags);
            dest.writeString(mAddress);
        }

        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
            public AddressHolder createFromParcel(Parcel in) {
                return new AddressHolder(in);
            }

            public AddressHolder[] newArray(int size) {
                return new AddressHolder[size];
            }
        };
    }
}
