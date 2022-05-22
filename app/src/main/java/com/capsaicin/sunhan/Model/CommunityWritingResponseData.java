package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class CommunityWritingResponseData {

    @SerializedName("writer")
    WriterItem writerItem;
    @SerializedName("_id")
    private String _id;
    @SerializedName("isDeleted")
    private Boolean isDeleted;
    @SerializedName("content")
    private String content;
    @SerializedName("createAt")
    private String createAt;
    @SerializedName("updateAt")
    private String updateAt;
    @SerializedName("commentCount")
    private int commentCount;

    public WriterItem getWriterItem() {
        return writerItem;
    }

    public void setWriterItem(WriterItem writerItem) {
        this.writerItem = writerItem;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.updateAt = commentCount;
    }
}