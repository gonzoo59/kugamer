package androidx.core.content.pm;

import android.content.pm.PermissionInfo;
import androidx.core.os.BuildCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
public final class PermissionInfoCompat {

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: assets/adb/sincoserver.dex */
    public @interface Protection {
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: assets/adb/sincoserver.dex */
    public @interface ProtectionFlags {
    }

    private PermissionInfoCompat() {
    }

    public static int getProtection(PermissionInfo permissionInfo) {
        if (BuildCompat.isAtLeastP()) {
            return permissionInfo.getProtection();
        }
        return permissionInfo.protectionLevel & 15;
    }

    public static int getProtectionFlags(PermissionInfo permissionInfo) {
        if (BuildCompat.isAtLeastP()) {
            return permissionInfo.getProtectionFlags();
        }
        return permissionInfo.protectionLevel & (-16);
    }
}
