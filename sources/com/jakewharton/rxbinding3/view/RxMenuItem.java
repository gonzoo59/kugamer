package com.jakewharton.rxbinding3.view;

import android.view.MenuItem;
import io.reactivex.Observable;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding3/view/RxMenuItem__MenuItemActionViewEventObservableKt", "com/jakewharton/rxbinding3/view/RxMenuItem__MenuItemClickObservableKt"}, k = 4, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class RxMenuItem {
    public static final Observable<MenuItemActionViewEvent> actionViewEvents(MenuItem menuItem) {
        return RxMenuItem__MenuItemActionViewEventObservableKt.actionViewEvents$default(menuItem, null, 1, null);
    }

    public static final Observable<MenuItemActionViewEvent> actionViewEvents(MenuItem menuItem, Function1<? super MenuItemActionViewEvent, Boolean> function1) {
        return RxMenuItem__MenuItemActionViewEventObservableKt.actionViewEvents(menuItem, function1);
    }

    public static final Observable<Unit> clicks(MenuItem menuItem) {
        return RxMenuItem__MenuItemClickObservableKt.clicks$default(menuItem, null, 1, null);
    }

    public static final Observable<Unit> clicks(MenuItem menuItem, Function1<? super MenuItem, Boolean> function1) {
        return RxMenuItem__MenuItemClickObservableKt.clicks(menuItem, function1);
    }
}
