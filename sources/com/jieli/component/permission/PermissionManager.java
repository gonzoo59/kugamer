package com.jieli.component.permission;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.jieli.component.BuildConfig;
import com.jieli.component.Logcat;
import com.jieli.component.utils.SystemUtil;
import java.util.ArrayList;
import org.opencv.videoio.Videoio;
/* loaded from: classes2.dex */
public class PermissionManager {
    private static final int PERMISSION_REQUEST_CODE = 4369;
    public static final String TAG = "PermissionManager";
    private Activity mActivity;
    private PermissionActivity mPermissionActivity;
    private String[] mUseDeniedPermissions;
    private OnPermissionStateCallback onPermissionStateCallback;

    /* renamed from: permissions  reason: collision with root package name */
    private String[] f3permissions;

    /* loaded from: classes2.dex */
    public interface OnPermissionStateCallback {
        void onError(int i, String str);

        void onFailed(boolean z, String str, Intent intent);

        void onSuccess();
    }

    public PermissionManager(Activity activity) {
        this.mActivity = (Activity) SystemUtil.checkNotNull(activity);
    }

    public static PermissionManager with(Activity activity) {
        return new PermissionManager(activity);
    }

    public PermissionManager permissions(String[] strArr) {
        this.f3permissions = strArr;
        return this;
    }

    public PermissionManager callback(OnPermissionStateCallback onPermissionStateCallback) {
        this.onPermissionStateCallback = onPermissionStateCallback;
        return this;
    }

    public void request() {
        getDeniedPermissions(this.mActivity, this.f3permissions);
        checkPermissions(this.f3permissions, this.mUseDeniedPermissions);
    }

    public void release() {
        this.onPermissionStateCallback = null;
        finishPermissionActivity();
        this.mActivity = null;
    }

    private void finishPermissionActivity() {
        PermissionActivity permissionActivity = this.mPermissionActivity;
        if (permissionActivity != null) {
            permissionActivity.finish();
            this.mPermissionActivity = null;
        }
    }

    private void callbackSuccessEvent() {
        OnPermissionStateCallback onPermissionStateCallback = this.onPermissionStateCallback;
        if (onPermissionStateCallback != null) {
            onPermissionStateCallback.onSuccess();
        }
        finishPermissionActivity();
    }

    private void callbackFailedEvent(String str) {
        callbackFailedEvent(false, str);
    }

    private void callbackFailedEvent(boolean z, String str) {
        OnPermissionStateCallback onPermissionStateCallback = this.onPermissionStateCallback;
        if (onPermissionStateCallback != null) {
            onPermissionStateCallback.onFailed(z, str, getIntentByPermission(str));
        }
        finishPermissionActivity();
    }

    private void callbackErrorEvent(int i, String str) {
        OnPermissionStateCallback onPermissionStateCallback = this.onPermissionStateCallback;
        if (onPermissionStateCallback != null) {
            onPermissionStateCallback.onError(i, str);
        }
        finishPermissionActivity();
    }

    private void checkPermissions(String[] strArr, String[] strArr2) {
        if (Build.VERSION.SDK_INT < 23) {
            callbackSuccessEvent();
        } else if (strArr != null && strArr.length > 0) {
            requestPermissions(strArr);
        } else if (strArr2 != null && strArr2.length > 0) {
            for (String str : strArr2) {
                callbackFailedEvent(str);
            }
        } else {
            callbackSuccessEvent();
        }
    }

