package com.jieli.component.utils;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.baidu.kwgames.util.GunPartsMgr;
import com.jieli.component.Logcat;
import com.jieli.component.bean.AppInfo;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/* loaded from: classes2.dex */
public class SystemUtil {
    private static final String TAG = "SystemUtil";

    public static <T> T checkNotNull(T t) {
        Objects.requireNonNull(t);
        return t;
    }

    public static <T> T checkNotNull(T t, String str) {
        Objects.requireNonNull(t, str);
        return t;
    }

    public static void checkAllNotNull(Object... objArr) {
        for (Object obj : objArr) {
            Objects.requireNonNull(obj);
        }
    }

    public static boolean isTextEmpty(CharSequence charSequence) {
        return charSequence != null && isTextEmpty(charSequence.toString());
    }

    public static boolean isTextEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static void restartApp(Context context) {
        ((AlarmManager) context.getApplicationContext().getSystemService("alarm")).set(1, System.currentTimeMillis() + 1000, PendingIntent.getActivity(context.getApplicationContext(), 0, context.getApplicationContext().getPackageManager().getLaunchIntentForPackage(context.getApplicationContext().getPackageName()), 1073741824));
    }

    public static void setImmersiveStateBar(Window window, boolean z) {
        if (window == null || Build.VERSION.SDK_INT < 19) {
            return;
        }
        window.addFlags(GunPartsMgr.GUN_TAIL_UZI_MASK);
        View decorView = window.getDecorView();
        if (decorView != null) {
            decorView.setSystemUiVisibility(1280);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.clearFlags(GunPartsMgr.GUN_TAIL_UZI_MASK);
            window.setStatusBarColor(0);
        }
        if (z) {
            String str = Build.BRAND;
            if (!TextUtils.isEmpty(str)) {
                if (str.contains("Meizu") || str.contains("meizu")) {
                    if (FlymeSetStatusBarLightMode(window, true)) {
                        return;
                    }
                } else if ((str.contains("Xiaomi") || str.contains("xiaomi")) && MIUISetStatusBarLightMode(window, true)) {
                    return;
                }
            }
            if (Build.VERSION.SDK_INT >= 23) {
                window.getDecorView().setSystemUiVisibility(9216);
            }
        }
    }

