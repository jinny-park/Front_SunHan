package com.example.front_sunhan.Model;

public class LetterItem {
    String letterName;
    String letterContent;
    String letterDate;

    public LetterItem(String letterName, String letterContent, String letterDate) {
        this.letterName = letterName;
        this.letterContent = letterContent;
        this.letterDate=letterDate;
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

}
