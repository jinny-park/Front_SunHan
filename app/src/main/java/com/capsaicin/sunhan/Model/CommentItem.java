package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class CommentItem {
    @SerializedName("writer") Object commuWriter;
    @SerializedName("_id") private String commuId;
    @SerializedName("content") private String commuContent;
    @SerializedName("isDeleted") private Boolean commuIsDeleted;
    @SerializedName("commentCount") private String commuIsCommentCount;
    @SerializedName("createAt") private String commuIsCreateAt;
    @SerializedName("updateAt") private String commuIsUpdateAt;

    public CommentItem(Object commuWriter, String commuId, String commuContent, Boolean commuIsDeleted, String commuIsCommentCount, String commuIsCreateAt, String commuIsUpdateAt) {
        this.commuWriter = commuWriter;
        this.commuId = commuId;
        this.commuContent = commuContent;
        this.commuIsDeleted = commuIsDeleted;
        this.commuIsCommentCount = commuIsCommentCount;
        this.commuIsCreateAt = commuIsCreateAt;
        this.commuIsUpdateAt = commuIsUpdateAt;
    }

    public Object getCommuWriter() {
        return commuWriter;
    }

    public void setCommuWriter(Object commuWriter) {
        this.commuWriter = commuWriter;
    }

    public String getCommuId() {
        return commuId;
    }

    public void setCommuId(String commuId) {
        this.commuId = commuId;
    }

    public String getCommuContent() {
        return commuContent;
    }

    public void setCommuContent(String commuContent) {
        this.commuContent = commuContent;
    }

    public Boolean getCommuIsDeleted() {
        return commuIsDeleted;
    }

    public void setCommuIsDeleted(Boolean commuIsDeleted) {
        this.commuIsDeleted = commuIsDeleted;
    }

    public String getCommuIsCommentCount() {
        return commuIsCommentCount;
    }

    public void setCommuIsCommentCount(String commuIsCommentCount) {
        this.commuIsCommentCount = commuIsCommentCount;
    }

    public String getCommuIsCreateAt() {
        return commuIsCreateAt;
    }

    public void setCommuIsCreateAt(String commuIsCreateAt) {
        this.commuIsCreateAt = commuIsCreateAt;
    }

    public String getCommuIsUpdateAt() {
        return commuIsUpdateAt;
    }

    public void setCommuIsUpdateAt(String commuIsUpdateAt) {
        this.commuIsUpdateAt = commuIsUpdateAt;
    }

    @Override
    public String toString() {
        return "CommentItem{" +
                "commuWriter=" + commuWriter +
                ", commuId='" + commuId + '\'' +
                ", commuContent='" + commuContent + '\'' +
                ", commuIsDeleted=" + commuIsDeleted +
                ", commuIsCommentCount=" + commuIsCommentCount +
                ", commuIsCreateAt='" + commuIsCreateAt + '\'' +
                ", commuIsUpdateAt='" + commuIsUpdateAt + '\'' +
                '}';
    }
}