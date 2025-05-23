package com.jieli.jl_bt_ota.interfaces;

import android.bluetooth.BluetoothDevice;
import com.jieli.jl_bt_ota.model.BleScanMessage;
import com.jieli.jl_bt_ota.model.base.BaseError;
/* loaded from: classes2.dex */
public interface IBluetoothCallback {
    void onA2dpStatus(BluetoothDevice bluetoothDevice, int i);

    void onAdapterStatus(boolean z, boolean z2);

    void onBleDataBlockChanged(BluetoothDevice bluetoothDevice, int i, int i2);

    void onBondStatus(BluetoothDevice bluetoothDevice, int i);

    void onBtDeviceConnection(BluetoothDevice bluetoothDevice, int i);

    void onConnection(BluetoothDevice bluetoothDevice, int i);

    @Deprecated
    void onDiscovery(BluetoothDevice bluetoothDevice);

    void onDiscovery(BluetoothDevice bluetoothDevice, BleScanMessage bleScanMessage);

    void onDiscoveryStatus(boolean z, boolean z2);

    void onError(BaseError baseError);

    void onHfpStatus(BluetoothDevice bluetoothDevice, int i);

    void onReceiveData(BluetoothDevice bluetoothDevice, byte[] bArr);
}
