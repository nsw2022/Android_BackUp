package com.nsw2022.ex66locationgeocoding;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Geocoder 는 현재 실디바이스 에서만 테스트 가능함. 단, Mac M1, M2 chip 사용하는 AVD는 동작함함

    EditText etAddress;
    EditText etLat, etLng;

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAddress= findViewById(R.id.et_address);
        etLat= findViewById(R.id.et_lat);
        etLng= findViewById(R.id.et_lng);

        findViewById(R.id.btn).setOnClickListener(v->clickBtn());
        findViewById(R.id.btn2).setOnClickListener(v->clickBtn2());
        findViewById(R.id.btn3).setOnClickListener(view -> clickBtn3());
    }

    void clickBtn(){
        // 주소 --> 좌표 ( 지오코딩 ) - 구글 서버에서 변환자료를 가져오기에 인터넷 퍼미션 필요
        String address= etAddress.getText().toString();

        // 지오코딩 작업을 수행해주는 객체 생성
        Geocoder geocoder= new Geocoder(this, Locale.KOREAN);

        try {
            List<Address> addresses= geocoder.getFromLocationName(address, 3);

            StringBuffer buffer= new StringBuffer();
            for( Address addr : addresses ){
                double latitude= addr.getLatitude();
                double longitude= addr.getLongitude();

                buffer.append( latitude +" , " + longitude +"\n" );
            }

            new AlertDialog.Builder(this).setMessage(buffer.toString()).create().show();

        } catch (IOException e) {
            Log.i("TAG", e.getMessage());
            Toast.makeText(this, "검색실패 : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void clickBtn2(){
        // 좌표 --> 주소 ( 역 지오코딩 )

        latitude = Double.parseDouble(etLat.getText().toString());
        longitude = Double.parseDouble(etLng.getText().toString());

        // 지오코딩을 해주는 객체 생성
        Geocoder geocoder = new Geocoder(this,Locale.KOREAN);

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,3);

            StringBuffer buffer = new StringBuffer();
            for ( Address addr: addresses ){
                buffer.append( addr.getCountryName() + "\n");//나라 이름
                buffer.append(addr.getPostalCode() + "\n");  //우편번호
                buffer.append(addr.getAddressLine(0) + "\n"); //주소1 : 도로명
                buffer.append(addr.getAddressLine(1) + "\n"); //주소2 : 상세주소 - 없으면 null
                buffer.append(addr.getAddressLine(2) + "\n"); //주소3 : 상세주소 - 없으면 null
                buffer.append("===================================\n\n");

            }

            new AlertDialog.Builder(this).setMessage(buffer.toString()).create().show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void clickBtn3(){
        // 지도앱을 실행시켜주는 묵시적 Intent
        Intent intent = new Intent();
        intent.setAction( Intent.ACTION_VIEW );

        // 지도 좌표 정보 Uri
        Uri uri = Uri.parse("geo:"+latitude+","+longitude + "?z=15");
        intent.setData(uri);

        startActivity(intent);
    }
}