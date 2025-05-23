package com.jieli.otasdk.activities;

import androidx.core.app.ActivityCompat;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import permissions.dispatcher.PermissionUtils;
/* compiled from: WelcomeActivityPermissionsDispatcher.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\u0002\u001a\u001a\u0010\u0006\u001a\u00020\u0007*\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000b\u001a\n\u0010\f\u001a\u00020\u0007*\u00020\b\"\u0016\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"PERMISSION_TOMAINACTIVITY", "", "", "[Ljava/lang/String;", "REQUEST_TOMAINACTIVITY", "", "onRequestPermissionsResult", "", "Lcom/jieli/otasdk/activities/WelcomeActivity;", "requestCode", "grantResults", "", "toMainActivityWithPermissionCheck", "otasdk_release"}, k = 2, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class WelcomeActivityPermissionsDispatcher {
    private static final String[] PERMISSION_TOMAINACTIVITY = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static final int REQUEST_TOMAINACTIVITY = 0;

    public static final void toMainActivityWithPermissionCheck(WelcomeActivity toMainActivityWithPermissionCheck) {
        Intrinsics.checkParameterIsNotNull(toMainActivityWithPermissionCheck, "$this$toMainActivityWithPermissionCheck");
        String[] strArr = PERMISSION_TOMAINACTIVITY;
        if (PermissionUtils.hasSelfPermissions(toMainActivityWithPermissionCheck, (String[]) Arrays.copyOf(strArr, strArr.length))) {
            toMainActivityWithPermissionCheck.toMainActivity();
            return;
        }
        WelcomeActivity welcomeActivity = toMainActivityWithPermissionCheck;
        if (PermissionUtils.shouldShowRequestPermissionRationale(welcomeActivity, (String[]) Arrays.copyOf(strArr, strArr.length))) {
            toMainActivityWithPermissionCheck.showRationaleForPermission(new WelcomeActivityToMainActivityPermissionRequest(toMainActivityWithPermissionCheck));
        } else {
            ActivityCompat.requestPermissions(welcomeActivity, strArr, REQUEST_TOMAINACTIVITY);
        }
    }

    public static final void onRequestPermissionsResult(WelcomeActivity onRequestPermissionsResult, int i, int[] grantResults) {
        Intrinsics.checkParameterIsNotNull(onRequestPermissionsResult, "$this$onRequestPermissionsResult");
        Intrinsics.checkParameterIsNotNull(grantResults, "grantResults");
        if (i == REQUEST_TOMAINACTIVITY) {
            if (PermissionUtils.verifyPermissions(Arrays.copyOf(grantResults, grantResults.length))) {
                onRequestPermissionsResult.toMainActivity();
                return;
            }
            String[] strArr = PERMISSION_TOMAINACTIVITY;
            if (!PermissionUtils.shouldShowRequestPermissionRationale(onRequestPermissionsResult, (String[]) Arrays.copyOf(strArr, strArr.length))) {
                onRequestPermissionsResult.onPermissionsNeverAskAgain();
            } else {
                onRequestPermissionsResult.onPermissionsDenied();
            }
        }
    }
}
