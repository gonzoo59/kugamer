package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.internal.operations.OperationsProvider;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueue;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import java.util.UUID;
/* loaded from: classes2.dex */
public final class LongWriteOperationBuilderImpl implements RxBleConnection.LongWriteOperationBuilder {
    byte[] bytes;
    PayloadSizeLimitProvider maxBatchSizeProvider;
    final ConnectionOperationQueue operationQueue;
    final OperationsProvider operationsProvider;
    private final RxBleConnection rxBleConnection;
    RxBleConnection.WriteOperationAckStrategy writeOperationAckStrategy = new ImmediateSerializedBatchAckStrategy();
    RxBleConnection.WriteOperationRetryStrategy writeOperationRetryStrategy = new NoRetryStrategy();
    private Single<BluetoothGattCharacteristic> writtenCharacteristicObservable;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public LongWriteOperationBuilderImpl(ConnectionOperationQueue connectionOperationQueue, MtuBasedPayloadSizeLimit mtuBasedPayloadSizeLimit, RxBleConnection rxBleConnection, OperationsProvider operationsProvider) {
        this.operationQueue = connectionOperationQueue;
        this.maxBatchSizeProvider = mtuBasedPayloadSizeLimit;
        this.rxBleConnection = rxBleConnection;
        this.operationsProvider = operationsProvider;
    }

    @Override // com.polidea.rxandroidble2.RxBleConnection.LongWriteOperationBuilder
    public RxBleConnection.LongWriteOperationBuilder setBytes(byte[] bArr) {
        this.bytes = bArr;
        return this;
    }

    @Override // com.polidea.rxandroidble2.RxBleConnection.LongWriteOperationBuilder
    public RxBleConnection.LongWriteOperationBuilder setCharacteristicUuid(UUID uuid) {
        this.writtenCharacteristicObservable = this.rxBleConnection.getCharacteristic(uuid);
        return this;
    }

    @Override // com.polidea.rxandroidble2.RxBleConnection.LongWriteOperationBuilder
    public RxBleConnection.LongWriteOperationBuilder setCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.writtenCharacteristicObservable = Single.just(bluetoothGattCharacteristic);
        return this;
    }

    @Override // com.polidea.rxandroidble2.RxBleConnection.LongWriteOperationBuilder
    public RxBleConnection.LongWriteOperationBuilder setMaxBatchSize(int i) {
        this.maxBatchSizeProvider = new ConstantPayloadSizeLimit(i);
        return this;
    }

    @Override // com.polidea.rxandroidble2.RxBleConnection.LongWriteOperationBuilder
    public RxBleConnection.LongWriteOperationBuilder setWriteOperationRetryStrategy(RxBleConnection.WriteOperationRetryStrategy writeOperationRetryStrategy) {
        this.writeOperationRetryStrategy = writeOperationRetryStrategy;
        return this;
    }

    @Override // com.polidea.rxandroidble2.RxBleConnection.LongWriteOperationBuilder
    public RxBleConnection.LongWriteOperationBuilder setWriteOperationAckStrategy(RxBleConnection.WriteOperationAckStrategy writeOperationAckStrategy) {
        this.writeOperationAckStrategy = writeOperationAckStrategy;
        return this;
    }

    @Override // com.polidea.rxandroidble2.RxBleConnection.LongWriteOperationBuilder
    public Observable<byte[]> build() {
        Single<BluetoothGattCharacteristic> single = this.writtenCharacteristicObservable;
        if (single == null) {
            throw new IllegalArgumentException("setCharacteristicUuid() or setCharacteristic() needs to be called before build()");
        }
        if (this.bytes == null) {
            throw new IllegalArgumentException("setBytes() needs to be called before build()");
        }
        return single.flatMapObservable(new Function<BluetoothGattCharacteristic, Observable<byte[]>>() { // from class: com.polidea.rxandroidble2.internal.connection.LongWriteOperationBuilderImpl.1
            @Override // io.reactivex.functions.Function
            public Observable<byte[]> apply(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                return LongWriteOperationBuilderImpl.this.operationQueue.queue(LongWriteOperationBuilderImpl.this.operationsProvider.provideLongWriteOperation(bluetoothGattCharacteristic, LongWriteOperationBuilderImpl.this.writeOperationAckStrategy, LongWriteOperationBuilderImpl.this.writeOperationRetryStrategy, LongWriteOperationBuilderImpl.this.maxBatchSizeProvider, LongWriteOperationBuilderImpl.this.bytes));
            }
        });
    }
}
