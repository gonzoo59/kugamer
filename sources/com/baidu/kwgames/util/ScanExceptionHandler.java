package com.baidu.kwgames.util;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import com.baidu.kwgames.R;
import com.polidea.rxandroidble2.exceptions.BleScanException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/* loaded from: classes.dex */
public class ScanExceptionHandler {
    private static final Map<Integer, Integer> ERROR_MESSAGES;

    static {
        HashMap hashMap = new HashMap();
        ERROR_MESSAGES = hashMap;
        hashMap.put(2, Integer.valueOf((int) R.string.error_bluetooth_not_available));
        hashMap.put(1, Integer.valueOf((int) R.string.error_bluetooth_disabled));
        hashMap.put(3, Integer.valueOf((int) R.string.error_location_permission_missing));
        hashMap.put(4, Integer.valueOf((int) R.string.error_location_services_disabled));
        hashMap.put(5, Integer.valueOf((int) R.string.error_scan_failed_already_started));
        hashMap.put(6, Integer.valueOf((int) R.string.error_scan_failed_application_registration_failed));
        hashMap.put(8, Integer.valueOf((int) R.string.error_scan_failed_feature_unsupported));
        hashMap.put(7, Integer.valueOf((int) R.string.error_scan_failed_internal_error));
        hashMap.put(9, Integer.valueOf((int) R.string.error_scan_failed_out_of_hardware_resources));
        hashMap.put(0, Integer.valueOf((int) R.string.error_bluetooth_cannot_start));
        hashMap.put(Integer.MAX_VALUE, Integer.valueOf((int) R.string.error_unknown_error));
    }

    private ScanExceptionHandler() {
    }

    public static void handleException(Activity activity, BleScanException bleScanException) {
        String string;
        int reason = bleScanException.getReason();
        if (reason == 2147483646) {
            string = getUndocumentedScanThrottleErrorMessage(activity, bleScanException.getRetryDateSuggestion());
        } else {
            Integer num = ERROR_MESSAGES.get(Integer.valueOf(reason));
            if (num != null) {
                string = activity.getString(num.intValue());
            } else {
                Log.w("Scanning", String.format("No message found for reason=%d. Consider adding one.", Integer.valueOf(reason)));
                string = activity.getString(R.string.error_unknown_error);
            }
        }
        Log.w("Scanning", string, bleScanException);
        Toast.makeText(activity, string, 0).show();
    }

    private static String getUndocumentedScanThrottleErrorMessage(Activity activity, Date date) {
        StringBuilder sb = new StringBuilder(activity.getString(R.string.error_undocumented_scan_throttle));
        if (date != null) {
            sb.append(String.format(Locale.getDefault(), activity.getString(R.string.error_undocumented_scan_throttle_retry), Long.valueOf(secondsTill(date))));
        }
        return sb.toString();
    }

    private static long secondsTill(Date date) {
        return TimeUnit.MILLISECONDS.toSeconds(date.getTime() - System.currentTimeMillis());
    }
}
