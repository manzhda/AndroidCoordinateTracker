package com.mda.coordinatetracker.network.urlbuilder;

import java.util.Map;

public class GeocodeURLBuilder extends GoogleApisHttpURLBuilder{
    public static String GEOCODE_URL = "/maps/api/geocode/json";

    public GeocodeURLBuilder(Map<String, String> params) {
		super(GEOCODE_URL, params);
	}
}
