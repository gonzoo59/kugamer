package com.baidu.kwgames.example3_discovery;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.baidu.kwgames.R;
import com.polidea.rxandroidble2.RxBleDeviceServices;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class DiscoveryResultsAdapter extends RecyclerView.Adapter<ViewHolder> {
    private OnAdapterItemClickListener onAdapterItemClickListener;
    private final List<AdapterItem> data = new ArrayList();
    private final View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.baidu.kwgames.example3_discovery.DiscoveryResultsAdapter.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (DiscoveryResultsAdapter.this.onAdapterItemClickListener != null) {
                DiscoveryResultsAdapter.this.onAdapterItemClickListener.onAdapterViewClick(view);
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface OnAdapterItemClickListener {
        void onAdapterViewClick(View view);
    }

    /* loaded from: classes.dex */
    public class ViewHolder_ViewBinding implements Unbinder {
        private ViewHolder target;

        public ViewHolder_ViewBinding(ViewHolder viewHolder, View view) {
            this.target = viewHolder;
            viewHolder.line1 = (TextView) Utils.findRequiredViewAsType(view, 16908308, "field 'line1'", TextView.class);
            viewHolder.line2 = (TextView) Utils.findRequiredViewAsType(view, 16908309, "field 'line2'", TextView.class);
        }

        @Override // butterknife.Unbinder
        public void unbind() {
            ViewHolder viewHolder = this.target;
            if (viewHolder == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.target = null;
            viewHolder.line1 = null;
            viewHolder.line2 = null;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.data.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return getItem(i).type;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        int itemViewType = viewHolder.getItemViewType();
        AdapterItem item = getItem(i);
        if (itemViewType == 1) {
            viewHolder.line1.setText(String.format("Service: %s", item.description));
        } else {
            viewHolder.line1.setText(String.format("Characteristic: %s", item.description));
        }
        viewHolder.line2.setText(item.uuid.toString());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(i == 1 ? R.layout.item_discovery_service : R.layout.item_discovery_characteristic, viewGroup, false);
        inflate.setOnClickListener(this.onClickListener);
        return new ViewHolder(inflate);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setOnAdapterItemClickListener(OnAdapterItemClickListener onAdapterItemClickListener) {
        this.onAdapterItemClickListener = onAdapterItemClickListener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void swapScanResult(RxBleDeviceServices rxBleDeviceServices) {
        this.data.clear();
        for (BluetoothGattService bluetoothGattService : rxBleDeviceServices.getBluetoothGattServices()) {
            this.data.add(new AdapterItem(1, getServiceType(bluetoothGattService), bluetoothGattService.getUuid()));
            for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService.getCharacteristics()) {
                this.data.add(new AdapterItem(2, describeProperties(bluetoothGattCharacteristic), bluetoothGattCharacteristic.getUuid()));
            }
        }
        notifyDataSetChanged();
    }

    private String describeProperties(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        ArrayList arrayList = new ArrayList();
        if (isCharacteristicReadable(bluetoothGattCharacteristic)) {
            arrayList.add("Read");
        }
        if (isCharacteristicWriteable(bluetoothGattCharacteristic)) {
            arrayList.add("Write");
        }
        if (isCharacteristicNotifiable(bluetoothGattCharacteristic)) {
            arrayList.add("Notify");
        }
        return TextUtils.join(" ", arrayList);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AdapterItem getItem(int i) {
        return this.data.get(i);
    }

    private String getServiceType(BluetoothGattService bluetoothGattService) {
        return bluetoothGattService.getType() == 0 ? "primary" : "secondary";
    }

    private boolean isCharacteristicNotifiable(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return (bluetoothGattCharacteristic.getProperties() & 16) != 0;
    }

    private boolean isCharacteristicReadable(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return (bluetoothGattCharacteristic.getProperties() & 2) != 0;
    }

    private boolean isCharacteristicWriteable(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return (bluetoothGattCharacteristic.getProperties() & 12) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class AdapterItem {
        static final int CHARACTERISTIC = 2;
        static final int SERVICE = 1;
        final String description;
        final int type;
        final UUID uuid;

        AdapterItem(int i, String str, UUID uuid) {
            this.type = i;
            this.description = str;
            this.uuid = uuid;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(16908308)
        TextView line1;
        @BindView(16908309)
        TextView line2;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
