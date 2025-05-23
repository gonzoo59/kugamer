package com.jieli.jl_bt_ota.impl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.jieli.jl_bt_ota.interfaces.IBluetoothCallback;
import com.jieli.jl_bt_ota.interfaces.IBluetoothManager;
import com.jieli.jl_bt_ota.interfaces.IUpgradeManager;
import com.jieli.jl_bt_ota.model.BleScanMessage;
import com.jieli.jl_bt_ota.model.BluetoothOTAConfigure;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.HashSet;
import java.util.Iterator;
/* loaded from: classes2.dex */
public abstract class BluetoothBase implements IBluetoothManager, IUpgradeManager {
    protected BluetoothAdapter mBluetoothAdapter;
    private BluetoothAdapterReceiver mBluetoothAdapterReceiver;
    private HashSet<IBluetoothCallback> mBluetoothCallbackSet;
    protected BluetoothOTAConfigure mBluetoothOption;
    private boolean mHasBle;
    protected String TAG = getClass().getSimpleName();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public BluetoothBase(Context context) {
        CommonUtil.setMainContext(context);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mHasBle = BluetoothUtil.hasBle(context);
        registerReceiver();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void finalize() throws Throwable {
        unregisterReceiver();
        super.finalize();
    }

    private void registerReceiver() {
        if (this.mBluetoothAdapterReceiver == null) {
            this.mBluetoothAdapterReceiver = new BluetoothAdapterReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            CommonUtil.getMainContext().registerReceiver(this.mBluetoothAdapterReceiver, intentFilter);
        }
    }

    private void unregisterReceiver() {
        if (this.mBluetoothAdapterReceiver != null) {
            CommonUtil.getMainContext().unregisterReceiver(this.mBluetoothAdapterReceiver);
            this.mBluetoothAdapterReceiver = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class BluetoothAdapterReceiver extends BroadcastReceiver {
        private BluetoothAdapterReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (TextUtils.isEmpty(action)) {
                    return;
                }
                action.hashCode();
                if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED") && BluetoothBase.this.mBluetoothAdapter != null) {
                    int state = BluetoothBase.this.mBluetoothAdapter.getState();
                    String str = BluetoothBase.this.TAG;
                    JL_Log.i(str, "recv action : ACTION_STATE_CHANGED, state : " + state);
                    if (10 == state) {
                        BluetoothBase bluetoothBase = BluetoothBase.this;
                        bluetoothBase.onAdapterStatus(false, bluetoothBase.mHasBle);
                    } else if (12 == state) {
                        BluetoothBase bluetoothBase2 = BluetoothBase.this;
                        bluetoothBase2.onAdapterStatus(true, bluetoothBase2.mHasBle);
                    }
                }
            }
        }
    }

    public boolean isBluetoothEnabled() {
        return BluetoothUtil.isBluetoothEnable();
    }

    public boolean enableBluetooth() {
        if (this.mBluetoothAdapter == null) {
            JL_Log.i(this.TAG, "-enableBluetooth- mBluetoothAdapter is null.");
            onAdapterStatus(false, this.mHasBle);
            return false;
        }
        this.mHasBle = BluetoothUtil.hasBle(CommonUtil.getMainContext());
        if (this.mBluetoothAdapter.isEnabled()) {
            onAdapterStatus(true, this.mHasBle);
        } else if (!this.mBluetoothAdapter.enable()) {
            onAdapterStatus(false, this.mHasBle);
        }
        return true;
    }

