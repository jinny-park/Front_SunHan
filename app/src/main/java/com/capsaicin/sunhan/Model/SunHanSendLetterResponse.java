package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class SunHanSendLetterResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private SunHanSendLetterData sunHanSendLetterData ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SunHanSendLetterData getSunHanSendLetterData() {
        return sunHanSendLetterData;
    }

    public void setSunHanSendLetterData(SunHanSendLetterData sunHanSendLetterData) {
        this.sunHanSendLetterData = sunHanSendLetterData;
    }
}
