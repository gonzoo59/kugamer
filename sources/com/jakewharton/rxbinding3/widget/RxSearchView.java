package com.jakewharton.rxbinding3.widget;

import android.widget.SearchView;
import com.jakewharton.rxbinding3.InitialValueObservable;
import io.reactivex.functions.Consumer;
import kotlin.Metadata;
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding3/widget/RxSearchView__SearchViewQueryConsumerKt", "com/jakewharton/rxbinding3/widget/RxSearchView__SearchViewQueryTextChangeEventsObservableKt", "com/jakewharton/rxbinding3/widget/RxSearchView__SearchViewQueryTextChangesObservableKt"}, k = 4, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class RxSearchView {
    public static final Consumer<? super CharSequence> query(SearchView searchView, boolean z) {
        return RxSearchView__SearchViewQueryConsumerKt.query(searchView, z);
    }

    public static final InitialValueObservable<SearchViewQueryTextEvent> queryTextChangeEvents(SearchView searchView) {
        return RxSearchView__SearchViewQueryTextChangeEventsObservableKt.queryTextChangeEvents(searchView);
    }

    public static final InitialValueObservable<CharSequence> queryTextChanges(SearchView searchView) {
        return RxSearchView__SearchViewQueryTextChangesObservableKt.queryTextChanges(searchView);
    }
}
