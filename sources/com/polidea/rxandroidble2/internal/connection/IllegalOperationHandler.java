package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import com.polidea.rxandroidble2.internal.BleIllegalOperationException;
/* loaded from: classes2.dex */
public abstract class IllegalOperationHandler {
    protected final IllegalOperationMessageCreator messageCreator;

    public abstract BleIllegalOperationException handleMismatchData(BluetoothGattCharacteristic bluetoothGattCharacteristic, int i);

    /* JADX INFO: Access modifiers changed from: package-private */
    public IllegalOperationHandler(IllegalOperationMessageCreator illegalOperationMessageCreator) {
        this.messageCreator = illegalOperationMessageCreator;
    }
}
