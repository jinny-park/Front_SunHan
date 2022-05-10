package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LetterItem {

    @SerializedName("writer") private String letterName;
    @SerializedName("_id") private String _id;
    @SerializedName("sunhanId") private String sunhanId;
    @SerializedName("content") private String letterContent;
    @SerializedName("blockNumber") private String blockNumber;
    @SerializedName("createAt") private String letterDate;
    @SerializedName("updateAt") private String updateAt;


    public LetterItem(String letterName, String letterContent, String letterDate) {
        this.letterName = letterName;
        this.letterContent = letterContent;
        this.letterDate=letterDate;
        this._id=_id;
        this.sunhanId=sunhanId;
        this.blockNumber=blockNumber;
        this.updateAt=updateAt;
    }

    public String getLetterName() {
        return letterName;
    }
    public void setLetterName(String letterName) { this.letterName = letterName; }

    public String getLetterContent() {
        return letterContent;
    }
    public void setLetterContent(String letterContent) { this.letterContent = letterContent; }

    public String getLetterDate() {
        return letterDate;
    }
    public void setLetterDate(String letterDate) { this.letterDate = letterDate; }

    public String getUpdateAt() {        return updateAt;    }
    public void setUpdateAt(String updateAt) {        this.updateAt = updateAt;    }

    public String getBlockNumber() {        return blockNumber;    }
    public void setBlockNumber(String blockNumber) {        this.blockNumber = blockNumber; }

    public String getSunhanId() {        return sunhanId;    }
    public void setSunhanId(String sunhanId) {        this.sunhanId = sunhanId;    }

    public String get_id() {        return _id;    }
    public void set_id(String _id) {        this._id = _id;    }

}
