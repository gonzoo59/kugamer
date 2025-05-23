package com.jakewharton.rxbinding3.widget;

import android.widget.AbsListView;
import io.reactivex.Observable;
import kotlin.Metadata;
/* compiled from: AbsListViewScrollEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding3/widget/RxAbsListView__AbsListViewScrollEventObservableKt"}, k = 4, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class RxAbsListView {
    public static final Observable<AbsListViewScrollEvent> scrollEvents(AbsListView absListView) {
        return RxAbsListView__AbsListViewScrollEventObservableKt.scrollEvents(absListView);
    }
}
