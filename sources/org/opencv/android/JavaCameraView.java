package org.opencv.android;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
/* loaded from: classes2.dex */
public class JavaCameraView extends CameraBridgeViewBase implements Camera.PreviewCallback {
    private static final int MAGIC_TEXTURE_ID = 10;
    private static final String TAG = "JavaCameraView";
    private byte[] mBuffer;
    protected Camera mCamera;
    protected JavaCameraFrame[] mCameraFrame;
    private boolean mCameraFrameReady;
    private int mChainIdx;
    private Mat[] mFrameChain;
    private int mPreviewFormat;
    private boolean mStopThread;
    private SurfaceTexture mSurfaceTexture;
    private Thread mThread;

    /* loaded from: classes2.dex */
    public static class JavaCameraSizeAccessor implements CameraBridgeViewBase.ListItemAccessor {
        @Override // org.opencv.android.CameraBridgeViewBase.ListItemAccessor
        public int getWidth(Object obj) {
            return ((Camera.Size) obj).width;
        }

        @Override // org.opencv.android.CameraBridgeViewBase.ListItemAccessor
        public int getHeight(Object obj) {
            return ((Camera.Size) obj).height;
        }
    }

    public JavaCameraView(Context context, int i) {
        super(context, i);
        this.mChainIdx = 0;
        this.mPreviewFormat = 17;
        this.mCameraFrameReady = false;
    }

