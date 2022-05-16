package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyCommentLogsResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ArrayList< MyCommentLogsItem > data = new ArrayList <> ();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<MyCommentLogsItem> getData() {
        return data;
    }

    public void setData(ArrayList<MyCommentLogsItem> data) {
        this.data = data;
    }
}
