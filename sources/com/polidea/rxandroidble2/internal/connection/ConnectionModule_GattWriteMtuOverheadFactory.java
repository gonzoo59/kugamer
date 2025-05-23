package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
/* loaded from: classes2.dex */
public final class ConnectionModule_GattWriteMtuOverheadFactory implements Factory<Integer> {
    private static final ConnectionModule_GattWriteMtuOverheadFactory INSTANCE = new ConnectionModule_GattWriteMtuOverheadFactory();

    @Override // bleshadow.javax.inject.Provider
    public Integer get() {
        return Integer.valueOf(ConnectionModule.gattWriteMtuOverhead());
    }

    public static ConnectionModule_GattWriteMtuOverheadFactory create() {
        return INSTANCE;
    }

    public static int proxyGattWriteMtuOverhead() {
        return ConnectionModule.gattWriteMtuOverhead();
    }
}
