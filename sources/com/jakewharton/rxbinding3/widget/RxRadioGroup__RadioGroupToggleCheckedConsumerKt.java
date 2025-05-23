package com.jakewharton.rxbinding3.widget;

import android.widget.RadioGroup;
import io.reactivex.functions.Consumer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: RadioGroupToggleCheckedConsumer.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007¨\u0006\u0004"}, d2 = {"checked", "Lio/reactivex/functions/Consumer;", "", "Landroid/widget/RadioGroup;", "rxbinding_release"}, k = 5, mv = {1, 1, 15}, xs = "com/jakewharton/rxbinding3/widget/RxRadioGroup")
/* loaded from: classes2.dex */
final /* synthetic */ class RxRadioGroup__RadioGroupToggleCheckedConsumerKt {
    public static final Consumer<? super Integer> checked(final RadioGroup checked) {
        Intrinsics.checkParameterIsNotNull(checked, "$this$checked");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding3.widget.RxRadioGroup__RadioGroupToggleCheckedConsumerKt$checked$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Integer num) {
                if (num != null && num.intValue() == -1) {
                    checked.clearCheck();
                    return;
                }
                RadioGroup radioGroup = checked;
                if (num == null) {
                    Intrinsics.throwNpe();
                }
                radioGroup.check(num.intValue());
            }
        };
    }
}
