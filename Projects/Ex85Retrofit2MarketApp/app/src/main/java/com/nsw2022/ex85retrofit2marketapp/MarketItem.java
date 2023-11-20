package com.nsw2022.ex85retrofit2marketapp;

public class MarketItem {
    // market 테이블에 만들어진 column 의 필드들
    int no;
    String name;
    String title;
    String msg;
    String price;
    String file;
    String date;

    public MarketItem(int no, String name, String title, String msg, String price, String file, String date) {
        this.no = no;
        this.name = name;
        this.title = title;
        this.msg = msg;
        this.price = price;
        this.file = file;
        this.date = date;
    }

    public MarketItem() {
    }
}
