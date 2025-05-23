package com.jieli.otasdk.tool.ota.ble.interfaces;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import com.jieli.otasdk.tool.ota.ble.model.BleScanInfo;
import java.util.List;
import java.util.UUID;
/* loaded from: classes2.dex */
public abstract class BleEventCallback implements IBleEventCallback {
    @Override // com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
    public void onAdapterChange(boolean z) {
    }

    @Override // com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
    public void onBleConnection(BluetoothDevice bluetoothDevice, int i) {
    }

    @Override // com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
    public void onBleDataBlockChanged(BluetoothDevice bluetoothDevice, int i, int i2) {
    }

    @Override // com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
    public void onBleDataNotification(BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, byte[] bArr) {
    }

    @Override // com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
    public void onBleNotificationStatus(BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, int i) {
    }

    @Override // com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
    public void onBleServiceDiscovery(BluetoothDevice bluetoothDevice, int i, List<BluetoothGattService> list) {
    }

    @Override // com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
    public void onBleWriteStatus(BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, byte[] bArr, int i) {
    }

    @Override // com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
    public void onConnectionUpdated(BluetoothDevice bluetoothDevice, int i, int i2, int i3, int i4) {
    }

    @Override // com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
    public void onDiscoveryBle(BluetoothDevice bluetoothDevice, BleScanInfo bleScanInfo) {
    }

    @Override // com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
    public void onDiscoveryBleChange(boolean z) {
    }
}
