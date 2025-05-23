package com.jieli.jl_bt_ota.interfaces;

import com.jieli.jl_bt_ota.model.BluetoothOTAConfigure;
/* loaded from: classes2.dex */
public interface IUpgradeManager {
    void cancelOTA();

    void configure(BluetoothOTAConfigure bluetoothOTAConfigure);

    void release();

    void startOTA(IUpgradeCallback iUpgradeCallback);
}
