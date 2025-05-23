package androidx.core.os;

import android.os.Handler;
import android.os.Message;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
public final class HandlerCompat {
    public static boolean postDelayed(Handler handler, Runnable runnable, Object obj, long j) {
        if (BuildCompat.isAtLeastP()) {
            return handler.postDelayed(runnable, obj, j);
        }
        Message obtain = Message.obtain(handler, runnable);
        obtain.obj = obj;
        return handler.sendMessageDelayed(obtain, j);
    }

    private HandlerCompat() {
    }
}
