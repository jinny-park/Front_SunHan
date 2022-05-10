package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class ChildrenResponse {
    @SerializedName("message") private String message;
    @SerializedName("data") ChildrenItem childrenItem;

    public String getMessage() {return message;}
    public ChildrenItem getChildrenItem() {return childrenItem;}

    public void setMessage(String message) {this.message = message;    }
    public void setChildrenItem(ChildrenItem childrenItem) {
        this.childrenItem = childrenItem;
    }
}
