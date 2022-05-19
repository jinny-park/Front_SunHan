package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class CommentItem {
    @SerializedName("writer") WriterItem c_writerItem;
    @SerializedName("_id") private String c_commuId;
    @SerializedName("content") private String c_commuContent;
    @SerializedName("isDeleted") private Boolean c_commuIsDeleted;
    @SerializedName("commentCount") private String c_commuIsCommentCount;
    @SerializedName("createAt") private String c_commuIsCreateAt;
    @SerializedName("updateAt") private String c_commuIsUpdateAt;

    public WriterItem getC_writerItem() {
        return c_writerItem;
    }

    public void setC_writerItem(WriterItem c_writerItem) {
        this.c_writerItem = c_writerItem;
    }

    public String getC_commuId() {
        return c_commuId;
    }

    public void setC_commuId(String c_commuId) {
        this.c_commuId = c_commuId;
    }

    public String getC_commuContent() {
        return c_commuContent;
    }

    public void setC_commuContent(String c_commuContent) {
        this.c_commuContent = c_commuContent;
    }

    public Boolean getC_commuIsDeleted() {
        return c_commuIsDeleted;
    }

    public void setC_commuIsDeleted(Boolean c_commuIsDeleted) {
        this.c_commuIsDeleted = c_commuIsDeleted;
    }

    public String getC_commuIsCommentCount() {
        return c_commuIsCommentCount;
    }

    public void setC_commuIsCommentCount(String c_commuIsCommentCount) {
        this.c_commuIsCommentCount = c_commuIsCommentCount;
    }

    public String getC_commuIsCreateAt() {
        return c_commuIsCreateAt;
    }

    public void setC_commuIsCreateAt(String c_commuIsCreateAt) {
        this.c_commuIsCreateAt = c_commuIsCreateAt;
    }

    public String getC_commuIsUpdateAt() {
        return c_commuIsUpdateAt;
    }

    public void setC_commuIsUpdateAt(String c_commuIsUpdateAt) {
        this.c_commuIsUpdateAt = c_commuIsUpdateAt;
    }
}