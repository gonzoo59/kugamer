package com.polidea.rxandroidble2.internal.serialization;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble2.exceptions.BleException;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.connection.ConnectionScope;
import com.polidea.rxandroidble2.internal.connection.ConnectionSubscriptionWatcher;
import com.polidea.rxandroidble2.internal.connection.DisconnectionRouterOutput;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.operations.Operation;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.functions.Cancellable;
import io.reactivex.observers.DisposableObserver;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
@ConnectionScope
/* loaded from: classes2.dex */
public class ConnectionOperationQueueImpl implements ConnectionOperationQueue, ConnectionSubscriptionWatcher {
    private final String deviceMacAddress;
    private final DisconnectionRouterOutput disconnectionRouterOutput;
    private DisposableObserver<BleException> disconnectionThrowableSubscription;
    private final Future<?> runnableFuture;
    final OperationPriorityFifoBlockingQueue queue = new OperationPriorityFifoBlockingQueue();
    volatile boolean shouldRun = true;
    private BleException disconnectionException = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public ConnectionOperationQueueImpl(@Named("mac-address") final String str, DisconnectionRouterOutput disconnectionRouterOutput, @Named("executor_connection_queue") ExecutorService executorService, @Named("bluetooth_interaction") final Scheduler scheduler) {
        this.deviceMacAddress = str;
        this.disconnectionRouterOutput = disconnectionRouterOutput;
        this.runnableFuture = executorService.submit(new Runnable() { // from class: com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueueImpl.1
            @Override // java.lang.Runnable
            public void run() {
                while (ConnectionOperationQueueImpl.this.shouldRun) {
                    try {
                        FIFORunnableEntry<?> take = ConnectionOperationQueueImpl.this.queue.take();
                        Operation<?> operation = take.operation;
                        long currentTimeMillis = System.currentTimeMillis();
                        LoggerUtil.logOperationStarted(operation);
                        LoggerUtil.logOperationRunning(operation);
                        QueueSemaphore queueSemaphore = new QueueSemaphore();
                        take.run(queueSemaphore, scheduler);
                        queueSemaphore.awaitRelease();
                        LoggerUtil.logOperationFinished(operation, currentTimeMillis, System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        synchronized (ConnectionOperationQueueImpl.this) {
                            if (!ConnectionOperationQueueImpl.this.shouldRun) {
                                break;
                            }
                            RxBleLog.e(e, "Error while processing connection operation queue", new Object[0]);
                        }
                    }
                }
                ConnectionOperationQueueImpl.this.flushQueue();
                RxBleLog.v("Terminated (%s)", LoggerUtil.commonMacMessage(str));
            }
        });
    }

    synchronized void flushQueue() {
        while (!this.queue.isEmpty()) {
            this.queue.takeNow().operationResultObserver.tryOnError(this.disconnectionException);
        }
    }

    @Override // com.polidea.rxandroidble2.internal.serialization.ClientOperationQueue
    public synchronized <T> Observable<T> queue(final Operation<T> operation) {
        if (!this.shouldRun) {
            return Observable.error(this.disconnectionException);
        }
        return Observable.create(new ObservableOnSubscribe<T>() { // from class: com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueueImpl.2
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<T> observableEmitter) {
                final FIFORunnableEntry fIFORunnableEntry = new FIFORunnableEntry(operation, observableEmitter);
                observableEmitter.setCancellable(new Cancellable() { // from class: com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueueImpl.2.1
                    @Override // io.reactivex.functions.Cancellable
                    public void cancel() {
                        if (ConnectionOperationQueueImpl.this.queue.remove(fIFORunnableEntry)) {
                            LoggerUtil.logOperationRemoved(operation);
                        }
                    }
                });
                LoggerUtil.logOperationQueued(operation);
                ConnectionOperationQueueImpl.this.queue.add(fIFORunnableEntry);
            }
        });
    }

    @Override // com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueue
    public synchronized void terminate(BleException bleException) {
        if (this.disconnectionException != null) {
            return;
        }
        RxBleLog.d(bleException, "Connection operations queue to be terminated (%s)", LoggerUtil.commonMacMessage(this.deviceMacAddress));
        this.shouldRun = false;
        this.disconnectionException = bleException;
        this.runnableFuture.cancel(true);
    }

    @Override // com.polidea.rxandroidble2.internal.connection.ConnectionSubscriptionWatcher
    public void onConnectionSubscribed() {
        this.disconnectionThrowableSubscription = (DisposableObserver) this.disconnectionRouterOutput.asValueOnlyObservable().subscribeWith(new DisposableObserver<BleException>() { // from class: com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueueImpl.3
            @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
            public void onComplete() {
            }

            @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer, org.reactivestreams.Subscriber
            public void onNext(BleException bleException) {
                ConnectionOperationQueueImpl.this.terminate(bleException);
            }
        });
    }

    @Override // com.polidea.rxandroidble2.internal.connection.ConnectionSubscriptionWatcher
    public void onConnectionUnsubscribed() {
        this.disconnectionThrowableSubscription.dispose();
        this.disconnectionThrowableSubscription = null;
        terminate(new BleDisconnectedException(this.deviceMacAddress, -1));
    }
}
