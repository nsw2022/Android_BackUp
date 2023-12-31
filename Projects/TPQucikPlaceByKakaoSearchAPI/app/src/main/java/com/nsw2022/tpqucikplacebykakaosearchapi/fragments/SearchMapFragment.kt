package com.nsw2022.tpqucikplacebykakaosearchapi.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nsw2022.tpqucikplacebykakaosearchapi.activities.MainActivity
import com.nsw2022.tpqucikplacebykakaosearchapi.activities.PlaceURLActivity
import com.nsw2022.tpqucikplacebykakaosearchapi.databinding.FragmentSearchListBinding
import com.nsw2022.tpqucikplacebykakaosearchapi.databinding.FragmentSearchMapBinding
import com.nsw2022.tpqucikplacebykakaosearchapi.model.Place
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class SearchMapFragment :Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return binding.root
    }
    val binding:FragmentSearchMapBinding by lazy{ FragmentSearchMapBinding.inflate(layoutInflater)}

    val mapView: MapView by lazy { MapView(context) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 맴뷰를 뷰그룹에 추가하여 화면에 배치하도록.
        binding.containerMapview.addView(mapView)

        // 마커 or 말풍선을 클릭햇을때 반응하기 - 반드시 마커 설정보다 먼저 맵뷰에 설정해놓아야 동작
        mapView.setPOIItemEventListener(markerEventListener)

        //지도에 관련된 설정들.. [ 지도위치, 마커추가 등 ]
        setMapandMarkers()



    }
    private fun setMapandMarkers(){
        // 맵중심점을 내 위치로 변경
        // 현재 내위치 정보.. 는 MainActivity 의 멤버변수로 저장되어 있음.
        var latitude:Double = (activity as MainActivity).mylocation?.latitude ?: 37.566805
        var longitude:Double = (activity as MainActivity).mylocation?.longitude ?: 126.978417

        var myMapPoint:MapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
        mapView.setMapCenterPointAndZoomLevel(myMapPoint, 5, true)
        mapView.zoomIn(true)
        mapView.zoomOut(true)

        // 내 위치에 마커 표시
        var marker = MapPOIItem()
        marker.apply {
            itemName="ME"
            mapPoint = myMapPoint
            markerType=MapPOIItem.MarkerType.BluePin
            selectedMarkerType=MapPOIItem.MarkerType.YellowPin
        }
        mapView.addPOIItem(marker)

        // 검색결과 장소들.. 마커들을 추가하기
        val documents:MutableList<Place>? = (activity as MainActivity).searchPlaceResponse?.documents
        documents?.forEach{
            val point:MapPoint = MapPoint.mapPointWithGeoCoord(it.y.toDouble(),it.x.toDouble())

            // 마커옵션 객체를 만들어서
            val marker:MapPOIItem = MapPOIItem().apply {
                itemName=it.place_name
                mapPoint=point
                markerType=MapPOIItem.MarkerType.RedPin
                selectedMarkerType=MapPOIItem.MarkerType.YellowPin

                // 마커에 표시되지는 않지만 저장하고 싶은 사용자정보가 있다면..
                userObject= it
            }
            mapView.addPOIItem(marker)

        }//forEach
    }

    //마커나 말풍선이 클릭되는 이벤트에 반응하는 리스너
    private val markerEventListener : MapView.POIItemEventListener = object : MapView.POIItemEventListener{
        override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {

        }

        override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {

        }

        //말풍선 클릭했을때 반응하는 콜백메소드
        override fun onCalloutBalloonOfPOIItemTouched(
            p0: MapView?,
            p1: MapPOIItem?,
            p2: MapPOIItem.CalloutBalloonButtonType?
        ) {
            // 두번째 파라미터 p1 : 클릭한 말풍선의 마커객체
            if (p1?.userObject == null) return

            var place:Place = p1?.userObject as Place

            // 장소의 상세정보 URL을 가지고 상세정보 웹페이지를 보여주는 화면으로 전환
            val intent: Intent = Intent(context,PlaceURLActivity::class.java)
            intent.putExtra("place_url",place.place_url)
            startActivity(intent)
        }

        override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {

        }

    }


}