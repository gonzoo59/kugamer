package com.ajguan.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Scroller;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.ajguan.library.exception.ERVHRuntimeException;
import com.ajguan.library.view.SimpleLoadMoreView;
import com.ajguan.library.view.SimpleRefreshHeaderView;
/* loaded from: classes.dex */
public class EasyRefreshLayout extends ViewGroup {
    private static final float DRAG_RATE = 1.0f;
    private static final int INVALID_POINTER = -1;
    private static long SCROLL_TO_LOADING_DURATION = 500;
    private static int SCROLL_TO_REFRESH_DURATION = 250;
    private static int SCROLL_TO_TOP_DURATION = 800;
    private static long SHOW_COMPLETED_TIME = 500;
    private static long SHOW_SCROLL_DOWN_DURATION = 300;
    private static final int START_POSITION = 0;
    private static final String TAG = "EsayRefreshLayout";
    private int activePointerId;
    private int advanceCount;
    private Runnable autoRefreshRunnable;
    private AutoScroll autoScroll;
    private View contentView;
    private int currentOffsetTop;
    private Runnable delayToScrollTopRunnable;
    private EasyEvent easyEvent;
    private boolean hasMeasureHeaderView;
    private boolean hasMeasureLoadMoreView;
    private boolean hasSendCancelEvent;
    private int headerViewHight;
    private float initDownX;
    private float initDownY;
    private boolean isAutoRefresh;
    private boolean isBeginDragged;
    boolean isCanLoad;
    private boolean isEnablePullToRefresh;
    private boolean isLoading;
    private boolean isLoadingFail;
    private boolean isNotMoreLoading;
    private boolean isRecycerView;
    private boolean isRefreshing;
    private boolean isTouch;
    private MotionEvent lastEvent;
    private float lastMotionX;
    private float lastMotionY;
    private int lastOffsetTop;
    private LoadModel loadMoreModel;
    private int loadMoreViewHeight;
    private float mDistance;
    private LayoutInflater mInflater;
    private View mLoadMoreView;
    private RecyclerView mRecyclerView;
    private float offsetY;
    private double pull_resistance;
    private View refreshHeaderView;
    private State state;
    private int totalDragDistance;
    private int touchSlop;
    private float yDiff;

    /* loaded from: classes.dex */
    public interface EasyEvent extends OnRefreshListener, LoadMoreEvent {
    }

    /* loaded from: classes.dex */
    public interface Event {
        void complete();
    }

    /* loaded from: classes.dex */
    public interface LoadMoreEvent {
        void onLoadMore();
    }

    /* loaded from: classes.dex */
    public interface OnRefreshListener {
        void onRefreshing();
    }

    public EasyRefreshLayout(Context context) {
        this(context, null);
    }

