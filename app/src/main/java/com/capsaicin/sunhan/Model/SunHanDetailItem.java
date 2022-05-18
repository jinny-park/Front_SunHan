package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SunHanDetailItem {

    @SerializedName("_id")
    private String _id;
    @SerializedName("name")
    private String name;
    @SerializedName("openingHours")
    private String openingHours;
    @SerializedName("address")
    private String address;
    @SerializedName("tatget")
    private String tatget;
    @SerializedName("offer")
    private String offer;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("category")
    private String category;
    @SerializedName("reviews")
    private ArrayList< LetterItem > reviews = new ArrayList();

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

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTatget() {
        return tatget;
    }

    public void setTatget(String tatget) {
        this.tatget = tatget;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<LetterItem> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<LetterItem> reviews) {
        this.reviews = reviews;
    }
}
