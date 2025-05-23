package com.lzf.easyfloat.permission.rom;

import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import org.opencv.videoio.Videoio;
/* loaded from: classes2.dex */
public class HuaweiUtils {
    private static final String TAG = "HuaweiUtils";

    public static boolean checkFloatWindowPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return checkOp(context, 24);
        }
        return true;
    }

    public static void applyPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity"));
            if (RomUtils.getEmuiVersion() == 3.1d) {
                context.startActivity(intent);
            } else {
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity"));
                context.startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            Intent intent2 = new Intent();
            intent2.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            intent2.setComponent(new ComponentName("com.Android.settings", "com.android.settings.permission.TabItem"));
            context.startActivity(intent2);
            e.printStackTrace();
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (SecurityException e2) {
            Intent intent3 = new Intent();
            intent3.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            intent3.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
            context.startActivity(intent3);
            Log.e(TAG, Log.getStackTraceString(e2));
        } catch (Exception e3) {
            Toast.makeText(context, "进入设置页面失败，请手动设置", 1).show();
            Log.e(TAG, Log.getStackTraceString(e3));
        }
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
}
