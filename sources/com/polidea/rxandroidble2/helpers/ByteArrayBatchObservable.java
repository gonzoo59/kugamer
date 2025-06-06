package com.polidea.rxandroidble2.helpers;

import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import java.nio.ByteBuffer;
import org.reactivestreams.Subscriber;
/* loaded from: classes2.dex */
public class ByteArrayBatchObservable extends Flowable<byte[]> {
    final ByteBuffer byteBuffer;
    final int maxBatchSize;

    public ByteArrayBatchObservable(byte[] bArr, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxBatchSize must be > 0 but found: " + i);
        }
        this.byteBuffer = ByteBuffer.wrap(bArr);
        this.maxBatchSize = i;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super byte[]> subscriber) {
        Flowable.generate(new Consumer<Emitter<byte[]>>() { // from class: com.polidea.rxandroidble2.helpers.ByteArrayBatchObservable.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Emitter<byte[]> emitter) {
                int min = Math.min(ByteArrayBatchObservable.this.byteBuffer.remaining(), ByteArrayBatchObservable.this.maxBatchSize);
                if (min == 0) {
                    emitter.onComplete();
                    return;
                }
                byte[] bArr = new byte[min];
                ByteArrayBatchObservable.this.byteBuffer.get(bArr);
                emitter.onNext(bArr);
            }
        }).subscribe(subscriber);
    }
}
