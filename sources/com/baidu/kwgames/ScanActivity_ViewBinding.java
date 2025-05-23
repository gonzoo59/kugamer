package com.baidu.kwgames;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
/* loaded from: classes.dex */
public class ScanActivity_ViewBinding implements Unbinder {
    private ScanActivity target;

    public ScanActivity_ViewBinding(ScanActivity scanActivity) {
        this(scanActivity, scanActivity.getWindow().getDecorView());
    }

    public ScanActivity_ViewBinding(ScanActivity scanActivity, View view) {
        this.target = scanActivity;
        scanActivity.recyclerView = (RecyclerView) Utils.findRequiredViewAsType(view, R.id.scan_results, "field 'recyclerView'", RecyclerView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        ScanActivity scanActivity = this.target;
        if (scanActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        scanActivity.recyclerView = null;
    }
}
