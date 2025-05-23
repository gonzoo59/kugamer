package com.jieli.otasdk.tool.ota.ble.interfaces;

import android.bluetooth.BluetoothGatt;
import java.util.UUID;
/* loaded from: classes2.dex */
public interface IBleOp {
    int getBleMtu();

    boolean writeDataByBle(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2, byte[] bArr);
}
