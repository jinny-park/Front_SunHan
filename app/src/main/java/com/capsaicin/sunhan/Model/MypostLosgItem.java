package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MypostLosgItem {

    @SerializedName("_id")
    private String _id;
    @SerializedName("writePosts")
    private ArrayList< MyPost > writePosts = new ArrayList<> ();

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ArrayList<MyPost> getWritePosts() {
        return writePosts;
    }

    public void setWritePosts(ArrayList<MyPost> writePosts) {
        this.writePosts = writePosts;
    }
}
