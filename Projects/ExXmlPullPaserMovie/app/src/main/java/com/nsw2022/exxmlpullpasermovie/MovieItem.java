package com.nsw2022.exxmlpullpasermovie;

//영화 1개의 정보를 저장하기 위한 데이터용 class
public class MovieItem {

    String rank;                //순위
    String movieNm;             //영화이름
    String openDt;              //개봉날짜
    String audiAcc;             //누적관객수

    public MovieItem(String rank, String movieNm, String openDt, String audiAcc) {
        this.rank = rank;
        this.movieNm = movieNm;
        this.openDt = openDt;
        this.audiAcc = audiAcc;
    }

    public MovieItem() {
    }
}
