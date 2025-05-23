package com.polidea.rxandroidble2.internal.serialization;

import io.reactivex.internal.schedulers.NonBlockingThread;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
/* loaded from: classes2.dex */
public class RxBleThreadFactory extends AtomicLong implements ThreadFactory {
    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        RxBleNonBlockingThread rxBleNonBlockingThread = new RxBleNonBlockingThread(runnable, "RxBleThread-" + incrementAndGet());
        rxBleNonBlockingThread.setPriority(5);
        rxBleNonBlockingThread.setDaemon(true);
        return rxBleNonBlockingThread;
    }

    /* loaded from: classes2.dex */
    static final class RxBleNonBlockingThread extends Thread implements NonBlockingThread {
        RxBleNonBlockingThread(Runnable runnable, String str) {
            super(runnable, str);
        }
    }
}
