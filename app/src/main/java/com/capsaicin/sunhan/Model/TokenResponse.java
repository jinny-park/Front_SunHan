package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    TokenItem tokenItem;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public TokenItem getTokenItem() {
        return tokenItem;
    }

    public void setTokenItem(TokenItem tokenItem) {
        this.tokenItem = tokenItem;
    }
}
