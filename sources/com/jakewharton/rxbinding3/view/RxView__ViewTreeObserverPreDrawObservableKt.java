package com.jakewharton.rxbinding3.view;

import android.view.View;
import io.reactivex.Observable;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: ViewTreeObserverPreDrawObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\u001a \u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0007¨\u0006\u0007"}, d2 = {"preDraws", "Lio/reactivex/Observable;", "", "Landroid/view/View;", "proceedDrawingPass", "Lkotlin/Function0;", "", "rxbinding_release"}, k = 5, mv = {1, 1, 15}, xs = "com/jakewharton/rxbinding3/view/RxView")
/* loaded from: classes2.dex */
final /* synthetic */ class RxView__ViewTreeObserverPreDrawObservableKt {
    public static final Observable<Unit> preDraws(View preDraws, Function0<Boolean> proceedDrawingPass) {
        Intrinsics.checkParameterIsNotNull(preDraws, "$this$preDraws");
        Intrinsics.checkParameterIsNotNull(proceedDrawingPass, "proceedDrawingPass");
        return new ViewTreeObserverPreDrawObservable(preDraws, proceedDrawingPass);
    }
}
