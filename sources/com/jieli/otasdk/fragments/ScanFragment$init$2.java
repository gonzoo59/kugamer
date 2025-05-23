package com.jieli.otasdk.fragments;

import android.bluetooth.BluetoothDevice;
import com.ajguan.library.EasyRefreshLayout;
import com.jieli.otasdk.R;
import com.jieli.otasdk.tool.DevScanPresenter;
import java.util.List;
import kotlin.Metadata;
/* compiled from: ScanFragment.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\b\u0010\u0004\u001a\u00020\u0003H\u0016¨\u0006\u0005"}, d2 = {"com/jieli/otasdk/fragments/ScanFragment$init$2", "Lcom/ajguan/library/EasyRefreshLayout$EasyEvent;", "onLoadMore", "", "onRefreshing", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class ScanFragment$init$2 implements EasyRefreshLayout.EasyEvent {
    final /* synthetic */ ScanFragment this$0;

    @Override // com.ajguan.library.EasyRefreshLayout.LoadMoreEvent
    public void onLoadMore() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ScanFragment$init$2(ScanFragment scanFragment) {
        this.this$0 = scanFragment;
    }

    @Override // com.ajguan.library.EasyRefreshLayout.OnRefreshListener
    public void onRefreshing() {
        ScanDeviceAdapter scanDeviceAdapter;
        List<BluetoothDevice> data;
        if (this.this$0.isRefreshing()) {
            return;
        }
        scanDeviceAdapter = this.this$0.adapter;
        if (scanDeviceAdapter != null && (data = scanDeviceAdapter.getData()) != null) {
            data.clear();
        }
        DevScanPresenter mPresenter = this.this$0.getMPresenter();
        if (mPresenter != null) {
            mPresenter.startScan();
        }
        this.this$0.setRefreshing(true);
        this.this$0.getMHandler().postDelayed(new Runnable() { // from class: com.jieli.otasdk.fragments.ScanFragment$init$2$onRefreshing$1
            @Override // java.lang.Runnable
            public final void run() {
                if (ScanFragment$init$2.this.this$0.isRefreshing()) {
                    ((EasyRefreshLayout) ScanFragment$init$2.this.this$0._$_findCachedViewById(R.id.easy_refresh)).refreshComplete();
                    ScanFragment$init$2.this.this$0.setRefreshing(false);
                }
            }
        }, 500L);
    }
}