    public EasyRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.pull_resistance = 2.0d;
        this.state = State.RESET;
        this.isEnablePullToRefresh = true;
        this.hasMeasureHeaderView = false;
        this.isCanLoad = false;
        this.isLoading = false;
        this.isLoadingFail = false;
        this.loadMoreModel = LoadModel.COMMON_MODEL;
        this.advanceCount = 0;
        this.delayToScrollTopRunnable = new Runnable() { // from class: com.ajguan.library.EasyRefreshLayout.1
            @Override // java.lang.Runnable
            public void run() {
                EasyRefreshLayout.this.autoScroll.scrollTo(0, EasyRefreshLayout.SCROLL_TO_TOP_DURATION);
            }
        };
        this.autoRefreshRunnable = new Runnable() { // from class: com.ajguan.library.EasyRefreshLayout.2
            @Override // java.lang.Runnable
            public void run() {
                EasyRefreshLayout.this.isAutoRefresh = true;
                EasyRefreshLayout.this.changeState(State.PULL);
                EasyRefreshLayout.this.autoScroll.scrollTo(EasyRefreshLayout.this.totalDragDistance, EasyRefreshLayout.SCROLL_TO_REFRESH_DURATION);
            }
        };
        initParameter(context, attributeSet);
    }

    private void initParameter(Context context, AttributeSet attributeSet) {
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setRefreshHeadView(getDefaultRefreshView());
        setLoadMoreView(getDefaultLoadMoreView());
        this.autoScroll = new AutoScroll();
    }

    public void setRefreshHeadView(View view) {
        View view2;
        if (view != null && view != (view2 = this.refreshHeaderView)) {
            removeView(view2);
        }
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        }
        this.refreshHeaderView = view;
        addView(view);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.contentView == null) {
            initContentView();
        }
        if (this.contentView == null) {
            return;
        }
        this.contentView.measure(View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
        measureChild(this.refreshHeaderView, i, i2);
        if (!this.hasMeasureHeaderView) {
            this.hasMeasureHeaderView = true;
            int measuredHeight = this.refreshHeaderView.getMeasuredHeight();
            this.headerViewHight = measuredHeight;
            this.totalDragDistance = measuredHeight;
        }
        measureChild(this.mLoadMoreView, i, i2);
        if (this.hasMeasureLoadMoreView) {
            return;
        }
        this.hasMeasureLoadMoreView = true;
        this.loadMoreViewHeight = this.mLoadMoreView.getMeasuredHeight();
    }

    private void initContentView() {
        if (this.contentView == null) {
            int i = 0;
            while (true) {
                if (i >= getChildCount()) {
                    break;
                }
                View childAt = getChildAt(i);
                if (childAt.equals(this.refreshHeaderView) || childAt.equals(this.mLoadMoreView)) {
                    i++;
                } else {
                    this.contentView = childAt;
                    if (childAt instanceof RecyclerView) {
                        this.isRecycerView = true;
                    } else {
                        this.isRecycerView = false;
                    }
                }
            }
        }
        if (this.isRecycerView) {
            initERVH();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (getChildCount() == 0) {
            throw new RuntimeException("child view can not be empty");
        }
        if (this.contentView == null) {
            initContentView();
        }
        View view = this.contentView;
        if (view == null) {
            throw new RuntimeException("main content of view can not be empty ");
        }
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop() + this.currentOffsetTop;
        int paddingLeft2 = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        int paddingTop2 = (measuredHeight - getPaddingTop()) - getPaddingBottom();
        view.layout(paddingLeft, paddingTop, paddingLeft2 + paddingLeft, paddingTop + paddingTop2);
        int i5 = measuredWidth / 2;
        int measuredWidth2 = this.refreshHeaderView.getMeasuredWidth() / 2;
        int i6 = this.currentOffsetTop;
        this.refreshHeaderView.layout(i5 - measuredWidth2, (-this.headerViewHight) + i6, measuredWidth2 + i5, i6);
        int measuredWidth3 = this.mLoadMoreView.getMeasuredWidth() / 2;
        this.mLoadMoreView.layout(i5 - measuredWidth3, paddingTop2, i5 + measuredWidth3, this.loadMoreViewHeight + paddingTop2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.isLoading || this.contentView == null) {
            return super.dispatchTouchEvent(motionEvent);
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mDistance = 0.0f;
            this.activePointerId = motionEvent.getPointerId(0);
            this.isTouch = true;
            this.hasSendCancelEvent = false;
            this.isBeginDragged = false;
            this.lastOffsetTop = this.currentOffsetTop;
            this.currentOffsetTop = this.contentView.getTop();
            float x = motionEvent.getX(0);
            this.lastMotionX = x;
            this.initDownX = x;
            float y = motionEvent.getY(0);
            this.lastMotionY = y;
            this.initDownY = y;
            this.autoScroll.stop();
            removeCallbacks(this.delayToScrollTopRunnable);
            removeCallbacks(this.autoRefreshRunnable);
            super.dispatchTouchEvent(motionEvent);
            return true;
        }
        if (actionMasked != 1) {
            if (actionMasked != 2) {
                if (actionMasked != 3) {
                    if (actionMasked == 5) {
                        int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
                        if (actionIndex < 0) {
                            return super.dispatchTouchEvent(motionEvent);
                        }
                        this.lastMotionX = motionEvent.getX(actionIndex);
                        this.lastMotionY = motionEvent.getY(actionIndex);
                        this.activePointerId = MotionEventCompat.getPointerId(motionEvent, actionIndex);
                    } else if (actionMasked == 6) {
                        onSecondaryPointerUp(motionEvent);
                        this.lastMotionY = motionEvent.getY(motionEvent.findPointerIndex(this.activePointerId));
                        this.lastMotionX = motionEvent.getX(motionEvent.findPointerIndex(this.activePointerId));
                    }
                }
            } else if (this.activePointerId == -1) {
                return super.dispatchTouchEvent(motionEvent);
            } else {
                this.autoScroll.stop();
                this.lastEvent = motionEvent;
                float x2 = motionEvent.getX(MotionEventCompat.findPointerIndex(motionEvent, this.activePointerId));
                float y2 = motionEvent.getY(MotionEventCompat.findPointerIndex(motionEvent, this.activePointerId));
                float f = y2 - this.lastMotionY;
                this.yDiff = f;
                this.mDistance += f;
                this.offsetY = f * 1.0f;
                this.lastMotionX = x2;
                this.lastMotionY = y2;
                if (Math.abs(x2 - this.lastMotionX) <= this.touchSlop) {
                    if (!this.isBeginDragged && Math.abs(y2 - this.initDownY) > this.touchSlop) {
                        this.isBeginDragged = true;
                    }
                    if (this.isBeginDragged) {
                        boolean z = this.offsetY > 0.0f;
                        boolean z2 = !canChildScrollUp();
                        boolean z3 = !z;
                        boolean z4 = this.currentOffsetTop > 0;
                        if ((z && z2) || (z3 && z4)) {
                            moveSpinner(this.offsetY);
                            return true;
                        }
                    }
                }
            }
            return super.dispatchTouchEvent(motionEvent);
        }
        if (this.currentOffsetTop > 0) {
            finishSpinner();
        }
        this.isTouch = false;
        this.activePointerId = -1;
        return super.dispatchTouchEvent(motionEvent);
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
        if (MotionEventCompat.getPointerId(motionEvent, actionIndex) == this.activePointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.lastMotionY = motionEvent.getY(i);
            this.lastMotionX = motionEvent.getX(i);
            this.activePointerId = MotionEventCompat.getPointerId(motionEvent, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void moveSpinner(float f) {
        int round;
        int i;
        if (this.isEnablePullToRefresh && (round = Math.round(f)) != 0) {
            if (!this.hasSendCancelEvent && this.isTouch && this.currentOffsetTop > 0) {
                sendCancelEvent();
                this.hasSendCancelEvent = true;
            }
            int max = Math.max(0, this.currentOffsetTop + round);
            int i2 = max - this.currentOffsetTop;
            float f2 = this.totalDragDistance;
            double max2 = Math.max(0.0f, Math.min(max - i, 2.0f * f2) / f2);
            float pow = (float) (max2 - Math.pow(max2 / this.pull_resistance, 2.0d));
            if (i2 > 0) {
                i2 = (int) (i2 * (1.0f - pow));
                max = Math.max(0, this.currentOffsetTop + i2);
            }
            if (this.state == State.RESET && this.currentOffsetTop == 0 && max > 0) {
                if (this.isNotMoreLoading || this.isLoadingFail) {
                    closeLoadView();
                }
                changeState(State.PULL);
            }
            if (this.currentOffsetTop > 0 && max <= 0 && (this.state == State.PULL || this.state == State.COMPLETE)) {
                changeState(State.RESET);
            }
            if (this.state == State.PULL && !this.isTouch) {
                int i3 = this.currentOffsetTop;
                int i4 = this.totalDragDistance;
                if (i3 > i4 && max <= i4) {
                    this.autoScroll.stop();
                    changeState(State.REFRESHING);
                    EasyEvent easyEvent = this.easyEvent;
                    if (easyEvent != null) {
                        this.isRefreshing = true;
                        easyEvent.onRefreshing();
                    }
                    i2 += this.totalDragDistance - max;
                }
            }
            setTargetOffsetTopAndBottom(i2);
            View view = this.refreshHeaderView;
            if (view instanceof IRefreshHeader) {
                ((IRefreshHeader) view).onPositionChange(this.currentOffsetTop, this.lastOffsetTop, this.totalDragDistance, this.isTouch, this.state);
            }
        }
    }

    private void finishSpinner() {
        if (this.state == State.REFRESHING) {
            int i = this.currentOffsetTop;
            int i2 = this.totalDragDistance;
            if (i > i2) {
                this.autoScroll.scrollTo(i2, SCROLL_TO_REFRESH_DURATION);
                return;
            }
            return;
        }
        this.autoScroll.scrollTo(0, SCROLL_TO_TOP_DURATION);
    }

    private boolean canChildScrollUp() {
        if (Build.VERSION.SDK_INT < 14) {
            View view = this.contentView;
            if (!(view instanceof AbsListView)) {
                return ViewCompat.canScrollVertically(view, -1) || this.contentView.getScrollY() > 0;
            }
            AbsListView absListView = (AbsListView) view;
            return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
        }
        return ViewCompat.canScrollVertically(this.contentView, -1);
    }

    private void setTargetOffsetTopAndBottom(int i) {
        if (i == 0) {
            return;
        }
        this.contentView.offsetTopAndBottom(i);
        this.refreshHeaderView.offsetTopAndBottom(i);
        this.lastOffsetTop = this.currentOffsetTop;
        this.currentOffsetTop = this.contentView.getTop();
        invalidate();
    }

    private void sendCancelEvent() {
        MotionEvent motionEvent = this.lastEvent;
        if (motionEvent == null) {
            return;
        }
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        super.dispatchTouchEvent(obtain);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeState(State state) {
        this.state = state;
        View view = this.refreshHeaderView;
        IRefreshHeader iRefreshHeader = view instanceof IRefreshHeader ? (IRefreshHeader) view : null;
        if (iRefreshHeader != null) {
            int i = AnonymousClass10.$SwitchMap$com$ajguan$library$State[state.ordinal()];
            if (i == 1) {
                iRefreshHeader.reset();
            } else if (i == 2) {
                iRefreshHeader.pull();
            } else if (i == 3) {
                iRefreshHeader.refreshing();
            } else if (i != 4) {
            } else {
                iRefreshHeader.complete();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.ajguan.library.EasyRefreshLayout$10  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$com$ajguan$library$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$com$ajguan$library$State = iArr;
            try {
                iArr[State.RESET.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ajguan$library$State[State.PULL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ajguan$library$State[State.REFRESHING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ajguan$library$State[State.COMPLETE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void refreshComplete() {
        this.isRefreshing = false;
        changeState(State.COMPLETE);
        if (this.currentOffsetTop == 0) {
            changeState(State.RESET);
        } else if (this.isTouch) {
        } else {
            postDelayed(this.delayToScrollTopRunnable, SHOW_COMPLETED_TIME);
        }
    }

    public void autoRefresh() {
        autoRefresh(500L);
    }

    public void autoRefresh(long j) {
        if (this.state != State.RESET) {
            return;
        }
        postDelayed(this.autoRefreshRunnable, j);
    }

    public View getDefaultRefreshView() {
        return new SimpleRefreshHeaderView(getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class AutoScroll implements Runnable {
        private int lastY;
        private Scroller scroller;

        public AutoScroll() {
            this.scroller = new Scroller(EasyRefreshLayout.this.getContext());
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!(!this.scroller.computeScrollOffset() || this.scroller.isFinished())) {
                int currY = this.scroller.getCurrY();
                int i = currY - this.lastY;
                this.lastY = currY;
                EasyRefreshLayout.this.moveSpinner(i);
                EasyRefreshLayout.this.post(this);
                EasyRefreshLayout.this.onScrollFinish(false);
                return;
            }
            stop();
            EasyRefreshLayout.this.onScrollFinish(true);
        }

        public void scrollTo(int i, int i2) {
            int i3 = i - EasyRefreshLayout.this.currentOffsetTop;
            stop();
            if (i3 == 0) {
                return;
            }
            this.scroller.startScroll(0, 0, 0, i3, i2);
            EasyRefreshLayout.this.post(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void stop() {
            EasyRefreshLayout.this.removeCallbacks(this);
            if (!this.scroller.isFinished()) {
                this.scroller.forceFinished(true);
            }
            this.lastY = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScrollFinish(boolean z) {
        if (!this.isAutoRefresh || z) {
            return;
        }
        this.isAutoRefresh = false;
        changeState(State.REFRESHING);
        EasyEvent easyEvent = this.easyEvent;
        if (easyEvent != null) {
            easyEvent.onRefreshing();
        }
        finishSpinner();
    }

    public void addEasyEvent(EasyEvent easyEvent) {
        if (easyEvent == null) {
            throw new ERVHRuntimeException("adapter can not be null");
        }
        this.easyEvent = easyEvent;
    }

    public boolean isEnablePullToRefresh() {
        return this.isEnablePullToRefresh;
    }

    public void setEnablePullToRefresh(boolean z) {
        this.isEnablePullToRefresh = z;
    }

    public boolean isRefreshing() {
        return this.isRefreshing;
    }

    public void setRefreshing(boolean z) {
        if (z) {
            changeState(State.REFRESHING);
            if (this.isNotMoreLoading || this.isLoadingFail) {
                closeLoadView();
            }
        }
        changeState(State.RESET);
    }

    private void initERVH() {
        if (this.mLoadMoreView == null) {
            getDefaultLoadMoreView();
            setLoadMoreView(this.mLoadMoreView);
        }
        if (this.isRecycerView) {
            RecyclerView recyclerView = (RecyclerView) this.contentView;
            this.mRecyclerView = recyclerView;
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.ajguan.library.EasyRefreshLayout.3
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView2, int i, int i2) {
                    super.onScrolled(recyclerView2, i, i2);
                    if (EasyRefreshLayout.this.loadMoreModel != LoadModel.ADVANCE_MODEL || EasyRefreshLayout.this.isLoading || EasyRefreshLayout.this.isRefreshing || EasyRefreshLayout.this.isLoadingFail || EasyRefreshLayout.this.isNotMoreLoading) {
                        return;
                    }
                    int lastVisiBleItem = EasyRefreshLayout.this.getLastVisiBleItem();
                    int itemCount = EasyRefreshLayout.this.mRecyclerView.getLayoutManager().getItemCount();
                    int childCount = EasyRefreshLayout.this.mRecyclerView.getLayoutManager().getChildCount();
                    if (childCount > 0 && lastVisiBleItem >= (itemCount - 1) - EasyRefreshLayout.this.advanceCount && itemCount >= childCount) {
                        EasyRefreshLayout.this.isCanLoad = true;
                    }
                    if (EasyRefreshLayout.this.isCanLoad) {
                        EasyRefreshLayout.this.isCanLoad = false;
                        EasyRefreshLayout.this.isLoading = true;
                        if (EasyRefreshLayout.this.easyEvent != null) {
                            EasyRefreshLayout.this.easyEvent.onLoadMore();
                        }
                    }
                }

                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(RecyclerView recyclerView2, int i) {
                    super.onScrollStateChanged(recyclerView2, i);
                    if (EasyRefreshLayout.this.loadMoreModel == LoadModel.ADVANCE_MODEL || Math.abs(EasyRefreshLayout.this.mDistance) <= EasyRefreshLayout.this.touchSlop || EasyRefreshLayout.this.mDistance >= 0.0f || EasyRefreshLayout.this.isLoading || EasyRefreshLayout.this.loadMoreModel != LoadModel.COMMON_MODEL || EasyRefreshLayout.this.isRefreshing || EasyRefreshLayout.this.isLoadingFail || EasyRefreshLayout.this.isNotMoreLoading) {
                        return;
                    }
                    int lastVisiBleItem = EasyRefreshLayout.this.getLastVisiBleItem();
                    int itemCount = EasyRefreshLayout.this.mRecyclerView.getLayoutManager().getItemCount();
                    int childCount = EasyRefreshLayout.this.mRecyclerView.getLayoutManager().getChildCount();
                    if (childCount > 0 && lastVisiBleItem >= itemCount - 1 && itemCount >= childCount) {
                        EasyRefreshLayout.this.isCanLoad = true;
                    }
                    if (EasyRefreshLayout.this.isCanLoad) {
                        EasyRefreshLayout.this.isCanLoad = false;
                        EasyRefreshLayout.this.isLoading = true;
                        ((ILoadMoreView) EasyRefreshLayout.this.mLoadMoreView).reset();
                        EasyRefreshLayout.this.mLoadMoreView.measure(0, 0);
                        ((ILoadMoreView) EasyRefreshLayout.this.mLoadMoreView).loading();
                        EasyRefreshLayout.this.showLoadView();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoadView() {
        ValueAnimator ofInt = ValueAnimator.ofInt(0, -this.mLoadMoreView.getMeasuredHeight());
        ofInt.setTarget(this.mLoadMoreView);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ajguan.library.EasyRefreshLayout.4
            private int lastDs;

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                this.lastDs = intValue;
                EasyRefreshLayout.this.mLoadMoreView.bringToFront();
                EasyRefreshLayout.this.mLoadMoreView.setTranslationY(intValue);
            }
        });
        ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.ajguan.library.EasyRefreshLayout.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (EasyRefreshLayout.this.easyEvent != null) {
                    EasyRefreshLayout.this.easyEvent.onLoadMore();
                }
            }
        });
        ofInt.setDuration(SCROLL_TO_LOADING_DURATION);
        ofInt.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideLoadView() {
        View view = this.mLoadMoreView;
        if (view == null || !this.isRecycerView) {
            return;
        }
        ValueAnimator ofInt = ValueAnimator.ofInt(0, view.getMeasuredHeight());
        ofInt.setTarget(this.mLoadMoreView);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ajguan.library.EasyRefreshLayout.6
            private int lastDs;

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                this.lastDs = intValue;
                EasyRefreshLayout.this.mLoadMoreView.bringToFront();
                EasyRefreshLayout.this.mLoadMoreView.setTranslationY(intValue);
            }
        });
        ofInt.addListener(new Animator.AnimatorListener() { // from class: com.ajguan.library.EasyRefreshLayout.7
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                EasyRefreshLayout.this.isLoading = false;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                EasyRefreshLayout.this.isLoading = false;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
                EasyRefreshLayout.this.isLoading = false;
            }
        });
        ofInt.setDuration(SHOW_SCROLL_DOWN_DURATION);
        ofInt.start();
    }

    public void closeLoadView() {
        if (this.loadMoreModel == LoadModel.ADVANCE_MODEL) {
            throw new RuntimeException("enableAdance Model cant not called closeLoadView method");
        }
        View view = this.mLoadMoreView;
        if (view == null || !this.isRecycerView) {
            return;
        }
        view.bringToFront();
        View view2 = this.mLoadMoreView;
        view2.setTranslationY(view2.getMeasuredHeight());
        resetLoadMoreState();
    }

    public View getLoadMoreView() {
        return getDefaultLoadMoreView();
    }

    public void setLoadMoreView(View view) {
        View view2;
        if (view == null) {
            throw new ERVHRuntimeException("loadMoreView can not be null");
        }
        if (view != null && view != (view2 = this.mLoadMoreView)) {
            removeView(view2);
        }
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        }
        this.mLoadMoreView = view;
        addView(view);
        resetLoadMoreState();
        ((ILoadMoreView) this.mLoadMoreView).reset();
        ((ILoadMoreView) this.mLoadMoreView).getCanClickFailView().setOnClickListener(new View.OnClickListener() { // from class: com.ajguan.library.EasyRefreshLayout.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view3) {
                if (!EasyRefreshLayout.this.isLoadingFail || EasyRefreshLayout.this.easyEvent == null) {
                    return;
                }
                EasyRefreshLayout.this.isLoading = true;
                ((ILoadMoreView) EasyRefreshLayout.this.mLoadMoreView).loading();
                EasyRefreshLayout.this.easyEvent.onLoadMore();
            }
        });
    }

    public void loadMoreComplete() {
        if (this.loadMoreModel == LoadModel.ADVANCE_MODEL) {
            this.isLoading = false;
        } else if (this.loadMoreModel == LoadModel.COMMON_MODEL) {
            loadMoreComplete(null);
        }
    }

    @Deprecated
    public void loadMoreComplete(Event event) {
        if (this.loadMoreModel == LoadModel.ADVANCE_MODEL) {
            throw new RuntimeException("enableAdance Model cant not called closeLoadView method");
        }
        loadMoreComplete(event, 500L);
    }

    @Deprecated
    public void loadMoreComplete(final Event event, long j) {
        if (this.loadMoreModel == LoadModel.ADVANCE_MODEL) {
            throw new RuntimeException("enableAdance Model cant not called closeLoadView method");
        }
        ((ILoadMoreView) this.mLoadMoreView).loadComplete();
        if (event == null) {
            hideLoadView();
            resetLoadMoreState();
            return;
        }
        new Handler().postDelayed(new Runnable() { // from class: com.ajguan.library.EasyRefreshLayout.9
            @Override // java.lang.Runnable
            public void run() {
                event.complete();
                EasyRefreshLayout.this.hideLoadView();
                EasyRefreshLayout.this.resetLoadMoreState();
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetLoadMoreState() {
        this.isCanLoad = false;
        this.isLoading = false;
        this.isLoadingFail = false;
        this.isNotMoreLoading = false;
    }

    public void loadMoreFail() {
        if (this.loadMoreModel == LoadModel.ADVANCE_MODEL) {
            throw new RuntimeException("enableAdance Model cant not called closeLoadView method");
        }
        ((ILoadMoreView) this.mLoadMoreView).loadFail();
        resetLoadMoreState();
        this.isLoadingFail = true;
    }

    public void loadNothing() {
        if (this.loadMoreModel == LoadModel.ADVANCE_MODEL) {
            throw new RuntimeException("enableAdance Model cant not called closeLoadView method");
        }
        ((ILoadMoreView) this.mLoadMoreView).loadNothing();
        resetLoadMoreState();
        this.isNotMoreLoading = true;
    }

    private View getDefaultLoadMoreView() {
        return new SimpleLoadMoreView(getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getLastVisiBleItem() {
        char c;
        RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            c = 1;
        } else if (layoutManager instanceof LinearLayoutManager) {
            c = 0;
        } else if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
            throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
        } else {
            c = 2;
        }
        if (c != 0) {
            if (c != 1) {
                if (c != 2) {
                    return -1;
                }
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                int[] iArr = new int[staggeredGridLayoutManager.getSpanCount()];
                staggeredGridLayoutManager.findLastVisibleItemPositions(iArr);
                return findMax(iArr);
            }
            return ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
    }

    private int findMax(int[] iArr) {
        int i = iArr[0];
        for (int i2 : iArr) {
            if (i2 > i) {
                i = i2;
            }
        }
        return i;
    }

    public boolean isEnableLoadMore() {
        return this.loadMoreModel == LoadModel.COMMON_MODEL || this.loadMoreModel == LoadModel.ADVANCE_MODEL;
    }

    public LoadModel getLoadMoreModel() {
        return this.loadMoreModel;
    }

    public void setLoadMoreModel(LoadModel loadModel, int i) {
        this.loadMoreModel = loadModel;
        this.advanceCount = i;
    }

    public int getAdvanceCount() {
        return this.advanceCount;
    }

    public void setAdvanceCount(int i) {
        this.advanceCount = i;
    }

    public void setLoadMoreModel(LoadModel loadModel) {
        setLoadMoreModel(loadModel, 0);
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public long getShowLoadViewAnimatorDuration() {
        return SCROLL_TO_LOADING_DURATION;
    }

    public void setShowLoadViewAnimatorDuration(long j) {
        SCROLL_TO_LOADING_DURATION = j;
    }

    public int getScrollToRefreshDuration() {
        return SCROLL_TO_REFRESH_DURATION;
    }

    public void setScrollToRefreshDuration(int i) {
        SCROLL_TO_REFRESH_DURATION = i;
    }

    public int getScrollToTopDuration() {
        return SCROLL_TO_TOP_DURATION;
    }

    public void setScrollToTopDuration(int i) {
        SCROLL_TO_TOP_DURATION = i;
    }

    public long getHideLoadViewAnimatorDuration() {
        return SHOW_COMPLETED_TIME;
    }

    public void setHideLoadViewAnimatorDuration(long j) {
        SHOW_COMPLETED_TIME = j;
    }

    public double getPullResistance() {
        return this.pull_resistance;
    }

    public void setPullResistance(double d) {
        this.pull_resistance = d;
    }
}