    private void requestPermissions(final String[] strArr) {
        PermissionActivity permissionActivity = this.mPermissionActivity;
        if (permissionActivity == null) {
            PermissionActivity.goToPermissionActivity(this.mActivity, new IPermissionActivityCallback() { // from class: com.jieli.component.permission.PermissionManager.1
                @Override // com.jieli.component.permission.IPermissionActivityCallback
                public void onResume() {
                }

                @Override // com.jieli.component.permission.IPermissionActivityCallback
                public void onCreate(Activity activity) {
                    PermissionManager.this.mPermissionActivity = (PermissionActivity) activity;
                    ActivityCompat.requestPermissions(PermissionManager.this.mPermissionActivity, strArr, PermissionManager.PERMISSION_REQUEST_CODE);
                }

                @Override // com.jieli.component.permission.IPermissionActivityCallback
                public void onRequestPermissionsResult(int i, String[] strArr2, int[] iArr) {
                    PermissionManager permissionManager = PermissionManager.this;
                    permissionManager.onRequestResult(permissionManager.mPermissionActivity, i, strArr2, iArr);
                }

                @Override // com.jieli.component.permission.IPermissionActivityCallback
                public void onDestroy() {
                    PermissionManager.this.mPermissionActivity = null;
                }
            });
        } else {
            ActivityCompat.requestPermissions(permissionActivity, strArr, PERMISSION_REQUEST_CODE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRequestResult(Activity activity, int i, String[] strArr, int[] iArr) {
        if (i != PERMISSION_REQUEST_CODE || Build.VERSION.SDK_INT < 23) {
            return;
        }
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (iArr[i2] != 0) {
                Logcat.e(TAG, "ret=" + iArr[i2] + "\tpermission=" + strArr[i2]);
                onPermissionStatus(activity, iArr[i2], strArr[i2]);
                return;
            }
        }
        Logcat.e(TAG, "PERMISSIONS are granted.");
        callbackSuccessEvent();
    }

    private void onPermissionStatus(Activity activity, int i, String str) {
        if (i != 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, str)) {
                Logcat.w(TAG, "shouldShowRequestPermissionRationale 1:" + str);
                callbackFailedEvent(true, str);
                return;
            }
            callbackFailedEvent(str);
        }
    }

    private Intent getIntentByPermission(String str) {
        if (str.equals("android.permission.WRITE_SETTINGS")) {
            Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
            intent.setData(Uri.parse("package:" + this.mActivity.getApplication().getPackageName()));
            return intent;
        }
        return new Intent("android.settings.SETTINGS");
    }

    private Intent getIntentByBrand() {
        String str = Build.MANUFACTURER;
        Logcat.e(TAG, str);
        if (TextUtils.isEmpty(str)) {
            return new Intent("android.settings.SETTINGS");
        }
        if (str.equalsIgnoreCase("Huawei")) {
            Intent intent = new Intent();
            intent.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            intent.putExtra("packageName", BuildConfig.LIBRARY_PACKAGE_NAME);
            intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
            return intent;
        } else if (str.equalsIgnoreCase("Meizu")) {
            Intent intent2 = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent2.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            intent2.addCategory("android.intent.category.DEFAULT");
            intent2.putExtra("packageName", BuildConfig.LIBRARY_PACKAGE_NAME);
            return intent2;
        } else if (str.equalsIgnoreCase("Xiaomi")) {
            Intent intent3 = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent3.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            intent3.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity"));
            intent3.putExtra("extra_pkgname", BuildConfig.LIBRARY_PACKAGE_NAME);
            return intent3;
        } else if (str.equalsIgnoreCase("Sony")) {
            Intent intent4 = new Intent();
            intent4.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            intent4.putExtra("packageName", BuildConfig.LIBRARY_PACKAGE_NAME);
            intent4.setComponent(new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity"));
            return intent4;
        } else if (str.equalsIgnoreCase("OPPO")) {
            Intent intent5 = new Intent();
            intent5.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            intent5.putExtra("packageName", BuildConfig.LIBRARY_PACKAGE_NAME);
            intent5.setComponent(new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity"));
            return intent5;
        } else if (str.equalsIgnoreCase("LG")) {
            Intent intent6 = new Intent("android.intent.action.MAIN");
            intent6.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            intent6.putExtra("packageName", BuildConfig.LIBRARY_PACKAGE_NAME);
            intent6.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity"));
            return intent6;
        } else if (str.equalsIgnoreCase("Letv")) {
            Intent intent7 = new Intent();
            intent7.setFlags(Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            intent7.putExtra("packageName", BuildConfig.LIBRARY_PACKAGE_NAME);
            intent7.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps"));
            return intent7;
        } else {
            return new Intent("android.settings.SETTINGS");
        }
    }

    private void getDeniedPermissions(Activity activity, String[] strArr) {
        if (activity == null || strArr == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (String str : strArr) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(activity, str);
            if (str.equals("android.permission.WRITE_SETTINGS")) {
                if (!Settings.System.canWrite(activity)) {
                    arrayList.add(str);
                }
            } else if (checkSelfPermission == -1) {
                arrayList.add(str);
            } else if (checkSelfPermission == -2) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, str)) {
                    arrayList.add(str);
                } else {
                    arrayList2.add(str);
                }
            }
        }
        if (arrayList2.size() > 0) {
            this.mUseDeniedPermissions = (String[]) arrayList2.toArray(new String[0]);
        }
        this.f3permissions = (String[]) arrayList.toArray(new String[0]);
    }
}
