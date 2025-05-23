package com.lzf.easyfloat.data;

import android.view.View;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.enums.SidePattern;
import com.lzf.easyfloat.interfaces.FloatCallbacks;
import com.lzf.easyfloat.interfaces.OnAppFloatAnimator;
import com.lzf.easyfloat.interfaces.OnDisplayHeight;
import com.lzf.easyfloat.interfaces.OnFloatAnimator;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: FloatConfig.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\bn\b\u0086\b\u0018\u00002\u00020\u0001B£\u0002\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\b\b\u0002\u0010\f\u001a\u00020\t\u0012\b\b\u0002\u0010\r\u001a\u00020\t\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\t\u0012\b\b\u0002\u0010\u0013\u001a\u00020\t\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003\u0012\u0014\b\u0002\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0016\u0012\u0014\b\u0002\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0016\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0019\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u001b\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u001d\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001f\u0012\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!\u0012\b\b\u0002\u0010\"\u001a\u00020#\u0012\u000e\b\u0002\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00070%\u0012\b\b\u0002\u0010&\u001a\u00020\t\u0012\b\b\u0002\u0010'\u001a\u00020\t¢\u0006\u0002\u0010(J\u0010\u0010s\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010YJ\t\u0010t\u001a\u00020\u0011HÆ\u0003J\t\u0010u\u001a\u00020\tHÆ\u0003J\t\u0010v\u001a\u00020\tHÆ\u0003J\t\u0010w\u001a\u00020\u0003HÆ\u0003J\u0015\u0010x\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0016HÆ\u0003J\u0015\u0010y\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0016HÆ\u0003J\u000b\u0010z\u001a\u0004\u0018\u00010\u0019HÆ\u0003J\u000b\u0010{\u001a\u0004\u0018\u00010\u001bHÆ\u0003J\u000b\u0010|\u001a\u0004\u0018\u00010\u001dHÆ\u0003J\u000b\u0010}\u001a\u0004\u0018\u00010\u001fHÆ\u0003J\u000b\u0010~\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u007f\u001a\u0004\u0018\u00010!HÆ\u0003J\n\u0010\u0080\u0001\u001a\u00020#HÆ\u0003J\u0010\u0010\u0081\u0001\u001a\b\u0012\u0004\u0012\u00020\u00070%HÆ\u0003J\u0010\u0010\u0082\u0001\u001a\u00020\tHÀ\u0003¢\u0006\u0003\b\u0083\u0001J\u0010\u0010\u0084\u0001\u001a\u00020\tHÀ\u0003¢\u0006\u0003\b\u0085\u0001J\f\u0010\u0086\u0001\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\n\u0010\u0087\u0001\u001a\u00020\tHÆ\u0003J\n\u0010\u0088\u0001\u001a\u00020\tHÆ\u0003J\n\u0010\u0089\u0001\u001a\u00020\tHÆ\u0003J\n\u0010\u008a\u0001\u001a\u00020\tHÆ\u0003J\n\u0010\u008b\u0001\u001a\u00020\tHÆ\u0003J\n\u0010\u008c\u0001\u001a\u00020\u000fHÆ\u0003J®\u0002\u0010\u008d\u0001\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\t2\b\b\u0002\u0010\r\u001a\u00020\t2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\t2\b\b\u0002\u0010\u0013\u001a\u00020\t2\b\b\u0002\u0010\u0014\u001a\u00020\u00032\u0014\b\u0002\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00162\u0014\b\u0002\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00162\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00192\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!2\b\b\u0002\u0010\"\u001a\u00020#2\u000e\b\u0002\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00070%2\b\b\u0002\u0010&\u001a\u00020\t2\b\b\u0002\u0010'\u001a\u00020\tHÆ\u0001¢\u0006\u0003\u0010\u008e\u0001J\u0015\u0010\u008f\u0001\u001a\u00020\t2\t\u0010\u0090\u0001\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\n\u0010\u0091\u0001\u001a\u00020\u0003HÖ\u0001J\n\u0010\u0092\u0001\u001a\u00020\u0007HÖ\u0001R\u001c\u0010 \u001a\u0004\u0018\u00010!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001a\u0010\"\u001a\u00020#X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u001a\u0010&\u001a\u00020\tX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u00106\"\u0004\b:\u00108R\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00070%¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010B\"\u0004\bC\u0010DR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010F\"\u0004\bG\u0010HR\u001a\u0010\u0014\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010J\"\u0004\bK\u0010LR\u001a\u0010\r\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bM\u00106\"\u0004\bN\u00108R\u001a\u0010\u0013\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u00106\"\u0004\bP\u00108R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010R\"\u0004\bS\u0010TR\u001a\u0010\u000b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u00106\"\u0004\bU\u00108R\u001a\u0010\n\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u00106\"\u0004\bV\u00108R\u001a\u0010\f\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u00106\"\u0004\bW\u00108R\u001e\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\\\u001a\u0004\bX\u0010Y\"\u0004\bZ\u0010[R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b]\u0010^\"\u0004\b_\u0010`R&\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\ba\u0010b\"\u0004\bc\u0010dR\u001a\u0010'\u001a\u00020\tX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\be\u00106\"\u0004\bf\u00108R&\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bg\u0010b\"\u0004\bh\u0010dR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bi\u0010j\"\u0004\bk\u0010lR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bm\u0010n\"\u0004\bo\u0010pR\u001a\u0010\u0012\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bq\u00106\"\u0004\br\u00108¨\u0006\u0093\u0001"}, d2 = {"Lcom/lzf/easyfloat/data/FloatConfig;", "", "layoutId", "", "layoutView", "Landroid/view/View;", "floatTag", "", "dragEnable", "", "isDrag", "isAnim", "isShow", "hasEditText", "sidePattern", "Lcom/lzf/easyfloat/enums/SidePattern;", "showPattern", "Lcom/lzf/easyfloat/enums/ShowPattern;", "widthMatch", "heightMatch", "gravity", "offsetPair", "Lkotlin/Pair;", "locationPair", "invokeView", "Lcom/lzf/easyfloat/interfaces/OnInvokeView;", "callbacks", "Lcom/lzf/easyfloat/interfaces/OnFloatCallbacks;", "floatCallbacks", "Lcom/lzf/easyfloat/interfaces/FloatCallbacks;", "floatAnimator", "Lcom/lzf/easyfloat/interfaces/OnFloatAnimator;", "appFloatAnimator", "Lcom/lzf/easyfloat/interfaces/OnAppFloatAnimator;", "displayHeight", "Lcom/lzf/easyfloat/interfaces/OnDisplayHeight;", "filterSet", "", "filterSelf", "needShow", "(Ljava/lang/Integer;Landroid/view/View;Ljava/lang/String;ZZZZZLcom/lzf/easyfloat/enums/SidePattern;Lcom/lzf/easyfloat/enums/ShowPattern;ZZILkotlin/Pair;Lkotlin/Pair;Lcom/lzf/easyfloat/interfaces/OnInvokeView;Lcom/lzf/easyfloat/interfaces/OnFloatCallbacks;Lcom/lzf/easyfloat/interfaces/FloatCallbacks;Lcom/lzf/easyfloat/interfaces/OnFloatAnimator;Lcom/lzf/easyfloat/interfaces/OnAppFloatAnimator;Lcom/lzf/easyfloat/interfaces/OnDisplayHeight;Ljava/util/Set;ZZ)V", "getAppFloatAnimator", "()Lcom/lzf/easyfloat/interfaces/OnAppFloatAnimator;", "setAppFloatAnimator", "(Lcom/lzf/easyfloat/interfaces/OnAppFloatAnimator;)V", "getCallbacks", "()Lcom/lzf/easyfloat/interfaces/OnFloatCallbacks;", "setCallbacks", "(Lcom/lzf/easyfloat/interfaces/OnFloatCallbacks;)V", "getDisplayHeight", "()Lcom/lzf/easyfloat/interfaces/OnDisplayHeight;", "setDisplayHeight", "(Lcom/lzf/easyfloat/interfaces/OnDisplayHeight;)V", "getDragEnable", "()Z", "setDragEnable", "(Z)V", "getFilterSelf$easyfloat_release", "setFilterSelf$easyfloat_release", "getFilterSet", "()Ljava/util/Set;", "getFloatAnimator", "()Lcom/lzf/easyfloat/interfaces/OnFloatAnimator;", "setFloatAnimator", "(Lcom/lzf/easyfloat/interfaces/OnFloatAnimator;)V", "getFloatCallbacks", "()Lcom/lzf/easyfloat/interfaces/FloatCallbacks;", "setFloatCallbacks", "(Lcom/lzf/easyfloat/interfaces/FloatCallbacks;)V", "getFloatTag", "()Ljava/lang/String;", "setFloatTag", "(Ljava/lang/String;)V", "getGravity", "()I", "setGravity", "(I)V", "getHasEditText", "setHasEditText", "getHeightMatch", "setHeightMatch", "getInvokeView", "()Lcom/lzf/easyfloat/interfaces/OnInvokeView;", "setInvokeView", "(Lcom/lzf/easyfloat/interfaces/OnInvokeView;)V", "setAnim", "setDrag", "setShow", "getLayoutId", "()Ljava/lang/Integer;", "setLayoutId", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getLayoutView", "()Landroid/view/View;", "setLayoutView", "(Landroid/view/View;)V", "getLocationPair", "()Lkotlin/Pair;", "setLocationPair", "(Lkotlin/Pair;)V", "getNeedShow$easyfloat_release", "setNeedShow$easyfloat_release", "getOffsetPair", "setOffsetPair", "getShowPattern", "()Lcom/lzf/easyfloat/enums/ShowPattern;", "setShowPattern", "(Lcom/lzf/easyfloat/enums/ShowPattern;)V", "getSidePattern", "()Lcom/lzf/easyfloat/enums/SidePattern;", "setSidePattern", "(Lcom/lzf/easyfloat/enums/SidePattern;)V", "getWidthMatch", "setWidthMatch", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component23$easyfloat_release", "component24", "component24$easyfloat_release", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Landroid/view/View;Ljava/lang/String;ZZZZZLcom/lzf/easyfloat/enums/SidePattern;Lcom/lzf/easyfloat/enums/ShowPattern;ZZILkotlin/Pair;Lkotlin/Pair;Lcom/lzf/easyfloat/interfaces/OnInvokeView;Lcom/lzf/easyfloat/interfaces/OnFloatCallbacks;Lcom/lzf/easyfloat/interfaces/FloatCallbacks;Lcom/lzf/easyfloat/interfaces/OnFloatAnimator;Lcom/lzf/easyfloat/interfaces/OnAppFloatAnimator;Lcom/lzf/easyfloat/interfaces/OnDisplayHeight;Ljava/util/Set;ZZ)Lcom/lzf/easyfloat/data/FloatConfig;", "equals", "other", "hashCode", "toString", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class FloatConfig {
    private OnAppFloatAnimator appFloatAnimator;
    private OnFloatCallbacks callbacks;
    private OnDisplayHeight displayHeight;
    private boolean dragEnable;
    private boolean filterSelf;
    private final Set<String> filterSet;
    private OnFloatAnimator floatAnimator;
    private FloatCallbacks floatCallbacks;
    private String floatTag;
    private int gravity;
    private boolean hasEditText;
    private boolean heightMatch;
    private OnInvokeView invokeView;
    private boolean isAnim;
    private boolean isDrag;
    private boolean isShow;
    private Integer layoutId;
    private View layoutView;
    private Pair<Integer, Integer> locationPair;
    private boolean needShow;
    private Pair<Integer, Integer> offsetPair;
    private ShowPattern showPattern;
    private SidePattern sidePattern;
    private boolean widthMatch;

    public FloatConfig() {
        this(null, null, null, false, false, false, false, false, null, null, false, false, 0, null, null, null, null, null, null, null, null, null, false, false, 16777215, null);
    }

    public final Integer component1() {
        return this.layoutId;
    }

    public final ShowPattern component10() {
        return this.showPattern;
    }

    public final boolean component11() {
        return this.widthMatch;
    }

    public final boolean component12() {
        return this.heightMatch;
    }

    public final int component13() {
        return this.gravity;
    }

    public final Pair<Integer, Integer> component14() {
        return this.offsetPair;
    }

    public final Pair<Integer, Integer> component15() {
        return this.locationPair;
    }

    public final OnInvokeView component16() {
        return this.invokeView;
    }

    public final OnFloatCallbacks component17() {
        return this.callbacks;
    }

    public final FloatCallbacks component18() {
        return this.floatCallbacks;
    }

    public final OnFloatAnimator component19() {
        return this.floatAnimator;
    }

    public final View component2() {
        return this.layoutView;
    }

    public final OnAppFloatAnimator component20() {
        return this.appFloatAnimator;
    }

    public final OnDisplayHeight component21() {
        return this.displayHeight;
    }

    public final Set<String> component22() {
        return this.filterSet;
    }

    public final boolean component23$easyfloat_release() {
        return this.filterSelf;
    }

    public final boolean component24$easyfloat_release() {
        return this.needShow;
    }

    public final String component3() {
        return this.floatTag;
    }

    public final boolean component4() {
        return this.dragEnable;
    }

    public final boolean component5() {
        return this.isDrag;
    }

    public final boolean component6() {
        return this.isAnim;
    }

    public final boolean component7() {
        return this.isShow;
    }

    public final boolean component8() {
        return this.hasEditText;
    }

    public final SidePattern component9() {
        return this.sidePattern;
    }

    public final FloatConfig copy(Integer num, View view, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, SidePattern sidePattern, ShowPattern showPattern, boolean z6, boolean z7, int i, Pair<Integer, Integer> offsetPair, Pair<Integer, Integer> locationPair, OnInvokeView onInvokeView, OnFloatCallbacks onFloatCallbacks, FloatCallbacks floatCallbacks, OnFloatAnimator onFloatAnimator, OnAppFloatAnimator onAppFloatAnimator, OnDisplayHeight displayHeight, Set<String> filterSet, boolean z8, boolean z9) {
        Intrinsics.checkParameterIsNotNull(sidePattern, "sidePattern");
        Intrinsics.checkParameterIsNotNull(showPattern, "showPattern");
        Intrinsics.checkParameterIsNotNull(offsetPair, "offsetPair");
        Intrinsics.checkParameterIsNotNull(locationPair, "locationPair");
        Intrinsics.checkParameterIsNotNull(displayHeight, "displayHeight");
        Intrinsics.checkParameterIsNotNull(filterSet, "filterSet");
        return new FloatConfig(num, view, str, z, z2, z3, z4, z5, sidePattern, showPattern, z6, z7, i, offsetPair, locationPair, onInvokeView, onFloatCallbacks, floatCallbacks, onFloatAnimator, onAppFloatAnimator, displayHeight, filterSet, z8, z9);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof FloatConfig) {
                FloatConfig floatConfig = (FloatConfig) obj;
                return Intrinsics.areEqual(this.layoutId, floatConfig.layoutId) && Intrinsics.areEqual(this.layoutView, floatConfig.layoutView) && Intrinsics.areEqual(this.floatTag, floatConfig.floatTag) && this.dragEnable == floatConfig.dragEnable && this.isDrag == floatConfig.isDrag && this.isAnim == floatConfig.isAnim && this.isShow == floatConfig.isShow && this.hasEditText == floatConfig.hasEditText && Intrinsics.areEqual(this.sidePattern, floatConfig.sidePattern) && Intrinsics.areEqual(this.showPattern, floatConfig.showPattern) && this.widthMatch == floatConfig.widthMatch && this.heightMatch == floatConfig.heightMatch && this.gravity == floatConfig.gravity && Intrinsics.areEqual(this.offsetPair, floatConfig.offsetPair) && Intrinsics.areEqual(this.locationPair, floatConfig.locationPair) && Intrinsics.areEqual(this.invokeView, floatConfig.invokeView) && Intrinsics.areEqual(this.callbacks, floatConfig.callbacks) && Intrinsics.areEqual(this.floatCallbacks, floatConfig.floatCallbacks) && Intrinsics.areEqual(this.floatAnimator, floatConfig.floatAnimator) && Intrinsics.areEqual(this.appFloatAnimator, floatConfig.appFloatAnimator) && Intrinsics.areEqual(this.displayHeight, floatConfig.displayHeight) && Intrinsics.areEqual(this.filterSet, floatConfig.filterSet) && this.filterSelf == floatConfig.filterSelf && this.needShow == floatConfig.needShow;
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        Integer num = this.layoutId;
        int hashCode = (num != null ? num.hashCode() : 0) * 31;
        View view = this.layoutView;
        int hashCode2 = (hashCode + (view != null ? view.hashCode() : 0)) * 31;
        String str = this.floatTag;
        int hashCode3 = (hashCode2 + (str != null ? str.hashCode() : 0)) * 31;
        boolean z = this.dragEnable;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode3 + i) * 31;
        boolean z2 = this.isDrag;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.isAnim;
        int i5 = z3;
        if (z3 != 0) {
            i5 = 1;
        }
        int i6 = (i4 + i5) * 31;
        boolean z4 = this.isShow;
        int i7 = z4;
        if (z4 != 0) {
            i7 = 1;
        }
        int i8 = (i6 + i7) * 31;
        boolean z5 = this.hasEditText;
        int i9 = z5;
        if (z5 != 0) {
            i9 = 1;
        }
        int i10 = (i8 + i9) * 31;
        SidePattern sidePattern = this.sidePattern;
        int hashCode4 = (i10 + (sidePattern != null ? sidePattern.hashCode() : 0)) * 31;
        ShowPattern showPattern = this.showPattern;
        int hashCode5 = (hashCode4 + (showPattern != null ? showPattern.hashCode() : 0)) * 31;
        boolean z6 = this.widthMatch;
        int i11 = z6;
        if (z6 != 0) {
            i11 = 1;
        }
        int i12 = (hashCode5 + i11) * 31;
        boolean z7 = this.heightMatch;
        int i13 = z7;
        if (z7 != 0) {
            i13 = 1;
        }
        int i14 = (((i12 + i13) * 31) + this.gravity) * 31;
        Pair<Integer, Integer> pair = this.offsetPair;
        int hashCode6 = (i14 + (pair != null ? pair.hashCode() : 0)) * 31;
        Pair<Integer, Integer> pair2 = this.locationPair;
        int hashCode7 = (hashCode6 + (pair2 != null ? pair2.hashCode() : 0)) * 31;
        OnInvokeView onInvokeView = this.invokeView;
        int hashCode8 = (hashCode7 + (onInvokeView != null ? onInvokeView.hashCode() : 0)) * 31;
        OnFloatCallbacks onFloatCallbacks = this.callbacks;
        int hashCode9 = (hashCode8 + (onFloatCallbacks != null ? onFloatCallbacks.hashCode() : 0)) * 31;
        FloatCallbacks floatCallbacks = this.floatCallbacks;
        int hashCode10 = (hashCode9 + (floatCallbacks != null ? floatCallbacks.hashCode() : 0)) * 31;
        OnFloatAnimator onFloatAnimator = this.floatAnimator;
        int hashCode11 = (hashCode10 + (onFloatAnimator != null ? onFloatAnimator.hashCode() : 0)) * 31;
        OnAppFloatAnimator onAppFloatAnimator = this.appFloatAnimator;
        int hashCode12 = (hashCode11 + (onAppFloatAnimator != null ? onAppFloatAnimator.hashCode() : 0)) * 31;
        OnDisplayHeight onDisplayHeight = this.displayHeight;
        int hashCode13 = (hashCode12 + (onDisplayHeight != null ? onDisplayHeight.hashCode() : 0)) * 31;
        Set<String> set = this.filterSet;
        int hashCode14 = (hashCode13 + (set != null ? set.hashCode() : 0)) * 31;
        boolean z8 = this.filterSelf;
        int i15 = z8;
        if (z8 != 0) {
            i15 = 1;
        }
        int i16 = (hashCode14 + i15) * 31;
        boolean z9 = this.needShow;
        return i16 + (z9 ? 1 : z9 ? 1 : 0);
    }

    public String toString() {
        return "FloatConfig(layoutId=" + this.layoutId + ", layoutView=" + this.layoutView + ", floatTag=" + this.floatTag + ", dragEnable=" + this.dragEnable + ", isDrag=" + this.isDrag + ", isAnim=" + this.isAnim + ", isShow=" + this.isShow + ", hasEditText=" + this.hasEditText + ", sidePattern=" + this.sidePattern + ", showPattern=" + this.showPattern + ", widthMatch=" + this.widthMatch + ", heightMatch=" + this.heightMatch + ", gravity=" + this.gravity + ", offsetPair=" + this.offsetPair + ", locationPair=" + this.locationPair + ", invokeView=" + this.invokeView + ", callbacks=" + this.callbacks + ", floatCallbacks=" + this.floatCallbacks + ", floatAnimator=" + this.floatAnimator + ", appFloatAnimator=" + this.appFloatAnimator + ", displayHeight=" + this.displayHeight + ", filterSet=" + this.filterSet + ", filterSelf=" + this.filterSelf + ", needShow=" + this.needShow + ")";
    }

    public FloatConfig(Integer num, View view, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, SidePattern sidePattern, ShowPattern showPattern, boolean z6, boolean z7, int i, Pair<Integer, Integer> offsetPair, Pair<Integer, Integer> locationPair, OnInvokeView onInvokeView, OnFloatCallbacks onFloatCallbacks, FloatCallbacks floatCallbacks, OnFloatAnimator onFloatAnimator, OnAppFloatAnimator onAppFloatAnimator, OnDisplayHeight displayHeight, Set<String> filterSet, boolean z8, boolean z9) {
        Intrinsics.checkParameterIsNotNull(sidePattern, "sidePattern");
        Intrinsics.checkParameterIsNotNull(showPattern, "showPattern");
        Intrinsics.checkParameterIsNotNull(offsetPair, "offsetPair");
        Intrinsics.checkParameterIsNotNull(locationPair, "locationPair");
        Intrinsics.checkParameterIsNotNull(displayHeight, "displayHeight");
        Intrinsics.checkParameterIsNotNull(filterSet, "filterSet");
        this.layoutId = num;
        this.layoutView = view;
        this.floatTag = str;
        this.dragEnable = z;
        this.isDrag = z2;
        this.isAnim = z3;
        this.isShow = z4;
        this.hasEditText = z5;
        this.sidePattern = sidePattern;
        this.showPattern = showPattern;
        this.widthMatch = z6;
        this.heightMatch = z7;
        this.gravity = i;
        this.offsetPair = offsetPair;
        this.locationPair = locationPair;
        this.invokeView = onInvokeView;
        this.callbacks = onFloatCallbacks;
        this.floatCallbacks = floatCallbacks;
        this.floatAnimator = onFloatAnimator;
        this.appFloatAnimator = onAppFloatAnimator;
        this.displayHeight = displayHeight;
        this.filterSet = filterSet;
        this.filterSelf = z8;
        this.needShow = z9;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ FloatConfig(java.lang.Integer r26, android.view.View r27, java.lang.String r28, boolean r29, boolean r30, boolean r31, boolean r32, boolean r33, com.lzf.easyfloat.enums.SidePattern r34, com.lzf.easyfloat.enums.ShowPattern r35, boolean r36, boolean r37, int r38, kotlin.Pair r39, kotlin.Pair r40, com.lzf.easyfloat.interfaces.OnInvokeView r41, com.lzf.easyfloat.interfaces.OnFloatCallbacks r42, com.lzf.easyfloat.interfaces.FloatCallbacks r43, com.lzf.easyfloat.interfaces.OnFloatAnimator r44, com.lzf.easyfloat.interfaces.OnAppFloatAnimator r45, com.lzf.easyfloat.interfaces.OnDisplayHeight r46, java.util.Set r47, boolean r48, boolean r49, int r50, kotlin.jvm.internal.DefaultConstructorMarker r51) {
        /*
            Method dump skipped, instructions count: 351
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lzf.easyfloat.data.FloatConfig.<init>(java.lang.Integer, android.view.View, java.lang.String, boolean, boolean, boolean, boolean, boolean, com.lzf.easyfloat.enums.SidePattern, com.lzf.easyfloat.enums.ShowPattern, boolean, boolean, int, kotlin.Pair, kotlin.Pair, com.lzf.easyfloat.interfaces.OnInvokeView, com.lzf.easyfloat.interfaces.OnFloatCallbacks, com.lzf.easyfloat.interfaces.FloatCallbacks, com.lzf.easyfloat.interfaces.OnFloatAnimator, com.lzf.easyfloat.interfaces.OnAppFloatAnimator, com.lzf.easyfloat.interfaces.OnDisplayHeight, java.util.Set, boolean, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final Integer getLayoutId() {
        return this.layoutId;
    }

    public final void setLayoutId(Integer num) {
        this.layoutId = num;
    }

    public final View getLayoutView() {
        return this.layoutView;
    }

    public final void setLayoutView(View view) {
        this.layoutView = view;
    }

    public final String getFloatTag() {
        return this.floatTag;
    }

    public final void setFloatTag(String str) {
        this.floatTag = str;
    }

    public final boolean getDragEnable() {
        return this.dragEnable;
    }

    public final void setDragEnable(boolean z) {
        this.dragEnable = z;
    }

    public final boolean isDrag() {
        return this.isDrag;
    }

    public final void setDrag(boolean z) {
        this.isDrag = z;
    }

    public final boolean isAnim() {
        return this.isAnim;
    }

    public final void setAnim(boolean z) {
        this.isAnim = z;
    }

    public final boolean isShow() {
        return this.isShow;
    }

    public final void setShow(boolean z) {
        this.isShow = z;
    }

    public final boolean getHasEditText() {
        return this.hasEditText;
    }

    public final void setHasEditText(boolean z) {
        this.hasEditText = z;
    }

    public final SidePattern getSidePattern() {
        return this.sidePattern;
    }

    public final void setSidePattern(SidePattern sidePattern) {
        Intrinsics.checkParameterIsNotNull(sidePattern, "<set-?>");
        this.sidePattern = sidePattern;
    }

    public final ShowPattern getShowPattern() {
        return this.showPattern;
    }

    public final void setShowPattern(ShowPattern showPattern) {
        Intrinsics.checkParameterIsNotNull(showPattern, "<set-?>");
        this.showPattern = showPattern;
    }

    public final boolean getWidthMatch() {
        return this.widthMatch;
    }

    public final void setWidthMatch(boolean z) {
        this.widthMatch = z;
    }

    public final boolean getHeightMatch() {
        return this.heightMatch;
    }

    public final void setHeightMatch(boolean z) {
        this.heightMatch = z;
    }

    public final int getGravity() {
        return this.gravity;
    }

    public final void setGravity(int i) {
        this.gravity = i;
    }

    public final Pair<Integer, Integer> getOffsetPair() {
        return this.offsetPair;
    }

    public final void setOffsetPair(Pair<Integer, Integer> pair) {
        Intrinsics.checkParameterIsNotNull(pair, "<set-?>");
        this.offsetPair = pair;
    }

    public final Pair<Integer, Integer> getLocationPair() {
        return this.locationPair;
    }

    public final void setLocationPair(Pair<Integer, Integer> pair) {
        Intrinsics.checkParameterIsNotNull(pair, "<set-?>");
        this.locationPair = pair;
    }

    public final OnInvokeView getInvokeView() {
        return this.invokeView;
    }

    public final void setInvokeView(OnInvokeView onInvokeView) {
        this.invokeView = onInvokeView;
    }

    public final OnFloatCallbacks getCallbacks() {
        return this.callbacks;
    }

    public final void setCallbacks(OnFloatCallbacks onFloatCallbacks) {
        this.callbacks = onFloatCallbacks;
    }

    public final FloatCallbacks getFloatCallbacks() {
        return this.floatCallbacks;
    }

    public final void setFloatCallbacks(FloatCallbacks floatCallbacks) {
        this.floatCallbacks = floatCallbacks;
    }

    public final OnFloatAnimator getFloatAnimator() {
        return this.floatAnimator;
    }

    public final void setFloatAnimator(OnFloatAnimator onFloatAnimator) {
        this.floatAnimator = onFloatAnimator;
    }

    public final OnAppFloatAnimator getAppFloatAnimator() {
        return this.appFloatAnimator;
    }

    public final void setAppFloatAnimator(OnAppFloatAnimator onAppFloatAnimator) {
        this.appFloatAnimator = onAppFloatAnimator;
    }

    public final OnDisplayHeight getDisplayHeight() {
        return this.displayHeight;
    }

    public final void setDisplayHeight(OnDisplayHeight onDisplayHeight) {
        Intrinsics.checkParameterIsNotNull(onDisplayHeight, "<set-?>");
        this.displayHeight = onDisplayHeight;
    }

    public final Set<String> getFilterSet() {
        return this.filterSet;
    }

    public final boolean getFilterSelf$easyfloat_release() {
        return this.filterSelf;
    }

    public final void setFilterSelf$easyfloat_release(boolean z) {
        this.filterSelf = z;
    }

    public final boolean getNeedShow$easyfloat_release() {
        return this.needShow;
    }

    public final void setNeedShow$easyfloat_release(boolean z) {
        this.needShow = z;
    }
}
