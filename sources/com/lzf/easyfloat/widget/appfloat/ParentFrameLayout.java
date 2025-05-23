package com.lzf.easyfloat.widget.appfloat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.interfaces.FloatCallbacks;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnFloatTouchListener;
import com.lzf.easyfloat.utils.InputMethodUtils;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: ParentFrameLayout.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0001\u0018\u00002\u00020\u0001:\u0001'B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0012\u0010\u0019\u001a\u00020\f2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0014J\u0012\u0010\u001e\u001a\u00020\f2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001fH\u0016J0\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\t2\u0006\u0010#\u001a\u00020\t2\u0006\u0010$\u001a\u00020\t2\u0006\u0010%\u001a\u00020\tH\u0014J\u0012\u0010&\u001a\u00020\f2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001fH\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006("}, d2 = {"Lcom/lzf/easyfloat/widget/appfloat/ParentFrameLayout;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "config", "Lcom/lzf/easyfloat/data/FloatConfig;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Lcom/lzf/easyfloat/data/FloatConfig;Landroid/util/AttributeSet;I)V", "isCreated", "", "layoutListener", "Lcom/lzf/easyfloat/widget/appfloat/ParentFrameLayout$OnLayoutListener;", "getLayoutListener", "()Lcom/lzf/easyfloat/widget/appfloat/ParentFrameLayout$OnLayoutListener;", "setLayoutListener", "(Lcom/lzf/easyfloat/widget/appfloat/ParentFrameLayout$OnLayoutListener;)V", "touchListener", "Lcom/lzf/easyfloat/interfaces/OnFloatTouchListener;", "getTouchListener", "()Lcom/lzf/easyfloat/interfaces/OnFloatTouchListener;", "setTouchListener", "(Lcom/lzf/easyfloat/interfaces/OnFloatTouchListener;)V", "dispatchKeyEventPreIme", "event", "Landroid/view/KeyEvent;", "onDetachedFromWindow", "", "onInterceptTouchEvent", "Landroid/view/MotionEvent;", "onLayout", "changed", "left", "top", "right", "bottom", "onTouchEvent", "OnLayoutListener", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class ParentFrameLayout extends FrameLayout {
    private HashMap _$_findViewCache;
    private final FloatConfig config;
    private boolean isCreated;
    private OnLayoutListener layoutListener;
    private OnFloatTouchListener touchListener;

    /* compiled from: ParentFrameLayout.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lcom/lzf/easyfloat/widget/appfloat/ParentFrameLayout$OnLayoutListener;", "", "onLayout", "", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public interface OnLayoutListener {
        void onLayout();
    }

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

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

    public /* synthetic */ ParentFrameLayout(Context context, FloatConfig floatConfig, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, floatConfig, (i2 & 4) != 0 ? null : attributeSet, (i2 & 8) != 0 ? 0 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ParentFrameLayout(Context context, FloatConfig config, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(config, "config");
        this.config = config;
    }

    public final OnFloatTouchListener getTouchListener() {
        return this.touchListener;
    }

    public final void setTouchListener(OnFloatTouchListener onFloatTouchListener) {
        this.touchListener = onFloatTouchListener;
    }

    public final OnLayoutListener getLayoutListener() {
        return this.layoutListener;
    }

    public final void setLayoutListener(OnLayoutListener onLayoutListener) {
        this.layoutListener = onLayoutListener;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.isCreated) {
            return;
        }
        this.isCreated = true;
        OnLayoutListener onLayoutListener = this.layoutListener;
        if (onLayoutListener != null) {
            onLayoutListener.onLayout();
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        OnFloatTouchListener onFloatTouchListener;
        if (motionEvent != null && (onFloatTouchListener = this.touchListener) != null) {
            onFloatTouchListener.onTouch(motionEvent);
        }
        return this.config.isDrag() || super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        OnFloatTouchListener onFloatTouchListener;
        if (motionEvent != null && (onFloatTouchListener = this.touchListener) != null) {
            onFloatTouchListener.onTouch(motionEvent);
        }
        return this.config.isDrag() || super.onTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        if (this.config.getHasEditText() && keyEvent != null && keyEvent.getAction() == 0 && keyEvent.getKeyCode() == 4) {
            InputMethodUtils.closedInputMethod(FloatManager.INSTANCE.getTag(this.config.getFloatTag()));
        }
        return super.dispatchKeyEventPreIme(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        FloatCallbacks.Builder builder;
        Function0<Unit> dismiss$easyfloat_release;
        super.onDetachedFromWindow();
        OnFloatCallbacks callbacks = this.config.getCallbacks();
        if (callbacks != null) {
            callbacks.dismiss();
        }
        FloatCallbacks floatCallbacks = this.config.getFloatCallbacks();
        if (floatCallbacks == null || (builder = floatCallbacks.getBuilder()) == null || (dismiss$easyfloat_release = builder.getDismiss$easyfloat_release()) == null) {
            return;
        }
        dismiss$easyfloat_release.invoke();
    }
}
