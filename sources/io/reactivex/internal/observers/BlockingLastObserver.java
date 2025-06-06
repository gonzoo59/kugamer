package io.reactivex.internal.observers;
/* loaded from: classes2.dex */
public final class BlockingLastObserver<T> extends BlockingBaseObserver<T> {
    @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
    public void onNext(T t) {
        this.value = t;
    }

    @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        this.value = null;
        this.error = th;
        countDown();
    }
}
