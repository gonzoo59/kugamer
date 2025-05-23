package com.jieli.component.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.jieli.component.Logcat;
import com.jieli.component.network.WifiHelper;
/* loaded from: classes2.dex */
public class NetworkStateBroadReceiver extends BroadcastReceiver {
    private WifiHelper mWifiHelper;
    private String tag = getClass().getSimpleName();

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        char c;
        if (this.mWifiHelper == null) {
            this.mWifiHelper = WifiHelper.getInstance(context.getApplicationContext());
        }
        if (context == null || intent == null) {
            return;
        }
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }
        action.hashCode();
        switch (action.hashCode()) {
            case -1172645946:
                if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -343630553:
                if (action.equals("android.net.wifi.STATE_CHANGE")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 233521600:
                if (action.equals("android.net.wifi.supplicant.STATE_CHANGE")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        int i = WifiHelper.STATE_NETWORK_TYPE_IS_MOBILE;
        switch (c) {
            case 0:
                if (this.mWifiHelper.isWifiOpen()) {
                    return;
                }
                if (!WifiHelper.isNetWorkConnectedType(context.getApplicationContext(), 0)) {
                    i = WifiHelper.STATE_NETWORK_NOT_OPEN;
                }
                this.mWifiHelper.notifyWifiState(i);
                return;
            case 1:
                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
                if (networkInfo == null) {
                    Logcat.e(this.tag, "networkInfo is null");
                    this.mWifiHelper.notifyWifiState(WifiHelper.STATE_NETWORK_INFO_EMPTY);
                    return;
                }
                String str = this.tag;
                Logcat.w(str, "networkInfo : " + networkInfo.toString());
                if (networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                    if (networkInfo.getType() != 1) {
                        Logcat.e(this.tag, "networkType is not TYPE_WIFI");
                        this.mWifiHelper.notifyWifiState(WifiHelper.STATE_NETWORK_TYPE_IS_MOBILE);
                        return;
                    } else if (connectionInfo == null || TextUtils.isEmpty(connectionInfo.getSSID())) {
                        String str2 = this.tag;
                        StringBuilder sb = new StringBuilder();
                        sb.append("wifiInfo is  empty or ssid is ");
                        sb.append(connectionInfo == null ? "null" : connectionInfo.toString());
                        Logcat.e(str2, sb.toString());
                        this.mWifiHelper.notifyWifiState(WifiHelper.STATE_WIFI_INFO_EMPTY);
                        return;
                    } else {
                        this.mWifiHelper.notifyWifiConnect(connectionInfo);
                        return;
                    }
                }
                return;
            case 2:
                SupplicantState supplicantState = (SupplicantState) intent.getParcelableExtra("newState");
                int intExtra = intent.getIntExtra("supplicantError", -1);
                String str3 = this.tag;
                Logcat.i(str3, "supplicantError=" + intExtra + ", state=" + supplicantState);
                if (SupplicantState.DISCONNECTED.equals(supplicantState) && intExtra == 1) {
                    this.mWifiHelper.notifyWifiState(WifiHelper.STATE_WIFI_PWD_NOT_MATCH);
                    return;
                }
                return;
            default:
                return;
        }
    }
}
