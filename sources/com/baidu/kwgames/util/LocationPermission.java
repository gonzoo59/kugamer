package com.baidu.kwgames.util;

import android.app.Activity;
import androidx.core.app.ActivityCompat;
import com.polidea.rxandroidble2.RxBleClient;
/* loaded from: classes.dex */
public class LocationPermission {
    private static final int REQUEST_PERMISSION_BLE_SCAN = 9358;

    private LocationPermission() {
    }

    public static void requestLocationPermission(Activity activity, RxBleClient rxBleClient) {
        ActivityCompat.requestPermissions(activity, new String[]{rxBleClient.getRecommendedScanRuntimePermissions()[0]}, REQUEST_PERMISSION_BLE_SCAN);
    }

    public static boolean isRequestLocationPermissionGranted(int i, String[] strArr, int[] iArr, RxBleClient rxBleClient) {
        if (i != REQUEST_PERMISSION_BLE_SCAN) {
            return false;
        }
        String[] recommendedScanRuntimePermissions = rxBleClient.getRecommendedScanRuntimePermissions();
        for (int i2 = 0; i2 < strArr.length; i2++) {
            for (String str : recommendedScanRuntimePermissions) {
                if (strArr[i2].equals(str) && iArr[i2] == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
