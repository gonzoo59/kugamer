package permissions.dispatcher;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import androidx.collection.SimpleArrayMap;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.legacy.app.FragmentCompat;
/* loaded from: classes2.dex */
public final class PermissionUtils {
    private static final SimpleArrayMap<String, Integer> MIN_SDK_PERMISSIONS;

    static {
        SimpleArrayMap<String, Integer> simpleArrayMap = new SimpleArrayMap<>(8);
        MIN_SDK_PERMISSIONS = simpleArrayMap;
        simpleArrayMap.put("com.android.voicemail.permission.ADD_VOICEMAIL", 14);
        simpleArrayMap.put("android.permission.BODY_SENSORS", 20);
        simpleArrayMap.put("android.permission.READ_CALL_LOG", 16);
        simpleArrayMap.put("android.permission.READ_EXTERNAL_STORAGE", 16);
        simpleArrayMap.put("android.permission.USE_SIP", 9);
        simpleArrayMap.put("android.permission.WRITE_CALL_LOG", 16);
        simpleArrayMap.put("android.permission.SYSTEM_ALERT_WINDOW", 23);
        simpleArrayMap.put("android.permission.WRITE_SETTINGS", 23);
    }

    private PermissionUtils() {
    }

    public static boolean verifyPermissions(int... iArr) {
        if (iArr.length == 0) {
            return false;
        }
        for (int i : iArr) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean permissionExists(String str) {
        Integer num = MIN_SDK_PERMISSIONS.get(str);
        return num == null || Build.VERSION.SDK_INT >= num.intValue();
    }

    public static boolean hasSelfPermissions(Context context, String... strArr) {
        for (String str : strArr) {
            if (permissionExists(str) && !hasSelfPermission(context, str)) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasSelfPermission(Context context, String str) {
        try {
            return PermissionChecker.checkSelfPermission(context, str) == 0;
        } catch (RuntimeException unused) {
            return false;
        }
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String... strArr) {
        for (String str : strArr) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean shouldShowRequestPermissionRationale(Fragment fragment, String... strArr) {
        for (String str : strArr) {
            if (fragment.shouldShowRequestPermissionRationale(str)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static boolean shouldShowRequestPermissionRationale(android.app.Fragment fragment, String... strArr) {
        for (String str : strArr) {
            if (FragmentCompat.shouldShowRequestPermissionRationale(fragment, str)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static void requestPermissions(android.app.Fragment fragment, String[] strArr, int i) {
        FragmentCompat.requestPermissions(fragment, strArr, i);
    }
}
