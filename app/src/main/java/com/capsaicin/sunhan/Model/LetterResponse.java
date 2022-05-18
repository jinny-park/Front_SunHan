package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LetterResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    ArrayList<LetterItem> data = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<LetterItem> getData() {
        return data;
    }

    public void setData(ArrayList<LetterItem> data) {
        this.data = data;
    }
}