    public JavaCameraView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mChainIdx = 0;
        this.mPreviewFormat = 17;
        this.mCameraFrameReady = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0275 A[Catch: Exception -> 0x0316, all -> 0x031c, TryCatch #2 {Exception -> 0x0316, blocks: (B:60:0x0150, B:62:0x0161, B:64:0x0174, B:66:0x017e, B:68:0x0188, B:70:0x0192, B:72:0x019c, B:74:0x01a6, B:76:0x01b0, B:78:0x01ba, B:81:0x01c5, B:83:0x01d1, B:85:0x0212, B:87:0x021c, B:88:0x021f, B:90:0x0225, B:92:0x022d, B:93:0x0232, B:95:0x0255, B:97:0x025d, B:99:0x0271, B:101:0x0275, B:102:0x027e, B:104:0x02f4, B:106:0x0308, B:105:0x0303, B:98:0x026e, B:82:0x01cb), top: B:119:0x0150, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:104:0x02f4 A[Catch: Exception -> 0x0316, all -> 0x031c, TryCatch #2 {Exception -> 0x0316, blocks: (B:60:0x0150, B:62:0x0161, B:64:0x0174, B:66:0x017e, B:68:0x0188, B:70:0x0192, B:72:0x019c, B:74:0x01a6, B:76:0x01b0, B:78:0x01ba, B:81:0x01c5, B:83:0x01d1, B:85:0x0212, B:87:0x021c, B:88:0x021f, B:90:0x0225, B:92:0x022d, B:93:0x0232, B:95:0x0255, B:97:0x025d, B:99:0x0271, B:101:0x0275, B:102:0x027e, B:104:0x02f4, B:106:0x0308, B:105:0x0303, B:98:0x026e, B:82:0x01cb), top: B:119:0x0150, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0303 A[Catch: Exception -> 0x0316, all -> 0x031c, TryCatch #2 {Exception -> 0x0316, blocks: (B:60:0x0150, B:62:0x0161, B:64:0x0174, B:66:0x017e, B:68:0x0188, B:70:0x0192, B:72:0x019c, B:74:0x01a6, B:76:0x01b0, B:78:0x01ba, B:81:0x01c5, B:83:0x01d1, B:85:0x0212, B:87:0x021c, B:88:0x021f, B:90:0x0225, B:92:0x022d, B:93:0x0232, B:95:0x0255, B:97:0x025d, B:99:0x0271, B:101:0x0275, B:102:0x027e, B:104:0x02f4, B:106:0x0308, B:105:0x0303, B:98:0x026e, B:82:0x01cb), top: B:119:0x0150, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0150 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ef A[Catch: all -> 0x031c, TryCatch #3 {, blocks: (B:5:0x0009, B:7:0x0014, B:8:0x001b, B:12:0x003d, B:14:0x0041, B:17:0x0047, B:19:0x004d, B:20:0x006c, B:26:0x009b, B:23:0x0075, B:56:0x014a, B:58:0x014e, B:60:0x0150, B:62:0x0161, B:64:0x0174, B:66:0x017e, B:68:0x0188, B:70:0x0192, B:72:0x019c, B:74:0x01a6, B:76:0x01b0, B:78:0x01ba, B:81:0x01c5, B:83:0x01d1, B:85:0x0212, B:87:0x021c, B:88:0x021f, B:90:0x0225, B:92:0x022d, B:93:0x0232, B:95:0x0255, B:97:0x025d, B:99:0x0271, B:101:0x0275, B:102:0x027e, B:104:0x02f4, B:106:0x0308, B:105:0x0303, B:98:0x026e, B:82:0x01cb, B:110:0x031a, B:109:0x0317, B:11:0x0023, B:27:0x009e, B:29:0x00a2, B:31:0x00ac, B:32:0x00b9, B:34:0x00bf, B:37:0x00c7, B:48:0x00ef, B:50:0x00f9, B:51:0x0101, B:52:0x0120, B:55:0x0128, B:38:0x00ca, B:40:0x00ce, B:41:0x00db, B:43:0x00e1, B:46:0x00ea), top: B:121:0x0009, inners: #0, #1, #2, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x014e A[Catch: all -> 0x031c, DONT_GENERATE, TRY_LEAVE, TryCatch #3 {, blocks: (B:5:0x0009, B:7:0x0014, B:8:0x001b, B:12:0x003d, B:14:0x0041, B:17:0x0047, B:19:0x004d, B:20:0x006c, B:26:0x009b, B:23:0x0075, B:56:0x014a, B:58:0x014e, B:60:0x0150, B:62:0x0161, B:64:0x0174, B:66:0x017e, B:68:0x0188, B:70:0x0192, B:72:0x019c, B:74:0x01a6, B:76:0x01b0, B:78:0x01ba, B:81:0x01c5, B:83:0x01d1, B:85:0x0212, B:87:0x021c, B:88:0x021f, B:90:0x0225, B:92:0x022d, B:93:0x0232, B:95:0x0255, B:97:0x025d, B:99:0x0271, B:101:0x0275, B:102:0x027e, B:104:0x02f4, B:106:0x0308, B:105:0x0303, B:98:0x026e, B:82:0x01cb, B:110:0x031a, B:109:0x0317, B:11:0x0023, B:27:0x009e, B:29:0x00a2, B:31:0x00ac, B:32:0x00b9, B:34:0x00bf, B:37:0x00c7, B:48:0x00ef, B:50:0x00f9, B:51:0x0101, B:52:0x0120, B:55:0x0128, B:38:0x00ca, B:40:0x00ce, B:41:0x00db, B:43:0x00e1, B:46:0x00ea), top: B:121:0x0009, inners: #0, #1, #2, #4 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected boolean initializeCamera(int r11, int r12) {
        /*
            Method dump skipped, instructions count: 799
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.opencv.android.JavaCameraView.initializeCamera(int, int):boolean");
    }

    protected void releaseCamera() {
        synchronized (this) {
            Camera camera = this.mCamera;
            if (camera != null) {
                camera.stopPreview();
                this.mCamera.setPreviewCallback(null);
                this.mCamera.release();
            }
            this.mCamera = null;
            Mat[] matArr = this.mFrameChain;
            if (matArr != null) {
                matArr[0].release();
                this.mFrameChain[1].release();
            }
            JavaCameraFrame[] javaCameraFrameArr = this.mCameraFrame;
            if (javaCameraFrameArr != null) {
                javaCameraFrameArr[0].release();
                this.mCameraFrame[1].release();
            }
        }
    }

    @Override // org.opencv.android.CameraBridgeViewBase
    protected boolean connectCamera(int i, int i2) {
        Log.d(TAG, "Connecting to camera");
        if (initializeCamera(i, i2)) {
            this.mCameraFrameReady = false;
            Log.d(TAG, "Starting processing thread");
            this.mStopThread = false;
            Thread thread = new Thread(new CameraWorker());
            this.mThread = thread;
            thread.start();
            return true;
        }
        return false;
    }

    @Override // org.opencv.android.CameraBridgeViewBase
    protected void disconnectCamera() {
        Log.d(TAG, "Disconnecting from camera");
        try {
            try {
                this.mStopThread = true;
                Log.d(TAG, "Notify thread");
                synchronized (this) {
                    notify();
                }
                Log.d(TAG, "Waiting for thread");
                Thread thread = this.mThread;
                if (thread != null) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.mThread = null;
            releaseCamera();
            this.mCameraFrameReady = false;
        } catch (Throwable th) {
            this.mThread = null;
            throw th;
        }
    }

    @Override // android.hardware.Camera.PreviewCallback
    public void onPreviewFrame(byte[] bArr, Camera camera) {
        synchronized (this) {
            this.mFrameChain[this.mChainIdx].put(0, 0, bArr);
            this.mCameraFrameReady = true;
            notify();
        }
        Camera camera2 = this.mCamera;
        if (camera2 != null) {
            camera2.addCallbackBuffer(this.mBuffer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class JavaCameraFrame implements CameraBridgeViewBase.CvCameraViewFrame {
        private int mHeight;
        private Mat mRgba = new Mat();
        private int mWidth;
        private Mat mYuvFrameData;

        @Override // org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame
        public Mat gray() {
            return this.mYuvFrameData.submat(0, this.mHeight, 0, this.mWidth);
        }

        @Override // org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame
        public Mat rgba() {
            if (JavaCameraView.this.mPreviewFormat != 17) {
                if (JavaCameraView.this.mPreviewFormat == 842094169) {
                    Imgproc.cvtColor(this.mYuvFrameData, this.mRgba, 100, 4);
                } else {
                    throw new IllegalArgumentException("Preview Format can be NV21 or YV12");
                }
            } else {
                Imgproc.cvtColor(this.mYuvFrameData, this.mRgba, 96, 4);
            }
            return this.mRgba;
        }

        public JavaCameraFrame(Mat mat, int i, int i2) {
            this.mWidth = i;
            this.mHeight = i2;
            this.mYuvFrameData = mat;
        }

        public void release() {
            this.mRgba.release();
        }
    }

    /* loaded from: classes2.dex */
    private class CameraWorker implements Runnable {
        private CameraWorker() {
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z;
            do {
                synchronized (JavaCameraView.this) {
                    while (!JavaCameraView.this.mCameraFrameReady && !JavaCameraView.this.mStopThread) {
                        try {
                            JavaCameraView.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    z = false;
                    if (JavaCameraView.this.mCameraFrameReady) {
                        JavaCameraView javaCameraView = JavaCameraView.this;
                        javaCameraView.mChainIdx = 1 - javaCameraView.mChainIdx;
                        JavaCameraView.this.mCameraFrameReady = false;
                        z = true;
                    }
                }
                if (!JavaCameraView.this.mStopThread && z && !JavaCameraView.this.mFrameChain[1 - JavaCameraView.this.mChainIdx].empty()) {
                    JavaCameraView javaCameraView2 = JavaCameraView.this;
                    javaCameraView2.deliverAndDrawFrame(javaCameraView2.mCameraFrame[1 - JavaCameraView.this.mChainIdx]);
                }
            } while (!JavaCameraView.this.mStopThread);
            Log.d(JavaCameraView.TAG, "Finish processing thread");
        }
    }
}
