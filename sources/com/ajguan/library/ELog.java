package com.ajguan.library;

import android.text.TextUtils;
import android.util.Log;
/* loaded from: classes.dex */
public final class ELog {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    private static boolean isEnabled = true;

    private ELog() {
    }

    public static void setEnable(boolean z) {
        isEnabled = z;
    }

    public static boolean isEnable() {
        return isEnabled;
    }

    private static void log(LEVEL level, String str, String str2, Throwable th) {
        if (isEnabled) {
            log2console(level, str, str2, th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.ajguan.library.ELog$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ajguan$library$ELog$LEVEL;

        static {
            int[] iArr = new int[LEVEL.values().length];
            $SwitchMap$com$ajguan$library$ELog$LEVEL = iArr;
            try {
                iArr[LEVEL.VERBOSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ajguan$library$ELog$LEVEL[LEVEL.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ajguan$library$ELog$LEVEL[LEVEL.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ajguan$library$ELog$LEVEL[LEVEL.WARN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$ajguan$library$ELog$LEVEL[LEVEL.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$ajguan$library$ELog$LEVEL[LEVEL.ASSERT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private static void log2console(LEVEL level, String str, String str2, Throwable th) {
        switch (AnonymousClass1.$SwitchMap$com$ajguan$library$ELog$LEVEL[level.ordinal()]) {
            case 1:
                if (th == null) {
                    Log.v(str, str2);
                    return;
                } else {
                    Log.v(str, str2, th);
                    return;
                }
            case 2:
                if (th == null) {
                    Log.d(str, str2);
                    return;
                } else {
                    Log.d(str, str2, th);
                    return;
                }
            case 3:
                if (th == null) {
                    Log.i(str, str2);
                    return;
                } else {
                    Log.i(str, str2, th);
                    return;
                }
            case 4:
                if (th == null) {
                    Log.w(str, str2);
                    return;
                } else if (TextUtils.isEmpty(str2)) {
                    Log.w(str, th);
                    return;
                } else {
                    Log.w(str, str2, th);
                    return;
                }
            case 5:
                if (th == null) {
                    Log.e(str, str2);
                    return;
                } else {
                    Log.e(str, str2, th);
                    return;
                }
            case 6:
                if (th == null) {
                    Log.wtf(str, str2);
                    return;
                } else if (TextUtils.isEmpty(str2)) {
                    Log.wtf(str, th);
                    return;
                } else {
                    Log.wtf(str, str2, th);
                    return;
                }
            default:
                return;
        }
    }

    public static void v(String str, String str2) {
        log(LEVEL.VERBOSE, str, str2, null);
    }

    public static void v(String str, String str2, Throwable th) {
        log(LEVEL.VERBOSE, str, str2, th);
    }

    public static void d(String str, String str2) {
        log(LEVEL.DEBUG, str, str2, null);
    }

    public static void d(String str, String str2, Throwable th) {
        log(LEVEL.DEBUG, str, str2, th);
    }

    public static void i(String str, String str2) {
        log(LEVEL.INFO, str, str2, null);
    }

    public static void i(String str, String str2, Throwable th) {
        log(LEVEL.INFO, str, str2, th);
    }

    public static void w(String str, String str2) {
        log(LEVEL.WARN, str, str2, null);
    }

    public static void w(String str, String str2, Throwable th) {
        log(LEVEL.WARN, str, str2, th);
    }

    public static void w(String str, Throwable th) {
        log(LEVEL.WARN, str, "", th);
    }

    public static void e(String str, String str2) {
        log(LEVEL.ERROR, str, str2, null);
    }

    public static void e(String str, String str2, Throwable th) {
        log(LEVEL.ERROR, str, str2, th);
    }

    /* loaded from: classes.dex */
    public enum LEVEL {
        VERBOSE(2, "V"),
        DEBUG(3, "D"),
        INFO(4, "I"),
        WARN(5, "W"),
        ERROR(6, "E"),
        ASSERT(7, "A");
        
        final int level;
        final String levelString;

        LEVEL() {
            throw new AssertionError();
        }

        LEVEL(int i, String str) {
            this.level = i;
            this.levelString = str;
        }

        public String getLevelString() {
            return this.levelString;
        }

        public int getLevel() {
            return this.level;
        }
    }
}
