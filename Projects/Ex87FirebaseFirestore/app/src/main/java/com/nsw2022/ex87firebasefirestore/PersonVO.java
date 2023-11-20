package com.nsw2022.ex87firebasefirestore;

public class PersonVO {
    // firebase에 곧바로 저장할 객체라면 멤버가 반드시 public이어야함
    public String name;
    public int age;
    public String address;

    public PersonVO() {
    }

    public PersonVO(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }
}
