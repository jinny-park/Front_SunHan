package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChildrenItem {

    @SerializedName("_id") private String childId;
    @SerializedName("name") private String childName;
    @SerializedName("address") private String childAddrs;
    @SerializedName("phoneNumber") private String childNum;
    @SerializedName("weekdayStartTime") private String childWeekStartTime;
    @SerializedName("weekdayEndTime") private String childWeekEndTime;
    @SerializedName("weekendStartTime") private String childWkndStartTime;
    @SerializedName("weekendEndTime") private String childWkndEndTime;
    @SerializedName("holydayStartTime") private String childHoliStartTime;
    @SerializedName("holydayEndTime") private String childHoliEndTime;
    @SerializedName("category") private String childCategory;
    @SerializedName("reviews")
    ArrayList< Object > childReviews = new ArrayList < Object > ();


    public ChildrenItem(String childName, String childAddrs, String childNum, String childWeekStartTime /*,String childId,
                        String childWeekEndTime, String childWkndStartTime, String childWkndEndTime, String childHoliStartTime,
                        String childHoliEndTime, String childCategory, ArrayList< Object > childReviews*/) {
        this.childName = childName;
        this.childAddrs = childAddrs;
        this.childNum=childNum;
        this.childWeekStartTime=childWeekStartTime;
        this.childId=childId;
        this.childWeekEndTime=childWeekEndTime;
        this.childWkndStartTime=childWkndStartTime;
        this.childWkndEndTime=childWkndEndTime;
        this.childHoliStartTime=childHoliStartTime;
        this.childHoliEndTime=childHoliEndTime;
        this.childCategory=childCategory;
        this.childReviews=childReviews;
    }

    public String getChildName() {return childName;}
    public void setChildName(String childName) { this.childName = childName; }

    public String getChildAddrs() {        return childAddrs;    }
    public void setChildAddrs(String childAddrs) { this.childAddrs = childAddrs; }

    public String getChildNum() {        return childNum;    }
    public void setChildNum(String childNum) { this.childNum = childNum; }

    public String getChildId() {return childId;    }
    public void setChildId(String childId) {        this.childId = childId;    }

    public String getChildWeekStartTime() {return childWeekStartTime;    }
    public void setChildWeekStartTime(String childWeekStartTime) {this.childWeekStartTime = childWeekStartTime;    }

    public String getChildWeekEndTime() {        return childWeekEndTime;    }
    public void setChildWeekEndTime(String childWeekEndTime) {        this.childWeekEndTime = childWeekEndTime;    }

    public String getChildWkndStartTime() {        return childWkndStartTime;    }
    public void setChildWkndStartTime(String childWkndStartTime) {        this.childWkndStartTime = childWkndStartTime;    }

    public String getChildWkndEndTime() {        return childWkndEndTime;    }
    public void setChildWkndEndTime(String childWkndEndTime) {        this.childWkndEndTime = childWkndEndTime;    }

    public String getChildHoliStartTime() {        return childHoliStartTime;    }
    public void setChildHoliStartTime(String childHoliStartTime) {        this.childHoliStartTime = childHoliStartTime;    }

    public String getChildHoliEndTime() {        return childHoliEndTime;    }
    public void setChildHoliEndTime(String childHoliEndTime) {        this.childHoliEndTime = childHoliEndTime;    }

    public String getChildCategory() {        return childCategory;    }
    public void setChildCategory(String childCategory) {        this.childCategory = childCategory;    }

    public ArrayList<Object> getChildReviews() {        return childReviews;    }
    public void setChildReviews(ArrayList<Object> childReviews) {        this.childReviews = childReviews;    }


}
