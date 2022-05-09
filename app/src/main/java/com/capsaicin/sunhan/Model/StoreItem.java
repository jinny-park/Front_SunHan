package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StoreItem {

    @SerializedName("_id") private String storeId;
    @SerializedName("name") private String storeName;
    @SerializedName("address") private String storeAddrs;
    @SerializedName("phoneNumber") private String storeNum;
    @SerializedName("openingHours") private String storeTime;
    @SerializedName("category") private String storeCategory;
    @SerializedName("offer") private String storeOffer;
    @SerializedName("tatget") private String storeTarget;
    @SerializedName("reviews") ArrayList < Object > storeReviews = new ArrayList < Object > ();

    public StoreItem(String storeName, String storeAddrs, String storeNum, String storeTime/*,
                     String storeCategory, String storeOffer,String storeId, String storeTarget, ArrayList< Object > storeReviews*/) {
        this.storeName = storeName;
        this.storeAddrs = storeAddrs;
        this.storeNum=storeNum;
        this.storeTime=storeTime;
        this.storeCategory=storeCategory;
        this.storeOffer=storeOffer;
        this.storeId=storeId;
        this.storeReviews=storeReviews;
        this.storeTarget=storeTarget;
    }

    public String getStoreName() {return storeName;}
    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getStoreAddrs() {        return storeAddrs;    }
    public void setStoreAddrs(String storeAddrs) { this.storeAddrs = storeAddrs; }

    public String getStoreNum() {        return storeNum;    }
    public void setStoreNum(String storeNum) { this.storeNum = storeNum; }

    public String getStoreTime() {        return storeTime;    }
    public void setStoreTime(String storeTime) { this.storeTime = storeTime; }

    public String getStoreCategory() {        return storeCategory;    }
    public void setStoreCategory(String storeCategory) { this.storeCategory = storeCategory; }

    public String getStoreOffer() {        return storeOffer;    }
    public void setStoreOffer(String storeOffer) { this.storeOffer = storeOffer; }

    public String getStoreId() {        return storeId;    }
    public void setStoreId(String storeId) { this.storeId = storeId; }

    public ArrayList<Object> getStoreReviews() {return storeReviews;}
    public void setStoreReviews(ArrayList<Object> storeReviews) {this.storeReviews = storeReviews;}

    public String getStoreTarget() {        return storeTarget;    }
    public void setStoreTarget(String storeTarget) { this.storeTarget = storeTarget; }

    // toString()을 Override 해주지 않으면 객체 주소값을 출력함
    @Override
    public String toString() {
        return "StoreItem{" +
                "storeName=" + storeName +
                ", storeAddrs=" + storeAddrs +
                ", storeNum=" + storeNum +
                ", storeTime=" + storeTime +
                ", storeCategory=" + storeCategory +
                ", storeOffer=" + storeOffer +
                ", storeId=" + storeId +
                ", storeTarget=" + storeTarget +
                ", storeReviews=" + storeReviews +
                '}';
    }

}
