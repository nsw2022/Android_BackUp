package com.nsw2022.ex58notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(v->clickBtn());
    }

    void clickBtn(){

        // 운영체제로부터 알림(Notification)을 관리하는 관리자 객체 소환
        NotificationManager notificationManager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification 객체를 생성해주는 Builder 객체 생성
        NotificationCompat.Builder builder= null;

        // Oreo버전(API 26버전)이상에서는 알림시에 NotificationChannel 이라는 개념이 필수 구성요소가 됨.
        // 그래서 버전에 따라서 코드가 달라짐.
        if( Build.VERSION.SDK_INT > Build.VERSION_CODES.O ){ //디바이스의 버전이 26버전(Oreo버전) 이상이냐?

            //알림채널 객체 생성
            NotificationChannel channel= new NotificationChannel("ch1","My Channel", NotificationManager.IMPORTANCE_HIGH);
            //NotificationManager.IMPORTANCE_HIGH : 소리도 나고, 화면도 살짝 가리는 알림창이 보여짐
            //NotificationManager.IMPORTANCE_DEFAULT : 소리는 나지만, 화면도 살짝 가리는 알림창이 안보임
            //NotificationManager.IMPORTANCE_LOW : 소리도 안나고, 화면도 살짝 가리는 알림창도 안보임
            
            // 채널에 사운드 설정
            Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.s_select);
            channel.setSound(uri,new AudioAttributes.Builder().build());

            //알림매니저에게 위 알림채널객체를 시스템에서 인식하도록 생성
            notificationManager.createNotificationChannel(channel);

            builder= new NotificationCompat.Builder(this, "ch1");
        }else{
            builder= new NotificationCompat.Builder(this, "");

            //빌더에게 사운드 설정
            Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.s_select);
            builder.setSound(uri);
        }

        // 빌더에게 Notification의 원하는 설정작업 요청

        //1. 상태표시줄 보이는 아이콘
        builder.setSmallIcon(R.drawable.ic_stat_name);

        //2. 상태바를 드래그하여 아래로 내리면 보이는 알림창(확장 상태바)의 설정
        Bitmap bm= BitmapFactory.decodeResource(getResources(),R.drawable.sydney);
        builder.setLargeIcon(bm);
        builder.setContentTitle("TITLE");               //알림창 제목
        builder.setContentText("Message....");          //알림창 메세지
        builder.setSubText("sub text");

        // 알림창을 클릭했을때 실행될 작업( 새로운 화면[SecondActivity] 실행 ) 설정
        Intent intent = new Intent(this,SecondActivity.class);
        // 지금 당장 Intent가 화면을 실행하는 것이 아니기에 잠시 보류시켜야 함.
        // 보류중인 Intent로 만들어야 함.
        PendingIntent pendingIntent = PendingIntent.getActivity(this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        // 알림창을 클릭하여 새로운 화면으로 이동했을때 상태표시줄의 스몰아이콘을 없애기
        builder.setAutoCancel(true);

        // 요즘들어 종종보이는 알림창 스타일
        //1. BigPictureStyle
        NotificationCompat.BigPictureStyle picStyle = new NotificationCompat.BigPictureStyle();
        picStyle.bigPicture( BitmapFactory.decodeResource(getResources(),R.drawable.sydney)  );
        builder.setStyle(picStyle);

        //2. BigTextStyle : 여러줄의 메세지 출력 스타일
        //3. InBoxStyle : 여러메세지를 보여줄 때 적합한 스타일
        //4. MediaStyle : 음악 재생버튼, 일시정지버튼 등을 가진 스타일

        builder.addAction(R.drawable.ic_stat_name,"더보기",pendingIntent);
        builder.addAction(R.drawable.ic_stat_name,"설정",pendingIntent);



       //3. 알림객체 생성
        Notification notification = builder.build();

        // 매니저에게 알림(Notification) 을 요청
        notificationManager.notify(1, notification);

    }

}