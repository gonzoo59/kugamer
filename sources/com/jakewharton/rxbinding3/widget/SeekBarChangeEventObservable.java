package com.jakewharton.rxbinding3.widget;

import android.widget.SeekBar;
import com.jakewharton.rxbinding3.InitialValueObservable;
import com.jakewharton.rxbinding3.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: SeekBarChangeEventObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u000eB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\n\u001a\u00020\u000b2\u000e\u0010\f\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\rH\u0014R\u0014\u0010\u0006\u001a\u00020\u00078TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/jakewharton/rxbinding3/widget/SeekBarChangeEventObservable;", "Lcom/jakewharton/rxbinding3/InitialValueObservable;", "Lcom/jakewharton/rxbinding3/widget/SeekBarChangeEvent;", "view", "Landroid/widget/SeekBar;", "(Landroid/widget/SeekBar;)V", "initialValue", "Lcom/jakewharton/rxbinding3/widget/SeekBarProgressChangeEvent;", "getInitialValue", "()Lcom/jakewharton/rxbinding3/widget/SeekBarProgressChangeEvent;", "subscribeListener", "", "observer", "Lio/reactivex/Observer;", "Listener", "rxbinding_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes2.dex */
final class SeekBarChangeEventObservable extends InitialValueObservable<SeekBarChangeEvent> {
    private final SeekBar view;

    public SeekBarChangeEventObservable(SeekBar view) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.view = view;
    }

    @Override // com.jakewharton.rxbinding3.InitialValueObservable
    protected void subscribeListener(Observer<? super SeekBarChangeEvent> observer) {
        Intrinsics.checkParameterIsNotNull(observer, "observer");
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            this.view.setOnSeekBarChangeListener(listener);
            observer.onSubscribe(listener);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.jakewharton.rxbinding3.InitialValueObservable
    /* renamed from: getInitialValue */
    public SeekBarChangeEvent getInitialValue2() {
        SeekBar seekBar = this.view;
        return new SeekBarProgressChangeEvent(seekBar, seekBar.getProgress(), false);
    }

    /* compiled from: SeekBarChangeEventObservable.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0014J \u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\u0012\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0004H\u0016R\u0016\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/jakewharton/rxbinding3/widget/SeekBarChangeEventObservable$Listener;", "Lio/reactivex/android/MainThreadDisposable;", "Landroid/widget/SeekBar$OnSeekBarChangeListener;", "view", "Landroid/widget/SeekBar;", "observer", "Lio/reactivex/Observer;", "Lcom/jakewharton/rxbinding3/widget/SeekBarChangeEvent;", "(Landroid/widget/SeekBar;Lio/reactivex/Observer;)V", "onDispose", "", "onProgressChanged", "seekBar", "progress", "", "fromUser", "", "onStartTrackingTouch", "onStopTrackingTouch", "rxbinding_release"}, k = 1, mv = {1, 1, 15})
    /* loaded from: classes2.dex */
    private static final class Listener extends MainThreadDisposable implements SeekBar.OnSeekBarChangeListener {
        private final Observer<? super SeekBarChangeEvent> observer;
        private final SeekBar view;

        public Listener(SeekBar view, Observer<? super SeekBarChangeEvent> observer) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(observer, "observer");
            this.view = view;
            this.observer = observer;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            Intrinsics.checkParameterIsNotNull(seekBar, "seekBar");
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(new SeekBarProgressChangeEvent(seekBar, i, z));
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
            Intrinsics.checkParameterIsNotNull(seekBar, "seekBar");
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(new SeekBarStartChangeEvent(seekBar));
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
            Intrinsics.checkParameterIsNotNull(seekBar, "seekBar");
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(new SeekBarStopChangeEvent(seekBar));
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.view.setOnSeekBarChangeListener(null);
        }
    }
}
