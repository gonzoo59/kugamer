package com.jieli.otasdk.activities;

import android.widget.CheckBox;
import android.widget.RadioGroup;
import com.jieli.otasdk.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: SettingsActivity.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\n¢\u0006\u0002\b\u0007"}, d2 = {"<anonymous>", "", "group", "Landroid/widget/RadioGroup;", "kotlin.jvm.PlatformType", "checkedId", "", "onCheckedChanged"}, k = 3, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class SettingsActivity$initView$4 implements RadioGroup.OnCheckedChangeListener {
    final /* synthetic */ SettingsActivity this$0;

    SettingsActivity$initView$4(SettingsActivity settingsActivity) {
        this.this$0 = settingsActivity;
    }

    @Override // android.widget.RadioGroup.OnCheckedChangeListener
    public final void onCheckedChanged(RadioGroup radioGroup, int i) {
        CheckBox cb_settings_use_spp_private_channel = (CheckBox) this.this$0._$_findCachedViewById(R.id.cb_settings_use_spp_private_channel);
        Intrinsics.checkExpressionValueIsNotNull(cb_settings_use_spp_private_channel, "cb_settings_use_spp_private_channel");
        cb_settings_use_spp_private_channel.setEnabled(i == R.id.rb_settings_way_spp);
    }
}
