package com.nsw2022.ex65locationfusedapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class MainActivity extends AppCompatActivity {

    // Google Map 에서 사용하고 있는 위치정보탐색 라이브러리 : Fused API
    // 위치정보제공자를 알아서 최적으로 찾아서 갱신해줌.
    // 라이브러리를 추가 해줘야 함. play-services-location 로 검색

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        findViewById(R.id.btn).setOnClickListener(v -> clickBtn());

        // 내 위치정보에 대한 동적 퍼미션 필요
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        int checkResult = checkSelfPermission(permissions[0]);
        if (checkResult == PackageManager.PERMISSION_DENIED) {
            requestPermissions(permissions, 10);
        }
    }//onCreate method..

    // 퍼미션요청 다이얼로그의 선택결과를 알려주는 콜백 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 10 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "내 위치정보 제공에 동의하셨습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "위치정보 사용 불가능", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    //위치정보 제공자를 이용하는 객체 ( 알아서 적절한 위치정보제공자를 선택 ) 참조변수
    FusedLocationProviderClient providerClient;

    void clickBtn() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //위치정보제공자를 이용하는 객체 생성
        providerClient = LocationServices.getFusedLocationProviderClient(this);

        // 적절한 위치정보제공자를 선택하는 기준을 가진 객체
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY); //높은 정확도 우선 : "gps"
        locationRequest.setInterval(5000); //위치정보 탐색 주기 : 5초마다

        providerClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    //업데이트된 위치정보의 결과를 받을때 반응하는 Callback 객체
    LocationCallback locationCallback= new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            //마지막 확인된 위치정보 얻기
            Location location= locationResult.getLastLocation();
            double latitude= location.getLatitude();
            double longitude= location.getLongitude();

            tv.setText( latitude +" , " + longitude);
        }
    };


    // 액티비티가 화면에 보이지 않기 시작할때 자동으로 발동하는 라이프사이클 콜백메소드
    @Override
    protected void onPause() {
        super.onPause();

        if( providerClient!=null ){
            providerClient.removeLocationUpdates(locationCallback);
        }
    }
}//MainActivity class..