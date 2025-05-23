package com.lzf.easyfloat.anim;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.View;
import android.view.WindowManager;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.OnAppFloatAnimator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: AppFloatDefaultAnimator.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J*\u0010\r\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u0010"}, d2 = {"Lcom/lzf/easyfloat/anim/AppFloatDefaultAnimator;", "Lcom/lzf/easyfloat/interfaces/OnAppFloatAnimator;", "()V", "enterAnim", "Landroid/animation/Animator;", "view", "Landroid/view/View;", "params", "Landroid/view/WindowManager$LayoutParams;", "windowManager", "Landroid/view/WindowManager;", "sidePattern", "Lcom/lzf/easyfloat/enums/SidePattern;", "exitAnim", "initValue", "", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public class AppFloatDefaultAnimator implements OnAppFloatAnimator {
    @Override // com.lzf.easyfloat.interfaces.OnAppFloatAnimator
    public Animator enterAnim(final View view, final WindowManager.LayoutParams params, final WindowManager windowManager, SidePattern sidePattern) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(params, "params");
        Intrinsics.checkParameterIsNotNull(windowManager, "windowManager");
        Intrinsics.checkParameterIsNotNull(sidePattern, "sidePattern");
        ValueAnimator ofInt = ValueAnimator.ofInt(initValue(view, params, windowManager), params.x);
        ofInt.setDuration(500L);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lzf.easyfloat.anim.AppFloatDefaultAnimator$enterAnim$$inlined$apply$lambda$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator it) {
                WindowManager.LayoutParams layoutParams = params;
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                Object animatedValue = it.getAnimatedValue();
                if (animatedValue == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
                }
                layoutParams.x = ((Integer) animatedValue).intValue();
                windowManager.updateViewLayout(view, params);
            }
        });
        return ofInt;
    }

    @Override // com.lzf.easyfloat.interfaces.OnAppFloatAnimator
    public Animator exitAnim(final View view, final WindowManager.LayoutParams params, final WindowManager windowManager, SidePattern sidePattern) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(params, "params");
        Intrinsics.checkParameterIsNotNull(windowManager, "windowManager");
        Intrinsics.checkParameterIsNotNull(sidePattern, "sidePattern");
        ValueAnimator ofInt = ValueAnimator.ofInt(params.x, initValue(view, params, windowManager));
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lzf.easyfloat.anim.AppFloatDefaultAnimator$exitAnim$$inlined$apply$lambda$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator it) {
                WindowManager.LayoutParams layoutParams = params;
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                Object animatedValue = it.getAnimatedValue();
                if (animatedValue == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
                }
                layoutParams.x = ((Integer) animatedValue).intValue();
                windowManager.updateViewLayout(view, params);
            }
        });
        return ofInt;
    }

    private final int initValue(View view, WindowManager.LayoutParams layoutParams, WindowManager windowManager) {
        Rect rect = new Rect();
        windowManager.getDefaultDisplay().getRectSize(rect);
        int i = layoutParams.x;
        return i < rect.right - (view.getRight() + i) ? -view.getRight() : rect.right;
    }
}
