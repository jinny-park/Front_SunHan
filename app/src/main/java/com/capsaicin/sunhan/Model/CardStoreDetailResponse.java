package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CardStoreDetailResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    CardStoreItem cardStoreItem;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CardStoreItem getCardStoreItem() {
        return cardStoreItem;
    }

    public void setCardStoreItem(CardStoreItem cardStoreItem) {
        this.cardStoreItem = cardStoreItem;
    }
}
