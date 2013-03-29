package com.mda.coordinatetracker.geocoder;

import android.location.Location;
import android.util.Log;
import biz.kulik.android.jaxb.library.parser.Parser;
import biz.kulik.android.jaxb.library.parser.ParserImpl;
import biz.kulik.android.jaxb.library.parser.UnMarshalerTypes;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GoogleGeocoderApi {
    private static final String TAG = GoogleGeocoderApi.class.getSimpleName();

    public static String reverseGeocode(Location loc) {
        String localityName = "";
        HttpURLConnection connection = null;
        URL serverAddress = null;

        try {
            String urlPattern = "http://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&sensor=%b";
            String urlFormatted = String.format(urlPattern, loc.getLatitude(), loc.getLongitude(), true);

            Log.d(TAG, urlFormatted);

            serverAddress = new URL(urlFormatted);
            //set up out communications stuff
            connection = null;

            //Set up the initial connection
            connection = (HttpURLConnection) serverAddress.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);

            connection.connect();

            AddressRootResponse addressRootResponse = parse(connection.getInputStream());
            List<Address> addressList = addressRootResponse.address;

            if (addressList != null && addressList.size() > 0) {
                localityName = addressList.get(0).getAddress();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return localityName;
    }

    private static AddressRootResponse parse(InputStream inputStream) {
        Parser parser = new ParserImpl(UnMarshalerTypes.JSONAdapter);
        return parser.parse(AddressRootResponse.class, inputStream);
    }
}