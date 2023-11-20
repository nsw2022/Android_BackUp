package com.nsw2022.ex64location

import android.Manifest
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.location.LocationManager
import android.os.Bundle
import com.nsw2022.ex64location.R
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.core.app.ActivityCompat
import android.location.LocationListener
import android.view.View

class MainActivity : AppCompatActivity() {
    var tv: TextView? = null
    var tv2: TextView? = null

    //위치정보관리자 참조변수
    var locationManager: LocationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 위치정보관리자 객체 소한
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        tv = findViewById(R.id.tv)
        tv2 = findViewById(R.id.tv2)
        findViewById<View>(R.id.btn).setOnClickListener { view: View? -> clickBtn() }
        findViewById<View>(R.id.btn2).setOnClickListener { view: View? -> clickBtn2() }
        findViewById<View>(R.id.btn3).setOnClickListener { view: View? -> clickBtn3() }

        //내 위치정보를 얻어오려면 반드시 동적 퍼미션 필요
        // 퍼미션을 받았는지 부터 확인
        val checkResult = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        if (checkResult == PackageManager.PERMISSION_DENIED) { // 퍼미션이 거부되어 있을때.
            // 사용자에게 퍼미션을 요청하는 다이얼로그 보여주는 기능 메소드 호출
            val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissions, 10)
        }
    } //onCreate

    // requestPermissions() 메소드를 통해 보여진 다이얼로그에서 [ 허가/거부 ]를 선택했을때
    // 자동으로 실행되는 콜백메소드
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 10) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "위치정보제공에 동의하셨습니다", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "위치정보 사용 불가", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun clickBtn() {

        // 명시적으로 퍼미션을 체크하는 코드가 있어야만 아래 있는 getLastKnownLocation()기능 사용이 가능함
        // 퍼미션이 허가 되어 있지 않다면 하지마!!
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        //위치정보 객체 참조변수
        var location: Location? = null
        if (locationManager!!.isProviderEnabled("gps")) {
            location = locationManager!!.getLastKnownLocation("gps")
        } else if (locationManager!!.isProviderEnabled("network")) {
            location = locationManager!!.getLastKnownLocation("network")
        }
        if (location == null) {
            tv!!.text = "내 위치를 못 찾겠어!"
        } else {
            // 위도,경도 얻어내기
            val latitude = location.latitude
            val longitude = location.longitude
            tv!!.text = "$latitude , $longitude"
        }
    }

    //실시간 내위치 업데이트
    fun clickBtn2() {
        // 명시적으로 퍼미션을 체크하는 코드가 있어야만 아래 있는 getLastKnownLocation()기능 사용이 가능함
        // 퍼미션이 허가 되어 있지 않다면 하지마!!
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        if (locationManager!!.isProviderEnabled("gps")) {
            locationManager!!.requestLocationUpdates("gps", 5000, 2f, locationListener)
        } else if (locationManager!!.isProviderEnabled("network")) {
            locationManager!!.requestLocationUpdates("network", 5000, 2f, locationListener)
        }
    }

    // 내위치 자동갱신 종료
    fun clickBtn3() {
        //업데이트 제거
        locationManager!!.removeUpdates(locationListener)
    }

    // 반경안에 들어가있는가?
    var isEnter = false

    // 멤버변수 영역에 위치정보 갱신을 듣는 리스너
    var locationListener = LocationListener { location ->
        val latitude = location.latitude
        val longitude = location.longitude
        tv2!!.text = "$latitude , $longitude"

        // 내가 특정 지점에 들어갔을대 이벤트 발생 ( 다이얼로그 보이기 )
        // 상왕십리역 좌표 : 37.5647, 127.0291

        // 내 위치( latitude, longitude)와 상황십리역 사이의 실제 거리(m)
        val result = FloatArray(3) //거리계산결과를 저장할 빈 배열 객체
        Location.distanceBetween(latitude, longitude, 37.5647, 127.0291, result)

        // results[0]에 두 좌표사이의 m 거리가 저장되어 있음.
        if (result[0] < 50) { //두좌표거리의 사이가 50미터 이내이면...
            if (!isEnter) {
                AlertDialog.Builder(this@MainActivity).setMessage("축하합니다~ 미션달성!").create().show()
                isEnter = true
            }
        } else {
            isEnter = false
        }
    }
}