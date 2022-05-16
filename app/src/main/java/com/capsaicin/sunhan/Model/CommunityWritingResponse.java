package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommunityWritingResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private CommunityWritingResponseData communityWritingResponseData;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommunityWritingResponseData getCommunityWritingResponseData() {
        return communityWritingResponseData;
    }

    public void setCommunityWritingResponseData(CommunityWritingResponseData communityWritingResponseData) {
        this.communityWritingResponseData = communityWritingResponseData;
    }
}
