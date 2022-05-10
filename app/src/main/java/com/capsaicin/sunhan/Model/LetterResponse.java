package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class LetterResponse {
    @SerializedName("message") private String message;
    @SerializedName("data") LetterItem letterItem;

    public String getMessage() {return message;}
    public LetterItem getLetterItem() {return letterItem;}

    public void setMessage(String message) {this.message = message;    }
    public void setLetterItem(LetterItem letterItem) {
        this.letterItem = letterItem;
    }
}