    public boolean disableBluetooth() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            return false;
        }
        if (!bluetoothAdapter.isEnabled() || this.mBluetoothAdapter.disable()) {
            onAdapterStatus(false, this.mHasBle);
            return true;
        }
        return false;
    }

    public boolean registerBluetoothCallback(IBluetoothCallback iBluetoothCallback) {
        if (iBluetoothCallback != null) {
            if (this.mBluetoothCallbackSet == null) {
                this.mBluetoothCallbackSet = new HashSet<>();
            }
            return this.mBluetoothCallbackSet.add(iBluetoothCallback);
        }
        return false;
    }

    public boolean unregisterBluetoothCallback(IBluetoothCallback iBluetoothCallback) {
        HashSet<IBluetoothCallback> hashSet;
        if (iBluetoothCallback == null || (hashSet = this.mBluetoothCallbackSet) == null) {
            return false;
        }
        return hashSet.remove(iBluetoothCallback);
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void configure(BluetoothOTAConfigure bluetoothOTAConfigure) {
        this.mBluetoothOption = (BluetoothOTAConfigure) CommonUtil.checkNotNull(bluetoothOTAConfigure, "configure must not null.");
    }

    public BluetoothOTAConfigure getBluetoothOption() {
        return this.mBluetoothOption;
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void release() {
        unregisterReceiver();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onAdapterStatus(final boolean z, final boolean z2) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothBase.1
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothBase.this.mBluetoothCallbackSet != null) {
                    Iterator it = new HashSet(BluetoothBase.this.mBluetoothCallbackSet).iterator();
                    while (it.hasNext()) {
                        ((IBluetoothCallback) it.next()).onAdapterStatus(z, z2);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Deprecated
    public void onDiscovery(final BluetoothDevice bluetoothDevice) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothBase.2
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothBase.this.mBluetoothCallbackSet != null) {
                    Iterator it = new HashSet(BluetoothBase.this.mBluetoothCallbackSet).iterator();
                    while (it.hasNext()) {
                        ((IBluetoothCallback) it.next()).onDiscovery(bluetoothDevice);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDiscovery(final BluetoothDevice bluetoothDevice, final BleScanMessage bleScanMessage) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothBase.3
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothBase.this.mBluetoothCallbackSet == null || bluetoothDevice == null) {
                    return;
                }
                Iterator it = new HashSet(BluetoothBase.this.mBluetoothCallbackSet).iterator();
                while (it.hasNext()) {
                    ((IBluetoothCallback) it.next()).onDiscovery(bluetoothDevice, bleScanMessage);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDiscoveryStatus(final boolean z, final boolean z2) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothBase.4
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothBase.this.mBluetoothCallbackSet != null) {
                    Iterator it = new HashSet(BluetoothBase.this.mBluetoothCallbackSet).iterator();
                    while (it.hasNext()) {
                        ((IBluetoothCallback) it.next()).onDiscoveryStatus(z, z2);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onA2dpStatus(final BluetoothDevice bluetoothDevice, final int i) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothBase.5
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothBase.this.mBluetoothCallbackSet != null) {
                    Iterator it = new HashSet(BluetoothBase.this.mBluetoothCallbackSet).iterator();
                    while (it.hasNext()) {
                        ((IBluetoothCallback) it.next()).onA2dpStatus(bluetoothDevice, i);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onHfpStatus(final BluetoothDevice bluetoothDevice, final int i) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothBase.6
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothBase.this.mBluetoothCallbackSet != null) {
                    Iterator it = new HashSet(BluetoothBase.this.mBluetoothCallbackSet).iterator();
                    while (it.hasNext()) {
                        ((IBluetoothCallback) it.next()).onHfpStatus(bluetoothDevice, i);
                    }
                }
            }
        });
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void onBtDeviceConnection(final BluetoothDevice bluetoothDevice, final int i) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothBase.7
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothBase.this.mBluetoothCallbackSet != null) {
                    Iterator it = new HashSet(BluetoothBase.this.mBluetoothCallbackSet).iterator();
                    while (it.hasNext()) {
                        ((IBluetoothCallback) it.next()).onBtDeviceConnection(bluetoothDevice, i);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onConnection(final BluetoothDevice bluetoothDevice, final int i) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothBase.8
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothBase.this.mBluetoothCallbackSet != null) {
                    Iterator it = new HashSet(BluetoothBase.this.mBluetoothCallbackSet).iterator();
                    while (it.hasNext()) {
                        ((IBluetoothCallback) it.next()).onConnection(bluetoothDevice, i);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onReceiveData(final BluetoothDevice bluetoothDevice, final byte[] bArr) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothBase.9
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothBase.this.mBluetoothCallbackSet != null) {
                    Iterator it = new HashSet(BluetoothBase.this.mBluetoothCallbackSet).iterator();
                    while (it.hasNext()) {
                        ((IBluetoothCallback) it.next()).onReceiveData(bluetoothDevice, bArr);
                    }
                }
            }
        });
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void onError(final BaseError baseError) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothBase.10
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothBase.this.mBluetoothCallbackSet != null) {
                    Iterator it = new HashSet(BluetoothBase.this.mBluetoothCallbackSet).iterator();
                    while (it.hasNext()) {
                        ((IBluetoothCallback) it.next()).onError(baseError);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBleDataBlockChanged(final BluetoothDevice bluetoothDevice, final int i, final int i2) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothBase.11
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothBase.this.mBluetoothCallbackSet != null) {
                    Iterator it = new HashSet(BluetoothBase.this.mBluetoothCallbackSet).iterator();
                    while (it.hasNext()) {
                        ((IBluetoothCallback) it.next()).onBleDataBlockChanged(bluetoothDevice, i, i2);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBondStatus(final BluetoothDevice bluetoothDevice, final int i) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothBase.12
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothBase.this.mBluetoothCallbackSet != null) {
                    Iterator it = new HashSet(BluetoothBase.this.mBluetoothCallbackSet).iterator();
                    while (it.hasNext()) {
                        ((IBluetoothCallback) it.next()).onBondStatus(bluetoothDevice, i);
                    }
                }
            }
        });
    }

    public BluetoothDevice getRemoteDevice(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (this.mBluetoothAdapter == null) {
            this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            JL_Log.w(this.TAG, "-mBluetoothAdapter- null ---- ");
            return null;
        }
        try {
            return bluetoothAdapter.getRemoteDevice(str);
        } catch (Exception e) {
            e.printStackTrace();
            String str2 = this.TAG;
            JL_Log.e(str2, "getRemoteDevice Exception " + e.getMessage());
            return null;
        }
    }
}
