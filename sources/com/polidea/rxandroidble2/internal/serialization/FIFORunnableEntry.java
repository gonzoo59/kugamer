package com.polidea.rxandroidble2.internal.serialization;

import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.operations.Operation;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicLong;
/* loaded from: classes2.dex */
class FIFORunnableEntry<T> implements Comparable<FIFORunnableEntry> {
    private static final AtomicLong SEQUENCE = new AtomicLong(0);
    final Operation<T> operation;
    final ObservableEmitter<T> operationResultObserver;
    private final long seqNum = SEQUENCE.getAndIncrement();

    /* JADX INFO: Access modifiers changed from: package-private */
    public FIFORunnableEntry(Operation<T> operation, ObservableEmitter<T> observableEmitter) {
        this.operation = operation;
        this.operationResultObserver = observableEmitter;
    }

    @Override // java.lang.Comparable
    public int compareTo(FIFORunnableEntry fIFORunnableEntry) {
        int compareTo = this.operation.compareTo(fIFORunnableEntry.operation);
        return (compareTo != 0 || fIFORunnableEntry.operation == this.operation) ? compareTo : this.seqNum < fIFORunnableEntry.seqNum ? -1 : 1;
    }

    public void run(final QueueSemaphore queueSemaphore, final Scheduler scheduler) {
        if (this.operationResultObserver.isDisposed()) {
            LoggerUtil.logOperationSkippedBecauseDisposedWhenAboutToRun(this.operation);
            queueSemaphore.release();
            return;
        }
        scheduler.scheduleDirect(new Runnable() { // from class: com.polidea.rxandroidble2.internal.serialization.FIFORunnableEntry.1
            @Override // java.lang.Runnable
            public void run() {
                FIFORunnableEntry.this.operation.run(queueSemaphore).unsubscribeOn(scheduler).subscribe((Observer<T>) new Observer<T>() { // from class: com.polidea.rxandroidble2.internal.serialization.FIFORunnableEntry.1.1
                    @Override // io.reactivex.Observer
                    public void onSubscribe(Disposable disposable) {
                        FIFORunnableEntry.this.operationResultObserver.setDisposable(disposable);
                    }

                    @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
                    public void onNext(T t) {
                        FIFORunnableEntry.this.operationResultObserver.onNext(t);
                    }

                    @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
                    public void onError(Throwable th) {
                        FIFORunnableEntry.this.operationResultObserver.tryOnError(th);
                    }

                    @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
                    public void onComplete() {
                        FIFORunnableEntry.this.operationResultObserver.onComplete();
                    }
                });
            }
        });
    }
}
