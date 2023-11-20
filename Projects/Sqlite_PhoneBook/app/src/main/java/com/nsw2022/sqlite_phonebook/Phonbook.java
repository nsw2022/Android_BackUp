package com.nsw2022.sqlite_phonebook;

public class Phonbook {

    String phone_id;
    String phone_name;
    String phone_number;
    byte[] user_image;

    public Phonbook() {
    }

    public Phonbook(String phone_id, String phone_name, String phone_number, byte[] user_image) {
        this.phone_id = phone_id;
        this.phone_name = phone_name;
        this.phone_number = phone_number;
        this.user_image = user_image;
    }
}
