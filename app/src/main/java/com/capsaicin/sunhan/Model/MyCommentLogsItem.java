package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyCommentLogsItem {
    @SerializedName("_id")
    private String _id;
    @SerializedName("writer")
    WriterItem writerItem;
    @SerializedName("content")
    private String content;
    @SerializedName("createAt")
    private String createAt;
    @SerializedName("updateAt")
    private String updateAt;
    @SerializedName("commentCount")
    private float commentCount;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public WriterItem getWriterItem() {
        return writerItem;
    }

    public void setWriterItem(WriterItem writerItem) {
        this.writerItem = writerItem;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public float getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(float commentCount) {
        this.commentCount = commentCount;
    }
}
