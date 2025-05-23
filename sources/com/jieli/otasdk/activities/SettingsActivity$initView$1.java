package com.jieli.otasdk.activities;

import android.view.View;
import kotlin.Metadata;
/* compiled from: SettingsActivity.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "view", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class SettingsActivity$initView$1 implements View.OnClickListener {
    final /* synthetic */ SettingsActivity this$0;

    SettingsActivity$initView$1(SettingsActivity settingsActivity) {
        this.this$0 = settingsActivity;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        this.this$0.finish();
    }
}
