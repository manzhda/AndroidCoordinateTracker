package com.mda.coordinatetracker.geocoder;

import biz.kulik.android.jaxb.library.Annotations.XmlElement;

/**
 * User:
 * Date: 12/2/12
 * Time: 5:41 PM
 */
public class Address {
    @XmlElement(name = "formatted_address")
    private String mAddress;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }
}
