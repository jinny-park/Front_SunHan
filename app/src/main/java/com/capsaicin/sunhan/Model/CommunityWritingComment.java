package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class CommunityWritingComment { // 글쓸 때 보내는 객체

    @SerializedName("postId")
    private String postId;

    @SerializedName("content")
    private String content;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
