package com.jieli.component;
/* loaded from: classes2.dex */
public class Logcat {
    public static final int DEBUG = 1;
    public static final int ERROR = 4;
    public static final int INFO = 2;
    public static final int VERBOSE = 0;
    public static final int WARRING = 3;
    private static boolean isLog = BuildConfig.DEBUG;
    private static int level = 4;
    private static AbsLogcat mAbsLogcat = new DefaultLogcat();

    /* loaded from: classes2.dex */
    interface AbsLogcat {
        void d(String str, String str2);

        void e(String str, String str2);

        void i(String str, String str2);

        void v(String str, String str2);

        void w(String str, String str2);
    }

    public static void setLogcatImpl(AbsLogcat absLogcat) {
        mAbsLogcat = absLogcat;
    }

    public static void setIsLog(boolean z) {
        isLog = z;
    }

    public static void setLevel(int i) {
        level = i;
    }

    public static void v(String str, String str2) {
        if (!isLog || level < 0) {
            return;
        }
        mAbsLogcat.v(str, str2);
    }

    public static void d(String str, String str2) {
        if (!isLog || level < 1) {
            return;
        }
        mAbsLogcat.d(str, str2);
    }

    public static void i(String str, String str2) {
        if (!isLog || level < 2) {
            return;
        }
        mAbsLogcat.i(str, str2);
    }

    public static void w(String str, String str2) {
        if (!isLog || level < 3) {
            return;
        }
        mAbsLogcat.w(str, str2);
    }

    public static void e(String str, String str2) {
        if (!isLog || level < 4) {
            return;
        }
        mAbsLogcat.e(str, str2);
    }
}
