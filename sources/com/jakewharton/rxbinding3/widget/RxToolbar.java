package com.jakewharton.rxbinding3.widget;

import android.view.MenuItem;
import android.widget.Toolbar;
import io.reactivex.Observable;
import kotlin.Metadata;
import kotlin.Unit;
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding3/widget/RxToolbar__ToolbarItemClickObservableKt", "com/jakewharton/rxbinding3/widget/RxToolbar__ToolbarNavigationClickObservableKt"}, k = 4, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class RxToolbar {
    public static final Observable<MenuItem> itemClicks(Toolbar toolbar) {
        return RxToolbar__ToolbarItemClickObservableKt.itemClicks(toolbar);
    }

    public static final Observable<Unit> navigationClicks(Toolbar toolbar) {
        return RxToolbar__ToolbarNavigationClickObservableKt.navigationClicks(toolbar);
    }
}
