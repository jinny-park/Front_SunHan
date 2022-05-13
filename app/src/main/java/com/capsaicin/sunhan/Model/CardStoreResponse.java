package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CardStoreResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    ArrayList< CardStoreItem > data = new ArrayList <> ();


    // Getter Methods

    public String getMessage() {
        return message;
    }

    // Setter Methods

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<CardStoreItem> getData() {
        return data;
    }

    public void setData(ArrayList<CardStoreItem> data) {
        this.data = data;
    }
}
