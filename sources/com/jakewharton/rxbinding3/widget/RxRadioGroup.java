package com.jakewharton.rxbinding3.widget;

import android.widget.RadioGroup;
import com.jakewharton.rxbinding3.InitialValueObservable;
import io.reactivex.functions.Consumer;
import kotlin.Metadata;
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding3/widget/RxRadioGroup__RadioGroupCheckedChangeObservableKt", "com/jakewharton/rxbinding3/widget/RxRadioGroup__RadioGroupToggleCheckedConsumerKt"}, k = 4, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class RxRadioGroup {
    public static final Consumer<? super Integer> checked(RadioGroup radioGroup) {
        return RxRadioGroup__RadioGroupToggleCheckedConsumerKt.checked(radioGroup);
    }

    public static final InitialValueObservable<Integer> checkedChanges(RadioGroup radioGroup) {
        return RxRadioGroup__RadioGroupCheckedChangeObservableKt.checkedChanges(radioGroup);
    }
}
