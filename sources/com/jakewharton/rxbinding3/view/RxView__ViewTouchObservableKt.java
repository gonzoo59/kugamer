package com.jakewharton.rxbinding3.view;

import android.view.MotionEvent;
import android.view.View;
import com.jakewharton.rxbinding3.internal.AlwaysTrue;
import io.reactivex.Observable;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: ViewTouchObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\u001a(\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005H\u0007¨\u0006\u0007"}, d2 = {"touches", "Lio/reactivex/Observable;", "Landroid/view/MotionEvent;", "Landroid/view/View;", "handled", "Lkotlin/Function1;", "", "rxbinding_release"}, k = 5, mv = {1, 1, 15}, xs = "com/jakewharton/rxbinding3/view/RxView")
/* loaded from: classes2.dex */
public final /* synthetic */ class RxView__ViewTouchObservableKt {
    public static final Observable<MotionEvent> touches(View view) {
        return touches$default(view, null, 1, null);
    }

    public static /* synthetic */ Observable touches$default(View view, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = AlwaysTrue.INSTANCE;
        }
        return RxView.touches(view, function1);
    }

    public static final Observable<MotionEvent> touches(View touches, Function1<? super MotionEvent, Boolean> handled) {
        Intrinsics.checkParameterIsNotNull(touches, "$this$touches");
        Intrinsics.checkParameterIsNotNull(handled, "handled");
        return new ViewTouchObservable(touches, handled);
    }
}
