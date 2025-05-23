package com.polidea.rxandroidble2.internal.scan;
/* loaded from: classes2.dex */
public interface ScanFilterInterface {
    boolean isAllFieldsEmpty();

    boolean matches(RxBleInternalScanResult rxBleInternalScanResult);
}
