package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class WritepostItem {

    @SerializedName("content")
    private String writeContent;

    public String getwriteContent() {
        return writeContent;
    }

    public void setwriteContent(String writeContent) {
        this.writeContent = writeContent;
    }
}
