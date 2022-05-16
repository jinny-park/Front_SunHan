package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class CommunityWritingPost { // 글쓸 때 보내는 객체

    @SerializedName("content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
