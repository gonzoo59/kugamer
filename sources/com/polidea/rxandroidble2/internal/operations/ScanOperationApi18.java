package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.scan.EmulatedScanFilterMatcher;
import com.polidea.rxandroidble2.internal.scan.InternalScanResultCreator;
import com.polidea.rxandroidble2.internal.scan.RxBleInternalScanResult;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import io.reactivex.ObservableEmitter;
/* loaded from: classes2.dex */
public class ScanOperationApi18 extends ScanOperation<RxBleInternalScanResult, BluetoothAdapter.LeScanCallback> {
    final EmulatedScanFilterMatcher scanFilterMatcher;
    final InternalScanResultCreator scanResultCreator;

    public ScanOperationApi18(RxBleAdapterWrapper rxBleAdapterWrapper, InternalScanResultCreator internalScanResultCreator, EmulatedScanFilterMatcher emulatedScanFilterMatcher) {
        super(rxBleAdapterWrapper);
        this.scanResultCreator = internalScanResultCreator;
        this.scanFilterMatcher = emulatedScanFilterMatcher;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.polidea.rxandroidble2.internal.operations.ScanOperation
    public BluetoothAdapter.LeScanCallback createScanCallback(final ObservableEmitter<RxBleInternalScanResult> observableEmitter) {
        return new BluetoothAdapter.LeScanCallback() { // from class: com.polidea.rxandroidble2.internal.operations.ScanOperationApi18.1
            @Override // android.bluetooth.BluetoothAdapter.LeScanCallback
            public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
                if (!ScanOperationApi18.this.scanFilterMatcher.isEmpty() && RxBleLog.isAtLeast(3) && RxBleLog.getShouldLogScannedPeripherals()) {
                    RxBleLog.d("%s, name=%s, rssi=%d, data=%s", LoggerUtil.commonMacMessage(bluetoothDevice.getAddress()), bluetoothDevice.getName(), Integer.valueOf(i), LoggerUtil.bytesToHex(bArr));
                }
                RxBleInternalScanResult create = ScanOperationApi18.this.scanResultCreator.create(bluetoothDevice, i, bArr);
                if (ScanOperationApi18.this.scanFilterMatcher.matches(create)) {
                    observableEmitter.onNext(create);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.polidea.rxandroidble2.internal.operations.ScanOperation
    public boolean startScan(RxBleAdapterWrapper rxBleAdapterWrapper, BluetoothAdapter.LeScanCallback leScanCallback) {
        if (this.scanFilterMatcher.isEmpty()) {
            RxBleLog.d("No library side filtering —> debug logs of scanned devices disabled", new Object[0]);
        }
        return rxBleAdapterWrapper.startLegacyLeScan(leScanCallback);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.polidea.rxandroidble2.internal.operations.ScanOperation
    public void stopScan(RxBleAdapterWrapper rxBleAdapterWrapper, BluetoothAdapter.LeScanCallback leScanCallback) {
        rxBleAdapterWrapper.stopLegacyLeScan(leScanCallback);
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("ScanOperationApi18{");
        if (this.scanFilterMatcher.isEmpty()) {
            str = "";
        } else {
            str = "ANY_MUST_MATCH -> " + this.scanFilterMatcher;
        }
        sb.append(str);
        sb.append('}');
        return sb.toString();
    }
}
