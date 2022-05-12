package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class BlockListResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private BlockUsers blockUsers;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BlockUsers getBlockUsers() {
        return blockUsers;
    }

    public void setBlockUsers(BlockUsers blockUsers) {
        this.blockUsers = blockUsers;
    }
}
