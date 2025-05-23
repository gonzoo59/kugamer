package com.jakewharton.rxbinding3.widget;

import android.widget.RatingBar;
import com.jakewharton.rxbinding3.InitialValueObservable;
import kotlin.Metadata;
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding3/widget/RxRatingBar__RatingBarRatingChangeEventObservableKt", "com/jakewharton/rxbinding3/widget/RxRatingBar__RatingBarRatingChangeObservableKt"}, k = 4, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class RxRatingBar {
    public static final InitialValueObservable<RatingBarChangeEvent> ratingChangeEvents(RatingBar ratingBar) {
        return RxRatingBar__RatingBarRatingChangeEventObservableKt.ratingChangeEvents(ratingBar);
    }

    public static final InitialValueObservable<Float> ratingChanges(RatingBar ratingBar) {
        return RxRatingBar__RatingBarRatingChangeObservableKt.ratingChanges(ratingBar);
    }
}
