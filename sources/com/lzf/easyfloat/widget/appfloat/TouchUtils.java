package com.lzf.easyfloat.widget.appfloat;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.FloatCallbacks;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.utils.DisplayUtils;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: TouchUtils.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0002J\u0018\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020$2\u0006\u0010 \u001a\u00020!H\u0002J \u0010%\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010#\u001a\u00020$2\u0006\u0010&\u001a\u00020'H\u0002J\u0010\u0010(\u001a\u00020\b2\u0006\u0010 \u001a\u00020!H\u0002J&\u0010)\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010*\u001a\u00020+2\u0006\u0010&\u001a\u00020'2\u0006\u0010#\u001a\u00020$J\u001e\u0010)\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010#\u001a\u00020$2\u0006\u0010&\u001a\u00020'R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lcom/lzf/easyfloat/widget/appfloat/TouchUtils;", "", "context", "Landroid/content/Context;", "config", "Lcom/lzf/easyfloat/data/FloatConfig;", "(Landroid/content/Context;Lcom/lzf/easyfloat/data/FloatConfig;)V", "bottomDistance", "", "getConfig", "()Lcom/lzf/easyfloat/data/FloatConfig;", "getContext", "()Landroid/content/Context;", "emptyHeight", "hasStatusBar", "", "lastX", "", "lastY", "leftDistance", "location", "", "minX", "minY", "parentHeight", "parentRect", "Landroid/graphics/Rect;", "parentWidth", "rightDistance", "topDistance", "dragEnd", "", "view", "Landroid/view/View;", "initDistanceValue", "params", "Landroid/view/WindowManager$LayoutParams;", "sideAnim", "windowManager", "Landroid/view/WindowManager;", "statusBarHeight", "updateFloat", "event", "Landroid/view/MotionEvent;", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class TouchUtils {
    private int bottomDistance;
    private final FloatConfig config;
    private final Context context;
    private int emptyHeight;
    private boolean hasStatusBar;
    private float lastX;
    private float lastY;
    private int leftDistance;
    private final int[] location;
    private int minX;
    private int minY;
    private int parentHeight;
    private Rect parentRect;
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

    public TouchUtils(Context context, FloatConfig config) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(config, "config");
        this.context = context;
        this.config = config;
        this.parentRect = new Rect();
        this.location = new int[2];
        this.hasStatusBar = true;
    }

    public final FloatConfig getConfig() {
        return this.config;
    }

    public final Context getContext() {
        return this.context;
    }

    public final void updateFloat(View view, WindowManager.LayoutParams params, WindowManager windowManager) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(params, "params");
        Intrinsics.checkParameterIsNotNull(windowManager, "windowManager");
        initDistanceValue(params, view);
        sideAnim(view, params, windowManager);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void updateFloat(View view, MotionEvent event, WindowManager windowManager, WindowManager.LayoutParams params) {
        FloatCallbacks.Builder builder;
        Function1<View, Unit> dragEnd$easyfloat_release;
        FloatCallbacks.Builder builder2;
        Function2<View, MotionEvent, Unit> drag$easyfloat_release;
        int i;
        int width;
        FloatCallbacks.Builder builder3;
        Function2<View, MotionEvent, Unit> touchEvent$easyfloat_release;
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(event, "event");
        Intrinsics.checkParameterIsNotNull(windowManager, "windowManager");
        Intrinsics.checkParameterIsNotNull(params, "params");
        OnFloatCallbacks callbacks = this.config.getCallbacks();
        if (callbacks != null) {
            callbacks.touchEvent(view, event);
        }
        FloatCallbacks floatCallbacks = this.config.getFloatCallbacks();
        if (floatCallbacks != null && (builder3 = floatCallbacks.getBuilder()) != null && (touchEvent$easyfloat_release = builder3.getTouchEvent$easyfloat_release()) != null) {
            touchEvent$easyfloat_release.invoke(view, event);
        }
        r1 = 0;
        r1 = 0;
        int height = 0;
        r1 = 0;
        int i2 = 0;
        if (!this.config.getDragEnable() || this.config.isAnim()) {
            this.config.setDrag(false);
            return;
        }
        int action = event.getAction() & 255;
        if (action == 0) {
            this.config.setDrag(false);
            this.lastX = event.getRawX();
            this.lastY = event.getRawY();
            this.parentWidth = DisplayUtils.INSTANCE.getScreenSize(this.context).x;
            this.parentHeight = DisplayUtils.INSTANCE.getScreenSize(this.context).y;
            view.getLocationOnScreen(this.location);
            this.hasStatusBar = this.location[1] > params.y;
            this.emptyHeight = this.parentHeight - view.getHeight();
        } else if (action == 1) {
            if (this.config.isDrag()) {
                switch (WhenMappings.$EnumSwitchMapping$1[this.config.getSidePattern().ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        sideAnim(view, params, windowManager);
                        return;
                    default:
                        OnFloatCallbacks callbacks2 = this.config.getCallbacks();
                        if (callbacks2 != null) {
                            callbacks2.dragEnd(view);
                        }
                        FloatCallbacks floatCallbacks2 = this.config.getFloatCallbacks();
                        if (floatCallbacks2 == null || (builder = floatCallbacks2.getBuilder()) == null || (dragEnd$easyfloat_release = builder.getDragEnd$easyfloat_release()) == null) {
                            return;
                        }
                        dragEnd$easyfloat_release.invoke(view);
                        return;
                }
            }
        } else if (action != 2) {
        } else {
            float rawX = event.getRawX() - this.lastX;
            float rawY = event.getRawY() - this.lastY;
            if (this.config.isDrag() || (rawX * rawX) + (rawY * rawY) >= 81) {
                this.config.setDrag(true);
                int i3 = params.x + ((int) rawX);
                int i4 = params.y + ((int) rawY);
                if (i3 < 0) {
                    i3 = 0;
                } else if (i3 > this.parentWidth - view.getWidth()) {
                    i3 = this.parentWidth - view.getWidth();
                }
                if (i4 < 0) {
                    i4 = 0;
                } else if (i4 > this.emptyHeight - statusBarHeight(view)) {
                    if (this.hasStatusBar) {
                        i4 = this.emptyHeight - statusBarHeight(view);
                    } else {
                        int i5 = this.emptyHeight;
                        if (i4 > i5) {
                            i4 = i5;
                        }
                    }
                }
                switch (WhenMappings.$EnumSwitchMapping$0[this.config.getSidePattern().ordinal()]) {
                    case 1:
                        break;
                    case 2:
                        i = this.parentWidth;
                        width = view.getWidth();
                        i2 = i - width;
                        break;
                    case 3:
                        i2 = i3;
                        i4 = 0;
                        break;
                    case 4:
                        height = this.parentHeight - view.getHeight();
                        i4 = height;
                        i2 = i3;
                        break;
                    case 5:
                        float rawX2 = event.getRawX() * 2;
                        int i6 = this.parentWidth;
                        if (rawX2 > i6) {
                            i2 = i6 - view.getWidth();
                            break;
                        }
                        break;
                    case 6:
                        float rawY2 = (event.getRawY() - this.parentRect.top) * 2;
                        int i7 = this.parentHeight;
                        if (rawY2 > i7) {
                            height = i7 - view.getHeight();
                        }
                        i4 = height;
                        i2 = i3;
                        break;
                    case 7:
                        this.leftDistance = (int) event.getRawX();
                        this.rightDistance = this.parentWidth - ((int) event.getRawX());
                        this.topDistance = ((int) event.getRawY()) - this.parentRect.top;
                        this.bottomDistance = (this.parentHeight + this.parentRect.top) - ((int) event.getRawY());
                        this.minX = Math.min(this.leftDistance, this.rightDistance);
                        int min = Math.min(this.topDistance, this.bottomDistance);
                        this.minY = min;
                        int i8 = this.minX;
                        if (i8 < min) {
                            if (this.leftDistance != i8) {
                                i = this.parentWidth;
                                width = view.getWidth();
                                i2 = i - width;
                                break;
                            }
                        } else {
                            if (this.topDistance != min) {
                                height = this.parentHeight - view.getHeight();
                            }
                            i4 = height;
                            i2 = i3;
                            break;
                        }
                        break;
                    default:
                        i2 = i3;
                        break;
                }
                params.x = i2;
                params.y = i4;
                windowManager.updateViewLayout(view, params);
                OnFloatCallbacks callbacks3 = this.config.getCallbacks();
                if (callbacks3 != null) {
                    callbacks3.drag(view, event);
                }
                FloatCallbacks floatCallbacks3 = this.config.getFloatCallbacks();
                if (floatCallbacks3 != null && (builder2 = floatCallbacks3.getBuilder()) != null && (drag$easyfloat_release = builder2.getDrag$easyfloat_release()) != null) {
                    drag$easyfloat_release.invoke(view, event);
                }
                this.lastX = event.getRawX();
                this.lastY = event.getRawY();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void sideAnim(final android.view.View r10, final android.view.WindowManager.LayoutParams r11, final android.view.WindowManager r12) {
        /*
            r9 = this;
            r9.initDistanceValue(r11, r10)
            com.lzf.easyfloat.data.FloatConfig r0 = r9.config
            com.lzf.easyfloat.enums.SidePattern r0 = r0.getSidePattern()
            int[] r1 = com.lzf.easyfloat.widget.appfloat.TouchUtils.WhenMappings.$EnumSwitchMapping$2
            int r0 = r0.ordinal()
            r0 = r1[r0]
            r1 = 1
            r2 = 0
            switch(r0) {
                case 1: goto L77;
                case 2: goto L71;
                case 3: goto L65;
                case 4: goto L62;
                case 5: goto L53;
                case 6: goto L3e;
                case 7: goto L17;
                default: goto L16;
            }
        L16:
            return
        L17:
            int r0 = r9.minX
            int r3 = r9.minY
            if (r0 >= r3) goto L29
            int r0 = r9.leftDistance
            int r3 = r9.rightDistance
            if (r0 >= r3) goto L24
            goto L77
        L24:
            int r0 = r11.x
            int r3 = r9.rightDistance
            goto L75
        L29:
            int r0 = r9.topDistance
            int r3 = r9.bottomDistance
            if (r0 >= r3) goto L30
            goto L62
        L30:
            boolean r0 = r9.hasStatusBar
            if (r0 == 0) goto L3b
            int r0 = r9.emptyHeight
            int r3 = r9.statusBarHeight(r10)
            goto L5d
        L3b:
            int r0 = r9.emptyHeight
            goto L63
        L3e:
            int r0 = r9.topDistance
            int r3 = r9.bottomDistance
            if (r0 >= r3) goto L45
            goto L62
        L45:
            boolean r0 = r9.hasStatusBar
            if (r0 == 0) goto L50
            int r0 = r9.emptyHeight
            int r3 = r9.statusBarHeight(r10)
            goto L5d
        L50:
            int r0 = r9.emptyHeight
            goto L63
        L53:
            boolean r0 = r9.hasStatusBar
            if (r0 == 0) goto L5f
            int r0 = r9.emptyHeight
            int r3 = r9.statusBarHeight(r10)
        L5d:
            int r0 = r0 - r3
            goto L63
        L5f:
            int r0 = r9.emptyHeight
            goto L63
        L62:
            r0 = 0
        L63:
            r4 = 0
            goto L79
        L65:
            int r0 = r9.leftDistance
            int r3 = r9.rightDistance
            if (r0 >= r3) goto L6c
            goto L77
        L6c:
            int r0 = r11.x
            int r3 = r9.rightDistance
            goto L75
        L71:
            int r0 = r11.x
            int r3 = r9.rightDistance
        L75:
            int r0 = r0 + r3
            goto L78
        L77:
            r0 = 0
        L78:
            r4 = 1
        L79:
            r3 = 2
            int[] r3 = new int[r3]
            if (r4 == 0) goto L81
            int r5 = r11.x
            goto L83
        L81:
            int r5 = r11.y
        L83:
            r3[r2] = r5
            r3[r1] = r0
            android.animation.ValueAnimator r0 = android.animation.ValueAnimator.ofInt(r3)
            com.lzf.easyfloat.widget.appfloat.TouchUtils$sideAnim$1 r1 = new com.lzf.easyfloat.widget.appfloat.TouchUtils$sideAnim$1
            r3 = r1
            r5 = r11
            r6 = r12
            r7 = r10
            r8 = r0
            r3.<init>()
            android.animation.ValueAnimator$AnimatorUpdateListener r1 = (android.animation.ValueAnimator.AnimatorUpdateListener) r1
            r0.addUpdateListener(r1)
            com.lzf.easyfloat.widget.appfloat.TouchUtils$sideAnim$2 r11 = new com.lzf.easyfloat.widget.appfloat.TouchUtils$sideAnim$2
            r11.<init>()
            android.animation.Animator$AnimatorListener r11 = (android.animation.Animator.AnimatorListener) r11
            r0.addListener(r11)
            r0.start()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lzf.easyfloat.widget.appfloat.TouchUtils.sideAnim(android.view.View, android.view.WindowManager$LayoutParams, android.view.WindowManager):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dragEnd(View view) {
        FloatCallbacks.Builder builder;
        Function1<View, Unit> dragEnd$easyfloat_release;
        this.config.setAnim(false);
        OnFloatCallbacks callbacks = this.config.getCallbacks();
        if (callbacks != null) {
            callbacks.dragEnd(view);
        }
        FloatCallbacks floatCallbacks = this.config.getFloatCallbacks();
        if (floatCallbacks == null || (builder = floatCallbacks.getBuilder()) == null || (dragEnd$easyfloat_release = builder.getDragEnd$easyfloat_release()) == null) {
            return;
        }
        dragEnd$easyfloat_release.invoke(view);
    }

    private final void initDistanceValue(WindowManager.LayoutParams layoutParams, View view) {
        int height;
        int i = layoutParams.x;
        this.leftDistance = i;
        this.rightDistance = this.parentWidth - (i + view.getRight());
        int i2 = layoutParams.y;
        this.topDistance = i2;
        if (this.hasStatusBar) {
            height = ((this.parentHeight - statusBarHeight(view)) - this.topDistance) - view.getHeight();
        } else {
            height = (this.parentHeight - i2) - view.getHeight();
        }
        this.bottomDistance = height;
        this.minX = Math.min(this.leftDistance, this.rightDistance);
        this.minY = Math.min(this.topDistance, this.bottomDistance);
    }

    private final int statusBarHeight(View view) {
        return DisplayUtils.INSTANCE.statusBarHeight(view);
    }
}
