package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommunityItem {

    @SerializedName("writer") WriterItem writerItem;
    @SerializedName("_id") private String commuId;
    @SerializedName("content") private String commuContent;
    @SerializedName("isDeleted") private Boolean commuIsDeleted;
    @SerializedName("commentCount") private String commuIsCommentCount;
    @SerializedName("createAt") private String commuIsCreateAt;
    @SerializedName("updateAt") private String commuIsUpdateAt;

    public WriterItem getWriterItem() {
        return writerItem;
    }

    public void setWriterItem(WriterItem writerItem) {
        this.writerItem = writerItem;
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
}