package com.jakewharton.rxbinding3.view;

import android.view.ViewGroup;
import io.reactivex.Observable;
import kotlin.Metadata;
/* compiled from: ViewGroupHierarchyChangeEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding3/view/RxViewGroup__ViewGroupHierarchyChangeEventObservableKt"}, k = 4, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class RxViewGroup {
    public static final Observable<ViewGroupHierarchyChangeEvent> changeEvents(ViewGroup viewGroup) {
        return RxViewGroup__ViewGroupHierarchyChangeEventObservableKt.changeEvents(viewGroup);
    }
}
