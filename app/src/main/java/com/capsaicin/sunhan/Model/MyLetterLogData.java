package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyLetterLogData {

    @SerializedName("_id")
    private String _id;
    @SerializedName("writeReviews")
    private ArrayList< LetterItem > writeReviews = new ArrayList <> ();

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ArrayList<LetterItem> getWriteReviews() {
        return writeReviews;
    }

    public void setWriteReviews(ArrayList<LetterItem> writeReviews) {
        this.writeReviews = writeReviews;
    }
}
