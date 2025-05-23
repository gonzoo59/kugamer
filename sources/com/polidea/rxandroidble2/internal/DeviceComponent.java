package com.polidea.rxandroidble2.internal;

import bleshadow.dagger.BindsInstance;
import bleshadow.dagger.Subcomponent;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.RxBleDevice;
@DeviceScope
@Subcomponent(modules = {DeviceModule.class})
/* loaded from: classes2.dex */
public interface DeviceComponent {

    /* loaded from: classes2.dex */
    public interface Builder {
        DeviceComponent build();

        @BindsInstance
        Builder macAddress(@Named("mac-address") String str);
    }

    @DeviceScope
    RxBleDevice provideDevice();
}
