package com.nsw2022.ex89firebasecloudmessagepush;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFCMService extends FirebaseMessagingService {
    // FCM 서버에 디바이스 고유 등록 ID(토큰)이 발급 되었을때 자동으로 발동하는 콜백 메소드
    // Manifest.mxl 에 이 서비스가 등록되어 있다면 앱을 실행만 하면 자동으로 발동함.[처음 한번만 발동함]
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        // 발급된 Token 확인해보기 - 현업에서는 이 토큰값을 웹서버(닷홈 같은 곳)에 보내서 저장 되도록 해야함
        Log.i("TOKEN",token);

    }

    // FCM push Message 의 2가지 유형 : 알림, 데이터

    //1. FCM 알림 메세지 유형 수신
    // 1) 앱이 켜져있을때 (Foreground 상태) - onMessageReceived() 메소드 호출되며 알림이 자동으로 만들어지지 않습니다
    // 2) 앱이 꺼져있을때 (Background 상태) - onMessageReceived() 메소드 호출되지 않으며 default(기본) 알림이 공지됨. - 데이터메세지는 Intent에 Extra-data로 전달됨.

    //2. FCM 데터 메세지 유형 수신
    // 1) 앱이 켜져있을때 (Foreground 상태) - onMessageReceived() 메소드 호출되며 알림이 자동으로 만들어지지 않습니다
    // 2) 앱이 꺼져있을때 (Background 상태) - onMessageReceived() 메소드 호출되며 알림이 자동으로 만들어지지 않습니다

    //3. FCM 알림 + 데이터 메세지 유형 수신
    // 1) 앱이 켜져있을때 (Foreground 상태) - onMessageReceived() 메소드 호출되며 알림이 자동으로 만들어지지 않습니다
    // 2) 앱이 꺼져있을때 (Background 상태) - onMessageReceived() 메소드 호출되지 않으며 default(기본) 알림이 공지됨. - 별도 아이콘 모양 설정이 없으면 둥근 흰색 아이콘이 보여짐짐

    // Firebase console 사이트에 push message 를 보내는 연습기 사이트가 존재
    // 단, 알림 메세지 유형으로만 보내짐.

    //FCM message 를 수신햇을때 자동 발동하는 콜백 메소드
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        // 메세지 수신했음을 확인
        Log.i("TAG","onMessage Received...");

        // 알림메세지 휴형으로 보낸 메세지의 Title, Text 받기
        RemoteMessage.Notification notification =message.getNotification();
        if (notification!=null) {
            String title= notification.getTitle();
            String text= notification.getBody();

            Log.i("TAG","알림메세지 : " + title + " - " + text);
        }

        // 데이터가 절달되었을 때.
        Map<String, String> data = message.getData();
        if(data.size()>0){
            String name= data.get("name");
            String age= data.get("age");
            String address=data.get("address");

            Log.i("TAG", "데이터 메세지 :" + name + ","+ age + "," + address);

            // 사용자에게는 메세지 수신을 알림으로 통지해야함.
            NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(this);
            NotificationChannelCompat channel = new NotificationChannelCompat.Builder("fcm_ch",NotificationManagerCompat.IMPORTANCE_HIGH).setName("EX89 FCM").build();
            notificationManagerCompat.createNotificationChannel(channel);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "fcm_ch");

            //알림 설정들
            builder.setSmallIcon(R.drawable.ic_fcm_noti);
            builder.setContentTitle(name);
            builder.setContentText(age+" - "+address);

            //알림 공지
            notificationManagerCompat.notify(20,builder.build());
        }

    }
}
