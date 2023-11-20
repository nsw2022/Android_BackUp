package com.nsw2022.ex80httprequestdb;

//DB 한줄의 값을들 저장하는 Data용 클래스 [VO클래스 : Value Object]
public class BoardItem {
    int no;
    String title;
    String msg;
    String date;

    public BoardItem() {
    }

    public BoardItem(int no, String title, String msg, String date) {
        this.no = no;
        this.title = title;
        this.msg = msg;
        this.date = date;
    }
}
