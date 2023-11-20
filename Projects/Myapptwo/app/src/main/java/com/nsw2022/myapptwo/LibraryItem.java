package com.nsw2022.myapptwo;

public class LibraryItem {

    String LBRRY_NAME;          //도서관이름
    String CODE_VALUE;          //구주소
    String ADRES;               //상세주소
    String TEL_NO;              //번호
    String FDRM_CLOSE_DATE;     //공휴일

    public LibraryItem(String LBRRY_NAME, String CODE_VALUE, String ADRES, String TEL_NO, String FDRM_CLOSE_DATE) {
        this.LBRRY_NAME = LBRRY_NAME;
        this.CODE_VALUE = CODE_VALUE;
        this.ADRES = ADRES;
        this.TEL_NO = TEL_NO;
        this.FDRM_CLOSE_DATE = FDRM_CLOSE_DATE;
    }

    public LibraryItem() {
    }
}
