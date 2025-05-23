package com.baidu.kwgames;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.scan.ScanResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
/* loaded from: classes.dex */
class ScanResultsAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final Comparator<ScanResult> SORTING_COMPARATOR = new Comparator() { // from class: com.baidu.kwgames.ScanResultsAdapter$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            int compareTo;
            compareTo = ((ScanResult) obj).getBleDevice().getMacAddress().compareTo(((ScanResult) obj2).getBleDevice().getMacAddress());
            return compareTo;
        }
    };
    private OnAdapterItemClickListener onAdapterItemClickListener;
    private final List<ScanResult> data = new ArrayList();
    private final View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.baidu.kwgames.ScanResultsAdapter.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (ScanResultsAdapter.this.onAdapterItemClickListener != null) {
                ScanResultsAdapter.this.onAdapterItemClickListener.onAdapterViewClick(view);
            }
        }
    };

    /* loaded from: classes.dex */
    interface OnAdapterItemClickListener {
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

    void addScanResult(ScanResult scanResult) {
        for (int i = 0; i < this.data.size(); i++) {
            if (this.data.get(i).getBleDevice().equals(scanResult.getBleDevice())) {
                this.data.set(i, scanResult);
                notifyItemChanged(i);
                return;
            }
        }
        this.data.add(scanResult);
        Collections.sort(this.data, SORTING_COMPARATOR);
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void clearScanResults() {
        this.data.clear();
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ScanResult getItemAtPosition(int i) {
        return this.data.get(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.data.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ScanResult scanResult = this.data.get(i);
        RxBleDevice bleDevice = scanResult.getBleDevice();
        viewHolder.line1.setText(String.format(Locale.getDefault(), "%s (%s)", bleDevice.getName(), bleDevice.getMacAddress()));
        viewHolder.line2.setText(String.format(Locale.getDefault(), "RSSI: %d", Integer.valueOf(scanResult.getRssi())));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(17367053, viewGroup, false);
        inflate.setOnClickListener(this.onClickListener);
        return new ViewHolder(inflate);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setOnAdapterItemClickListener(OnAdapterItemClickListener onAdapterItemClickListener) {
        this.onAdapterItemClickListener = onAdapterItemClickListener;
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
