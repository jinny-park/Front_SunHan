package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private TokenItem tokenItem;

    @SerializedName("errors")
    private Error error;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TokenItem getTokenItem() {
        return tokenItem;
    }

    public void setTokenItem(TokenItem tokenItem) {
        this.tokenItem = tokenItem;
    }
}
