package com.polidea.rxandroidble2.exceptions;

import android.bluetooth.BluetoothGatt;
/* loaded from: classes2.dex */
public class BleGattCallbackTimeoutException extends BleGattException {
    public BleGattCallbackTimeoutException(BluetoothGatt bluetoothGatt, BleGattOperationType bleGattOperationType) {
        super(bluetoothGatt, bleGattOperationType);
    }
}
