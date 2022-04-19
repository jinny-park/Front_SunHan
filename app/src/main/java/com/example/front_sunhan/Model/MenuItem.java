package com.example.front_sunhan.Model;

public class MenuItem {
    String menuName;
    String menuPrice;

    public MenuItem(String menuName, String menuPrice) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public String getMenuName() {
        return menuName;
    }
    public void setMenuName(String menuName) { this.menuName = menuName; }

    public String getMenuPrice() {
        return menuPrice;
    }
    public void setMenuPrice(String menuPrice) { this.menuPrice = menuPrice; }

}
