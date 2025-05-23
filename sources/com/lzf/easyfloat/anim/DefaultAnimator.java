package com.lzf.easyfloat.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.OnFloatAnimator;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: DefaultAnimator.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J*\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\"\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\"\u0010\u001a\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u001f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/lzf/easyfloat/anim/DefaultAnimator;", "Lcom/lzf/easyfloat/interfaces/OnFloatAnimator;", "()V", "bottomDistance", "", "floatRect", "Landroid/graphics/Rect;", "leftDistance", "minX", "minY", "parentRect", "rightDistance", "topDistance", "animTriple", "Lkotlin/Triple;", "", "", "view", "Landroid/view/View;", "sidePattern", "Lcom/lzf/easyfloat/enums/SidePattern;", "bottomValue", "enterAnim", "Landroid/animation/Animator;", "parentView", "Landroid/view/ViewGroup;", "exitAnim", "initValue", "", "leftValue", "rightValue", "topValue", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public class DefaultAnimator implements OnFloatAnimator {
    private int bottomDistance;
    private int leftDistance;
    private int minX;
    private int minY;
    private int rightDistance;
    private int topDistance;
    private Rect floatRect = new Rect();
    private Rect parentRect = new Rect();

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SidePattern.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[SidePattern.LEFT.ordinal()] = 1;
            iArr[SidePattern.RESULT_LEFT.ordinal()] = 2;
            iArr[SidePattern.RIGHT.ordinal()] = 3;
            iArr[SidePattern.RESULT_RIGHT.ordinal()] = 4;
            iArr[SidePattern.TOP.ordinal()] = 5;
            iArr[SidePattern.RESULT_TOP.ordinal()] = 6;
            iArr[SidePattern.BOTTOM.ordinal()] = 7;
            iArr[SidePattern.RESULT_BOTTOM.ordinal()] = 8;
            iArr[SidePattern.DEFAULT.ordinal()] = 9;
            iArr[SidePattern.AUTO_HORIZONTAL.ordinal()] = 10;
            iArr[SidePattern.RESULT_HORIZONTAL.ordinal()] = 11;
            iArr[SidePattern.AUTO_VERTICAL.ordinal()] = 12;
            iArr[SidePattern.RESULT_VERTICAL.ordinal()] = 13;
        }
    }

    @Override // com.lzf.easyfloat.interfaces.OnFloatAnimator
    public Animator enterAnim(View view, ViewGroup parentView, SidePattern sidePattern) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(parentView, "parentView");
        Intrinsics.checkParameterIsNotNull(sidePattern, "sidePattern");
        initValue(view, parentView);
        Triple<String, Float, Float> animTriple = animTriple(view, sidePattern);
        return ObjectAnimator.ofFloat(view, animTriple.component1(), animTriple.component2().floatValue(), animTriple.component3().floatValue()).setDuration(50L);
    }

    @Override // com.lzf.easyfloat.interfaces.OnFloatAnimator
    public Animator exitAnim(View view, ViewGroup parentView, SidePattern sidePattern) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(parentView, "parentView");
        Intrinsics.checkParameterIsNotNull(sidePattern, "sidePattern");
        initValue(view, parentView);
        Triple<String, Float, Float> animTriple = animTriple(view, sidePattern);
        return ObjectAnimator.ofFloat(view, animTriple.component1(), animTriple.component3().floatValue(), animTriple.component2().floatValue()).setDuration(50L);
    }

    private final Triple<String, Float, Float> animTriple(View view, SidePattern sidePattern) {
        float leftValue;
        String str = "translationY";
        switch (WhenMappings.$EnumSwitchMapping$0[sidePattern.ordinal()]) {
            case 1:
            case 2:
                leftValue = leftValue(view);
                str = "translationX";
                break;
            case 3:
            case 4:
                leftValue = rightValue(view);
                str = "translationX";
                break;
            case 5:
            case 6:
                leftValue = topValue(view);
                break;
            case 7:
            case 8:
                leftValue = rightValue(view);
                break;
            case 9:
            case 10:
            case 11:
                leftValue = this.leftDistance < this.rightDistance ? leftValue(view) : rightValue(view);
                str = "translationX";
                break;
            case 12:
            case 13:
                if (this.topDistance >= this.bottomDistance) {
                    leftValue = bottomValue(view);
                    break;
                } else {
                    leftValue = topValue(view);
                    break;
                }
            default:
                if (this.minX <= this.minY) {
                    leftValue = this.leftDistance < this.rightDistance ? leftValue(view) : rightValue(view);
                    str = "translationX";
                    break;
                } else if (this.topDistance >= this.bottomDistance) {
                    leftValue = bottomValue(view);
                    break;
                } else {
                    leftValue = topValue(view);
                    break;
                }
        }
        return new Triple<>(str, Float.valueOf(leftValue), Float.valueOf(Intrinsics.areEqual(str, "translationX") ? view.getTranslationX() : view.getTranslationY()));
    }

    private final float leftValue(View view) {
        return (-(this.leftDistance + view.getWidth())) + view.getTranslationX();
    }

    private final float rightValue(View view) {
        return this.rightDistance + view.getWidth() + view.getTranslationX();
    }

    private final float topValue(View view) {
        return (-(this.topDistance + view.getHeight())) + view.getTranslationY();
    }

    private final float bottomValue(View view) {
        return this.bottomDistance + view.getHeight() + view.getTranslationY();
    }

    private final void initValue(View view, ViewGroup viewGroup) {
        view.getGlobalVisibleRect(this.floatRect);
        viewGroup.getGlobalVisibleRect(this.parentRect);
        this.leftDistance = this.floatRect.left;
        this.rightDistance = this.parentRect.right - this.floatRect.right;
        this.topDistance = this.floatRect.top - this.parentRect.top;
        this.bottomDistance = this.parentRect.bottom - this.floatRect.bottom;
        this.minX = Math.min(this.leftDistance, this.rightDistance);
        this.minY = Math.min(this.topDistance, this.bottomDistance);
    }
}
