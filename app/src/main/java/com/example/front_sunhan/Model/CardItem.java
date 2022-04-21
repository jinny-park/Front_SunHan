package com.example.front_sunhan.Model;

public class CardItem {
    
    String name;
    String account;
    String cardName;
    String money;
    String bday;

    public CardItem(String name, String account, String cardName, String money, String bday) {
        this.name = name;
        this.account = account;
        this.cardName = cardName;
        this.money = money;
        this.bday = bday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }
}
