package com.polidea.rxandroidble2.exceptions;
/* loaded from: classes2.dex */
public class BleAlreadyConnectedException extends BleException {
    public BleAlreadyConnectedException(String str) {
        super("Already connected to device with MAC address " + str);
    }
}
