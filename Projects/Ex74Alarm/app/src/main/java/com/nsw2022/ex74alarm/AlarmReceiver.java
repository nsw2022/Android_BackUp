package com.nsw2022.ex74alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

//4대 컴포넌트는 반드는 AndroidManifest.xml 에 등록되어야 작동함
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "알람 수신", Toast.LENGTH_SHORT).show();
    }
}
