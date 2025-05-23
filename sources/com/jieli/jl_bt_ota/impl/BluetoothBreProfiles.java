package com.jieli.jl_bt_ota.impl;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.text.TextUtils;
import com.jieli.jl_bt_ota.constant.BluetoothConstant;
import com.jieli.jl_bt_ota.constant.ErrorCode;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public abstract class BluetoothBreProfiles extends BluetoothPair {
    private final BluetoothProfile.ServiceListener mBTServiceListener;
    private BluetoothA2dp mBluetoothA2dp;
    private BluetoothHandFreeReceiver mBluetoothHandFreeReceiver;
    private BluetoothHeadset mBluetoothHfp;

    public BluetoothBreProfiles(Context context) {
        super(context);
        this.mBTServiceListener = new BluetoothProfile.ServiceListener() { // from class: com.jieli.jl_bt_ota.impl.BluetoothBreProfiles.1
            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                String str = BluetoothBreProfiles.this.TAG;
                JL_Log.i(str, "------------onServiceConnected--------profile=" + i);
                if (2 == i) {
                    BluetoothBreProfiles.this.mBluetoothA2dp = (BluetoothA2dp) bluetoothProfile;
                } else if (1 == i) {
                    BluetoothBreProfiles.this.mBluetoothHfp = (BluetoothHeadset) bluetoothProfile;
                }
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceDisconnected(int i) {
                JL_Log.i(BluetoothBreProfiles.this.TAG, "------------onServiceDisconnected--------");
                if (2 == i) {
                    BluetoothBreProfiles.this.mBluetoothA2dp = null;
                } else if (1 == i) {
                    BluetoothBreProfiles.this.mBluetoothHfp = null;
                }
            }
        };
        init(context);
        registerReceiver();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.jieli.jl_bt_ota.impl.BluetoothPair, com.jieli.jl_bt_ota.impl.BluetoothDiscovery, com.jieli.jl_bt_ota.impl.BluetoothBase
    public void finalize() throws Throwable {
        unregisterReceiver();
        super.finalize();
    }

    private boolean init(Context context) {
        boolean z;
        boolean z2 = false;
        if (context == null) {
            return false;
        }
        if (this.mBluetoothAdapter == null) {
            this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (this.mBluetoothAdapter == null) {
            JL_Log.e(this.TAG, "get bluetooth adapter is null.");
            return false;
        }
        if (this.mBluetoothA2dp == null) {
            try {
                z2 = this.mBluetoothAdapter.getProfileProxy(context, this.mBTServiceListener, 2);
                if (!z2) {
                    JL_Log.e(this.TAG, "BluetoothBreProfiles: a2dp error.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.mBluetoothHfp != null) {
            return true;
        }
        try {
            z = this.mBluetoothAdapter.getProfileProxy(context, this.mBTServiceListener, 1);
            if (z) {
                return z;
            }
            try {
                JL_Log.e(this.TAG, "BluetoothBreProfiles: hfp error");
                return z;
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                return z;
            }
        } catch (Exception e3) {
            e = e3;
            z = z2;
        }
    }

    protected BluetoothHeadset getBluetoothHfp() {
        return this.mBluetoothHfp;
    }

    protected BluetoothA2dp getmBluetoothA2dp() {
        return this.mBluetoothA2dp;
    }

    public List<BluetoothDevice> getDevicesConnectedByProfile() {
        BluetoothHeadset bluetoothHeadset = this.mBluetoothHfp;
        List<BluetoothDevice> connectedDevices = bluetoothHeadset != null ? bluetoothHeadset.getConnectedDevices() : null;
        ArrayList arrayList = connectedDevices != null ? new ArrayList(connectedDevices) : null;
        BluetoothA2dp bluetoothA2dp = this.mBluetoothA2dp;
        if (bluetoothA2dp != null) {
            connectedDevices = bluetoothA2dp.getConnectedDevices();
        }
        if (connectedDevices != null) {
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            arrayList.addAll(connectedDevices);
        }
        return arrayList;
    }

    public boolean deviceHasHfp(BluetoothDevice bluetoothDevice) {
        return BluetoothUtil.deviceHasProfile(bluetoothDevice, BluetoothConstant.UUID_HFP);
    }

    public boolean deviceHasA2dp(BluetoothDevice bluetoothDevice) {
        return BluetoothUtil.deviceHasProfile(bluetoothDevice, BluetoothConstant.UUID_A2DP);
    }

    public int isConnectedByProfile(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            JL_Log.e(this.TAG, "-isConnectedByProfile- device is null.");
            return 0;
        } else if (this.mBluetoothHfp == null || this.mBluetoothA2dp == null) {
            JL_Log.e(this.TAG, "mBluetoothHfp or mBluetoothA2dp is null.");
            init(CommonUtil.getMainContext());
            return ErrorCode.SUB_ERR_A2DP_NOT_INIT;
        } else if (bluetoothDevice.getType() == 2) {
            JL_Log.e(this.TAG, "device is Invalid.");
            return 0;
        } else {
            List<BluetoothDevice> connectedDevices = this.mBluetoothHfp.getConnectedDevices();
            if (connectedDevices != null) {
                for (BluetoothDevice bluetoothDevice2 : connectedDevices) {
                    if (bluetoothDevice2.getAddress().equals(bluetoothDevice.getAddress())) {
                        JL_Log.w(this.TAG, "device connect hfp.");
                        return 2;
                    }
                }
            }
            List<BluetoothDevice> connectedDevices2 = this.mBluetoothA2dp.getConnectedDevices();
            if (connectedDevices2 != null) {
                for (BluetoothDevice bluetoothDevice3 : connectedDevices2) {
                    if (bluetoothDevice3.getAddress().equals(bluetoothDevice.getAddress())) {
                        JL_Log.w(this.TAG, "device connect a2dp.");
                        return 2;
                    }
                }
            }
            return 0;
        }
    }

    public boolean disconnectByProfiles(BluetoothDevice bluetoothDevice) {
        boolean z = false;
        if (bluetoothDevice == null) {
            JL_Log.i(this.TAG, "-disconnectByProfiles- device is null ");
            return false;
        }
        String str = this.TAG;
        JL_Log.i(str, "-disconnectByProfiles- device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice));
        if (bluetoothDevice.getType() != 2) {
            int isConnectedByA2dp = isConnectedByA2dp(bluetoothDevice);
            if (isConnectedByA2dp == 2) {
                z = disconnectFromA2dp(bluetoothDevice);
                String str2 = this.TAG;
                JL_Log.i(str2, "-disconnectByProfiles- disconnectFromA2dp ret : " + z);
            }
            int isConnectedByHfp = isConnectedByHfp(bluetoothDevice);
            if (isConnectedByHfp == 2) {
                boolean disconnectFromHfp = disconnectFromHfp(bluetoothDevice);
                String str3 = this.TAG;
                JL_Log.i(str3, "-disconnectByProfiles- disconnectFromHfp ret : " + disconnectFromHfp);
                z = disconnectFromHfp;
            }
            if (isConnectedByA2dp == 0 && isConnectedByHfp == 0) {
                return true;
            }
            return z;
        }
        return false;
    }

    public boolean disconnectFromA2dp(BluetoothDevice bluetoothDevice) {
        boolean z = false;
        if (bluetoothDevice == null) {
            JL_Log.e(this.TAG, "-disconnectFromA2dp- device is null");
            return false;
        } else if (this.mBluetoothA2dp == null) {
            JL_Log.e(this.TAG, "-disconnectFromA2dp- mBluetoothA2dp is null");
            return false;
        } else {
            int isConnectedByA2dp = isConnectedByA2dp(bluetoothDevice);
            if (isConnectedByA2dp == 0) {
                JL_Log.i(this.TAG, "-disconnectFromA2dp- A2dp is disconnected");
                return true;
            }
            if (isConnectedByA2dp == 2) {
                try {
                    Method method = BluetoothA2dp.class.getMethod("disconnect", BluetoothDevice.class);
                    method.setAccessible(true);
                    z = ((Boolean) method.invoke(this.mBluetoothA2dp, bluetoothDevice)).booleanValue();
                } catch (Exception e) {
                    String str = this.TAG;
                    JL_Log.e(str, "-disconnectFromA2dp- have an exception : " + e.toString());
                    e.printStackTrace();
                }
            }
            String str2 = this.TAG;
            JL_Log.i(str2, "-disconnectFromA2dp- ret : " + z);
            return z;
        }
    }

    protected boolean disconnectFromA2dp(String str) {
        BluetoothDevice remoteDevice = getRemoteDevice(str);
        return remoteDevice != null && disconnectFromA2dp(remoteDevice);
    }

    public int isConnectedByA2dp(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            JL_Log.e(this.TAG, "-isConnectedByA2dp- device is null");
            return 0;
        }
        BluetoothA2dp bluetoothA2dp = this.mBluetoothA2dp;
        if (bluetoothA2dp == null) {
            JL_Log.e(this.TAG, "-isConnectedByA2dp- mBluetoothA2dp is null");
            return ErrorCode.SUB_ERR_A2DP_NOT_INIT;
        }
        List<BluetoothDevice> connectedDevices = bluetoothA2dp.getConnectedDevices();
        if (connectedDevices != null) {
            for (BluetoothDevice bluetoothDevice2 : connectedDevices) {
                if (bluetoothDevice2.getAddress().equals(bluetoothDevice.getAddress())) {
                    JL_Log.i(this.TAG, "-isConnectedByA2dp- ret : true");
                    return 2;
                }
            }
        } else {
            JL_Log.i(this.TAG, "-isConnectedByA2dp- connect list is null");
        }
        JL_Log.i(this.TAG, "-isConnectedByA2dp- ret : false");
        return this.mBluetoothA2dp.getConnectionState(bluetoothDevice);
    }

    public boolean disconnectFromHfp(BluetoothDevice bluetoothDevice) {
        boolean z = false;
        if (bluetoothDevice == null) {
            JL_Log.e(this.TAG, "-disconnectFromHfp- device is null");
            return false;
        } else if (this.mBluetoothHfp == null) {
            JL_Log.e(this.TAG, "-disconnectFromHfp- mBluetoothHfp is null");
            return false;
        } else {
            int isConnectedByHfp = isConnectedByHfp(bluetoothDevice);
            if (isConnectedByHfp == 0) {
                JL_Log.i(this.TAG, "-disconnectFromHfp- hfp is disconnected");
                return true;
            }
            if (isConnectedByHfp == 2) {
                try {
                    Method method = BluetoothHeadset.class.getMethod("disconnect", BluetoothDevice.class);
                    method.setAccessible(true);
                    z = ((Boolean) method.invoke(this.mBluetoothHfp, bluetoothDevice)).booleanValue();
                } catch (Exception e) {
                    String str = this.TAG;
                    JL_Log.e(str, "-disconnectFromHfp- have an exception : " + e.toString());
                    e.printStackTrace();
                }
            }
            String str2 = this.TAG;
            JL_Log.i(str2, "-disconnectFromHfp- ret : " + z);
            return z;
        }
    }

    public boolean disconnectFromHfp(String str) {
        BluetoothDevice remoteDevice = getRemoteDevice(str);
        return remoteDevice != null && disconnectFromHfp(remoteDevice);
    }

    public int isConnectedByHfp(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            JL_Log.e(this.TAG, "-isConnectedByHfp- device is null");
            return 0;
        }
        BluetoothHeadset bluetoothHeadset = this.mBluetoothHfp;
        if (bluetoothHeadset == null) {
            JL_Log.e(this.TAG, "-isConnectedByHfp- mBluetoothHfp is null");
            return ErrorCode.SUB_ERR_HFP_NOT_INIT;
        }
        List<BluetoothDevice> connectedDevices = bluetoothHeadset.getConnectedDevices();
        if (connectedDevices != null) {
            for (BluetoothDevice bluetoothDevice2 : connectedDevices) {
                if (bluetoothDevice2.getAddress().equals(bluetoothDevice.getAddress())) {
                    JL_Log.i(this.TAG, "-isConnectedByHfp- ret : true.");
                    return 2;
                }
            }
        } else {
            JL_Log.i(this.TAG, "-isConnectedByHfp- no connect list");
        }
        int connectionState = this.mBluetoothHfp.getConnectionState(bluetoothDevice);
        String str = this.TAG;
        JL_Log.i(str, "-isConnectedByHfp- ret : " + connectionState);
        return connectionState;
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothPair, com.jieli.jl_bt_ota.impl.BluetoothDiscovery, com.jieli.jl_bt_ota.impl.BluetoothBase, com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void release() {
        super.release();
        unregisterReceiver();
        if (this.mBluetoothA2dp != null && this.mBluetoothAdapter != null) {
            this.mBluetoothAdapter.closeProfileProxy(2, this.mBluetoothA2dp);
            this.mBluetoothA2dp = null;
        }
        if (this.mBluetoothHfp != null && this.mBluetoothAdapter != null) {
            this.mBluetoothAdapter.closeProfileProxy(1, this.mBluetoothHfp);
            this.mBluetoothHfp = null;
        }
        this.mBluetoothAdapter = null;
    }

    private void registerReceiver() {
        if (this.mBluetoothHandFreeReceiver == null) {
            this.mBluetoothHandFreeReceiver = new BluetoothHandFreeReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.bluetooth.device.action.UUID");
            intentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.a2dp.profile.action.PLAYING_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
            CommonUtil.getMainContext().registerReceiver(this.mBluetoothHandFreeReceiver, intentFilter);
        }
    }

    private void unregisterReceiver() {
        if (this.mBluetoothHandFreeReceiver != null) {
            CommonUtil.getMainContext().unregisterReceiver(this.mBluetoothHandFreeReceiver);
            this.mBluetoothHandFreeReceiver = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class BluetoothHandFreeReceiver extends BroadcastReceiver {
        private BluetoothHandFreeReceiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            char c;
            if (intent != null) {
                String action = intent.getAction();
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (TextUtils.isEmpty(action) || bluetoothDevice == null) {
                    return;
                }
                action.hashCode();
                switch (action.hashCode()) {
                    case -377527494:
                        if (action.equals("android.bluetooth.device.action.UUID")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 545516589:
                        if (action.equals("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1244161670:
                        if (action.equals("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("android.bluetooth.device.extra.UUID");
                        if (parcelableArrayExtra == null) {
                            JL_Log.i(BluetoothBreProfiles.this.TAG, "onReceive: ACTION_UUID no uuids");
                            return;
                        }
                        ParcelUuid[] parcelUuidArr = new ParcelUuid[parcelableArrayExtra.length];
                        for (int i = 0; i < parcelableArrayExtra.length; i++) {
                            parcelUuidArr[i] = ParcelUuid.fromString(parcelableArrayExtra[i].toString());
                            String str = BluetoothBreProfiles.this.TAG;
                            JL_Log.i(str, "onReceive: ACTION_UUID " + parcelUuidArr[i].toString());
                        }
                        return;
                    case 1:
                        try {
                            int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
                            String str2 = BluetoothBreProfiles.this.TAG;
                            JL_Log.i(str2, "onReceive: hfp ACTION_CONNECTION_STATE_CHANGED device : " + bluetoothDevice.getName() + ", state : " + intExtra);
                            BluetoothBreProfiles.this.onHfpStatus(bluetoothDevice, intExtra);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    case 2:
                        try {
                            int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
                            String str3 = BluetoothBreProfiles.this.TAG;
                            JL_Log.i(str3, "onReceive: a2dp ACTION_CONNECTION_STATE_CHANGED device : " + bluetoothDevice.getName() + ", state : " + intExtra2);
                            BluetoothBreProfiles.this.onA2dpStatus(bluetoothDevice, intExtra2);
                            return;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            return;
                        }
                    default:
                        return;
                }
            }
        }
    }
}
