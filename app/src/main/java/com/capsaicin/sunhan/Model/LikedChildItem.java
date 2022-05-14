package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class LikedChildItem {

    @SerializedName("_id")
    private String _id;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("weekdayStartTime")
    private String weekdayStartTime;
    @SerializedName("weekdayEndTime")
    private String weekdayEndTime;
    @SerializedName("weekendStartTime")
    private String weekendStartTime;
    @SerializedName("weekendEndTime")
    private String weekendEndTime;
    @SerializedName("holydayStartTime")
    private String holydayStartTime;
    @SerializedName("holydayEndTime")
    private String holydayEndTime;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWeekdayStartTime() {
        return weekdayStartTime;
    }

    public void setWeekdayStartTime(String weekdayStartTime) {
        this.weekdayStartTime = weekdayStartTime;
    }

    public String getWeekdayEndTime() {
        return weekdayEndTime;
    }

    public void setWeekdayEndTime(String weekdayEndTime) {
        this.weekdayEndTime = weekdayEndTime;
    }

    public String getWeekendStartTime() {
        return weekendStartTime;
    }

    public void setWeekendStartTime(String weekendStartTime) {
        this.weekendStartTime = weekendStartTime;
    }

    public String getWeekendEndTime() {
        return weekendEndTime;
    }

    public void setWeekendEndTime(String weekendEndTime) {
        this.weekendEndTime = weekendEndTime;
    }

    public String getHolydayStartTime() {
        return holydayStartTime;
    }

    public void setHolydayStartTime(String holydayStartTime) {
        this.holydayStartTime = holydayStartTime;
    }

    public String getHolydayEndTime() {
        return holydayEndTime;
    }

    public void setHolydayEndTime(String holydayEndTime) {
        this.holydayEndTime = holydayEndTime;
    }
}
