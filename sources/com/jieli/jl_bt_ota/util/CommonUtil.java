package com.jieli.jl_bt_ota.util;

import android.content.Context;
import android.os.Handler;
import java.util.Calendar;
import java.util.Objects;
/* loaded from: classes2.dex */
public class CommonUtil {
    private static Context mainContext;
    private static Handler mainHandler;

    public static Context getMainContext() {
        return mainContext;
    }

    public static void setMainContext(Context context) {
        mainContext = (Context) checkNotNull(context);
        CrashHandler.getInstance().init(mainContext);
        mainHandler = new Handler(mainContext.getMainLooper());
    }

    public static Handler getMainHandler() {
        return mainHandler;
    }

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

    public static long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    protected void finalize() throws Throwable {
        releaseMainContext();
        super.finalize();
    }

    private static void releaseMainContext() {
        Handler handler = mainHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            mainHandler = null;
        }
        if (mainContext != null) {
            mainContext = null;
            System.gc();
        }
    }
}
