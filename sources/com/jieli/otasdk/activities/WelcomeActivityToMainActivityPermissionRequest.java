package com.jieli.otasdk.activities;

import androidx.core.app.ActivityCompat;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import permissions.dispatcher.PermissionRequest;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: WelcomeActivityPermissionsDispatcher.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/jieli/otasdk/activities/WelcomeActivityToMainActivityPermissionRequest;", "Lpermissions/dispatcher/PermissionRequest;", "target", "Lcom/jieli/otasdk/activities/WelcomeActivity;", "(Lcom/jieli/otasdk/activities/WelcomeActivity;)V", "weakTarget", "Ljava/lang/ref/WeakReference;", "cancel", "", "proceed", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class WelcomeActivityToMainActivityPermissionRequest implements PermissionRequest {
    private final WeakReference<WelcomeActivity> weakTarget;

    public WelcomeActivityToMainActivityPermissionRequest(WelcomeActivity target) {
        Intrinsics.checkParameterIsNotNull(target, "target");
        this.weakTarget = new WeakReference<>(target);
    }

    @Override // permissions.dispatcher.PermissionRequest
    public void proceed() {
        String[] strArr;
        int i;
        WelcomeActivity welcomeActivity = this.weakTarget.get();
        if (welcomeActivity != null) {
            Intrinsics.checkExpressionValueIsNotNull(welcomeActivity, "weakTarget.get() ?: return");
            strArr = WelcomeActivityPermissionsDispatcher.PERMISSION_TOMAINACTIVITY;
            i = WelcomeActivityPermissionsDispatcher.REQUEST_TOMAINACTIVITY;
            ActivityCompat.requestPermissions(welcomeActivity, strArr, i);
        }
    }

    @Override // permissions.dispatcher.PermissionRequest
    public void cancel() {
        WelcomeActivity welcomeActivity = this.weakTarget.get();
        if (welcomeActivity != null) {
            Intrinsics.checkExpressionValueIsNotNull(welcomeActivity, "weakTarget.get() ?: return");
            welcomeActivity.onPermissionsDenied();
        }
    }
}
