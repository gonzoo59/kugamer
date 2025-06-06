package com.polidea.rxandroidble2.internal.util;

import com.polidea.rxandroidble2.internal.serialization.QueueReleaseInterface;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import java.util.concurrent.atomic.AtomicBoolean;
/* loaded from: classes2.dex */
public class QueueReleasingEmitterWrapper<T> implements Observer<T>, Cancellable {
    private final ObservableEmitter<T> emitter;
    private final AtomicBoolean isEmitterCanceled = new AtomicBoolean(false);
    private final QueueReleaseInterface queueReleaseInterface;

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
    }

    public QueueReleasingEmitterWrapper(ObservableEmitter<T> observableEmitter, QueueReleaseInterface queueReleaseInterface) {
        this.emitter = observableEmitter;
        this.queueReleaseInterface = queueReleaseInterface;
        observableEmitter.setCancellable(this);
    }

    @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
    public void onComplete() {
        this.queueReleaseInterface.release();
        this.emitter.onComplete();
    }

    @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        this.queueReleaseInterface.release();
        this.emitter.tryOnError(th);
    }

    @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
    public void onNext(T t) {
        this.emitter.onNext(t);
    }

    @Override // io.reactivex.functions.Cancellable
    public synchronized void cancel() {
        this.isEmitterCanceled.set(true);
    }

    public synchronized boolean isWrappedEmitterUnsubscribed() {
        return this.isEmitterCanceled.get();
    }
}
