package com.jieli.otasdk.tool.ota.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.jieli.jl_bt_ota.constant.BluetoothConstant;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import com.jieli.jl_bt_ota.util.PreferencesHelper;
import com.jieli.otasdk.MainApplication;
import com.jieli.otasdk.tool.ota.ble.SendBleDataThread;
import com.jieli.otasdk.tool.ota.ble.interfaces.BleEventCallback;
import com.jieli.otasdk.tool.ota.ble.interfaces.IBleOp;
import com.jieli.otasdk.tool.ota.ble.interfaces.OnThreadStateListener;
import com.jieli.otasdk.tool.ota.ble.interfaces.OnWriteDataCallback;
import com.jieli.otasdk.tool.ota.ble.model.BleScanInfo;
import com.jieli.otasdk.util.AppUtil;
import com.jieli.otasdk.util.JL_Constant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
/* loaded from: classes2.dex */
public class BleManager implements IBleOp {
    private static final int CALLBACK_TIMEOUT = 3000;
    private static final int CONNECT_BLE_TIMEOUT = 40000;
    private static final long DELAY_WAITING_TIME = 5000;
    private static final int MSG_BLE_DISCOVER_SERVICES_CALLBACK_TIMEOUT = 4117;
    private static final int MSG_CHANGE_BLE_MTU_TIMEOUT = 4116;
    private static final int MSG_CONNECT_BLE_TIMEOUT = 4113;
    private static final int MSG_NOTIFY_BLE_TIMEOUT = 4115;
    private static final int MSG_RECONNECT_BLE = 4118;
    private static final int MSG_SCAN_BLE_TIMEOUT = 4112;
    private static final int MSG_SCAN_HID_DEVICE = 4114;
    private static final int RECONNECT_BLE_DELAY = 2000;
    private static final int SCAN_BLE_TIMEOUT = 8000;
    public static final int SEND_DATA_MAX_TIMEOUT = 8000;
    private static final String TAG = "BleManager";
    private static volatile BleManager instance;
    private volatile boolean isBleScanning;
    private BaseBtAdapterReceiver mAdapterReceiver;
    private final BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private BluetoothDevice mConnectedBtDevice;
    private volatile BluetoothGatt mConnectedBtGatt;
    private BluetoothDevice mConnectingBtDevice;
    private final Context mContext;
    private NotifyCharacteristicRunnable mNotifyCharacteristicRunnable;
    private String mReconnectDevAddr;
    private SendBleDataThread mSendBleDataThread;
    public static final UUID BLE_UUID_SERVICE = BluetoothConstant.UUID_SERVICE;
    public static final UUID BLE_UUID_WRITE = BluetoothConstant.UUID_WRITE;
    public static final UUID BLE_UUID_NOTIFICATION = BluetoothConstant.UUID_NOTIFICATION;
    private final List<BluetoothDevice> mDiscoveredBleDevices = new ArrayList();
    private final BleEventCallbackManager mCallbackManager = new BleEventCallbackManager();
    private volatile int mBleMtu = 20;
    private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.jieli.otasdk.tool.ota.ble.BleManager.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            List<BluetoothGattService> services;
            boolean z = true;
            switch (message.what) {
                case 4112:
                    if (BleManager.this.isBleScanning) {
                        BleManager.this.stopLeScan();
                        break;
                    }
                    break;
                case 4113:
                    if (BleManager.this.mConnectedBtDevice == null) {
                        BleManager bleManager = BleManager.this;
                        bleManager.handleBleConnection(bleManager.mConnectingBtDevice, 0);
                        BleManager.this.setConnectingBtDevice(null);
                        break;
                    }
                    break;
                case 4114:
                    List<BluetoothDevice> systemConnectedBtDeviceList = BluetoothUtil.getSystemConnectedBtDeviceList();
                    if (systemConnectedBtDeviceList != null) {
                        for (BluetoothDevice bluetoothDevice : systemConnectedBtDeviceList) {
                            if (bluetoothDevice.getType() != 1 && bluetoothDevice.getBondState() == 12) {
                                BleManager.this.handleDiscoveryBle(bluetoothDevice, null);
                            }
                        }
                    }
                    BleManager.this.mHandler.sendEmptyMessageDelayed(4114, 1000L);
                    break;
                case 4115:
                    if (BleManager.this.mConnectedBtDevice == null) {
                        BleManager bleManager2 = BleManager.this;
                        bleManager2.disconnectBleDevice(bleManager2.mConnectingBtDevice);
                        break;
                    }
                    break;
                case BleManager.MSG_CHANGE_BLE_MTU_TIMEOUT /* 4116 */:
                    BluetoothDevice bluetoothDevice2 = (BluetoothDevice) message.obj;
                    String str = BleManager.TAG;
                    JL_Log.i(str, "-MSG_CHANGE_BLE_MTU_TIMEOUT- request mtu timeout, device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice2));
                    if (BluetoothUtil.deviceEquals(bluetoothDevice2, BleManager.this.getConnectedBtDevice())) {
                        BleManager.this.handleBleConnectedEvent(bluetoothDevice2);
                        break;
                    } else {
                        BleManager.this.handleBleConnection(bluetoothDevice2, 0);
                        break;
                    }
                case BleManager.MSG_BLE_DISCOVER_SERVICES_CALLBACK_TIMEOUT /* 4117 */:
                    if (message.obj instanceof BluetoothDevice) {
                        BluetoothDevice bluetoothDevice3 = (BluetoothDevice) message.obj;
                        if (BluetoothUtil.deviceEquals(bluetoothDevice3, BleManager.this.mConnectedBtDevice)) {
                            if (BleManager.this.mConnectedBtGatt != null && (services = BleManager.this.mConnectedBtGatt.getServices()) != null && services.size() > 0) {
                                BleManager.this.mBluetoothGattCallback.onServicesDiscovered(BleManager.this.mConnectedBtGatt, 0);
                                z = false;
                            }
                            if (z) {
                                JL_Log.d(BleManager.TAG, "discover services timeout.");
                                BleManager.this.disconnectBleDevice(bluetoothDevice3);
                                BleManager.this.setReconnectDevAddr(bluetoothDevice3.getAddress());
                                break;
                            }
                        }
                    }
                    break;
                case BleManager.MSG_RECONNECT_BLE /* 4118 */:
                    if (message.obj instanceof BluetoothDevice) {
                        if (BleManager.this.reconnectDevice((BluetoothDevice) message.obj)) {
                            BleManager.this.setReconnectDevAddr(null);
                            break;
                        }
                    }
                    break;
            }
            return false;
        }
    });
    private final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() { // from class: com.jieli.otasdk.tool.ota.ble.BleManager$$ExternalSyntheticLambda0
        @Override // android.bluetooth.BluetoothAdapter.LeScanCallback
        public final void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
            BleManager.this.m132lambda$new$0$comjieliotasdktoolotableBleManager(bluetoothDevice, i, bArr);
        }
    };
    private final ScanCallback mScanCallback = new ScanCallback() { // from class: com.jieli.otasdk.tool.ota.ble.BleManager.3
        @Override // android.bluetooth.le.ScanCallback
        public void onBatchScanResults(List<ScanResult> list) {
        }

        @Override // android.bluetooth.le.ScanCallback
        public void onScanResult(int i, ScanResult scanResult) {
            if (scanResult == null || scanResult.getScanRecord() == null) {
                return;
            }
            BleManager.this.filterDevice(scanResult.getDevice(), scanResult.getRssi(), scanResult.getScanRecord().getBytes(), Build.VERSION.SDK_INT >= 26 ? scanResult.isConnectable() : true);
        }

        @Override // android.bluetooth.le.ScanCallback
        public void onScanFailed(int i) {
            String str = BleManager.TAG;
            JL_Log.d(str, "onScanFailed : " + i);
            BleManager.this.stopLeScan();
        }
    };
    private final BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() { // from class: com.jieli.otasdk.tool.ota.ble.BleManager.4
        public void onConnectionUpdated(BluetoothGatt bluetoothGatt, int i, int i2, int i3, int i4) {
            BluetoothDevice device;
            if (bluetoothGatt == null || (device = bluetoothGatt.getDevice()) == null) {
                return;
            }
            String str = BleManager.TAG;
            JL_Log.e(str, "onConnectionUpdated >> device : " + BluetoothUtil.printBtDeviceInfo(device) + ", interval : " + i + ", latency : " + i2 + ", timeout : " + i3 + ", status : " + i4);
            BleManager.this.mCallbackManager.onConnectionUpdated(device, i, i2, i3, i4);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            BluetoothDevice device;
            if (bluetoothGatt == null || (device = bluetoothGatt.getDevice()) == null) {
                return;
            }
            String str = BleManager.TAG;
            JL_Log.i(str, "onConnectionStateChange >> device : " + BluetoothUtil.printBtDeviceInfo(device) + ", status : " + i + ", newState : " + i2);
            if (i2 == 0 || i2 == 3 || i2 == 2) {
                BleManager.this.stopConnectTimeout();
                BleManager.this.setConnectingBtDevice(null);
                if (i2 != 2) {
                    BleManager.this.setConnectedBtDevice(null);
                    BleManager.this.setConnectedBtGatt(null);
                    bluetoothGatt.close();
                    BleManager.this.stopSendDataThread();
                    if (BleManager.this.isReConnectDevice(device)) {
                        BleManager.this.mHandler.removeMessages(BleManager.MSG_RECONNECT_BLE);
                        BleManager.this.mHandler.sendMessageDelayed(BleManager.this.mHandler.obtainMessage(BleManager.MSG_RECONNECT_BLE, device), 2000L);
                        return;
                    }
                } else {
                    boolean discoverServices = bluetoothGatt.discoverServices();
                    String str2 = BleManager.TAG;
                    JL_Log.d(str2, "onConnectionStateChange >> discoverServices : " + discoverServices);
                    BleManager.this.setConnectedBtDevice(device);
                    if (discoverServices) {
                        BleManager.this.startSendDataThread();
                        BleManager.this.mHandler.removeMessages(BleManager.MSG_BLE_DISCOVER_SERVICES_CALLBACK_TIMEOUT);
                        BleManager.this.mHandler.sendMessageDelayed(BleManager.this.mHandler.obtainMessage(BleManager.MSG_BLE_DISCOVER_SERVICES_CALLBACK_TIMEOUT, device), 3000L);
                        return;
                    }
                    BleManager.this.disconnectBleDevice(device);
                    return;
                }
            }
            BleManager.this.handleBleConnection(device, i2);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            BluetoothDevice device;
            if (bluetoothGatt == null || (device = bluetoothGatt.getDevice()) == null) {
                return;
            }
            BleManager.this.mHandler.removeMessages(BleManager.MSG_BLE_DISCOVER_SERVICES_CALLBACK_TIMEOUT);
            BleManager.this.mCallbackManager.onBleServiceDiscovery(device, i, bluetoothGatt.getServices());
            boolean z = false;
            if (i == 0) {
                BluetoothUtil.printBleGattServices(device, bluetoothGatt, i);
                Iterator<BluetoothGattService> it = bluetoothGatt.getServices().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    BluetoothGattService next = it.next();
                    if (BleManager.BLE_UUID_SERVICE.equals(next.getUuid()) && next.getCharacteristic(BleManager.BLE_UUID_WRITE) != null && next.getCharacteristic(BleManager.BLE_UUID_NOTIFICATION) != null) {
                        JL_Log.i(BleManager.TAG, "start NotifyCharacteristicRunnable...");
                        BleManager bleManager = BleManager.this;
                        bleManager.mNotifyCharacteristicRunnable = new NotifyCharacteristicRunnable(bluetoothGatt, BleManager.BLE_UUID_SERVICE, BleManager.BLE_UUID_NOTIFICATION);
                        BleManager.this.mHandler.post(BleManager.this.mNotifyCharacteristicRunnable);
                        z = true;
                        break;
                    }
                }
            }
            String str = BleManager.TAG;
            JL_Log.i(str, "onServicesDiscovered : " + z);
            if (z) {
                return;
            }
            BleManager.this.disconnectBleDevice(device);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            BluetoothDevice device;
            if (bluetoothGatt == null || (device = bluetoothGatt.getDevice()) == null || bluetoothGattCharacteristic == null) {
                return;
            }
            UUID uuid = bluetoothGattCharacteristic.getUuid();
            byte[] value = bluetoothGattCharacteristic.getValue();
            BluetoothGattService service = bluetoothGattCharacteristic.getService();
            UUID uuid2 = service != null ? service.getUuid() : null;
            String str = BleManager.TAG;
            JL_Log.d(str, "onCharacteristicChanged >> deice : " + BluetoothUtil.printBtDeviceInfo(device) + ", serviceUuid : " + uuid2 + ", characteristicUuid : " + uuid + ",\n data : " + CHexConver.byte2HexStr(value));
            BleManager.this.mCallbackManager.onBleDataNotification(device, uuid2, uuid, value);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            BluetoothDevice device;
            super.onCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic, i);
            if (bluetoothGatt == null || (device = bluetoothGatt.getDevice()) == null || bluetoothGattCharacteristic == null) {
                return;
            }
            UUID uuid = bluetoothGattCharacteristic.getUuid();
            BluetoothGattService service = bluetoothGattCharacteristic.getService();
            UUID uuid2 = service != null ? service.getUuid() : null;
            String str = BleManager.TAG;
            JL_Log.d(str, "onCharacteristicWrite : status : " + i);
            BleManager.this.wakeupSendThread(bluetoothGatt, uuid2, uuid, i, bluetoothGattCharacteristic.getValue());
            BleManager.this.mCallbackManager.onBleWriteStatus(device, uuid2, uuid, bluetoothGattCharacteristic.getValue(), i);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            BluetoothDevice device;
            UUID uuid;
            UUID uuid2;
            JL_Log.e(BleManager.TAG, "onDescriptorWrite : gatt : " + bluetoothGatt + ", descriptor : " + bluetoothGattDescriptor + ", status : " + i);
            if (bluetoothGatt == null || (device = bluetoothGatt.getDevice()) == null || bluetoothGattDescriptor == null) {
                return;
            }
            BluetoothGattCharacteristic characteristic = bluetoothGattDescriptor.getCharacteristic();
            if (characteristic != null) {
                uuid2 = characteristic.getUuid();
                BluetoothGattService service = characteristic.getService();
                uuid = service != null ? service.getUuid() : null;
            } else {
                uuid = null;
                uuid2 = null;
            }
            BleManager.this.mCallbackManager.onBleNotificationStatus(device, uuid, uuid2, i);
            if (BleManager.this.mNotifyCharacteristicRunnable == null || !BluetoothUtil.deviceEquals(device, BleManager.this.mNotifyCharacteristicRunnable.getBleDevice()) || uuid == null || !uuid.equals(BleManager.this.mNotifyCharacteristicRunnable.getServiceUUID()) || uuid2 == null || !uuid2.equals(BleManager.this.mNotifyCharacteristicRunnable.getCharacteristicUUID())) {
                return;
            }
            if (i == 0) {
                BleManager.this.mNotifyCharacteristicRunnable = null;
                BleManager.this.startChangeMtu(bluetoothGatt, 509);
                return;
            }
            int retryNum = BleManager.this.mNotifyCharacteristicRunnable.getRetryNum();
            if (retryNum >= 3) {
                BleManager.this.disconnectBleDevice(device);
                return;
            }
            BleManager.this.mNotifyCharacteristicRunnable.setRetryNum(retryNum + 1);
            BleManager.this.mHandler.postDelayed(BleManager.this.mNotifyCharacteristicRunnable, 100L);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onMtuChanged(BluetoothGatt bluetoothGatt, int i, int i2) {
            BluetoothDevice device;
            if (bluetoothGatt == null || (device = bluetoothGatt.getDevice()) == null) {
                return;
            }
            if (i2 == 0) {
                BleManager.this.mBleMtu = i - 3;
            }
            String str = BleManager.TAG;
            JL_Log.d(str, "onMtuChanged - status = " + i2 + ", mtu = " + i + ",\ndevice: " + BluetoothUtil.printBtDeviceInfo(device));
            BleManager.this.mCallbackManager.onBleDataBlockChanged(device, i, i2);
            if (BleManager.this.mHandler.hasMessages(BleManager.MSG_CHANGE_BLE_MTU_TIMEOUT)) {
                BleManager.this.stopChangeMtu();
                JL_Log.i(BleManager.TAG, "-onMtuChanged- handleBleConnectedEvent");
                BleManager.this.handleBleConnectedEvent(device);
            }
        }
    };

    private BleManager(Context context) {
        this.mContext = (Context) CommonUtil.checkNotNull(context);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothAdapter = defaultAdapter;
        if (Build.VERSION.SDK_INT >= 21) {
            this.mBluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();
        }
        registerReceiver();
    }

    public static BleManager getInstance() {
        if (instance == null) {
            synchronized (BleManager.class) {
                if (instance == null) {
                    instance = new BleManager(MainApplication.getInstance());
                }
            }
        }
        return instance;
    }

    public static List<BluetoothDevice> getConnectedBleDeviceList(Context context) {
        BluetoothManager bluetoothManager;
        if (context == null || (bluetoothManager = (BluetoothManager) context.getSystemService("bluetooth")) == null) {
            return null;
        }
        return bluetoothManager.getConnectedDevices(7);
    }

    public void destroy() {
        JL_Log.w(TAG, ">>>>>>>>>>>>>>destroy >>>>>>>>>>>>>>> ");
        if (isBleScanning()) {
            stopLeScan();
        }
        disconnectBleDevice(getConnectedBtDevice());
        unregisterReceiver();
        stopConnectTimeout();
        stopSendDataThread();
        isBleScanning(false);
        this.mDiscoveredBleDevices.clear();
        this.mCallbackManager.release();
        this.mHandler.removeCallbacksAndMessages(null);
        instance = null;
    }

    public void registerBleEventCallback(BleEventCallback bleEventCallback) {
        this.mCallbackManager.registerBleEventCallback(bleEventCallback);
    }

    public void unregisterBleEventCallback(BleEventCallback bleEventCallback) {
        this.mCallbackManager.unregisterBleEventCallback(bleEventCallback);
    }

    public boolean isBluetoothEnable() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    public boolean enableBluetooth() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            return false;
        }
        boolean isEnabled = bluetoothAdapter.isEnabled();
        return !isEnabled ? this.mBluetoothAdapter.enable() : isEnabled;
    }

    public boolean disableBluetooth() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            return false;
        }
        boolean z = !bluetoothAdapter.isEnabled();
        return !z ? this.mBluetoothAdapter.disable() : z;
    }

    public boolean isBleScanning() {
        return this.isBleScanning;
    }

    public boolean startLeScan(long j) {
        BluetoothLeScanner bluetoothLeScanner;
        if (this.mBluetoothAdapter != null && isBluetoothEnable()) {
            if (j <= 0) {
                j = 8000;
            }
            boolean z = true;
            if (this.isBleScanning) {
                JL_Log.i(TAG, "scanning ble .....");
                BluetoothLeScanner bluetoothLeScanner2 = this.mBluetoothLeScanner;
                if (bluetoothLeScanner2 != null) {
                    bluetoothLeScanner2.flushPendingScanResults(this.mScanCallback);
                }
                this.mDiscoveredBleDevices.clear();
                this.mHandler.removeMessages(4112);
                this.mHandler.sendEmptyMessageDelayed(4112, j);
                syncSystemBleDevice();
                return true;
            } else if (!AppUtil.isHasLocationPermission(this.mContext)) {
                JL_Log.i(TAG, "App does not have location permission.");
                return false;
            } else {
                if (Build.VERSION.SDK_INT >= 21 && (bluetoothLeScanner = this.mBluetoothLeScanner) != null) {
                    bluetoothLeScanner.startScan(this.mScanCallback);
                } else {
                    z = this.mBluetoothAdapter.startLeScan(this.mLeScanCallback);
                }
                isBleScanning(z);
                if (z) {
                    this.mDiscoveredBleDevices.clear();
                    this.mHandler.removeMessages(4112);
                    this.mHandler.sendEmptyMessageDelayed(4112, j);
                    syncSystemBleDevice();
                }
                return z;
            }
        }
        return false;
    }

    public void stopLeScan() {
        BluetoothLeScanner bluetoothLeScanner;
        if (this.mBluetoothAdapter == null || !isBluetoothEnable()) {
            return;
        }
        try {
            if (Build.VERSION.SDK_INT >= 21 && (bluetoothLeScanner = this.mBluetoothLeScanner) != null) {
                bluetoothLeScanner.stopScan(this.mScanCallback);
            } else {
                this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mHandler.removeMessages(4112);
        this.mHandler.removeMessages(4114);
        isBleScanning(false);
    }

    public BluetoothDevice getConnectedBtDevice() {
        return this.mConnectedBtDevice;
    }

    public BluetoothGatt getConnectedBtGatt() {
        return this.mConnectedBtGatt;
    }

    public void setReconnectDevAddr(String str) {
        List<BluetoothDevice> systemConnectedBtDeviceList;
        this.mReconnectDevAddr = str;
        if (str == null) {
            this.mHandler.removeMessages(MSG_RECONNECT_BLE);
        }
        if (isBluetoothEnable() && !isBleScanning()) {
            startLeScan(12000L);
        }
        if (PreferencesHelper.getSharedPreferences(MainApplication.getInstance().getApplicationContext()).getBoolean(JL_Constant.KEY_IS_HID_DEVICE, false) && BluetoothAdapter.checkBluetoothAddress(str) && (systemConnectedBtDeviceList = BluetoothUtil.getSystemConnectedBtDeviceList()) != null) {
            for (BluetoothDevice bluetoothDevice : systemConnectedBtDeviceList) {
                if (reconnectDevice(bluetoothDevice)) {
                    JL_Log.i(TAG, "reconnect device start. 22222 ");
                    return;
                }
            }
        }
    }

    @Override // com.jieli.otasdk.tool.ota.ble.interfaces.IBleOp
    public int getBleMtu() {
        return this.mBleMtu;
    }

    public void connectBleDevice(BluetoothDevice bluetoothDevice) {
        BluetoothGatt connectGatt;
        if (bluetoothDevice == null) {
            return;
        }
        if (this.mConnectedBtDevice != null) {
            JL_Log.e(TAG, "BleDevice is connected, please call disconnectBleDevice method at first.");
            setReconnectDevAddr(null);
        } else if (this.mConnectingBtDevice != null) {
            JL_Log.e(TAG, "BleDevice is connecting, please wait.");
        } else {
            if (isBleScanning()) {
                stopLeScan();
            }
            if (Build.VERSION.SDK_INT >= 23) {
                connectGatt = bluetoothDevice.connectGatt(this.mContext, false, this.mBluetoothGattCallback, 2);
            } else {
                connectGatt = bluetoothDevice.connectGatt(this.mContext, false, this.mBluetoothGattCallback);
            }
            if (connectGatt != null) {
                setConnectedBtGatt(connectGatt);
                setConnectingBtDevice(bluetoothDevice);
                handleBleConnection(bluetoothDevice, 1);
                startConnectTimeout();
                JL_Log.d(TAG, "connect start....");
            }
        }
    }

    public void disconnectBleDevice(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice != null && BluetoothUtil.deviceEquals(bluetoothDevice, this.mConnectedBtDevice)) {
            synchronized (this) {
                if (this.mConnectedBtGatt != null) {
                    this.mConnectedBtGatt.disconnect();
                    this.mConnectedBtGatt.close();
                }
                setConnectedBtGatt(null);
            }
            setConnectedBtDevice(null);
            handleBleConnection(bluetoothDevice, 0);
        }
    }

    @Override // com.jieli.otasdk.tool.ota.ble.interfaces.IBleOp
    public boolean writeDataByBle(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2, byte[] bArr) {
        boolean z = false;
        if (bluetoothGatt == null || uuid == null || uuid2 == null || bArr == null || bArr.length == 0) {
            JL_Log.d(TAG, "writeDataByBle : 1111111");
            return false;
        }
        BluetoothGattService service = bluetoothGatt.getService(uuid);
        if (service == null) {
            JL_Log.d(TAG, "writeDataByBle : 22222");
            return false;
        }
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(uuid2);
        if (characteristic == null) {
            JL_Log.d(TAG, "writeDataByBle : 3333");
            return false;
        }
        try {
            characteristic.setValue(bArr);
            z = bluetoothGatt.writeCharacteristic(characteristic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = TAG;
        JL_Log.d(str, "writeDataByBle : " + CHexConver.byte2HexStr(bArr) + ", ret : " + z);
        return z;
    }

    public void writeDataByBleAsync(BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, byte[] bArr, OnWriteDataCallback onWriteDataCallback) {
        addSendTask(bluetoothDevice, uuid, uuid2, bArr, onWriteDataCallback);
    }

    private void isBleScanning(boolean z) {
        this.isBleScanning = z;
        this.mCallbackManager.onDiscoveryBleChange(z);
        if (this.isBleScanning && PreferencesHelper.getSharedPreferences(MainApplication.getInstance().getApplicationContext()).getBoolean(JL_Constant.KEY_IS_HID_DEVICE, false)) {
            this.mHandler.sendEmptyMessage(4114);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setConnectingBtDevice(BluetoothDevice bluetoothDevice) {
        this.mConnectingBtDevice = bluetoothDevice;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setConnectedBtDevice(BluetoothDevice bluetoothDevice) {
        this.mConnectedBtDevice = bluetoothDevice;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setConnectedBtGatt(BluetoothGatt bluetoothGatt) {
        synchronized (this) {
            this.mConnectedBtGatt = bluetoothGatt;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isReConnectDevice(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice != null && BluetoothAdapter.checkBluetoothAddress(this.mReconnectDevAddr) && this.mReconnectDevAddr.equals(bluetoothDevice.getAddress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean reconnectDevice(BluetoothDevice bluetoothDevice) {
        if (isReConnectDevice(bluetoothDevice) && this.mConnectingBtDevice == null) {
            JL_Log.i(TAG, "reconnect device start.");
            connectBleDevice(bluetoothDevice);
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void filterDevice(BluetoothDevice bluetoothDevice, int i, byte[] bArr, boolean z) {
        if (!isBluetoothEnable() || TextUtils.isEmpty(bluetoothDevice.getName()) || this.mDiscoveredBleDevices.contains(bluetoothDevice)) {
            return;
        }
        String str = TAG;
        JL_Log.d(str, "notify device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice));
        this.mDiscoveredBleDevices.add(bluetoothDevice);
        handleDiscoveryBle(bluetoothDevice, new BleScanInfo().setRawData(bArr).setRssi(i).setEnableConnect(z));
    }

    private void startConnectTimeout() {
        if (this.mHandler.hasMessages(4113)) {
            return;
        }
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(4113, this.mConnectingBtDevice), 40000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopConnectTimeout() {
        if (this.mHandler.hasMessages(4113)) {
            this.mHandler.removeMessages(4113);
        }
    }

    private void syncSystemBleDevice() {
        List<BluetoothDevice> connectedBleDeviceList = getConnectedBleDeviceList(this.mContext);
        if (connectedBleDeviceList == null || connectedBleDeviceList.isEmpty()) {
            return;
        }
        for (BluetoothDevice bluetoothDevice : connectedBleDeviceList) {
            if (!BluetoothUtil.deviceEquals(bluetoothDevice, this.mConnectedBtDevice) && !this.mDiscoveredBleDevices.contains(bluetoothDevice)) {
                this.mDiscoveredBleDevices.add(bluetoothDevice);
                handleDiscoveryBle(bluetoothDevice, new BleScanInfo().setEnableConnect(true));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSendDataThread() {
        if (this.mSendBleDataThread == null) {
            SendBleDataThread sendBleDataThread = new SendBleDataThread(this, new OnThreadStateListener() { // from class: com.jieli.otasdk.tool.ota.ble.BleManager.2
                @Override // com.jieli.otasdk.tool.ota.ble.interfaces.OnThreadStateListener
                public void onStart(long j, String str) {
                }

                @Override // com.jieli.otasdk.tool.ota.ble.interfaces.OnThreadStateListener
                public void onEnd(long j, String str) {
                    BleManager.this.mSendBleDataThread = null;
                }
            });
            this.mSendBleDataThread = sendBleDataThread;
            sendBleDataThread.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopSendDataThread() {
        SendBleDataThread sendBleDataThread = this.mSendBleDataThread;
        if (sendBleDataThread != null) {
            sendBleDataThread.stopThread();
            this.mSendBleDataThread = null;
        }
    }

    private void addSendTask(BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, byte[] bArr, OnWriteDataCallback onWriteDataCallback) {
        if ((this.mSendBleDataThread == null || this.mConnectedBtGatt == null || !BluetoothUtil.deviceEquals(bluetoothDevice, this.mConnectedBtGatt.getDevice())) ? false : this.mSendBleDataThread.addSendTask(getConnectedBtGatt(), uuid, uuid2, bArr, onWriteDataCallback)) {
            return;
        }
        onWriteDataCallback.onBleResult(bluetoothDevice, uuid, uuid2, false, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wakeupSendThread(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2, int i, byte[] bArr) {
        if (this.mSendBleDataThread != null) {
            SendBleDataThread.BleSendTask bleSendTask = new SendBleDataThread.BleSendTask(bluetoothGatt, uuid, uuid2, bArr, null);
            bleSendTask.setStatus(i);
            this.mSendBleDataThread.wakeupSendThread(bleSendTask);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDiscoveryBle(BluetoothDevice bluetoothDevice, BleScanInfo bleScanInfo) {
        if (!PreferencesHelper.getSharedPreferences(MainApplication.getInstance().getApplicationContext()).getBoolean(JL_Constant.KEY_IS_HID_DEVICE, false) && PreferencesHelper.getSharedPreferences(MainApplication.getInstance().getApplicationContext()).getBoolean(JL_Constant.KEY_USE_CUSTOM_RECONNECT_WAY, false) && reconnectDevice(bluetoothDevice)) {
            JL_Log.i(TAG, "reconnect device start...3333");
        }
        this.mCallbackManager.onDiscoveryBle(bluetoothDevice, bleScanInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBleConnection(BluetoothDevice bluetoothDevice, int i) {
        if (i == 0 || i == 2) {
            this.mHandler.removeMessages(4115);
            if (isReConnectDevice(bluetoothDevice)) {
                setReconnectDevAddr(null);
            }
        }
        String str = TAG;
        JL_Log.i(str, "handleBleConnection >> device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice) + ", status : " + i);
        this.mCallbackManager.onBleConnection(bluetoothDevice, i);
    }

    private void registerReceiver() {
        if (this.mAdapterReceiver == null) {
            this.mAdapterReceiver = new BaseBtAdapterReceiver();
            IntentFilter intentFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
            intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
            this.mContext.registerReceiver(this.mAdapterReceiver, intentFilter);
        }
    }

    private void unregisterReceiver() {
        BaseBtAdapterReceiver baseBtAdapterReceiver = this.mAdapterReceiver;
        if (baseBtAdapterReceiver != null) {
            this.mContext.unregisterReceiver(baseBtAdapterReceiver);
            this.mAdapterReceiver = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean enableBLEDeviceNotification(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2) {
        if (bluetoothGatt == null) {
            JL_Log.w(TAG, "bluetooth gatt is null....");
            return false;
        }
        BluetoothGattService service = bluetoothGatt.getService(uuid);
        if (service == null) {
            JL_Log.w(TAG, "bluetooth gatt service is null....");
            return false;
        }
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(uuid2);
        if (characteristic == null) {
            JL_Log.w(TAG, "bluetooth characteristic is null....");
            return false;
        }
        boolean characteristicNotification = bluetoothGatt.setCharacteristicNotification(characteristic, true);
        if (characteristicNotification) {
            for (BluetoothGattDescriptor bluetoothGattDescriptor : characteristic.getDescriptors()) {
                characteristicNotification = tryToWriteDescriptor(bluetoothGatt, bluetoothGattDescriptor, 0, false);
                if (!characteristicNotification) {
                    JL_Log.w(TAG, "tryToWriteDescriptor failed....");
                }
            }
        } else {
            JL_Log.w(TAG, "setCharacteristicNotification is failed....");
        }
        String str = TAG;
        JL_Log.w(str, "enableBLEDeviceNotification ret : " + characteristicNotification + ", serviceUUID : " + uuid + ", characteristicUUID : " + uuid2);
        return characteristicNotification;
    }

    private boolean tryToWriteDescriptor(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i, boolean z) {
        if (!z) {
            z = bluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            String str = TAG;
            JL_Log.i(str, "..descriptor : .setValue  ret : " + z);
            if (z) {
                i = 0;
            } else {
                i++;
                if (i >= 3) {
                    return false;
                }
                JL_Log.i(str, "-tryToWriteDescriptor- : retryCount : " + i + ", isSkipSetValue :  false");
                SystemClock.sleep(50L);
                tryToWriteDescriptor(bluetoothGatt, bluetoothGattDescriptor, i, false);
            }
        }
        if (z) {
            z = bluetoothGatt.writeDescriptor(bluetoothGattDescriptor);
            String str2 = TAG;
            JL_Log.i(str2, "..bluetoothGatt : .writeDescriptor  ret : " + z);
            if (!z) {
                int i2 = i + 1;
                if (i2 >= 3) {
                    return false;
                }
                JL_Log.i(str2, "-tryToWriteDescriptor- 2222 : retryCount : " + i2 + ", isSkipSetValue :  true");
                SystemClock.sleep(50L);
                tryToWriteDescriptor(bluetoothGatt, bluetoothGattDescriptor, i2, true);
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startChangeMtu(BluetoothGatt bluetoothGatt, int i) {
        BluetoothDevice device;
        if (bluetoothGatt == null || (device = bluetoothGatt.getDevice()) == null) {
            return;
        }
        if (this.mHandler.hasMessages(MSG_CHANGE_BLE_MTU_TIMEOUT)) {
            JL_Log.w(TAG, "-startChangeMtu- Adjusting the MTU for BLE");
            return;
        }
        boolean z = false;
        if (i > 20) {
            z = Build.VERSION.SDK_INT >= 21 ? bluetoothGatt.requestMtu(i + 3) : true;
        }
        if (z) {
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(MSG_CHANGE_BLE_MTU_TIMEOUT, device), DELAY_WAITING_TIME);
            return;
        }
        handleBleConnectedEvent(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopChangeMtu() {
        this.mHandler.removeMessages(MSG_CHANGE_BLE_MTU_TIMEOUT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBleConnectedEvent(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            JL_Log.e(TAG, "-handleBleConnectedEvent- device is null.");
            return;
        }
        stopChangeMtu();
        handleBleConnection(bluetoothDevice, 2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-jieli-otasdk-tool-ota-ble-BleManager  reason: not valid java name */
    public /* synthetic */ void m132lambda$new$0$comjieliotasdktoolotableBleManager(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        filterDevice(bluetoothDevice, i, bArr, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class BaseBtAdapterReceiver extends BroadcastReceiver {
        private BaseBtAdapterReceiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action;
            char c;
            if (intent == null || (action = intent.getAction()) == null) {
                return;
            }
            action.hashCode();
            switch (action.hashCode()) {
                case -1530327060:
                    if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -301431627:
                    if (action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1821585647:
                    if (action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
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
                    int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", -1);
                    if (BleManager.this.mBluetoothAdapter != null && intExtra == -1) {
                        intExtra = BleManager.this.mBluetoothAdapter.getState();
                    }
                    if (intExtra == 10) {
                        BleManager.this.mCallbackManager.onAdapterChange(false);
                        return;
                    } else if (intExtra == 12) {
                        BleManager.this.mCallbackManager.onAdapterChange(true);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    String str = BleManager.TAG;
                    JL_Log.i(str, "BaseBtAdapterReceiver: ACTION_ACL_CONNECTED, device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice));
                    if (BleManager.this.reconnectDevice(bluetoothDevice)) {
                        JL_Log.i(BleManager.TAG, "reconnectDevice start...1111");
                        return;
                    }
                    return;
                case 2:
                    String str2 = BleManager.TAG;
                    JL_Log.i(str2, "BaseBtAdapterReceiver: ACTION_ACL_DISCONNECTED, device : " + BluetoothUtil.printBtDeviceInfo((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")));
                    return;
                default:
                    return;
            }
        }
    }

    /* loaded from: classes2.dex */
    private class NotifyCharacteristicRunnable implements Runnable {
        private final UUID mCharacteristicUUID;
        private final BluetoothGatt mGatt;
        private final UUID mServiceUUID;
        private int retryNum;

        private NotifyCharacteristicRunnable(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2) {
            this.retryNum = 0;
            this.mGatt = bluetoothGatt;
            this.mServiceUUID = uuid;
            this.mCharacteristicUUID = uuid2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRetryNum(int i) {
            this.retryNum = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getRetryNum() {
            return this.retryNum;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public BluetoothDevice getBleDevice() {
            BluetoothGatt bluetoothGatt = this.mGatt;
            if (bluetoothGatt == null) {
                return null;
            }
            return bluetoothGatt.getDevice();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public UUID getServiceUUID() {
            return this.mServiceUUID;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public UUID getCharacteristicUUID() {
            return this.mCharacteristicUUID;
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean enableBLEDeviceNotification = BleManager.this.enableBLEDeviceNotification(this.mGatt, this.mServiceUUID, this.mCharacteristicUUID);
            String str = BleManager.TAG;
            JL_Log.w(str, "enableBLEDeviceNotification ===> " + enableBLEDeviceNotification);
            if (enableBLEDeviceNotification) {
                BleManager.this.mHandler.sendEmptyMessageDelayed(4115, 3000L);
                return;
            }
            BluetoothGatt bluetoothGatt = this.mGatt;
            if (bluetoothGatt != null) {
                BleManager.this.disconnectBleDevice(bluetoothGatt.getDevice());
            }
        }
    }
}
