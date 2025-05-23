package com.jieli.component.permission;

import android.app.Activity;
/* loaded from: classes2.dex */
public interface IPermissionActivityCallback {
    void onCreate(Activity activity);

    void onDestroy();

    void onRequestPermissionsResult(int i, String[] strArr, int[] iArr);

    void onResume();
}
