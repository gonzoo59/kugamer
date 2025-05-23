package com.baidu.kwgames;

import android.text.TextUtils;
import java.lang.reflect.Method;
/* loaded from: classes.dex */
public class SystemProperties {
    private static final Method getStringProperty = getMethod(getClass("android.os.SystemProperties"));

    private static String defaultString(String str, String str2) {
        return str == null ? str2 : str;
    }

    private static Class<?> getClass(String str) {
        try {
            try {
                Class<?> cls = Class.forName(str);
                if (cls != null) {
                    return cls;
                }
                throw new ClassNotFoundException();
            } catch (ClassNotFoundException unused) {
                return null;
            }
        } catch (ClassNotFoundException unused2) {
            return ClassLoader.getSystemClassLoader().loadClass(str);
        }
    }

    private static Method getMethod(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        try {
            return cls.getMethod("get", String.class);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String get(String str) {
        Method method = getStringProperty;
        if (method != null) {
            try {
                Object invoke = method.invoke(null, str);
                return invoke == null ? "" : trimToEmpty(invoke.toString());
            } catch (Exception unused) {
            }
        }
        return "";
    }

    public static String get(String str, String str2) {
        Method method = getStringProperty;
        if (method != null) {
            try {
                return defaultString(trimToNull((String) method.invoke(null, str)), str2);
            } catch (Exception unused) {
            }
        }
        return str2;
    }

    private static String trimToNull(String str) {
        String trim = trim(str);
        if (TextUtils.isEmpty(trim)) {
            return null;
        }
        return trim;
    }

    private static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }

    private static String trim(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }
}
