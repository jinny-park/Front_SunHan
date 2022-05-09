package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("errors")
    ErrorMessage errors;

    // Getter Methods

    public ErrorMessage getErrors() {
        return errors;
    }

    public void setErrors(ErrorMessage errors) {
        this.errors = errors;
    }
}
