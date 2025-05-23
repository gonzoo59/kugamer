package com.jakewharton.rxbinding3.widget;

import android.widget.Adapter;
import android.widget.AdapterView;
import com.jakewharton.rxbinding3.internal.AlwaysTrue;
import io.reactivex.Observable;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AdapterViewItemLongClickObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\u001a2\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\b\b\u0000\u0010\u0003*\u00020\u0004*\b\u0012\u0004\u0012\u0002H\u00030\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0007¨\u0006\t"}, d2 = {"itemLongClicks", "Lio/reactivex/Observable;", "", "T", "Landroid/widget/Adapter;", "Landroid/widget/AdapterView;", "handled", "Lkotlin/Function0;", "", "rxbinding_release"}, k = 5, mv = {1, 1, 15}, xs = "com/jakewharton/rxbinding3/widget/RxAdapterView")
/* loaded from: classes2.dex */
public final /* synthetic */ class RxAdapterView__AdapterViewItemLongClickObservableKt {
    public static final <T extends Adapter> Observable<Integer> itemLongClicks(AdapterView<T> adapterView) {
        return RxAdapterView.itemLongClicks$default(adapterView, null, 1, null);
    }

    public static /* synthetic */ Observable itemLongClicks$default(AdapterView adapterView, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = AlwaysTrue.INSTANCE;
        }
        return RxAdapterView.itemLongClicks(adapterView, function0);
    }

    public static final <T extends Adapter> Observable<Integer> itemLongClicks(AdapterView<T> itemLongClicks, Function0<Boolean> handled) {
        Intrinsics.checkParameterIsNotNull(itemLongClicks, "$this$itemLongClicks");
        Intrinsics.checkParameterIsNotNull(handled, "handled");
        return new AdapterViewItemLongClickObservable(itemLongClicks, handled);
    }
}
