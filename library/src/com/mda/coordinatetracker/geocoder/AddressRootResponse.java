package com.mda.coordinatetracker.geocoder;

import biz.kulik.android.jaxb.library.Annotations.XmlElement;

import java.util.List;

/**
 * User:
 * Date: 11/18/12
 * Time: 9:25 PM
 */
public class AddressRootResponse {
    @XmlElement(name = "results")
    public List<Address> address;
}
