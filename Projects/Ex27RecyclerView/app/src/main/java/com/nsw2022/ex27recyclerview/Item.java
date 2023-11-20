package com.nsw2022.ex27recyclerview;

public class Item {
    String name, message;

    public Item(String name, String message) {
        this.name = name;
        this.message = message;
    }

    //가급적 생성자는 오버로딩
    public Item() {
    }
}
