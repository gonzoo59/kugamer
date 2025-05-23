package com.jieli.jl_bt_ota.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes2.dex */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static volatile CrashHandler INSTANCE = null;
    public static final String TAG = "CrashHandler";
    private Map<String, String> infos = new HashMap();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (CrashHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CrashHandler();
                }
            }
        }
        return INSTANCE;
    }

    public void init(Context context) {
        this.mContext = context;
        if (this.mDefaultHandler == null) {
            this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
        if (!handleException(th) && (uncaughtExceptionHandler = this.mDefaultHandler) != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
            return;
        }
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            String str = TAG;
            JL_Log.e(str, "InterruptedException error : " + getExceptionMsg(e));
        }
        this.mContext = null;
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.jieli.jl_bt_ota.util.CrashHandler$1] */
    private boolean handleException(Throwable th) {
        if (th == null) {
            return false;
        }
        new Thread() { // from class: com.jieli.jl_bt_ota.util.CrashHandler.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                Toast.makeText(CrashHandler.this.mContext, "很抱歉,程序出现异常,即将退出.", 1).show();
                Looper.loop();
            }
        }.start();
        collectDeviceInfo(this.mContext);
        saveCrashInfo2File(th);
        return true;
    }

    private void collectDeviceInfo(Context context) {
        Field[] declaredFields;
        PackageManager packageManager;
        try {
            packageManager = context.getPackageManager();
        } catch (PackageManager.NameNotFoundException e) {
            JL_Log.e(TAG, "an error occured when collect package info : " + getExceptionMsg(e));
        }
        if (packageManager == null) {
            return;
        }
        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 1);
        if (packageInfo != null) {
            this.infos.put("versionName", packageInfo.versionName == null ? "null" : packageInfo.versionName);
            this.infos.put("versionCode", packageInfo.versionCode + "");
        }
        for (Field field : Build.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.get(null) != null) {
                    this.infos.put(field.getName(), field.get(null).toString());
                    JL_Log.d(TAG, field.getName() + " : " + field.get(null));
                }
            } catch (Exception e2) {
                JL_Log.e(TAG, "an error occured when collect crash info : " + getExceptionMsg(e2));
            }
        }
    }

    private String saveCrashInfo2File(Throwable th) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : this.infos.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        sb.append(stringWriter.toString());
        JL_Log.e(TAG, sb.toString());
        return null;
    }

    private String getExceptionMsg(Exception exc) {
        if (exc == null) {
            return null;
        }
        return exc.toString();
    }
}
