package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class UserItem {

    @SerializedName("_id")
    private String _id;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("email")
    private String email;

    @SerializedName("avatarUrl")
    private String avatarUrl;


    // Getter Methods

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String get_id() {
        return _id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    // Setter Methods

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
