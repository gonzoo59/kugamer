package com.jieli.otasdk.tool;

import android.bluetooth.BluetoothDevice;
import com.jieli.otasdk.base.BasePresenter;
import com.jieli.otasdk.base.BaseView;
import kotlin.Metadata;
/* compiled from: IDeviceContract.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0002\u0002\u0003¨\u0006\u0004"}, d2 = {"Lcom/jieli/otasdk/tool/IDeviceContract;", "", "IDevScanPresenter", "IDevScanView", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public interface IDeviceContract {

    /* compiled from: IDeviceContract.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0012\u0010\u0006\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0005H&J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u0003H&J\b\u0010\u000b\u001a\u00020\u0003H&¨\u0006\f"}, d2 = {"Lcom/jieli/otasdk/tool/IDeviceContract$IDevScanPresenter;", "Lcom/jieli/otasdk/base/BasePresenter;", "connectBtDevice", "", "device", "Landroid/bluetooth/BluetoothDevice;", "disconnectBtDevice", "getConnectedDevice", "isScanning", "", "startScan", "stopScan", "otasdk_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public interface IDevScanPresenter extends BasePresenter {
        void connectBtDevice(BluetoothDevice bluetoothDevice);

        void disconnectBtDevice(BluetoothDevice bluetoothDevice);

        BluetoothDevice getConnectedDevice();

        boolean isScanning();

        void startScan();

        void stopScan();
    }

    /* compiled from: IDeviceContract.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u0004H&J\u001a\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&¨\u0006\u000f"}, d2 = {"Lcom/jieli/otasdk/tool/IDeviceContract$IDevScanView;", "Lcom/jieli/otasdk/base/BaseView;", "Lcom/jieli/otasdk/tool/IDeviceContract$IDevScanPresenter;", "onConnectStatus", "", "device", "Landroid/bluetooth/BluetoothDevice;", "status", "", "onErrorCallback", "code", "message", "", "onMandatoryUpgrade", "onScanStatus", "otasdk_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public interface IDevScanView extends BaseView<IDevScanPresenter> {
        void onConnectStatus(BluetoothDevice bluetoothDevice, int i);

        void onErrorCallback(int i, String str);

        void onMandatoryUpgrade();

        void onScanStatus(int i, BluetoothDevice bluetoothDevice);
    }
}
