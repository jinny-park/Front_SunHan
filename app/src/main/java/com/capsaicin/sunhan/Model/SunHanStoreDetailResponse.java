package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class SunHanStoreDetailResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    SunHanDetailItem sunHanDetailItem;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SunHanDetailItem getSunHanDetailItem() {
        return sunHanDetailItem;
    }

    public void setSunHanDetailItem(SunHanDetailItem sunHanDetailItem) {
        this.sunHanDetailItem = sunHanDetailItem;
    }
}
