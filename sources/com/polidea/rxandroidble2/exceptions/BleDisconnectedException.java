package com.polidea.rxandroidble2.exceptions;

import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.utils.GattStatusParser;
/* loaded from: classes2.dex */
public class BleDisconnectedException extends BleException {
    public static final int UNKNOWN_STATUS = -1;
    public final String bluetoothDeviceAddress;
    public final int state;

    public static BleDisconnectedException adapterDisabled(String str) {
        return new BleDisconnectedException(new BleAdapterDisabledException(), str, -1);
    }

    @Deprecated
    public BleDisconnectedException() {
        this("", -1);
    }

    @Deprecated
    public BleDisconnectedException(Throwable th, String str) {
        this(th, str, -1);
    }

    @Deprecated
    public BleDisconnectedException(String str) {
        this(str, -1);
    }

    public BleDisconnectedException(Throwable th, String str, int i) {
        super(createMessage(str, i), th);
        this.bluetoothDeviceAddress = str;
        this.state = i;
    }

    public BleDisconnectedException(String str, int i) {
        super(createMessage(str, i));
        this.bluetoothDeviceAddress = str;
        this.state = i;
    }

    private static String createMessage(String str, int i) {
        String gattCallbackStatusDescription = GattStatusParser.getGattCallbackStatusDescription(i);
        return "Disconnected from " + LoggerUtil.commonMacMessage(str) + " with status " + i + " (" + gattCallbackStatusDescription + ")";
    }
}
