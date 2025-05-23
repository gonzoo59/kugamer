package com.jakewharton.rxbinding3.widget;

import android.widget.Adapter;
import com.jakewharton.rxbinding3.InitialValueObservable;
import kotlin.Metadata;
/* compiled from: AdapterDataChangeObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding3/widget/RxAdapter__AdapterDataChangeObservableKt"}, k = 4, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class RxAdapter {
    public static final <T extends Adapter> InitialValueObservable<T> dataChanges(T t) {
        return RxAdapter__AdapterDataChangeObservableKt.dataChanges(t);
    }
}
