package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class SunHanSendLetterData {

    @SerializedName("writer")
    private WriterItem writerItem;
    @SerializedName("sunhanId")
    private String sunhanId;
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
    @SerializedName("__v")
    private float __v;


    public WriterItem getWriterItem() {
        return writerItem;
    }

    public void setWriterItem(WriterItem writerItem) {
        this.writerItem = writerItem;
    }

    public String getSunhanId() {
        return sunhanId;
    }

    public void setSunhanId(String sunhanId) {
        this.sunhanId = sunhanId;
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

    public float get__v() {
        return __v;
    }

    public void set__v(float __v) {
        this.__v = __v;
    }
}
