package com.jieli.otasdk.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import androidx.core.app.ActivityCompat;
import java.util.Date;
/* loaded from: classes2.dex */
public class AppUtil {
    private static final long DOUBLE_CLICK_INTERVAL = 2000;
    private static long lastClickTime;

    public static boolean isHasLocationPermission(Context context) {
        return isHasPermission(context, "android.permission.ACCESS_COARSE_LOCATION");
    }

    public static boolean isHasStoragePermission(Context context) {
        return isHasPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") && isHasPermission(context, "android.permission.READ_EXTERNAL_STORAGE");
    }

    public static boolean isHasPermission(Context context, String str) {
        return context != null && ActivityCompat.checkSelfPermission(context, str) == 0;
    }

    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(DOUBLE_CLICK_INTERVAL);
    }

    public static boolean isFastDoubleClick(long j) {
        long time = new Date().getTime();
        boolean z = time - lastClickTime <= j;
        lastClickTime = time;
        return z;
    }

    public static boolean enableBluetooth() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            return false;
        }
        boolean isEnabled = defaultAdapter.isEnabled();
        return !isEnabled ? defaultAdapter.enable() : isEnabled;
    }
}
