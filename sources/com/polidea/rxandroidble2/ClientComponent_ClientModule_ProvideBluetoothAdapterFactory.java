package com.polidea.rxandroidble2;

import android.bluetooth.BluetoothAdapter;
import bleshadow.dagger.internal.Factory;
import com.polidea.rxandroidble2.ClientComponent;
/* loaded from: classes2.dex */
public final class ClientComponent_ClientModule_ProvideBluetoothAdapterFactory implements Factory<BluetoothAdapter> {
    private static final ClientComponent_ClientModule_ProvideBluetoothAdapterFactory INSTANCE = new ClientComponent_ClientModule_ProvideBluetoothAdapterFactory();

    @Override // bleshadow.javax.inject.Provider
    public BluetoothAdapter get() {
        return ClientComponent.ClientModule.provideBluetoothAdapter();
    }

    public static ClientComponent_ClientModule_ProvideBluetoothAdapterFactory create() {
        return INSTANCE;
    }

    public static BluetoothAdapter proxyProvideBluetoothAdapter() {
        return ClientComponent.ClientModule.provideBluetoothAdapter();
    }
}
