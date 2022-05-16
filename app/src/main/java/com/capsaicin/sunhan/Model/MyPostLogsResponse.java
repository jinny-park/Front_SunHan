package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class MyPostLogsResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private  MypostLosgItem mypostLosgItem;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MypostLosgItem getMypostLosgItem() {
        return mypostLosgItem;
    }

    public void setMypostLosgItem(MypostLosgItem mypostLosgItem) {
        this.mypostLosgItem = mypostLosgItem;
    }
}
