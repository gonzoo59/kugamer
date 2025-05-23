package com.jakewharton.rxbinding3.widget;

import android.widget.AutoCompleteTextView;
import io.reactivex.Observable;
import kotlin.Metadata;
/* compiled from: AutoCompleteTextViewItemClickEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding3/widget/RxAutoCompleteTextView__AutoCompleteTextViewItemClickEventObservableKt"}, k = 4, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class RxAutoCompleteTextView {
    public static final Observable<AdapterViewItemClickEvent> itemClickEvents(AutoCompleteTextView autoCompleteTextView) {
        return RxAutoCompleteTextView__AutoCompleteTextViewItemClickEventObservableKt.itemClickEvents(autoCompleteTextView);
    }
}
