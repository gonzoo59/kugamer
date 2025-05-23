package com.jieli.jl_bt_ota.interfaces;

import android.bluetooth.BluetoothDevice;
import com.jieli.jl_bt_ota.model.BleScanMessage;
import com.jieli.jl_bt_ota.model.base.BaseError;
/* loaded from: classes2.dex */
public abstract class BtEventCallback implements IBluetoothCallback {
    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
    public void onA2dpStatus(BluetoothDevice bluetoothDevice, int i) {
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
    public void onAdapterStatus(boolean z, boolean z2) {
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
    public void onBleDataBlockChanged(BluetoothDevice bluetoothDevice, int i, int i2) {
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
    public void onBondStatus(BluetoothDevice bluetoothDevice, int i) {
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
    public void onBtDeviceConnection(BluetoothDevice bluetoothDevice, int i) {
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
    public void onConnection(BluetoothDevice bluetoothDevice, int i) {
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
    public void onDiscovery(BluetoothDevice bluetoothDevice) {
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
    public void onDiscovery(BluetoothDevice bluetoothDevice, BleScanMessage bleScanMessage) {
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
    public void onDiscoveryStatus(boolean z, boolean z2) {
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
    public void onError(BaseError baseError) {
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
    public void onHfpStatus(BluetoothDevice bluetoothDevice, int i) {
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
    public void onReceiveData(BluetoothDevice bluetoothDevice, byte[] bArr) {
    }
}
