package com.nsw2022.ex62service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

// 서비스는 4대 컴포넌트여서 AndroidManifest.xml에 등록
public class MyService extends Service {

    MediaPlayer mp;

    // startService()를 통해 이 서비스객체가 실행되면 자동으로 발동하는 라이프사이클 메소드
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "START", Toast.LENGTH_SHORT).show();

        // startForegroundService()로 실행된 서비스가 진짜로 kill 되지 않으려면..
        // 이 곳에서 foreground로 서비스 한다고 명시 해야만 함.
        // 사용자에게 백그라운드 동작이 되고 있다고 알려주기 위해 알림(Notification)을 무조건
        // 만들어야만 하는 기술.

        NotificationManager notificationManager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=null;

        if(Build.VERSION.SDK_INT>=26){
            NotificationChannel channel= new NotificationChannel("ch1","Ex62 앱", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);

            builder= new NotificationCompat.Builder(this, "ch1");
        }else{
            builder= new NotificationCompat.Builder(this, "");
        }

        builder.setSmallIcon(R.drawable.ic_noti);
        builder.setContentTitle("Music Service");
        builder.setContentText("뮤직서비스가 실행됩니다.");

        //알림창을 클릭하여 서비스 제어 버튼이 있는 MainActivity가 실행되도록.
        Intent i= new Intent(this, MainActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 100, i, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        //알림객체 생성
        Notification notification= builder.build();
        startForeground(1, notification); //포어그라운드에서 돌아가려면 퍼미션 필요(매니페스트에 작성)


        // MediaPlayer 생성 및 실행
        if( mp == null ){
            mp= MediaPlayer.create(this, R.raw.kalimba);
            mp.setVolume(0.7f, 0.7f);
            mp.setLooping(true);
        }

        mp.start();

        return START_STICKY;
    }

    // stopService()를 통해 이 서비스객체를 종료시키면 자동으로 발동하는 라이프사이클 메소드
    @Override
    public void onDestroy() {
        Toast.makeText(this, "DESTROY", Toast.LENGTH_SHORT).show();

        // MediaPlayer 종료
        if( mp != null){
            mp.stop();
            mp.release();
            mp= null;
        }

        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
