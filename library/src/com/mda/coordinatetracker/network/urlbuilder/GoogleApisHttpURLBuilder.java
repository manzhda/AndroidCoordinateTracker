package com.mda.coordinatetracker.network.urlbuilder;

import java.util.Map;

public class GoogleApisHttpURLBuilder extends AbstractURLBuilder{
    private final Map<String, String> mParams;

    public GoogleApisHttpURLBuilder(String path, Map<String, String> params) {
		super(path);
        mParams = params;
	}

	@Override
    String getProtocol() {
		return "http";
	}

    @Override
    String getHost() {
        return "maps.googleapis.com";
    }

    @Override
    String getPort() {
		return "80";
	}

    @Override
    Map<String, String> getParams() {
        return mParams;
    }
}
