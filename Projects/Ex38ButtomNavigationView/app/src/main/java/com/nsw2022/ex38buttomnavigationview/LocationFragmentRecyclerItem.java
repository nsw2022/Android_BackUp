package com.nsw2022.ex38buttomnavigationview;

import androidx.fragment.app.Fragment;

public class LocationFragmentRecyclerItem {
    String title;
    int imgId;
    double rating;

    public LocationFragmentRecyclerItem(String title, int imgId, double rating) {
        this.title = title;
        this.imgId = imgId;
        this.rating = rating;
    }

    public LocationFragmentRecyclerItem() {
    }
}
