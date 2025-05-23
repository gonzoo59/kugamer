package androidx.core.graphics;

import android.graphics.Bitmap;
import android.os.Build;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
public final class BitmapCompat {
    public static boolean hasMipMap(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 18) {
            return bitmap.hasMipMap();
        }
        return false;
    }

    public static void setHasMipMap(Bitmap bitmap, boolean z) {
        if (Build.VERSION.SDK_INT >= 18) {
            bitmap.setHasMipMap(z);
        }
    }

    public static int getAllocationByteCount(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 19) {
            return bitmap.getAllocationByteCount();
        }
        return bitmap.getByteCount();
    }

    private BitmapCompat() {
    }
}
