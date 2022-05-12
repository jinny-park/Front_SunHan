package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BlockUsers {
    @SerializedName("_id")
    private String _id;

    @SerializedName("blockUsers")
    ArrayList< BlockedItem > blockUsers = new ArrayList < BlockedItem > ();


    // Getter Methods

    public String get_id() {
        return _id;
    }

    // Setter Methods

    public void set_id(String _id) {
        this._id = _id;
    }

    public ArrayList<BlockedItem> getBlockUsers() {
        return blockUsers;
    }

    public void setBlockUsers(ArrayList<BlockedItem> blockUsers) {
        this.blockUsers = blockUsers;
    }
}
