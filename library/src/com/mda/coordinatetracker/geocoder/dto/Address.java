package com.mda.coordinatetracker.geocoder.dto;

import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("formatted_address")
    private String formatted_address;
    @SerializedName("geometry")
    private Geometry geometry;

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getFormattedAddress() {
        return formatted_address;
    }

    public void setFormattedAddress(String formatted_address) {
        this.formatted_address = formatted_address;
    }


}