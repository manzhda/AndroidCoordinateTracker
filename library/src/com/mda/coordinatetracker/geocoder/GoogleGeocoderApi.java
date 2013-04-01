package com.mda.coordinatetracker.geocoder;

import android.location.Location;
import biz.kulik.android.jaxb.library.parser.Parser;
import biz.kulik.android.jaxb.library.parser.ParserImpl;
import biz.kulik.android.jaxb.library.parser.UnMarshalerTypes;
import com.mda.coordinatetracker.network.Connection;
import com.mda.coordinatetracker.network.urlbuilder.GeocodeURLBuilder;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleGeocoderApi {
    private static final String TAG = GoogleGeocoderApi.class.getSimpleName();

    public static String reverseGeocode(Location loc) {
        String localityName = "";
        HttpURLConnection connection = null;
        URL serverAddress = null;

        try {

            Map<String, String > params = new HashMap<String, String>();
            params.put("latlng=", loc.getLatitude() +"," + loc.getLongitude());
            params.put("sensor", "" + true);

            String url =  new GeocodeURLBuilder(params).build();

            connection = Connection.createConnection(url);
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