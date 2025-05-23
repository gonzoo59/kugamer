package org.opencv.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.List;
import org.opencv.R;
import org.opencv.core.Mat;
import org.opencv.core.Size;
/* loaded from: classes2.dex */
public abstract class CameraBridgeViewBase extends SurfaceView implements SurfaceHolder.Callback {
    public static final int CAMERA_ID_ANY = -1;
    public static final int CAMERA_ID_BACK = 99;
    public static final int CAMERA_ID_FRONT = 98;
    public static final int GRAY = 2;
    protected static final int MAX_UNSPECIFIED = -1;
    public static final int RGBA = 1;
    private static final int STARTED = 1;
    private static final int STOPPED = 0;
    private static final String TAG = "CameraBridge";
    private Bitmap mCacheBitmap;
    protected int mCameraIndex;
    protected boolean mCameraPermissionGranted;
    protected boolean mEnabled;
    protected FpsMeter mFpsMeter;
    protected int mFrameHeight;
    protected int mFrameWidth;
    private CvCameraViewListener2 mListener;
    protected int mMaxHeight;
    protected int mMaxWidth;
    protected int mPreviewFormat;
    protected float mScale;
    private int mState;
    private boolean mSurfaceExist;
    private final Object mSyncObject;

    /* loaded from: classes2.dex */
    public interface CvCameraViewFrame {
        Mat gray();

        Mat rgba();
    }

    /* loaded from: classes2.dex */
    public interface CvCameraViewListener {
        Mat onCameraFrame(Mat mat);

        void onCameraViewStarted(int i, int i2);

        void onCameraViewStopped();
    }

    /* loaded from: classes2.dex */
    public interface CvCameraViewListener2 {
        Mat onCameraFrame(CvCameraViewFrame cvCameraViewFrame);

        void onCameraViewStarted(int i, int i2);

        void onCameraViewStopped();
    }

    /* loaded from: classes2.dex */
    public interface ListItemAccessor {
        int getHeight(Object obj);

        int getWidth(Object obj);
    }

    private void onEnterStoppedState() {
    }

    private void onExitStoppedState() {
    }

    protected abstract boolean connectCamera(int i, int i2);

    protected abstract void disconnectCamera();

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    public CameraBridgeViewBase(Context context, int i) {
        super(context);
        this.mState = 0;
        this.mSyncObject = new Object();
        this.mScale = 0.0f;
        this.mPreviewFormat = 1;
        this.mCameraPermissionGranted = false;
        this.mFpsMeter = null;
        this.mCameraIndex = i;
        getHolder().addCallback(this);
        this.mMaxWidth = -1;
        this.mMaxHeight = -1;
    }

