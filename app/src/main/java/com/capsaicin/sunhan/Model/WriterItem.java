package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class WriterItem {
    @SerializedName("_id")
    private String _id;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("avatarUrl")
    private String avatarUrl;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
