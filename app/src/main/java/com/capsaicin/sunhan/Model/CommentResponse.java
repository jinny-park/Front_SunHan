package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommentResponse {
    @SerializedName("message") private String message;
    @SerializedName("data") ArrayList< CommentItem > data = new ArrayList <> ();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<CommentItem> getData() {
        return data;
    }

    public void setData(ArrayList<CommentItem> data) {
        this.data = data;
    }
}
