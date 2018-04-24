package org.diiage.martin.broadcastintentsmartin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver{
    private static final String TAG = "MyBroadcastReceiver";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String BATTERY_CHANGED = "android.intent.action.BATTERY_CHANGED";

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction() == BATTERY_CHANGED){
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = (level / (float)scale) * 100;

            if(batteryPct == 20){
                String log = String.valueOf(batteryPct * 100);
                Log.d(TAG, log);
                Toast.makeText(context, log, Toast.LENGTH_LONG).show();
            }
        }
        if(intent.getAction() == SMS_RECEIVED){
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[])bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                }
                if (messages.length > -1) {
                    String log = messages[0].getMessageBody();
                    Toast.makeText(context, log, Toast.LENGTH_LONG).show();
                }
            }
        }



    }
}
