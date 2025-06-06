package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.exceptions.BleGattCannotStartException;
import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.SingleResponseOperation;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import io.reactivex.Single;
/* loaded from: classes2.dex */
public class ConnectionPriorityChangeOperation extends SingleResponseOperation<Long> {
    private final int connectionPriority;
    private final TimeoutConfiguration successTimeoutConfiguration;

    private static String connectionPriorityToString(int i) {
        return i != 0 ? i != 2 ? "CONNECTION_PRIORITY_HIGH" : "CONNECTION_PRIORITY_LOW_POWER" : "CONNECTION_PRIORITY_BALANCED";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public ConnectionPriorityChangeOperation(RxBleGattCallback rxBleGattCallback, BluetoothGatt bluetoothGatt, TimeoutConfiguration timeoutConfiguration, int i, TimeoutConfiguration timeoutConfiguration2) {
        super(bluetoothGatt, rxBleGattCallback, BleGattOperationType.CONNECTION_PRIORITY_CHANGE, timeoutConfiguration);
        this.connectionPriority = i;
        this.successTimeoutConfiguration = timeoutConfiguration2;
    }

    @Override // com.polidea.rxandroidble2.internal.SingleResponseOperation
    protected Single<Long> getCallback(RxBleGattCallback rxBleGattCallback) {
        return Single.timer(this.successTimeoutConfiguration.timeout, this.successTimeoutConfiguration.timeoutTimeUnit, this.successTimeoutConfiguration.timeoutScheduler);
    }

    @Override // com.polidea.rxandroidble2.internal.SingleResponseOperation
    protected boolean startOperation(BluetoothGatt bluetoothGatt) throws IllegalArgumentException, BleGattCannotStartException {
        return bluetoothGatt.requestConnectionPriority(this.connectionPriority);
    }

    @Override // com.polidea.rxandroidble2.internal.SingleResponseOperation
    public String toString() {
        return "ConnectionPriorityChangeOperation{" + super.toString() + ", connectionPriority=" + connectionPriorityToString(this.connectionPriority) + ", successTimeout=" + this.successTimeoutConfiguration + '}';
    }
}
