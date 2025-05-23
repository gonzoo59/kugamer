package com.jieli.otasdk.widget;

import android.bluetooth.BluetoothDevice;
import com.jieli.component.base.Jl_BaseActivity;
import com.jieli.otasdk.tool.DevScanPresenter;
import com.jieli.otasdk.tool.IDeviceContract;
import java.io.PrintStream;
/* loaded from: classes2.dex */
public class ScanView implements IDeviceContract.IDevScanView {
    private Jl_BaseActivity activity;
    private BluetoothDevice mDevice = null;
    DevScanPresenter mPresenter;
    private OnListener onListener;
    private String targetMac;

    /* loaded from: classes2.dex */
    public interface OnListener {
        void onConnectStatus(BluetoothDevice bluetoothDevice, int i);
    }

    @Override // com.jieli.otasdk.tool.IDeviceContract.IDevScanView
    public void onErrorCallback(int i, String str) {
    }

    @Override // com.jieli.otasdk.tool.IDeviceContract.IDevScanView
    public void onMandatoryUpgrade() {
    }

    @Override // com.jieli.otasdk.base.BaseView
    public void setPresenter(IDeviceContract.IDevScanPresenter iDevScanPresenter) {
    }

    public ScanView(Jl_BaseActivity jl_BaseActivity, String str) {
        this.mPresenter = null;
        this.targetMac = str;
        this.activity = jl_BaseActivity;
        DevScanPresenter devScanPresenter = new DevScanPresenter(this);
        this.mPresenter = devScanPresenter;
        devScanPresenter.start();
        this.mPresenter.startScan();
    }

    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }

    @Override // com.jieli.otasdk.tool.IDeviceContract.IDevScanView
    public void onScanStatus(int i, BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null || !bluetoothDevice.getAddress().equals(this.targetMac)) {
            return;
        }
        this.mPresenter.stopScan();
        this.mPresenter.connectBtDevice(bluetoothDevice);
    }

    @Override // com.jieli.otasdk.tool.IDeviceContract.IDevScanView
    public void onConnectStatus(BluetoothDevice bluetoothDevice, int i) {
        PrintStream printStream = System.out;
        printStream.println("status:" + i);
        if (i == 3) {
            return;
        }
        if (i != 1) {
            if (i != 4 && i == 2) {
                this.mPresenter.startScan();
                return;
            }
            return;
        }
        System.out.println(bluetoothDevice.getName());
        System.out.println(bluetoothDevice.getAddress());
        System.out.println(bluetoothDevice.getType());
        this.mDevice = bluetoothDevice;
        OnListener onListener = this.onListener;
        if (onListener != null) {
            onListener.onConnectStatus(bluetoothDevice, i);
        }
    }

    public void disconnectBtDevice() {
        BluetoothDevice bluetoothDevice = this.mDevice;
        if (bluetoothDevice != null) {
            this.mPresenter.disconnectBtDevice(bluetoothDevice);
        }
    }
}
