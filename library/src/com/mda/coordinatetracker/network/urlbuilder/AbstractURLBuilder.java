package com.mda.coordinatetracker.network.urlbuilder;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractURLBuilder {
	private String path;
	
	public AbstractURLBuilder(String path) {
		this.path = path;
	}
	
	abstract String getProtocol();
	abstract String getHost();
	abstract String getPort();

    Map<String, String> getParams(){
        return new HashMap<String, String>();
    };

	public String getSeperator(){
		return ":";
	}
	
	public String build(){
		StringBuilder s = new StringBuilder();
		s.append(getProtocol());
		s.append("://");
		s.append(getHost());
		s.append(getSeperator());
		s.append(getPort());
		s.append(path);
        s.append("?");
        for (String key: getParams().keySet()){
            s.append("&");
            s.append(key + "=" + getParams().get(key));
        }

		System.out.println(s.toString());
		return s.toString();
	}
}
