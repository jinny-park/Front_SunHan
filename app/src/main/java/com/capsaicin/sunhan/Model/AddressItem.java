package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class AddressItem {

    @SerializedName("lat")
    double lat;

    @SerializedName("lng")
    double lng;

    public AddressItem(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
