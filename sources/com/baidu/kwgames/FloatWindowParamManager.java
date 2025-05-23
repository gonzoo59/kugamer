package com.baidu.kwgames;

import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import com.blankj.utilcode.util.AppUtils;
import java.lang.reflect.Method;
import org.opencv.videoio.Videoio;
/* loaded from: classes.dex */
public class FloatWindowParamManager {
    public static final String TAG = "FloatWindowParamManager";

    public static boolean checkPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Settings.canDrawOverlays(context);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return checkOps(context);
        }
        return true;
    }

    public static boolean checkOverlayPermission(Context context) {
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
        if (appOpsManager != null) {
            try {
                return appOpsManager.checkOpNoThrow("android:system_alert_window", Process.myUid(), context.getPackageName()) == 0;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private static boolean checkOps(Context context) {
        Method method;
        try {
            Object systemService = context.getSystemService("appops");
            if (systemService == null || (method = systemService.getClass().getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class)) == null) {
                return false;
            }
            if (((Integer) method.invoke(systemService, 24, Integer.valueOf(Binder.getCallingUid()), AppUtils.getAppPackageName())).intValue() != 0) {
                if (RomUtils.isDomesticSpecialRom()) {
                    return false;
                }
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0086, code lost:
        if (r0.equals("LENOVO") == false) goto L5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean tryJumpToPermissionPage(android.content.Context r5) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.kwgames.FloatWindowParamManager.tryJumpToPermissionPage(android.content.Context):boolean");
    }

    private static boolean startActivitySafely(Intent intent, Context context) {
        try {
            if (isIntentAvailable(intent, context)) {
                intent.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
                context.startActivity(intent);
                return true;
            }
            return false;
        } catch (Exception unused) {
            Log.e(TAG, "启动Activity失败！！！！！！");
            return false;
        }
    }

    public static boolean isIntentAvailable(Intent intent, Context context) {
        return intent != null && context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }

    private static boolean applyCommonPermission(Context context) {
        try {
            Intent intent = new Intent(Settings.class.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION").get(null).toString());
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            startActivitySafely(intent, context);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private static boolean applyCoolpadPermission(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.yulong.android.seccenter", "com.yulong.android.seccenter.dataprotection.ui.AppListActivity");
        return startActivitySafely(intent, context);
    }

    private static boolean applyLenovoPermission(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.lenovo.safecenter", "com.lenovo.safecenter.MainTab.LeSafeMainActivity");
        return startActivitySafely(intent, context);
    }

    private static boolean applyZTEPermission(Context context) {
        Intent intent = new Intent();
        intent.setAction("com.zte.heartyservice.intent.action.startActivity.PERMISSION_SCANNER");
        return startActivitySafely(intent, context);
    }

    private static boolean applyLetvPermission(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AppActivity");
        return startActivitySafely(intent, context);
    }

    private static boolean applyVivoPermission(Context context) {
        Intent intent = new Intent();
        intent.putExtra("packagename", context.getPackageName());
        intent.setAction("com.vivo.permissionmanager");
        intent.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            return startActivitySafely(intent, context);
        }
        intent.setAction("com.iqoo.secure");
        intent.setClassName("com.iqoo.secure", "com.iqoo.secure.safeguard.SoftPermissionDetailActivity");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            return startActivitySafely(intent, context);
        }
        intent.setAction("com.iqoo.secure");
        intent.setClassName("com.iqoo.secure", "com.iqoo.secure.MainActivity");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            return startActivitySafely(intent, context);
        }
        return startActivitySafely(intent, context);
    }

    private static boolean applyOppoPermission(Context context) {
        Intent intent = new Intent();
        intent.putExtra("packageName", context.getPackageName());
        intent.setAction("com.oppo.safe");
        intent.setClassName("com.oppo.safe", "com.oppo.safe.permission.PermissionAppListActivity");
        if (!startActivitySafely(intent, context)) {
            intent.setAction("com.color.safecenter");
            intent.setClassName("com.color.safecenter", "com.color.safecenter.permission.floatwindow.FloatWindowListActivity");
            if (!startActivitySafely(intent, context)) {
                intent.setAction("com.coloros.safecenter");
                intent.setClassName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity");
                return startActivitySafely(intent, context);
            }
        }
        return true;
    }

    private static boolean apply360Permission(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.Settings$OverlaySettingsActivity");
        if (startActivitySafely(intent, context)) {
            return true;
        }
        intent.setClassName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        return startActivitySafely(intent, context);
    }

    private static boolean applyMiuiPermission(Context context) {
        Intent intent = new Intent();
        intent.setAction("miui.intent.action.APP_PERM_EDITOR");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra("extra_pkgname", context.getPackageName());
        return startActivitySafely(intent, context);
    }

    public static boolean getAppDetailSettingIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return startActivitySafely(intent, context);
    }

    private static boolean applyMeizuPermission(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity");
        intent.putExtra("packageName", context.getPackageName());
        return startActivitySafely(intent, context);
    }

    private static boolean applyHuaweiPermission(Context context) {
        try {
            try {
                Intent intent = new Intent();
                intent.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity"));
                if (!startActivitySafely(intent, context)) {
                    intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity"));
                    context.startActivity(intent);
                }
                return true;
            } catch (Exception e) {
                Log.e(TAG, "Huawei跳转失败1" + e);
                return getAppDetailSettingIntent(context);
            }
        } catch (ActivityNotFoundException e2) {
            try {
                Intent intent2 = new Intent();
                intent2.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
                intent2.setComponent(new ComponentName("com.Android.settings", "com.android.settings.permission.TabItem"));
                context.startActivity(intent2);
                return true;
            } catch (Exception unused) {
                Log.e(TAG, "Huawei跳转失败2" + e2);
                return getAppDetailSettingIntent(context);
            }
        } catch (SecurityException unused2) {
            Intent intent3 = new Intent();
            intent3.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            intent3.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
            context.startActivity(intent3);
            return true;
        } catch (Exception unused3) {
            return getAppDetailSettingIntent(context);
        }
    }

    private static boolean applySmartisanPermission(Context context) {
        Intent intent = new Intent("com.smartisanos.security.action.SWITCHED_PERMISSIONS_NEW");
        intent.setClassName("com.smartisanos.security", "com.smartisanos.security.SwitchedPermissions");
        intent.putExtra("index", 17);
        if (startActivitySafely(intent, context)) {
            return true;
        }
        Intent intent2 = new Intent("com.smartisanos.security.action.SWITCHED_PERMISSIONS");
        intent2.setClassName("com.smartisanos.security", "com.smartisanos.security.SwitchedPermissions");
        intent2.putExtra("permission", new String[]{"android.permission.SYSTEM_ALERT_WINDOW"});
        return startActivitySafely(intent2, context);
    }

    public static WindowManager.LayoutParams getFloatLayoutParam(boolean z, boolean z2) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= 26) {
            layoutParams.type = 2038;
            if (Build.VERSION.SDK_INT >= 28) {
                layoutParams.layoutInDisplayCutoutMode = 1;
            }
        } else if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 23) {
            layoutParams.type = 2005;
        } else {
            layoutParams.type = 2002;
        }
        layoutParams.packageName = AppUtils.getAppPackageName();
        layoutParams.flags |= 16777216;
        if (z2) {
            layoutParams.flags |= 40;
        } else {
            layoutParams.flags |= 568;
        }
        if (z) {
            layoutParams.flags |= 66816;
            layoutParams.width = -1;
            layoutParams.height = -1;
        } else {
            layoutParams.flags |= 65792;
            layoutParams.width = -2;
            layoutParams.height = -2;
        }
        layoutParams.format = -2;
        return layoutParams;
    }
}
