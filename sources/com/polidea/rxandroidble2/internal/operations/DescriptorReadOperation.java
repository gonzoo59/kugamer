package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattDescriptor;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.SingleResponseOperation;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.util.ByteAssociation;
import com.polidea.rxandroidble2.internal.util.ByteAssociationUtil;
import io.reactivex.Single;
/* loaded from: classes2.dex */
public class DescriptorReadOperation extends SingleResponseOperation<ByteAssociation<BluetoothGattDescriptor>> {
    private final BluetoothGattDescriptor bluetoothGattDescriptor;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public DescriptorReadOperation(RxBleGattCallback rxBleGattCallback, BluetoothGatt bluetoothGatt, @Named("operation-timeout") TimeoutConfiguration timeoutConfiguration, BluetoothGattDescriptor bluetoothGattDescriptor) {
        super(bluetoothGatt, rxBleGattCallback, BleGattOperationType.DESCRIPTOR_READ, timeoutConfiguration);
        this.bluetoothGattDescriptor = bluetoothGattDescriptor;
    }

    @Override // com.polidea.rxandroidble2.internal.SingleResponseOperation
    protected Single<ByteAssociation<BluetoothGattDescriptor>> getCallback(RxBleGattCallback rxBleGattCallback) {
        return rxBleGattCallback.getOnDescriptorRead().filter(ByteAssociationUtil.descriptorPredicate(this.bluetoothGattDescriptor)).firstOrError();
    }

    @Override // com.polidea.rxandroidble2.internal.SingleResponseOperation
    protected boolean startOperation(BluetoothGatt bluetoothGatt) {
        return bluetoothGatt.readDescriptor(this.bluetoothGattDescriptor);
    }

    @Override // com.polidea.rxandroidble2.internal.SingleResponseOperation
    public String toString() {
        return "DescriptorReadOperation{" + super.toString() + ", descriptor=" + LoggerUtil.wrap(this.bluetoothGattDescriptor, false) + '}';
    }
}
