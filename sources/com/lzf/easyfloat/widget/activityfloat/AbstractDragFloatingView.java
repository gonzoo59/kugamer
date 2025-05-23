package com.lzf.easyfloat.widget.activityfloat;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.lzf.easyfloat.anim.AnimatorManager;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.FloatCallbacks;
import com.lzf.easyfloat.interfaces.OnFloatAnimator;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.lzf.easyfloat.utils.Logger;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: AbstractDragFloatingView.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010 \u001a\u00020!H\u0002J\r\u0010\"\u001a\u00020!H\u0000¢\u0006\u0002\b#J\u000f\u0010$\u001a\u0004\u0018\u00010\u0007H&¢\u0006\u0002\u0010%J\b\u0010&\u001a\u00020!H\u0002J\b\u0010'\u001a\u00020!H\u0002J\u0010\u0010(\u001a\u00020!2\u0006\u0010\u0002\u001a\u00020\u0003H\u0004J\b\u0010)\u001a\u00020!H\u0014J\u0012\u0010*\u001a\u00020\u00132\b\u0010+\u001a\u0004\u0018\u00010,H\u0016J0\u0010-\u001a\u00020!2\u0006\u0010.\u001a\u00020\u00132\u0006\u0010/\u001a\u00020\u00072\u0006\u00100\u001a\u00020\u00072\u0006\u00101\u001a\u00020\u00072\u0006\u00102\u001a\u00020\u0007H\u0015J\u0012\u00103\u001a\u00020\u00132\b\u0010+\u001a\u0004\u0018\u00010,H\u0016J\u0010\u00104\u001a\u00020!2\u0006\u00105\u001a\u000206H&J\b\u00107\u001a\u00020!H\u0002J$\u00108\u001a\u000e\u0012\u0004\u0012\u00020:\u0012\u0004\u0012\u00020:092\u0006\u0010;\u001a\u00020:2\u0006\u0010<\u001a\u00020:H\u0002J\b\u0010=\u001a\u00020!H\u0002J\u0010\u0010>\u001a\u00020!2\u0006\u0010+\u001a\u00020,H\u0002R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006?"}, d2 = {"Lcom/lzf/easyfloat/widget/activityfloat/AbstractDragFloatingView;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "bottomDistance", "config", "Lcom/lzf/easyfloat/data/FloatConfig;", "getConfig", "()Lcom/lzf/easyfloat/data/FloatConfig;", "setConfig", "(Lcom/lzf/easyfloat/data/FloatConfig;)V", "floatRect", "Landroid/graphics/Rect;", "isCreated", "", "lastX", "lastY", "leftDistance", "minX", "minY", "parentHeight", "parentRect", "parentView", "Landroid/view/ViewGroup;", "parentWidth", "rightDistance", "topDistance", "enterAnim", "", "exitAnim", "exitAnim$easyfloat_release", "getLayoutId", "()Ljava/lang/Integer;", "initDistanceValue", "initParent", "initView", "onDetachedFromWindow", "onInterceptTouchEvent", "event", "Landroid/view/MotionEvent;", "onLayout", "changed", "l", "t", "r", "b", "onTouchEvent", "renderView", "view", "Landroid/view/View;", "sideAnim", "sideForLatest", "Lkotlin/Pair;", "", "x", "y", "touchOver", "updateView", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public abstract class AbstractDragFloatingView extends FrameLayout {
    private HashMap _$_findViewCache;
    private int bottomDistance;
    private FloatConfig config;
    private Rect floatRect;
    private boolean isCreated;
    private int lastX;
    private int lastY;
    private int leftDistance;
    private int minX;
    private int minY;
    private int parentHeight;
    private Rect parentRect;
    private ViewGroup parentView;
    private int parentWidth;
    private int rightDistance;
    private int topDistance;

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

        static {
            int[] iArr = new int[SidePattern.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[SidePattern.LEFT.ordinal()] = 1;
            iArr[SidePattern.RIGHT.ordinal()] = 2;
            iArr[SidePattern.TOP.ordinal()] = 3;
            iArr[SidePattern.BOTTOM.ordinal()] = 4;
            iArr[SidePattern.AUTO_HORIZONTAL.ordinal()] = 5;
            iArr[SidePattern.AUTO_VERTICAL.ordinal()] = 6;
            iArr[SidePattern.AUTO_SIDE.ordinal()] = 7;
            int[] iArr2 = new int[SidePattern.values().length];
            $EnumSwitchMapping$1 = iArr2;
            iArr2[SidePattern.RESULT_LEFT.ordinal()] = 1;
            iArr2[SidePattern.RESULT_RIGHT.ordinal()] = 2;
            iArr2[SidePattern.RESULT_TOP.ordinal()] = 3;
            iArr2[SidePattern.RESULT_BOTTOM.ordinal()] = 4;
            iArr2[SidePattern.RESULT_HORIZONTAL.ordinal()] = 5;
            iArr2[SidePattern.RESULT_VERTICAL.ordinal()] = 6;
            iArr2[SidePattern.RESULT_SIDE.ordinal()] = 7;
            int[] iArr3 = new int[SidePattern.values().length];
            $EnumSwitchMapping$2 = iArr3;
            iArr3[SidePattern.RESULT_LEFT.ordinal()] = 1;
            iArr3[SidePattern.RESULT_RIGHT.ordinal()] = 2;
            iArr3[SidePattern.RESULT_HORIZONTAL.ordinal()] = 3;
            iArr3[SidePattern.RESULT_TOP.ordinal()] = 4;
            iArr3[SidePattern.RESULT_BOTTOM.ordinal()] = 5;
            iArr3[SidePattern.RESULT_VERTICAL.ordinal()] = 6;
            iArr3[SidePattern.RESULT_SIDE.ordinal()] = 7;
        }
    }

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    public abstract Integer getLayoutId();

    public abstract void renderView(View view);

    public /* synthetic */ AbstractDragFloatingView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractDragFloatingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.parentRect = new Rect();
        this.floatRect = new Rect();
        new FrameLayout(context, attributeSet, i);
        this.config = new FloatConfig(null, null, null, false, false, false, false, false, null, null, false, false, 0, null, null, null, null, null, null, null, null, null, false, false, 16777215, null);
        initView(context);
        setOnClickListener(new View.OnClickListener() { // from class: com.lzf.easyfloat.widget.activityfloat.AbstractDragFloatingView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
            }
        });
    }

    public final FloatConfig getConfig() {
        return this.config;
    }

    public final void setConfig(FloatConfig floatConfig) {
        Intrinsics.checkParameterIsNotNull(floatConfig, "<set-?>");
        this.config = floatConfig;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void initView(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (getLayoutId() != null) {
            LayoutInflater from = LayoutInflater.from(context);
            Integer layoutId = getLayoutId();
            if (layoutId == null) {
                Intrinsics.throwNpe();
            }
            View inflate = from.inflate(layoutId.intValue(), this);
            Intrinsics.checkExpressionValueIsNotNull(inflate, "LayoutInflater.from(cont…te(getLayoutId()!!, this)");
            renderView(inflate);
            OnInvokeView invokeView = this.config.getInvokeView();
            if (invokeView != null) {
                invokeView.invoke(this);
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.isCreated) {
            return;
        }
        this.isCreated = true;
        if (true ^ Intrinsics.areEqual(this.config.getLocationPair(), new Pair(0, 0))) {
            setX(this.config.getLocationPair().getFirst().intValue());
            setY(this.config.getLocationPair().getSecond().intValue());
        } else {
            setX(getX() + this.config.getOffsetPair().getFirst().floatValue());
            setY(getY() + this.config.getOffsetPair().getSecond().floatValue());
        }
        initParent();
        initDistanceValue();
        enterAnim();
    }

    private final void initParent() {
        if (getParent() == null || !(getParent() instanceof ViewGroup)) {
            return;
        }
        ViewParent parent = getParent();
        if (parent == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.view.ViewGroup");
        }
        ViewGroup viewGroup = (ViewGroup) parent;
        this.parentView = viewGroup;
        if (viewGroup == null) {
            Intrinsics.throwNpe();
        }
        this.parentHeight = viewGroup.getHeight();
        ViewGroup viewGroup2 = this.parentView;
        if (viewGroup2 == null) {
            Intrinsics.throwNpe();
        }
        this.parentWidth = viewGroup2.getWidth();
        ViewGroup viewGroup3 = this.parentView;
        if (viewGroup3 == null) {
            Intrinsics.throwNpe();
        }
        viewGroup3.getGlobalVisibleRect(this.parentRect);
        Logger logger = Logger.INSTANCE;
        logger.e("parentRect: " + this.parentRect);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent != null) {
            updateView(motionEvent);
        }
        return this.config.isDrag() || super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent != null) {
            updateView(motionEvent);
        }
        return this.config.isDrag() || super.onTouchEvent(motionEvent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private final void updateView(MotionEvent motionEvent) {
        FloatCallbacks.Builder builder;
        Function2<View, MotionEvent, Unit> drag$easyfloat_release;
        FloatCallbacks.Builder builder2;
        Function2<View, MotionEvent, Unit> touchEvent$easyfloat_release;
        OnFloatCallbacks callbacks = this.config.getCallbacks();
        if (callbacks != null) {
            callbacks.touchEvent(this, motionEvent);
        }
        FloatCallbacks floatCallbacks = this.config.getFloatCallbacks();
        if (floatCallbacks != null && (builder2 = floatCallbacks.getBuilder()) != null && (touchEvent$easyfloat_release = builder2.getTouchEvent$easyfloat_release()) != null) {
            touchEvent$easyfloat_release.invoke(this, motionEvent);
        }
        if (!this.config.getDragEnable() || this.config.isAnim()) {
            this.config.setDrag(false);
            setPressed(true);
            return;
        }
        int rawX = (int) motionEvent.getRawX();
        int rawY = (int) motionEvent.getRawY();
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.config.setDrag(false);
            setPressed(true);
            this.lastX = rawX;
            this.lastY = rawY;
            getParent().requestDisallowInterceptTouchEvent(true);
            initParent();
        } else if (action != 1) {
            if (action == 2 && this.parentHeight > 0 && this.parentWidth > 0) {
                int i = rawX - this.lastX;
                int i2 = rawY - this.lastY;
                if (this.config.isDrag() || (i * i) + (i2 * i2) >= 81) {
                    this.config.setDrag(true);
                    float x = getX() + i;
                    float y = getY() + i2;
                    float f = 0;
                    float f2 = 0.0f;
                    if (x < f) {
                        x = 0.0f;
                    } else if (x > this.parentWidth - getWidth()) {
                        x = this.parentWidth - getWidth();
                    }
                    if (y < f) {
                        y = 0.0f;
                    } else if (y > this.parentHeight - getHeight()) {
                        y = this.parentHeight - getHeight();
                    }
                    switch (WhenMappings.$EnumSwitchMapping$0[this.config.getSidePattern().ordinal()]) {
                        case 1:
                            break;
                        case 2:
                            f2 = this.parentRect.right - getWidth();
                            break;
                        case 3:
                            f2 = x;
                            y = 0.0f;
                            break;
                        case 4:
                            f2 = this.parentRect.bottom - getHeight();
                            y = f2;
                            f2 = x;
                            break;
                        case 5:
                            if ((rawX * 2) - this.parentRect.left > this.parentRect.right) {
                                f2 = this.parentRect.right - getWidth();
                                break;
                            }
                            break;
                        case 6:
                            if (rawY - this.parentRect.top > this.parentRect.bottom - rawY) {
                                f2 = this.parentRect.bottom - getHeight();
                            }
                            y = f2;
                            f2 = x;
                            break;
                        case 7:
                            this.leftDistance = rawX - this.parentRect.left;
                            this.rightDistance = this.parentRect.right - rawX;
                            this.topDistance = rawY - this.parentRect.top;
                            this.bottomDistance = this.parentRect.bottom - rawY;
                            this.minX = Math.min(this.leftDistance, this.rightDistance);
                            this.minY = Math.min(this.topDistance, this.bottomDistance);
                            Pair<Float, Float> sideForLatest = sideForLatest(x, y);
                            f2 = sideForLatest.getFirst().floatValue();
                            y = sideForLatest.getSecond().floatValue();
                            break;
                        default:
                            f2 = x;
                            break;
                    }
                    setX(f2);
                    setY(y);
                    this.lastX = rawX;
                    this.lastY = rawY;
                    OnFloatCallbacks callbacks2 = this.config.getCallbacks();
                    if (callbacks2 != null) {
                        callbacks2.drag(this, motionEvent);
                    }
                    FloatCallbacks floatCallbacks2 = this.config.getFloatCallbacks();
                    if (floatCallbacks2 == null || (builder = floatCallbacks2.getBuilder()) == null || (drag$easyfloat_release = builder.getDrag$easyfloat_release()) == null) {
                        return;
                    }
                    drag$easyfloat_release.invoke(this, motionEvent);
                }
            }
        } else {
            setPressed(!this.config.isDrag());
            if (this.config.isDrag()) {
                switch (WhenMappings.$EnumSwitchMapping$1[this.config.getSidePattern().ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        sideAnim();
                        return;
                    default:
                        touchOver();
                        return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void touchOver() {
        FloatCallbacks.Builder builder;
        Function1<View, Unit> dragEnd$easyfloat_release;
        this.config.setAnim(false);
        this.config.setDrag(false);
        OnFloatCallbacks callbacks = this.config.getCallbacks();
        if (callbacks != null) {
            callbacks.dragEnd(this);
        }
        FloatCallbacks floatCallbacks = this.config.getFloatCallbacks();
        if (floatCallbacks == null || (builder = floatCallbacks.getBuilder()) == null || (dragEnd$easyfloat_release = builder.getDragEnd$easyfloat_release()) == null) {
            return;
        }
        dragEnd$easyfloat_release.invoke(this);
    }

    private final void sideAnim() {
        float translationX;
        float f;
        float translationX2;
        float f2;
        float translationY;
        float f3;
        float f4;
        initDistanceValue();
        float f5 = 0.0f;
        String str = "translationY";
        switch (WhenMappings.$EnumSwitchMapping$2[this.config.getSidePattern().ordinal()]) {
            case 1:
                translationX = getTranslationX();
                f = -this.leftDistance;
                translationX2 = getTranslationX();
                f3 = f + translationX2;
                str = "translationX";
                float f6 = translationX;
                f5 = f3;
                f4 = f6;
                break;
            case 2:
                translationX = getTranslationX();
                f = this.rightDistance;
                translationX2 = getTranslationX();
                f3 = f + translationX2;
                str = "translationX";
                float f62 = translationX;
                f5 = f3;
                f4 = f62;
                break;
            case 3:
                translationX = getTranslationX();
                int i = this.leftDistance;
                int i2 = this.rightDistance;
                f = i < i2 ? -i : i2;
                translationX2 = getTranslationX();
                f3 = f + translationX2;
                str = "translationX";
                float f622 = translationX;
                f5 = f3;
                f4 = f622;
                break;
            case 4:
                translationX = getTranslationY();
                f2 = -this.topDistance;
                translationY = getTranslationY();
                f3 = f2 + translationY;
                float f6222 = translationX;
                f5 = f3;
                f4 = f6222;
                break;
            case 5:
                translationX = getTranslationY();
                f2 = this.bottomDistance;
                translationY = getTranslationY();
                f3 = f2 + translationY;
                float f62222 = translationX;
                f5 = f3;
                f4 = f62222;
                break;
            case 6:
                translationX = getTranslationY();
                int i3 = this.topDistance;
                int i4 = this.bottomDistance;
                f2 = i3 < i4 ? -i3 : i4;
                translationY = getTranslationY();
                f3 = f2 + translationY;
                float f622222 = translationX;
                f5 = f3;
                f4 = f622222;
                break;
            case 7:
                if (this.minX < this.minY) {
                    translationX = getTranslationX();
                    int i5 = this.leftDistance;
                    int i6 = this.rightDistance;
                    f = i5 < i6 ? -i5 : i6;
                    translationX2 = getTranslationX();
                    f3 = f + translationX2;
                    str = "translationX";
                    float f6222222 = translationX;
                    f5 = f3;
                    f4 = f6222222;
                    break;
                } else {
                    translationX = getTranslationY();
                    int i7 = this.topDistance;
                    int i8 = this.bottomDistance;
                    f2 = i7 < i8 ? -i7 : i8;
                    translationY = getTranslationY();
                    f3 = f2 + translationY;
                    float f62222222 = translationX;
                    f5 = f3;
                    f4 = f62222222;
                }
            default:
                str = "translationX";
                f4 = 0.0f;
                break;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, str, f4, f5);
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.lzf.easyfloat.widget.activityfloat.AbstractDragFloatingView$sideAnim$1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                AbstractDragFloatingView.this.touchOver();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                AbstractDragFloatingView.this.getConfig().setAnim(true);
            }
        });
        ofFloat.start();
    }

    private final Pair<Float, Float> sideForLatest(float f, float f2) {
        int i = this.minX;
        int i2 = this.minY;
        if (i < i2) {
            f = this.leftDistance == i ? 0.0f : this.parentWidth - getWidth();
        } else {
            f2 = this.topDistance == i2 ? 0.0f : this.parentHeight - getHeight();
        }
        return new Pair<>(Float.valueOf(f), Float.valueOf(f2));
    }

    private final void initDistanceValue() {
        getGlobalVisibleRect(this.floatRect);
        this.leftDistance = this.floatRect.left - this.parentRect.left;
        this.rightDistance = this.parentRect.right - this.floatRect.right;
        this.topDistance = this.floatRect.top - this.parentRect.top;
        this.bottomDistance = this.parentRect.bottom - this.floatRect.bottom;
        this.minX = Math.min(this.leftDistance, this.rightDistance);
        this.minY = Math.min(this.topDistance, this.bottomDistance);
        Logger logger = Logger.INSTANCE;
        logger.i(this.leftDistance + "   " + this.rightDistance + "   " + this.topDistance + "   " + this.bottomDistance);
    }

    private final void enterAnim() {
        if (this.parentView == null) {
            return;
        }
        OnFloatAnimator floatAnimator = this.config.getFloatAnimator();
        AbstractDragFloatingView abstractDragFloatingView = this;
        ViewGroup viewGroup = this.parentView;
        if (viewGroup == null) {
            Intrinsics.throwNpe();
        }
        Animator enterAnim = new AnimatorManager(floatAnimator, abstractDragFloatingView, viewGroup, this.config.getSidePattern()).enterAnim();
        if (enterAnim != null) {
            enterAnim.addListener(new Animator.AnimatorListener() { // from class: com.lzf.easyfloat.widget.activityfloat.AbstractDragFloatingView$enterAnim$1
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    AbstractDragFloatingView.this.getConfig().setAnim(false);
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    AbstractDragFloatingView.this.getConfig().setAnim(true);
                }
            });
        }
        if (enterAnim != null) {
            enterAnim.start();
        }
    }

    public final void exitAnim$easyfloat_release() {
        if (this.config.isAnim() || this.parentView == null) {
            return;
        }
        OnFloatAnimator floatAnimator = this.config.getFloatAnimator();
        AbstractDragFloatingView abstractDragFloatingView = this;
        ViewGroup viewGroup = this.parentView;
        if (viewGroup == null) {
            Intrinsics.throwNpe();
        }
        Animator exitAnim = new AnimatorManager(floatAnimator, abstractDragFloatingView, viewGroup, this.config.getSidePattern()).exitAnim();
        if (exitAnim == null) {
            ViewGroup viewGroup2 = this.parentView;
            if (viewGroup2 != null) {
                viewGroup2.removeView(abstractDragFloatingView);
                return;
            }
            return;
        }
        exitAnim.addListener(new Animator.AnimatorListener() { // from class: com.lzf.easyfloat.widget.activityfloat.AbstractDragFloatingView$exitAnim$1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ViewGroup viewGroup3;
                AbstractDragFloatingView.this.getConfig().setAnim(false);
                viewGroup3 = AbstractDragFloatingView.this.parentView;
                if (viewGroup3 != null) {
                    viewGroup3.removeView(AbstractDragFloatingView.this);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                AbstractDragFloatingView.this.getConfig().setAnim(true);
            }
        });
        exitAnim.start();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        FloatCallbacks.Builder builder;
        Function0<Unit> dismiss$easyfloat_release;
        super.onDetachedFromWindow();
        OnFloatCallbacks callbacks = this.config.getCallbacks();
        if (callbacks != null) {
            callbacks.dismiss();
        }
        FloatCallbacks floatCallbacks = this.config.getFloatCallbacks();
        if (floatCallbacks == null || (builder = floatCallbacks.getBuilder()) == null || (dismiss$easyfloat_release = builder.getDismiss$easyfloat_release()) == null) {
            return;
        }
        dismiss$easyfloat_release.invoke();
    }
}
