package com.example.front_sunhan.Model;

public class StoreItem {
    String storeName;
    String storeAddrs;
    String storeNum;
    String storeTime;

    public StoreItem(String storeName, String storeAddrs, String storeNum, String storeTime) {
        this.storeName = storeName;
        this.storeAddrs = storeAddrs;
        this.storeNum=storeNum;
        this.storeTime=storeTime;
    }

    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getStoreAddrs() {
        return storeAddrs;
    }
    public void setStoreAddrs(String storeAddrs) { this.storeAddrs = storeAddrs; }

    public String getStoreNum() {
        return storeNum;
    }
    public void setStoreNum(String storeNum) { this.storeNum = storeNum; }

    public String getStoreTime() {
        return storeTime;
    }
    public void setStoreTime(String storeTime) { this.storeTime = storeTime; }

}
