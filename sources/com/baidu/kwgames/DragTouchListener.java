package com.baidu.kwgames;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/* loaded from: classes.dex */
public class DragTouchListener implements View.OnTouchListener {
    public static final int FREE = 2;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private float curX;
    private float curY;
    private int direct;
    private final GestureDetector gestureDetector;
    private boolean isFinish;
    private OnDragListener listener;
    private int maxDistance;
    private float startX;
    private float startY;
    private float tranX;
    private float tranY;
    private final View view;

    /* loaded from: classes.dex */
    public interface OnDragListener {
        void onClick(View view);

        void onDragFinish(View view);

        void onDragging(View view);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    private @interface OrientationMode {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DragTouchListener(View view) {
        this.direct = 2;
        this.maxDistance = -1;
        this.isFinish = false;
        this.view = view;
        this.gestureDetector = new GestureDetector(view.getContext(), new SingleTapConfirm());
    }

    DragTouchListener(View view, int i) {
        this.maxDistance = -1;
        this.isFinish = false;
        this.view = view;
        this.direct = i;
        this.gestureDetector = new GestureDetector(view.getContext(), new SingleTapConfirm());
    }

    public void setDirect(int i) {
        this.direct = i;
    }

    public void setAllowedMaxDistance(int i) {
        if (i >= -1) {
            this.maxDistance = i;
        }
    }

    private boolean isInAllowDistance() {
        int i = this.maxDistance;
        if (i == -1) {
            return true;
        }
        int i2 = this.direct;
        if (i2 == 0) {
            return ((float) i) > Math.abs(this.curX - this.startX);
        } else if (i2 == 1) {
            return ((float) i) > Math.abs(this.curY - this.startY);
        } else {
            float f = this.curX - this.startX;
            float f2 = this.curY - this.startY;
            return ((double) i) > Math.sqrt((double) ((f * f) + (f2 * f2)));
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        OnDragListener onDragListener;
        if (this.gestureDetector.onTouchEvent(motionEvent)) {
            this.listener.onClick(view);
            return true;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.startX = motionEvent.getRawX();
            this.startY = motionEvent.getRawY();
            this.tranX = view.getTranslationX();
            this.tranY = view.getTranslationY();
            this.isFinish = false;
        } else if (action != 1) {
            if (action == 2 && !this.isFinish) {
                this.curX = motionEvent.getRawX();
                float rawY = motionEvent.getRawY();
                this.curY = rawY;
                int i = this.direct;
                if (i == 2) {
                    view.setTranslationX((this.tranX + this.curX) - this.startX);
                    view.setTranslationY((this.tranY + this.curY) - this.startY);
                } else if (i == 0) {
                    view.setTranslationX((this.tranX + this.curX) - this.startX);
                } else if (i == 1) {
                    view.setTranslationY((this.tranY + rawY) - this.startY);
                }
                OnDragListener onDragListener2 = this.listener;
                if (onDragListener2 != null) {
                    onDragListener2.onDragging(view);
                }
                if (!isInAllowDistance() && (onDragListener = this.listener) != null) {
                    onDragListener.onDragFinish(view);
                    this.isFinish = true;
                }
            }
        } else {
            OnDragListener onDragListener3 = this.listener;
            if (onDragListener3 != null && !this.isFinish) {
                onDragListener3.onDragFinish(view);
                this.isFinish = true;
            }
        }
        return true;
    }

    public void reset() {
        this.view.setTranslationX(0.0f);
        this.view.setTranslationY(0.0f);
    }

    public void setOnDragListener(OnDragListener onDragListener) {
        this.listener = onDragListener;
    }

    /* loaded from: classes.dex */
    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return true;
        }

        private SingleTapConfirm() {
        }
    }
}
