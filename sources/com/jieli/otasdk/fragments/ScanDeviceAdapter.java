package com.jieli.otasdk.fragments;

import android.bluetooth.BluetoothDevice;
import com.baidu.kwgames.GameSettingFloat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jieli.component.utils.HandlerManager;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.otasdk.R;
import com.jieli.otasdk.tool.ota.ble.BleManager;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: ScanDeviceAdapter.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0015\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0002J\u0018\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0002H\u0014J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u000b\u001a\u0004\u0018\u00010\u0002H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/jieli/otasdk/fragments/ScanDeviceAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Landroid/bluetooth/BluetoothDevice;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", GameSettingFloat.TAG_ATTR_DATA, "", "(Ljava/util/List;)V", "refreshTask", "Ljava/lang/Runnable;", "addDevice", "", "device", "convert", "holder", "item", "isConnectedDevice", "", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class ScanDeviceAdapter extends BaseQuickAdapter<BluetoothDevice, BaseViewHolder> {
    private Runnable refreshTask;

    public ScanDeviceAdapter(List<BluetoothDevice> list) {
        super(R.layout.item_device_list, list);
        this.refreshTask = new Runnable() { // from class: com.jieli.otasdk.fragments.ScanDeviceAdapter$refreshTask$1
            @Override // java.lang.Runnable
            public final void run() {
                ScanDeviceAdapter.this.notifyDataSetChanged();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder holder, BluetoothDevice item) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        Intrinsics.checkParameterIsNotNull(item, "item");
        holder.setText(R.id.tv_device_name, item.getName());
        holder.getView(R.id.iv_device_status);
        holder.setImageResource(R.id.iv_device_status, isConnectedDevice(item) ? R.drawable.ic_device_sel : R.drawable.ic_device_normal);
    }

    public final void addDevice(BluetoothDevice bluetoothDevice) {
        List<BluetoothDevice> data = getData();
        if (!CollectionsKt.contains(data, bluetoothDevice) && bluetoothDevice != null) {
            data.add(bluetoothDevice);
        }
        HandlerManager handlerManager = HandlerManager.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(handlerManager, "HandlerManager.getInstance()");
        handlerManager.getMainHandler().removeCallbacks(this.refreshTask);
        HandlerManager handlerManager2 = HandlerManager.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(handlerManager2, "HandlerManager.getInstance()");
        handlerManager2.getMainHandler().postDelayed(this.refreshTask, 300L);
    }

    private final boolean isConnectedDevice(BluetoothDevice bluetoothDevice) {
        BleManager bleManager = BleManager.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(bleManager, "BleManager.getInstance()");
        return BluetoothUtil.deviceEquals(bleManager.getConnectedBtDevice(), bluetoothDevice);
    }
}
