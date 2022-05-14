package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ScrapsSunHanItem {

    @SerializedName("scrapSunhan")
    ArrayList<LikedSunHanItem> scrapSunhan = new ArrayList ();

    public ArrayList<LikedSunHanItem> getScrapSunhan() {
        return scrapSunhan;
    }

    public void setScrapSunhan(ArrayList<LikedSunHanItem> scrapSunhan) {
        this.scrapSunhan = scrapSunhan;
    }
}
