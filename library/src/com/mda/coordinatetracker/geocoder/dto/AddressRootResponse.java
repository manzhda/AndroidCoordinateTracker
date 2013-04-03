package com.mda.coordinatetracker.geocoder.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * User: mda
 * Date: 11/18/12
 * Time: 9:25 PM
 */
public class AddressRootResponse {
    @SerializedName("results")
    public List<Address> results;
}
