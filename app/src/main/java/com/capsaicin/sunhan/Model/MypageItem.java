package com.capsaicin.sunhan.Model;

public class MypageItem {
    String itemName;
    int myPageIcon;

    public int getMyPageIcon() {
        return myPageIcon;
    }

    public void setMyPageIcon(int myPageIcon) {
        this.myPageIcon = myPageIcon;
    }

    public MypageItem(String itemName, int myPageIcon) {
        this.itemName = itemName;
        this.myPageIcon = myPageIcon;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
