package io.reactivex.internal.operators.observable;

import io.reactivex.Notification;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
/* loaded from: classes2.dex */
public final class ObservableMaterialize<T> extends AbstractObservableWithUpstream<T, Notification<T>> {
    public ObservableMaterialize(ObservableSource<T> observableSource) {
        super(observableSource);
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Notification<T>> observer) {
        this.source.subscribe(new MaterializeObserver(observer));
    }

    /* loaded from: classes2.dex */
    static final class MaterializeObserver<T> implements Observer<T>, Disposable {
        final Observer<? super Notification<T>> downstream;
        Disposable upstream;

        MaterializeObserver(Observer<? super Notification<T>> observer) {
            this.downstream = observer;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.downstream.onNext(Notification.createOnNext(t));
        }

        @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.downstream.onNext(Notification.createOnError(th));
            this.downstream.onComplete();
        }

        @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
        public void onComplete() {
            this.downstream.onNext(Notification.createOnComplete());
            this.downstream.onComplete();
        }
    }
}
