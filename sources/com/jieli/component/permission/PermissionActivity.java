package com.jieli.component.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import org.opencv.videoio.Videoio;
/* loaded from: classes2.dex */
public class PermissionActivity extends Activity {
    private static final String TAG = "PermissionActivity";
    private static IPermissionActivityCallback mCallback;

    public static void goToPermissionActivity(Context context, IPermissionActivityCallback iPermissionActivityCallback) {
        mCallback = iPermissionActivityCallback;
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.addFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
        context.startActivity(intent);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        IPermissionActivityCallback iPermissionActivityCallback = mCallback;
        if (iPermissionActivityCallback != null) {
            iPermissionActivityCallback.onCreate(this);
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        IPermissionActivityCallback iPermissionActivityCallback = mCallback;
        if (iPermissionActivityCallback != null) {
            iPermissionActivityCallback.onResume();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        IPermissionActivityCallback iPermissionActivityCallback = mCallback;
        if (iPermissionActivityCallback != null) {
            iPermissionActivityCallback.onDestroy();
        }
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        IPermissionActivityCallback iPermissionActivityCallback = mCallback;
        if (iPermissionActivityCallback != null) {
            iPermissionActivityCallback.onRequestPermissionsResult(i, strArr, iArr);
        }
    }
}
