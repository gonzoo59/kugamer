package com.jieli.otasdk.tool.ota;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import com.jieli.jl_bt_ota.impl.BluetoothOTAManager;
import com.jieli.jl_bt_ota.model.BluetoothOTAConfigure;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.tool.DeviceReConnectManager;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.JL_Log;
import com.jieli.otasdk.tool.ota.ble.BleManager;
import com.jieli.otasdk.tool.ota.ble.interfaces.BleEventCallback;
import com.jieli.otasdk.tool.ota.ble.interfaces.OnWriteDataCallback;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: OTAManager.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000Q\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002*\u0001\u0006\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\u0012\u0010\u000e\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\u0010\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\n\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\n\u0010\u0014\u001a\u0004\u0018\u00010\rH\u0016J\b\u0010\u0015\u001a\u00020\u000bH\u0016J\u001c\u0010\u0016\u001a\u00020\u00172\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u000b2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cR\u0010\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/jieli/otasdk/tool/ota/OTAManager;", "Lcom/jieli/jl_bt_ota/impl/BluetoothOTAManager;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "bleEventCallback", "com/jieli/otasdk/tool/ota/OTAManager$bleEventCallback$1", "Lcom/jieli/otasdk/tool/ota/OTAManager$bleEventCallback$1;", "bleManager", "Lcom/jieli/otasdk/tool/ota/ble/BleManager;", "connectBluetoothDevice", "", "bluetoothDevice", "Landroid/bluetooth/BluetoothDevice;", "disconnectBluetoothDevice", "errorEventCallback", "baseError", "Lcom/jieli/jl_bt_ota/model/base/BaseError;", "getConnectedBluetoothGatt", "Landroid/bluetooth/BluetoothGatt;", "getConnectedDevice", "release", "sendDataToDevice", "", "bytes", "", "setReconnectAddr", "addr", "", "Companion", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class OTAManager extends BluetoothOTAManager {
    public static final Companion Companion = new Companion(null);
    private final OTAManager$bleEventCallback$1 bleEventCallback;
    private final BleManager bleManager;

    @Override // com.jieli.jl_bt_ota.impl.BluetoothOTAManager, com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void errorEventCallback(BaseError baseError) {
        Intrinsics.checkParameterIsNotNull(baseError, "baseError");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.jieli.otasdk.tool.ota.OTAManager$bleEventCallback$1] */
    public OTAManager(Context context) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        BleManager bleManager = BleManager.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(bleManager, "BleManager.getInstance()");
        this.bleManager = bleManager;
        ?? r0 = new BleEventCallback() { // from class: com.jieli.otasdk.tool.ota.OTAManager$bleEventCallback$1
            @Override // com.jieli.otasdk.tool.ota.ble.interfaces.BleEventCallback, com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
            public void onBleConnection(BluetoothDevice bluetoothDevice, int i) {
                String str;
                super.onBleConnection(bluetoothDevice, i);
                str = OTAManager.this.TAG;
                JL_Log.i(str, "onBleConnection >>> device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice) + ", status ：" + i + ", change status : " + OTAManager.Companion.changeConnectStatus(i));
                OTAManager.this.onBtDeviceConnection(bluetoothDevice, OTAManager.Companion.changeConnectStatus(i));
            }

            @Override // com.jieli.otasdk.tool.ota.ble.interfaces.BleEventCallback, com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
            public void onBleDataNotification(BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, byte[] bArr) {
                String str;
                super.onBleDataNotification(bluetoothDevice, uuid, uuid2, bArr);
                str = OTAManager.this.TAG;
                JL_Log.i(str, "onBleDataNotification >>> " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice) + ", data ：" + CHexConver.byte2HexStr(bArr) + ' ');
                OTAManager.this.onReceiveDeviceData(bluetoothDevice, bArr);
            }

            @Override // com.jieli.otasdk.tool.ota.ble.interfaces.BleEventCallback, com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
            public void onBleDataBlockChanged(BluetoothDevice bluetoothDevice, int i, int i2) {
                BleManager bleManager2;
                super.onBleDataBlockChanged(bluetoothDevice, i, i2);
                OTAManager oTAManager = OTAManager.this;
                bleManager2 = oTAManager.bleManager;
                oTAManager.onMtuChanged(bleManager2.getConnectedBtGatt(), i, i2);
            }
        };
        this.bleEventCallback = r0;
        BluetoothOTAConfigure bluetoothOTAConfigure = new BluetoothOTAConfigure();
        bluetoothOTAConfigure.setPriority(0);
        bluetoothOTAConfigure.setUseReconnect(false);
        bluetoothOTAConfigure.setUseAuthDevice(true);
        bluetoothOTAConfigure.setMtu(20);
        bluetoothOTAConfigure.setNeedChangeMtu(false);
        bluetoothOTAConfigure.setUseJLServer(false);
        configure(bluetoothOTAConfigure);
        bleManager.registerBleEventCallback((BleEventCallback) r0);
        if (bleManager.getConnectedBtDevice() != null) {
            onBtDeviceConnection(bleManager.getConnectedBtDevice(), 1);
            onMtuChanged(bleManager.getConnectedBtGatt(), bleManager.getBleMtu() + 3, 0);
        }
    }

    /* compiled from: OTAManager.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004¨\u0006\u0006"}, d2 = {"Lcom/jieli/otasdk/tool/ota/OTAManager$Companion;", "", "()V", "changeConnectStatus", "", "status", "otasdk_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public static final class Companion {
        public final int changeConnectStatus(int i) {
            if (i != 0) {
                if (i == 1) {
                    return 3;
                }
                if (i == 2) {
                    return 1;
                }
            }
            return 0;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public BluetoothDevice getConnectedDevice() {
        return this.bleManager.getConnectedBtDevice();
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public BluetoothGatt getConnectedBluetoothGatt() {
        return this.bleManager.getConnectedBtGatt();
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void connectBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bleManager.connectBleDevice(bluetoothDevice);
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void disconnectBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bleManager.disconnectBleDevice(bluetoothDevice);
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public boolean sendDataToDevice(BluetoothDevice bluetoothDevice, byte[] bArr) {
        if (bluetoothDevice == null || bArr == null) {
            return false;
        }
        this.bleManager.writeDataByBleAsync(bluetoothDevice, BleManager.BLE_UUID_SERVICE, BleManager.BLE_UUID_WRITE, bArr, new OnWriteDataCallback() { // from class: com.jieli.otasdk.tool.ota.OTAManager$sendDataToDevice$1
            @Override // com.jieli.otasdk.tool.ota.ble.interfaces.OnWriteDataCallback
            public final void onBleResult(BluetoothDevice bluetoothDevice2, UUID uuid, UUID uuid2, boolean z, byte[] bArr2) {
                String str;
                str = OTAManager.this.TAG;
                JL_Log.i(str, "-writeDataByBleAsync- result = " + z + ", device:" + BluetoothUtil.printBtDeviceInfo(bluetoothDevice2) + ", data:[" + CHexConver.byte2HexStr(bArr2) + ']');
            }
        });
        return true;
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothOTAManager, com.jieli.jl_bt_ota.impl.BluetoothBreProfiles, com.jieli.jl_bt_ota.impl.BluetoothPair, com.jieli.jl_bt_ota.impl.BluetoothDiscovery, com.jieli.jl_bt_ota.impl.BluetoothBase, com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void release() {
        super.release();
        this.bleManager.unregisterBleEventCallback(this.bleEventCallback);
    }

    public final void setReconnectAddr(String str) {
        if (BluetoothAdapter.checkBluetoothAddress(str)) {
            DeviceReConnectManager deviceReConnectManager = DeviceReConnectManager.getInstance(this);
            Intrinsics.checkExpressionValueIsNotNull(deviceReConnectManager, "DeviceReConnectManager.getInstance(this)");
            deviceReConnectManager.setReconnectAddress(str);
        }
    }
}
