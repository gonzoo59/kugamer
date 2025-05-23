package org.opencv.android;

import android.hardware.Camera;
import android.util.Log;
/* loaded from: classes2.dex */
public class CameraRenderer extends CameraGLRendererBase {
    public static final String LOGTAG = "CameraRenderer";
    private Camera mCamera;
    private boolean mPreviewStarted;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CameraRenderer(CameraGLSurfaceView cameraGLSurfaceView) {
        super(cameraGLSurfaceView);
        this.mPreviewStarted = false;
    }

    @Override // org.opencv.android.CameraGLRendererBase
    protected synchronized void closeCamera() {
        Log.i(LOGTAG, "closeCamera");
        Camera camera = this.mCamera;
        if (camera != null) {
            camera.stopPreview();
            this.mPreviewStarted = false;
            this.mCamera.release();
            this.mCamera = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00e6 A[Catch: all -> 0x018b, TryCatch #2 {, blocks: (B:3:0x0001, B:5:0x0012, B:6:0x0019, B:10:0x003b, B:12:0x003f, B:15:0x0044, B:17:0x004a, B:18:0x0065, B:24:0x0094, B:21:0x006e, B:54:0x013d, B:56:0x0141, B:59:0x014a, B:61:0x0154, B:63:0x015c, B:64:0x0161, B:65:0x0166, B:68:0x016f, B:9:0x0021, B:25:0x0097, B:27:0x009b, B:29:0x00a5, B:30:0x00b1, B:32:0x00b7, B:35:0x00bf, B:46:0x00e6, B:48:0x00f0, B:49:0x00f8, B:50:0x0113, B:53:0x011b, B:36:0x00c2, B:38:0x00c6, B:39:0x00d2, B:41:0x00d8, B:44:0x00e1), top: B:78:0x0001, inners: #0, #1, #3, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0141 A[Catch: all -> 0x018b, TRY_LEAVE, TryCatch #2 {, blocks: (B:3:0x0001, B:5:0x0012, B:6:0x0019, B:10:0x003b, B:12:0x003f, B:15:0x0044, B:17:0x004a, B:18:0x0065, B:24:0x0094, B:21:0x006e, B:54:0x013d, B:56:0x0141, B:59:0x014a, B:61:0x0154, B:63:0x015c, B:64:0x0161, B:65:0x0166, B:68:0x016f, B:9:0x0021, B:25:0x0097, B:27:0x009b, B:29:0x00a5, B:30:0x00b1, B:32:0x00b7, B:35:0x00bf, B:46:0x00e6, B:48:0x00f0, B:49:0x00f8, B:50:0x0113, B:53:0x011b, B:36:0x00c2, B:38:0x00c6, B:39:0x00d2, B:41:0x00d8, B:44:0x00e1), top: B:78:0x0001, inners: #0, #1, #3, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x014a A[Catch: all -> 0x018b, TRY_ENTER, TryCatch #2 {, blocks: (B:3:0x0001, B:5:0x0012, B:6:0x0019, B:10:0x003b, B:12:0x003f, B:15:0x0044, B:17:0x004a, B:18:0x0065, B:24:0x0094, B:21:0x006e, B:54:0x013d, B:56:0x0141, B:59:0x014a, B:61:0x0154, B:63:0x015c, B:64:0x0161, B:65:0x0166, B:68:0x016f, B:9:0x0021, B:25:0x0097, B:27:0x009b, B:29:0x00a5, B:30:0x00b1, B:32:0x00b7, B:35:0x00bf, B:46:0x00e6, B:48:0x00f0, B:49:0x00f8, B:50:0x0113, B:53:0x011b, B:36:0x00c2, B:38:0x00c6, B:39:0x00d2, B:41:0x00d8, B:44:0x00e1), top: B:78:0x0001, inners: #0, #1, #3, #4 }] */
    @Override // org.opencv.android.CameraGLRendererBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected synchronized void openCamera(int r7) {
        /*
            Method dump skipped, instructions count: 398
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.opencv.android.CameraRenderer.openCamera(int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0102 A[Catch: all -> 0x0126, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x0029, B:9:0x0032, B:11:0x0036, B:13:0x003a, B:14:0x003c, B:16:0x0040, B:18:0x0044, B:19:0x0046, B:21:0x0056, B:22:0x0060, B:24:0x0066, B:29:0x0096, B:35:0x00b1, B:37:0x00fe, B:39:0x0102, B:40:0x0109, B:36:0x00d0, B:41:0x0110), top: B:47:0x0007 }] */
    @Override // org.opencv.android.CameraGLRendererBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized void setCameraPreviewSize(int r18, int r19) {
        /*
            Method dump skipped, instructions count: 297
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.opencv.android.CameraRenderer.setCameraPreviewSize(int, int):void");
    }
}
