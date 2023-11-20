package com.nsw2022.ex88firebasechatting;

import java.util.Calendar;

public class MessageItem {
    public String nickName ;
    public String message ;
    public String profileUrl;
    public String time ;

    public MessageItem(String nickName, String message, String profileUrl, String time) {
        this.nickName = nickName;
        this.message = message;
        this.profileUrl = profileUrl;
        this.time = time;
    }

    public MessageItem() {
    }
}
