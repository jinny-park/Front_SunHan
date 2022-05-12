package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class NickNameItem {

    @SerializedName("nickname")
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
