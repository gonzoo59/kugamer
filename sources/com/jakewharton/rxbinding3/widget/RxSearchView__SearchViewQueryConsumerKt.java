package com.jakewharton.rxbinding3.widget;

import android.widget.SearchView;
import io.reactivex.functions.Consumer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: SearchViewQueryConsumer.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u001c\u0010\u0000\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007¨\u0006\u0006"}, d2 = {"query", "Lio/reactivex/functions/Consumer;", "", "Landroid/widget/SearchView;", "submit", "", "rxbinding_release"}, k = 5, mv = {1, 1, 15}, xs = "com/jakewharton/rxbinding3/widget/RxSearchView")
/* loaded from: classes2.dex */
final /* synthetic */ class RxSearchView__SearchViewQueryConsumerKt {
    public static final Consumer<? super CharSequence> query(final SearchView query, final boolean z) {
        Intrinsics.checkParameterIsNotNull(query, "$this$query");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding3.widget.RxSearchView__SearchViewQueryConsumerKt$query$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(CharSequence charSequence) {
                query.setQuery(charSequence, z);
            }
        };
    }
}
