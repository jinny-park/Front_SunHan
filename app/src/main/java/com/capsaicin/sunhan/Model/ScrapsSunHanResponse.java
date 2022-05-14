package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class ScrapsSunHanResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ScrapsSunHanItem scrapsItem;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ScrapsSunHanItem getScrapsItem() {
        return scrapsItem;
    }

    public void setScrapsItem(ScrapsSunHanItem scrapsItem) {
        this.scrapsItem = scrapsItem;
    }
}
