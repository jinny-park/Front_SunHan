package com.example.front_sunhan.Model;

public class CommunityItem {
    private int profile;
    private String nickname;
    private String writetime;
    private String content;
    private int writenum;

    public CommunityItem(int profile, String nickname, String writetime, String content, int writenum) {
        this.profile = profile;
        this.nickname = nickname;
        this.writetime = writetime;
        this.content = content;
        this.writenum = writenum;
    }

    public int getProfile() { return profile; }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getWritetime() {
        return writetime;
    }

    public void setWritetime(String writetime) {
        this.writetime = writetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getWritenum() {
        return writenum;
    }

    public void setWritenum(int writenum) {
        this.writenum = writenum;
    }
}
