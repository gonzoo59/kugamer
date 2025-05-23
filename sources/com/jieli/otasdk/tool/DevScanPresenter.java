package com.jieli.otasdk.tool;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.ParcelUuid;
import android.text.TextUtils;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.otasdk.tool.IDeviceContract;
import com.jieli.otasdk.tool.ota.OTAManager;
import com.jieli.otasdk.tool.ota.ble.BleManager;
import com.jieli.otasdk.tool.ota.ble.interfaces.BleEventCallback;
import com.jieli.otasdk.tool.ota.ble.model.BleScanInfo;
import com.jieli.otasdk.util.AppUtil;
import com.jieli.otasdk.util.JL_Constant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: DevScanPresenter.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010*\u0002\n\r\u0018\u0000 &2\u00020\u0001:\u0001&B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0010H\u0016J\u001a\u0010\u0014\u001a\u00020\u00152\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017J\u0012\u0010\u0018\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\n\u0010\u0019\u001a\u0004\u0018\u00010\u0012H\u0016J\u0010\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u0015H\u0002J\u001a\u0010\u001c\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u001d\u001a\u00020\bH\u0002J\u0012\u0010\u001e\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002J\u0010\u0010\u001f\u001a\u00020\u00102\u0006\u0010 \u001a\u00020\u0015H\u0002J\b\u0010!\u001a\u00020\u0015H\u0016J\b\u0010\"\u001a\u00020\u0010H\u0016J\b\u0010#\u001a\u00020\u0010H\u0016J\b\u0010$\u001a\u00020\u0010H\u0002J\b\u0010%\u001a\u00020\u0010H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u0010\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lcom/jieli/otasdk/tool/DevScanPresenter;", "Lcom/jieli/otasdk/tool/IDeviceContract$IDevScanPresenter;", "view", "Lcom/jieli/otasdk/tool/IDeviceContract$IDevScanView;", "(Lcom/jieli/otasdk/tool/IDeviceContract$IDevScanView;)V", "bleManager", "Lcom/jieli/otasdk/tool/ota/ble/BleManager;", "interval", "", "mBleEventCallback", "com/jieli/otasdk/tool/DevScanPresenter$mBleEventCallback$1", "Lcom/jieli/otasdk/tool/DevScanPresenter$mBleEventCallback$1;", "mScanSppDevice", "com/jieli/otasdk/tool/DevScanPresenter$mScanSppDevice$1", "Lcom/jieli/otasdk/tool/DevScanPresenter$mScanSppDevice$1;", "connectBtDevice", "", "device", "Landroid/bluetooth/BluetoothDevice;", "destroy", "deviceHasProfile", "", "uuid", "Ljava/util/UUID;", "disconnectBtDevice", "getConnectedDevice", "handleAdapterStatus", "bEnabled", "handleConnection", "status", "handleDiscoveryDevice", "handleDiscoveryStatus", "bStart", "isScanning", "start", "startScan", "stopEdrScan", "stopScan", "Companion", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class DevScanPresenter implements IDeviceContract.IDevScanPresenter {
    public static final Companion Companion = new Companion(null);
    public static final int MAX_INTERVAL = 12;
    private final BleManager bleManager;
    private int interval;
    private final DevScanPresenter$mBleEventCallback$1 mBleEventCallback;
    private final DevScanPresenter$mScanSppDevice$1 mScanSppDevice;
    private IDeviceContract.IDevScanView view;

    /* JADX WARN: Type inference failed for: r2v2, types: [com.jieli.otasdk.tool.DevScanPresenter$mScanSppDevice$1] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.jieli.otasdk.tool.DevScanPresenter$mBleEventCallback$1] */
    public DevScanPresenter(IDeviceContract.IDevScanView view) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.view = view;
        BleManager bleManager = BleManager.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(bleManager, "BleManager.getInstance()");
        this.bleManager = bleManager;
        this.mScanSppDevice = new Runnable() { // from class: com.jieli.otasdk.tool.DevScanPresenter$mScanSppDevice$1
            @Override // java.lang.Runnable
            public void run() {
                int i;
                int i2;
                int i3;
                IDeviceContract.IDevScanView iDevScanView;
                IDeviceContract.IDevScanView iDevScanView2;
                IDeviceContract.IDevScanView iDevScanView3;
                i = DevScanPresenter.this.interval;
                if (i == 0) {
                    iDevScanView3 = DevScanPresenter.this.view;
                    iDevScanView3.onScanStatus(1, null);
                }
                List<BluetoothDevice> systemConnectedBtDeviceList = BluetoothUtil.getSystemConnectedBtDeviceList();
                if (systemConnectedBtDeviceList != null && (!systemConnectedBtDeviceList.isEmpty())) {
                    ArrayList arrayList = new ArrayList();
                    for (BluetoothDevice bluetoothDevice : systemConnectedBtDeviceList) {
                        int type = bluetoothDevice.getType();
                        boolean deviceHasProfile = DevScanPresenter.this.deviceHasProfile(bluetoothDevice, JL_Constant.Companion.getUUID_A2DP());
                        boolean deviceHasProfile2 = DevScanPresenter.this.deviceHasProfile(bluetoothDevice, JL_Constant.Companion.getUUID_SPP());
                        if (type != 2 && deviceHasProfile && deviceHasProfile2 && !arrayList.contains(bluetoothDevice)) {
                            arrayList.add(bluetoothDevice);
                            iDevScanView2 = DevScanPresenter.this.view;
                            iDevScanView2.onScanStatus(2, bluetoothDevice);
                        }
                    }
                }
                i2 = DevScanPresenter.this.interval;
                if (i2 >= 12) {
                    DevScanPresenter.this.interval = 0;
                    iDevScanView = DevScanPresenter.this.view;
                    iDevScanView.onScanStatus(0, null);
                    return;
                }
                DevScanPresenter devScanPresenter = DevScanPresenter.this;
                i3 = devScanPresenter.interval;
                devScanPresenter.interval = i3 + 1;
                Handler mainHandler = CommonUtil.getMainHandler();
                if (mainHandler != null) {
                    mainHandler.postDelayed(this, 1000L);
                }
            }
        };
        this.mBleEventCallback = new BleEventCallback() { // from class: com.jieli.otasdk.tool.DevScanPresenter$mBleEventCallback$1
            @Override // com.jieli.otasdk.tool.ota.ble.interfaces.BleEventCallback, com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
            public void onAdapterChange(boolean z) {
                DevScanPresenter.this.handleAdapterStatus(z);
            }

            @Override // com.jieli.otasdk.tool.ota.ble.interfaces.BleEventCallback, com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
            public void onDiscoveryBleChange(boolean z) {
                DevScanPresenter.this.handleDiscoveryStatus(z);
            }

            @Override // com.jieli.otasdk.tool.ota.ble.interfaces.BleEventCallback, com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
            public void onDiscoveryBle(BluetoothDevice bluetoothDevice, BleScanInfo bleScanInfo) {
                DevScanPresenter.this.handleDiscoveryDevice(bluetoothDevice);
            }

            @Override // com.jieli.otasdk.tool.ota.ble.interfaces.BleEventCallback, com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
            public void onBleConnection(BluetoothDevice bluetoothDevice, int i) {
                DevScanPresenter.this.handleConnection(bluetoothDevice, OTAManager.Companion.changeConnectStatus(i));
            }
        };
    }

    /* compiled from: DevScanPresenter.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/jieli/otasdk/tool/DevScanPresenter$Companion;", "", "()V", "MAX_INTERVAL", "", "otasdk_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // com.jieli.otasdk.base.BasePresenter
    public void start() {
        this.bleManager.registerBleEventCallback(this.mBleEventCallback);
    }

    @Override // com.jieli.otasdk.base.BasePresenter
    public void destroy() {
        this.bleManager.unregisterBleEventCallback(this.mBleEventCallback);
    }

    @Override // com.jieli.otasdk.tool.IDeviceContract.IDevScanPresenter
    public boolean isScanning() {
        return this.bleManager.isBleScanning();
    }

    @Override // com.jieli.otasdk.tool.IDeviceContract.IDevScanPresenter
    public void startScan() {
        if (BluetoothUtil.isBluetoothEnable()) {
            this.bleManager.startLeScan(12000L);
        } else {
            AppUtil.enableBluetooth();
        }
    }

    @Override // com.jieli.otasdk.tool.IDeviceContract.IDevScanPresenter
    public void stopScan() {
        this.bleManager.stopLeScan();
    }

    @Override // com.jieli.otasdk.tool.IDeviceContract.IDevScanPresenter
    public BluetoothDevice getConnectedDevice() {
        return this.bleManager.getConnectedBtDevice();
    }

    @Override // com.jieli.otasdk.tool.IDeviceContract.IDevScanPresenter
    public void connectBtDevice(BluetoothDevice device) {
        Intrinsics.checkParameterIsNotNull(device, "device");
        if (this.bleManager.getConnectedBtDevice() != null) {
            BleManager bleManager = this.bleManager;
            bleManager.disconnectBleDevice(bleManager.getConnectedBtDevice());
            return;
        }
        this.bleManager.connectBleDevice(device);
    }

    @Override // com.jieli.otasdk.tool.IDeviceContract.IDevScanPresenter
    public void disconnectBtDevice(BluetoothDevice bluetoothDevice) {
        this.bleManager.disconnectBleDevice(bluetoothDevice);
    }

    private final void stopEdrScan() {
        if (this.interval > 0) {
            this.interval = 0;
            this.view.onScanStatus(0, null);
            Handler mainHandler = CommonUtil.getMainHandler();
            if (mainHandler != null) {
                mainHandler.removeCallbacks(this.mScanSppDevice);
            }
        }
    }

    public final boolean deviceHasProfile(BluetoothDevice bluetoothDevice, UUID uuid) {
        ParcelUuid[] uuids;
        if (!BluetoothUtil.isBluetoothEnable() || bluetoothDevice == null || TextUtils.isEmpty(String.valueOf(uuid)) || (uuids = bluetoothDevice.getUuids()) == null) {
            return false;
        }
        for (ParcelUuid parcelUuid : uuids) {
            String valueOf = String.valueOf(uuid);
            Locale locale = Locale.getDefault();
            Intrinsics.checkExpressionValueIsNotNull(locale, "Locale.getDefault()");
            if (valueOf == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = valueOf.toLowerCase(locale);
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
            if (Intrinsics.areEqual(lowerCase, parcelUuid.toString())) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleAdapterStatus(boolean z) {
        if (z) {
            startScan();
            return;
        }
        stopEdrScan();
        this.view.onScanStatus(0, null);
        this.view.onConnectStatus(getConnectedDevice(), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleDiscoveryStatus(boolean z) {
        this.view.onScanStatus(z ? 1 : 0, null);
        if (!z || getConnectedDevice() == null) {
            return;
        }
        this.view.onScanStatus(2, getConnectedDevice());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleDiscoveryDevice(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null || !BluetoothUtil.isBluetoothEnable()) {
            return;
        }
        this.view.onScanStatus(2, bluetoothDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleConnection(BluetoothDevice bluetoothDevice, int i) {
        this.view.onConnectStatus(bluetoothDevice, i);
    }
}
