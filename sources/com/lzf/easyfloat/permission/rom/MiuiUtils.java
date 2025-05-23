package com.lzf.easyfloat.permission.rom;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import org.opencv.videoio.Videoio;
/* loaded from: classes2.dex */
public class MiuiUtils {
    private static final String TAG = "MiuiUtils";

    public static int getMiuiVersion() {
        String systemProperty = RomUtils.getSystemProperty("ro.miui.ui.version.name");
        if (systemProperty != null) {
            try {
                return Integer.parseInt(systemProperty.substring(1));
            } catch (Exception e) {
                Log.e(TAG, "get miui version code error, version : " + systemProperty);
                Log.e(TAG, Log.getStackTraceString(e));
                return -1;
            }
        }
        return -1;
    }

    public static boolean checkFloatWindowPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return checkOp(context, 24);
        }
        return true;
    }

    private static boolean checkOp(Context context, int i) {
        if (Build.VERSION.SDK_INT >= 19) {
            try {
                return ((Integer) AppOpsManager.class.getDeclaredMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class).invoke((AppOpsManager) context.getSystemService("appops"), Integer.valueOf(i), Integer.valueOf(Binder.getCallingUid()), context.getPackageName())).intValue() == 0;
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        } else {
            Log.e(TAG, "Below API 19 cannot invoke!");
        }
        return false;
    }

    public static void applyMiuiPermission(Context context) {
        int miuiVersion = getMiuiVersion();
        if (miuiVersion == 5) {
            goToMiuiPermissionActivity_V5(context);
        } else if (miuiVersion == 6) {
            goToMiuiPermissionActivity_V6(context);
        } else if (miuiVersion == 7) {
            goToMiuiPermissionActivity_V7(context);
        } else if (miuiVersion >= 8) {
            goToMiuiPermissionActivity_V8(context);
        } else {
            Log.e(TAG, "this is a special MIUI rom version, its version code " + miuiVersion);
        }
    }

    private static boolean isIntentAvailable(Intent intent, Context context) {
        return intent != null && context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }

    public static void goToMiuiPermissionActivity_V5(Context context) {
        String packageName = context.getPackageName();
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", packageName, null));
        intent.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        } else {
            Log.e(TAG, "intent is not available!");
        }
    }

    public static void goToMiuiPermissionActivity_V6(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.putExtra("extra_pkgname", context.getPackageName());
        intent.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        } else {
            Log.e(TAG, "Intent is not available!");
        }
    }

    public static void goToMiuiPermissionActivity_V7(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.putExtra("extra_pkgname", context.getPackageName());
        intent.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
        } else {
            Log.e(TAG, "Intent is not available!");
        }
    }

    public static void goToMiuiPermissionActivity_V8(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        intent.putExtra("extra_pkgname", context.getPackageName());
        intent.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
        if (isIntentAvailable(intent, context)) {
            context.startActivity(intent);
            return;
        }
        Intent intent2 = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent2.setPackage("com.miui.securitycenter");
        intent2.putExtra("extra_pkgname", context.getPackageName());
        intent2.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
        if (isIntentAvailable(intent2, context)) {
            context.startActivity(intent2);
        } else {
            Log.e(TAG, "Intent is not available!");
        }
    }
}
