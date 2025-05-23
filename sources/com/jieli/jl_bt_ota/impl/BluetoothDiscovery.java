package com.jieli.jl_bt_ota.impl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.jieli.jl_bt_ota.constant.ErrorCode;
import com.jieli.jl_bt_ota.model.BleScanMessage;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class BluetoothDiscovery extends BluetoothBase {
    private BluetoothAdapter.LeScanCallback leScanCallback;
    private Runnable mBleTimeOut;
    private BluetoothDiscoveryReceiver mBluetoothDiscoveryReceiver;
    private BluetoothLeScanner mBluetoothLeScanner;
    private Runnable mBreTimeOut;
    private final List<BluetoothDevice> mDiscoveredDevices;
    private final List<BluetoothDevice> mDiscoveredEdrDevices;
    private final Handler mHandler;
    private boolean mIsBleScanning;
    private boolean mIsBreScanning;
    private final List<BluetoothDevice> mPairedDevices;
    private ScanCallback mScanCallback;
    private int mScanType;
    private long mTimeout;

    public BluetoothDiscovery(Context context) {
        super(context);
        this.mPairedDevices = new ArrayList();
        this.mDiscoveredDevices = new ArrayList();
        this.mDiscoveredEdrDevices = new ArrayList();
        this.mIsBleScanning = false;
        this.mIsBreScanning = false;
        this.mTimeout = 8000L;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mBreTimeOut = new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothDiscovery.1
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothDiscovery.this.mIsBreScanning) {
                    BluetoothDiscovery.this.stopDeviceScan();
                    BluetoothDiscovery.this.mIsBreScanning = false;
                }
            }
        };
        this.mBleTimeOut = new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothDiscovery.2
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothDiscovery.this.mIsBleScanning) {
                    JL_Log.w(BluetoothDiscovery.this.TAG, "-mBleTimeOut- stopBLEScan: ");
                    BluetoothDiscovery.this.stopBLEScan();
                }
            }
        };
        this.leScanCallback = new BluetoothAdapter.LeScanCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothDiscovery.3
            @Override // android.bluetooth.BluetoothAdapter.LeScanCallback
            public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
                BluetoothDiscovery.this.filterDevice(bluetoothDevice, i, bArr, true);
            }
        };
        this.mScanCallback = new ScanCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothDiscovery.4
            @Override // android.bluetooth.le.ScanCallback
            public void onBatchScanResults(List<ScanResult> list) {
                super.onBatchScanResults(list);
            }

            @Override // android.bluetooth.le.ScanCallback
            public void onScanResult(int i, ScanResult scanResult) {
                super.onScanResult(i, scanResult);
                if (scanResult == null || scanResult.getScanRecord() == null) {
                    return;
                }
                BluetoothDiscovery.this.filterDevice(scanResult.getDevice(), scanResult.getRssi(), scanResult.getScanRecord().getBytes(), Build.VERSION.SDK_INT >= 26 ? scanResult.isConnectable() : true);
            }

            @Override // android.bluetooth.le.ScanCallback
            public void onScanFailed(int i) {
                super.onScanFailed(i);
                BluetoothDiscovery.this.onError(new BaseError(5, i, "scan ble error."));
            }
        };
        if (Build.VERSION.SDK_INT >= 21) {
            this.mBluetoothLeScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.jieli.jl_bt_ota.impl.BluetoothBase
    public void finalize() throws Throwable {
        unregisterReceiver();
        super.finalize();
    }

    public ArrayList<BluetoothDevice> getDiscoveredBluetoothDevices() {
        if (this.mScanType == 1) {
            return new ArrayList<>(this.mDiscoveredEdrDevices);
        }
        return new ArrayList<>(this.mDiscoveredDevices);
    }

    private void registerReceiver() {
        if (this.mBluetoothDiscoveryReceiver == null) {
            this.mBluetoothDiscoveryReceiver = new BluetoothDiscoveryReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
            intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
            intentFilter.addAction("android.bluetooth.device.action.FOUND");
            CommonUtil.getMainContext().registerReceiver(this.mBluetoothDiscoveryReceiver, intentFilter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterReceiver() {
        if (this.mBluetoothDiscoveryReceiver != null) {
            CommonUtil.getMainContext().unregisterReceiver(this.mBluetoothDiscoveryReceiver);
            this.mBluetoothDiscoveryReceiver = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class BluetoothDiscoveryReceiver extends BroadcastReceiver {
        private BluetoothDiscoveryReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            BluetoothDevice bluetoothDevice;
            String action = intent.getAction();
            if ("android.bluetooth.adapter.action.DISCOVERY_STARTED".equals(action)) {
                BluetoothDiscovery.this.mIsBreScanning = true;
                BluetoothDiscovery.this.mDiscoveredEdrDevices.clear();
                BluetoothDiscovery.this.notifyDiscoveryStatus(false, true);
                BluetoothDiscovery.this.mHandler.removeCallbacks(BluetoothDiscovery.this.mBreTimeOut);
                BluetoothDiscovery.this.mHandler.postDelayed(BluetoothDiscovery.this.mBreTimeOut, BluetoothDiscovery.this.mTimeout);
            } else if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action)) {
                BluetoothDiscovery.this.mIsBreScanning = false;
                BluetoothDiscovery.this.mDiscoveredEdrDevices.clear();
                BluetoothDiscovery.this.notifyDiscoveryStatus(false, false);
                BluetoothDiscovery.this.mHandler.removeCallbacks(BluetoothDiscovery.this.mBreTimeOut);
                BluetoothDiscovery.this.unregisterReceiver();
            } else if ("android.bluetooth.device.action.FOUND".equals(action) && (bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")) != null && BluetoothDiscovery.this.isBluetoothEnabled()) {
                if (!((BluetoothDiscovery.this.mScanType == 1 && bluetoothDevice.getType() != 2) || (BluetoothDiscovery.this.mScanType == 0 && bluetoothDevice.getType() != 1) || BluetoothDiscovery.this.mScanType == 2) || BluetoothDiscovery.this.mDiscoveredEdrDevices.contains(bluetoothDevice)) {
                    return;
                }
                BluetoothDiscovery.this.mDiscoveredEdrDevices.add(bluetoothDevice);
                BluetoothDiscovery.this.onDiscovery(bluetoothDevice);
                BluetoothDiscovery.this.onDiscovery(bluetoothDevice, new BleScanMessage().setEnableConnect(true));
            }
        }
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothBase
    public void onAdapterStatus(boolean z, boolean z2) {
        super.onAdapterStatus(z, z2);
        if (z) {
            return;
        }
        this.mIsBleScanning = false;
        this.mIsBreScanning = false;
        this.mDiscoveredDevices.clear();
        this.mDiscoveredEdrDevices.clear();
    }

    public int startDeviceScan(long j, int i) {
        this.mScanType = i;
        if (this.mBluetoothAdapter == null) {
            this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (this.mBluetoothAdapter == null) {
            JL_Log.e(this.TAG, "startDeviceScan :: this device is not supported bluetooth.");
            return 4098;
        } else if (!isBluetoothEnabled()) {
            notifyDiscoveryStatus(false, false);
            return 4099;
        } else {
            if (j <= 0) {
                this.mTimeout = 8000L;
            } else {
                this.mTimeout = j;
            }
            if (this.mBluetoothAdapter.isDiscovering()) {
                stopDeviceScan();
                SystemClock.sleep(500L);
            }
            registerReceiver();
            boolean startDiscovery = this.mBluetoothAdapter.startDiscovery();
            String str = this.TAG;
            Log.w(str, "-startDiscovery- >>>>>> bRet : " + startDiscovery);
            if (startDiscovery) {
                return 0;
            }
            notifyDiscoveryStatus(false, false);
            return 8194;
        }
    }

    public int stopDeviceScan() {
        if (this.mBluetoothAdapter == null) {
            JL_Log.e(this.TAG, "this device is not supported bluetooth.");
            return ErrorCode.SUB_ERR_OP_FAILED;
        } else if (this.mIsBreScanning) {
            Log.w(this.TAG, "-cancelDiscovery- >>>>>> ");
            if (this.mBluetoothAdapter.cancelDiscovery()) {
                this.mHandler.removeCallbacks(this.mBreTimeOut);
                return 0;
            }
            return 8194;
        } else {
            return 0;
        }
    }

    public int startBLEScan(long j) {
        if (this.mBluetoothAdapter == null) {
            this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (this.mBluetoothAdapter == null) {
            JL_Log.e(this.TAG, "this device is not supported bluetooth.");
            return ErrorCode.SUB_ERR_OP_FAILED;
        }
        this.mScanType = 0;
        if (!isBluetoothEnabled()) {
            notifyDiscoveryStatus(true, false);
            return 4099;
        }
        if (j <= 0) {
            j = 8000;
        }
        if (this.mIsBleScanning) {
            JL_Log.i(this.TAG, "scanning ble .....");
            if (this.mBluetoothLeScanner != null && Build.VERSION.SDK_INT >= 21) {
                this.mBluetoothLeScanner.flushPendingScanResults(this.mScanCallback);
            }
            this.mDiscoveredDevices.clear();
            this.mHandler.removeCallbacks(this.mBleTimeOut);
            this.mHandler.postDelayed(this.mBleTimeOut, j);
            notifyDiscoveryStatus(true, true);
            return 8194;
        }
        this.mHandler.removeCallbacks(this.mBleTimeOut);
        this.mHandler.postDelayed(this.mBleTimeOut, j);
        this.mIsBleScanning = true;
        notifyDiscoveryStatus(true, true);
        if (Build.VERSION.SDK_INT >= 21 && this.mBluetoothLeScanner != null) {
            ScanSettings build = this.mBluetoothOption.getBleScanMode() >= -1 ? new ScanSettings.Builder().setScanMode(this.mBluetoothOption.getBleScanMode()).build() : null;
            if (build == null) {
                this.mBluetoothLeScanner.startScan(this.mScanCallback);
            } else {
                this.mBluetoothLeScanner.startScan((List<ScanFilter>) null, build, this.mScanCallback);
            }
            Log.w(this.TAG, "-startBLEScan- >>>>>> startScan :>> ");
        } else {
            boolean startLeScan = this.mBluetoothAdapter.startLeScan(this.leScanCallback);
            String str = this.TAG;
            Log.w(str, "-startBLEScan- >>>>>> bRet : " + startLeScan);
            if (!startLeScan) {
                notifyDiscoveryStatus(true, false);
                return 8194;
            }
        }
        this.mDiscoveredDevices.clear();
        return 0;
    }

    public int stopBLEScan() {
        if (this.mBluetoothAdapter == null) {
            JL_Log.e(this.TAG, "this device is not supported bluetooth.");
            return ErrorCode.SUB_ERR_OP_FAILED;
        } else if (this.mIsBleScanning) {
            stopBleScanNoCallback();
            notifyDiscoveryStatus(true, false);
            return 0;
        } else {
            return 0;
        }
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothBase, com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void release() {
        super.release();
        stopDeviceScan();
        stopBLEScan();
        unregisterReceiver();
        this.mIsBleScanning = false;
        this.mIsBreScanning = false;
        this.mDiscoveredDevices.clear();
        this.mDiscoveredEdrDevices.clear();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    private void stopBleScanNoCallback() {
        if (this.mIsBleScanning) {
            Log.w(this.TAG, "-stopBLEScan- >>>>>> ");
            this.mIsBleScanning = false;
            if (Build.VERSION.SDK_INT >= 21 && this.mBluetoothLeScanner != null) {
                try {
                    if (isBluetoothEnabled()) {
                        this.mBluetoothLeScanner.stopScan(this.mScanCallback);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (this.mBluetoothAdapter != null) {
                try {
                    if (isBluetoothEnabled()) {
                        this.mBluetoothAdapter.stopLeScan(this.leScanCallback);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.mHandler.removeCallbacks(this.mBleTimeOut);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void filterDevice(BluetoothDevice bluetoothDevice, int i, byte[] bArr, boolean z) {
        if (bluetoothDevice == null || !isBluetoothEnabled() || TextUtils.isEmpty(bluetoothDevice.getName()) || this.mDiscoveredDevices.contains(bluetoothDevice)) {
            return;
        }
        this.mDiscoveredDevices.add(bluetoothDevice);
        onDiscovery(bluetoothDevice);
        onDiscovery(bluetoothDevice, new BleScanMessage().setRawData(bArr).setRssi(i).setEnableConnect(z));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isBleScanning() {
        return this.mIsBleScanning;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isDeviceScanning() {
        return this.mIsBreScanning;
    }

    public boolean isScanning() {
        return this.mIsBreScanning || this.mIsBleScanning;
    }

    public List<BluetoothDevice> getPairedDevices() {
        this.mPairedDevices.clear();
        if (this.mBluetoothAdapter != null && this.mBluetoothAdapter.getBondedDevices() != null) {
            this.mPairedDevices.addAll(this.mBluetoothAdapter.getBondedDevices());
        }
        return this.mPairedDevices;
    }

    public List<BluetoothDevice> getPairedBLEDevices() {
        this.mPairedDevices.clear();
        if (this.mBluetoothAdapter == null) {
            JL_Log.e(this.TAG, "this device is not supported bluetooth.");
            return this.mPairedDevices;
        }
        for (BluetoothDevice bluetoothDevice : this.mBluetoothAdapter.getBondedDevices()) {
            int type = bluetoothDevice.getType();
            if (type == 2 || type == 3) {
                this.mPairedDevices.add(bluetoothDevice);
            }
        }
        return this.mPairedDevices;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDiscoveryStatus(boolean z, boolean z2) {
        String str = this.TAG;
        JL_Log.i(str, "-notifyDiscoveryStatus- bBle : " + z + " ,bStart : " + z2);
        int i = this.mScanType;
        if (i == 0 && z) {
            onDiscoveryStatus(true, z2);
        } else if (i == 1 && !z) {
            onDiscoveryStatus(false, z2);
        }
        if (z2) {
            return;
        }
        this.mIsBleScanning = false;
        this.mIsBreScanning = false;
        this.mScanType = 0;
    }
}
