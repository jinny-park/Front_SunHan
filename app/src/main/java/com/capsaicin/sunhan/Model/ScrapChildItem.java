package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ScrapChildItem {

    @SerializedName("scrapChild")
    ArrayList<LikedChildItem> likedChildItems = new ArrayList ();

    public ArrayList<LikedChildItem> getLikedChildItems() {
        return likedChildItems;
    }

    public void setLikedChildItems(ArrayList<LikedChildItem> likedChildItems) {
        this.likedChildItems = likedChildItems;
    }
}
