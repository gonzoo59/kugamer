package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.BleIllegalOperationException;
import io.reactivex.Completable;
import io.reactivex.functions.Action;
/* loaded from: classes2.dex */
public class IllegalOperationChecker {
    final IllegalOperationHandler resultHandler;

    @Inject
    public IllegalOperationChecker(IllegalOperationHandler illegalOperationHandler) {
        this.resultHandler = illegalOperationHandler;
    }

    public Completable checkAnyPropertyMatches(final BluetoothGattCharacteristic bluetoothGattCharacteristic, final int i) {
        return Completable.fromAction(new Action() { // from class: com.polidea.rxandroidble2.internal.connection.IllegalOperationChecker.1
            @Override // io.reactivex.functions.Action
            public void run() {
                BleIllegalOperationException handleMismatchData;
                if ((bluetoothGattCharacteristic.getProperties() & i) == 0 && (handleMismatchData = IllegalOperationChecker.this.resultHandler.handleMismatchData(bluetoothGattCharacteristic, i)) != null) {
                    throw handleMismatchData;
                }
            }
        });
    }
}
