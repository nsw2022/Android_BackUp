package com.nsw2022.ex25listviewcustom;

public class Item {
    String name;    //전현무
    String nation;  //국가
    int imgId;      //R.drawable.korea

    // 생성(new)할때 자동으로 실행되는 메소드
    //생성자
    public Item(String name,String nation,int imgId){
        this.name=name;
        this.nation=nation;
        this.imgId=imgId;

    }


}
