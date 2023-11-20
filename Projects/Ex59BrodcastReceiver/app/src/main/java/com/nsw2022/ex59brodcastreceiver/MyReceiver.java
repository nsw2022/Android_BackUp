package com.nsw2022.ex59brodcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

// 반드시 안드로이드 Manifest.xml안에 리시버를 등록해야만 동작함
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "received", Toast.LENGTH_SHORT).show();

        // 방송 액션값 얻어오기
        String action = intent.getAction();
        if (action.equals("aaa")){
            Toast.makeText(context, "aaa", Toast.LENGTH_SHORT).show();
        }else if (action.equals("bbb")){
            Toast.makeText(context, "bbb", Toast.LENGTH_SHORT).show();
        }

    }
}
