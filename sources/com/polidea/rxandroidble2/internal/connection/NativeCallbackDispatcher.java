package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import com.polidea.rxandroidble2.HiddenBluetoothGattCallback;
/* loaded from: classes2.dex */
class NativeCallbackDispatcher {
    private BluetoothGattCallback nativeCallback;
    private HiddenBluetoothGattCallback nativeCallbackHidden;

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyNativeChangedCallback(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothGattCallback bluetoothGattCallback = this.nativeCallback;
        if (bluetoothGattCallback != null) {
            bluetoothGattCallback.onCharacteristicChanged(bluetoothGatt, bluetoothGattCharacteristic);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyNativeConnectionStateCallback(BluetoothGatt bluetoothGatt, int i, int i2) {
        BluetoothGattCallback bluetoothGattCallback = this.nativeCallback;
        if (bluetoothGattCallback != null) {
            bluetoothGattCallback.onConnectionStateChange(bluetoothGatt, i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyNativeDescriptorReadCallback(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
        BluetoothGattCallback bluetoothGattCallback = this.nativeCallback;
        if (bluetoothGattCallback != null) {
            bluetoothGattCallback.onDescriptorRead(bluetoothGatt, bluetoothGattDescriptor, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyNativeDescriptorWriteCallback(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
        BluetoothGattCallback bluetoothGattCallback = this.nativeCallback;
        if (bluetoothGattCallback != null) {
            bluetoothGattCallback.onDescriptorWrite(bluetoothGatt, bluetoothGattDescriptor, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyNativeMtuChangedCallback(BluetoothGatt bluetoothGatt, int i, int i2) {
        BluetoothGattCallback bluetoothGattCallback = this.nativeCallback;
        if (bluetoothGattCallback != null) {
            bluetoothGattCallback.onMtuChanged(bluetoothGatt, i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyNativeReadRssiCallback(BluetoothGatt bluetoothGatt, int i, int i2) {
        BluetoothGattCallback bluetoothGattCallback = this.nativeCallback;
        if (bluetoothGattCallback != null) {
            bluetoothGattCallback.onReadRemoteRssi(bluetoothGatt, i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyNativeReliableWriteCallback(BluetoothGatt bluetoothGatt, int i) {
        BluetoothGattCallback bluetoothGattCallback = this.nativeCallback;
        if (bluetoothGattCallback != null) {
            bluetoothGattCallback.onReliableWriteCompleted(bluetoothGatt, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyNativeServicesDiscoveredCallback(BluetoothGatt bluetoothGatt, int i) {
        BluetoothGattCallback bluetoothGattCallback = this.nativeCallback;
        if (bluetoothGattCallback != null) {
            bluetoothGattCallback.onServicesDiscovered(bluetoothGatt, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyNativeWriteCallback(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        BluetoothGattCallback bluetoothGattCallback = this.nativeCallback;
        if (bluetoothGattCallback != null) {
            bluetoothGattCallback.onCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyNativeParamsUpdateCallback(BluetoothGatt bluetoothGatt, int i, int i2, int i3, int i4) {
        HiddenBluetoothGattCallback hiddenBluetoothGattCallback = this.nativeCallbackHidden;
        if (hiddenBluetoothGattCallback != null) {
            hiddenBluetoothGattCallback.onConnectionUpdated(bluetoothGatt, i, i2, i3, i4);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setNativeCallback(BluetoothGattCallback bluetoothGattCallback) {
        this.nativeCallback = bluetoothGattCallback;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void notifyNativeReadCallback(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        BluetoothGattCallback bluetoothGattCallback = this.nativeCallback;
        if (bluetoothGattCallback != null) {
            bluetoothGattCallback.onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setNativeCallbackHidden(HiddenBluetoothGattCallback hiddenBluetoothGattCallback) {
        this.nativeCallbackHidden = hiddenBluetoothGattCallback;
    }
}
