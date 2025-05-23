package com.jieli.component.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
/* loaded from: classes2.dex */
public class SmsBroadcastReceiver extends BroadcastReceiver {
    private Context context;
    private String tag = getClass().getSimpleName();

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
    }

    public SmsBroadcastReceiver(Context context) {
        this.context = context;
        IntentFilter intentFilter = new IntentFilter();
        if (Build.VERSION.SDK_INT >= 19) {
            intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        }
        context.registerReceiver(this, intentFilter);
    }

    public void release() {
        this.context.unregisterReceiver(this);
        this.context = null;
    }
}
