package com.nsw2022.ex60broadcastreceiverbooting;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

// 리서버는 4대 컴포넌트여서 반드시 AndroidManifest.xml에 등록해야만 동작함.
public class MyBootingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // 수신한 방송 액션값 얻어오기
        String action= intent.getAction();

        // N버전(api 25)부터는 앱을 설치한 후 최소 한번은 사용자가 앱 런처(목록)화면에서 직접 아이콘을
        // 눌러서 실행한 앱만이 부팅방송을 들을 수 있음.
        if( action.equals( Intent.ACTION_BOOT_COMPLETED ) ){
            Toast.makeText(context, "boot received", Toast.LENGTH_SHORT).show();

            //부팅이 완료되었을때 내 앱(MainActivity)가 실행되도록 할 수 있음.
//            Intent i= new Intent(context, MainActivity.class);
//            context.startActivity(i);

            // android 10버전(api 29)부터는 리시버에서 곧바로 액티비티를 실행하는 것을 금지함.
            // 대신 Notification(알림)을 보여주고 알림창으로 통해 특정 액티비티가 실행되도록 권고함.
            if(Build.VERSION.SDK_INT>=29){

                NotificationManager notificationManager= (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

                //알림채널 객체 생성
                NotificationChannel channel= new NotificationChannel("ch1","부팅완료 리시버 앱", NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel( channel );

                //알림객체를 만들어주는 건축가 객체 생성
                NotificationCompat.Builder builder= new NotificationCompat.Builder(context, "ch1");

                //건축가에게 원하는 알림 설정
                builder.setSmallIcon(R.drawable.ic_noti); //상태표시줄의 아이콘

                //알림창의 설정들
                builder.setContentTitle("부팅이 완료되었습니다~~");
                builder.setContentText("이제 Ex60앱의 MainActivity 를 실행 할 수 있습니다.");

                builder.setAutoCancel(true); // 알림창을 클릭하면 자동 아이콘 없어짐.

                // 알림창 눌렀을때 실행할 MainActivity를 실행시켜주는 Intent객체 생성
                Intent i =new Intent(context,MainActivity.class);
                // 바로 실행하지 않기에 보류중인(Pending) Intent로 변환
                PendingIntent pendingIntent=PendingIntent.getActivity(context,100,i,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
                builder.setContentIntent(pendingIntent);

                Notification notification = builder.build();
                notificationManager.notify(1,notification);


            }else{
                Intent i= new Intent(context, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //기존 액티비티가 없는 상태에서 액티비티를 실행할때 필수 설정.
                context.startActivity(i);
            }

        }

    }
}
