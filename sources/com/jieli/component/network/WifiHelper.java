package com.jieli.component.network;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.baidu.kwgames.BackupActivity;
import com.jieli.component.Logcat;
import com.jieli.component.receivers.NetworkStateBroadReceiver;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
/* loaded from: classes2.dex */
public class WifiHelper {
    public static final int STATE_NETWORK_INFO_EMPTY = 61168;
    public static final int STATE_NETWORK_NOT_OPEN = 61172;
    public static final int STATE_NETWORK_TYPE_IS_MOBILE = 61169;
    public static final int STATE_WIFI_INFO_EMPTY = 61170;
    public static final int STATE_WIFI_IS_CONNECTED = 61173;
    public static final int STATE_WIFI_PWD_NOT_MATCH = 61171;
    private static String TAG = "WifiHelper";
    public static int WIFI_CONNECTED = 1;
    public static int WIFI_CONNECTING = 0;
    public static int WIFI_CONNECT_FAILED = 2;
    public static int WIFI_UNKNOWN_ERROR = -1;
    private static WifiHelper instance;
    private static String otherWifiSSID;
    private NetworkStateBroadReceiver mNetworkStateBroadReceiver;
    private WifiManager mWifiManager;
    private Set<OnWifiCallBack> wifiCallBackSet;
    private WifiManager.WifiLock wifiLock;

    /* loaded from: classes2.dex */
    public enum NetState {
        NET_NO,
        NET_2G,
        NET_3G,
        NET_4G,
        NET_WIFI,
        NET_UNKNOWN
    }

    /* loaded from: classes2.dex */
    public interface OnWifiCallBack {
        void onConnected(WifiInfo wifiInfo);

        void onState(int i);
    }

    /* loaded from: classes2.dex */
    public enum WifiCipherType {
        NONE,
        IEEE8021XEAP,
        WEP,
        WPA,
        WPA2,
        WPAWPA2
    }

    public static WifiHelper getInstance(Context context) {
        synchronized (WifiHelper.class) {
            if (instance == null) {
                instance = new WifiHelper(context.getApplicationContext());
            }
        }
        return instance;
    }

    private WifiHelper(Context context) {
        if (context != null) {
            this.mWifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            this.wifiCallBackSet = new HashSet();
            startScan();
        }
    }

    public void registerOnWifiCallback(OnWifiCallBack onWifiCallBack) {
        Set<OnWifiCallBack> set = this.wifiCallBackSet;
        if (set == null || onWifiCallBack == null) {
            return;
        }
        set.add(onWifiCallBack);
    }

    public void registerBroadCastReceiver(Context context) {
        if (this.mNetworkStateBroadReceiver == null) {
            this.mNetworkStateBroadReceiver = new NetworkStateBroadReceiver();
        }
        IntentFilter intentFilter = new IntentFilter("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.supplicant.STATE_CHANGE");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.getApplicationContext().registerReceiver(this.mNetworkStateBroadReceiver, intentFilter);
    }

    public void unregisterBroadcastReceiver(Context context) {
        if (this.mNetworkStateBroadReceiver != null) {
            context.getApplicationContext().unregisterReceiver(this.mNetworkStateBroadReceiver);
            this.mNetworkStateBroadReceiver = null;
        }
    }

    public void unregisterOnWifiCallback(OnWifiCallBack onWifiCallBack) {
        Set<OnWifiCallBack> set = this.wifiCallBackSet;
        if (set == null || onWifiCallBack == null) {
            return;
        }
        set.remove(onWifiCallBack);
    }

    public void clearAllOnWifiCallback() {
        Set<OnWifiCallBack> set = this.wifiCallBackSet;
        if (set != null) {
            set.clear();
        }
    }

    public void notifyWifiConnect(WifiInfo wifiInfo) {
        Set<OnWifiCallBack> set = this.wifiCallBackSet;
        if (set == null || wifiInfo == null) {
            return;
        }
        for (OnWifiCallBack onWifiCallBack : set) {
            onWifiCallBack.onConnected(wifiInfo);
        }
    }

    public void notifyWifiState(int i) {
        Set<OnWifiCallBack> set = this.wifiCallBackSet;
        if (set != null) {
            for (OnWifiCallBack onWifiCallBack : set) {
                onWifiCallBack.onState(i);
            }
        }
    }

