package com.jieli.jl_bt_ota.impl;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.constant.ErrorCode;
import com.jieli.jl_bt_ota.impl.RcspAuth;
import com.jieli.jl_bt_ota.interfaces.CommandCallback;
import com.jieli.jl_bt_ota.interfaces.IActionCallback;
import com.jieli.jl_bt_ota.interfaces.IUpgradeCallback;
import com.jieli.jl_bt_ota.model.DataInfo;
import com.jieli.jl_bt_ota.model.DeviceStatus;
import com.jieli.jl_bt_ota.model.ReConnectDevMsg;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.base.CommonResponse;
import com.jieli.jl_bt_ota.model.command.EnterUpdateModeCmd;
import com.jieli.jl_bt_ota.model.command.ExitUpdateModeCmd;
import com.jieli.jl_bt_ota.model.command.FirmwareUpdateBlockCmd;
import com.jieli.jl_bt_ota.model.command.FirmwareUpdateStatusCmd;
import com.jieli.jl_bt_ota.model.command.GetDevMD5Cmd;
import com.jieli.jl_bt_ota.model.command.GetTargetInfoCmd;
import com.jieli.jl_bt_ota.model.command.GetUpdateFileOffsetCmd;
import com.jieli.jl_bt_ota.model.command.InquireUpdateCmd;
import com.jieli.jl_bt_ota.model.command.NotifyCommunicationWayCmd;
import com.jieli.jl_bt_ota.model.command.NotifyUpdateContentSizeCmd;
import com.jieli.jl_bt_ota.model.command.RebootDeviceCmd;
import com.jieli.jl_bt_ota.model.command.SettingsMtuCmd;
import com.jieli.jl_bt_ota.model.parameter.FirmwareUpdateBlockParam;
import com.jieli.jl_bt_ota.model.parameter.FirmwareUpdateBlockResponseParam;
import com.jieli.jl_bt_ota.model.parameter.InquireUpdateParam;
import com.jieli.jl_bt_ota.model.parameter.NotifyCommunicationWayParam;
import com.jieli.jl_bt_ota.model.parameter.NotifyUpdateContentSizeParam;
import com.jieli.jl_bt_ota.model.parameter.RebootDeviceParam;
import com.jieli.jl_bt_ota.model.response.EnterUpdateModeResponse;
import com.jieli.jl_bt_ota.model.response.ExitUpdateModeResponse;
import com.jieli.jl_bt_ota.model.response.FirmwareUpdateStatusResponse;
import com.jieli.jl_bt_ota.model.response.GetDevMD5Response;
import com.jieli.jl_bt_ota.model.response.InquireUpdateResponse;
import com.jieli.jl_bt_ota.model.response.SettingsMtuResponse;
import com.jieli.jl_bt_ota.model.response.TargetInfoResponse;
import com.jieli.jl_bt_ota.model.response.UpdateFileOffsetResponse;
import com.jieli.jl_bt_ota.thread.ReadFileThread;
import com.jieli.jl_bt_ota.tool.CommandHelper;
import com.jieli.jl_bt_ota.tool.DataHandler;
import com.jieli.jl_bt_ota.tool.DeviceReConnectManager;
import com.jieli.jl_bt_ota.tool.DeviceStatusManager;
import com.jieli.jl_bt_ota.tool.ParseHelper;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.CommandBuilder;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.FileUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
/* loaded from: classes2.dex */
public abstract class BluetoothOTAManager extends BluetoothBreProfiles implements RcspAuth.IRcspAuthOp {
    private static final long BLE_RECONNECT_DELAY = 3000;
    private static final long DEFAULT_DELAY = 500;
    private static final long DELAY_WAITING_TIME = 5000;
    private static final int MSG_CHANGE_BLE_MTU_TIMEOUT = 4661;
    private static final int MSG_CHANGE_SPP_TIMEOUT = 4662;
    private static final int MSG_OTA_RECONNECT_DEVICE = 4663;
    private static final int MSG_OTA_RECONNECT_DEVICE_TIMEOUT = 4664;
    private static final int MSG_START_RECEIVE_CMD_TIMEOUT = 4660;
    private static volatile boolean isOTA = false;
    private static volatile IUpgradeCallback mUpgradeCallback;
    private static volatile byte[] mUpgradeDataBuf;
    private static volatile WaitDeviceReConnect mWaitDeviceReConnect;
    protected volatile int mBleMtu;
    private volatile BluetoothDevice mChangeTargetDevice;
    private final CommandCallback mCommandCallback;
    private volatile BluetoothDevice mConnectedBtDevice;
    private int mCurrentSumFileSize;
    private final DeviceReConnectManager mDeviceReConnectManager;
    private final DeviceStatusManager mDeviceStatusManager;
    private final Handler mHandler;
    private final RcspAuth.OnRcspAuthListener mOnRcspAuthListener;
    private final RcspAuth mRcspAuth;
    private volatile BluetoothDevice mReConnectDevice;
    private final IActionCallback<byte[]> mReadFileCallback;
    private long mStartTime;
    private long mTotalTime;
    private int mUpdateContentSize;
    private long timeout_ms;

