package androidx.core.view;

import android.view.Menu;
import android.view.MenuItem;
import androidx.core.internal.view.SupportMenu;
import androidx.core.os.BuildCompat;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
public final class MenuCompat {
    @Deprecated
    public static void setShowAsAction(MenuItem menuItem, int i) {
        menuItem.setShowAsAction(i);
    }

    public static void setGroupDividerEnabled(Menu menu, boolean z) {
        if (menu instanceof SupportMenu) {
            ((SupportMenu) menu).setGroupDividerEnabled(z);
        } else if (BuildCompat.isAtLeastP()) {
            menu.setGroupDividerEnabled(z);
        }
    }

    private MenuCompat() {
    }
}
