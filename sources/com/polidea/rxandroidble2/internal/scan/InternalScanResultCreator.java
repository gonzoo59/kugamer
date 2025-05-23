package com.polidea.rxandroidble2.internal.scan;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.ClientScope;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.util.UUIDUtil;
import com.polidea.rxandroidble2.scan.ScanCallbackType;
@ClientScope
/* loaded from: classes2.dex */
public class InternalScanResultCreator {
    private final UUIDUtil uuidUtil;

    @Inject
    public InternalScanResultCreator(UUIDUtil uUIDUtil) {
        this.uuidUtil = uUIDUtil;
    }

    public RxBleInternalScanResult create(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        return new RxBleInternalScanResult(bluetoothDevice, i, System.nanoTime(), this.uuidUtil.parseFromBytes(bArr), ScanCallbackType.CALLBACK_TYPE_UNSPECIFIED);
    }

    public RxBleInternalScanResult create(ScanResult scanResult) {
        return new RxBleInternalScanResult(scanResult.getDevice(), scanResult.getRssi(), scanResult.getTimestampNanos(), new ScanRecordImplNativeWrapper(scanResult.getScanRecord()), ScanCallbackType.CALLBACK_TYPE_BATCH);
    }

    public RxBleInternalScanResult create(int i, ScanResult scanResult) {
        return new RxBleInternalScanResult(scanResult.getDevice(), scanResult.getRssi(), scanResult.getTimestampNanos(), new ScanRecordImplNativeWrapper(scanResult.getScanRecord()), toScanCallbackType(i));
    }

    private static ScanCallbackType toScanCallbackType(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i == 4) {
                    return ScanCallbackType.CALLBACK_TYPE_MATCH_LOST;
                }
                RxBleLog.w("Unknown callback type %d -> check android.bluetooth.le.ScanSettings", Integer.valueOf(i));
                return ScanCallbackType.CALLBACK_TYPE_UNKNOWN;
            }
            return ScanCallbackType.CALLBACK_TYPE_FIRST_MATCH;
        }
        return ScanCallbackType.CALLBACK_TYPE_ALL_MATCHES;
    }
}
