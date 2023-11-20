package com.nsw2022.ex68kakaomap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;


import com.kakao.util.maps.helper.Utility;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MainActivity extends AppCompatActivity {

    // 카카오 개발자 사이트 가이드 문서 참고..
    // 실 디바이스에서만 작동 단, Mac, M1, M2 chip 사용 에뮬레이터는 동작 됨.

    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 키 해시값을 얻어오는 기능을 가진 클래스에게 디버그용 키해시값 얻어오기
        String keyHash= Utility.getKeyHash(this);
        Log.d("TAG",keyHash);

        mapView=new MapView(this);
        ViewGroup mapContainer = findViewById(R.id.map_container);
        mapContainer.addView(mapView);

        // 중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);

        // 줌 레벨 변경
                mapView.setZoomLevel(7, true);

        // 중심점 변경 + 줌 레벨 변경
                mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(33.41, 126.52), 9, true);

        // 줌 인
                mapView.zoomIn(true);

        // 줌 아웃
                mapView.zoomOut(true);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("미래IT캠퍼스");
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.5653, 127.0254));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);

        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.5653, 127.0254), 5, true);
    }
}