package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    UserItem userItem;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserItem getUserItem() {
        return userItem;
    }

    public void setUserItem(UserItem userItem) {
        this.userItem = userItem;
    }

}
