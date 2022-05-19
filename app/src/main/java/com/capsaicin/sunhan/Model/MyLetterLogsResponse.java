package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class MyLetterLogsResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private MyLetterLogData myLetterLogData ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyLetterLogData getMyLetterLogData() {
        return myLetterLogData;
    }

    public void setMyLetterLogData(MyLetterLogData myLetterLogData) {
        this.myLetterLogData = myLetterLogData;
    }
}
