package com.lzf.easyfloat.anim;

import android.animation.Animator;
import android.view.View;
import android.view.WindowManager;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.interfaces.OnAppFloatAnimator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: AppFloatAnimatorManager.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ\b\u0010\r\u001a\u0004\u0018\u00010\fR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/lzf/easyfloat/anim/AppFloatAnimatorManager;", "", "view", "Landroid/view/View;", "params", "Landroid/view/WindowManager$LayoutParams;", "windowManager", "Landroid/view/WindowManager;", "config", "Lcom/lzf/easyfloat/data/FloatConfig;", "(Landroid/view/View;Landroid/view/WindowManager$LayoutParams;Landroid/view/WindowManager;Lcom/lzf/easyfloat/data/FloatConfig;)V", "enterAnim", "Landroid/animation/Animator;", "exitAnim", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class AppFloatAnimatorManager {
    private final FloatConfig config;
    private final WindowManager.LayoutParams params;
    private final View view;
    private final WindowManager windowManager;

    public AppFloatAnimatorManager(View view, WindowManager.LayoutParams params, WindowManager windowManager, FloatConfig config) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(params, "params");
        Intrinsics.checkParameterIsNotNull(windowManager, "windowManager");
        Intrinsics.checkParameterIsNotNull(config, "config");
        this.view = view;
        this.params = params;
        this.windowManager = windowManager;
        this.config = config;
    }

    public final Animator enterAnim() {
        OnAppFloatAnimator appFloatAnimator = this.config.getAppFloatAnimator();
        if (appFloatAnimator != null) {
            return appFloatAnimator.enterAnim(this.view, this.params, this.windowManager, this.config.getSidePattern());
        }
        return null;
    }

    public final Animator exitAnim() {
        OnAppFloatAnimator appFloatAnimator = this.config.getAppFloatAnimator();
        if (appFloatAnimator != null) {
            return appFloatAnimator.exitAnim(this.view, this.params, this.windowManager, this.config.getSidePattern());
        }
        return null;
    }
}
