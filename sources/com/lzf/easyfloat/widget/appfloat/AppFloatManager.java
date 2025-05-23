package com.lzf.easyfloat.widget.appfloat;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.google.android.material.badge.BadgeDrawable;
import com.lzf.easyfloat.anim.AppFloatAnimatorManager;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.FloatCallbacks;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnFloatTouchListener;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.lzf.easyfloat.utils.DisplayUtils;
import com.lzf.easyfloat.utils.LifecycleUtils;
import com.lzf.easyfloat.utils.Logger;
import com.lzf.easyfloat.widget.appfloat.ParentFrameLayout;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.opencv.videoio.Videoio;
/* compiled from: AppFloatManager.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010%\u001a\u00020&H\u0002J\r\u0010'\u001a\u0004\u0018\u00010&¢\u0006\u0002\u0010(J\u0010\u0010)\u001a\u00020&2\u0006\u0010*\u001a\u00020+H\u0002J\u0006\u0010,\u001a\u00020&J\b\u0010-\u001a\u00020&H\u0002J\b\u0010.\u001a\u00020&H\u0002J\u0006\u0010/\u001a\u00020&J\u0012\u00100\u001a\u00020&2\b\u00101\u001a\u0004\u0018\u00010+H\u0003J\u0018\u00102\u001a\u00020&2\u0006\u00103\u001a\u0002042\b\b\u0002\u00105\u001a\u000206J\u0016\u00107\u001a\u00020&2\u0006\u00108\u001a\u0002042\u0006\u00109\u001a\u000204R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020 X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$¨\u0006:"}, d2 = {"Lcom/lzf/easyfloat/widget/appfloat/AppFloatManager;", "", "context", "Landroid/content/Context;", "config", "Lcom/lzf/easyfloat/data/FloatConfig;", "(Landroid/content/Context;Lcom/lzf/easyfloat/data/FloatConfig;)V", "getConfig", "()Lcom/lzf/easyfloat/data/FloatConfig;", "setConfig", "(Lcom/lzf/easyfloat/data/FloatConfig;)V", "getContext", "()Landroid/content/Context;", "frameLayout", "Lcom/lzf/easyfloat/widget/appfloat/ParentFrameLayout;", "getFrameLayout", "()Lcom/lzf/easyfloat/widget/appfloat/ParentFrameLayout;", "setFrameLayout", "(Lcom/lzf/easyfloat/widget/appfloat/ParentFrameLayout;)V", "params", "Landroid/view/WindowManager$LayoutParams;", "getParams", "()Landroid/view/WindowManager$LayoutParams;", "setParams", "(Landroid/view/WindowManager$LayoutParams;)V", "touchUtils", "Lcom/lzf/easyfloat/widget/appfloat/TouchUtils;", "getTouchUtils", "()Lcom/lzf/easyfloat/widget/appfloat/TouchUtils;", "setTouchUtils", "(Lcom/lzf/easyfloat/widget/appfloat/TouchUtils;)V", "windowManager", "Landroid/view/WindowManager;", "getWindowManager", "()Landroid/view/WindowManager;", "setWindowManager", "(Landroid/view/WindowManager;)V", "addView", "", "createFloat", "()Lkotlin/Unit;", "enterAnim", "floatingView", "Landroid/view/View;", "exitAnim", "floatOver", "initParams", "resetView", "setGravity", "view", "setVisible", "visible", "", "needShow", "", "updateFloat", "x", "y", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class AppFloatManager {
    private FloatConfig config;
    private final Context context;
    private ParentFrameLayout frameLayout;
    public WindowManager.LayoutParams params;
    public TouchUtils touchUtils;
    public WindowManager windowManager;

    public AppFloatManager(Context context, FloatConfig config) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(config, "config");
        this.context = context;
        this.config = config;
    }

    public final FloatConfig getConfig() {
        return this.config;
    }

    public final Context getContext() {
        return this.context;
    }

    public final void setConfig(FloatConfig floatConfig) {
        Intrinsics.checkParameterIsNotNull(floatConfig, "<set-?>");
        this.config = floatConfig;
    }

    public final WindowManager getWindowManager() {
        WindowManager windowManager = this.windowManager;
        if (windowManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("windowManager");
        }
        return windowManager;
    }

    public final void setWindowManager(WindowManager windowManager) {
        Intrinsics.checkParameterIsNotNull(windowManager, "<set-?>");
        this.windowManager = windowManager;
    }

    public final WindowManager.LayoutParams getParams() {
        WindowManager.LayoutParams layoutParams = this.params;
        if (layoutParams == null) {
            Intrinsics.throwUninitializedPropertyAccessException("params");
        }
        return layoutParams;
    }

    public final void setParams(WindowManager.LayoutParams layoutParams) {
        Intrinsics.checkParameterIsNotNull(layoutParams, "<set-?>");
        this.params = layoutParams;
    }

    public final ParentFrameLayout getFrameLayout() {
        return this.frameLayout;
    }

    public final void setFrameLayout(ParentFrameLayout parentFrameLayout) {
        this.frameLayout = parentFrameLayout;
    }

    public final TouchUtils getTouchUtils() {
        TouchUtils touchUtils = this.touchUtils;
        if (touchUtils == null) {
            Intrinsics.throwUninitializedPropertyAccessException("touchUtils");
        }
        return touchUtils;
    }

    public final void setTouchUtils(TouchUtils touchUtils) {
        Intrinsics.checkParameterIsNotNull(touchUtils, "<set-?>");
        this.touchUtils = touchUtils;
    }

    public final Unit createFloat() {
        FloatCallbacks.Builder builder;
        Function3<Boolean, String, View, Unit> createdResult$easyfloat_release;
        try {
            this.touchUtils = new TouchUtils(this.context, this.config);
            initParams();
            addView();
            this.config.setShow(true);
            return Unit.INSTANCE;
        } catch (Exception e) {
            OnFloatCallbacks callbacks = this.config.getCallbacks();
            if (callbacks != null) {
                callbacks.createdResult(false, String.valueOf(e), null);
            }
            FloatCallbacks floatCallbacks = this.config.getFloatCallbacks();
            if (floatCallbacks == null || (builder = floatCallbacks.getBuilder()) == null || (createdResult$easyfloat_release = builder.getCreatedResult$easyfloat_release()) == null) {
                return null;
            }
            return createdResult$easyfloat_release.invoke(false, String.valueOf(e), null);
        }
    }

    private final void initParams() {
        Object systemService = this.context.getSystemService("window");
        if (systemService == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.view.WindowManager");
        }
        this.windowManager = (WindowManager) systemService;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= 28) {
            layoutParams.layoutInDisplayCutoutMode = 1;
        }
        layoutParams.type = Build.VERSION.SDK_INT >= 26 ? 2038 : 2002;
        layoutParams.format = 1;
        layoutParams.gravity = BadgeDrawable.TOP_START;
        layoutParams.flags = Videoio.CAP_PROP_XI_ACQ_TRANSPORT_BUFFER_COMMIT;
        layoutParams.width = this.config.getWidthMatch() ? -1 : -2;
        layoutParams.height = this.config.getHeightMatch() ? -1 : -2;
        if (!Intrinsics.areEqual(this.config.getLocationPair(), new Pair(0, 0))) {
            layoutParams.x = this.config.getLocationPair().getFirst().intValue();
            layoutParams.y = this.config.getLocationPair().getSecond().intValue();
        }
        this.params = layoutParams;
    }

    public final void resetView() {
        WindowManager windowManager = this.windowManager;
        if (windowManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("windowManager");
        }
        windowManager.removeViewImmediate(this.frameLayout);
        WindowManager windowManager2 = this.windowManager;
        if (windowManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("windowManager");
        }
        ParentFrameLayout parentFrameLayout = this.frameLayout;
        WindowManager.LayoutParams layoutParams = this.params;
        if (layoutParams == null) {
            Intrinsics.throwUninitializedPropertyAccessException("params");
        }
        windowManager2.addView(parentFrameLayout, layoutParams);
    }

    private final void addView() {
        ParentFrameLayout parentFrameLayout = new ParentFrameLayout(this.context, this.config, null, 0, 12, null);
        this.frameLayout = parentFrameLayout;
        parentFrameLayout.setTag(this.config.getFloatTag());
        LayoutInflater from = LayoutInflater.from(this.context);
        Integer layoutId = this.config.getLayoutId();
        if (layoutId == null) {
            Intrinsics.throwNpe();
        }
        final View floatingView = from.inflate(layoutId.intValue(), (ViewGroup) this.frameLayout, true);
        Intrinsics.checkExpressionValueIsNotNull(floatingView, "floatingView");
        floatingView.setVisibility(4);
        WindowManager windowManager = this.windowManager;
        if (windowManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("windowManager");
        }
        ParentFrameLayout parentFrameLayout2 = this.frameLayout;
        WindowManager.LayoutParams layoutParams = this.params;
        if (layoutParams == null) {
            Intrinsics.throwUninitializedPropertyAccessException("params");
        }
        windowManager.addView(parentFrameLayout2, layoutParams);
        ParentFrameLayout parentFrameLayout3 = this.frameLayout;
        if (parentFrameLayout3 != null) {
            parentFrameLayout3.setTouchListener(new OnFloatTouchListener() { // from class: com.lzf.easyfloat.widget.appfloat.AppFloatManager$addView$1
                @Override // com.lzf.easyfloat.interfaces.OnFloatTouchListener
                public void onTouch(MotionEvent event) {
                    Intrinsics.checkParameterIsNotNull(event, "event");
                    TouchUtils touchUtils = AppFloatManager.this.getTouchUtils();
                    ParentFrameLayout frameLayout = AppFloatManager.this.getFrameLayout();
                    if (frameLayout == null) {
                        Intrinsics.throwNpe();
                    }
                    touchUtils.updateFloat(frameLayout, event, AppFloatManager.this.getWindowManager(), AppFloatManager.this.getParams());
                }
            });
        }
        ParentFrameLayout parentFrameLayout4 = this.frameLayout;
        if (parentFrameLayout4 != null) {
            parentFrameLayout4.setLayoutListener(new ParentFrameLayout.OnLayoutListener() { // from class: com.lzf.easyfloat.widget.appfloat.AppFloatManager$addView$2
                @Override // com.lzf.easyfloat.widget.appfloat.ParentFrameLayout.OnLayoutListener
                public void onLayout() {
                    FloatCallbacks.Builder builder;
                    Function3<Boolean, String, View, Unit> createdResult$easyfloat_release;
                    AppFloatManager appFloatManager = AppFloatManager.this;
                    appFloatManager.setGravity(appFloatManager.getFrameLayout());
                    FloatConfig config = AppFloatManager.this.getConfig();
                    if (config.getFilterSelf$easyfloat_release() || ((config.getShowPattern() == ShowPattern.BACKGROUND && LifecycleUtils.INSTANCE.isForeground()) || (config.getShowPattern() == ShowPattern.FOREGROUND && !LifecycleUtils.INSTANCE.isForeground()))) {
                        AppFloatManager.setVisible$default(AppFloatManager.this, 8, false, 2, null);
                    } else {
                        AppFloatManager appFloatManager2 = AppFloatManager.this;
                        View floatingView2 = floatingView;
                        Intrinsics.checkExpressionValueIsNotNull(floatingView2, "floatingView");
                        appFloatManager2.enterAnim(floatingView2);
                    }
                    config.setLayoutView(floatingView);
                    OnInvokeView invokeView = config.getInvokeView();
                    if (invokeView != null) {
                        invokeView.invoke(floatingView);
                    }
                    OnFloatCallbacks callbacks = config.getCallbacks();
                    if (callbacks != null) {
                        callbacks.createdResult(true, null, floatingView);
                    }
                    FloatCallbacks floatCallbacks = config.getFloatCallbacks();
                    if (floatCallbacks == null || (builder = floatCallbacks.getBuilder()) == null || (createdResult$easyfloat_release = builder.getCreatedResult$easyfloat_release()) == null) {
                        return;
                    }
                    createdResult$easyfloat_release.invoke(true, null, floatingView);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setGravity(View view) {
        if ((!Intrinsics.areEqual(this.config.getLocationPair(), new Pair(0, 0))) || view == null) {
            return;
        }
        Rect rect = new Rect();
        WindowManager windowManager = this.windowManager;
        if (windowManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("windowManager");
        }
        windowManager.getDefaultDisplay().getRectSize(rect);
        int statusBarHeight = rect.bottom - DisplayUtils.INSTANCE.statusBarHeight(view);
        switch (this.config.getGravity()) {
            case 1:
            case 49:
                WindowManager.LayoutParams layoutParams = this.params;
                if (layoutParams == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("params");
                }
                layoutParams.x = (rect.right - view.getWidth()) >> 1;
                break;
            case 5:
            case 53:
            case 8388613:
            case BadgeDrawable.TOP_END /* 8388661 */:
                WindowManager.LayoutParams layoutParams2 = this.params;
                if (layoutParams2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("params");
                }
                layoutParams2.x = rect.right - view.getWidth();
                break;
            case 16:
            case 19:
            case 8388627:
                WindowManager.LayoutParams layoutParams3 = this.params;
                if (layoutParams3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("params");
                }
                layoutParams3.y = (statusBarHeight - view.getHeight()) >> 1;
                break;
            case 17:
                WindowManager.LayoutParams layoutParams4 = this.params;
                if (layoutParams4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("params");
                }
                layoutParams4.x = (rect.right - view.getWidth()) >> 1;
                WindowManager.LayoutParams layoutParams5 = this.params;
                if (layoutParams5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("params");
                }
                layoutParams5.y = (statusBarHeight - view.getHeight()) >> 1;
                break;
            case 21:
            case 8388629:
                WindowManager.LayoutParams layoutParams6 = this.params;
                if (layoutParams6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("params");
                }
                layoutParams6.x = rect.right - view.getWidth();
                WindowManager.LayoutParams layoutParams7 = this.params;
                if (layoutParams7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("params");
                }
                layoutParams7.y = (statusBarHeight - view.getHeight()) >> 1;
                break;
            case 80:
            case 83:
            case BadgeDrawable.BOTTOM_START /* 8388691 */:
                WindowManager.LayoutParams layoutParams8 = this.params;
                if (layoutParams8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("params");
                }
                layoutParams8.y = statusBarHeight - view.getHeight();
                break;
            case 81:
                WindowManager.LayoutParams layoutParams9 = this.params;
                if (layoutParams9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("params");
                }
                layoutParams9.x = (rect.right - view.getWidth()) >> 1;
                WindowManager.LayoutParams layoutParams10 = this.params;
                if (layoutParams10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("params");
                }
                layoutParams10.y = statusBarHeight - view.getHeight();
                break;
            case 85:
            case BadgeDrawable.BOTTOM_END /* 8388693 */:
                WindowManager.LayoutParams layoutParams11 = this.params;
                if (layoutParams11 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("params");
                }
                layoutParams11.x = rect.right - view.getWidth();
                WindowManager.LayoutParams layoutParams12 = this.params;
                if (layoutParams12 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("params");
                }
                layoutParams12.y = statusBarHeight - view.getHeight();
                break;
        }
        WindowManager.LayoutParams layoutParams13 = this.params;
        if (layoutParams13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("params");
        }
        layoutParams13.x += this.config.getOffsetPair().getFirst().intValue();
        WindowManager.LayoutParams layoutParams14 = this.params;
        if (layoutParams14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("params");
        }
        layoutParams14.y += this.config.getOffsetPair().getSecond().intValue();
        WindowManager windowManager2 = this.windowManager;
        if (windowManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("windowManager");
        }
        WindowManager.LayoutParams layoutParams15 = this.params;
        if (layoutParams15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("params");
        }
        windowManager2.updateViewLayout(view, layoutParams15);
    }

    public static /* synthetic */ void setVisible$default(AppFloatManager appFloatManager, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        appFloatManager.setVisible(i, z);
    }

    public final void setVisible(int i, boolean z) {
        FloatCallbacks.Builder builder;
        Function1<View, Unit> hide$easyfloat_release;
        FloatCallbacks.Builder builder2;
        Function1<View, Unit> show$easyfloat_release;
        ParentFrameLayout parentFrameLayout = this.frameLayout;
        if (parentFrameLayout != null) {
            if (parentFrameLayout == null) {
                Intrinsics.throwNpe();
            }
            if (parentFrameLayout.getChildCount() < 1) {
                return;
            }
            this.config.setNeedShow$easyfloat_release(z);
            ParentFrameLayout parentFrameLayout2 = this.frameLayout;
            if (parentFrameLayout2 == null) {
                Intrinsics.throwNpe();
            }
            parentFrameLayout2.setVisibility(i);
            ParentFrameLayout parentFrameLayout3 = this.frameLayout;
            if (parentFrameLayout3 == null) {
                Intrinsics.throwNpe();
            }
            View view = parentFrameLayout3.getChildAt(0);
            if (i == 0) {
                this.config.setShow(true);
                OnFloatCallbacks callbacks = this.config.getCallbacks();
                if (callbacks != null) {
                    Intrinsics.checkExpressionValueIsNotNull(view, "view");
                    callbacks.show(view);
                }
                FloatCallbacks floatCallbacks = this.config.getFloatCallbacks();
                if (floatCallbacks == null || (builder2 = floatCallbacks.getBuilder()) == null || (show$easyfloat_release = builder2.getShow$easyfloat_release()) == null) {
                    return;
                }
                Intrinsics.checkExpressionValueIsNotNull(view, "view");
                show$easyfloat_release.invoke(view);
                return;
            }
            this.config.setShow(false);
            OnFloatCallbacks callbacks2 = this.config.getCallbacks();
            if (callbacks2 != null) {
                Intrinsics.checkExpressionValueIsNotNull(view, "view");
                callbacks2.hide(view);
            }
            FloatCallbacks floatCallbacks2 = this.config.getFloatCallbacks();
            if (floatCallbacks2 == null || (builder = floatCallbacks2.getBuilder()) == null || (hide$easyfloat_release = builder.getHide$easyfloat_release()) == null) {
                return;
            }
            Intrinsics.checkExpressionValueIsNotNull(view, "view");
            hide$easyfloat_release.invoke(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void enterAnim(View view) {
        if (this.frameLayout == null || this.config.isAnim()) {
            return;
        }
        ParentFrameLayout parentFrameLayout = this.frameLayout;
        if (parentFrameLayout == null) {
            Intrinsics.throwNpe();
        }
        ParentFrameLayout parentFrameLayout2 = parentFrameLayout;
        WindowManager.LayoutParams layoutParams = this.params;
        if (layoutParams == null) {
            Intrinsics.throwUninitializedPropertyAccessException("params");
        }
        WindowManager windowManager = this.windowManager;
        if (windowManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("windowManager");
        }
        new AppFloatAnimatorManager(parentFrameLayout2, layoutParams, windowManager, this.config).enterAnim();
        view.setVisibility(0);
        WindowManager windowManager2 = this.windowManager;
        if (windowManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("windowManager");
        }
        WindowManager.LayoutParams layoutParams2 = this.params;
        if (layoutParams2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("params");
        }
        windowManager2.updateViewLayout(view, layoutParams2);
        this.config.setAnim(false);
    }

    public final void exitAnim() {
        if (this.frameLayout == null || this.config.isAnim()) {
            return;
        }
        ParentFrameLayout parentFrameLayout = this.frameLayout;
        if (parentFrameLayout == null) {
            Intrinsics.throwNpe();
        }
        ParentFrameLayout parentFrameLayout2 = parentFrameLayout;
        WindowManager.LayoutParams layoutParams = this.params;
        if (layoutParams == null) {
            Intrinsics.throwUninitializedPropertyAccessException("params");
        }
        WindowManager windowManager = this.windowManager;
        if (windowManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("windowManager");
        }
        Animator exitAnim = new AppFloatAnimatorManager(parentFrameLayout2, layoutParams, windowManager, this.config).exitAnim();
        if (exitAnim == null) {
            floatOver();
            return;
        }
        WindowManager.LayoutParams layoutParams2 = this.params;
        if (layoutParams2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("params");
        }
        layoutParams2.flags = Videoio.CAP_PROP_XI_ACQ_TRANSPORT_BUFFER_COMMIT;
        exitAnim.addListener(new Animator.AnimatorListener() { // from class: com.lzf.easyfloat.widget.appfloat.AppFloatManager$exitAnim$1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                AppFloatManager.this.floatOver();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                AppFloatManager.this.getConfig().setAnim(true);
            }
        });
        exitAnim.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void floatOver() {
        try {
            this.config.setAnim(false);
            FloatManager.INSTANCE.remove(this.config.getFloatTag());
            WindowManager windowManager = this.windowManager;
            if (windowManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("windowManager");
            }
            windowManager.removeView(this.frameLayout);
        } catch (Exception e) {
            Logger logger = Logger.INSTANCE;
            logger.e("浮窗关闭出现异常：" + e);
        }
    }

    public final void updateFloat(final int i, final int i2) {
        final ParentFrameLayout parentFrameLayout = this.frameLayout;
        if (parentFrameLayout != null) {
            if (i == -1 && i2 == -1) {
                parentFrameLayout.postDelayed(new Runnable() { // from class: com.lzf.easyfloat.widget.appfloat.AppFloatManager$updateFloat$$inlined$let$lambda$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.getTouchUtils().updateFloat(ParentFrameLayout.this, this.getParams(), this.getWindowManager());
                    }
                }, 1L);
                return;
            }
            WindowManager.LayoutParams layoutParams = this.params;
            if (layoutParams == null) {
                Intrinsics.throwUninitializedPropertyAccessException("params");
            }
            layoutParams.x = i;
            WindowManager.LayoutParams layoutParams2 = this.params;
            if (layoutParams2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("params");
            }
            layoutParams2.y = i2;
            WindowManager windowManager = this.windowManager;
            if (windowManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("windowManager");
            }
            ParentFrameLayout parentFrameLayout2 = parentFrameLayout;
            WindowManager.LayoutParams layoutParams3 = this.params;
            if (layoutParams3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("params");
            }
            windowManager.updateViewLayout(parentFrameLayout2, layoutParams3);
        }
    }
}
