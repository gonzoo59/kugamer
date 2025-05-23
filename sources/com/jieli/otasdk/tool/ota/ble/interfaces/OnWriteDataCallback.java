package com.jieli.otasdk.tool.ota.ble.interfaces;

import android.bluetooth.BluetoothDevice;
import java.util.UUID;
/* loaded from: classes2.dex */
public interface OnWriteDataCallback {
    void onBleResult(BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, boolean z, byte[] bArr);
}
