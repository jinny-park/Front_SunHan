package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class ScrapChildResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ScrapChildItem scrapChildItem;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ScrapChildItem getScrapChildItem() {
        return scrapChildItem;
    }

    public void setScrapChildItem(ScrapChildItem scrapChildItem) {
        this.scrapChildItem = scrapChildItem;
    }
}
