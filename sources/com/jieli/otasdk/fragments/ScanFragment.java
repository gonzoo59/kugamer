package com.jieli.otasdk.fragments;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jieli.component.base.Jl_BaseActivity;
import com.jieli.component.utils.ValueUtil;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_dialog.Jl_Dialog;
import com.jieli.otasdk.R;
import com.jieli.otasdk.activities.MainActivity;
import com.jieli.otasdk.tool.DevScanPresenter;
import com.jieli.otasdk.tool.IDeviceContract;
import com.jieli.otasdk.widget.SpecialDecoration;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
/* compiled from: ScanFragment.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u001aH\u0002J\u0012\u0010\u001c\u001a\u00020\u001a2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020!H\u0016J\u001a\u0010\"\u001a\u00020\u001a2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&H\u0016J&\u0010'\u001a\u0004\u0018\u00010(2\u0006\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010,2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010-\u001a\u00020\u001aH\u0016J\u0018\u0010.\u001a\u00020\u001a2\u0006\u0010/\u001a\u00020&2\u0006\u00100\u001a\u000201H\u0016J\b\u00102\u001a\u00020\u001aH\u0016J\u001a\u00103\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020&2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\b\u00104\u001a\u00020\u001aH\u0016J\u001a\u00105\u001a\u00020\u001a2\u0006\u00106\u001a\u00020(2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u0010\u00107\u001a\u00020\u001a2\u0006\u00108\u001a\u000209H\u0016J\u0010\u0010:\u001a\u00020\u001a2\u0006\u0010;\u001a\u00020\tH\u0016J\b\u0010<\u001a\u00020\u001aH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006="}, d2 = {"Lcom/jieli/otasdk/fragments/ScanFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/jieli/otasdk/tool/IDeviceContract$IDevScanView;", "()V", "activity", "Lcom/jieli/component/base/Jl_BaseActivity;", "adapter", "Lcom/jieli/otasdk/fragments/ScanDeviceAdapter;", "isRefreshing", "", "()Z", "setRefreshing", "(Z)V", "mHandler", "Landroid/os/Handler;", "getMHandler", "()Landroid/os/Handler;", "mNotifyDialog", "Lcom/jieli/jl_dialog/Jl_Dialog;", "mPresenter", "Lcom/jieli/otasdk/tool/DevScanPresenter;", "getMPresenter", "()Lcom/jieli/otasdk/tool/DevScanPresenter;", "setMPresenter", "(Lcom/jieli/otasdk/tool/DevScanPresenter;)V", "dismissConnectionDialog", "", "init", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onAttach", "context", "Landroid/content/Context;", "onConnectStatus", "device", "Landroid/bluetooth/BluetoothDevice;", "status", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroyView", "onErrorCallback", "code", "message", "", "onMandatoryUpgrade", "onScanStatus", "onStart", "onViewCreated", "view", "setPresenter", "presenter", "Lcom/jieli/otasdk/tool/IDeviceContract$IDevScanPresenter;", "setUserVisibleHint", "isVisibleToUser", "showConnectionDialog", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class ScanFragment extends Fragment implements IDeviceContract.IDevScanView {
    private HashMap _$_findViewCache;
    private Jl_BaseActivity activity;
    private ScanDeviceAdapter adapter;
    private boolean isRefreshing;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private Jl_Dialog mNotifyDialog;
    private DevScanPresenter mPresenter;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.jieli.otasdk.tool.IDeviceContract.IDevScanView
    public void onMandatoryUpgrade() {
    }

    public final DevScanPresenter getMPresenter() {
        return this.mPresenter;
    }

    public final void setMPresenter(DevScanPresenter devScanPresenter) {
        this.mPresenter = devScanPresenter;
    }

    public final Handler getMHandler() {
        return this.mHandler;
    }

    public final boolean isRefreshing() {
        return this.isRefreshing;
    }

    public final void setRefreshing(boolean z) {
        this.isRefreshing = z;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        return inflater.inflate(R.layout.fragment_scan, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        super.onAttach(context);
        if (this.activity == null && (context instanceof Jl_BaseActivity)) {
            this.activity = (Jl_BaseActivity) context;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        super.onViewCreated(view, bundle);
        if (this.activity == null && (getActivity() instanceof MainActivity)) {
            FragmentActivity activity = getActivity();
            if (activity == null) {
                throw new TypeCastException("null cannot be cast to non-null type com.jieli.otasdk.activities.MainActivity");
            }
            this.activity = (MainActivity) activity;
        }
        this.mPresenter = new DevScanPresenter(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        init();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        DevScanPresenter devScanPresenter = this.mPresenter;
        if (devScanPresenter != null) {
            devScanPresenter.start();
        }
    }

    private final void init() {
        this.adapter = new ScanDeviceAdapter(new ArrayList());
        RecyclerView rc_device_list = (RecyclerView) _$_findCachedViewById(R.id.rc_device_list);
        Intrinsics.checkExpressionValueIsNotNull(rc_device_list, "rc_device_list");
        rc_device_list.setAdapter(this.adapter);
        RecyclerView rc_device_list2 = (RecyclerView) _$_findCachedViewById(R.id.rc_device_list);
        Intrinsics.checkExpressionValueIsNotNull(rc_device_list2, "rc_device_list");
        rc_device_list2.setLayoutManager(new LinearLayoutManager(this.activity));
        int color = getResources().getColor(R.color.rc_decoration);
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R.id.rc_device_list);
        Jl_BaseActivity jl_BaseActivity = this.activity;
        Jl_BaseActivity jl_BaseActivity2 = jl_BaseActivity;
        if (jl_BaseActivity == null) {
            Intrinsics.throwNpe();
        }
        recyclerView.addItemDecoration(new SpecialDecoration(jl_BaseActivity2, 1, color, ValueUtil.dp2px(jl_BaseActivity, 1)));
        ScanDeviceAdapter scanDeviceAdapter = this.adapter;
        if (scanDeviceAdapter != null) {
            scanDeviceAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.jieli.otasdk.fragments.ScanFragment$init$$inlined$let$lambda$1
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<?, ?> a, View view, int i) {
                    ScanDeviceAdapter scanDeviceAdapter2;
                    Intrinsics.checkParameterIsNotNull(a, "a");
                    Intrinsics.checkParameterIsNotNull(view, "view");
                    DevScanPresenter mPresenter = ScanFragment.this.getMPresenter();
                    BluetoothDevice connectedDevice = mPresenter != null ? mPresenter.getConnectedDevice() : null;
                    scanDeviceAdapter2 = ScanFragment.this.adapter;
                    BluetoothDevice item = scanDeviceAdapter2 != null ? scanDeviceAdapter2.getItem(i) : null;
                    if (connectedDevice != null && BluetoothUtil.deviceEquals(connectedDevice, item)) {
                        DevScanPresenter mPresenter2 = ScanFragment.this.getMPresenter();
                        if (mPresenter2 != null) {
                            mPresenter2.disconnectBtDevice(item);
                            return;
                        }
                        return;
                    }
                    DevScanPresenter mPresenter3 = ScanFragment.this.getMPresenter();
                    if (mPresenter3 != null) {
                        mPresenter3.stopScan();
                    }
                    DevScanPresenter mPresenter4 = ScanFragment.this.getMPresenter();
                    if (mPresenter4 != null) {
                        if (item == null) {
                            Intrinsics.throwNpe();
                        }
                        mPresenter4.connectBtDevice(item);
                    }
                }
            });
        }
        ((EasyRefreshLayout) _$_findCachedViewById(R.id.easy_refresh)).addEasyEvent(new ScanFragment$init$2(this));
        EasyRefreshLayout easy_refresh = (EasyRefreshLayout) _$_findCachedViewById(R.id.easy_refresh);
        Intrinsics.checkExpressionValueIsNotNull(easy_refresh, "easy_refresh");
        easy_refresh.setLoadMoreModel(LoadModel.NONE);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        DevScanPresenter devScanPresenter = this.mPresenter;
        if (devScanPresenter != null) {
            devScanPresenter.destroy();
        }
        _$_clearFindViewByIdCache();
    }

    @Override // com.jieli.otasdk.base.BaseView
    public void setPresenter(IDeviceContract.IDevScanPresenter presenter) {
        Intrinsics.checkParameterIsNotNull(presenter, "presenter");
        if (this.mPresenter != null) {
        }
    }

    @Override // com.jieli.otasdk.tool.IDeviceContract.IDevScanView
    public void onScanStatus(int i, BluetoothDevice bluetoothDevice) {
        ScanDeviceAdapter scanDeviceAdapter;
        BluetoothDevice connectedDevice;
        if (isDetached() || !isAdded()) {
            return;
        }
        if (i == 2) {
            EditText ed_scan_ble_filter = (EditText) _$_findCachedViewById(R.id.ed_scan_ble_filter);
            Intrinsics.checkExpressionValueIsNotNull(ed_scan_ble_filter, "ed_scan_ble_filter");
            String obj = ed_scan_ble_filter.getText().toString();
            if (TextUtils.isEmpty(bluetoothDevice != null ? bluetoothDevice.getName() : null)) {
                return;
            }
            String str = obj;
            if (!TextUtils.isEmpty(str)) {
                String name = bluetoothDevice != null ? bluetoothDevice.getName() : null;
                if (name == null) {
                    Intrinsics.throwNpe();
                }
                if (!StringsKt.contains$default((CharSequence) name, (CharSequence) str, false, 2, (Object) null)) {
                    return;
                }
            }
            ScanDeviceAdapter scanDeviceAdapter2 = this.adapter;
            if (scanDeviceAdapter2 != null) {
                scanDeviceAdapter2.addDevice(bluetoothDevice);
                return;
            }
            return;
        }
        boolean z = i == 1;
        if (z && (scanDeviceAdapter = this.adapter) != null) {
            scanDeviceAdapter.getData().clear();
            DevScanPresenter devScanPresenter = this.mPresenter;
            if (devScanPresenter != null && (connectedDevice = devScanPresenter.getConnectedDevice()) != null) {
                scanDeviceAdapter.getData().add(connectedDevice);
            }
            scanDeviceAdapter.notifyDataSetChanged();
        }
        TextView textView = (TextView) _$_findCachedViewById(R.id.tv_scan_tip);
        if (textView != null) {
            textView.setText(getString(z ? R.string.scaning_tip : R.string.scan_tip));
        }
    }

    @Override // com.jieli.otasdk.tool.IDeviceContract.IDevScanView
    public void onConnectStatus(BluetoothDevice bluetoothDevice, int i) {
        ScanDeviceAdapter scanDeviceAdapter;
        if (i == 3) {
            showConnectionDialog();
            return;
        }
        if (i == 4 && bluetoothDevice != null && (scanDeviceAdapter = this.adapter) != null) {
            scanDeviceAdapter.addDevice(bluetoothDevice);
        }
        dismissConnectionDialog();
        DevScanPresenter devScanPresenter = this.mPresenter;
        if (devScanPresenter != null) {
            devScanPresenter.startScan();
        }
    }

    @Override // com.jieli.otasdk.tool.IDeviceContract.IDevScanView
    public void onErrorCallback(int i, String message) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        if (i == 10596) {
            TextView tv_scan_tip = (TextView) _$_findCachedViewById(R.id.tv_scan_tip);
            Intrinsics.checkExpressionValueIsNotNull(tv_scan_tip, "tv_scan_tip");
            tv_scan_tip.setText(getString(R.string.bt_bluetooth_close));
        }
    }

    private final void showConnectionDialog() {
        if (this.mNotifyDialog == null) {
            this.mNotifyDialog = Jl_Dialog.builder().title(getString(R.string.tips)).content(getString(R.string.bt_connecting)).showProgressBar(true).width(0.8f).cancel(false).build();
        }
    }

    private final void dismissConnectionDialog() {
        Jl_Dialog jl_Dialog = this.mNotifyDialog;
        if (jl_Dialog == null || !jl_Dialog.isShow()) {
            return;
        }
        jl_Dialog.dismiss();
    }

    @Override // androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean z) {
        if (z) {
            DevScanPresenter devScanPresenter = this.mPresenter;
            if (devScanPresenter != null) {
                devScanPresenter.startScan();
                return;
            }
            return;
        }
        DevScanPresenter devScanPresenter2 = this.mPresenter;
        if (devScanPresenter2 != null) {
            devScanPresenter2.stopScan();
        }
    }
}
