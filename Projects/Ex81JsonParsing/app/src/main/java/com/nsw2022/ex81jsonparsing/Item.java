package com.nsw2022.ex81jsonparsing;

public class Item {
    int no;
    String name;
    Address addr;

    public Item(int no, String name, Address addr) {
        this.no = no;
        this.name = name;
        this.addr = addr;
    }
}

class Address {
    String nation;
    String citiy;

    public Address(String nation, String citiy) {
        this.nation = nation;
        this.citiy = citiy;
    }
}
