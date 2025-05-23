package com.jieli.otasdk.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.jieli.component.utils.SystemUtil;
import com.jieli.jl_bt_ota.util.PreferencesHelper;
import com.jieli.otasdk.R;
import com.jieli.otasdk.base.BaseActivity;
import com.jieli.otasdk.util.JL_Constant;
import java.util.Arrays;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
/* compiled from: SettingsActivity.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\tH\u0002J\b\u0010\n\u001a\u00020\tH\u0002J\u0012\u0010\u000b\u001a\u00020\t2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\b\u0010\u000e\u001a\u00020\tH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/jieli/otasdk/activities/SettingsActivity;", "Lcom/jieli/otasdk/base/BaseActivity;", "()V", "isChangeConfiguration", "", "isHidDevice", "isUseDeviceAuth", "useCustomReconnectWay", "checkIsChangeConfiguration", "", "initView", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "Companion", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class SettingsActivity extends BaseActivity {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private boolean isChangeConfiguration;
    private boolean isHidDevice;
    private boolean isUseDeviceAuth;
    private boolean useCustomReconnectWay;

    @Override // com.jieli.otasdk.base.BaseActivity
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.jieli.otasdk.base.BaseActivity
    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    /* compiled from: SettingsActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/jieli/otasdk/activities/SettingsActivity$Companion;", "", "()V", "newInstance", "Lcom/jieli/otasdk/activities/SettingsActivity;", "otasdk_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final SettingsActivity newInstance() {
            return new SettingsActivity();
        }
    }

    @Override // com.jieli.otasdk.base.BaseActivity, com.jieli.component.base.Jl_BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_settings);
        this.isUseDeviceAuth = PreferencesHelper.getSharedPreferences(getApplicationContext()).getBoolean(JL_Constant.KEY_IS_USE_DEVICE_AUTH, true);
        this.isHidDevice = PreferencesHelper.getSharedPreferences(getApplicationContext()).getBoolean(JL_Constant.KEY_IS_HID_DEVICE, false);
        this.useCustomReconnectWay = PreferencesHelper.getSharedPreferences(getApplicationContext()).getBoolean(JL_Constant.KEY_USE_CUSTOM_RECONNECT_WAY, false);
        initView();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.jieli.component.base.Jl_BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.isChangeConfiguration = false;
    }

    private final void initView() {
        TextView tv_settings_version_value = (TextView) _$_findCachedViewById(R.id.tv_settings_version_value);
        Intrinsics.checkExpressionValueIsNotNull(tv_settings_version_value, "tv_settings_version_value");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(" : %s", Arrays.copyOf(new Object[]{SystemUtil.getVersioName(getApplicationContext())}, 1));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
        tv_settings_version_value.setText(format);
        TextView tv_settings_top_right = (TextView) _$_findCachedViewById(R.id.tv_settings_top_right);
        Intrinsics.checkExpressionValueIsNotNull(tv_settings_top_right, "tv_settings_top_right");
        tv_settings_top_right.setVisibility(8);
        ConstraintLayout cl_settings_container = (ConstraintLayout) _$_findCachedViewById(R.id.cl_settings_container);
        Intrinsics.checkExpressionValueIsNotNull(cl_settings_container, "cl_settings_container");
        cl_settings_container.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkIsChangeConfiguration() {
        boolean z = true;
        boolean z2 = PreferencesHelper.getSharedPreferences(getApplicationContext()).getBoolean(JL_Constant.KEY_IS_USE_DEVICE_AUTH, true);
        boolean z3 = PreferencesHelper.getSharedPreferences(getApplicationContext()).getBoolean(JL_Constant.KEY_IS_HID_DEVICE, false);
        boolean z4 = PreferencesHelper.getSharedPreferences(getApplicationContext()).getBoolean(JL_Constant.KEY_USE_CUSTOM_RECONNECT_WAY, false);
        if (z2 == this.isUseDeviceAuth && z3 == this.isHidDevice && z4 == this.useCustomReconnectWay) {
            z = false;
        }
        this.isChangeConfiguration = z;
    }
}
