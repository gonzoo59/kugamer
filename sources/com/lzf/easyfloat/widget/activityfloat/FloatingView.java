package com.lzf.easyfloat.widget.activityfloat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.lzf.easyfloat.data.FloatConfig;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: FloatingView.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u000f\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u000e\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0010¨\u0006\u0011"}, d2 = {"Lcom/lzf/easyfloat/widget/activityfloat/FloatingView;", "Lcom/lzf/easyfloat/widget/activityfloat/AbstractDragFloatingView;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "getLayoutId", "", "()Ljava/lang/Integer;", "renderView", "", "view", "Landroid/view/View;", "setFloatConfig", "config", "Lcom/lzf/easyfloat/data/FloatConfig;", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class FloatingView extends AbstractDragFloatingView {
    private HashMap _$_findViewCache;

    @Override // com.lzf.easyfloat.widget.activityfloat.AbstractDragFloatingView
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.lzf.easyfloat.widget.activityfloat.AbstractDragFloatingView
    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.lzf.easyfloat.widget.activityfloat.AbstractDragFloatingView
    public void renderView(View view) {
        Intrinsics.checkParameterIsNotNull(view, "view");
    }

    public /* synthetic */ FloatingView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FloatingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        Intrinsics.checkParameterIsNotNull(context, "context");
    }

    public final void setFloatConfig(FloatConfig config) {
        Intrinsics.checkParameterIsNotNull(config, "config");
        setConfig(config);
        Context context = getContext();
        Intrinsics.checkExpressionValueIsNotNull(context, "context");
        initView(context);
        requestLayout();
    }

    @Override // com.lzf.easyfloat.widget.activityfloat.AbstractDragFloatingView
    public Integer getLayoutId() {
        return getConfig().getLayoutId();
    }
}
