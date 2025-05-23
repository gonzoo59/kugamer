package com.lzf.easyfloat.anim;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.OnFloatAnimator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: AnimatorManager.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B'\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ\b\u0010\r\u001a\u0004\u0018\u00010\fR\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/lzf/easyfloat/anim/AnimatorManager;", "", "onFloatAnimator", "Lcom/lzf/easyfloat/interfaces/OnFloatAnimator;", "view", "Landroid/view/View;", "parentView", "Landroid/view/ViewGroup;", "sidePattern", "Lcom/lzf/easyfloat/enums/SidePattern;", "(Lcom/lzf/easyfloat/interfaces/OnFloatAnimator;Landroid/view/View;Landroid/view/ViewGroup;Lcom/lzf/easyfloat/enums/SidePattern;)V", "enterAnim", "Landroid/animation/Animator;", "exitAnim", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class AnimatorManager {
    private final OnFloatAnimator onFloatAnimator;
    private final ViewGroup parentView;
    private final SidePattern sidePattern;
    private final View view;

    public AnimatorManager(OnFloatAnimator onFloatAnimator, View view, ViewGroup parentView, SidePattern sidePattern) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(parentView, "parentView");
        Intrinsics.checkParameterIsNotNull(sidePattern, "sidePattern");
        this.onFloatAnimator = onFloatAnimator;
        this.view = view;
        this.parentView = parentView;
        this.sidePattern = sidePattern;
    }

    public final Animator enterAnim() {
        OnFloatAnimator onFloatAnimator = this.onFloatAnimator;
        if (onFloatAnimator != null) {
            return onFloatAnimator.enterAnim(this.view, this.parentView, this.sidePattern);
        }
        return null;
    }

    public final Animator exitAnim() {
        OnFloatAnimator onFloatAnimator = this.onFloatAnimator;
        if (onFloatAnimator != null) {
            return onFloatAnimator.exitAnim(this.view, this.parentView, this.sidePattern);
        }
        return null;
    }
}
