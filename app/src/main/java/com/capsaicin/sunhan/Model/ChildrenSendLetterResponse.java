package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class ChildrenSendLetterResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ChildrenSendLetterData childrenSendLetterData;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChildrenSendLetterData getChildrenSendLetterData() {
        return childrenSendLetterData;
    }

    public void setChildrenSendLetterData(ChildrenSendLetterData childrenSendLetterData) {
        this.childrenSendLetterData = childrenSendLetterData;
    }
}
