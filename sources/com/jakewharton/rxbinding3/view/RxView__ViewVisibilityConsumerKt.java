package com.jakewharton.rxbinding3.view;

import android.view.View;
import io.reactivex.functions.Consumer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ViewVisibilityConsumer.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u001a\u001e\u0010\u0000\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\u0001*\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007¨\u0006\u0006"}, d2 = {"visibility", "Lio/reactivex/functions/Consumer;", "", "Landroid/view/View;", "visibilityWhenFalse", "", "rxbinding_release"}, k = 5, mv = {1, 1, 15}, xs = "com/jakewharton/rxbinding3/view/RxView")
/* loaded from: classes2.dex */
public final /* synthetic */ class RxView__ViewVisibilityConsumerKt {
    public static final Consumer<? super Boolean> visibility(View view) {
        return RxView.visibility$default(view, 0, 1, null);
    }

    public static /* synthetic */ Consumer visibility$default(View view, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 8;
        }
        return RxView.visibility(view, i);
    }

    public static final Consumer<? super Boolean> visibility(final View visibility, final int i) {
        Intrinsics.checkParameterIsNotNull(visibility, "$this$visibility");
        boolean z = true;
        if (i != 0) {
            if (i != 4 && i != 8) {
                z = false;
            }
            if (!z) {
                throw new IllegalArgumentException("Must set visibility to INVISIBLE or GONE when false.".toString());
            }
            return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding3.view.RxView__ViewVisibilityConsumerKt$visibility$3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Boolean value) {
                    View view = visibility;
                    Intrinsics.checkExpressionValueIsNotNull(value, "value");
                    view.setVisibility(value.booleanValue() ? 0 : i);
                }
            };
        }
        throw new IllegalArgumentException("Setting visibility to VISIBLE when false would have no effect.".toString());
    }
}
