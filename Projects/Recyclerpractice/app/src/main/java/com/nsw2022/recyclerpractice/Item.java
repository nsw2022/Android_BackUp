package com.nsw2022.recyclerpractice;

public class Item {
    String name;
    String message;

    int profileImgID;
    int imgId;

    public Item() {
    }

    public Item(String name, String message, int profileImgID, int imgId) {
        this.name = name;
        this.message = message;
        this.profileImgID = profileImgID;
        this.imgId = imgId;
    }
}
