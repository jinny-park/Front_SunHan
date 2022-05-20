package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommunityDetailResponse {
    @SerializedName("message") private String message;
    @SerializedName("data") CommunityItem communityItem;
    @SerializedName("list") ArrayList<CommunityItem> communityItemList = new ArrayList<>();

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

    public ArrayList<CommunityItem> getCommunityItemList() {
        return communityItemList;
    }

    public void setCommunityItemList(ArrayList<CommunityItem> communityItemList) {
        this.communityItemList = communityItemList;
    }
}