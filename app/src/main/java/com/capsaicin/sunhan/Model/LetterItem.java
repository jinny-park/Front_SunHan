package com.capsaicin.sunhan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LetterItem {
    String letterName;
    String letterContent;
    String letterDate;

/*
    @SerializedName("reviews")
    private String storeLetter;
    @SerializedName("reviews")
    private String storeLetter;
    @SerializedName("reviews")
    private String storeLetter;
    @SerializedName("reviews")
    private String storeLetter;
*/

    public LetterItem(String letterName, String letterContent, String letterDate) {
        this.letterName = letterName;
        this.letterContent = letterContent;
        this.letterDate=letterDate;
    }

//    public String getStoreLetter() {
//        return storeLetter;
//    }
//    public void setStoreLetter(String storeLetter) { this.storeLetter = storeLetter; }
//

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

}
