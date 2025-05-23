package com.jieli.otasdk.activities;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import com.jieli.otasdk.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
/* compiled from: SettingsActivity.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class SettingsActivity$initView$3 implements View.OnClickListener {
    final /* synthetic */ SettingsActivity this$0;

    SettingsActivity$initView$3(SettingsActivity settingsActivity) {
        this.this$0 = settingsActivity;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        CharSequence trim;
        EditText et_settings_input_data = (EditText) this.this$0._$_findCachedViewById(R.id.et_settings_input_data);
        Intrinsics.checkExpressionValueIsNotNull(et_settings_input_data, "et_settings_input_data");
        Editable text = et_settings_input_data.getText();
        if (text == null || (trim = StringsKt.trim(text)) == null) {
            return;
        }
        trim.toString();
    }
}
