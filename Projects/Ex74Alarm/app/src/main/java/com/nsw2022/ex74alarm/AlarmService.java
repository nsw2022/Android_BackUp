package com.nsw2022.ex74alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // 알림객체 생성 - 건축가
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        NotificationChannelCompat notificationChannel = new NotificationChannelCompat.Builder("ch1",NotificationManagerCompat.IMPORTANCE_HIGH).setName("alarm channel").build();
        notificationManager.createNotificationChannel(notificationChannel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"ch1");

        builder.setSmallIcon(R.drawable.ic_noti);
        builder.setContentTitle("My Alarm");
        builder.setContentText("알람이 시작되었습니다");

        Intent i = new Intent(this, AlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,200,i,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        builder.addAction(R.drawable.ic_noti,"확인",pendingIntent);

        Notification notification=builder.build();

        // 포어그라운드로 동작한다고 명시해야함. <- 이걸안하면 백그라운드에서 운영체제가 죽임
        startForeground(100,notification);


        // 알림설정 화면이 켜져있다면.. 곧바로 액티비티 실행도 가능함.

        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopForeground(true);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
