package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommunityResponse {
    @SerializedName("message") private String message;
    @SerializedName("data") ArrayList< CommunityItem > data = new ArrayList <> ();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<CommunityItem> getData() {
        return data;
    }

    public void setData(ArrayList<CommunityItem> data) {
        this.data = data;
    }
}
