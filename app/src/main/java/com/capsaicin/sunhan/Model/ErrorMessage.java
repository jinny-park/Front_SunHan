package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class ErrorMessage {

    @SerializedName("message")
    private String message;


    // Getter Methods

    public String getMessage() {
        return message;
    }

    // Setter Methods

    public void setMessage(String message) {
        this.message = message;
    }
}
