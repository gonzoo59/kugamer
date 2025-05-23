package com.jieli.jl_bt_ota.tool;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.jieli.jl_bt_ota.impl.BluetoothOTAManager;
import com.jieli.jl_bt_ota.interfaces.BtEventCallback;
import com.jieli.jl_bt_ota.model.BleScanMessage;
import com.jieli.jl_bt_ota.model.ReConnectDevMsg;
import com.jieli.jl_bt_ota.model.response.TargetInfoResponse;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
/* loaded from: classes2.dex */
public class DeviceReConnectManager {
    private static final long FAST_CONNECT_TIMEOUT = 36000;
    private static final int MSG_RECONNECT_DEVICE_TIMEOUT = 37974;
    private static final String TAG = "DeviceReConnectManager";
    private static final int TASK_INTERVAL_TIME = 16000;
    private static volatile DeviceReConnectManager instance;
    private long beginTaskTime = 0;
    private long leftTimeoutTime = 0;
    private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.jieli.jl_bt_ota.tool.DeviceReConnectManager.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != DeviceReConnectManager.MSG_RECONNECT_DEVICE_TIMEOUT) {
                return false;
            }
            DeviceReConnectManager.this.stopReconnectTask();
            return false;
        }
    });
    private final BtEventCallback mIBluetoothCallback;
    private final BluetoothOTAManager mIBluetoothManager;
    private volatile ReConnectDevMsg mReConnectDevMsg;
    private ReConnectTask mReConnectTask;
    private Timer mTimer;

    private DeviceReConnectManager(BluetoothOTAManager bluetoothOTAManager) {
        BtEventCallback btEventCallback = new BtEventCallback() { // from class: com.jieli.jl_bt_ota.tool.DeviceReConnectManager.2
            @Override // com.jieli.jl_bt_ota.interfaces.BtEventCallback, com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
            public void onDiscovery(BluetoothDevice bluetoothDevice) {
            }

            @Override // com.jieli.jl_bt_ota.interfaces.BtEventCallback, com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
            public void onDiscoveryStatus(boolean z, boolean z2) {
            }

            @Override // com.jieli.jl_bt_ota.interfaces.BtEventCallback, com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
            public void onAdapterStatus(boolean z, boolean z2) {
                if (z) {
                    return;
                }
                DeviceReConnectManager.this.stopReconnectTask();
            }

            @Override // com.jieli.jl_bt_ota.interfaces.BtEventCallback, com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
            public void onDiscovery(BluetoothDevice bluetoothDevice, BleScanMessage bleScanMessage) {
                if (bluetoothDevice == null) {
                    return;
                }
                boolean isDeviceReconnecting = DeviceReConnectManager.this.isDeviceReconnecting();
                boolean checkIsReconnectDevice = DeviceReConnectManager.this.checkIsReconnectDevice(bluetoothDevice);
                JL_Log.i(DeviceReConnectManager.TAG, "-onDiscovery-  isDeviceReconnecting : " + isDeviceReconnecting + " ,isReConnectDevice : " + checkIsReconnectDevice + ", device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice));
                if (isDeviceReconnecting && checkIsReconnectDevice) {
                    DeviceReConnectManager.this.stopTimer();
                    if (DeviceReConnectManager.this.beginTaskTime > 0) {
                        long currentTime = CommonUtil.getCurrentTime();
                        DeviceReConnectManager deviceReConnectManager = DeviceReConnectManager.this;
                        deviceReConnectManager.leftTimeoutTime = DeviceReConnectManager.FAST_CONNECT_TIMEOUT - (currentTime - deviceReConnectManager.beginTaskTime);
                        DeviceReConnectManager.this.beginTaskTime = currentTime;
                        DeviceReConnectManager.this.mHandler.removeMessages(DeviceReConnectManager.MSG_RECONNECT_DEVICE_TIMEOUT);
                    }
                    DeviceReConnectManager.this.mIBluetoothManager.connectBluetoothDevice(bluetoothDevice);
                }
            }

            @Override // com.jieli.jl_bt_ota.interfaces.BtEventCallback, com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
            public void onConnection(BluetoothDevice bluetoothDevice, int i) {
                boolean isDeviceReconnecting = DeviceReConnectManager.this.isDeviceReconnecting();
                boolean checkIsReconnectDevice = DeviceReConnectManager.this.checkIsReconnectDevice(bluetoothDevice);
                JL_Log.w(DeviceReConnectManager.TAG, "-onConnection- device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice) + ", status : " + i + ", isDeviceReconnecting : " + isDeviceReconnecting + ", isReConnectDevice : " + checkIsReconnectDevice);
                if (checkIsReconnectDevice) {
                    if (i == 1) {
                        DeviceReConnectManager.this.stopReconnectTask();
                    } else if ((i == 2 || i == 0) && isDeviceReconnecting) {
                        JL_Log.i(DeviceReConnectManager.TAG, "--> retry---------> leftTimeoutTime : " + DeviceReConnectManager.this.leftTimeoutTime);
                        if (DeviceReConnectManager.this.leftTimeoutTime > 0) {
                            DeviceReConnectManager.this.startReconnectTask();
                            DeviceReConnectManager.this.mHandler.removeMessages(DeviceReConnectManager.MSG_RECONNECT_DEVICE_TIMEOUT);
                            DeviceReConnectManager.this.mHandler.sendEmptyMessageDelayed(DeviceReConnectManager.MSG_RECONNECT_DEVICE_TIMEOUT, DeviceReConnectManager.this.leftTimeoutTime);
                            return;
                        }
                        JL_Log.i(DeviceReConnectManager.TAG, "--> retry 3 time ---------> stopReconnectTask");
                        DeviceReConnectManager.this.stopReconnectTask();
                    }
                }
            }
        };
        this.mIBluetoothCallback = btEventCallback;
        this.mIBluetoothManager = bluetoothOTAManager;
        bluetoothOTAManager.registerBluetoothCallback(btEventCallback);
    }

    public static DeviceReConnectManager getInstance(BluetoothOTAManager bluetoothOTAManager) {
        if (instance == null) {
            synchronized (DeviceReConnectManager.class) {
                if (instance == null) {
                    instance = new DeviceReConnectManager(bluetoothOTAManager);
                }
            }
        }
        return instance;
    }

    protected void finalize() throws Throwable {
        JL_Log.w(TAG, "finalize===> ");
        release();
        super.finalize();
    }

    public boolean isWaitingForUpdate() {
        ReConnectDevMsg reConnectDevMsg = getReConnectDevMsg();
        return reConnectDevMsg != null && BluetoothAdapter.checkBluetoothAddress(reConnectDevMsg.getAddress());
    }

    public boolean isDeviceReconnecting() {
        return this.mHandler.hasMessages(MSG_RECONNECT_DEVICE_TIMEOUT);
    }

    public void setReconnectAddress(String str) {
        if (!BluetoothAdapter.checkBluetoothAddress(str)) {
            setReConnectDevMsg(null);
        } else if (this.mReConnectDevMsg == null) {
            setReConnectDevMsg(new ReConnectDevMsg(this.mIBluetoothManager.getBluetoothOption().getPriority(), str));
        } else {
            this.mReConnectDevMsg.setAddress(str);
            JL_Log.d(TAG, "setReconnectAddress : " + this.mReConnectDevMsg);
        }
    }

    public void setReConnectDevMsg(ReConnectDevMsg reConnectDevMsg) {
        if (this.mReConnectDevMsg != reConnectDevMsg) {
            this.mReConnectDevMsg = reConnectDevMsg;
            JL_Log.d(TAG, "setReConnectDevMsg : " + reConnectDevMsg);
        }
    }

    public String getReconnectAddress() {
        ReConnectDevMsg reConnectDevMsg = getReConnectDevMsg();
        if (reConnectDevMsg == null) {
            return null;
        }
        return reConnectDevMsg.getAddress();
    }

    public void startReconnectTask() {
        if (isDevConnected()) {
            JL_Log.i(TAG, "-startReconnectTask- device is connected.");
            stopReconnectTask();
            return;
        }
        JL_Log.i(TAG, "-startReconnectTask- start....");
        long currentTime = CommonUtil.getCurrentTime();
        this.beginTaskTime = currentTime;
        this.leftTimeoutTime = FAST_CONNECT_TIMEOUT - currentTime;
        startTimer();
        this.mHandler.removeMessages(MSG_RECONNECT_DEVICE_TIMEOUT);
        this.mHandler.sendEmptyMessageDelayed(MSG_RECONNECT_DEVICE_TIMEOUT, FAST_CONNECT_TIMEOUT);
    }

    public void stopReconnectTask() {
        JL_Log.i(TAG, "--> stopReconnectTask --------->");
        stopTimer();
        this.mHandler.removeMessages(MSG_RECONNECT_DEVICE_TIMEOUT);
        this.beginTaskTime = 0L;
        this.leftTimeoutTime = 0L;
    }

    public boolean checkIsReconnectDevice(BluetoothDevice bluetoothDevice) {
        String str;
        String reconnectAddress = getReconnectAddress();
        if (bluetoothDevice == null || !BluetoothAdapter.checkBluetoothAddress(reconnectAddress)) {
            return false;
        }
        TargetInfoResponse deviceInfo = DeviceStatusManager.getInstance().getDeviceInfo(bluetoothDevice);
        String str2 = null;
        if (deviceInfo != null) {
            str2 = deviceInfo.getEdrAddr();
            str = deviceInfo.getBleAddr();
        } else {
            str = null;
        }
        JL_Log.w(TAG, "-checkIsReconnectDevice- device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice) + "\n, mReconnectAddress : " + reconnectAddress + ", mEdrAddr : " + str2 + ", mBleAddr : " + str);
        return reconnectAddress.equals(bluetoothDevice.getAddress()) || reconnectAddress.equals(str2) || reconnectAddress.equals(str);
    }

    public void release() {
        BluetoothOTAManager bluetoothOTAManager = this.mIBluetoothManager;
        if (bluetoothOTAManager != null) {
            bluetoothOTAManager.unregisterBluetoothCallback(this.mIBluetoothCallback);
        }
        setReConnectDevMsg(null);
        stopReconnectTask();
        this.mHandler.removeCallbacksAndMessages(null);
        instance = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ReConnectDevMsg getReConnectDevMsg() {
        if (this.mReConnectDevMsg == null) {
            return null;
        }
        return this.mReConnectDevMsg.cloneObject();
    }

    private void stopScan() {
        if (this.mIBluetoothManager != null) {
            JL_Log.w(TAG, "-stopScan- >>>>>>stopBLEScan ");
            this.mIBluetoothManager.stopBLEScan();
            this.mIBluetoothManager.stopDeviceScan();
        }
    }

    private void startTimer() {
        stopTimer();
        JL_Log.i(TAG, "--> startTimer =======>");
        this.mTimer = new Timer();
        ReConnectTask reConnectTask = new ReConnectTask();
        this.mReConnectTask = reConnectTask;
        this.mTimer.schedule(reConnectTask, 0L, 16000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopTimer() {
        JL_Log.i(TAG, "--> stopTimer ===============>");
        stopScan();
        ReConnectTask reConnectTask = this.mReConnectTask;
        if (reConnectTask != null) {
            reConnectTask.cancel();
            this.mReConnectTask = null;
        }
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
            this.mTimer = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BluetoothDevice getBtDeviceConnectedBySystem(String str) {
        List<BluetoothDevice> systemConnectedBtDeviceList = BluetoothUtil.getSystemConnectedBtDeviceList();
        if (BluetoothAdapter.checkBluetoothAddress(str) && systemConnectedBtDeviceList != null) {
            for (BluetoothDevice bluetoothDevice : systemConnectedBtDeviceList) {
                if (str.equals(bluetoothDevice.getAddress())) {
                    return bluetoothDevice;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDevConnected() {
        BluetoothOTAManager bluetoothOTAManager = this.mIBluetoothManager;
        return (bluetoothOTAManager == null || bluetoothOTAManager.getConnectedDevice() == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class ReConnectTask extends TimerTask {
        private ReConnectTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (DeviceReConnectManager.this.mIBluetoothManager == null) {
                return;
            }
            ReConnectDevMsg reConnectDevMsg = DeviceReConnectManager.this.getReConnectDevMsg();
            String address = reConnectDevMsg == null ? null : reConnectDevMsg.getAddress();
            JL_Log.i(DeviceReConnectManager.TAG, "ReConnectTask -----> mReconnectAddress : " + reConnectDevMsg + ", isDevConnected : " + DeviceReConnectManager.this.isDevConnected());
            if (reConnectDevMsg == null || !BluetoothAdapter.checkBluetoothAddress(address) || DeviceReConnectManager.this.isDevConnected()) {
                return;
            }
            BluetoothDevice btDeviceConnectedBySystem = DeviceReConnectManager.this.getBtDeviceConnectedBySystem(address);
            JL_Log.w(DeviceReConnectManager.TAG, "ReConnectTask -----> connectedDeviceBySystem : " + BluetoothUtil.printBtDeviceInfo(btDeviceConnectedBySystem));
            if (btDeviceConnectedBySystem != null) {
                BluetoothDevice remoteDevice = DeviceReConnectManager.this.mIBluetoothManager.getRemoteDevice(address);
                if (remoteDevice != null) {
                    JL_Log.i(DeviceReConnectManager.TAG, "ReConnectTask---> connectBluetoothDevice >>>> ");
                    DeviceReConnectManager.this.mIBluetoothManager.connectBluetoothDevice(remoteDevice);
                    return;
                }
                JL_Log.i(DeviceReConnectManager.TAG, "ReConnectTask---> stopReconnectTask >>>> because mCacheBleDevice is null.");
                DeviceReConnectManager.this.stopReconnectTask();
                return;
            }
            int startBLEScan = reConnectDevMsg.getWay() == 0 ? DeviceReConnectManager.this.mIBluetoothManager.startBLEScan(16000L) : DeviceReConnectManager.this.mIBluetoothManager.startDeviceScan(16000L, 1);
            JL_Log.i(DeviceReConnectManager.TAG, "ReConnectTask ----> start scan bluetooth ....." + startBLEScan);
        }
    }
}
