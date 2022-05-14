package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommunityDetailResponse {
    @SerializedName("message") private String message;
    @SerializedName("data") ArrayList< CommunityDetailItem > data = new ArrayList <> ();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<CommunityDetailItem> getData() {
        return data;
    }

    public void setData(ArrayList<CommunityDetailItem> data) {
        this.data = data;
    }
}
