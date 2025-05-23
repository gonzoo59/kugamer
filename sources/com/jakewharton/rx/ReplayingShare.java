package com.jakewharton.rx;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.Objects;
import kotlin.jvm.internal.LongCompanionObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
/* loaded from: classes2.dex */
public final class ReplayingShare<T> implements ObservableTransformer<T, T>, FlowableTransformer<T, T> {
    private static final ReplayingShare<Object> INSTANCE = new ReplayingShare<>(null);
    private final T defaultValue;

    public static <T> ReplayingShare<T> instance() {
        return (ReplayingShare<T>) INSTANCE;
    }

    public static <T> ReplayingShare<T> createWithDefault(T t) {
        Objects.requireNonNull(t, "defaultValue == null");
        return new ReplayingShare<>(t);
    }

    private ReplayingShare(T t) {
        this.defaultValue = t;
    }

    @Override // io.reactivex.ObservableTransformer
    public Observable<T> apply(Observable<T> observable) {
        LastSeen lastSeen = new LastSeen(this.defaultValue);
        return new LastSeenObservable(observable.doOnEach(lastSeen).share(), lastSeen);
    }

    @Override // io.reactivex.FlowableTransformer
    public Flowable<T> apply(Flowable<T> flowable) {
        LastSeen lastSeen = new LastSeen(this.defaultValue);
        return new LastSeenFlowable(flowable.doOnEach(lastSeen).share(), lastSeen);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class LastSeen<T> implements Observer<T>, Subscriber<T> {
        private final T defaultValue;
        volatile T value;

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
        }

        @Override // org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
        }

        LastSeen(T t) {
            this.defaultValue = t;
            this.value = t;
        }

        @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.value = t;
        }

        @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.value = this.defaultValue;
        }

        @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
        public void onComplete() {
            this.value = this.defaultValue;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class LastSeenObservable<T> extends Observable<T> {
        private final LastSeen<T> lastSeen;
        private final Observable<T> upstream;

        LastSeenObservable(Observable<T> observable, LastSeen<T> lastSeen) {
            this.upstream = observable;
            this.lastSeen = lastSeen;
        }

        @Override // io.reactivex.Observable
        protected void subscribeActual(Observer<? super T> observer) {
            this.upstream.subscribe(new LastSeenObserver(observer, this.lastSeen));
        }
    }

    /* loaded from: classes2.dex */
    static final class LastSeenObserver<T> implements Observer<T> {
        private final Observer<? super T> downstream;
        private final LastSeen<T> lastSeen;

        LastSeenObserver(Observer<? super T> observer, LastSeen<T> lastSeen) {
            this.downstream = observer;
            this.lastSeen = lastSeen;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            this.downstream.onSubscribe(disposable);
            T t = this.lastSeen.value;
            if (t == null || disposable.isDisposed()) {
                return;
            }
            this.downstream.onNext(t);
        }

        @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.downstream.onNext(t);
        }

        @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class LastSeenFlowable<T> extends Flowable<T> {
        private final LastSeen<T> lastSeen;
        private final Flowable<T> upstream;

        LastSeenFlowable(Flowable<T> flowable, LastSeen<T> lastSeen) {
            this.upstream = flowable;
            this.lastSeen = lastSeen;
        }

        @Override // io.reactivex.Flowable
        protected void subscribeActual(Subscriber<? super T> subscriber) {
            this.upstream.subscribe(new LastSeenSubscriber(subscriber, this.lastSeen));
        }
    }

    /* loaded from: classes2.dex */
    static final class LastSeenSubscriber<T> implements Subscriber<T>, Subscription {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private volatile boolean cancelled;
        private final Subscriber<? super T> downstream;
        private boolean first = true;
        private final LastSeen<T> lastSeen;
        private Subscription subscription;

        LastSeenSubscriber(Subscriber<? super T> subscriber, LastSeen<T> lastSeen) {
            this.downstream = subscriber;
            this.lastSeen = lastSeen;
        }

        @Override // org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            this.downstream.onSubscribe(this);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (j == 0) {
                return;
            }
            if (this.first) {
                this.first = false;
                T t = this.lastSeen.value;
                if (t != null && !this.cancelled) {
                    this.downstream.onNext(t);
                    if (j != LongCompanionObject.MAX_VALUE) {
                        j--;
                        if (j == 0) {
                            return;
                        }
                    }
                }
            }
            this.subscription.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Subscription subscription = this.subscription;
            this.cancelled = true;
            subscription.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.downstream.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }
    }
}
