package com.polidea.rxandroidble2.exceptions;

import java.util.UUID;
/* loaded from: classes2.dex */
public class BleDescriptorNotFoundException extends BleException {
    private final UUID descriptorUUID;

    public BleDescriptorNotFoundException(UUID uuid) {
        super("Descriptor not found with UUID " + uuid);
        this.descriptorUUID = uuid;
    }

    public UUID getDescriptorUUID() {
        return this.descriptorUUID;
    }
}
