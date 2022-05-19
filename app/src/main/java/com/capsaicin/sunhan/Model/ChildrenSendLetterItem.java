package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class ChildrenSendLetterItem {

    @SerializedName("content")
    private String content;
    @SerializedName("childrenId")
    private String childrenId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(String childrenId) {
        this.childrenId = childrenId;
    }
}
