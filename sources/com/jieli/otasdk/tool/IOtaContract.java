package com.jieli.otasdk.tool;

import android.bluetooth.BluetoothDevice;
import com.jieli.otasdk.base.BasePresenter;
import com.jieli.otasdk.base.BaseView;
import kotlin.Metadata;
/* compiled from: IOtaContract.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0002\u0002\u0003¨\u0006\u0004"}, d2 = {"Lcom/jieli/otasdk/tool/IOtaContract;", "", "IOtaPresenter", "IOtaView", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public interface IOtaContract {

    /* compiled from: IOtaContract.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0012\u0010\u0004\u001a\u00020\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&J\n\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\nH&J\u0012\u0010\f\u001a\u00020\u00032\b\u0010\r\u001a\u0004\u0018\u00010\u0006H&J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0006H&¨\u0006\u0010"}, d2 = {"Lcom/jieli/otasdk/tool/IOtaContract$IOtaPresenter;", "Lcom/jieli/otasdk/base/BasePresenter;", "cancelOTA", "", "connectBle", "address", "", "getConnectedDevice", "Landroid/bluetooth/BluetoothDevice;", "isDevConnected", "", "isOTA", "reconnectDev", "devAddr", "startOTA", "filePath", "otasdk_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public interface IOtaPresenter extends BasePresenter {
        void cancelOTA();

        void connectBle(String str);

        BluetoothDevice getConnectedDevice();

        boolean isDevConnected();

        boolean isOTA();

        void reconnectDev(String str);

        void startOTA(String str);
    }

    /* compiled from: IOtaContract.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\u0004H&J\b\u0010\n\u001a\u00020\u0004H&J\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH&J\u0018\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0012H&J\u0012\u0010\u0013\u001a\u00020\u00042\b\u0010\u0014\u001a\u0004\u0018\u00010\u000eH&J\b\u0010\u0015\u001a\u00020\u0004H&J\b\u0010\u0016\u001a\u00020\u0004H&¨\u0006\u0017"}, d2 = {"Lcom/jieli/otasdk/tool/IOtaContract$IOtaView;", "Lcom/jieli/otasdk/base/BaseView;", "Lcom/jieli/otasdk/tool/IOtaContract$IOtaPresenter;", "onConnection", "", "device", "Landroid/bluetooth/BluetoothDevice;", "status", "", "onMandatoryUpgrade", "onOTACancel", "onOTAError", "code", "message", "", "onOTAProgress", "type", "progress", "", "onOTAReconnect", "btAddr", "onOTAStart", "onOTAStop", "otasdk_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public interface IOtaView extends BaseView<IOtaPresenter> {
        void onConnection(BluetoothDevice bluetoothDevice, int i);

        void onMandatoryUpgrade();

        void onOTACancel();

        void onOTAError(int i, String str);

        void onOTAProgress(int i, float f);

        void onOTAReconnect(String str);

        void onOTAStart();

        void onOTAStop();
    }
}