    public BluetoothOTAManager(Context context) {
        super(context);
        this.timeout_ms = 20000L;
        this.mStartTime = 0L;
        this.mTotalTime = 0L;
        this.mUpdateContentSize = 0;
        this.mCurrentSumFileSize = 0;
        this.mBleMtu = 20;
        this.mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case BluetoothOTAManager.MSG_START_RECEIVE_CMD_TIMEOUT /* 4660 */:
                        BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_SEND_TIMEOUT, "receive cmd timeout."));
                        break;
                    case BluetoothOTAManager.MSG_CHANGE_BLE_MTU_TIMEOUT /* 4661 */:
                        BluetoothDevice bluetoothDevice = (BluetoothDevice) message.obj;
                        String str = BluetoothOTAManager.this.TAG;
                        JL_Log.i(str, "-MSG_CHANGE_BLE_MTU_TIMEOUT- request mtu timeout, device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice));
                        if (BluetoothOTAManager.this.isConnectedBLEDevice(bluetoothDevice)) {
                            BluetoothOTAManager.this.handleBleConnectedEvent(bluetoothDevice);
                            break;
                        } else {
                            BluetoothOTAManager.this.notifyConnectionStatus(bluetoothDevice, 2);
                            break;
                        }
                    case BluetoothOTAManager.MSG_CHANGE_SPP_TIMEOUT /* 4662 */:
                        if (BluetoothOTAManager.this.mChangeTargetDevice != null) {
                            String str2 = BluetoothOTAManager.this.TAG;
                            JL_Log.i(str2, "-MSG_CHANGE_SPP_TIMEOUT- change spp timeout, device : " + BluetoothUtil.printBtDeviceInfo(BluetoothOTAManager.this.mChangeTargetDevice));
                            BluetoothOTAManager bluetoothOTAManager = BluetoothOTAManager.this;
                            if (bluetoothOTAManager.isConnectedSppDevice(bluetoothOTAManager.mChangeTargetDevice)) {
                                BluetoothOTAManager bluetoothOTAManager2 = BluetoothOTAManager.this;
                                bluetoothOTAManager2.notifyConnectionStatus(bluetoothOTAManager2.mChangeTargetDevice, 1);
                            } else {
                                BluetoothOTAManager bluetoothOTAManager3 = BluetoothOTAManager.this;
                                bluetoothOTAManager3.notifyConnectionStatus(bluetoothOTAManager3.mChangeTargetDevice, 2);
                            }
                            BluetoothOTAManager.this.setChangeTargetDevice(null);
                            break;
                        }
                        break;
                    case BluetoothOTAManager.MSG_OTA_RECONNECT_DEVICE /* 4663 */:
                        if (BluetoothOTAManager.this.mDeviceReConnectManager.isWaitingForUpdate()) {
                            BluetoothOTAManager bluetoothOTAManager4 = BluetoothOTAManager.this;
                            bluetoothOTAManager4.callbackReconnectEvent(bluetoothOTAManager4.mDeviceReConnectManager.getReconnectAddress());
                            if (!BluetoothOTAManager.this.getBluetoothOption().isUseReconnect()) {
                                BluetoothOTAManager.this.mDeviceReConnectManager.startReconnectTask();
                                break;
                            }
                        }
                        break;
                    case BluetoothOTAManager.MSG_OTA_RECONNECT_DEVICE_TIMEOUT /* 4664 */:
                        CommandCallback commandCallback = message.obj instanceof CommandCallback ? (CommandCallback) message.obj : null;
                        JL_Log.w(BluetoothOTAManager.this.TAG, "OTAReConnectTimeout  is start.");
                        BluetoothOTAManager.this.resetOTAFlag();
                        BaseError baseError = new BaseError(4, ErrorCode.SUB_ERR_OTA_FAILED, "OTA update timeout");
                        if (commandCallback != null) {
                            commandCallback.onErrCode(baseError);
                        }
                        BluetoothOTAManager.this.callbackError(baseError);
                        BluetoothOTAManager.this.mHandler.removeMessages(BluetoothOTAManager.MSG_OTA_RECONNECT_DEVICE);
                        BluetoothOTAManager.this.mHandler.removeMessages(BluetoothOTAManager.MSG_OTA_RECONNECT_DEVICE_TIMEOUT);
                        break;
                }
                return true;
            }
        });
        RcspAuth.OnRcspAuthListener onRcspAuthListener = new RcspAuth.OnRcspAuthListener() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.22
            @Override // com.jieli.jl_bt_ota.impl.RcspAuth.OnRcspAuthListener
            public void onInitResult(boolean z) {
                String str = BluetoothOTAManager.this.TAG;
                JL_Log.e(str, "-onInitResult- " + z);
            }

            @Override // com.jieli.jl_bt_ota.impl.RcspAuth.OnRcspAuthListener
            public void onAuthSuccess(BluetoothDevice bluetoothDevice) {
                BluetoothOTAManager.this.mDeviceStatusManager.updateDeviceIsAuth(bluetoothDevice, true);
                if (BluetoothOTAManager.this.isUseBle(bluetoothDevice)) {
                    String str = BluetoothOTAManager.this.TAG;
                    JL_Log.w(str, "-onAuthSuccess- ble >>> auth ok, startChangeMtu ： " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice));
                    BluetoothOTAManager.this.startChangeMtu(bluetoothDevice);
                    return;
                }
                String str2 = BluetoothOTAManager.this.TAG;
                JL_Log.w(str2, "-onAuthSuccess- spp >>> auth ok, handlerSppConnected : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice));
                BluetoothOTAManager.this.handleSppConnected(bluetoothDevice);
            }

            @Override // com.jieli.jl_bt_ota.impl.RcspAuth.OnRcspAuthListener
            public void onAuthFailed(BluetoothDevice bluetoothDevice, int i, String str) {
                JL_Log.w(BluetoothOTAManager.this.TAG, String.format(Locale.getDefault(), "-onAuthFailed- device : %s, code : %d, message : %s", BluetoothUtil.printBtDeviceInfo(bluetoothDevice), Integer.valueOf(i), str));
                BluetoothOTAManager.this.mDeviceStatusManager.updateDeviceIsAuth(bluetoothDevice, false);
                BluetoothOTAManager.this.callbackConnectFailedAndReason(bluetoothDevice, new BaseError(5, ErrorCode.SUB_ERR_AUTH_DEVICE, "auth device failed."));
            }
        };
        this.mOnRcspAuthListener = onRcspAuthListener;
        this.mReadFileCallback = new IActionCallback<byte[]>() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.23
            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onSuccess(byte[] bArr) {
                JL_Log.i(BluetoothOTAManager.this.TAG, "-mReadFileCallback- onSuccess");
                byte[] unused = BluetoothOTAManager.mUpgradeDataBuf = bArr;
                BluetoothOTAManager.this.upgradePrepare();
            }

            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onError(BaseError baseError) {
                JL_Log.w(BluetoothOTAManager.this.TAG, "-mReadFileCallback- onError");
                BluetoothOTAManager.this.callbackError(baseError);
            }
        };
        this.mCommandCallback = new CommandCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.24
            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onCommandResponse(CommandBase commandBase) {
                int id = commandBase.getId();
                if (id == 3) {
                    GetTargetInfoCmd getTargetInfoCmd = (GetTargetInfoCmd) commandBase;
                    String str = BluetoothOTAManager.this.TAG;
                    JL_Log.w(str, "-mCommandCallback- recv ... CMD_GET_TARGET_INFO :: " + getTargetInfoCmd);
                    if (getTargetInfoCmd.getStatus() == 0) {
                        TargetInfoResponse response = getTargetInfoCmd.getResponse();
                        BluetoothDevice connectedBtDevice = BluetoothOTAManager.this.getConnectedBtDevice();
                        BluetoothOTAManager.this.mDeviceStatusManager.updateDeviceTargetInfo(connectedBtDevice, response);
                        if (BluetoothOTAManager.this.handleTargetInfoForUpgrade(connectedBtDevice, response)) {
                            return;
                        }
                        BluetoothOTAManager.this.upgradeStep03();
                        return;
                    }
                    JL_Log.i(BluetoothOTAManager.this.TAG, "-mCommandCallback- get target info response is error.");
                    BluetoothOTAManager.this.failedToUpdate("ota failed. CMD_GET_TARGET_INFO is not OK.");
                } else if (id != 11) {
                } else {
                    NotifyCommunicationWayCmd notifyCommunicationWayCmd = (NotifyCommunicationWayCmd) commandBase;
                    String str2 = BluetoothOTAManager.this.TAG;
                    JL_Log.w(str2, "-mCommandCallback- recv ... CMD_SWITCH_DEVICE_REQUEST :: " + notifyCommunicationWayCmd);
                    int status = notifyCommunicationWayCmd.getStatus();
                    if (status == 0) {
                        if (BluetoothOTAManager.this.mReConnectDevice != null) {
                            BluetoothOTAManager bluetoothOTAManager = BluetoothOTAManager.this;
                            bluetoothOTAManager.startUpgradeReConnect(bluetoothOTAManager.mReConnectDevice);
                            return;
                        }
                        JL_Log.e(BluetoothOTAManager.this.TAG, "-mCommandCallback- response is error. failedToUpdate");
                        BluetoothOTAManager.this.failedToUpdate("ota failed. reason : mReConnectDevice is null. ");
                        return;
                    }
                    JL_Log.i(BluetoothOTAManager.this.TAG, "-mCommandCallback- notify communication way response is error.");
                    BluetoothOTAManager bluetoothOTAManager2 = BluetoothOTAManager.this;
                    bluetoothOTAManager2.failedToUpdate("ota failed. reason : CMD_SWITCH_DEVICE_REQUEST is not OK. status : " + status);
                }
            }

            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onErrCode(BaseError baseError) {
                String str = BluetoothOTAManager.this.TAG;
                JL_Log.w(str, "mCommandCallback -onErrCode- " + baseError);
                BluetoothOTAManager bluetoothOTAManager = BluetoothOTAManager.this;
                StringBuilder sb = new StringBuilder();
                sb.append("ota failed. reason : ");
                sb.append(baseError != null ? baseError.getMessage() : "");
                bluetoothOTAManager.failedToUpdate(sb.toString());
            }
        };
        this.mDeviceStatusManager = DeviceStatusManager.getInstance();
        this.mDeviceReConnectManager = DeviceReConnectManager.getInstance(this);
        this.mRcspAuth = new RcspAuth(this, onRcspAuthListener);
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothDiscovery, com.jieli.jl_bt_ota.impl.BluetoothBase
    public void onAdapterStatus(boolean z, boolean z2) {
        super.onAdapterStatus(z, z2);
        if (z) {
            return;
        }
        stopReConnectManagerOp();
        callbackError(new BaseError(4099, "bluetooth is closed."));
        BluetoothDevice connectedBtDevice = getConnectedBtDevice();
        if (connectedBtDevice != null) {
            notifyConnectionStatus(connectedBtDevice, 0);
        }
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothBase
    public void onA2dpStatus(BluetoothDevice bluetoothDevice, int i) {
        super.onA2dpStatus(bluetoothDevice, i);
        handleBrEdrProfileStatus(bluetoothDevice, 2, i);
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothBase
    public void onHfpStatus(BluetoothDevice bluetoothDevice, int i) {
        super.onHfpStatus(bluetoothDevice, i);
        handleBrEdrProfileStatus(bluetoothDevice, 1, i);
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void onMtuChanged(BluetoothGatt bluetoothGatt, int i, int i2) {
        if (bluetoothGatt == null) {
            return;
        }
        if (i2 == 0) {
            this.mBleMtu = i - 3;
        }
        String str = this.TAG;
        JL_Log.e(str, "--onMtuChanged-- mBleMtu : " + this.mBleMtu);
        onBleDataBlockChanged(bluetoothGatt.getDevice(), this.mBleMtu, i2);
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothBase
    public void onBleDataBlockChanged(BluetoothDevice bluetoothDevice, int i, int i2) {
        super.onBleDataBlockChanged(bluetoothDevice, i, i2);
        JL_Log.i(this.TAG, String.format(Locale.getDefault(), "-onBleDataBlockChanged- device : %s, block : %d, status : %d", BluetoothUtil.printBtDeviceInfo(bluetoothDevice), Integer.valueOf(i), Integer.valueOf(i2)));
        if (this.mHandler.hasMessages(MSG_CHANGE_BLE_MTU_TIMEOUT)) {
            stopChangeMtu();
            JL_Log.i(this.TAG, "-onBleDataBlockChanged- handlerBleConnectedEvent");
            handleBleConnectedEvent(bluetoothDevice);
        }
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothBase, com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void onBtDeviceConnection(BluetoothDevice bluetoothDevice, int i) {
        super.onBtDeviceConnection(bluetoothDevice, i);
        if (i != 1) {
            if (BluetoothUtil.deviceEquals(bluetoothDevice, getConnectedBtDevice())) {
                setConnectedBtDevice(null);
            }
            notifyConnectionStatus(bluetoothDevice, i);
            return;
        }
        stopReConnectManagerOp();
        if (!checkDeviceIsCertify(bluetoothDevice)) {
            this.mRcspAuth.stopAuth(bluetoothDevice, false);
            if (this.mRcspAuth.startAuth(bluetoothDevice)) {
                return;
            }
            onConnectFailed(bluetoothDevice);
        } else if (isConnectedBLEDevice(bluetoothDevice)) {
            startChangeMtu(bluetoothDevice);
        } else {
            handleSppConnected(bluetoothDevice);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0009, code lost:
        if (r8 != 2) goto L28;
     */
    @Override // com.jieli.jl_bt_ota.impl.BluetoothBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onConnection(android.bluetooth.BluetoothDevice r7, int r8) {
        /*
            r6 = this;
            r0 = 3
            if (r8 == r0) goto Lb0
            if (r8 == 0) goto L82
            r0 = 1
            if (r8 == r0) goto Ld
            r0 = 2
            if (r8 == r0) goto L82
            goto Lb0
        Ld:
            com.jieli.jl_bt_ota.tool.DeviceReConnectManager r1 = r6.mDeviceReConnectManager
            boolean r1 = r1.isWaitingForUpdate()
            com.jieli.jl_bt_ota.tool.DeviceReConnectManager r2 = r6.mDeviceReConnectManager
            boolean r2 = r2.checkIsReconnectDevice(r7)
            if (r2 != 0) goto L2d
            com.jieli.jl_bt_ota.model.BluetoothOTAConfigure r2 = r6.getBluetoothOption()
            if (r2 == 0) goto L2c
            com.jieli.jl_bt_ota.model.BluetoothOTAConfigure r2 = r6.getBluetoothOption()
            boolean r2 = r2.isUseReconnect()
            if (r2 == 0) goto L2c
            goto L2d
        L2c:
            r0 = 0
        L2d:
            com.jieli.jl_bt_ota.tool.DeviceStatusManager r2 = r6.mDeviceStatusManager
            boolean r2 = r2.isMandatoryUpgrade(r7)
            java.lang.String r3 = r6.TAG
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "-onBtDeviceConnection- ok , device:"
            r4.append(r5)
            java.lang.String r5 = com.jieli.jl_bt_ota.util.BluetoothUtil.printBtDeviceInfo(r7)
            r4.append(r5)
            java.lang.String r5 = "isWaitingForUpdate : "
            r4.append(r5)
            r4.append(r1)
            java.lang.String r5 = " , isReConnectDevice : "
            r4.append(r5)
            r4.append(r0)
            java.lang.String r0 = ", isMandatoryUpgrade : "
            r4.append(r0)
            r4.append(r2)
            java.lang.String r0 = r4.toString()
            com.jieli.jl_bt_ota.util.JL_Log.w(r3, r0)
            if (r1 == 0) goto Lb0
            if (r2 == 0) goto Lb0
            java.lang.String r0 = r6.TAG
            java.lang.String r1 = "-wait for update- continue..."
            com.jieli.jl_bt_ota.util.JL_Log.e(r0, r1)
            com.jieli.jl_bt_ota.tool.DeviceReConnectManager r0 = r6.mDeviceReConnectManager
            r1 = 0
            r0.setReConnectDevMsg(r1)
            com.jieli.jl_bt_ota.impl.BluetoothOTAManager$WaitDeviceReConnect r0 = com.jieli.jl_bt_ota.impl.BluetoothOTAManager.mWaitDeviceReConnect
            if (r0 == 0) goto L7e
            r6.wakeupWaitDeviceReConnect()
            goto Lb0
        L7e:
            r6.upgradeStep03()
            goto Lb0
        L82:
            com.jieli.jl_bt_ota.tool.DeviceReConnectManager r0 = r6.mDeviceReConnectManager
            boolean r0 = r0.isWaitingForUpdate()
            if (r0 == 0) goto L99
            android.os.Handler r0 = r6.mHandler
            r1 = 4663(0x1237, float:6.534E-42)
            r0.removeMessages(r1)
            android.os.Handler r0 = r6.mHandler
            r2 = 500(0x1f4, double:2.47E-321)
            r0.sendEmptyMessageDelayed(r1, r2)
            goto Lb0
        L99:
            boolean r0 = com.jieli.jl_bt_ota.impl.BluetoothOTAManager.isOTA
            if (r0 == 0) goto Lb0
            java.lang.String r0 = r6.TAG
            java.lang.String r1 = "-onBtDeviceConnection- failed."
            com.jieli.jl_bt_ota.util.JL_Log.w(r0, r1)
            com.jieli.jl_bt_ota.model.base.BaseError r0 = new com.jieli.jl_bt_ota.model.base.BaseError
            r1 = 16385(0x4001, float:2.296E-41)
            java.lang.String r2 = "bluetooth device not connect."
            r0.<init>(r1, r2)
            r6.callbackError(r0)
        Lb0:
            super.onConnection(r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.onConnection(android.bluetooth.BluetoothDevice, int):void");
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void onReceiveDeviceData(BluetoothDevice bluetoothDevice, byte[] bArr) {
        if (bluetoothDevice == null || bArr == null) {
            return;
        }
        String str = this.TAG;
        JL_Log.d(str, "---onReceiveData-- >>> device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice) + ", recv data : " + CHexConver.byte2HexStr(bArr));
        if (!checkDeviceIsCertify(bluetoothDevice)) {
            JL_Log.i(this.TAG, "--onReceiveDeviceData-- >>> handleAuthData ");
            this.mRcspAuth.handleAuthData(bluetoothDevice, bArr);
            return;
        }
        DataHandler.getInstance(this).addRecvData(new DataInfo().setType(1).setRecvData(bArr));
        JL_Log.d(this.TAG, "--onReceiveDeviceData-- >> addRecvData >>>> ");
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void receiveDataFromDevice(BluetoothDevice bluetoothDevice, byte[] bArr) {
        BasePacket next;
        SettingsMtuResponse response;
        ArrayList<BasePacket> findPacketData = ParseHelper.findPacketData(bArr);
        if (findPacketData != null && findPacketData.size() > 0) {
            Iterator<BasePacket> it = findPacketData.iterator();
            while (it.hasNext() && (next = it.next()) != null) {
                String str = this.TAG;
                JL_Log.i(str, "receiveDataFromDevice :: " + next);
                CommandBase convert2Command = ParseHelper.convert2Command(next);
                if (convert2Command == null) {
                    JL_Log.e(this.TAG, "receiveDataFromDevice :: command is null");
                    return;
                }
                int id = convert2Command.getId();
                if (next.getType() == 1) {
                    onReceiveData(bluetoothDevice, bArr);
                    if (id == 229) {
                        stopReceiveCmdTimeout();
                        if (isOTA) {
                            FirmwareUpdateBlockCmd firmwareUpdateBlockCmd = (FirmwareUpdateBlockCmd) convert2Command;
                            FirmwareUpdateBlockParam param = firmwareUpdateBlockCmd.getParam();
                            if (param != null) {
                                int nextUpdateBlockOffsetAddr = param.getNextUpdateBlockOffsetAddr();
                                int nextUpdateBlockLen = param.getNextUpdateBlockLen();
                                if (isStartSumProgress()) {
                                    int i = this.mCurrentSumFileSize + nextUpdateBlockLen;
                                    this.mCurrentSumFileSize = i;
                                    callbackProgress(bluetoothDevice, getCurrentProgress(i));
                                }
                                upgradeStep04(firmwareUpdateBlockCmd, nextUpdateBlockOffsetAddr, nextUpdateBlockLen);
                            } else {
                                BaseError baseError = new BaseError(ErrorCode.SUB_ERR_PARSE_DATA, "param is null.");
                                baseError.setOpCode(id);
                                callbackError(baseError);
                            }
                        } else {
                            CommandBase commandBase = (FirmwareUpdateBlockCmd) convert2Command;
                            commandBase.setStatus(1);
                            sendCommandResponse(commandBase);
                            startReceiveCmdTimeout();
                        }
                    } else if (id == 232) {
                        if (isOTA) {
                            NotifyUpdateContentSizeCmd notifyUpdateContentSizeCmd = (NotifyUpdateContentSizeCmd) convert2Command;
                            String str2 = this.TAG;
                            JL_Log.e(str2, "-receiveDataFromDevice- notifyUpdateContentSizeCmd : " + notifyUpdateContentSizeCmd.toString());
                            NotifyUpdateContentSizeParam param2 = notifyUpdateContentSizeCmd.getParam();
                            if (param2 != null && param2.getContentSize() > 0) {
                                this.mStartTime = CommonUtil.getCurrentTime();
                                this.mCurrentSumFileSize = param2.getCurrentProgress();
                                this.mUpdateContentSize = param2.getContentSize();
                                callbackProgress(bluetoothDevice, getCurrentProgress(this.mCurrentSumFileSize));
                                notifyUpdateContentSizeCmd.setStatus(0);
                                notifyUpdateContentSizeCmd.setResponse(new CommonResponse());
                                sendCommandResponse(notifyUpdateContentSizeCmd);
                            } else if (param2 != null && param2.getContentSize() == 0) {
                                resetOtaProgress();
                                JL_Log.w(this.TAG, "-receiveDataFromDevice- notifyUpdateContentSizeCmd : length is 0.");
                            } else {
                                callbackError(new BaseError(3, ErrorCode.SUB_ERR_PARSE_DATA, "param is error.").setOpCode(id));
                            }
                        } else {
                            CommandBase commandBase2 = (NotifyUpdateContentSizeCmd) convert2Command;
                            commandBase2.setStatus(1);
                            sendCommandResponse(commandBase2);
                        }
                    }
                } else if (id == 209) {
                    SettingsMtuCmd settingsMtuCmd = (SettingsMtuCmd) convert2Command;
                    if (settingsMtuCmd.getStatus() == 0 && (response = settingsMtuCmd.getResponse()) != null) {
                        ParseHelper.setMaxCommunicationMtu(response.getRealMtu());
                    }
                } else if (id == 231) {
                    if (next.getStatus() == 0) {
                        JL_Log.e(this.TAG, "--recv CMD_REBOOT_DEVICE >>> ");
                        disconnectBluetoothDevice(bluetoothDevice);
                    }
                } else {
                    switch (id) {
                        case Command.CMD_OTA_ENTER_UPDATE_MODE /* 227 */:
                            EnterUpdateModeCmd enterUpdateModeCmd = (EnterUpdateModeCmd) convert2Command;
                            if (enterUpdateModeCmd.getStatus() == 0) {
                                EnterUpdateModeResponse response2 = enterUpdateModeCmd.getResponse();
                                if (response2 != null && response2.getCanUpdateFlag() == 0) {
                                    ParseHelper.setMaxCommunicationMtu(520);
                                    break;
                                }
                            } else {
                                continue;
                            }
                            break;
                        case Command.CMD_OTA_EXIT_UPDATE_MODE /* 228 */:
                            ExitUpdateModeCmd exitUpdateModeCmd = (ExitUpdateModeCmd) convert2Command;
                            if (exitUpdateModeCmd.getStatus() == 0) {
                                ExitUpdateModeResponse response3 = exitUpdateModeCmd.getResponse();
                                if (response3 != null && response3.getResult() == 0) {
                                    ParseHelper.setMaxCommunicationMtu(520);
                                    break;
                                }
                            } else {
                                continue;
                            }
                            break;
                        case Command.CMD_OTA_SEND_FIRMWARE_UPDATE_BLOCK /* 229 */:
                            resetOTAFlag();
                            continue;
                    }
                }
            }
            return;
        }
        JL_Log.e(this.TAG, "receiveDataFromDevice :: not find RCSP data.");
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void errorEventCallback(BaseError baseError) {
        callbackError(baseError);
    }

    public boolean isOTA() {
        return isOTA;
    }

    public void setIsOTA(boolean z) {
        isOTA = z;
    }

    public long getTimeout_ms() {
        return this.timeout_ms;
    }

    public void setTimeout_ms(long j) {
        this.timeout_ms = j;
    }

    public void resetTotalTime() {
        this.mTotalTime = 0L;
    }

    public long getTotalTime() {
        return this.mTotalTime;
    }

    public boolean checkDeviceIsCertify(BluetoothDevice bluetoothDevice) {
        return !this.mBluetoothOption.isUseAuthDevice() || this.mDeviceStatusManager.isAuthBtDevice(bluetoothDevice);
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void queryMandatoryUpdate(final IActionCallback<TargetInfoResponse> iActionCallback) {
        final BluetoothDevice connectedBtDevice = getConnectedBtDevice();
        if (connectedBtDevice == null) {
            callbackErrorEvent(iActionCallback, new BaseError(ErrorCode.SUB_ERR_BLE_NOT_CONNECTED, "device is disconnected."));
            return;
        }
        TargetInfoResponse deviceInfo = this.mDeviceStatusManager.getDeviceInfo(connectedBtDevice);
        String str = this.TAG;
        JL_Log.i(str, "-queryMandatoryUpdate- cache deviceInfo : " + deviceInfo);
        if (deviceInfo != null) {
            if (deviceInfo.getMandatoryUpgradeFlag() == 1) {
                callbackSuccessEvent(iActionCallback, deviceInfo);
                return;
            } else {
                callbackErrorEvent(iActionCallback, new BaseError(0, "device connect ok"));
                return;
            }
        }
        getDeviceInfo(connectedBtDevice, new CommandCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.2
            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onCommandResponse(CommandBase commandBase) {
                TargetInfoResponse response = ((GetTargetInfoCmd) commandBase).getResponse();
                String str2 = BluetoothOTAManager.this.TAG;
                JL_Log.i(str2, "-queryMandatoryUpdate- targetInfo : " + response);
                if (commandBase.getStatus() != 0 || response == null) {
                    BluetoothOTAManager.this.callbackErrorEvent(iActionCallback, new BaseError(3, ErrorCode.SUB_ERR_DATA_FORMAT, "response is error."));
                    return;
                }
                BluetoothOTAManager.this.mDeviceStatusManager.updateDeviceTargetInfo(connectedBtDevice, response);
                if (response.getMandatoryUpgradeFlag() == 1) {
                    BluetoothOTAManager.this.callbackSuccessEvent(iActionCallback, response);
                } else {
                    BluetoothOTAManager.this.callbackErrorEvent(iActionCallback, new BaseError(0, "device connect ok"));
                }
            }

            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onErrCode(BaseError baseError) {
                BluetoothOTAManager.this.callbackErrorEvent(iActionCallback, baseError);
            }
        });
    }

    public void sendCommandAsync(CommandBase commandBase, int i, CommandCallback commandCallback) {
        sendCommandAsync(null, commandBase, i, commandCallback);
    }

    public void sendCommandAsync(BluetoothDevice bluetoothDevice, CommandBase commandBase, int i, CommandCallback commandCallback) {
        boolean z;
        if (bluetoothDevice == null) {
            bluetoothDevice = getConnectedBtDevice();
        }
        if (commandBase != null && bluetoothDevice != null && checkDeviceIsCertify(bluetoothDevice)) {
            String str = this.TAG;
            JL_Log.d(str, "-sendCommandAsync- cmd : " + commandBase + ", timeoutMs : " + i);
            if (commandBase.getId() == 227) {
                boolean isDoubleBackupUpgrade = this.mDeviceStatusManager.isDoubleBackupUpgrade(bluetoothDevice);
                String str2 = this.TAG;
                JL_Log.i(str2, "-sendCommandAsync- isDoubleBackUp : " + isDoubleBackupUpgrade);
                if (!isDoubleBackupUpgrade) {
                    DeviceStatus deviceStatus = this.mDeviceStatusManager.getDeviceStatus(bluetoothDevice);
                    boolean isMandatoryUpgrade = this.mDeviceStatusManager.isMandatoryUpgrade(bluetoothDevice);
                    String str3 = this.TAG;
                    JL_Log.i(str3, "-sendCommandAsync- isMandatoryUpgrade : " + isMandatoryUpgrade);
                    if (!isMandatoryUpgrade) {
                        this.mDeviceReConnectManager.setReConnectDevMsg(new ReConnectDevMsg(getDeviceCommunicationWay(bluetoothDevice), bluetoothDevice.getAddress()));
                        if (deviceStatus != null && deviceStatus.getTargetInfo() != null) {
                            z = handleTargetInfoForUpgrade(bluetoothDevice, deviceStatus.getTargetInfo());
                        } else {
                            getDeviceInfo(bluetoothDevice, this.mCommandCallback);
                            z = true;
                        }
                        if (z) {
                            startWaitDeviceReConnect(commandBase, i, commandCallback);
                            return;
                        }
                    }
                }
            }
            commandBase.setOpCodeSn(BluetoothUtil.autoIncSN());
            CommandHelper.getInstance().putCommandBase(commandBase);
            BasePacket convert2BasePacket = ParseHelper.convert2BasePacket(commandBase, 1);
            if (convert2BasePacket != null) {
                DataInfo callback = new DataInfo().setType(0).setBasePacket(convert2BasePacket).setTimeoutMs(i).setCallback(commandCallback);
                String str4 = this.TAG;
                JL_Log.e(str4, "-sendCommandAsync- addSendData : " + callback);
                DataHandler.getInstance(this).addSendData(callback);
                return;
            }
            JL_Log.e(this.TAG, "-sendCommandAsync- basePacket is null...");
            BaseError baseError = new BaseError(3, 4097, "parameter error..");
            baseError.setOpCode(commandBase.getId());
            callbackError(baseError);
            return;
        }
        String str5 = this.TAG;
        JL_Log.e(str5, "-sendCommandAsync- send command failed, mConnectedDevice : " + bluetoothDevice + ", checkDeviceIsCertify : " + checkDeviceIsCertify(bluetoothDevice));
        if (commandCallback != null) {
            BaseError baseError2 = new BaseError(3, ErrorCode.SUB_ERR_SEND_FAILED, "send command failed.");
            if (commandBase != null) {
                baseError2.setOpCode(commandBase.getId());
            }
            commandCallback.onErrCode(baseError2);
        }
    }

    public void sendCommandResponse(CommandBase commandBase) {
        BluetoothDevice connectedBtDevice = getConnectedBtDevice();
        if (commandBase != null && connectedBtDevice != null && checkDeviceIsCertify(connectedBtDevice)) {
            BasePacket convert2BasePacket = ParseHelper.convert2BasePacket(commandBase, 0);
            if (convert2BasePacket != null) {
                DataHandler.getInstance(this).addSendData(new DataInfo().setType(0).setBasePacket(convert2BasePacket));
                return;
            }
            JL_Log.i(this.TAG, "-sendCommandResponse- basePacket is null");
            BaseError baseError = new BaseError(3, ErrorCode.SUB_ERR_PARSE_DATA, "command format is error..");
            baseError.setOpCode(commandBase.getId());
            callbackError(baseError);
            return;
        }
        BaseError baseError2 = new BaseError(3, ErrorCode.SUB_ERR_SEND_FAILED, "send command failed.");
        if (commandBase != null) {
            baseError2.setOpCode(commandBase.getId());
        }
        callbackError(baseError2);
    }

    @Override // com.jieli.jl_bt_ota.impl.RcspAuth.IRcspAuthOp
    public boolean sendAuthDataToDevice(BluetoothDevice bluetoothDevice, byte[] bArr) {
        return sendDataToDevice(bluetoothDevice, bArr);
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void startOTA(IUpgradeCallback iUpgradeCallback) {
        BluetoothDevice connectedBtDevice = getConnectedBtDevice();
        if (connectedBtDevice != null) {
            if (!getBluetoothOption().isUseAuthDevice()) {
                this.mDeviceStatusManager.updateDeviceIsAuth(connectedBtDevice, true);
            }
            if (!isOTA) {
                setIsOTA(true);
                mUpgradeCallback = iUpgradeCallback;
                if (FileUtil.checkFileExist(getBluetoothOption().getFirmwareFilePath())) {
                    startReadFileThread(getBluetoothOption().getFirmwareFilePath());
                    return;
                } else if (getBluetoothOption().getFirmwareFileData() != null && getBluetoothOption().getFirmwareFileData().length > 0) {
                    mUpgradeDataBuf = getBluetoothOption().getFirmwareFileData();
                    callbackStartOTA();
                    callbackProgress(connectedBtDevice, 0.0f);
                    upgradePrepare();
                    return;
                } else {
                    callbackError(iUpgradeCallback, new BaseError(ErrorCode.SUB_ERR_FILE_NOT_FOUND, "Not found OTA data."));
                    return;
                }
            }
            callbackError(iUpgradeCallback, new BaseError(ErrorCode.SUB_ERR_OTA_IN_HANDLE, "OTA is continuing,please stop it at first."));
            return;
        }
        BaseError baseError = new BaseError(ErrorCode.SUB_ERR_BLE_NOT_CONNECTED, "bluetooth device not connect.");
        callbackError(baseError);
        callbackError(iUpgradeCallback, baseError);
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void cancelOTA() {
        exitUpdateMode();
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothBreProfiles, com.jieli.jl_bt_ota.impl.BluetoothPair, com.jieli.jl_bt_ota.impl.BluetoothDiscovery, com.jieli.jl_bt_ota.impl.BluetoothBase, com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void release() {
        super.release();
        cancelOTA();
        stopReConnectManagerOp();
        stopReceiveCmdTimeout();
        stopOTAReConnectTimeout();
        wakeupWaitDeviceReConnect();
        resetOtaProgress();
        this.mDeviceStatusManager.clear();
        this.mDeviceReConnectManager.release();
        this.mRcspAuth.removeListener(this.mOnRcspAuthListener);
        this.mRcspAuth.destroy();
        setIsOTA(false);
        mUpgradeCallback = null;
        this.mHandler.removeCallbacksAndMessages(null);
        DataHandler.getInstance(this).release();
        JL_Log.e(this.TAG, "release..........>>>>>>>>>>>>>>>>>");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isUseBle(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice != null) {
            int type = bluetoothDevice.getType();
            if (type == 1) {
                return false;
            }
            if (type != 2) {
                if (isDeviceConnected()) {
                    return isConnectedBLEDevice(bluetoothDevice);
                }
                if (this.mBluetoothOption.getPriority() != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isStartSumProgress() {
        return this.mUpdateContentSize > 0;
    }

    private boolean isDeviceConnected() {
        return getConnectedBtDevice() != null;
    }

    private boolean isConnectedDevice(BluetoothDevice bluetoothDevice) {
        return isDeviceConnected() && BluetoothUtil.deviceEquals(getConnectedBtDevice(), bluetoothDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isConnectedBLEDevice(BluetoothDevice bluetoothDevice) {
        return getConnectedBluetoothGatt() != null && BluetoothUtil.deviceEquals(getConnectedBluetoothGatt().getDevice(), bluetoothDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isConnectedSppDevice(BluetoothDevice bluetoothDevice) {
        return isDeviceConnected() && !isConnectedBLEDevice(bluetoothDevice);
    }

    private void setReConnectDevice(BluetoothDevice bluetoothDevice) {
        this.mReConnectDevice = bluetoothDevice;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setChangeTargetDevice(BluetoothDevice bluetoothDevice) {
        this.mChangeTargetDevice = bluetoothDevice;
        this.mHandler.removeMessages(MSG_CHANGE_SPP_TIMEOUT);
        if (bluetoothDevice != null) {
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(MSG_CHANGE_SPP_TIMEOUT, 1, 0), 30000L);
        }
    }

    private byte[] readBlockData(int i, int i2) {
        if (mUpgradeDataBuf == null || mUpgradeDataBuf.length <= 0) {
            return null;
        }
        byte[] bArr = new byte[i2];
        if (i + i2 <= mUpgradeDataBuf.length) {
            System.arraycopy(mUpgradeDataBuf, i, bArr, 0, i2);
            return bArr;
        }
        return null;
    }

    private float getCurrentProgress(int i) {
        if (isStartSumProgress()) {
            float f = (i * 100.0f) / this.mUpdateContentSize;
            if (f >= 100.0f) {
                return 99.9f;
            }
            return f;
        }
        return 0.0f;
    }

    private void startReadFileThread(String str) {
        if (isDeviceConnected()) {
            callbackStartOTA();
            callbackProgress(getConnectedBtDevice(), 0.0f);
            new ReadFileThread(str, this.mReadFileCallback).start();
            return;
        }
        callbackError(new BaseError(ErrorCode.SUB_ERR_BLE_NOT_CONNECTED, "device not connect."));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startReceiveCmdTimeout() {
        if (this.timeout_ms > 0) {
            stopReceiveCmdTimeout();
            this.mHandler.sendEmptyMessageDelayed(MSG_START_RECEIVE_CMD_TIMEOUT, this.timeout_ms);
        }
    }

    private void stopReceiveCmdTimeout() {
        this.mHandler.removeMessages(MSG_START_RECEIVE_CMD_TIMEOUT);
    }

    private void startOTAReConnectTimeout(CommandCallback commandCallback) {
        this.mHandler.removeMessages(MSG_OTA_RECONNECT_DEVICE_TIMEOUT);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(MSG_OTA_RECONNECT_DEVICE_TIMEOUT, commandCallback), 30000L);
    }

    private void stopOTAReConnectTimeout() {
        stopReceiveCmdTimeout();
        this.mHandler.removeMessages(MSG_OTA_RECONNECT_DEVICE_TIMEOUT);
    }

    private void startDeviceAuth(BluetoothDevice bluetoothDevice) {
        JL_Log.d(this.TAG, String.format(Locale.getDefault(), "-startDeviceAuth- device : %s, ret : %s ", BluetoothUtil.printBtDeviceInfo(bluetoothDevice), Boolean.valueOf(this.mRcspAuth.startAuth(bluetoothDevice))));
    }

    private void stopDeviceAuth(BluetoothDevice bluetoothDevice) {
        String str = this.TAG;
        JL_Log.d(str, "-stopDeviceAuth- device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice));
        this.mRcspAuth.stopAuth(bluetoothDevice, true);
    }

    private boolean requestBleMtu(BluetoothDevice bluetoothDevice, int i) {
        BluetoothGatt connectedBluetoothGatt = getConnectedBluetoothGatt();
        if (connectedBluetoothGatt == null || !BluetoothUtil.deviceEquals(connectedBluetoothGatt.getDevice(), bluetoothDevice)) {
            JL_Log.e(this.TAG, "--requestBleMtu-- device is disconnected.");
            return false;
        } else if (Build.VERSION.SDK_INT >= 21) {
            JL_Log.e(this.TAG, "--requestBleMtu-- requestMtu is started.");
            if (connectedBluetoothGatt.requestMtu(i + 3)) {
                return true;
            }
            JL_Log.e(this.TAG, "--requestBleMtu-- requestMtu failed. callback old mtu.");
            onBleDataBlockChanged(bluetoothDevice, this.mBleMtu, ErrorCode.SUB_ERR_CHANGE_BLE_MTU);
            return false;
        } else {
            JL_Log.e(this.TAG, "--requestBleMtu-- android sdk not support requestMtu method.");
            onBleDataBlockChanged(bluetoothDevice, this.mBleMtu, ErrorCode.SUB_ERR_CHANGE_BLE_MTU);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startChangeMtu(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return;
        }
        if (this.mHandler.hasMessages(MSG_CHANGE_BLE_MTU_TIMEOUT)) {
            JL_Log.w(this.TAG, "-startChangeMtu- Adjusting the MTU for BLE");
            return;
        }
        boolean z = false;
        if (this.mBluetoothOption.isNeedChangeMtu() && this.mBluetoothOption.getMtu() > 20) {
            z = requestBleMtu(bluetoothDevice, this.mBluetoothOption.getMtu());
        }
        if (z) {
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(MSG_CHANGE_BLE_MTU_TIMEOUT, bluetoothDevice), DELAY_WAITING_TIME);
            return;
        }
        handleBleConnectedEvent(bluetoothDevice);
    }

    private void stopChangeMtu() {
        this.mHandler.removeMessages(MSG_CHANGE_BLE_MTU_TIMEOUT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean startChangeSpp(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return false;
        }
        String str = this.TAG;
        JL_Log.i(str, "-startChangeSpp- device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice));
        if (isConnectedSppDevice(bluetoothDevice)) {
            notifyConnectionStatus(bluetoothDevice, 1);
            return true;
        }
        setChangeTargetDevice(bluetoothDevice);
        return true;
    }

    private void startWaitDeviceReConnect(CommandBase commandBase, int i, CommandCallback commandCallback) {
        if (mWaitDeviceReConnect == null) {
            startOTAReConnectTimeout(commandCallback);
            mWaitDeviceReConnect = new WaitDeviceReConnect(commandBase, i, commandCallback);
            mWaitDeviceReConnect.start();
        }
    }

    private void wakeupWaitDeviceReConnect() {
        if (mWaitDeviceReConnect != null) {
            mWaitDeviceReConnect.wakeUp();
        }
    }

    private int getDeviceCommunicationWay(BluetoothDevice bluetoothDevice) {
        int priority = this.mBluetoothOption.getPriority();
        TargetInfoResponse deviceInfo = this.mDeviceStatusManager.getDeviceInfo(bluetoothDevice);
        if (deviceInfo == null || deviceInfo.isSupportDoubleBackup()) {
            return priority;
        }
        int singleBackupOtaWay = deviceInfo.getSingleBackupOtaWay();
        if (singleBackupOtaWay == 1) {
            return 0;
        }
        if (singleBackupOtaWay == 2) {
            return 1;
        }
        return priority;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBleConnectedEvent(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            JL_Log.e(this.TAG, "-handleBleConnectedEvent- device is null.");
            return;
        }
        stopChangeMtu();
        String str = this.TAG;
        JL_Log.i(str, "-handleBleConnectedEvent- device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice));
        setConnectedBtDevice(bluetoothDevice);
        getDeviceInfoWithConnection(bluetoothDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSppConnected(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            JL_Log.e(this.TAG, "-handleSppConnected- device is null.");
            return;
        }
        setConnectedBtDevice(bluetoothDevice);
        String str = this.TAG;
        JL_Log.i(str, "-handleSppConnected- device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice));
        DeviceStatus deviceStatus = this.mDeviceStatusManager.getDeviceStatus(bluetoothDevice);
        if (deviceStatus == null || deviceStatus.getTargetInfo() == null) {
            getDeviceInfoWithConnection(bluetoothDevice);
        } else if (this.mDeviceStatusManager.isMandatoryUpgrade(bluetoothDevice)) {
            notifyConnectionStatus(bluetoothDevice, 1);
        } else {
            notifyDeviceCommunicationWay(bluetoothDevice);
            notifyConnectionStatus(bluetoothDevice, 1);
        }
    }

    private void getDeviceInfoWithConnection(final BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return;
        }
        JL_Log.i(this.TAG, "-getDeviceInfoWithConnection- start....");
        getDeviceInfo(bluetoothDevice, new CommandCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.3
            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onCommandResponse(CommandBase commandBase) {
                TargetInfoResponse response = ((GetTargetInfoCmd) commandBase).getResponse();
                String str = BluetoothOTAManager.this.TAG;
                JL_Log.w(str, "-getDeviceInfoWithConnection- targetInfo : " + response + ",  connectedDevice : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice));
                if (commandBase.getStatus() == 0 && response != null) {
                    BluetoothOTAManager.this.mDeviceStatusManager.updateDeviceTargetInfo(bluetoothDevice, response);
                    if (response.isSupportMD5()) {
                        BluetoothOTAManager.this.getDevMD5(bluetoothDevice);
                    }
                    if (!(response.getMandatoryUpgradeFlag() == 1)) {
                        BluetoothOTAManager.this.releaseWaitingForUpdateLock();
                        BluetoothOTAManager.this.notifyConnectionStatus(bluetoothDevice, 1);
                        return;
                    }
                    String str2 = BluetoothOTAManager.this.TAG;
                    JL_Log.w(str2, "getDeviceInfoWithConnection >>>> sdkType : " + response.getSdkType());
                    ParseHelper.setMaxCommunicationMtu(520);
                    if (response.getSdkType() < 2) {
                        boolean isConnectedBLEDevice = BluetoothOTAManager.this.isConnectedBLEDevice(bluetoothDevice);
                        boolean isUseBle = BluetoothOTAManager.this.isUseBle(bluetoothDevice);
                        if (isUseBle && isConnectedBLEDevice) {
                            BluetoothOTAManager.this.notifyConnectionStatus(bluetoothDevice, 1);
                            return;
                        } else if (isConnectedBLEDevice) {
                            BluetoothOTAManager.this.bleChangeSpp(bluetoothDevice, response);
                            return;
                        } else {
                            if (isUseBle) {
                                BluetoothOTAManager.this.mBluetoothOption.setPriority(1);
                            }
                            BluetoothOTAManager.this.notifyConnectionStatus(bluetoothDevice, 1);
                            return;
                        }
                    }
                    if (BluetoothOTAManager.this.getConnectedBluetoothGatt() != null && Build.VERSION.SDK_INT >= 21) {
                        boolean requestConnectionPriority = BluetoothOTAManager.this.getConnectedBluetoothGatt().requestConnectionPriority(1);
                        String str3 = BluetoothOTAManager.this.TAG;
                        JL_Log.w(str3, "-getDeviceInfoWithConnection- requestConnectionPriority :: ret : " + requestConnectionPriority);
                    }
                    BluetoothOTAManager.this.notifyConnectionStatus(bluetoothDevice, 1);
                    return;
                }
                String str4 = BluetoothOTAManager.this.TAG;
                JL_Log.w(str4, "-getDeviceInfoWithConnection- response error  " + response);
                BluetoothOTAManager.this.callbackConnectFailedAndReason(bluetoothDevice, new BaseError(3, ErrorCode.SUB_ERR_DATA_FORMAT, "response is error."));
            }

            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onErrCode(BaseError baseError) {
                String str = BluetoothOTAManager.this.TAG;
                JL_Log.i(str, "-getDeviceInfoWithConnection- =onErrCode= error : " + baseError);
                BluetoothOTAManager.this.callbackConnectFailedAndReason(bluetoothDevice, new BaseError(3, ErrorCode.SUB_ERR_SEND_FAILED, "send cmd failed."));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDevMD5(final BluetoothDevice bluetoothDevice) {
        sendCommandAsync(new GetDevMD5Cmd(), 3000, new CommandCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.4
            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onCommandResponse(CommandBase commandBase) {
                if (commandBase == null) {
                    return;
                }
                if (commandBase.getStatus() == 0) {
                    GetDevMD5Response response = ((GetDevMD5Cmd) commandBase).getResponse();
                    if (response != null) {
                        String str = BluetoothOTAManager.this.TAG;
                        JL_Log.i(str, "getDeviceMD5 ok, MD5 : " + response.getMd5());
                        BluetoothOTAManager.this.mDeviceStatusManager.updateDeviceMD5(bluetoothDevice, response.getMd5());
                        return;
                    }
                    return;
                }
                String str2 = BluetoothOTAManager.this.TAG;
                JL_Log.d(str2, "getDeviceMD5 failed. bad status : " + commandBase.getStatus());
            }

            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onErrCode(BaseError baseError) {
                String str = BluetoothOTAManager.this.TAG;
                JL_Log.w(str, "GetDevMD5 has an error : " + baseError);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bleChangeSpp(BluetoothDevice bluetoothDevice, TargetInfoResponse targetInfoResponse) {
        if (targetInfoResponse != null) {
            boolean z = true;
            boolean z2 = targetInfoResponse.getCurFunction() == 22;
            boolean isBleOnly = targetInfoResponse.isBleOnly();
            DeviceStatusManager deviceStatusManager = this.mDeviceStatusManager;
            if (!z2 && !isBleOnly) {
                z = false;
            }
            deviceStatusManager.updateDeviceIsEnterLowPowerMode(bluetoothDevice, z);
            this.mDeviceStatusManager.updateDeviceTargetInfo(bluetoothDevice, targetInfoResponse);
            String str = this.TAG;
            JL_Log.i(str, "-bleChangeSpp- isEnterLowPower : " + z2);
            notifyDeviceCommunicationWay(bluetoothDevice);
            return;
        }
        JL_Log.w(this.TAG, "bleChangeSpp ->>>> spp response error.");
        callbackConnectFailedAndReason(bluetoothDevice, new BaseError(3, ErrorCode.SUB_ERR_DATA_FORMAT, "response is error.").setOpCode(3));
    }

    private void notifyDeviceCommunicationWay(final BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return;
        }
        final int deviceCommunicationWay = getDeviceCommunicationWay(bluetoothDevice);
        sendCommandAsync(new NotifyCommunicationWayCmd(new NotifyCommunicationWayParam(deviceCommunicationWay)), 3000, new CommandCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.5
            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onCommandResponse(CommandBase commandBase) {
                if (commandBase.getStatus() == 0) {
                    boolean isConnectedBLEDevice = BluetoothOTAManager.this.isConnectedBLEDevice(bluetoothDevice);
                    boolean isMandatoryUpgrade = BluetoothOTAManager.this.mDeviceStatusManager.isMandatoryUpgrade(bluetoothDevice);
                    String str = BluetoothOTAManager.this.TAG;
                    JL_Log.i(str, "-notifyDeviceCommunicationWay- communicationWay : " + deviceCommunicationWay + " :>:>:> isMandatoryUpdate : " + isMandatoryUpgrade + ", isBleConnected : " + isConnectedBLEDevice);
                    if (deviceCommunicationWay == 1) {
                        if (isConnectedBLEDevice) {
                            TargetInfoResponse deviceInfo = BluetoothOTAManager.this.mDeviceStatusManager.getDeviceInfo(bluetoothDevice);
                            if (deviceInfo == null) {
                                BluetoothOTAManager.this.onConnectFailed(bluetoothDevice);
                                return;
                            }
                            String edrAddr = deviceInfo.getEdrAddr();
                            if (isMandatoryUpgrade) {
                                BluetoothOTAManager.this.mDeviceReConnectManager.setReConnectDevMsg(new ReConnectDevMsg(1, edrAddr));
                            }
                            if (BluetoothOTAManager.this.startChangeSpp(bluetoothDevice)) {
                                return;
                            }
                            BluetoothOTAManager.this.onConnectFailed(bluetoothDevice);
                            return;
                        }
                        BluetoothOTAManager.this.notifyConnectionStatus(bluetoothDevice, 1);
                        return;
                    }
                    return;
                }
                String str2 = BluetoothOTAManager.this.TAG;
                JL_Log.i(str2, "-notifyDeviceCommunicationWay- status : " + commandBase.getStatus());
                BluetoothOTAManager.this.callbackConnectFailedAndReason(bluetoothDevice, new BaseError(3, ErrorCode.SUB_ERR_DATA_FORMAT, "response is error.").setOpCode(11));
            }

            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onErrCode(BaseError baseError) {
                String str = BluetoothOTAManager.this.TAG;
                JL_Log.w(str, "-notifyDeviceCommunicationWay- onErrCode >>>>> " + baseError);
                BluetoothOTAManager.this.callbackConnectFailedAndReason(bluetoothDevice, new BaseError(3, ErrorCode.SUB_ERR_SEND_FAILED, "send cmd failed."));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleTargetInfoForUpgrade(BluetoothDevice bluetoothDevice, TargetInfoResponse targetInfoResponse) {
        if (targetInfoResponse == null || targetInfoResponse.isSupportDoubleBackup()) {
            return false;
        }
        if (targetInfoResponse.getMandatoryUpgradeFlag() == 0) {
            setReConnectDevice(bluetoothDevice);
            int deviceCommunicationWay = getDeviceCommunicationWay(bluetoothDevice);
            String str = this.TAG;
            JL_Log.w(str, "-handlerTargetInfoForUpgrade- enter update mode... 0 ,  " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice) + ", communicationWay : " + deviceCommunicationWay);
            sendCommandAsync(new NotifyCommunicationWayCmd(new NotifyCommunicationWayParam(deviceCommunicationWay)), 3000, this.mCommandCallback);
            return true;
        }
        ParseHelper.setMaxCommunicationMtu(520);
        JL_Log.w(this.TAG, "-handlerTargetInfoForUpgrade- enter update mode... 1");
        releaseWaitingForUpdateLock();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00cd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void startUpgradeReConnect(android.bluetooth.BluetoothDevice r10) {
        /*
            r9 = this;
            if (r10 == 0) goto Lde
            boolean r0 = r9.isConnectedBLEDevice(r10)
            java.lang.String r1 = r10.getAddress()
            int r2 = r9.getDeviceCommunicationWay(r10)
            com.jieli.jl_bt_ota.tool.DeviceStatusManager r3 = r9.mDeviceStatusManager
            com.jieli.jl_bt_ota.model.response.TargetInfoResponse r3 = r3.getDeviceInfo(r10)
            r4 = 0
            r5 = 2
            r6 = 0
            r7 = 1
            if (r3 == 0) goto L36
            boolean r8 = r3.isSupportDoubleBackup()
            if (r8 != 0) goto L36
            int r8 = r3.getSingleBackupOtaWay()
            if (r0 == 0) goto L2e
            if (r8 != r5) goto L36
            java.lang.String r0 = r3.getEdrAddr()
            r2 = 1
            goto L37
        L2e:
            if (r8 != r7) goto L36
            java.lang.String r0 = r3.getBleAddr()
            r2 = 0
            goto L37
        L36:
            r0 = r6
        L37:
            boolean r3 = android.bluetooth.BluetoothAdapter.checkBluetoothAddress(r0)
            if (r3 == 0) goto L3e
            r1 = r0
        L3e:
            com.jieli.jl_bt_ota.tool.DeviceReConnectManager r0 = r9.mDeviceReConnectManager
            com.jieli.jl_bt_ota.model.ReConnectDevMsg r3 = new com.jieli.jl_bt_ota.model.ReConnectDevMsg
            r3.<init>(r2, r1)
            r0.setReConnectDevMsg(r3)
            java.lang.String r0 = r9.TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "-startUpgradeReConnect- mConnectedDevice : "
            r2.append(r3)
            java.lang.String r3 = com.jieli.jl_bt_ota.util.BluetoothUtil.printBtDeviceInfo(r10)
            r2.append(r3)
            java.lang.String r3 = ", reConnectAddr : "
            r2.append(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            com.jieli.jl_bt_ota.util.JL_Log.w(r0, r1)
            boolean r0 = r9.isConnectedSppDevice(r10)
            if (r0 == 0) goto Lcd
            int r0 = r9.isConnectedByProfile(r10)
            if (r0 != r5) goto L77
            r4 = 1
        L77:
            java.lang.String r0 = r9.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "-startUpgradeReConnect- isConnectEdr : "
            r1.append(r2)
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            com.jieli.jl_bt_ota.util.JL_Log.d(r0, r1)
            if (r4 == 0) goto Lb6
            boolean r0 = r9.disconnectByProfiles(r10)
            java.lang.String r1 = r9.TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "-startUpgradeReConnect- disconnectEdrRet : "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            com.jieli.jl_bt_ota.util.JL_Log.i(r1, r2)
            if (r0 != 0) goto Ldb
            java.lang.String r0 = r9.TAG
            java.lang.String r1 = "-startUpgradeReConnect- edr is disconnected. disconnectSPPDevice."
            com.jieli.jl_bt_ota.util.JL_Log.i(r0, r1)
            r9.disconnectBluetoothDevice(r10)
            goto Ldb
        Lb6:
            int r0 = r9.isConnectedByA2dp(r10)
            if (r0 == 0) goto Lc2
            int r0 = r9.isConnectedByHfp(r10)
            if (r0 != 0) goto Ldb
        Lc2:
            java.lang.String r0 = r9.TAG
            java.lang.String r1 = "-startUpgradeReConnect- disconnectBluetoothDevice >>> "
            com.jieli.jl_bt_ota.util.JL_Log.d(r0, r1)
            r9.disconnectBluetoothDevice(r10)
            goto Ldb
        Lcd:
            android.os.Handler r10 = r9.mHandler
            r0 = 4663(0x1237, float:6.534E-42)
            r10.removeMessages(r0)
            android.os.Handler r10 = r9.mHandler
            r1 = 3000(0xbb8, double:1.482E-320)
            r10.sendEmptyMessageDelayed(r0, r1)
        Ldb:
            r9.setReConnectDevice(r6)
        Lde:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.startUpgradeReConnect(android.bluetooth.BluetoothDevice):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackSuccessEvent(final IActionCallback<TargetInfoResponse> iActionCallback, final TargetInfoResponse targetInfoResponse) {
        if (iActionCallback != null) {
            this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.6
                @Override // java.lang.Runnable
                public void run() {
                    iActionCallback.onSuccess(targetInfoResponse);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackErrorEvent(final IActionCallback<TargetInfoResponse> iActionCallback, final BaseError baseError) {
        if (iActionCallback != null) {
            this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.7
                @Override // java.lang.Runnable
                public void run() {
                    iActionCallback.onError(baseError);
                }
            });
        }
    }

    private void callbackStartOTA() {
        resetTotalTime();
        if (mUpgradeCallback != null) {
            this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.8
                @Override // java.lang.Runnable
                public void run() {
                    if (BluetoothOTAManager.mUpgradeCallback != null) {
                        BluetoothOTAManager.mUpgradeCallback.onStartOTA();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackReconnectEvent(final String str) {
        if (mUpgradeCallback == null || str == null) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.9
            @Override // java.lang.Runnable
            public void run() {
                if (BluetoothOTAManager.mUpgradeCallback != null) {
                    BluetoothOTAManager.mUpgradeCallback.onNeedReconnect(str);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackProgress(BluetoothDevice bluetoothDevice, float f) {
        TargetInfoResponse deviceInfo = DeviceStatusManager.getInstance().getDeviceInfo(bluetoothDevice);
        callbackProgress((deviceInfo == null || deviceInfo.isNeedBootLoader()) ? 0 : 1, f);
    }

    private void callbackProgress(final int i, final float f) {
        if (this.mStartTime > 0) {
            this.mTotalTime = CommonUtil.getCurrentTime() - this.mStartTime;
        }
        if (mUpgradeCallback != null) {
            this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.10
                @Override // java.lang.Runnable
                public void run() {
                    if (BluetoothOTAManager.mUpgradeCallback != null) {
                        BluetoothOTAManager.mUpgradeCallback.onProgress(i, f);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackStopOTA() {
        if (this.mStartTime > 0) {
            this.mTotalTime = CommonUtil.getCurrentTime() - this.mStartTime;
            this.mStartTime = 0L;
        }
        stopReceiveCmdTimeout();
        setIsOTA(false);
        resetOtaProgress();
        if (mUpgradeCallback != null) {
            this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.11
                @Override // java.lang.Runnable
                public void run() {
                    if (BluetoothOTAManager.mUpgradeCallback != null) {
                        BluetoothOTAManager.mUpgradeCallback.onStopOTA();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackCancelOTA() {
        stopReceiveCmdTimeout();
        setIsOTA(false);
        resetOtaProgress();
        if (this.mStartTime > 0) {
            this.mTotalTime = CommonUtil.getCurrentTime() - this.mStartTime;
            this.mStartTime = 0L;
        }
        if (mUpgradeCallback != null) {
            this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.12
                @Override // java.lang.Runnable
                public void run() {
                    if (BluetoothOTAManager.mUpgradeCallback != null) {
                        BluetoothOTAManager.mUpgradeCallback.onCancelOTA();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackError(BaseError baseError) {
        callbackError(mUpgradeCallback, baseError);
    }

    private void callbackError(final IUpgradeCallback iUpgradeCallback, final BaseError baseError) {
        if (baseError == null) {
            return;
        }
        if (16392 != baseError.getSubCode()) {
            stopReceiveCmdTimeout();
            setIsOTA(false);
            resetOtaProgress();
            if (this.mStartTime > 0) {
                this.mTotalTime = CommonUtil.getCurrentTime() - this.mStartTime;
                this.mStartTime = 0L;
            }
        }
        if (iUpgradeCallback != null) {
            this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.13
                @Override // java.lang.Runnable
                public void run() {
                    iUpgradeCallback.onError(baseError);
                }
            });
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0010, code lost:
        r0 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0019, code lost:
        if (isConnectedByHfp(r3) == 0) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x000d, code lost:
        if (isConnectedByA2dp(r3) == 0) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x000f, code lost:
        r5 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void handleBrEdrProfileStatus(android.bluetooth.BluetoothDevice r3, int r4, int r5) {
        /*
            r2 = this;
            if (r3 == 0) goto L3e
            if (r5 == 0) goto L5
            goto L3e
        L5:
            r5 = 0
            r0 = 1
            if (r4 != r0) goto L12
            int r4 = r2.isConnectedByA2dp(r3)
            if (r4 != 0) goto L10
        Lf:
            r5 = 1
        L10:
            r0 = r5
            goto L1c
        L12:
            r1 = 2
            if (r4 != r1) goto L1c
            int r4 = r2.isConnectedByHfp(r3)
            if (r4 != 0) goto L10
            goto Lf
        L1c:
            if (r0 == 0) goto L3e
            com.jieli.jl_bt_ota.tool.DeviceReConnectManager r4 = r2.mDeviceReConnectManager
            boolean r4 = r4.isWaitingForUpdate()
            if (r4 == 0) goto L3e
            boolean r4 = r2.isConnectedDevice(r3)
            if (r4 == 0) goto L30
            r2.disconnectBluetoothDevice(r3)
            goto L3e
        L30:
            android.os.Handler r3 = r2.mHandler
            r4 = 4663(0x1237, float:6.534E-42)
            r3.removeMessages(r4)
            android.os.Handler r3 = r2.mHandler
            r0 = 500(0x1f4, double:2.47E-321)
            r3.sendEmptyMessageDelayed(r4, r0)
        L3e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.handleBrEdrProfileStatus(android.bluetooth.BluetoothDevice, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void upgradePrepare() {
        final BluetoothDevice connectedBtDevice = getConnectedBtDevice();
        if (this.mDeviceStatusManager.getDeviceStatus(connectedBtDevice) == null) {
            getDeviceInfo(connectedBtDevice, new CommandCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.14
                @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
                public void onCommandResponse(CommandBase commandBase) {
                    TargetInfoResponse response;
                    if (commandBase == null || commandBase.getStatus() != 0 || (response = ((GetTargetInfoCmd) commandBase).getResponse()) == null) {
                        BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_RESPONSE_BAD_STATUS, "GetTargetInfo[0x03] command returns an bad status."));
                        return;
                    }
                    BluetoothOTAManager.this.mDeviceStatusManager.updateDeviceTargetInfo(connectedBtDevice, response);
                    BluetoothOTAManager.this.upgradeStep01();
                }

                @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
                public void onErrCode(BaseError baseError) {
                    BluetoothOTAManager.this.callbackError(baseError);
                }
            });
        } else {
            upgradeStep01();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void upgradeStep01() {
        if (!isOTA) {
            JL_Log.d(this.TAG, "upgradeStep01 : ota has exited.");
            return;
        }
        JL_Log.d(this.TAG, "upgradeStep01 : send GetUpdateFileOffsetCmd.");
        sendCommandAsync(new GetUpdateFileOffsetCmd(), 3000, new CommandCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.15
            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onCommandResponse(CommandBase commandBase) {
                GetUpdateFileOffsetCmd getUpdateFileOffsetCmd = (GetUpdateFileOffsetCmd) commandBase;
                String str = BluetoothOTAManager.this.TAG;
                JL_Log.i(str, "Step01.获取升级文件信息的偏移地址, \n" + getUpdateFileOffsetCmd);
                if (commandBase.getStatus() == 0) {
                    UpdateFileOffsetResponse response = getUpdateFileOffsetCmd.getResponse();
                    if (response == null) {
                        BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_PARSE_DATA, "response is null."));
                        return;
                    }
                    int updateFileFlagLen = response.getUpdateFileFlagLen();
                    BluetoothOTAManager.this.upgradeStep02(response.getUpdateFileFlagOffset(), updateFileFlagLen);
                    return;
                }
                BluetoothOTAManager bluetoothOTAManager = BluetoothOTAManager.this;
                bluetoothOTAManager.callbackError(new BaseError(ErrorCode.SUB_ERR_DATA_FORMAT, "response status is not success, status : " + commandBase.getStatus()));
            }

            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onErrCode(BaseError baseError) {
                BluetoothOTAManager.this.callbackError(baseError);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void upgradeStep02(int i, int i2) {
        if (!isOTA) {
            JL_Log.d(this.TAG, "upgradeStep02 : ota has exited.");
        } else if (i2 >= 0 && i >= 0) {
            InquireUpdateParam inquireUpdateParam = new InquireUpdateParam();
            if (i2 > 0) {
                inquireUpdateParam.setUpdateFileFlagData(readBlockData(i, i2));
            } else {
                inquireUpdateParam.setUpdateFileFlagData(new byte[]{(byte) this.mBluetoothOption.getPriority()});
            }
            sendCommandAsync(new InquireUpdateCmd(inquireUpdateParam), 3000, new CommandCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.16
                @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
                public void onCommandResponse(CommandBase commandBase) {
                    InquireUpdateCmd inquireUpdateCmd = (InquireUpdateCmd) commandBase;
                    String str = BluetoothOTAManager.this.TAG;
                    JL_Log.i(str, "Step02.发送升级文件校验信息，确认是否可以升级, \n" + inquireUpdateCmd);
                    if (commandBase.getStatus() == 0) {
                        final BluetoothDevice connectedBtDevice = BluetoothOTAManager.this.getConnectedBtDevice();
                        InquireUpdateResponse response = inquireUpdateCmd.getResponse();
                        if (response == null) {
                            BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_PARSE_DATA, "response is null."));
                            return;
                        }
                        int canUpdateFlag = response.getCanUpdateFlag();
                        if (canUpdateFlag == 0) {
                            TargetInfoResponse deviceInfo = DeviceStatusManager.getInstance().getDeviceInfo(connectedBtDevice);
                            String str2 = BluetoothOTAManager.this.TAG;
                            JL_Log.d(str2, "upgradeStep02 >> deviceInfo ： " + deviceInfo + "， device : " + BluetoothUtil.printBtDeviceInfo(connectedBtDevice));
                            if (deviceInfo == null) {
                                BluetoothOTAManager.this.getDeviceInfo(connectedBtDevice, new CommandCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.16.1
                                    @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
                                    public void onCommandResponse(CommandBase commandBase2) {
                                        if (commandBase2.getStatus() == 0) {
                                            String str3 = BluetoothOTAManager.this.TAG;
                                            JL_Log.i(str3, "upgradeStep02 >> GetTargetInfoCmd ： " + commandBase2 + "， device : " + BluetoothUtil.printBtDeviceInfo(connectedBtDevice));
                                            TargetInfoResponse response2 = ((GetTargetInfoCmd) commandBase2).getResponse();
                                            if (response2 != null) {
                                                BluetoothOTAManager.this.mDeviceStatusManager.updateDeviceTargetInfo(connectedBtDevice, response2);
                                                if (!response2.isNeedBootLoader()) {
                                                    BluetoothOTAManager.this.upgradeStep03();
                                                    return;
                                                }
                                                ParseHelper.setMaxCommunicationMtu(520);
                                                BluetoothOTAManager.this.startReceiveCmdTimeout();
                                                return;
                                            }
                                        }
                                        BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_RESPONSE_BAD_STATUS, "GetTargetInfoCmd is error."));
                                    }

                                    @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
                                    public void onErrCode(BaseError baseError) {
                                        String str3 = BluetoothOTAManager.this.TAG;
                                        JL_Log.i(str3, "upgradeStep02 >> GetTargetInfoCmd error ： " + baseError + "， device : " + BluetoothUtil.printBtDeviceInfo(connectedBtDevice));
                                        BluetoothOTAManager.this.callbackError(baseError);
                                    }
                                });
                                return;
                            } else if (!deviceInfo.isNeedBootLoader()) {
                                BluetoothOTAManager.this.upgradeStep03();
                                return;
                            } else {
                                ParseHelper.setMaxCommunicationMtu(520);
                                BluetoothOTAManager.this.startReceiveCmdTimeout();
                                return;
                            }
                        } else if (canUpdateFlag == 1) {
                            BluetoothOTAManager.this.callbackError(new BaseError(16386, "device low voltage equipment"));
                            return;
                        } else if (canUpdateFlag == 2) {
                            BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_CHECK_UPGRADE_FILE, "check upgrade file failed."));
                            return;
                        } else if (canUpdateFlag == 3) {
                            BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_UPGRADE_FILE_VERSION_SAME, "upgrade file version no change."));
                            return;
                        } else if (canUpdateFlag == 4) {
                            BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_TWS_NOT_CONNECT, "TWS is not connected."));
                            return;
                        } else if (canUpdateFlag == 5) {
                            BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_HEADSET_NOT_IN_CHARGING_BIN, "The headset is not in the charging bin."));
                            return;
                        } else {
                            BluetoothOTAManager bluetoothOTAManager = BluetoothOTAManager.this;
                            bluetoothOTAManager.callbackError(new BaseError(ErrorCode.SUB_ERR_OTA_FAILED, "inquire update unknown flag : " + canUpdateFlag));
                            return;
                        }
                    }
                    BluetoothOTAManager bluetoothOTAManager2 = BluetoothOTAManager.this;
                    bluetoothOTAManager2.callbackError(new BaseError(ErrorCode.SUB_ERR_DATA_FORMAT, "response status is not success, status : " + commandBase.getStatus()));
                }

                @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
                public void onErrCode(BaseError baseError) {
                    BluetoothOTAManager.this.callbackError(baseError);
                }
            });
        } else {
            callbackError(new BaseError(4097, "param is error."));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void upgradeStep03() {
        if (!isOTA) {
            JL_Log.d(this.TAG, "upgradeStep03 : ota has exited.");
        } else {
            sendCommandAsync(new EnterUpdateModeCmd(), 3000, new CommandCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.17
                @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
                public void onCommandResponse(CommandBase commandBase) {
                    EnterUpdateModeCmd enterUpdateModeCmd = (EnterUpdateModeCmd) commandBase;
                    String str = BluetoothOTAManager.this.TAG;
                    JL_Log.i(str, "Step03.请求进入升级模式, \n" + enterUpdateModeCmd);
                    if (commandBase.getStatus() == 0) {
                        EnterUpdateModeResponse response = enterUpdateModeCmd.getResponse();
                        if (response == null) {
                            BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_PARSE_DATA, "response is null."));
                            return;
                        } else if (response.getCanUpdateFlag() != 0) {
                            BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_OTA_FAILED, "device enter update mode failed."));
                            return;
                        } else {
                            JL_Log.w(BluetoothOTAManager.this.TAG, "enter upgrade mode success, waiting for device command.");
                            if (!BluetoothOTAManager.isOTA) {
                                BluetoothOTAManager.this.setIsOTA(true);
                            }
                            BluetoothOTAManager.this.startReceiveCmdTimeout();
                            return;
                        }
                    }
                    BluetoothOTAManager bluetoothOTAManager = BluetoothOTAManager.this;
                    bluetoothOTAManager.callbackError(new BaseError(ErrorCode.SUB_ERR_DATA_FORMAT, "response status is not success, status : " + commandBase.getStatus()));
                }

                @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
                public void onErrCode(BaseError baseError) {
                    BluetoothOTAManager.this.callbackError(baseError);
                }
            });
        }
    }

    private void upgradeStep04(FirmwareUpdateBlockCmd firmwareUpdateBlockCmd, int i, int i2) {
        if (!isOTA) {
            JL_Log.d(this.TAG, "upgradeStep04 : ota has exited.");
        } else if (i == 0 && i2 == 0) {
            stopReceiveCmdTimeout();
            firmwareUpdateBlockCmd.setParam(null);
            firmwareUpdateBlockCmd.setStatus(0);
            sendCommandResponse(firmwareUpdateBlockCmd);
            upgradeStep05();
        } else {
            byte[] readBlockData = readBlockData(i, i2);
            if (readBlockData != null && readBlockData.length > 0) {
                firmwareUpdateBlockCmd.setParam(new FirmwareUpdateBlockResponseParam(readBlockData));
                firmwareUpdateBlockCmd.setStatus(0);
                sendCommandResponse(firmwareUpdateBlockCmd);
                startReceiveCmdTimeout();
                return;
            }
            callbackError(new BaseError(ErrorCode.SUB_ERR_OFFSET_OVER, "offset over limit."));
        }
    }

    private void upgradeStep05() {
        if (!isOTA) {
            JL_Log.d(this.TAG, "upgradeStep05 : ota has exited.");
        } else {
            sendCommandAsync(new FirmwareUpdateStatusCmd(), 3000, new CommandCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.18
                @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
                public void onCommandResponse(CommandBase commandBase) {
                    FirmwareUpdateStatusCmd firmwareUpdateStatusCmd = (FirmwareUpdateStatusCmd) commandBase;
                    String str = BluetoothOTAManager.this.TAG;
                    JL_Log.i(str, "Step05.询问升级状态, \n" + firmwareUpdateStatusCmd);
                    if (commandBase.getStatus() == 0) {
                        FirmwareUpdateStatusResponse response = firmwareUpdateStatusCmd.getResponse();
                        if (response == null) {
                            BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_PARSE_DATA, "response is null."));
                            return;
                        }
                        int result = response.getResult();
                        if (result != 128) {
                            switch (result) {
                                case 0:
                                    BluetoothOTAManager.this.setIsOTA(false);
                                    BluetoothOTAManager.this.rebootDevice();
                                    BluetoothOTAManager.this.mHandler.postDelayed(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.18.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            BluetoothOTAManager.this.callbackProgress(BluetoothOTAManager.this.getConnectedBtDevice(), 100.0f);
                                            BluetoothOTAManager.this.callbackStopOTA();
                                        }
                                    }, BluetoothOTAManager.DEFAULT_DELAY);
                                    return;
                                case 1:
                                    BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_CHECK_RECEIVED_DATA_FAILED, "check received data error."));
                                    return;
                                case 2:
                                    BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_OTA_FAILED, "upgrade failed."));
                                    return;
                                case 3:
                                    BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_UPGRADE_KEY_NOT_MATCH, "upgrade key not match."));
                                    return;
                                case 4:
                                    BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_CHECK_UPGRADE_FILE, "check upgrade file error."));
                                    return;
                                case 5:
                                    BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_UPGRADE_TYPE_NOT_MATCH, "upgrade type not match."));
                                    return;
                                case 6:
                                    BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_UPGRADE_DATA_LEN, "data length error."));
                                    return;
                                case 7:
                                    BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_UPGRADE_FLASH_READ, "flash read error."));
                                    return;
                                case 8:
                                    BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_UPGRADE_CMD_TIMEOUT, "cmd timeout."));
                                    return;
                                default:
                                    BluetoothOTAManager bluetoothOTAManager = BluetoothOTAManager.this;
                                    bluetoothOTAManager.callbackError(new BaseError(-1, "firmware update unknown status : " + result));
                                    return;
                            }
                        }
                        BluetoothOTAManager.this.resetTotalTime();
                        BluetoothOTAManager.this.resetOtaProgress();
                        BluetoothOTAManager.this.upgradeStep03();
                        return;
                    }
                    BluetoothOTAManager bluetoothOTAManager2 = BluetoothOTAManager.this;
                    bluetoothOTAManager2.callbackError(new BaseError(ErrorCode.SUB_ERR_DATA_FORMAT, "response status is not success, status : " + commandBase.getStatus()));
                }

                @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
                public void onErrCode(BaseError baseError) {
                    BluetoothOTAManager.this.callbackError(baseError);
                }
            });
        }
    }

    private void exitUpdateMode() {
        if (!isOTA) {
            JL_Log.d(this.TAG, "exitUpdateMode : ota has exited.");
            return;
        }
        TargetInfoResponse deviceInfo = DeviceStatusManager.getInstance().getDeviceInfo(getConnectedBtDevice());
        if (deviceInfo == null || !deviceInfo.isSupportDoubleBackup()) {
            return;
        }
        setIsOTA(false);
        sendCommandAsync(new ExitUpdateModeCmd(), 3000, new CommandCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.19
            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onCommandResponse(CommandBase commandBase) {
                ExitUpdateModeCmd exitUpdateModeCmd = (ExitUpdateModeCmd) commandBase;
                if (commandBase.getStatus() == 0) {
                    ExitUpdateModeResponse response = exitUpdateModeCmd.getResponse();
                    if (response == null) {
                        BluetoothOTAManager.this.callbackError(new BaseError(ErrorCode.SUB_ERR_PARSE_DATA, "response is null."));
                        return;
                    } else if (response.getResult() == 0) {
                        JL_Log.i(BluetoothOTAManager.this.TAG, "-exitUpdateMode- callbackCancelOTA");
                        BluetoothOTAManager.this.callbackCancelOTA();
                        return;
                    } else {
                        return;
                    }
                }
                BluetoothOTAManager bluetoothOTAManager = BluetoothOTAManager.this;
                bluetoothOTAManager.callbackError(new BaseError(ErrorCode.SUB_ERR_DATA_FORMAT, "response status is not success, status : " + commandBase.getStatus()));
            }

            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onErrCode(BaseError baseError) {
                BluetoothOTAManager.this.callbackError(baseError);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rebootDevice() {
        sendCommandAsync(new RebootDeviceCmd(new RebootDeviceParam(0)), 3000, new CommandCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.20
            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onCommandResponse(CommandBase commandBase) {
                String str = BluetoothOTAManager.this.TAG;
                JL_Log.i(str, "-rebootDevice- " + commandBase);
            }

            @Override // com.jieli.jl_bt_ota.interfaces.CommandCallback
            public void onErrCode(BaseError baseError) {
                String str = BluetoothOTAManager.this.TAG;
                JL_Log.w(str, "-rebootDevice- =onErrCode= " + baseError);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyConnectionStatus(BluetoothDevice bluetoothDevice, int i) {
        String str = this.TAG;
        JL_Log.i(str, "-notifyConnectionStatus- device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice) + ", status : " + i);
        if (i != 3) {
            if (i == 1 || i == 4) {
                JL_Log.i(this.TAG, "-notifyConnectionStatus- handler connected event.");
            } else if (i == 2 || i == 0) {
                JL_Log.w(this.TAG, "-notifyConnectionStatus- handler disconnect event.");
                DataHandler.getInstance(this).release();
                stopReceiveCmdTimeout();
                this.mDeviceStatusManager.removeDeviceStatus(bluetoothDevice);
                if (BluetoothUtil.deviceEquals(bluetoothDevice, this.mChangeTargetDevice)) {
                    this.mHandler.postDelayed(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.21
                        @Override // java.lang.Runnable
                        public void run() {
                            BluetoothOTAManager bluetoothOTAManager = BluetoothOTAManager.this;
                            bluetoothOTAManager.connectBluetoothDevice(bluetoothOTAManager.mChangeTargetDevice);
                            BluetoothOTAManager.this.setChangeTargetDevice(null);
                        }
                    }, DEFAULT_DELAY);
                }
            }
        }
        onConnection(bluetoothDevice, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectFailed(BluetoothDevice bluetoothDevice) {
        String str = this.TAG;
        JL_Log.i(str, "-onConnectFailed- device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice));
        notifyConnectionStatus(bluetoothDevice, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackConnectFailedAndReason(BluetoothDevice bluetoothDevice, BaseError baseError) {
        String str = this.TAG;
        JL_Log.i(str, "-callbackConnectFailedAndReason- device ：" + BluetoothUtil.printBtDeviceInfo(bluetoothDevice) + " , error : " + baseError);
        onConnectFailed(bluetoothDevice);
        callbackError(baseError);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void failedToUpdate(String str) {
        JL_Log.e(this.TAG, ">>>>>>>>>>>>>>>>>>> failedToUpdate <<<<<<<<<<<<<<<<<<<<<<<<<<<");
        setReConnectDevice(null);
        releaseWaitingForUpdateLock();
        callbackError(new BaseError(4, ErrorCode.SUB_ERR_OTA_FAILED, str));
    }

    private void stopReConnectManagerOp() {
        if (this.mDeviceReConnectManager.isDeviceReconnecting()) {
            this.mDeviceReConnectManager.stopReconnectTask();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetOTAFlag() {
        if (this.mDeviceReConnectManager.isWaitingForUpdate()) {
            this.mDeviceReConnectManager.setReConnectDevMsg(null);
            this.mDeviceReConnectManager.stopReconnectTask();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseWaitingForUpdateLock() {
        JL_Log.i(this.TAG, "-releaseWaitingForUpdateLock-");
        resetOTAFlag();
        wakeupWaitDeviceReConnect();
        stopOTAReConnectTimeout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetOtaProgress() {
        this.mCurrentSumFileSize = 0;
        this.mUpdateContentSize = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDeviceInfo(BluetoothDevice bluetoothDevice, CommandCallback commandCallback) {
        sendCommandAsync(bluetoothDevice, CommandBuilder.buildGetTargetInfoCmdForAll(), 3000, commandCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BluetoothDevice getConnectedBtDevice() {
        if (this.mConnectedBtDevice == null) {
            this.mConnectedBtDevice = getConnectedDevice();
        } else if (getConnectedDevice() != null && !BluetoothUtil.deviceEquals(getConnectedDevice(), this.mConnectedBtDevice)) {
            this.mConnectedBtDevice = getConnectedDevice();
        }
        return this.mConnectedBtDevice;
    }

    private void setConnectedBtDevice(BluetoothDevice bluetoothDevice) {
        this.mConnectedBtDevice = bluetoothDevice;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class WaitDeviceReConnect extends Thread {
        private boolean isWait;
        private CommandBase mCommandBase;
        private CommandCallback mCommandCallback;
        private final Object mObject;
        private int timeoutMs;

        private WaitDeviceReConnect(CommandBase commandBase, int i, CommandCallback commandCallback) {
            super("WaitDeviceReConnect");
            this.mObject = new Object();
            this.mCommandBase = commandBase;
            this.timeoutMs = i;
            this.mCommandCallback = commandCallback;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            JL_Log.i(BluetoothOTAManager.this.TAG, "WaitDeviceReConnect start");
            synchronized (this.mObject) {
                while (BluetoothOTAManager.this.mDeviceReConnectManager.isWaitingForUpdate()) {
                    try {
                        this.isWait = true;
                        this.mObject.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        this.isWait = false;
                    }
                }
            }
            boolean hasMessages = BluetoothOTAManager.this.mHandler.hasMessages(BluetoothOTAManager.MSG_OTA_RECONNECT_DEVICE_TIMEOUT);
            String str = BluetoothOTAManager.this.TAG;
            JL_Log.i(str, "wait over.... hasOTAConnectTimeout : " + hasMessages);
            WaitDeviceReConnect unused = BluetoothOTAManager.mWaitDeviceReConnect = null;
            if (hasMessages) {
                BluetoothOTAManager.this.mHandler.removeMessages(BluetoothOTAManager.MSG_OTA_RECONNECT_DEVICE_TIMEOUT);
                BluetoothOTAManager.this.sendCommandAsync(this.mCommandBase, this.timeoutMs, this.mCommandCallback);
            }
            JL_Log.i(BluetoothOTAManager.this.TAG, "WaitDeviceReConnect end");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void wakeUp() {
            synchronized (this.mObject) {
                if (this.isWait) {
                    this.mObject.notify();
                }
            }
        }
    }
}
