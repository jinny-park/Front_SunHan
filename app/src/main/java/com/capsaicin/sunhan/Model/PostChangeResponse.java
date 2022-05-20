package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class PostChangeResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("communityDetailItem")
    private CommunityDetailItem communityDetailItem;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommunityDetailItem getCommunityDetailItem() {
        return communityDetailItem;
    }

    public void setCommunityDetailItem(CommunityDetailItem communityDetailItem) {
        this.communityDetailItem = communityDetailItem;
    }
}