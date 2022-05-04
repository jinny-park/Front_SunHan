package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class TokenItem {

    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("refreshToken")
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // toString()을 Override 해주지 않으면 객체 주소값을 출력함
    @Override
    public String toString() {
        return "TokenItem{" +
                "accessToken=" + accessToken +
                ", refreshToken=" + refreshToken +
                '}';
    }

}
