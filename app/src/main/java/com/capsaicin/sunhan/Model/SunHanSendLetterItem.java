package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class SunHanSendLetterItem {

    @SerializedName("content")
    private String content;
    @SerializedName("sunhanId")
    private String sunhanId;

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
}
