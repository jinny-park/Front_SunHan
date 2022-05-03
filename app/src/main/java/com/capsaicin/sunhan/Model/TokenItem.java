package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class TokenItem {

    @SerializedName("accessToken")
    private int accessToken;

    @SerializedName("refreshToken")
    private int refreshToken;

    // toString()을 Override 해주지 않으면 객체 주소값을 출력함
    @Override
    public String toString() {
        return "TokenItem{" +
                "accessToken=" + accessToken +
                ", refreshToken=" + refreshToken +
                '}';
    }
}
