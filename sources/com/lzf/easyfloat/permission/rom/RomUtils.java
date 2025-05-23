package com.lzf.easyfloat.permission.rom;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
/* compiled from: RomUtils.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\u0006J\u0006\u0010\b\u001a\u00020\u0006J\u0006\u0010\t\u001a\u00020\u0006J\u0006\u0010\n\u001a\u00020\u0006J\u0006\u0010\u000b\u001a\u00020\u0006J\b\u0010\f\u001a\u00020\rH\u0007J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0011\u001a\u00020\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/lzf/easyfloat/permission/rom/RomUtils;", "", "()V", "TAG", "", "checkIs360Rom", "", "checkIsHuaweiRom", "checkIsMeizuRom", "checkIsMiuiRom", "checkIsOppoRom", "checkIsVivoRom", "getEmuiVersion", "", "getMiuiVersion", "", "getSystemProperty", "propName", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RomUtils {
    public static final RomUtils INSTANCE = new RomUtils();
    private static final String TAG = "RomUtils--->";

    private RomUtils() {
    }

    @JvmStatic
    public static final double getEmuiVersion() {
        try {
            String systemProperty = getSystemProperty("ro.build.version.emui");
            if (systemProperty == null) {
                Intrinsics.throwNpe();
            }
            int indexOf$default = StringsKt.indexOf$default((CharSequence) systemProperty, "_", 0, false, 6, (Object) null) + 1;
            if (systemProperty == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String substring = systemProperty.substring(indexOf$default);
            Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
            return Double.parseDouble(substring);
        } catch (Exception e) {
            e.printStackTrace();
            return 4.0d;
        }
    }

    public final int getMiuiVersion() {
        String systemProperty = getSystemProperty("ro.miui.ui.version.name");
        if (systemProperty != null) {
            try {
                String substring = systemProperty.substring(1);
                Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
                return Integer.parseInt(substring);
            } catch (Exception unused) {
                Log.e(TAG, "get miui version code error, version : " + systemProperty);
                return -1;
            }
        }
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0081 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @kotlin.jvm.JvmStatic
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.String getSystemProperty(java.lang.String r7) {
        /*
            java.lang.String r0 = "Exception while closing InputStream"
            java.lang.String r1 = "RomUtils--->"
            java.lang.String r2 = "propName"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r7, r2)
            r2 = 0
            r3 = r2
            java.io.BufferedReader r3 = (java.io.BufferedReader) r3
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            r4.<init>()     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            java.lang.String r5 = "getprop "
            r4.append(r5)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            r4.append(r7)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            java.lang.Process r3 = r3.exec(r4)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            java.lang.String r6 = "p"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r3, r6)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            java.io.InputStream r3 = r3.getInputStream()     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            r5.<init>(r3)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            java.io.Reader r5 = (java.io.Reader) r5     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            r3 = 1024(0x400, float:1.435E-42)
            r4.<init>(r5, r3)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            java.lang.String r3 = r4.readLine()     // Catch: java.io.IOException -> L54 java.lang.Throwable -> L7d
            java.lang.String r5 = "input.readLine()"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r3, r5)     // Catch: java.io.IOException -> L54 java.lang.Throwable -> L7d
            r4.close()     // Catch: java.io.IOException -> L54 java.lang.Throwable -> L7d
            r4.close()     // Catch: java.io.IOException -> L4d
            goto L53
        L4d:
            r7 = move-exception
            java.lang.Throwable r7 = (java.lang.Throwable) r7
            android.util.Log.e(r1, r0, r7)
        L53:
            return r3
        L54:
            r3 = move-exception
            goto L5a
        L56:
            r7 = move-exception
            goto L7f
        L58:
            r3 = move-exception
            r4 = r2
        L5a:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7d
            r5.<init>()     // Catch: java.lang.Throwable -> L7d
            java.lang.String r6 = "Unable to read sysprop "
            r5.append(r6)     // Catch: java.lang.Throwable -> L7d
            r5.append(r7)     // Catch: java.lang.Throwable -> L7d
            java.lang.String r7 = r5.toString()     // Catch: java.lang.Throwable -> L7d
            java.lang.Throwable r3 = (java.lang.Throwable) r3     // Catch: java.lang.Throwable -> L7d
            android.util.Log.e(r1, r7, r3)     // Catch: java.lang.Throwable -> L7d
            if (r4 == 0) goto L7c
            r4.close()     // Catch: java.io.IOException -> L76
            goto L7c
        L76:
            r7 = move-exception
            java.lang.Throwable r7 = (java.lang.Throwable) r7
            android.util.Log.e(r1, r0, r7)
        L7c:
            return r2
        L7d:
            r7 = move-exception
            r2 = r4
        L7f:
            if (r2 == 0) goto L8b
            r2.close()     // Catch: java.io.IOException -> L85
            goto L8b
        L85:
            r2 = move-exception
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            android.util.Log.e(r1, r0, r2)
        L8b:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lzf.easyfloat.permission.rom.RomUtils.getSystemProperty(java.lang.String):java.lang.String");
    }

    public final boolean checkIsHuaweiRom() {
        String str = Build.MANUFACTURER;
        Intrinsics.checkExpressionValueIsNotNull(str, "Build.MANUFACTURER");
        return StringsKt.contains$default((CharSequence) str, (CharSequence) "HUAWEI", false, 2, (Object) null);
    }

    public final boolean checkIsMiuiRom() {
        return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"));
    }

    public final boolean checkIsMeizuRom() {
        String systemProperty = getSystemProperty("ro.build.display.id");
        String str = systemProperty;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (systemProperty == null) {
            Intrinsics.throwNpe();
        }
        if (!StringsKt.contains$default((CharSequence) str, (CharSequence) "flyme", false, 2, (Object) null)) {
            String lowerCase = systemProperty.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            if (!StringsKt.contains$default((CharSequence) lowerCase, (CharSequence) "flyme", false, 2, (Object) null)) {
                return false;
            }
        }
        return true;
    }

    public final boolean checkIs360Rom() {
        String str = Build.MANUFACTURER;
        Intrinsics.checkExpressionValueIsNotNull(str, "Build.MANUFACTURER");
        if (!StringsKt.contains$default((CharSequence) str, (CharSequence) "QiKU", false, 2, (Object) null)) {
            String str2 = Build.MANUFACTURER;
            Intrinsics.checkExpressionValueIsNotNull(str2, "Build.MANUFACTURER");
            if (!StringsKt.contains$default((CharSequence) str2, (CharSequence) "360", false, 2, (Object) null)) {
                return false;
            }
        }
        return true;
    }

    public final boolean checkIsOppoRom() {
        String str = Build.MANUFACTURER;
        Intrinsics.checkExpressionValueIsNotNull(str, "Build.MANUFACTURER");
        if (!StringsKt.contains$default((CharSequence) str, (CharSequence) "OPPO", false, 2, (Object) null)) {
            String str2 = Build.MANUFACTURER;
            Intrinsics.checkExpressionValueIsNotNull(str2, "Build.MANUFACTURER");
            if (!StringsKt.contains$default((CharSequence) str2, (CharSequence) "oppo", false, 2, (Object) null)) {
                return false;
            }
        }
        return true;
    }

    public final boolean checkIsVivoRom() {
        String str = Build.MANUFACTURER;
        Intrinsics.checkExpressionValueIsNotNull(str, "Build.MANUFACTURER");
        if (!StringsKt.contains$default((CharSequence) str, (CharSequence) "VIVO", false, 2, (Object) null)) {
            String str2 = Build.MANUFACTURER;
            Intrinsics.checkExpressionValueIsNotNull(str2, "Build.MANUFACTURER");
            if (!StringsKt.contains$default((CharSequence) str2, (CharSequence) "vivo", false, 2, (Object) null)) {
                return false;
            }
        }
        return true;
    }
}