    public boolean isWifiOpen() {
        WifiManager wifiManager = this.mWifiManager;
        return wifiManager != null && wifiManager.isWifiEnabled();
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
            return connectivityManager.getActiveNetworkInfo();
        }
        return null;
    }

    public static boolean isNetWorkConnectedType(Context context, int i) {
        ConnectivityManager connectivityManager;
        NetworkInfo[] allNetworkInfo;
        if (context != null && (connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")) != null && (allNetworkInfo = connectivityManager.getAllNetworkInfo()) != null) {
            for (NetworkInfo networkInfo : allNetworkInfo) {
                if (networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                    return networkInfo.getType() == i;
                }
            }
        }
        return false;
    }

    public boolean isOutSideWifi(String str) {
        WifiInfo wifiConnectionInfo = getWifiConnectionInfo();
        if (wifiConnectionInfo != null) {
            String formatSSID = formatSSID(wifiConnectionInfo.getSSID());
            return !TextUtils.isEmpty(formatSSID) && formatSSID.startsWith(str);
        }
        return false;
    }

    public int getWifiState(Context context) {
        if (context == null) {
            return WIFI_UNKNOWN_ERROR;
        }
        NetworkInfo networkInfo = getNetworkInfo(context);
        if (networkInfo == null) {
            return WIFI_UNKNOWN_ERROR;
        }
        if (networkInfo.getDetailedState() == NetworkInfo.DetailedState.OBTAINING_IPADDR || networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTING) {
            return WIFI_CONNECTING;
        }
        if (networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
            return WIFI_CONNECTED;
        }
        return WIFI_CONNECT_FAILED;
    }

    public static String interceptChar0Before(String str) {
        if (str == null) {
            return null;
        }
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            if (Character.valueOf(c).hashCode() == 0) {
                break;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public boolean isWifiClosed() {
        int wifiState = getWifiState();
        return wifiState == 1 || wifiState == 0;
    }

    public boolean isWifiOpened() {
        int wifiState = getWifiState();
        return wifiState == 3 || wifiState == 2;
    }

    public void openWifi() {
        if (this.mWifiManager == null || !isWifiClosed()) {
            return;
        }
        this.mWifiManager.setWifiEnabled(true);
    }

    public void closeWifi() {
        if (this.mWifiManager == null || !isWifiOpened()) {
            return;
        }
        this.mWifiManager.setWifiEnabled(false);
    }

    public int getWifiState() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            return wifiManager.getWifiState();
        }
        return 0;
    }

    public List<WifiConfiguration> getSavedWifiConfiguration() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            return wifiManager.getConfiguredNetworks();
        }
        return null;
    }

    public List<ScanResult> getWifiScanResult() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            return wifiManager.getScanResults();
        }
        return null;
    }

    public synchronized void startScan() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            wifiManager.startScan();
        }
    }

    public void connectionConfiguration(int i) {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            wifiManager.disconnect();
            this.mWifiManager.enableNetwork(i, true);
        }
    }

    public void disconnectionConfiguration(int i) {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            wifiManager.disableNetwork(i);
            this.mWifiManager.disconnect();
        }
    }

    private boolean configurationNetWorkIdCheck(int i) {
        List<WifiConfiguration> savedWifiConfiguration = getSavedWifiConfiguration();
        if (savedWifiConfiguration == null) {
            return false;
        }
        for (WifiConfiguration wifiConfiguration : savedWifiConfiguration) {
            if (wifiConfiguration != null && wifiConfiguration.networkId == i) {
                return true;
            }
        }
        return false;
    }

    public WifiInfo getWifiConnectionInfo() {
        return this.mWifiManager.getConnectionInfo();
    }

    public void createWifiLock(String str) {
        if (this.mWifiManager != null) {
            if (TextUtils.isEmpty(str)) {
                str = "wifiLock";
            }
            this.wifiLock = this.mWifiManager.createWifiLock(str);
        }
    }

    public void acquireWifiLock() {
        WifiManager.WifiLock wifiLock = this.wifiLock;
        if (wifiLock != null) {
            wifiLock.acquire();
            return;
        }
        createWifiLock("wifiLock");
        WifiManager.WifiLock wifiLock2 = this.wifiLock;
        if (wifiLock2 != null) {
            wifiLock2.acquire();
        }
    }

    public void releaseWifiLock() {
        WifiManager.WifiLock wifiLock = this.wifiLock;
        if (wifiLock == null || !wifiLock.isHeld()) {
            return;
        }
        this.wifiLock.acquire();
    }

    public int addNetWork(WifiConfiguration wifiConfiguration) {
        WifiManager wifiManager;
        if (wifiConfiguration == null || (wifiManager = this.mWifiManager) == null) {
            return -255;
        }
        return wifiManager.addNetwork(wifiConfiguration);
    }

    public void addNetWorkAndConnect(WifiConfiguration wifiConfiguration) {
        int addNetWork = addNetWork(wifiConfiguration);
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager == null || addNetWork == -255) {
            return;
        }
        wifiManager.disconnect();
        this.mWifiManager.enableNetwork(addNetWork, true);
    }

    public int getConnectedWifiLevel() {
        WifiInfo wifiConnectionInfo = getWifiConnectionInfo();
        if (wifiConnectionInfo != null) {
            String formatSSID = formatSSID(wifiConnectionInfo.getSSID());
            List<ScanResult> wifiScanResult = getWifiScanResult();
            if (wifiScanResult != null) {
                for (ScanResult scanResult : wifiScanResult) {
                    if (scanResult != null) {
                        String formatSSID2 = formatSSID(scanResult.SSID);
                        if (!TextUtils.isEmpty(formatSSID2) && formatSSID2.equals(formatSSID)) {
                            return scanResult.level;
                        }
                    }
                }
                return 1;
            }
            return 1;
        }
        return 1;
    }

    public boolean removeSavedNetWork(String str) {
        List<WifiConfiguration> savedWifiConfiguration;
        if (TextUtils.isEmpty(str) || this.mWifiManager == null || (savedWifiConfiguration = getSavedWifiConfiguration()) == null) {
            return false;
        }
        for (WifiConfiguration wifiConfiguration : savedWifiConfiguration) {
            if (wifiConfiguration != null) {
                String formatSSID = formatSSID(wifiConfiguration.SSID);
                str = formatSSID(str);
                if (!TextUtils.isEmpty(str) && str.equals(formatSSID)) {
                    return this.mWifiManager.removeNetwork(wifiConfiguration.networkId);
                }
            }
        }
        return false;
    }

    public void remoteNetWork(int i) {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            wifiManager.removeNetwork(i);
        }
    }

    public void addNetWorkAndConnect(String str, String str2, WifiCipherType wifiCipherType) {
        if (this.mWifiManager == null || wifiCipherType == null) {
            return;
        }
        WifiConfiguration isWifiConfigurationSaved = isWifiConfigurationSaved(str, wifiCipherType);
        if (isWifiConfigurationSaved != null) {
            connectionConfiguration(isWifiConfigurationSaved.networkId);
            return;
        }
        addNetWork(createWifiConfiguration(str, str2, wifiCipherType));
        List<WifiConfiguration> savedWifiConfiguration = getSavedWifiConfiguration();
        if (savedWifiConfiguration != null) {
            for (WifiConfiguration wifiConfiguration : savedWifiConfiguration) {
                if (wifiConfiguration != null) {
                    String formatSSID = formatSSID(wifiConfiguration.SSID);
                    str = formatSSID(str);
                    if (!TextUtils.isEmpty(formatSSID) && formatSSID.equals(str)) {
                        this.mWifiManager.disconnect();
                        this.mWifiManager.enableNetwork(wifiConfiguration.networkId, true);
                        return;
                    }
                }
            }
        }
    }

    private WifiConfiguration isWifiConfigurationSaved(String str, WifiCipherType wifiCipherType) {
        List<WifiConfiguration> savedWifiConfiguration = getSavedWifiConfiguration();
        if (savedWifiConfiguration == null) {
            return null;
        }
        String formatSSID = formatSSID(str);
        for (WifiConfiguration wifiConfiguration : savedWifiConfiguration) {
            if (wifiConfiguration != null) {
                String formatSSID2 = formatSSID(wifiConfiguration.SSID);
                if (!TextUtils.isEmpty(formatSSID2) && formatSSID2.equals(formatSSID)) {
                    String str2 = null;
                    for (int i = 0; i < wifiConfiguration.allowedKeyManagement.size(); i++) {
                        if (wifiConfiguration.allowedKeyManagement.get(i) && i < WifiConfiguration.KeyMgmt.strings.length) {
                            str2 = WifiConfiguration.KeyMgmt.strings[i];
                        }
                    }
                    Logcat.w(TAG, "isWifiConfigurationSaved  keyMgmt = " + str2 + " , wifiCipherType : " + wifiCipherType);
                    if ((wifiCipherType == WifiCipherType.WPA && "WPA_PSK".equals(str2)) || (wifiCipherType == WifiCipherType.NONE && "NONE".equals(str2))) {
                        Logcat.w(TAG, "isWifiConfigurationSaved return object, network id : " + wifiConfiguration.networkId);
                        return wifiConfiguration;
                    }
                }
            }
        }
        return null;
    }

    private WifiConfiguration createWifiConfiguration(String str, String str2, WifiCipherType wifiCipherType) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        wifiConfiguration.SSID = "\"" + str + "\"";
        int i = AnonymousClass1.$SwitchMap$com$jieli$component$network$WifiHelper$WifiCipherType[wifiCipherType.ordinal()];
        if (i == 1) {
            wifiConfiguration.allowedKeyManagement.set(0);
        } else if (i != 2) {
            if (i == 3) {
                wifiConfiguration.hiddenSSID = true;
                String[] strArr = wifiConfiguration.wepKeys;
                strArr[0] = "\"" + str2 + "\"";
                wifiConfiguration.wepTxKeyIndex = 0;
                wifiConfiguration.allowedKeyManagement.set(0);
                wifiConfiguration.allowedGroupCiphers.set(0);
                wifiConfiguration.allowedGroupCiphers.set(2);
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedGroupCiphers.set(1);
                wifiConfiguration.allowedAuthAlgorithms.set(1);
            } else if (i == 4) {
                wifiConfiguration.preSharedKey = "\"" + str2 + "\"";
                wifiConfiguration.hiddenSSID = true;
                wifiConfiguration.allowedAuthAlgorithms.set(0);
                wifiConfiguration.allowedGroupCiphers.set(2);
                wifiConfiguration.allowedKeyManagement.set(1);
                wifiConfiguration.allowedPairwiseCiphers.set(1);
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedPairwiseCiphers.set(2);
                wifiConfiguration.status = 2;
            } else if (i != 5) {
                return null;
            } else {
                wifiConfiguration.preSharedKey = "\"" + str2 + "\"";
                wifiConfiguration.allowedAuthAlgorithms.set(0);
                wifiConfiguration.allowedGroupCiphers.set(2);
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedKeyManagement.set(1);
                wifiConfiguration.allowedPairwiseCiphers.set(1);
                wifiConfiguration.allowedPairwiseCiphers.set(2);
                wifiConfiguration.allowedProtocols.set(1);
                wifiConfiguration.status = 2;
            }
        }
        return wifiConfiguration;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.jieli.component.network.WifiHelper$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$jieli$component$network$WifiHelper$WifiCipherType;

        static {
            int[] iArr = new int[WifiCipherType.values().length];
            $SwitchMap$com$jieli$component$network$WifiHelper$WifiCipherType = iArr;
            try {
                iArr[WifiCipherType.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$jieli$component$network$WifiHelper$WifiCipherType[WifiCipherType.IEEE8021XEAP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$jieli$component$network$WifiHelper$WifiCipherType[WifiCipherType.WEP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$jieli$component$network$WifiHelper$WifiCipherType[WifiCipherType.WPA.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$jieli$component$network$WifiHelper$WifiCipherType[WifiCipherType.WPA2.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static int getNetWorkType(Context context) {
        if (isNetWorkConnectedType(context, 1)) {
            String typeName = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo().getTypeName();
            if (typeName.equalsIgnoreCase("WIFI")) {
                return 1;
            }
            if (typeName.equalsIgnoreCase("MOBILE")) {
                switch (((TelephonyManager) context.getSystemService(BackupActivity.TAG_PHONE_MODEL)).getNetworkType()) {
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                        return 1;
                    case 4:
                    case 7:
                    case 11:
                    default:
                        return 0;
                }
            }
            return 0;
        }
        return 0;
    }

    public static NetState getConnectedType(Context context) {
        NetState netState = NetState.NET_NO;
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting()) {
            return netState;
        }
        int type = activeNetworkInfo.getType();
        if (type != 0) {
            if (type == 1) {
                return NetState.NET_WIFI;
            }
            return NetState.NET_UNKNOWN;
        }
        switch (activeNetworkInfo.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return netState;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return NetState.NET_3G;
            case 13:
                return NetState.NET_4G;
            default:
                if (activeNetworkInfo.getSubtypeName().equalsIgnoreCase("TD-SCDMA") || activeNetworkInfo.getSubtypeName().equalsIgnoreCase("WCDMA") || activeNetworkInfo.getSubtypeName().equalsIgnoreCase("CDMA2000")) {
                    return NetState.NET_3G;
                }
                return NetState.NET_UNKNOWN;
        }
    }

    public static String getWifiIP(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        if (wifiManager == null) {
            return null;
        }
        String formatIpAddress = formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        if (TextUtils.isEmpty(formatIpAddress)) {
            return null;
        }
        if (formatIpAddress.equals("0.0.0.0")) {
            formatIpAddress = getLocalIpAddress();
            if (formatIpAddress.equals("0.0.0.0")) {
                Logcat.e(TAG, "WIFI IP Error");
            }
        }
        return formatIpAddress;
    }

    private static String getLocalIpAddress() {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                for (InetAddress inetAddress : Collections.list(networkInterface.getInetAddresses())) {
                    String hostAddress = inetAddress.getHostAddress();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return hostAddress;
                    }
                }
            }
            return "0.0.0.0";
        } catch (SocketException e) {
            e.printStackTrace();
            return "0.0.0.0";
        }
    }

    private static String formatIpAddress(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }

    public List<ScanResult> getSpecifiedSSIDList(String str) {
        ArrayList arrayList = new ArrayList();
        startScan();
        List<ScanResult> wifiScanResult = getWifiScanResult();
        if (wifiScanResult == null) {
            Logcat.e(TAG, "scanResultList is null");
            return null;
        }
        for (ScanResult scanResult : wifiScanResult) {
            String formatSSID = formatSSID(scanResult.SSID);
            if (!TextUtils.isEmpty(formatSSID) && formatSSID.startsWith(str)) {
                arrayList.add(scanResult);
            }
        }
        return arrayList;
    }

    public void connectOtherWifi(String str) {
        startScan();
        otherWifiSSID = null;
        List<WifiConfiguration> savedWifiConfiguration = getSavedWifiConfiguration();
        List<ScanResult> wifiScanResult = getWifiScanResult();
        if (wifiScanResult == null || savedWifiConfiguration == null) {
            Logcat.e(TAG, "scanResultList or saveWifiList is null");
            return;
        }
        boolean z = false;
        for (ScanResult scanResult : wifiScanResult) {
            String formatSSID = formatSSID(scanResult.SSID);
            if (!TextUtils.isEmpty(formatSSID) && !formatSSID.startsWith(str)) {
                Iterator<WifiConfiguration> it = savedWifiConfiguration.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    WifiConfiguration next = it.next();
                    String formatSSID2 = formatSSID(next.SSID);
                    if (!TextUtils.isEmpty(formatSSID2) && formatSSID.equals(formatSSID2)) {
                        String str2 = TAG;
                        Logcat.e(str2, "Save networkName-> " + formatSSID + " network_id -> " + next.networkId + " networkName : " + formatSSID2);
                        WifiManager wifiManager = this.mWifiManager;
                        if (wifiManager != null) {
                            wifiManager.disconnect();
                            z = this.mWifiManager.enableNetwork(next.networkId, true);
                        }
                        otherWifiSSID = next.SSID;
                    }
                }
                if (z) {
                    return;
                }
            }
        }
    }

    public String getOtherWifiSSID() {
        return otherWifiSSID;
    }

    public static String formatSSID(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.contains("\"")) {
            str = str.replace("\"", "");
        }
        return str.contains(" ") ? str.replace(" ", "") : str;
    }

    public void release() {
        instance = null;
        otherWifiSSID = null;
        releaseWifiLock();
        this.mWifiManager = null;
    }
}
