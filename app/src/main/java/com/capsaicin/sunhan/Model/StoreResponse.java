package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class StoreResponse {
    @SerializedName("message") private String message;
    @SerializedName("data") StoreItem storeItem;

    public String getMessage() {return message;}
    public StoreItem getStoreItem() {return storeItem;}

    public void setMessage(String message) {this.message = message;    }
    public void setStoreItem(StoreItem storeItem) {
        this.storeItem = storeItem;
    }
}
