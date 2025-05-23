package com.polidea.rxandroidble2.internal;

import android.os.DeadObjectException;
import com.polidea.rxandroidble2.exceptions.BleException;
import com.polidea.rxandroidble2.internal.operations.Operation;
import com.polidea.rxandroidble2.internal.serialization.QueueReleaseInterface;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
/* loaded from: classes2.dex */
public abstract class QueueOperation<T> implements Operation<T> {
    protected abstract void protectedRun(ObservableEmitter<T> observableEmitter, QueueReleaseInterface queueReleaseInterface) throws Throwable;

    protected abstract BleException provideException(DeadObjectException deadObjectException);

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Operation<?> operation) {
        return compareTo2((Operation) operation);
    }

    @Override // com.polidea.rxandroidble2.internal.operations.Operation
    public final Observable<T> run(final QueueReleaseInterface queueReleaseInterface) {
        return Observable.create(new ObservableOnSubscribe<T>() { // from class: com.polidea.rxandroidble2.internal.QueueOperation.1
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<T> observableEmitter) {
                try {
                    QueueOperation.this.protectedRun(observableEmitter, queueReleaseInterface);
                } catch (DeadObjectException e) {
                    observableEmitter.tryOnError(QueueOperation.this.provideException(e));
                    RxBleLog.e(e, "QueueOperation terminated with a DeadObjectException", new Object[0]);
                } catch (Throwable th) {
                    observableEmitter.tryOnError(th);
                    RxBleLog.e(th, "QueueOperation terminated with an unexpected exception", new Object[0]);
                }
            }
        });
    }

    @Override // com.polidea.rxandroidble2.internal.operations.Operation
    public Priority definedPriority() {
        return Priority.NORMAL;
    }

    /* renamed from: compareTo  reason: avoid collision after fix types in other method */
    public int compareTo2(Operation operation) {
        return operation.definedPriority().priority - definedPriority().priority;
    }
}
