package com.jakewharton.rxbinding3.view;

import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.jakewharton.rxbinding3.InitialValueObservable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding3/view/RxView__ViewAttachEventObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewAttachesObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewClickObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewDragObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewFocusChangeObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewHoverObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewKeyObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewLayoutChangeEventObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewLayoutChangeObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewLongClickObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewScrollChangeEventObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewSystemUiVisibilityChangeObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewTouchObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewTreeObserverDrawObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewTreeObserverGlobalLayoutObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewTreeObserverPreDrawObservableKt", "com/jakewharton/rxbinding3/view/RxView__ViewVisibilityConsumerKt"}, k = 4, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class RxView {
    public static final Observable<ViewAttachEvent> attachEvents(View view) {
        return RxView__ViewAttachEventObservableKt.attachEvents(view);
    }

    public static final Observable<Unit> attaches(View view) {
        return RxView__ViewAttachesObservableKt.attaches(view);
    }

    public static final Observable<Unit> clicks(View view) {
        return RxView__ViewClickObservableKt.clicks(view);
    }

    public static final Observable<Unit> detaches(View view) {
        return RxView__ViewAttachesObservableKt.detaches(view);
    }

    public static final Observable<DragEvent> drags(View view) {
        return RxView__ViewDragObservableKt.drags$default(view, null, 1, null);
    }

    public static final Observable<DragEvent> drags(View view, Function1<? super DragEvent, Boolean> function1) {
        return RxView__ViewDragObservableKt.drags(view, function1);
    }

    public static final Observable<Unit> draws(View view) {
        return RxView__ViewTreeObserverDrawObservableKt.draws(view);
    }

    public static final InitialValueObservable<Boolean> focusChanges(View view) {
        return RxView__ViewFocusChangeObservableKt.focusChanges(view);
    }

    public static final Observable<Unit> globalLayouts(View view) {
        return RxView__ViewTreeObserverGlobalLayoutObservableKt.globalLayouts(view);
    }

    public static final Observable<MotionEvent> hovers(View view) {
        return RxView__ViewHoverObservableKt.hovers$default(view, null, 1, null);
    }

    public static final Observable<MotionEvent> hovers(View view, Function1<? super MotionEvent, Boolean> function1) {
        return RxView__ViewHoverObservableKt.hovers(view, function1);
    }

    public static final Observable<KeyEvent> keys(View view) {
        return RxView__ViewKeyObservableKt.keys$default(view, null, 1, null);
    }

    public static final Observable<KeyEvent> keys(View view, Function1<? super KeyEvent, Boolean> function1) {
        return RxView__ViewKeyObservableKt.keys(view, function1);
    }

    public static final Observable<ViewLayoutChangeEvent> layoutChangeEvents(View view) {
        return RxView__ViewLayoutChangeEventObservableKt.layoutChangeEvents(view);
    }

    public static final Observable<Unit> layoutChanges(View view) {
        return RxView__ViewLayoutChangeObservableKt.layoutChanges(view);
    }

    public static final Observable<Unit> longClicks(View view) {
        return RxView__ViewLongClickObservableKt.longClicks$default(view, null, 1, null);
    }

    public static final Observable<Unit> longClicks(View view, Function0<Boolean> function0) {
        return RxView__ViewLongClickObservableKt.longClicks(view, function0);
    }

    public static final Observable<Unit> preDraws(View view, Function0<Boolean> function0) {
        return RxView__ViewTreeObserverPreDrawObservableKt.preDraws(view, function0);
    }

    public static final Observable<ViewScrollChangeEvent> scrollChangeEvents(View view) {
        return RxView__ViewScrollChangeEventObservableKt.scrollChangeEvents(view);
    }

    public static final Observable<Integer> systemUiVisibilityChanges(View view) {
        return RxView__ViewSystemUiVisibilityChangeObservableKt.systemUiVisibilityChanges(view);
    }

    public static final Observable<MotionEvent> touches(View view) {
        return RxView__ViewTouchObservableKt.touches$default(view, null, 1, null);
    }

    public static final Observable<MotionEvent> touches(View view, Function1<? super MotionEvent, Boolean> function1) {
        return RxView__ViewTouchObservableKt.touches(view, function1);
    }

    public static final Consumer<? super Boolean> visibility(View view) {
        return RxView__ViewVisibilityConsumerKt.visibility$default(view, 0, 1, null);
    }

    public static final Consumer<? super Boolean> visibility(View view, int i) {
        return RxView__ViewVisibilityConsumerKt.visibility(view, i);
    }
}
