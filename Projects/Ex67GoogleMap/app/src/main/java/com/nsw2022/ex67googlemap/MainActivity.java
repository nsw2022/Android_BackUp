package com.nsw2022.ex67googlemap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //xml에 추가한 SupportMapFragment를 찾아와서 참조하기
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);


        // 비동기 방식(Thread를 이용)으로 구글 서버에서 맵의 데이터를 읽어오도록... 비동기(너이거해!)
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                // 여기가 실행된다면 지도 데이터가 온전히 잘 불러진것임.

                // 마커가 표시될 좌표객체
                LatLng mrhi = new LatLng(37.5653, 127.0253);

                // 마커객체
                MarkerOptions marker = new MarkerOptions();
                marker.title("미래IT캠퍼스");
                marker.snippet("왕십리에 있는 미래IT캠퍼스");
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maker_mrhi)); //백터이미지는 안됨.
                marker.position(mrhi);


                googleMap.addMarker(marker);

                // 지도를 원하는 위치로 이동 [ 지도를 보여주는 카메라 이동 ]
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mrhi, 17));//줌 :1~25

                // 마커를 클릭시에 보여지는 InfoWindow(툴팁박스) 를 클릭했을때 반응하기
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(@NonNull Marker marker) {
                        // "미래 IT 캠퍼스" 교육원 홈페이지를 실행 - 크롬브라우저 앱이 실행되도록.
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://www.mrhi.or.kr"));
                        startActivity(intent);
                    }
                });

                // 지도의 대표적인 설정들 - xml에서도 적용 가능함  [ 구글 개발자 사이트의 가이드문서 참고 ]
                UiSettings settings = googleMap.getUiSettings();
                settings.setZoomControlsEnabled(true);

                // 내 위치탐색버튼
                settings.setMyLocationButtonEnabled(true);

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);

            }
        });


        // 내 위치정보제공에 대한 동적 퍼미션
        String[] permissions=new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        if (checkSelfPermission(permissions[0])== PackageManager.PERMISSION_DENIED); requestPermissions(permissions,100);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "내 위치 탐색 허용", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "내 위치 탐색 불허", Toast.LENGTH_SHORT).show();
        }
    }
}