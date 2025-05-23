package com.jieli.otasdk.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import com.jieli.component.utils.ToastUtil;
import com.jieli.jl_bt_ota.util.PreferencesHelper;
import com.jieli.otasdk.R;
import com.jieli.otasdk.util.JL_Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: SettingsActivity.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, k = 3, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class SettingsActivity$initView$2 implements View.OnClickListener {
    final /* synthetic */ SettingsActivity this$0;

    SettingsActivity$initView$2(SettingsActivity settingsActivity) {
        this.this$0 = settingsActivity;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        boolean z;
        Context applicationContext = this.this$0.getApplicationContext();
        CheckBox cb_settings_device_auth = (CheckBox) this.this$0._$_findCachedViewById(R.id.cb_settings_device_auth);
        Intrinsics.checkExpressionValueIsNotNull(cb_settings_device_auth, "cb_settings_device_auth");
        PreferencesHelper.putBooleanValue(applicationContext, JL_Constant.KEY_IS_USE_DEVICE_AUTH, cb_settings_device_auth.isChecked());
        Context applicationContext2 = this.this$0.getApplicationContext();
        CheckBox cb_settings_hid_device = (CheckBox) this.this$0._$_findCachedViewById(R.id.cb_settings_hid_device);
        Intrinsics.checkExpressionValueIsNotNull(cb_settings_hid_device, "cb_settings_hid_device");
        PreferencesHelper.putBooleanValue(applicationContext2, JL_Constant.KEY_IS_HID_DEVICE, cb_settings_hid_device.isChecked());
        Context applicationContext3 = this.this$0.getApplicationContext();
        CheckBox cb_settings_custom_reconnect_way = (CheckBox) this.this$0._$_findCachedViewById(R.id.cb_settings_custom_reconnect_way);
        Intrinsics.checkExpressionValueIsNotNull(cb_settings_custom_reconnect_way, "cb_settings_custom_reconnect_way");
        PreferencesHelper.putBooleanValue(applicationContext3, JL_Constant.KEY_USE_CUSTOM_RECONNECT_WAY, cb_settings_custom_reconnect_way.isChecked());
        this.this$0.checkIsChangeConfiguration();
        z = this.this$0.isChangeConfiguration;
        if (z) {
            this.this$0.isChangeConfiguration = false;
            ToastUtil.showToastShort(R.string.settings_success_and_restart);
            new Handler().postDelayed(new Runnable() { // from class: com.jieli.otasdk.activities.SettingsActivity$initView$2.1
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsActivity$initView$2.this.this$0.finish();
                    SettingsActivity$initView$2.this.this$0.sendBroadcast(new Intent(JL_Constant.ACTION_EXIT_APP));
                }
            }, 1000L);
        }
    }
}
