package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class CardStoreLetter {

    @SerializedName("writer")
    UserItem userItem;
    @SerializedName("childrenId")
    private String childrenId;
    @SerializedName("content")
    private String content;
    @SerializedName("blockNumber")
    private float blockNumber;
    @SerializedName("createAt")
    private String createAt;
    @SerializedName("updateAt")
    private String updateAt;
    @SerializedName("_id")
    private String _id;

    public UserItem getUserItem() {
        return userItem;
    }

    public void setUserItem(UserItem userItem) {
        this.userItem = userItem;
    }

    public String getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(String childrenId) {
        this.childrenId = childrenId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(float blockNumber) {
        this.blockNumber = blockNumber;
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
