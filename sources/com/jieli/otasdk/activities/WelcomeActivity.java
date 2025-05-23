package com.jieli.otasdk.activities;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.DialogFragment;
import com.baidu.kwgames.GameSettingFloat;
import com.blankj.utilcode.util.ScreenUtils;
import com.jieli.component.utils.HandlerManager;
import com.jieli.jl_dialog.Jl_Dialog;
import com.jieli.jl_dialog.interfaces.OnViewClickListener;
import com.jieli.otasdk.R;
import com.jieli.otasdk.base.BaseActivity;
import com.jieli.otasdk.util.OtaFileObserverHelper;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import permissions.dispatcher.PermissionRequest;
/* compiled from: WelcomeActivity.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 $2\u00020\u0001:\u0001$B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u000b\u001a\u00020\nH\u0002J\"\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014J\u0012\u0010\u0012\u001a\u00020\n2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\b\u0010\u0015\u001a\u00020\nH\u0014J\b\u0010\u0016\u001a\u00020\nH\u0007J\b\u0010\u0017\u001a\u00020\nH\u0007J-\u0010\u0018\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u000e\u0010\u0019\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016¢\u0006\u0002\u0010\u001eJ\b\u0010\u001f\u001a\u00020\nH\u0002J\u0010\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\"H\u0007J\b\u0010#\u001a\u00020\nH\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/jieli/otasdk/activities/WelcomeActivity;", "Lcom/jieli/otasdk/base/BaseActivity;", "()V", "notifyGpsDialog", "Lcom/jieli/jl_dialog/Jl_Dialog;", "checkGpsProviderEnable", "", "context", "Landroid/content/Context;", "dismissNotifyGPSDialog", "", "goToMainActivity", "onActivityResult", "requestCode", "", "resultCode", GameSettingFloat.TAG_ATTR_DATA, "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPermissionsDenied", "onPermissionsNeverAskAgain", "onRequestPermissionsResult", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "showNotifyGPSDialog", "showRationaleForPermission", "request", "Lpermissions/dispatcher/PermissionRequest;", "toMainActivity", "Companion", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class WelcomeActivity extends BaseActivity {
    public static final Companion Companion = new Companion(null);
    public static final int GPS_REQUEST_CODE = 1234;
    private HashMap _$_findViewCache;
    private Jl_Dialog notifyGpsDialog;

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

    /* compiled from: WelcomeActivity.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/jieli/otasdk/activities/WelcomeActivity$Companion;", "", "()V", "GPS_REQUEST_CODE", "", "otasdk_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final void toMainActivity() {
        if (checkGpsProviderEnable(getApplicationContext())) {
            goToMainActivity();
        } else {
            showNotifyGPSDialog();
        }
    }

    @Override // com.jieli.otasdk.base.BaseActivity, com.jieli.component.base.Jl_BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ScreenUtils.setFullScreen(this);
        setContentView(R.layout.activity_welcome);
        if (!isTaskRoot()) {
            WelcomeActivityPermissionsDispatcher.toMainActivityWithPermissionCheck(this);
            return;
        }
        HandlerManager handlerManager = HandlerManager.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(handlerManager, "HandlerManager.getInstance()");
        handlerManager.getMainHandler().postDelayed(new Runnable() { // from class: com.jieli.otasdk.activities.WelcomeActivity$onCreate$1
            @Override // java.lang.Runnable
            public final void run() {
                WelcomeActivityPermissionsDispatcher.toMainActivityWithPermissionCheck(WelcomeActivity.this);
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.jieli.component.base.Jl_BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        dismissNotifyGPSDialog();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (1234 == i) {
            goToMainActivity();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, String[] permissions2, int[] grantResults) {
        Intrinsics.checkParameterIsNotNull(permissions2, "permissions");
        Intrinsics.checkParameterIsNotNull(grantResults, "grantResults");
        super.onRequestPermissionsResult(i, permissions2, grantResults);
        WelcomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, i, grantResults);
    }

    public final void showRationaleForPermission(PermissionRequest request) {
        Intrinsics.checkParameterIsNotNull(request, "request");
        request.proceed();
    }

    public final void onPermissionsNeverAskAgain() {
        goToMainActivity();
    }

    public final void onPermissionsDenied() {
        goToMainActivity();
    }

    private final boolean checkGpsProviderEnable(Context context) {
        if (context == null) {
            return false;
        }
        Object systemService = context.getSystemService("location");
        if (systemService == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.location.LocationManager");
        }
        return ((LocationManager) systemService).isProviderEnabled("gps");
    }

    private final void showNotifyGPSDialog() {
        Jl_Dialog jl_Dialog;
        if (isDestroyed() || isFinishing()) {
            return;
        }
        if (this.notifyGpsDialog == null) {
            this.notifyGpsDialog = new Jl_Dialog.Builder().title(getString(R.string.tips)).content(getString(R.string.open_gpg_tip)).cancel(false).left(getString(R.string.cancel)).leftColor(getResources().getColor(R.color.gray_text_444444)).right(getString(R.string.to_setting)).rightColor(getResources().getColor(R.color.red_FF688C)).leftClickListener(new OnViewClickListener() { // from class: com.jieli.otasdk.activities.WelcomeActivity$showNotifyGPSDialog$1
                @Override // com.jieli.jl_dialog.interfaces.OnViewClickListener
                public final void onClick(View view, DialogFragment dialogFragment) {
                    WelcomeActivity.this.dismissNotifyGPSDialog();
                    WelcomeActivity.this.goToMainActivity();
                }
            }).rightClickListener(new OnViewClickListener() { // from class: com.jieli.otasdk.activities.WelcomeActivity$showNotifyGPSDialog$2
                @Override // com.jieli.jl_dialog.interfaces.OnViewClickListener
                public final void onClick(View view, DialogFragment dialogFragment) {
                    WelcomeActivity.this.dismissNotifyGPSDialog();
                    WelcomeActivity.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), WelcomeActivity.GPS_REQUEST_CODE);
                }
            }).build();
        }
        Jl_Dialog jl_Dialog2 = this.notifyGpsDialog;
        Boolean valueOf = jl_Dialog2 != null ? Boolean.valueOf(jl_Dialog2.isShow()) : null;
        if (valueOf == null) {
            Intrinsics.throwNpe();
        }
        if (valueOf.booleanValue() || (jl_Dialog = this.notifyGpsDialog) == null) {
            return;
        }
        jl_Dialog.show(getSupportFragmentManager(), "notify_gps_dialog");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dismissNotifyGPSDialog() {
        Jl_Dialog jl_Dialog = this.notifyGpsDialog;
        if (jl_Dialog != null) {
            if (jl_Dialog == null) {
                Intrinsics.throwNpe();
            }
            if (jl_Dialog.isShow() && !isDestroyed()) {
                Jl_Dialog jl_Dialog2 = this.notifyGpsDialog;
                if (jl_Dialog2 == null) {
                    Intrinsics.throwNpe();
                }
                jl_Dialog2.dismiss();
            }
            this.notifyGpsDialog = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void goToMainActivity() {
        OtaFileObserverHelper.getInstance().startObserver();
        if (isTaskRoot()) {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
}
