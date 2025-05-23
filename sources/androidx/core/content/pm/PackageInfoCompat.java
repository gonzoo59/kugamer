package androidx.core.content.pm;

import android.content.pm.PackageInfo;
import androidx.core.os.BuildCompat;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
public final class PackageInfoCompat {
    public static long getLongVersionCode(PackageInfo packageInfo) {
        if (BuildCompat.isAtLeastP()) {
            return packageInfo.getLongVersionCode();
        }
        return packageInfo.versionCode;
    }

    private PackageInfoCompat() {
    }
}
