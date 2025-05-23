package com.lzf.easyfloat.widget.activityfloat;

import android.app.Activity;
import android.content.ComponentName;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.interfaces.FloatCallbacks;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: ActivityFloatManager.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0017\u0010\r\u001a\u0004\u0018\u00010\n2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002J\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u001a\u0010\u0015\u001a\n \u0016*\u0004\u0018\u00010\u000f0\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002J\u0012\u0010\u0017\u001a\u00020\u00182\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u001a\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u00182\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u001a\u0010\u001b\u001a\u0004\u0018\u00010\u00122\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u001c\u001a\u00020\u001dR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/lzf/easyfloat/widget/activityfloat/ActivityFloatManager;", "", "activity", "Landroid/app/Activity;", "(Landroid/app/Activity;)V", "getActivity", "()Landroid/app/Activity;", "parentFrame", "Landroid/widget/FrameLayout;", "createFloat", "", "config", "Lcom/lzf/easyfloat/data/FloatConfig;", "dismiss", "tag", "", "(Ljava/lang/String;)Lkotlin/Unit;", "floatingView", "Lcom/lzf/easyfloat/widget/activityfloat/FloatingView;", "getFloatView", "Landroid/view/View;", "getTag", "kotlin.jvm.PlatformType", "isShow", "", "setDragEnable", "dragEnable", "setVisibility", "visibility", "", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class ActivityFloatManager {
    private final Activity activity;
    private FrameLayout parentFrame;

    public ActivityFloatManager(Activity activity) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        this.activity = activity;
        Window window = activity.getWindow();
        Intrinsics.checkExpressionValueIsNotNull(window, "activity.window");
        View findViewById = window.getDecorView().findViewById(16908290);
        Intrinsics.checkExpressionValueIsNotNull(findViewById, "activity.window.decorVie…yId(android.R.id.content)");
        this.parentFrame = (FrameLayout) findViewById;
    }

    public final Activity getActivity() {
        return this.activity;
    }

    public final void createFloat(FloatConfig config) {
        FloatCallbacks.Builder builder;
        Function3<Boolean, String, View, Unit> createdResult$easyfloat_release;
        Intrinsics.checkParameterIsNotNull(config, "config");
        FloatingView floatingView = new FloatingView(this.activity, null, 2, null);
        floatingView.setTag(getTag(config.getFloatTag()));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(config.getWidthMatch() ? -1 : -2, config.getHeightMatch() ? -1 : -2);
        if (Intrinsics.areEqual(config.getLocationPair(), new Pair(0, 0))) {
            layoutParams.gravity = config.getGravity();
        }
        floatingView.setLayoutParams(layoutParams);
        floatingView.setFloatConfig(config);
        FloatingView floatingView2 = floatingView;
        this.parentFrame.addView(floatingView2);
        config.setLayoutView(floatingView2);
        OnFloatCallbacks callbacks = config.getCallbacks();
        if (callbacks != null) {
            callbacks.createdResult(true, null, floatingView2);
        }
        FloatCallbacks floatCallbacks = config.getFloatCallbacks();
        if (floatCallbacks == null || (builder = floatCallbacks.getBuilder()) == null || (createdResult$easyfloat_release = builder.getCreatedResult$easyfloat_release()) == null) {
            return;
        }
        createdResult$easyfloat_release.invoke(true, null, floatingView);
    }

    public final Unit dismiss(String str) {
        FloatingView floatingView = floatingView(str);
        if (floatingView != null) {
            floatingView.exitAnim$easyfloat_release();
            return Unit.INSTANCE;
        }
        return null;
    }

    public final FloatingView setVisibility(String str, int i) {
        FloatCallbacks.Builder builder;
        Function1<View, Unit> show$easyfloat_release;
        FloatCallbacks.Builder builder2;
        Function1<View, Unit> hide$easyfloat_release;
        FloatingView floatingView = floatingView(str);
        if (floatingView != null) {
            floatingView.setVisibility(i);
            if (i == 8) {
                OnFloatCallbacks callbacks = floatingView.getConfig().getCallbacks();
                if (callbacks != null) {
                    callbacks.hide(floatingView);
                }
                FloatCallbacks floatCallbacks = floatingView.getConfig().getFloatCallbacks();
                if (floatCallbacks == null || (builder2 = floatCallbacks.getBuilder()) == null || (hide$easyfloat_release = builder2.getHide$easyfloat_release()) == null) {
                    return floatingView;
                }
                hide$easyfloat_release.invoke(floatingView);
                return floatingView;
            }
            OnFloatCallbacks callbacks2 = floatingView.getConfig().getCallbacks();
            if (callbacks2 != null) {
                callbacks2.show(floatingView);
            }
            FloatCallbacks floatCallbacks2 = floatingView.getConfig().getFloatCallbacks();
            if (floatCallbacks2 == null || (builder = floatCallbacks2.getBuilder()) == null || (show$easyfloat_release = builder.getShow$easyfloat_release()) == null) {
                return floatingView;
            }
            show$easyfloat_release.invoke(floatingView);
            return floatingView;
        }
        return null;
    }

    public static /* synthetic */ boolean isShow$default(ActivityFloatManager activityFloatManager, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        return activityFloatManager.isShow(str);
    }

    public final boolean isShow(String str) {
        FloatingView floatingView = floatingView(str);
        return floatingView != null && floatingView.getVisibility() == 0;
    }

    public static /* synthetic */ void setDragEnable$default(ActivityFloatManager activityFloatManager, boolean z, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        activityFloatManager.setDragEnable(z, str);
    }

    public final void setDragEnable(boolean z, String str) {
        FloatConfig config;
        FloatingView floatingView = floatingView(str);
        if (floatingView == null || (config = floatingView.getConfig()) == null) {
            return;
        }
        config.setDragEnable(z);
    }

    public static /* synthetic */ View getFloatView$default(ActivityFloatManager activityFloatManager, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        return activityFloatManager.getFloatView(str);
    }

    public final View getFloatView(String str) {
        FloatConfig config;
        FloatingView floatingView = floatingView(str);
        if (floatingView == null || (config = floatingView.getConfig()) == null) {
            return null;
        }
        return config.getLayoutView();
    }

    private final FloatingView floatingView(String str) {
        return (FloatingView) this.parentFrame.findViewWithTag(getTag(str));
    }

    private final String getTag(String str) {
        if (str != null) {
            return str;
        }
        ComponentName componentName = this.activity.getComponentName();
        Intrinsics.checkExpressionValueIsNotNull(componentName, "activity.componentName");
        return componentName.getClassName();
    }
}
