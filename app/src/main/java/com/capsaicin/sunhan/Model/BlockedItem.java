package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class BlockedItem {

    @SerializedName("_id")
    private String _id;
    @SerializedName("nickname")
    String nickname;

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

    public BlockedItem(String userNickname) {
        this.nickname = userNickname;
    }

}
