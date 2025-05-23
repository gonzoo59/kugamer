package com.jieli.otasdk.tool;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import com.jieli.component.Logcat;
import com.jieli.jl_bt_ota.interfaces.BtEventCallback;
import com.jieli.jl_bt_ota.interfaces.IBluetoothCallback;
import com.jieli.jl_bt_ota.interfaces.IUpgradeCallback;
import com.jieli.jl_bt_ota.model.BluetoothOTAConfigure;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.tool.DeviceStatusManager;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.JL_Log;
import com.jieli.otasdk.tool.IOtaContract;
import com.jieli.otasdk.tool.ota.OTAManager;
import com.jieli.otasdk.tool.ota.ble.BleManager;
import com.jieli.otasdk.tool.ota.ble.interfaces.BleEventCallback;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
/* compiled from: OtaPresenter.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000Q\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007*\u0003\n\r\u0010\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0012\u0010\u0018\u001a\u00020\u00172\b\u0010\u0019\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\u001a\u001a\u00020\u0017H\u0016J\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u001eH\u0016J\u0012\u0010 \u001a\u00020\u00172\b\u0010!\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\"\u001a\u00020\u0017H\u0016J\u0010\u0010#\u001a\u00020\u00172\u0006\u0010$\u001a\u00020\u0015H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u0010\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000eR\u0010\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0011R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/jieli/otasdk/tool/OtaPresenter;", "Lcom/jieli/otasdk/tool/IOtaContract$IOtaPresenter;", "view", "Lcom/jieli/otasdk/tool/IOtaContract$IOtaView;", "context", "Landroid/content/Context;", "(Lcom/jieli/otasdk/tool/IOtaContract$IOtaView;Landroid/content/Context;)V", "bleManager", "Lcom/jieli/otasdk/tool/ota/ble/BleManager;", "mBleEventCallback", "com/jieli/otasdk/tool/OtaPresenter$mBleEventCallback$1", "Lcom/jieli/otasdk/tool/OtaPresenter$mBleEventCallback$1;", "mOTABtEventCallback", "com/jieli/otasdk/tool/OtaPresenter$mOTABtEventCallback$1", "Lcom/jieli/otasdk/tool/OtaPresenter$mOTABtEventCallback$1;", "mOTAManagerCallback", "com/jieli/otasdk/tool/OtaPresenter$mOTAManagerCallback$1", "Lcom/jieli/otasdk/tool/OtaPresenter$mOTAManagerCallback$1;", "otaManager", "Lcom/jieli/otasdk/tool/ota/OTAManager;", "tag", "", "cancelOTA", "", "connectBle", "address", "destroy", "getConnectedDevice", "Landroid/bluetooth/BluetoothDevice;", "isDevConnected", "", "isOTA", "reconnectDev", "devAddr", "start", "startOTA", "filePath", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class OtaPresenter implements IOtaContract.IOtaPresenter {
    private final BleManager bleManager;
    private final OtaPresenter$mBleEventCallback$1 mBleEventCallback;
    private final OtaPresenter$mOTABtEventCallback$1 mOTABtEventCallback;
    private final OtaPresenter$mOTAManagerCallback$1 mOTAManagerCallback;
    private OTAManager otaManager;
    private final String tag;
    private IOtaContract.IOtaView view;

    /* JADX WARN: Type inference failed for: r2v3, types: [com.jieli.otasdk.tool.OtaPresenter$mOTABtEventCallback$1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.jieli.otasdk.tool.OtaPresenter$mOTAManagerCallback$1] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.jieli.otasdk.tool.OtaPresenter$mBleEventCallback$1] */
    public OtaPresenter(IOtaContract.IOtaView view, Context context) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.view = view;
        BleManager bleManager = BleManager.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(bleManager, "BleManager.getInstance()");
        this.bleManager = bleManager;
        this.tag = "zzc_ota";
        ?? r2 = new BtEventCallback() { // from class: com.jieli.otasdk.tool.OtaPresenter$mOTABtEventCallback$1
            @Override // com.jieli.jl_bt_ota.interfaces.BtEventCallback, com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
            public void onConnection(BluetoothDevice bluetoothDevice, int i) {
                IOtaContract.IOtaView iOtaView;
                iOtaView = OtaPresenter.this.view;
                iOtaView.onConnection(bluetoothDevice, i);
            }
        };
        this.mOTABtEventCallback = r2;
        OTAManager oTAManager = new OTAManager(context);
        this.otaManager = oTAManager;
        oTAManager.registerBluetoothCallback((IBluetoothCallback) r2);
        this.mOTAManagerCallback = new IUpgradeCallback() { // from class: com.jieli.otasdk.tool.OtaPresenter$mOTAManagerCallback$1
            @Override // com.jieli.jl_bt_ota.interfaces.IUpgradeCallback
            public void onError(BaseError baseError) {
                IOtaContract.IOtaView iOtaView;
                if (baseError != null) {
                    iOtaView = OtaPresenter.this.view;
                    int subCode = baseError.getSubCode();
                    String message = baseError.getMessage();
                    Intrinsics.checkExpressionValueIsNotNull(message, "p0.message");
                    iOtaView.onOTAError(subCode, message);
                }
            }

            @Override // com.jieli.jl_bt_ota.interfaces.IUpgradeCallback
            public void onNeedReconnect(String str) {
                String str2;
                IOtaContract.IOtaView iOtaView;
                str2 = OtaPresenter.this.tag;
                Logcat.e(str2, "onNeedReconnect : " + str);
                iOtaView = OtaPresenter.this.view;
                iOtaView.onOTAReconnect(str);
            }

            @Override // com.jieli.jl_bt_ota.interfaces.IUpgradeCallback
            public void onStopOTA() {
                IOtaContract.IOtaView iOtaView;
                iOtaView = OtaPresenter.this.view;
                iOtaView.onOTAStop();
            }

            @Override // com.jieli.jl_bt_ota.interfaces.IUpgradeCallback
            public void onProgress(int i, float f) {
                IOtaContract.IOtaView iOtaView;
                iOtaView = OtaPresenter.this.view;
                iOtaView.onOTAProgress(i, f);
            }

            @Override // com.jieli.jl_bt_ota.interfaces.IUpgradeCallback
            public void onStartOTA() {
                IOtaContract.IOtaView iOtaView;
                iOtaView = OtaPresenter.this.view;
                iOtaView.onOTAStart();
            }

            @Override // com.jieli.jl_bt_ota.interfaces.IUpgradeCallback
            public void onCancelOTA() {
                IOtaContract.IOtaView iOtaView;
                iOtaView = OtaPresenter.this.view;
                iOtaView.onOTACancel();
            }
        };
        this.mBleEventCallback = new BleEventCallback() { // from class: com.jieli.otasdk.tool.OtaPresenter$mBleEventCallback$1
            @Override // com.jieli.otasdk.tool.ota.ble.interfaces.BleEventCallback, com.jieli.otasdk.tool.ota.ble.interfaces.IBleEventCallback
            public void onBleConnection(BluetoothDevice bluetoothDevice, int i) {
                String str;
                str = OtaPresenter.this.tag;
                JL_Log.w(str, "onBleConnection : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice) + ", status:" + i);
            }
        };
    }

    @Override // com.jieli.otasdk.base.BasePresenter
    public void start() {
        this.bleManager.registerBleEventCallback(this.mBleEventCallback);
    }

    @Override // com.jieli.otasdk.base.BasePresenter
    public void destroy() {
        JL_Log.w(this.tag, "================destroy=================");
        this.bleManager.unregisterBleEventCallback(this.mBleEventCallback);
        OTAManager oTAManager = this.otaManager;
        if (oTAManager != null) {
            oTAManager.release();
        }
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaPresenter
    public BluetoothDevice getConnectedDevice() {
        return this.bleManager.getConnectedBtDevice();
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaPresenter
    public boolean isDevConnected() {
        BluetoothDevice connectedDevice = getConnectedDevice();
        return (connectedDevice == null || DeviceStatusManager.getInstance().getDeviceInfo(connectedDevice) == null) ? false : true;
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaPresenter
    public boolean isOTA() {
        OTAManager oTAManager = this.otaManager;
        if (oTAManager != null) {
            if (oTAManager == null) {
                Intrinsics.throwNpe();
            }
            return oTAManager.isOTA();
        }
        return false;
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaPresenter
    public void connectBle(String str) {
        this.bleManager.connectBleDevice(BluetoothUtil.getRemoteDevice(str));
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaPresenter
    public void startOTA(String filePath) {
        Intrinsics.checkParameterIsNotNull(filePath, "filePath");
        String str = this.tag;
        JL_Log.i(str, "startOTA :: " + StringCompanionObject.INSTANCE);
        OTAManager oTAManager = this.otaManager;
        if (oTAManager != null) {
            BluetoothOTAConfigure bluetoothOption = oTAManager.getBluetoothOption();
            Intrinsics.checkExpressionValueIsNotNull(bluetoothOption, "it.bluetoothOption");
            bluetoothOption.setFirmwareFilePath(filePath);
            oTAManager.startOTA(this.mOTAManagerCallback);
        }
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaPresenter
    public void cancelOTA() {
        OTAManager oTAManager = this.otaManager;
        if (oTAManager != null) {
            oTAManager.cancelOTA();
        }
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaPresenter
    public void reconnectDev(String str) {
        JL_Log.i("zzc_ota", "change addr before : " + str);
        byte[] addressCovertToByteArray = BluetoothUtil.addressCovertToByteArray(str);
        addressCovertToByteArray[addressCovertToByteArray.length + (-1)] = CHexConver.intToByte(CHexConver.byteToInt(addressCovertToByteArray[addressCovertToByteArray.length + (-1)]) + 1);
        String hexDataCovetToAddress = BluetoothUtil.hexDataCovetToAddress(addressCovertToByteArray);
        JL_Log.i("zzc_ota", "change addr after: " + hexDataCovetToAddress);
        OTAManager oTAManager = this.otaManager;
        if (oTAManager != null) {
            oTAManager.setReconnectAddr(hexDataCovetToAddress);
        }
        this.bleManager.setReconnectDevAddr(str);
    }
}
