package com.nsw2022.ex91kotlinrecyclerview

// 리사이클러클러 뷰가 보여줄 Item 1개의 뷰안에 보여줄 값들을 저장할 데이터용 클래스
// 내용이 없다면 클래스의 {}도 생략가능함.
// data class : 일반 class 와 다르게 .equals()메소드를 오버라이드 하여
//              객체간의 주소값을 비교하지 않고 멤버값을 비교해주도록 셜계되는 특별한 class
data class Item constructor(var title:String,var msg:String, var img:Int)