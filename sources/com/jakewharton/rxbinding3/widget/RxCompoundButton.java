package com.jakewharton.rxbinding3.widget;

import android.widget.CompoundButton;
import com.jakewharton.rxbinding3.InitialValueObservable;
import kotlin.Metadata;
/* compiled from: CompoundButtonCheckedChangeObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding3/widget/RxCompoundButton__CompoundButtonCheckedChangeObservableKt"}, k = 4, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class RxCompoundButton {
    public static final InitialValueObservable<Boolean> checkedChanges(CompoundButton compoundButton) {
        return RxCompoundButton__CompoundButtonCheckedChangeObservableKt.checkedChanges(compoundButton);
    }
}
