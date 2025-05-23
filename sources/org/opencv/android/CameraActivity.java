package org.opencv.android;

import android.app.Activity;
import android.os.Build;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class CameraActivity extends Activity {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;

    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return new ArrayList();
    }

    protected void onCameraPermissionGranted() {
        List<? extends CameraBridgeViewBase> cameraViewList = getCameraViewList();
        if (cameraViewList == null) {
            return;
        }
        for (CameraBridgeViewBase cameraBridgeViewBase : cameraViewList) {
            if (cameraBridgeViewBase != null) {
                cameraBridgeViewBase.setCameraPermissionGranted();
            }
        }
    }

    @Override // android.app.Activity
    protected void onStart() {
        boolean z;
        super.onStart();
        if (Build.VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.CAMERA") == 0) {
            z = true;
        } else {
            requestPermissions(new String[]{"android.permission.CAMERA"}, 200);
            z = false;
        }
        if (z) {
            onCameraPermissionGranted();
        }
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 200 && iArr.length > 0 && iArr[0] == 0) {
            onCameraPermissionGranted();
        }
        super.onRequestPermissionsResult(i, strArr, iArr);
    }
}
