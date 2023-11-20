package com.nsw2022.ex28recyclerview2;

public class Item {

    String name;
    String message;

    int profileImgID;
    int imgId;

    public Item(String name, String mssage, int profileImgID, int imgId) {
        this.name = name;
        this.message = mssage;
        this.profileImgID = profileImgID;
        this.imgId = imgId;
    }

    public Item() {
    }
}
