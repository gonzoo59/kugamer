package com.baidu.kwgames;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
/* loaded from: classes.dex */
public class BackupActivity_ViewBinding implements Unbinder {
    private BackupActivity target;
    private View view7f090192;
    private View view7f09019e;
    private View view7f0901a0;
    private View view7f0901c9;
    private View view7f0901ca;

    public BackupActivity_ViewBinding(BackupActivity backupActivity) {
        this(backupActivity, backupActivity.getWindow().getDecorView());
    }

    public BackupActivity_ViewBinding(final BackupActivity backupActivity, View view) {
        this.target = backupActivity;
        backupActivity.m_layoutRoot = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.m_layoutRoot, "field 'm_layoutRoot'", LinearLayout.class);
        View findRequiredView = Utils.findRequiredView(view, R.id.m_btnBackup, "field 'm_btnBackup' and method 'onBackup'");
        backupActivity.m_btnBackup = (Button) Utils.castView(findRequiredView, R.id.m_btnBackup, "field 'm_btnBackup'", Button.class);
        this.view7f090192 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.BackupActivity_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                backupActivity.onBackup();
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, R.id.m_btnRestore, "field 'm_btnRestore' and method 'onRestore'");
        backupActivity.m_btnRestore = (Button) Utils.castView(findRequiredView2, R.id.m_btnRestore, "field 'm_btnRestore'", Button.class);
        this.view7f0901c9 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.BackupActivity_ViewBinding.2
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                backupActivity.onRestore();
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, R.id.m_btnDelete, "field 'm_btnDelete' and method 'onDelete'");
        backupActivity.m_btnDelete = (Button) Utils.castView(findRequiredView3, R.id.m_btnDelete, "field 'm_btnDelete'", Button.class);
        this.view7f09019e = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.BackupActivity_ViewBinding.3
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                backupActivity.onDelete();
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, R.id.m_btnShare, "field 'm_btnShare' and method 'onShare'");
        backupActivity.m_btnShare = (Button) Utils.castView(findRequiredView4, R.id.m_btnShare, "field 'm_btnShare'", Button.class);
        this.view7f0901ca = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.BackupActivity_ViewBinding.4
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                backupActivity.onShare();
            }
        });
        View findRequiredView5 = Utils.findRequiredView(view, R.id.m_btnExit, "field 'm_btnExit' and method 'onExit'");
        backupActivity.m_btnExit = (Button) Utils.castView(findRequiredView5, R.id.m_btnExit, "field 'm_btnExit'", Button.class);
        this.view7f0901a0 = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() { // from class: com.baidu.kwgames.BackupActivity_ViewBinding.5
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                backupActivity.onExit();
            }
        });
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        BackupActivity backupActivity = this.target;
        if (backupActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        backupActivity.m_layoutRoot = null;
        backupActivity.m_btnBackup = null;
        backupActivity.m_btnRestore = null;
        backupActivity.m_btnDelete = null;
        backupActivity.m_btnShare = null;
        backupActivity.m_btnExit = null;
        this.view7f090192.setOnClickListener(null);
        this.view7f090192 = null;
        this.view7f0901c9.setOnClickListener(null);
        this.view7f0901c9 = null;
        this.view7f09019e.setOnClickListener(null);
        this.view7f09019e = null;
        this.view7f0901ca.setOnClickListener(null);
        this.view7f0901ca = null;
        this.view7f0901a0.setOnClickListener(null);
        this.view7f0901a0 = null;
    }
}