    private static boolean FlymeSetStatusBarLightMode(Window window, boolean z) {
        if (window != null) {
            try {
                WindowManager.LayoutParams attributes = window.getAttributes();
                Field declaredField = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field declaredField2 = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                declaredField.setAccessible(true);
                declaredField2.setAccessible(true);
                int i = declaredField.getInt(null);
                int i2 = declaredField2.getInt(attributes);
                declaredField2.setInt(attributes, z ? i2 | i : (~i) & i2);
                window.setAttributes(attributes);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static boolean MIUISetStatusBarLightMode(Window window, boolean z) {
        boolean z2 = true;
        if (window != null) {
            Class<?> cls = window.getClass();
            try {
                Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                int i = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
                Method method = cls.getMethod("setExtraFlags", Integer.TYPE, Integer.TYPE);
                if (z) {
                    method.invoke(window, Integer.valueOf(i), Integer.valueOf(i));
                } else {
                    method.invoke(window, 0, Integer.valueOf(i));
                }
            } catch (Exception e) {
                e = e;
                z2 = false;
            }
            try {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (z) {
                        window.getDecorView().setSystemUiVisibility(9216);
                    } else {
                        window.getDecorView().setSystemUiVisibility(0);
                    }
                }
                return true;
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                return z2;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getPackageByName(android.content.Context r5, java.lang.String r6) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            java.lang.String r1 = ""
            if (r0 == 0) goto L9
            return r1
        L9:
            r6.toLowerCase()
            android.content.pm.PackageManager r0 = r5.getPackageManager()
            r2 = 0
            java.util.List r0 = r0.getInstalledPackages(r2)
            java.util.Iterator r0 = r0.iterator()
        L19:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L45
            java.lang.Object r2 = r0.next()
            android.content.pm.PackageInfo r2 = (android.content.pm.PackageInfo) r2
            android.content.pm.PackageManager r3 = r5.getPackageManager()
            android.content.pm.ApplicationInfo r4 = r2.applicationInfo
            java.lang.CharSequence r3 = r3.getApplicationLabel(r4)
            java.lang.String r3 = r3.toString()
            boolean r4 = r3.equalsIgnoreCase(r6)
            if (r4 != 0) goto L43
            java.lang.String r3 = r3.toLowerCase()
            boolean r3 = r3.contains(r6)
            if (r3 == 0) goto L19
        L43:
            java.lang.String r1 = r2.packageName
        L45:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.component.utils.SystemUtil.getPackageByName(android.content.Context, java.lang.String):java.lang.String");
    }

    public static byte[] getFromRaw(Context context, int i) {
        byte[] bArr;
        InputStream openRawResource;
        InputStream inputStream = null;
        r0 = null;
        byte[] bArr2 = null;
        inputStream = null;
        try {
            try {
                openRawResource = context.getResources().openRawResource(i);
            } catch (Exception e) {
                e = e;
                bArr = null;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArr3 = new byte[1048576];
            byte[] bArr4 = new byte[1024];
            int i2 = 0;
            while (true) {
                int read = openRawResource.read(bArr4, 0, 1024);
                if (read < 0) {
                    break;
                }
                System.arraycopy(bArr4, 0, bArr3, i2, 1024);
                i2 += read;
            }
            if (i2 > 0) {
                bArr2 = new byte[i2];
                System.arraycopy(bArr3, 0, bArr2, 0, i2);
            }
            if (openRawResource != null) {
                try {
                    openRawResource.close();
                    return bArr2;
                } catch (IOException e2) {
                    e2.printStackTrace();
                    return bArr2;
                }
            }
            return bArr2;
        } catch (Exception e3) {
            e = e3;
            byte[] bArr5 = bArr2;
            inputStream = openRawResource;
            bArr = bArr5;
            e.printStackTrace();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            return bArr;
        } catch (Throwable th2) {
            th = th2;
            inputStream = openRawResource;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean isAppInForeground(Context context) {
        ComponentName componentName;
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (activityManager == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT > 20) {
            boolean z = false;
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                if (runningAppProcessInfo.importance == 100) {
                    for (String str : runningAppProcessInfo.pkgList) {
                        if (str.equals(context.getPackageName())) {
                            z = true;
                        }
                    }
                }
            }
            return z;
        }
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
        return (runningTasks == null || runningTasks.isEmpty() || (componentName = runningTasks.get(0).topActivity) == null || !componentName.getPackageName().equals(context.getPackageName())) ? false : true;
    }

    public static void moveAppToFront(Context context) {
        List<ActivityManager.RunningTaskInfo> runningTasks;
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (activityManager == null || (runningTasks = activityManager.getRunningTasks(100)) == null) {
            return;
        }
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
            if (runningTaskInfo.topActivity != null && runningTaskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
                activityManager.moveTaskToFront(runningTaskInfo.id, 2);
                return;
            }
        }
    }

    public static int[] getDrawableResIdFromArray(Context context, int i) {
        TypedArray obtainTypedArray = context.getResources().obtainTypedArray(i);
        int length = obtainTypedArray.length();
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = obtainTypedArray.getResourceId(i2, 0);
        }
        obtainTypedArray.recycle();
        return iArr;
    }

    public static String getVersioName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "default_version_name";
        }
    }

    public static int getVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                return (int) packageManager.getPackageInfo(context.getPackageName(), 0).getLongVersionCode();
            }
            return packageManager.getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getAppName(Context context) {
        CharSequence applicationLabel = context.getPackageManager().getApplicationLabel(context.getApplicationInfo());
        if (TextUtils.isEmpty(applicationLabel)) {
            throw new RuntimeException("app名称读取失败");
        }
        return applicationLabel.toString();
    }

    public static boolean startAppByName(Context context, String str, boolean z, boolean z2) {
        List<PackageInfo> installedPackages;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        if (Build.VERSION.SDK_INT >= 24) {
            installedPackages = packageManager.getInstalledPackages(8192);
        } else {
            installedPackages = packageManager.getInstalledPackages(8192);
        }
        for (PackageInfo packageInfo : installedPackages) {
            String charSequence = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            if (str.equals(charSequence)) {
                return startAppByPackage(context, packageInfo.packageName);
            }
            if (z && str.equalsIgnoreCase(charSequence)) {
                return startAppByPackage(context, packageInfo.packageName);
            }
            if (z2 && charSequence.contains(str)) {
                return startAppByPackage(context, packageInfo.packageName);
            }
            if (z && z2 && charSequence.toUpperCase().contains(str.toUpperCase())) {
                return startAppByPackage(context, packageInfo.packageName);
            }
            Logcat.i(TAG, charSequence + ":" + packageInfo.packageName);
        }
        return false;
    }

    public static boolean startAppByPackage(Context context, String str) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str);
        if (launchIntentForPackage != null) {
            context.startActivity(launchIntentForPackage);
            return true;
        }
        return false;
    }

    @Deprecated
    public static boolean closeAppByName(Context context, String str, boolean z, boolean z2) {
        List<PackageInfo> installedPackages;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        if (Build.VERSION.SDK_INT >= 24) {
            installedPackages = packageManager.getInstalledPackages(8192);
        } else {
            installedPackages = packageManager.getInstalledPackages(8192);
        }
        for (PackageInfo packageInfo : installedPackages) {
            String charSequence = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            if (str.equals(charSequence)) {
                return closeAppByPackage(context, packageInfo.packageName);
            }
            if (z && str.equalsIgnoreCase(charSequence)) {
                return closeAppByPackage(context, packageInfo.packageName);
            }
            if (z2 && charSequence.contains(str)) {
                return closeAppByPackage(context, packageInfo.packageName);
            }
            if (z && z2 && charSequence.toUpperCase().contains(str.toUpperCase())) {
                return closeAppByPackage(context, packageInfo.packageName);
            }
        }
        return false;
    }

    @Deprecated
    public static boolean closeAppByPackage(Context context, String str) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        Logcat.i(TAG, "----------closeAppByPackage:-----------" + str);
        try {
            Method declaredMethod = activityManager.getClass().getDeclaredMethod("forceStopPackage", String.class);
            if (declaredMethod.isAccessible()) {
                declaredMethod.invoke(activityManager, str);
            } else {
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(activityManager, str);
                declaredMethod.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0050, code lost:
        if (r0 == null) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0053, code lost:
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String readContactPhoneByName(android.content.Context r8, java.lang.String r9) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            java.lang.String r1 = ""
            if (r0 == 0) goto L9
            return r1
        L9:
            r0 = 0
            android.content.ContentResolver r2 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            android.net.Uri r3 = android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r0 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            if (r0 == 0) goto L44
        L1a:
            boolean r8 = r0.moveToNext()     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            if (r8 == 0) goto L44
            java.lang.String r8 = "display_name"
            int r8 = r0.getColumnIndex(r8)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            java.lang.String r8 = r0.getString(r8)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            java.lang.String r8 = r8.toUpperCase()     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            boolean r8 = r8.contains(r9)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            if (r8 == 0) goto L1a
            java.lang.String r8 = "data1"
            int r8 = r0.getColumnIndex(r8)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            java.lang.String r1 = r0.getString(r8)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            boolean r8 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c
            if (r8 != 0) goto L1a
        L44:
            if (r0 == 0) goto L53
        L46:
            r0.close()
            goto L53
        L4a:
            r8 = move-exception
            goto L54
        L4c:
            r8 = move-exception
            r8.printStackTrace()     // Catch: java.lang.Throwable -> L4a
            if (r0 == 0) goto L53
            goto L46
        L53:
            return r1
        L54:
            if (r0 == 0) goto L59
            r0.close()
        L59:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.component.utils.SystemUtil.readContactPhoneByName(android.content.Context, java.lang.String):java.lang.String");
    }

    public static List<AppInfo> getInstallApp(Context context, boolean z) {
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        for (int i = 0; i < installedPackages.size(); i++) {
            PackageInfo packageInfo = installedPackages.get(i);
            AppInfo versionCode = new AppInfo().setName(packageInfo.applicationInfo.loadLabel(packageManager).toString()).setPackageName(packageInfo.applicationInfo.packageName).setVersionName(packageInfo.versionName).setVersionCode(packageInfo.versionCode);
            boolean z2 = true;
            if ((packageInfo.applicationInfo.flags & 1) <= 0 && (packageInfo.applicationInfo.flags & 128) <= 0) {
                z2 = false;
            }
            versionCode.setSystem(z2);
            Drawable loadLogo = packageInfo.applicationInfo.loadLogo(packageManager);
            if (loadLogo == null) {
                loadLogo = packageInfo.applicationInfo.loadIcon(packageManager);
            }
            versionCode.setLogo(loadLogo);
            if (!z2 || z) {
                arrayList.add(versionCode);
            }
        }
        return arrayList;
    }

    public static float[] getScreenResolution(Context context) {
        if (context == null) {
            return null;
        }
        float[] fArr = new float[3];
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            return new float[]{0.0f, 0.0f, 0.0f};
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        fArr[0] = displayMetrics.widthPixels;
        fArr[1] = displayMetrics.heightPixels;
        fArr[2] = displayMetrics.density;
        return fArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getIMEI(android.content.Context r2) {
        /*
            java.lang.String r0 = "phone"
            java.lang.Object r0 = r2.getSystemService(r0)
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0
            if (r0 == 0) goto L17
            java.lang.String r1 = "android.permission.READ_PHONE_STATE"
            int r1 = androidx.core.app.ActivityCompat.checkSelfPermission(r2, r1)
            if (r1 != 0) goto L17
            java.lang.String r0 = r0.getDeviceId()     // Catch: java.lang.Exception -> L17
            goto L18
        L17:
            r0 = 0
        L18:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L28
            android.content.ContentResolver r2 = r2.getContentResolver()
            java.lang.String r0 = "android_id"
            java.lang.String r0 = android.provider.Settings.System.getString(r2, r0)
        L28:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r1 = ">>>getIMEI : "
            r2.append(r1)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            java.lang.String r1 = "SystemUtil"
            com.jieli.component.Logcat.w(r1, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.component.utils.SystemUtil.getIMEI(android.content.Context):java.lang.String");
    }

    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getSystemModel() {
        return Build.MODEL;
    }

    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    public static boolean isCanDrawOverlays(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }
}
