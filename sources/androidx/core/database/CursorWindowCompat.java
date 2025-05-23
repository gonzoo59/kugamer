package androidx.core.database;

import android.database.CursorWindow;
import android.os.Build;
import androidx.core.os.BuildCompat;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
public final class CursorWindowCompat {
    private CursorWindowCompat() {
    }

    public CursorWindow create(String str, long j) {
        if (BuildCompat.isAtLeastP()) {
            return new CursorWindow(str, j);
        }
        if (Build.VERSION.SDK_INT >= 15) {
            return new CursorWindow(str);
        }
        return new CursorWindow(false);
    }
}
