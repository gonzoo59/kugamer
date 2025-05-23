package com.jakewharton.rxbinding3.widget;

import android.widget.TextView;
import com.jakewharton.rxbinding3.InitialValueObservable;
import io.reactivex.Observable;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding3/widget/RxTextView__TextViewAfterTextChangeEventObservableKt", "com/jakewharton/rxbinding3/widget/RxTextView__TextViewBeforeTextChangeEventObservableKt", "com/jakewharton/rxbinding3/widget/RxTextView__TextViewEditorActionEventObservableKt", "com/jakewharton/rxbinding3/widget/RxTextView__TextViewEditorActionObservableKt", "com/jakewharton/rxbinding3/widget/RxTextView__TextViewTextChangeEventObservableKt", "com/jakewharton/rxbinding3/widget/RxTextView__TextViewTextChangesObservableKt"}, k = 4, mv = {1, 1, 15})
/* loaded from: classes2.dex */
public final class RxTextView {
    public static final InitialValueObservable<TextViewAfterTextChangeEvent> afterTextChangeEvents(TextView textView) {
        return RxTextView__TextViewAfterTextChangeEventObservableKt.afterTextChangeEvents(textView);
    }

    public static final InitialValueObservable<TextViewBeforeTextChangeEvent> beforeTextChangeEvents(TextView textView) {
        return RxTextView__TextViewBeforeTextChangeEventObservableKt.beforeTextChangeEvents(textView);
    }

    public static final Observable<TextViewEditorActionEvent> editorActionEvents(TextView textView) {
        return RxTextView__TextViewEditorActionEventObservableKt.editorActionEvents$default(textView, null, 1, null);
    }

    public static final Observable<TextViewEditorActionEvent> editorActionEvents(TextView textView, Function1<? super TextViewEditorActionEvent, Boolean> function1) {
        return RxTextView__TextViewEditorActionEventObservableKt.editorActionEvents(textView, function1);
    }

    public static final Observable<Integer> editorActions(TextView textView) {
        return RxTextView__TextViewEditorActionObservableKt.editorActions$default(textView, null, 1, null);
    }

    public static final Observable<Integer> editorActions(TextView textView, Function1<? super Integer, Boolean> function1) {
        return RxTextView__TextViewEditorActionObservableKt.editorActions(textView, function1);
    }

    public static final InitialValueObservable<TextViewTextChangeEvent> textChangeEvents(TextView textView) {
        return RxTextView__TextViewTextChangeEventObservableKt.textChangeEvents(textView);
    }

    public static final InitialValueObservable<CharSequence> textChanges(TextView textView) {
        return RxTextView__TextViewTextChangesObservableKt.textChanges(textView);
    }
}
