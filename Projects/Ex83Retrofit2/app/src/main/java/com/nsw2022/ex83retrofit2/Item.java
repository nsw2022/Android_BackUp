package com.nsw2022.ex83retrofit2;

public class Item {
    int no;        //이 멤버변수의 이름은 반드시 json문자열의 식별자와 일치해야 함.
    String title;
    String msg;

    public Item(int no, String title, String msg) {
        this.no = no;
        this.title = title;
        this.msg = msg;
    }

    public Item() {
    }
}
