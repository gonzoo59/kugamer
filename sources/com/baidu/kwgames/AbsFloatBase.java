package com.baidu.kwgames;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
/* loaded from: classes.dex */
public abstract class AbsFloatBase {
    static final int FULLSCREEN_NOT_TOUCHABLE = 2;
    static final int FULLSCREEN_TOUCHABLE = 1;
    public static final String TAG = "AbsFloatBase";
    static final int WRAP_CONTENT_NOT_TOUCHABLE = 4;
    static final int WRAP_CONTENT_TOUCHABLE = 3;
    private boolean mAdded;
    protected Context mContext;
    View mInflate;
    WindowManager.LayoutParams mLayoutParams;
    protected OnShowListener mListener;
    WindowManager mWindowManager;
    protected int mAddX = 0;
    protected int mAddY = 0;
    int mGravity = 17;
    int mViewMode = 2;
    Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean mInvisibleNeed = false;
    private boolean mRequestFocus = false;

    /* loaded from: classes.dex */
    public interface OnShowListener {
        void onEnableTouch(boolean z);

        void onRemove();

        void onSave(byte[] bArr, int i);

        void onShow();
    }

    protected abstract void onAddWindowFailed(Exception exc);

    public AbsFloatBase(Context context) {
        this.mContext = context;
        create();
    }

    public void setInvisibleNeed(boolean z) {
        this.mInvisibleNeed = z;
    }

    public void requestFocus(boolean z) {
        this.mRequestFocus = z;
    }

    public void create() {
        this.mWindowManager = (WindowManager) this.mContext.getApplicationContext().getSystemService("window");
    }

    public synchronized void show() {
        if (this.mInflate == null) {
            throw new IllegalStateException("FloatView can not be null");
        }
        OnShowListener onShowListener = this.mListener;
        if (onShowListener != null) {
            onShowListener.onShow();
        }
        if (this.mAdded) {
            this.mInflate.setVisibility(0);
            return;
        }
        getLayoutParam(this.mViewMode);
        this.mInflate.setVisibility(0);
        try {
            this.mLayoutParams.x = this.mAddX;
            this.mLayoutParams.y = this.mAddY;
            this.mLayoutParams.screenOrientation = 0;
            if (2 == this.mViewMode) {
                this.mLayoutParams.alpha = 0.8f;
            }
            this.mWindowManager.addView(this.mInflate, this.mLayoutParams);
            this.mAdded = true;
        } catch (Exception e) {
            Log.e(TAG, "添加悬浮窗失败！！！！！！请检查悬浮窗权限");
            onAddWindowFailed(e);
        }
    }

    public void hide() {
        View view = this.mInflate;
        if (view != null) {
            view.setVisibility(4);
        }
    }

    public void gone() {
        View view = this.mInflate;
        if (view != null) {
            view.setVisibility(8);
        }
    }

    public void remove() {
        OnShowListener onShowListener = this.mListener;
        if (onShowListener != null) {
            onShowListener.onRemove();
        }
        View view = this.mInflate;
        if (view != null && this.mWindowManager != null) {
            if (view.isAttachedToWindow()) {
                this.mWindowManager.removeView(this.mInflate);
            }
            this.mAdded = false;
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public View inflate(int i) {
        View inflate = View.inflate(this.mContext, i, null);
        this.mInflate = inflate;
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public <T extends View> T findView(int i) {
        View view = this.mInflate;
        if (view != null) {
            return (T) view.findViewById(i);
        }
        return null;
    }

    protected void getLayoutParam(int i) {
        if (i == 1) {
            this.mLayoutParams = FloatWindowParamManager.getFloatLayoutParam(true, true);
        } else if (i == 2) {
            this.mLayoutParams = FloatWindowParamManager.getFloatLayoutParam(true, false);
        } else if (i == 3) {
            this.mLayoutParams = FloatWindowParamManager.getFloatLayoutParam(false, true);
        } else if (i == 4) {
            this.mLayoutParams = FloatWindowParamManager.getFloatLayoutParam(false, false);
        }
        if (this.mRequestFocus) {
            this.mLayoutParams.flags &= -9;
        }
        this.mLayoutParams.gravity = this.mGravity;
    }

    public boolean getVisibility() {
        View view = this.mInflate;
        return view != null && view.getVisibility() == 0;
    }

    public void toggleVisibility() {
        if (this.mInflate != null) {
            if (getVisibility()) {
                if (this.mInvisibleNeed) {
                    hide();
                    return;
                } else {
                    gone();
                    return;
                }
            }
            show();
        }
    }

    public void setListener(OnShowListener onShowListener) {
        this.mListener = onShowListener;
    }
}
