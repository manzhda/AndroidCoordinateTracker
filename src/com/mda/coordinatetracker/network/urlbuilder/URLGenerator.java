package com.mda.coordinatetracker.network.urlbuilder;

import java.util.HashMap;
import java.util.Map;

public class URLGenerator {
	public static void main(String[] args){

        double lat = 30.267153;
        double lng = -97.7430608;


        Map<String, String > params = new HashMap<String, String>();
        params.put("latlng", lat +"," + lng);
        params.put("sensor", "" + true);

        GoogleApisHttpURLBuilder builder = new GeocodeURLBuilder(params);
        builder.build();
	}
}