    public CameraBridgeViewBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mState = 0;
        this.mSyncObject = new Object();
        this.mScale = 0.0f;
        this.mPreviewFormat = 1;
        this.mCameraIndex = -1;
        this.mCameraPermissionGranted = false;
        this.mFpsMeter = null;
        int attributeCount = attributeSet.getAttributeCount();
        Log.d(TAG, "Attr count: " + Integer.valueOf(attributeCount));
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.CameraBridgeViewBase);
        if (obtainStyledAttributes.getBoolean(R.styleable.CameraBridgeViewBase_show_fps, false)) {
            enableFpsMeter();
        }
        this.mCameraIndex = obtainStyledAttributes.getInt(R.styleable.CameraBridgeViewBase_camera_id, -1);
        getHolder().addCallback(this);
        this.mMaxWidth = -1;
        this.mMaxHeight = -1;
        obtainStyledAttributes.recycle();
    }

    public void setCameraIndex(int i) {
        this.mCameraIndex = i;
    }

    /* loaded from: classes2.dex */
    protected class CvCameraViewListenerAdapter implements CvCameraViewListener2 {
        private CvCameraViewListener mOldStyleListener;
        private int mPreviewFormat = 1;

        public CvCameraViewListenerAdapter(CvCameraViewListener cvCameraViewListener) {
            this.mOldStyleListener = cvCameraViewListener;
        }

        @Override // org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
        public void onCameraViewStarted(int i, int i2) {
            this.mOldStyleListener.onCameraViewStarted(i, i2);
        }

        @Override // org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
        public void onCameraViewStopped() {
            this.mOldStyleListener.onCameraViewStopped();
        }

        @Override // org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
        public Mat onCameraFrame(CvCameraViewFrame cvCameraViewFrame) {
            int i = this.mPreviewFormat;
            if (i != 1) {
                if (i == 2) {
                    return this.mOldStyleListener.onCameraFrame(cvCameraViewFrame.gray());
                }
                Log.e(CameraBridgeViewBase.TAG, "Invalid frame format! Only RGBA and Gray Scale are supported!");
                return null;
            }
            return this.mOldStyleListener.onCameraFrame(cvCameraViewFrame.rgba());
        }

        public void setFrameFormat(int i) {
            this.mPreviewFormat = i;
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        Log.d(TAG, "call surfaceChanged event");
        synchronized (this.mSyncObject) {
            if (!this.mSurfaceExist) {
                this.mSurfaceExist = true;
                checkCurrentState();
            } else {
                this.mSurfaceExist = false;
                checkCurrentState();
                this.mSurfaceExist = true;
                checkCurrentState();
            }
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        synchronized (this.mSyncObject) {
            this.mSurfaceExist = false;
            checkCurrentState();
        }
    }

    public void setCameraPermissionGranted() {
        synchronized (this.mSyncObject) {
            this.mCameraPermissionGranted = true;
            checkCurrentState();
        }
    }

    public void enableView() {
        synchronized (this.mSyncObject) {
            this.mEnabled = true;
            checkCurrentState();
        }
    }

    public void disableView() {
        synchronized (this.mSyncObject) {
            this.mEnabled = false;
            checkCurrentState();
        }
    }

    public void enableFpsMeter() {
        if (this.mFpsMeter == null) {
            FpsMeter fpsMeter = new FpsMeter();
            this.mFpsMeter = fpsMeter;
            fpsMeter.setResolution(this.mFrameWidth, this.mFrameHeight);
        }
    }

    public void disableFpsMeter() {
        this.mFpsMeter = null;
    }

    public void setCvCameraViewListener(CvCameraViewListener2 cvCameraViewListener2) {
        this.mListener = cvCameraViewListener2;
    }

    public void setCvCameraViewListener(CvCameraViewListener cvCameraViewListener) {
        CvCameraViewListenerAdapter cvCameraViewListenerAdapter = new CvCameraViewListenerAdapter(cvCameraViewListener);
        cvCameraViewListenerAdapter.setFrameFormat(this.mPreviewFormat);
        this.mListener = cvCameraViewListenerAdapter;
    }

    public void setMaxFrameSize(int i, int i2) {
        this.mMaxWidth = i;
        this.mMaxHeight = i2;
    }

    public void SetCaptureFormat(int i) {
        this.mPreviewFormat = i;
        CvCameraViewListener2 cvCameraViewListener2 = this.mListener;
        if (cvCameraViewListener2 instanceof CvCameraViewListenerAdapter) {
            ((CvCameraViewListenerAdapter) cvCameraViewListener2).setFrameFormat(i);
        }
    }

    private void checkCurrentState() {
        Log.d(TAG, "call checkCurrentState");
        int i = (this.mEnabled && this.mCameraPermissionGranted && this.mSurfaceExist && getVisibility() == 0) ? 1 : 0;
        int i2 = this.mState;
        if (i != i2) {
            processExitState(i2);
            this.mState = i;
            processEnterState(i);
        }
    }

    private void processEnterState(int i) {
        Log.d(TAG, "call processEnterState: " + i);
        if (i == 0) {
            onEnterStoppedState();
            CvCameraViewListener2 cvCameraViewListener2 = this.mListener;
            if (cvCameraViewListener2 != null) {
                cvCameraViewListener2.onCameraViewStopped();
            }
        } else if (i != 1) {
        } else {
            onEnterStartedState();
            CvCameraViewListener2 cvCameraViewListener22 = this.mListener;
            if (cvCameraViewListener22 != null) {
                cvCameraViewListener22.onCameraViewStarted(this.mFrameWidth, this.mFrameHeight);
            }
        }
    }

    private void processExitState(int i) {
        Log.d(TAG, "call processExitState: " + i);
        if (i == 0) {
            onExitStoppedState();
        } else if (i != 1) {
        } else {
            onExitStartedState();
        }
    }

    private void onEnterStartedState() {
        Log.d(TAG, "call onEnterStartedState");
        if (connectCamera(getWidth(), getHeight())) {
            return;
        }
        AlertDialog create = new AlertDialog.Builder(getContext()).create();
        create.setCancelable(false);
        create.setMessage("It seems that you device does not support camera (or it is locked). Application will be closed.");
        create.setButton(-3, "OK", new DialogInterface.OnClickListener() { // from class: org.opencv.android.CameraBridgeViewBase.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ((Activity) CameraBridgeViewBase.this.getContext()).finish();
            }
        });
        create.show();
    }

    private void onExitStartedState() {
        disconnectCamera();
        Bitmap bitmap = this.mCacheBitmap;
        if (bitmap != null) {
            bitmap.recycle();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void deliverAndDrawFrame(CvCameraViewFrame cvCameraViewFrame) {
        Mat rgba;
        Canvas lockCanvas;
        CvCameraViewListener2 cvCameraViewListener2 = this.mListener;
        if (cvCameraViewListener2 != null) {
            rgba = cvCameraViewListener2.onCameraFrame(cvCameraViewFrame);
        } else {
            rgba = cvCameraViewFrame.rgba();
        }
        boolean z = true;
        if (rgba != null) {
            try {
                Utils.matToBitmap(rgba, this.mCacheBitmap);
            } catch (Exception e) {
                Log.e(TAG, "Mat type: " + rgba);
                Log.e(TAG, "Bitmap type: " + this.mCacheBitmap.getWidth() + "*" + this.mCacheBitmap.getHeight());
                StringBuilder sb = new StringBuilder();
                sb.append("Utils.matToBitmap() throws an exception: ");
                sb.append(e.getMessage());
                Log.e(TAG, sb.toString());
                z = false;
            }
        }
        if (!z || this.mCacheBitmap == null || (lockCanvas = getHolder().lockCanvas()) == null) {
            return;
        }
        lockCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        if (this.mScale != 0.0f) {
            lockCanvas.drawBitmap(this.mCacheBitmap, new Rect(0, 0, this.mCacheBitmap.getWidth(), this.mCacheBitmap.getHeight()), new Rect((int) ((lockCanvas.getWidth() - (this.mScale * this.mCacheBitmap.getWidth())) / 2.0f), (int) ((lockCanvas.getHeight() - (this.mScale * this.mCacheBitmap.getHeight())) / 2.0f), (int) (((lockCanvas.getWidth() - (this.mScale * this.mCacheBitmap.getWidth())) / 2.0f) + (this.mScale * this.mCacheBitmap.getWidth())), (int) (((lockCanvas.getHeight() - (this.mScale * this.mCacheBitmap.getHeight())) / 2.0f) + (this.mScale * this.mCacheBitmap.getHeight()))), (Paint) null);
        } else {
            lockCanvas.drawBitmap(this.mCacheBitmap, new Rect(0, 0, this.mCacheBitmap.getWidth(), this.mCacheBitmap.getHeight()), new Rect((lockCanvas.getWidth() - this.mCacheBitmap.getWidth()) / 2, (lockCanvas.getHeight() - this.mCacheBitmap.getHeight()) / 2, ((lockCanvas.getWidth() - this.mCacheBitmap.getWidth()) / 2) + this.mCacheBitmap.getWidth(), ((lockCanvas.getHeight() - this.mCacheBitmap.getHeight()) / 2) + this.mCacheBitmap.getHeight()), (Paint) null);
        }
        FpsMeter fpsMeter = this.mFpsMeter;
        if (fpsMeter != null) {
            fpsMeter.measure();
            this.mFpsMeter.draw(lockCanvas, 20.0f, 30.0f);
        }
        getHolder().unlockCanvasAndPost(lockCanvas);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void AllocateCache() {
        this.mCacheBitmap = Bitmap.createBitmap(this.mFrameWidth, this.mFrameHeight, Bitmap.Config.ARGB_8888);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Size calculateCameraFrameSize(List<?> list, ListItemAccessor listItemAccessor, int i, int i2) {
        int i3 = this.mMaxWidth;
        if (i3 != -1 && i3 < i) {
            i = i3;
        }
        int i4 = this.mMaxHeight;
        if (i4 != -1 && i4 < i2) {
            i2 = i4;
        }
        int i5 = 0;
        int i6 = 0;
        for (Object obj : list) {
            int width = listItemAccessor.getWidth(obj);
            int height = listItemAccessor.getHeight(obj);
            Log.d(TAG, "trying size: " + width + "x" + height);
            if (width <= i && height <= i2 && width >= i5 && height >= i6) {
                i6 = height;
                i5 = width;
            }
        }
        if ((i5 == 0 || i6 == 0) && list.size() > 0) {
            Log.i(TAG, "fallback to the first frame size");
            Object obj2 = list.get(0);
            i5 = listItemAccessor.getWidth(obj2);
            i6 = listItemAccessor.getHeight(obj2);
        }
        return new Size(i5, i6);
    }
}
