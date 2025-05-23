package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
/* loaded from: classes2.dex */
public final class ConnectionModule_MinimumMtuFactory implements Factory<Integer> {
    private static final ConnectionModule_MinimumMtuFactory INSTANCE = new ConnectionModule_MinimumMtuFactory();

    @Override // bleshadow.javax.inject.Provider
    public Integer get() {
        return Integer.valueOf(ConnectionModule.minimumMtu());
    }

    public static ConnectionModule_MinimumMtuFactory create() {
        return INSTANCE;
    }

    public static int proxyMinimumMtu() {
        return ConnectionModule.minimumMtu();
    }
}
