package com.lzf.easyfloat.interfaces;

import android.view.MotionEvent;
import android.view.View;
import kotlin.Metadata;
/* compiled from: OnFloatCallbacks.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J$\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH&J\b\u0010\n\u001a\u00020\u0003H&J\u0018\u0010\u000b\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH&J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&J\u0010\u0010\u0010\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&J\u0018\u0010\u0011\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH&¨\u0006\u0012"}, d2 = {"Lcom/lzf/easyfloat/interfaces/OnFloatCallbacks;", "", "createdResult", "", "isCreated", "", "msg", "", "view", "Landroid/view/View;", "dismiss", "drag", "event", "Landroid/view/MotionEvent;", "dragEnd", "hide", "show", "touchEvent", "easyfloat_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public interface OnFloatCallbacks {
    void createdResult(boolean z, String str, View view);

    void dismiss();

    void drag(View view, MotionEvent motionEvent);

    void dragEnd(View view);

    void hide(View view);

    void show(View view);

    void touchEvent(View view, MotionEvent motionEvent);
}
