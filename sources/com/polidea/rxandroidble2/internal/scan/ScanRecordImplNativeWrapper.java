package com.polidea.rxandroidble2.internal.scan;

import android.os.ParcelUuid;
import android.util.SparseArray;
import com.polidea.rxandroidble2.scan.ScanRecord;
import java.util.List;
import java.util.Map;
/* loaded from: classes2.dex */
public class ScanRecordImplNativeWrapper implements ScanRecord {
    private final android.bluetooth.le.ScanRecord nativeScanRecord;

    public ScanRecordImplNativeWrapper(android.bluetooth.le.ScanRecord scanRecord) {
        this.nativeScanRecord = scanRecord;
    }

    @Override // com.polidea.rxandroidble2.scan.ScanRecord
    public int getAdvertiseFlags() {
        return this.nativeScanRecord.getAdvertiseFlags();
    }

    @Override // com.polidea.rxandroidble2.scan.ScanRecord
    public List<ParcelUuid> getServiceUuids() {
        return this.nativeScanRecord.getServiceUuids();
    }

    @Override // com.polidea.rxandroidble2.scan.ScanRecord
    public SparseArray<byte[]> getManufacturerSpecificData() {
        return this.nativeScanRecord.getManufacturerSpecificData();
    }

    @Override // com.polidea.rxandroidble2.scan.ScanRecord
    public byte[] getManufacturerSpecificData(int i) {
        return this.nativeScanRecord.getManufacturerSpecificData(i);
    }

    @Override // com.polidea.rxandroidble2.scan.ScanRecord
    public Map<ParcelUuid, byte[]> getServiceData() {
        return this.nativeScanRecord.getServiceData();
    }

    @Override // com.polidea.rxandroidble2.scan.ScanRecord
    public byte[] getServiceData(ParcelUuid parcelUuid) {
        return this.nativeScanRecord.getServiceData(parcelUuid);
    }

    @Override // com.polidea.rxandroidble2.scan.ScanRecord
    public int getTxPowerLevel() {
        return this.nativeScanRecord.getTxPowerLevel();
    }

    @Override // com.polidea.rxandroidble2.scan.ScanRecord
    public String getDeviceName() {
        return this.nativeScanRecord.getDeviceName();
    }

    @Override // com.polidea.rxandroidble2.scan.ScanRecord
    public byte[] getBytes() {
        return this.nativeScanRecord.getBytes();
    }
}
