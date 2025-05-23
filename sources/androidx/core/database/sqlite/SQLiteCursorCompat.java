package androidx.core.database.sqlite;

import android.database.sqlite.SQLiteCursor;
import androidx.core.os.BuildCompat;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
public final class SQLiteCursorCompat {
    private SQLiteCursorCompat() {
    }

    public void setFillWindowForwardOnly(SQLiteCursor sQLiteCursor, boolean z) {
        if (BuildCompat.isAtLeastP()) {
            sQLiteCursor.setFillWindowForwardOnly(z);
        }
    }
}
