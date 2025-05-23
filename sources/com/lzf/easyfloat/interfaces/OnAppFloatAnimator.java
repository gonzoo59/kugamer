package com.lzf.easyfloat.interfaces;

import android.animation.Animator;
import android.view.View;
import android.view.WindowManager;
import com.lzf.easyfloat.enums.SidePattern;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: OnAppFloatAnimator.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J*\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J*\u0010\f\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\r"}, d2 = {"Lcom/lzf/easyfloat/interfaces/OnAppFloatAnimator;", "", "enterAnim", "Landroid/animation/Animator;", "view", "Landroid/view/View;", "params", "Landroid/view/WindowManager$LayoutParams;", "windowManager", "Landroid/view/WindowManager;", "sidePattern", "Lcom/lzf/easyfloat/enums/SidePattern;", "exitAnim", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public interface OnAppFloatAnimator {

    /* compiled from: OnAppFloatAnimator.kt */
    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public static final class DefaultImpls {
        public static Animator enterAnim(OnAppFloatAnimator onAppFloatAnimator, View view, WindowManager.LayoutParams params, WindowManager windowManager, SidePattern sidePattern) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(params, "params");
            Intrinsics.checkParameterIsNotNull(windowManager, "windowManager");
            Intrinsics.checkParameterIsNotNull(sidePattern, "sidePattern");
            return null;
        }

        public static Animator exitAnim(OnAppFloatAnimator onAppFloatAnimator, View view, WindowManager.LayoutParams params, WindowManager windowManager, SidePattern sidePattern) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(params, "params");
            Intrinsics.checkParameterIsNotNull(windowManager, "windowManager");
            Intrinsics.checkParameterIsNotNull(sidePattern, "sidePattern");
            return null;
        }
    }

    Animator enterAnim(View view, WindowManager.LayoutParams layoutParams, WindowManager windowManager, SidePattern sidePattern);

    Animator exitAnim(View view, WindowManager.LayoutParams layoutParams, WindowManager windowManager, SidePattern sidePattern);
}
