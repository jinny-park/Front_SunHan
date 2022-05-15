package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommunityDetailResponse {
    @SerializedName("message") private String message;
    @SerializedName("data") CommunityItem communityItem;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommunityItem getCommunityItem() {
        return communityItem;
    }

    public void setCommunityDetailItem(CommunityItem communityItem) {
        this.communityItem = communityItem;
    }
}
