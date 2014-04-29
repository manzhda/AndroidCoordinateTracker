package com.mda.coordinatetracker.geocoder;

import android.location.Location;
import android.util.Log;
import com.google.gson.Gson;
import com.mda.coordinatetracker.geocoder.dto.Address;
import com.mda.coordinatetracker.geocoder.dto.AddressRootResponse;
import com.mda.coordinatetracker.network.Connection;
import com.mda.coordinatetracker.network.urlbuilder.GeocodeURLBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GoogleGeocoderApi {
    private static final String TAG = GoogleGeocoderApi.class.getSimpleName();

    public static com.mda.coordinatetracker.geocoder.dto.Location directGeocode(String address){
        Map<String, String > params = new TreeMap<String, String>();
        try{
           address = URLEncoder.encode(address,"utf-8");
        } catch (UnsupportedEncodingException e) {
            address = "";
        }
        params.put("address", address);
        params.put("sensor", "" + true);

        String url =  new GeocodeURLBuilder(params).build();
        Address resultAddress = geocode(url);

        return resultAddress != null?resultAddress.getGeometry().getLocation():null;

    }

    public static String reverseGeocode(Location location){
        Map<String, String > params = new TreeMap<String, String>();
        params.put("latlng", location.getLatitude() +"," + location.getLongitude());
        params.put("sensor", "" + true);

        String url =  new GeocodeURLBuilder(params).build();
        Address address = geocode(url);

        return address != null?address.getFormattedAddress():"";
    }

    private static Address geocode(String url) {
        Log.d(TAG, "Url for geocode: "  + url);

        String localityName = "";
        HttpURLConnection connection = null;
        URL serverAddress = null;

        try {
            connection = Connection.createConnection(url);
            connection.connect();

            AddressRootResponse addressRootResponse = parse(connection.getInputStream());
            List<Address> addressList = addressRootResponse.results;

            if (addressList != null && addressList.size() > 0) {
                return addressList.get(0);
            }

        } catch (Exception ex) {
            Log.d(TAG, "Couldn't get address", ex);
        }
        return null;
    }

    private static AddressRootResponse parse(InputStream content){
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(content);
        AddressRootResponse result = gson.fromJson(reader, AddressRootResponse.class);
        return result;
    }
}