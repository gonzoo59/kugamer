package com.lzf.easyfloat.interfaces;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;
import com.lzf.easyfloat.enums.SidePattern;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: OnFloatAnimator.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\"\u0010\n\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\u000b"}, d2 = {"Lcom/lzf/easyfloat/interfaces/OnFloatAnimator;", "", "enterAnim", "Landroid/animation/Animator;", "view", "Landroid/view/View;", "parentView", "Landroid/view/ViewGroup;", "sidePattern", "Lcom/lzf/easyfloat/enums/SidePattern;", "exitAnim", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public interface OnFloatAnimator {

    /* compiled from: OnFloatAnimator.kt */
    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public static final class DefaultImpls {
        public static Animator enterAnim(OnFloatAnimator onFloatAnimator, View view, ViewGroup parentView, SidePattern sidePattern) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(parentView, "parentView");
            Intrinsics.checkParameterIsNotNull(sidePattern, "sidePattern");
            return null;
        }

        public static Animator exitAnim(OnFloatAnimator onFloatAnimator, View view, ViewGroup parentView, SidePattern sidePattern) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(parentView, "parentView");
            Intrinsics.checkParameterIsNotNull(sidePattern, "sidePattern");
            return null;
        }
    }

    Animator enterAnim(View view, ViewGroup viewGroup, SidePattern sidePattern);

    Animator exitAnim(View view, ViewGroup viewGroup, SidePattern sidePattern);
}
