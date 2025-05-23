package com.jakewharton.rxbinding3.view;

import android.view.View;
import io.reactivex.Observable;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: ViewAttachesObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007\u001a\u0012\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007¨\u0006\u0005"}, d2 = {"attaches", "Lio/reactivex/Observable;", "", "Landroid/view/View;", "detaches", "rxbinding_release"}, k = 5, mv = {1, 1, 15}, xs = "com/jakewharton/rxbinding3/view/RxView")
/* loaded from: classes2.dex */
final /* synthetic */ class RxView__ViewAttachesObservableKt {
    public static final Observable<Unit> attaches(View attaches) {
        Intrinsics.checkParameterIsNotNull(attaches, "$this$attaches");
        return new ViewAttachesObservable(attaches, true);
    }

    public static final Observable<Unit> detaches(View detaches) {
        Intrinsics.checkParameterIsNotNull(detaches, "$this$detaches");
        return new ViewAttachesObservable(detaches, false);
    }
}
