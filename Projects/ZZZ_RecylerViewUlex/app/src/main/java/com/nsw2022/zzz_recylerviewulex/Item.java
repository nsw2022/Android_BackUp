package com.nsw2022.zzz_recylerviewulex;

import android.widget.ImageView;

public class Item {

    int imgId;
    int RantingBar;
    String scoreInt;
    String score;
    String price;

    public Item(int imgId, int rantingBar, String scoreInt, String score, String price) {
        this.imgId = imgId;
        RantingBar = rantingBar;
        this.scoreInt = scoreInt;
        this.score = score;
        this.price = price;
    }

    public Item() {
    }
}
