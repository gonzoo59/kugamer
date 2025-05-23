package com.polidea.rxandroidble2.internal;

import java.util.UUID;
/* loaded from: classes2.dex */
public class BleIllegalOperationException extends RuntimeException {
    public final UUID characteristicUUID;
    public final int neededProperties;
    public final int supportedProperties;

    public BleIllegalOperationException(String str, UUID uuid, int i, int i2) {
        super(str);
        this.characteristicUUID = uuid;
        this.supportedProperties = i;
        this.neededProperties = i2;
    }
}
