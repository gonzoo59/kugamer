package com.polidea.rxandroidble2.internal.util;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.RxBleLog;
import java.util.List;
import java.util.Set;
/* loaded from: classes2.dex */
public class RxBleAdapterWrapper {
    private final BluetoothAdapter bluetoothAdapter;

    @Inject
    public RxBleAdapterWrapper(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
    }

    public BluetoothDevice getRemoteDevice(String str) {
        return this.bluetoothAdapter.getRemoteDevice(str);
    }

    public boolean hasBluetoothAdapter() {
        return this.bluetoothAdapter != null;
    }

    public boolean isBluetoothEnabled() {
        BluetoothAdapter bluetoothAdapter = this.bluetoothAdapter;
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    public boolean startLegacyLeScan(BluetoothAdapter.LeScanCallback leScanCallback) {
        return this.bluetoothAdapter.startLeScan(leScanCallback);
    }

    public void stopLegacyLeScan(BluetoothAdapter.LeScanCallback leScanCallback) {
        this.bluetoothAdapter.stopLeScan(leScanCallback);
    }

    public void startLeScan(List<ScanFilter> list, ScanSettings scanSettings, ScanCallback scanCallback) {
        this.bluetoothAdapter.getBluetoothLeScanner().startScan(list, scanSettings, scanCallback);
    }

    public int startLeScan(List<ScanFilter> list, ScanSettings scanSettings, PendingIntent pendingIntent) {
        return this.bluetoothAdapter.getBluetoothLeScanner().startScan(list, scanSettings, pendingIntent);
    }

    public void stopLeScan(PendingIntent pendingIntent) {
        this.bluetoothAdapter.getBluetoothLeScanner().stopScan(pendingIntent);
    }

    public void stopLeScan(ScanCallback scanCallback) {
        if (!this.bluetoothAdapter.isEnabled()) {
            RxBleLog.v("BluetoothAdapter is disabled, calling BluetoothLeScanner.stopScan(ScanCallback) may cause IllegalStateException", new Object[0]);
            return;
        }
        BluetoothLeScanner bluetoothLeScanner = this.bluetoothAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner == null) {
            RxBleLog.w("Cannot call BluetoothLeScanner.stopScan(ScanCallback) on 'null' reference; BluetoothAdapter.isEnabled() == %b", Boolean.valueOf(this.bluetoothAdapter.isEnabled()));
        } else {
            bluetoothLeScanner.stopScan(scanCallback);
        }
    }

    public Set<BluetoothDevice> getBondedDevices() {
        return this.bluetoothAdapter.getBondedDevices();
    }
}
