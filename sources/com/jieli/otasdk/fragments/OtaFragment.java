package com.jieli.otasdk.fragments;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jieli.component.ui.CommonDecoration;
import com.jieli.component.utils.FileUtil;
import com.jieli.component.utils.ToastUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import com.jieli.jl_bt_ota.util.PreferencesHelper;
import com.jieli.otasdk.MainApplication;
import com.jieli.otasdk.R;
import com.jieli.otasdk.tool.IOtaContract;
import com.jieli.otasdk.tool.OtaPresenter;
import com.jieli.otasdk.util.FileObserverCallback;
import com.jieli.otasdk.util.JL_Constant;
import com.jieli.otasdk.util.OtaFileObserverHelper;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;
/* compiled from: OtaFragment.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 @2\u00020\u00012\u00020\u0002:\u0001@B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0017\u0010\u000e\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002¢\u0006\u0002\u0010\u0011J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\u001a\u0010\u0016\u001a\u00020\u00132\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u0010H\u0016J\u0012\u0010\u001a\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J&\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010!\u001a\u00020\u0013H\u0016J\b\u0010\"\u001a\u00020\u0013H\u0016J\b\u0010#\u001a\u00020\u0013H\u0016J\b\u0010$\u001a\u00020\u0013H\u0016J\u0018\u0010%\u001a\u00020\u00132\u0006\u0010&\u001a\u00020\u00102\u0006\u0010'\u001a\u00020\rH\u0016J\u0018\u0010(\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010)\u001a\u00020*H\u0016J\u0012\u0010+\u001a\u00020\u00132\b\u0010,\u001a\u0004\u0018\u00010\rH\u0016J\b\u0010-\u001a\u00020\u0013H\u0016J\b\u0010.\u001a\u00020\u0013H\u0016J\b\u0010/\u001a\u00020\u0013H\u0016J\b\u00100\u001a\u00020\u0013H\u0016J\b\u00101\u001a\u00020\u0013H\u0002J\u0010\u00102\u001a\u00020\u00132\u0006\u00103\u001a\u000204H\u0016J\u0012\u00105\u001a\u00020\u00132\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\u0017\u00106\u001a\u00020\u00132\b\u00107\u001a\u0004\u0018\u00010\u000bH\u0002¢\u0006\u0002\u00108J\u0010\u00109\u001a\u00020\u00132\u0006\u0010:\u001a\u00020\u000bH\u0002J\u0018\u0010;\u001a\u00020\u00132\u0006\u0010)\u001a\u00020*2\u0006\u0010<\u001a\u00020\u0010H\u0002J\u0018\u0010=\u001a\u00020\u00132\u0006\u0010)\u001a\u00020*2\u0006\u0010<\u001a\u00020\u0010H\u0003J\u001a\u0010>\u001a\u00020\u00132\b\u0010?\u001a\u0004\u0018\u00010\r2\u0006\u0010<\u001a\u00020\u0010H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006A"}, d2 = {"Lcom/jieli/otasdk/fragments/OtaFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/jieli/otasdk/tool/IOtaContract$IOtaView;", "()V", "adapter", "Lcom/jieli/otasdk/fragments/FileAdapter;", "mHandler", "Landroid/os/Handler;", "otaHelper", "Lcom/jieli/otasdk/tool/OtaPresenter;", "checkFile", "", FileDownloadModel.PATH, "", "getBtDeviceTypeString", "type", "", "(Ljava/lang/Integer;)Ljava/lang/String;", "onActivityCreated", "", "savedInstanceState", "Landroid/os/Bundle;", "onConnection", "device", "Landroid/bluetooth/BluetoothDevice;", "status", "onCreate", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroy", "onDestroyView", "onMandatoryUpgrade", "onOTACancel", "onOTAError", "code", "message", "onOTAProgress", "progress", "", "onOTAReconnect", "btAddr", "onOTAStart", "onOTAStop", "onResume", "onStart", "readFileList", "setPresenter", "presenter", "Lcom/jieli/otasdk/tool/IOtaContract$IOtaPresenter;", "updateConnectedDeviceInfo", "updateDeviceConnectedStatus", "connected", "(Ljava/lang/Boolean;)V", "updateOtaUiStatus", "isUpgrade", "updateUpgradeProgressPb", "visibility", "updateUpgradeProgressTv", "updateUpgradeTips", "content", "Companion", "otasdk_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class OtaFragment extends Fragment implements IOtaContract.IOtaView {
    public static final Companion Companion = new Companion(null);
    private static final int MSG_UPDATE_OTA_FILE_LIST = 1;
    private HashMap _$_findViewCache;
    private FileAdapter adapter;
    private final Handler mHandler = new Handler(new Handler.Callback() { // from class: com.jieli.otasdk.fragments.OtaFragment$mHandler$1
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            OtaFragment.this.readFileList();
            return false;
        }
    });
    private OtaPresenter otaHelper;

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

    /* compiled from: OtaFragment.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/jieli/otasdk/fragments/OtaFragment$Companion;", "", "()V", "MSG_UPDATE_OTA_FILE_LIST", "", "otasdk_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        OtaFragment otaFragment = this;
        FragmentActivity activity = getActivity();
        if (activity == null) {
            Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(activity, "activity!!");
        Context applicationContext = activity.getApplicationContext();
        Intrinsics.checkExpressionValueIsNotNull(applicationContext, "activity!!.applicationContext");
        this.otaHelper = new OtaPresenter(otaFragment, applicationContext);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        OtaPresenter otaPresenter = this.otaHelper;
        if (otaPresenter != null) {
            otaPresenter.start();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        return inflater.inflate(R.layout.fragment_ota, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        RecyclerView rv_file_list = (RecyclerView) _$_findCachedViewById(R.id.rv_file_list);
        Intrinsics.checkExpressionValueIsNotNull(rv_file_list, "rv_file_list");
        rv_file_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((RecyclerView) _$_findCachedViewById(R.id.rv_file_list)).addItemDecoration(new CommonDecoration(getActivity(), 1));
        this.adapter = new FileAdapter(new ArrayList());
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.view_file_empty, (ViewGroup) null);
        TextView tvTips = (TextView) emptyView.findViewById(R.id.tv_file_empty_tips);
        Intrinsics.checkExpressionValueIsNotNull(tvTips, "tvTips");
        tvTips.setMovementMethod(ScrollingMovementMethod.getInstance());
        FileAdapter fileAdapter = this.adapter;
        if (fileAdapter != null) {
            Intrinsics.checkExpressionValueIsNotNull(emptyView, "emptyView");
            fileAdapter.setEmptyView(emptyView);
        }
        RecyclerView rv_file_list2 = (RecyclerView) _$_findCachedViewById(R.id.rv_file_list);
        Intrinsics.checkExpressionValueIsNotNull(rv_file_list2, "rv_file_list");
        rv_file_list2.setAdapter(this.adapter);
        FileAdapter fileAdapter2 = this.adapter;
        if (fileAdapter2 != null) {
            fileAdapter2.setOnItemClickListener(new OnItemClickListener() { // from class: com.jieli.otasdk.fragments.OtaFragment$onActivityCreated$1
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
                    FileAdapter fileAdapter3;
                    Intrinsics.checkParameterIsNotNull(baseQuickAdapter, "<anonymous parameter 0>");
                    Intrinsics.checkParameterIsNotNull(view, "<anonymous parameter 1>");
                    fileAdapter3 = OtaFragment.this.adapter;
                    if (fileAdapter3 != null) {
                        fileAdapter3.setSelectedIndex(i);
                    }
                }
            });
        }
        ((Button) _$_findCachedViewById(R.id.btn_upgrade)).setOnClickListener(new View.OnClickListener() { // from class: com.jieli.otasdk.fragments.OtaFragment$onActivityCreated$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                boolean checkFile;
                OtaPresenter otaPresenter;
                StringBuilder sb = new StringBuilder();
                File externalFilesDir = MainApplication.getInstance().getExternalFilesDir(null);
                if (externalFilesDir == null) {
                    Intrinsics.throwNpe();
                }
                sb.append(externalFilesDir.getAbsolutePath());
                sb.append("/M2 Ultimate.ufw");
                String sb2 = sb.toString();
                String tag = OtaFragment.this.getTag();
                JL_Log.w(tag, "ota file path : " + sb2);
                if (sb2 == null) {
                    return;
                }
                checkFile = OtaFragment.this.checkFile(sb2);
                if (checkFile) {
                    otaPresenter = OtaFragment.this.otaHelper;
                    if (otaPresenter != null) {
                        otaPresenter.startOTA(sb2);
                        return;
                    }
                    return;
                }
                ToastUtil.showToastShort(OtaFragment.this.getString(R.string.ota_please_chose_file));
            }
        });
        ((ImageView) _$_findCachedViewById(R.id.iv_refresh_file_list)).setOnClickListener(new View.OnClickListener() { // from class: com.jieli.otasdk.fragments.OtaFragment$onActivityCreated$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                OtaFragment.this.readFileList();
            }
        });
        OtaPresenter otaPresenter = this.otaHelper;
        updateDeviceConnectedStatus(otaPresenter != null ? Boolean.valueOf(otaPresenter.isDevConnected()) : null);
        OtaFileObserverHelper.getInstance().registerFileObserverCallback(new FileObserverCallback() { // from class: com.jieli.otasdk.fragments.OtaFragment$onActivityCreated$4
            @Override // com.jieli.otasdk.util.FileObserverCallback
            public final void onChange(int i, String str) {
                Handler handler;
                Handler handler2;
                if (str != null) {
                    handler = OtaFragment.this.mHandler;
                    handler.removeMessages(1);
                    handler2 = OtaFragment.this.mHandler;
                    handler2.sendEmptyMessageDelayed(1, 300L);
                }
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        readFileList();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        OtaPresenter otaPresenter = this.otaHelper;
        if (otaPresenter != null) {
            otaPresenter.destroy();
        }
    }

    private final void updateConnectedDeviceInfo(BluetoothDevice bluetoothDevice) {
        if (!isAdded() || isDetached()) {
            return;
        }
        if (bluetoothDevice == null) {
            TextView tv_connect_dev_name_vale = (TextView) _$_findCachedViewById(R.id.tv_connect_dev_name_vale);
            Intrinsics.checkExpressionValueIsNotNull(tv_connect_dev_name_vale, "tv_connect_dev_name_vale");
            tv_connect_dev_name_vale.setText("");
            TextView tv_connect_dev_address_vale = (TextView) _$_findCachedViewById(R.id.tv_connect_dev_address_vale);
            Intrinsics.checkExpressionValueIsNotNull(tv_connect_dev_address_vale, "tv_connect_dev_address_vale");
            tv_connect_dev_address_vale.setText("");
            TextView tv_connect_dev_type_vale = (TextView) _$_findCachedViewById(R.id.tv_connect_dev_type_vale);
            Intrinsics.checkExpressionValueIsNotNull(tv_connect_dev_type_vale, "tv_connect_dev_type_vale");
            tv_connect_dev_type_vale.setText("");
            return;
        }
        TextView tv_connect_dev_name_vale2 = (TextView) _$_findCachedViewById(R.id.tv_connect_dev_name_vale);
        Intrinsics.checkExpressionValueIsNotNull(tv_connect_dev_name_vale2, "tv_connect_dev_name_vale");
        tv_connect_dev_name_vale2.setText(bluetoothDevice.getName());
        TextView tv_connect_dev_address_vale2 = (TextView) _$_findCachedViewById(R.id.tv_connect_dev_address_vale);
        Intrinsics.checkExpressionValueIsNotNull(tv_connect_dev_address_vale2, "tv_connect_dev_address_vale");
        tv_connect_dev_address_vale2.setText(bluetoothDevice.getAddress());
        TextView tv_connect_dev_type_vale2 = (TextView) _$_findCachedViewById(R.id.tv_connect_dev_type_vale);
        Intrinsics.checkExpressionValueIsNotNull(tv_connect_dev_type_vale2, "tv_connect_dev_type_vale");
        tv_connect_dev_type_vale2.setText(getBtDeviceTypeString(Integer.valueOf(bluetoothDevice.getType())));
    }

    private final String getBtDeviceTypeString(Integer num) {
        if (num == null) {
            return "";
        }
        if (num.intValue() == 1) {
            String string = getString(R.string.device_type_classic);
            Intrinsics.checkExpressionValueIsNotNull(string, "getString(R.string.device_type_classic)");
            return string;
        } else if (num.intValue() == 2) {
            String string2 = getString(R.string.device_type_ble);
            Intrinsics.checkExpressionValueIsNotNull(string2, "getString(R.string.device_type_ble)");
            return string2;
        } else if (num.intValue() == 3) {
            String string3 = getString(R.string.device_type_dual_mode);
            Intrinsics.checkExpressionValueIsNotNull(string3, "getString(R.string.device_type_dual_mode)");
            return string3;
        } else if (num.intValue() == 0) {
            String string4 = getString(R.string.device_type_unknown);
            Intrinsics.checkExpressionValueIsNotNull(string4, "getString(R.string.device_type_unknown)");
            return string4;
        } else {
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateUpgradeTips(String str, int i) {
        TextView textView;
        if (!isAdded() || isDetached()) {
            return;
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R.id.tv_upgrade_tips);
        if (textView2 != null) {
            textView2.setVisibility(i);
        }
        if (i != 0 || str == null || (textView = (TextView) _$_findCachedViewById(R.id.tv_upgrade_tips)) == null) {
            return;
        }
        textView.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateUpgradeProgressTv(float f, int i) {
        if (!isAdded() || isDetached()) {
            return;
        }
        TextView textView = (TextView) _$_findCachedViewById(R.id.tv_upgrade_progress);
        if (textView != null) {
            textView.setVisibility(i);
        }
        if (i == 0) {
            int roundToInt = MathKt.roundToInt(f);
            TextView textView2 = (TextView) _$_findCachedViewById(R.id.tv_upgrade_progress);
            if (textView2 != null) {
                textView2.setText(roundToInt + " %");
            }
        }
    }

    private final void updateUpgradeProgressPb(float f, int i) {
        if (!isAdded() || isDetached()) {
            return;
        }
        ProgressBar progressBar = (ProgressBar) _$_findCachedViewById(R.id.bar_upgrade);
        if (progressBar != null) {
            progressBar.setVisibility(i);
        }
        if (i == 0) {
            int roundToInt = MathKt.roundToInt(f);
            ProgressBar progressBar2 = (ProgressBar) _$_findCachedViewById(R.id.bar_upgrade);
            if (progressBar2 != null) {
                progressBar2.setProgress(roundToInt);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean checkFile(String str) {
        JL_Log.i("sen", "checkFile-->" + str);
        return new File(str).exists();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void readFileList() {
        Iterator it;
        FragmentActivity activity = getActivity();
        FragmentActivity activity2 = getActivity();
        if (activity2 == null) {
            Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(activity2, "activity!!");
        File file = new File(FileUtil.splicingFilePath(activity, activity2.getPackageName(), JL_Constant.DIR_UPGRADE, null, null));
        File[] fileArr = new File[0];
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && (it = ArrayIteratorKt.iterator(listFiles)) != null) {
                while (it.hasNext()) {
                    File it2 = (File) it.next();
                    Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                    String name = it2.getName();
                    Intrinsics.checkExpressionValueIsNotNull(name, "it.name");
                    if (!StringsKt.endsWith$default(name, ".ufw", false, 2, (Object) null)) {
                        String name2 = it2.getName();
                        Intrinsics.checkExpressionValueIsNotNull(name2, "it.name");
                        if (StringsKt.endsWith$default(name2, ".bfu", false, 2, (Object) null)) {
                        }
                    }
                    fileArr = (File[]) ArraysKt.plus(fileArr, it2);
                }
            }
        } else {
            file.mkdirs();
        }
        JL_Log.w(getTag(), "readFileList ---> " + fileArr.length + ", adapter = " + this.adapter);
        FileAdapter fileAdapter = this.adapter;
        if (fileAdapter != null) {
            fileAdapter.setNewInstance(new ArrayList());
        }
        for (File file2 : fileArr) {
            FileAdapter fileAdapter2 = this.adapter;
            if (fileAdapter2 != null) {
                fileAdapter2.addData((FileAdapter) file2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateOtaUiStatus(boolean z) {
        if (z) {
            Button btn_upgrade = (Button) _$_findCachedViewById(R.id.btn_upgrade);
            Intrinsics.checkExpressionValueIsNotNull(btn_upgrade, "btn_upgrade");
            btn_upgrade.setVisibility(4);
            ProgressBar bar_upgrade = (ProgressBar) _$_findCachedViewById(R.id.bar_upgrade);
            Intrinsics.checkExpressionValueIsNotNull(bar_upgrade, "bar_upgrade");
            bar_upgrade.setVisibility(0);
            return;
        }
        Button btn_upgrade2 = (Button) _$_findCachedViewById(R.id.btn_upgrade);
        Intrinsics.checkExpressionValueIsNotNull(btn_upgrade2, "btn_upgrade");
        btn_upgrade2.setVisibility(0);
        ProgressBar bar_upgrade2 = (ProgressBar) _$_findCachedViewById(R.id.bar_upgrade);
        Intrinsics.checkExpressionValueIsNotNull(bar_upgrade2, "bar_upgrade");
        bar_upgrade2.setVisibility(4);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        OtaPresenter otaPresenter;
        this.mHandler.removeCallbacksAndMessages(null);
        OtaPresenter otaPresenter2 = this.otaHelper;
        if (otaPresenter2 == null) {
            Intrinsics.throwNpe();
        }
        if (otaPresenter2.isOTA() && (otaPresenter = this.otaHelper) != null) {
            otaPresenter.cancelOTA();
        }
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateDeviceConnectedStatus(Boolean bool) {
        if (bool == null) {
            Intrinsics.throwNpe();
        }
        int i = bool.booleanValue() ? 0 : 4;
        if (bool.booleanValue()) {
            TextView tv_connect_status = (TextView) _$_findCachedViewById(R.id.tv_connect_status);
            Intrinsics.checkExpressionValueIsNotNull(tv_connect_status, "tv_connect_status");
            tv_connect_status.setText(getString(R.string.device_status_connected));
            Button btn_upgrade = (Button) _$_findCachedViewById(R.id.btn_upgrade);
            Intrinsics.checkExpressionValueIsNotNull(btn_upgrade, "btn_upgrade");
            btn_upgrade.setEnabled(true);
            Button btn_upgrade2 = (Button) _$_findCachedViewById(R.id.btn_upgrade);
            Intrinsics.checkExpressionValueIsNotNull(btn_upgrade2, "btn_upgrade");
            btn_upgrade2.setVisibility(i);
            ((Button) _$_findCachedViewById(R.id.btn_upgrade)).setBackgroundResource(R.drawable.bg_btn_upgrade);
            updateUpgradeTips(getString(R.string.ota_upgrade_not_started), i);
            updateUpgradeProgressTv(0.0f, 8);
            updateUpgradeProgressPb(0.0f, i);
            OtaPresenter otaPresenter = this.otaHelper;
            updateConnectedDeviceInfo(otaPresenter != null ? otaPresenter.getConnectedDevice() : null);
            return;
        }
        TextView tv_connect_status2 = (TextView) _$_findCachedViewById(R.id.tv_connect_status);
        Intrinsics.checkExpressionValueIsNotNull(tv_connect_status2, "tv_connect_status");
        tv_connect_status2.setText(getString(R.string.device_status_disconnected));
        Button btn_upgrade3 = (Button) _$_findCachedViewById(R.id.btn_upgrade);
        Intrinsics.checkExpressionValueIsNotNull(btn_upgrade3, "btn_upgrade");
        btn_upgrade3.setEnabled(false);
        Button btn_upgrade4 = (Button) _$_findCachedViewById(R.id.btn_upgrade);
        Intrinsics.checkExpressionValueIsNotNull(btn_upgrade4, "btn_upgrade");
        btn_upgrade4.setVisibility(0);
        ((Button) _$_findCachedViewById(R.id.btn_upgrade)).setBackgroundResource(R.drawable.dbg_btn_unenable);
        updateUpgradeTips(null, 0);
        updateUpgradeProgressTv(0.0f, 8);
        ProgressBar bar_upgrade = (ProgressBar) _$_findCachedViewById(R.id.bar_upgrade);
        Intrinsics.checkExpressionValueIsNotNull(bar_upgrade, "bar_upgrade");
        bar_upgrade.setVisibility(i);
        updateConnectedDeviceInfo(null);
    }

    @Override // com.jieli.otasdk.base.BaseView
    public void setPresenter(IOtaContract.IOtaPresenter presenter) {
        Intrinsics.checkParameterIsNotNull(presenter, "presenter");
        if (this.otaHelper != null) {
        }
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onOTAError(final int i, final String message) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        String tag = getTag();
        JL_Log.e(tag, "startOta has error : " + message);
        this.mHandler.post(new Runnable() { // from class: com.jieli.otasdk.fragments.OtaFragment$onOTAError$1
            @Override // java.lang.Runnable
            public final void run() {
                if (OtaFragment.this.isAdded()) {
                    int i2 = i;
                    if (i2 == 16392) {
                        ToastUtil.showToastShort(message);
                        return;
                    }
                    if (i2 == 20485) {
                        OtaFragment.this.readFileList();
                    }
                    StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                    String string = OtaFragment.this.getString(R.string.ota_upgrade_failed);
                    Intrinsics.checkExpressionValueIsNotNull(string, "getString(R.string.ota_upgrade_failed)");
                    String format = String.format(string, Arrays.copyOf(new Object[]{message}, 1));
                    Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(format, *args)");
                    OtaFragment.this.updateUpgradeProgressTv(0.0f, 8);
                    OtaFragment.this.updateUpgradeTips(format, 0);
                    OtaFragment.this.updateOtaUiStatus(false);
                }
            }
        });
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onOTAReconnect(String str) {
        OtaPresenter otaPresenter;
        if (getContext() == null || !PreferencesHelper.getSharedPreferences(getContext()).getBoolean(JL_Constant.KEY_USE_CUSTOM_RECONNECT_WAY, false) || (otaPresenter = this.otaHelper) == null) {
            return;
        }
        otaPresenter.reconnectDev(str);
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onConnection(BluetoothDevice bluetoothDevice, int i) {
        OtaPresenter otaPresenter = this.otaHelper;
        if (otaPresenter == null) {
            Intrinsics.throwNpe();
        }
        if (otaPresenter.isOTA()) {
            return;
        }
        OtaPresenter otaPresenter2 = this.otaHelper;
        updateDeviceConnectedStatus(otaPresenter2 != null ? Boolean.valueOf(otaPresenter2.isDevConnected()) : null);
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onMandatoryUpgrade() {
        ToastUtil.showToastShort(R.string.device_must_mandatory_upgrade);
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onOTAStart() {
        Window window;
        if (isAdded()) {
            FragmentActivity activity = getActivity();
            if (activity != null && (window = activity.getWindow()) != null) {
                window.addFlags(128);
            }
            updateUpgradeProgressPb(0.0f, 4);
            updateUpgradeProgressTv(0.0f, 4);
            updateUpgradeTips(getString(R.string.ota_checking_upgrade_file), 0);
            updateOtaUiStatus(true);
        }
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onOTAStop() {
        this.mHandler.post(new Runnable() { // from class: com.jieli.otasdk.fragments.OtaFragment$onOTAStop$1
            @Override // java.lang.Runnable
            public final void run() {
                Window window;
                if (OtaFragment.this.isAdded()) {
                    FragmentActivity activity = OtaFragment.this.getActivity();
                    if (activity != null && (window = activity.getWindow()) != null) {
                        window.clearFlags(128);
                    }
                    OtaFragment.this.updateUpgradeProgressTv(0.0f, 8);
                    OtaFragment otaFragment = OtaFragment.this;
                    otaFragment.updateUpgradeTips(otaFragment.getString(R.string.ota_complete), 0);
                    OtaFragment.this.updateDeviceConnectedStatus(false);
                }
            }
        });
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onOTACancel() {
        Window window;
        if (isAdded()) {
            FragmentActivity activity = getActivity();
            if (activity != null && (window = activity.getWindow()) != null) {
                window.clearFlags(128);
            }
            updateUpgradeProgressTv(0.0f, 8);
            updateUpgradeTips(getString(R.string.ota_upgrade_cancel), 0);
            updateOtaUiStatus(false);
        }
    }

    @Override // com.jieli.otasdk.tool.IOtaContract.IOtaView
    public void onOTAProgress(int i, float f) {
        if (isAdded()) {
            if (f > 0) {
                updateUpgradeProgressTv(f, 0);
                String string = getString(i == 0 ? R.string.ota_check_file : R.string.ota_upgrading);
                Intrinsics.checkExpressionValueIsNotNull(string, "if (type == 0) getString…g(R.string.ota_upgrading)");
                updateUpgradeTips(string, 0);
            } else {
                updateUpgradeProgressTv(0.0f, 4);
            }
            updateUpgradeProgressPb(f, 0);
        }
    }
}
