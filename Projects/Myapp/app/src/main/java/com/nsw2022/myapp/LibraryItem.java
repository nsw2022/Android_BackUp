package com.nsw2022.myapp;

import android.widget.TextView;

public class LibraryItem {
    // 도서관 이름 공휴일 전화번호 주소

    String LBRRY_NAME;            //이름
    String FDRM_CLOSE_DATE;       //공휴일
    String TEL_NO;                //도서관번호
    String ADRES;                 //주소

    public LibraryItem(String LBRRY_NAME, String FDRM_CLOSE_DATE, String TEL_NO, String ADRES) {
        this.LBRRY_NAME = LBRRY_NAME;
        this.FDRM_CLOSE_DATE = FDRM_CLOSE_DATE;
        this.TEL_NO = TEL_NO;
        this.ADRES = ADRES;
    }

    public LibraryItem() {
    }


}
