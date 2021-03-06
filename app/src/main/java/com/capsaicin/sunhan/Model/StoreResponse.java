package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StoreResponse {
    @SerializedName("message") private String message;
    @SerializedName("data")
    private ArrayList< StoreItem > data = new ArrayList ();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<StoreItem> getData() {
        return data;
    }

    public void setData(ArrayList<StoreItem> data) {
        this.data = data;
    }
}
