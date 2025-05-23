package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import java.util.concurrent.atomic.AtomicReference;
@ConnectionScope
/* loaded from: classes2.dex */
public class BluetoothGattProvider {
    private final AtomicReference<BluetoothGatt> reference = new AtomicReference<>();

    public BluetoothGatt getBluetoothGatt() {
        return this.reference.get();
    }

    public void updateBluetoothGatt(BluetoothGatt bluetoothGatt) {
        this.reference.compareAndSet(null, bluetoothGatt);
    }
}
