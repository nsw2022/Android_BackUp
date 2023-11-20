package com.nsw2022.ex74alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //알람매니저 참조변수
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 운영체제로 부터 알람 매니저 소환
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        findViewById(R.id.btn).setOnClickListener(view -> clickBtn());
        findViewById(R.id.btn2).setOnClickListener(view -> clickBtn2());
        findViewById(R.id.btn3).setOnClickListener(view -> clickBtn3());



    }//onCreate

    void clickBtn(){
        // 알람시계 앱을 실행하는 인텐트 - 퍼미션 필요
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR,10);
        intent.putExtra(AlarmClock.EXTRA_MINUTES,30);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE,"TEST ALARM");

        ArrayList<Integer> days=new ArrayList<Integer>();
        days.add(Calendar.MONDAY);
        days.add(Calendar.WEDNESDAY);
        intent.putExtra(AlarmClock.EXTRA_DAYS,days);

        intent.putExtra(AlarmClock.EXTRA_SKIP_UI,true);//알람확인 UI를 스킵

        startActivity(intent);

    }

    void clickBtn2() {
        // 알람앱이 아니라 시스템에 있는 알람 매니저에게 직접 알람을 설정 - android12 버전(가장최근이 12) 버전부터 퍼미션 필요

        // 10초후로 알람을 설정

        // 알람설정시간에 실행될 컴포넌트(Activity, Service, BroadcastReceiver) 실행시켜주는 Intent - 최신버전은 Activity 실행 불가
        Intent intent = new Intent(this,AlarmReceiver.class);
        // 알람시간까지 잠시 방송을 보류
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10000, pendingIntent);
    }

    void clickBtn3() {
        // 특정 날짜 설정

        // 현재날짜가 기본으로 보여지도록
        // 날짜 관련된거 두개 Date, Calendar
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        new DatePickerDialog(this, dateSetListener , year ,month, day).show();
    }

    // 특정 날짜와 시간을 저장하는 멤버변수
    int year, month, day;
    int hour, minute;

    // 날짜선택에 반응하는 리스너 객체 생성
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            // 파라미터 3개 i, i1, i2 : year, month, day
            year = i;     //2022
            month = i1;   //8[9월]      0~11 (1월이 0임)
            day=i2;       //

            // 시간을 선택하는 다이얼로그를 보이기.
            Calendar calendar =Calendar.getInstance();
            hour=calendar.get(Calendar.HOUR_OF_DAY); //0~23
            minute=calendar.get(Calendar.MINUTE);// 0~59

            new TimePickerDialog(MainActivity.this,timeSetListener,hour,minute,true).show();


        }
    };

    // 타임 선택에 반응하는 리스너 객체 생성
    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            // 파라미터 2개 i, i1 : hour minute
            hour = i;
            minute = i1;

            //Toast.makeText(MainActivity.this, year + "/" + month + "/" + day + "\n" + hour + "/" + minute, Toast.LENGTH_SHORT).show();

            // 선택한 날짜와 시간으로 Calender 객체 생성
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,day,hour,minute,0);

            //선택한 시간으로 알람을 설정 - 알람시간시에는 AlarmService를 실행하도록.
            Intent intent = new Intent(MainActivity.this,AlarmService.class);
            //알람시간까지 잠시 보류하는 Intent로 변환
            PendingIntent pendingIntent = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                pendingIntent=PendingIntent.getForegroundService(MainActivity.this,11,intent,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);

            }else{
                pendingIntent=PendingIntent.getService(MainActivity.this,11,intent,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
            }

            // 알람매니저에게 알람 설정
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);


        }
    };

}//Main
