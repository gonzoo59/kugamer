package com.jakewharton.rxbinding3.widget;

import android.widget.Adapter;
import android.widget.AdapterView;
import com.jakewharton.rxbinding3.InitialValueObservable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: AdapterViewSelectionObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\"\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\b\b\u0000\u0010\u0003*\u00020\u0004*\b\u0012\u0004\u0012\u0002H\u00030\u0005H\u0007¨\u0006\u0006"}, d2 = {"selectionEvents", "Lcom/jakewharton/rxbinding3/InitialValueObservable;", "Lcom/jakewharton/rxbinding3/widget/AdapterViewSelectionEvent;", "T", "Landroid/widget/Adapter;", "Landroid/widget/AdapterView;", "rxbinding_release"}, k = 5, mv = {1, 1, 15}, xs = "com/jakewharton/rxbinding3/widget/RxAdapterView")
/* loaded from: classes2.dex */
final /* synthetic */ class RxAdapterView__AdapterViewSelectionObservableKt {
    public static final <T extends Adapter> InitialValueObservable<AdapterViewSelectionEvent> selectionEvents(AdapterView<T> selectionEvents) {
        Intrinsics.checkParameterIsNotNull(selectionEvents, "$this$selectionEvents");
        return new AdapterViewSelectionObservable(selectionEvents);
    }
}
