package com.jieli.jl_bt_ota.impl;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.baidu.kwgames.Constants;
import com.jieli.jl_bt_ota.constant.BluetoothConstant;
import com.jieli.jl_bt_ota.interfaces.JieLiLibLoader;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
/* loaded from: classes2.dex */
public class RcspAuth {
    private static final int AUTH_RETRY_COUNT = 3;
    private static final long DELAY_AUTH_WAITING_TIME = 2000;
    public static final int ERR_AUTH_DEVICE_TIMEOUT = 40977;
    public static final int ERR_AUTH_TRY_NUM_OVER_LIMIT = 40976;
    public static final int ERR_AUTH_USER_STOP = 40979;
    private static final int MSG_AUTH_DEVICE_TIMEOUT = 18;
    private static final int MSG_SEND_AUTH_DATA_TIMEOUT = 17;
    private static final String TAG = "RcspAuth";
    private static volatile boolean mIsLibLoaded = false;
    public static final JieLiLibLoader sLocalLibLoader = new JieLiLibLoader() { // from class: com.jieli.jl_bt_ota.impl.RcspAuth.2
        @Override // com.jieli.jl_bt_ota.interfaces.JieLiLibLoader
        public void loadLibrary(String str) throws UnsatisfiedLinkError, SecurityException {
            System.loadLibrary(str);
        }
    };
    private final boolean isLibInit;
    private final IRcspAuthOp mIRcspAuthOp;
    private final List<OnRcspAuthListener> mOnRcspAuthListeners = Collections.synchronizedList(new ArrayList());
    private final Map<String, AuthTask> mAuthTaskMap = Collections.synchronizedMap(new HashMap());
    private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.jieli.jl_bt_ota.impl.RcspAuth.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i != 17) {
                if (i != 18) {
                    return false;
                }
                BluetoothDevice bluetoothDevice = (BluetoothDevice) message.obj;
                AuthTask authTask = (AuthTask) RcspAuth.this.mAuthTaskMap.get(bluetoothDevice.getAddress());
                if (authTask == null || authTask.isAuthDevice()) {
                    return false;
                }
                RcspAuth.this.onAuthFailed(bluetoothDevice, RcspAuth.ERR_AUTH_DEVICE_TIMEOUT, "Auth device timeout.");
                return false;
            }
            BluetoothDevice bluetoothDevice2 = (BluetoothDevice) message.obj;
            AuthTask authTask2 = (AuthTask) RcspAuth.this.mAuthTaskMap.get(bluetoothDevice2.getAddress());
            if (authTask2 == null || authTask2.getRetryNum() >= 3) {
                RcspAuth.this.onAuthFailed(bluetoothDevice2, RcspAuth.ERR_AUTH_TRY_NUM_OVER_LIMIT, "The number of retries exceeded.");
                return false;
            }
            authTask2.setRetryNum(authTask2.getRetryNum() + 1);
            RcspAuth.this.sendAuthDataToDevice(authTask2.getDevice(), authTask2.getRandomData());
            RcspAuth.this.mHandler.removeMessages(18);
            RcspAuth.this.mHandler.sendMessageDelayed(RcspAuth.this.mHandler.obtainMessage(18, bluetoothDevice2), RcspAuth.DELAY_AUTH_WAITING_TIME);
            return false;
        }
    });

    /* loaded from: classes2.dex */
    public interface IRcspAuthOp {
        boolean sendAuthDataToDevice(BluetoothDevice bluetoothDevice, byte[] bArr);
    }

    /* loaded from: classes2.dex */
    public interface OnRcspAuthListener {
        void onAuthFailed(BluetoothDevice bluetoothDevice, int i, String str);

        void onAuthSuccess(BluetoothDevice bluetoothDevice);

        void onInitResult(boolean z);
    }

    private native byte[] getEncryptedAuthData(byte[] bArr);

    private native byte[] getRandomAuthData();

    private native boolean nativeInit();

    private native int setLinkKey(byte[] bArr);

    public static void loadLibrariesOnce(JieLiLibLoader jieLiLibLoader) {
        synchronized (BluetoothBase.class) {
            if (!mIsLibLoaded) {
                if (jieLiLibLoader == null) {
                    jieLiLibLoader = sLocalLibLoader;
                }
                jieLiLibLoader.loadLibrary(BluetoothConstant.JL_AUTH);
                mIsLibLoaded = true;
            }
        }
    }

    public RcspAuth(IRcspAuthOp iRcspAuthOp, OnRcspAuthListener onRcspAuthListener) {
        loadLibrariesOnce(null);
        this.isLibInit = nativeInit();
        this.mIRcspAuthOp = iRcspAuthOp;
        addListener(onRcspAuthListener);
    }

    public void addListener(OnRcspAuthListener onRcspAuthListener) {
        if (onRcspAuthListener != null) {
            this.mOnRcspAuthListeners.add(onRcspAuthListener);
            onRcspAuthListener.onInitResult(this.isLibInit);
        }
    }

    public void removeListener(OnRcspAuthListener onRcspAuthListener) {
        if (onRcspAuthListener != null) {
            this.mOnRcspAuthListeners.clear();
        }
    }

    public int setDeviceConnectionLinkKey(byte[] bArr) {
        return setLinkKey(bArr);
    }

    public byte[] getRandomData() {
        return getRandomAuthData();
    }

    public byte[] getAuthData(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return getEncryptedAuthData(bArr);
    }

    public byte[] getAuthOkData() {
        return new byte[]{2, 112, Constants.KEY_KP9, 115, 115};
    }

    public boolean startAuth(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return false;
        }
        if (this.mAuthTaskMap.containsKey(bluetoothDevice.getAddress())) {
            String str = TAG;
            JL_Log.i(str, "-startAuth- ");
            AuthTask authTask = this.mAuthTaskMap.get(bluetoothDevice.getAddress());
            if (authTask != null && (authTask.isAuthDevice() || this.mHandler.hasMessages(18))) {
                JL_Log.i(str, "-startAuth- The device has been certified or certification of device is in progress.");
                return true;
            }
            this.mAuthTaskMap.remove(bluetoothDevice.getAddress());
        }
        AuthTask randomData = new AuthTask().setDevice(bluetoothDevice).setRandomData(getRandomData());
        this.mAuthTaskMap.put(bluetoothDevice.getAddress(), randomData);
        boolean sendAuthDataToDevice = sendAuthDataToDevice(bluetoothDevice, randomData.getRandomData());
        if (sendAuthDataToDevice) {
            this.mHandler.removeMessages(17);
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(17, bluetoothDevice), DELAY_AUTH_WAITING_TIME);
        }
        return sendAuthDataToDevice;
    }

    public void handleAuthData(BluetoothDevice bluetoothDevice, byte[] bArr) {
        AuthTask authTask;
        if (bluetoothDevice == null || bArr == null || (authTask = this.mAuthTaskMap.get(bluetoothDevice.getAddress())) == null || authTask.isAuthDevice()) {
            return;
        }
        String str = TAG;
        boolean z = true;
        JL_Log.d(str, String.format(Locale.getDefault(), "-handleAuthData- device : %s, data : %s", BluetoothUtil.printBtDeviceInfo(bluetoothDevice), CHexConver.byte2HexStr(bArr)));
        if (!authTask.isAuthProgressResult()) {
            byte[] authData = getAuthData(authTask.getRandomData());
            if (authData != null && Arrays.equals(authData, bArr)) {
                z = sendAuthDataToDevice(bluetoothDevice, getAuthOkData());
            }
            z = false;
        } else if (bArr.length == 17 && bArr[0] == 0) {
            z = sendAuthDataToDevice(bluetoothDevice, getAuthData(bArr));
        } else {
            if (Arrays.equals(bArr, getAuthOkData())) {
                authTask.setAuthDevice(true);
                onAuthSuccess(bluetoothDevice);
                JL_Log.w(str, String.format(Locale.getDefault(), "-handleAuthData- device : %s, auth ok.", BluetoothUtil.printBtDeviceInfo(bluetoothDevice)));
            }
            z = false;
        }
        authTask.setAuthProgressResult(z);
        if (!z) {
            authTask.setAuthDevice(false);
            onAuthFailed(bluetoothDevice, ERR_AUTH_DEVICE_TIMEOUT, "Auth device timeout.");
            return;
        }
        this.mHandler.removeMessages(17);
        this.mHandler.removeMessages(18);
        if (authTask.isAuthDevice()) {
            return;
        }
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(18, bluetoothDevice), DELAY_AUTH_WAITING_TIME);
    }

    public void stopAuth(BluetoothDevice bluetoothDevice, boolean z) {
        if (bluetoothDevice == null) {
            return;
        }
        AuthTask remove = this.mAuthTaskMap.remove(bluetoothDevice.getAddress());
        if (z) {
            if (remove != null) {
                onAuthFailed(bluetoothDevice, ERR_AUTH_USER_STOP, "User stop auth device.");
            }
            this.mHandler.removeMessages(17);
            this.mHandler.removeMessages(18);
        }
    }

    public void destroy() {
        this.mHandler.removeMessages(17);
        this.mHandler.removeMessages(18);
        this.mAuthTaskMap.clear();
        this.mOnRcspAuthListeners.clear();
        mIsLibLoaded = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean sendAuthDataToDevice(BluetoothDevice bluetoothDevice, byte[] bArr) {
        if (bluetoothDevice == null || bArr == null) {
            return false;
        }
        boolean sendAuthDataToDevice = this.mIRcspAuthOp.sendAuthDataToDevice(bluetoothDevice, bArr);
        JL_Log.i(TAG, String.format(Locale.getDefault(), "-sendAuthDataToDevice- device : %s, authData : %s", BluetoothUtil.printBtDeviceInfo(bluetoothDevice), CHexConver.byte2HexStr(bArr)));
        return sendAuthDataToDevice;
    }

    private void onInitResult(final boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.RcspAuth.3
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = new ArrayList(RcspAuth.this.mOnRcspAuthListeners).iterator();
                while (it.hasNext()) {
                    ((OnRcspAuthListener) it.next()).onInitResult(z);
                }
            }
        });
    }

    private void onAuthSuccess(final BluetoothDevice bluetoothDevice) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.RcspAuth.4
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = new ArrayList(RcspAuth.this.mOnRcspAuthListeners).iterator();
                while (it.hasNext()) {
                    ((OnRcspAuthListener) it.next()).onAuthSuccess(bluetoothDevice);
                }
            }
        });
        if (bluetoothDevice != null) {
            this.mAuthTaskMap.remove(bluetoothDevice.getAddress());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAuthFailed(final BluetoothDevice bluetoothDevice, final int i, final String str) {
        this.mHandler.removeMessages(17);
        this.mHandler.removeMessages(18);
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.RcspAuth.5
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = new ArrayList(RcspAuth.this.mOnRcspAuthListeners).iterator();
                while (it.hasNext()) {
                    ((OnRcspAuthListener) it.next()).onAuthFailed(bluetoothDevice, i, str);
                }
            }
        });
        if (bluetoothDevice != null) {
            this.mAuthTaskMap.remove(bluetoothDevice.getAddress());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class AuthTask {
        private BluetoothDevice device;
        private boolean isAuthDevice;
        private boolean isAuthProgressResult;
        private int protocol;
        private byte[] randomData;
        private int retryNum;

        private AuthTask() {
        }

        public BluetoothDevice getDevice() {
            return this.device;
        }

        public AuthTask setDevice(BluetoothDevice bluetoothDevice) {
            this.device = bluetoothDevice;
            return this;
        }

        public int getProtocol() {
            return this.protocol;
        }

        public AuthTask setProtocol(int i) {
            this.protocol = i;
            return this;
        }

        public boolean isAuthProgressResult() {
            return this.isAuthProgressResult;
        }

        public AuthTask setAuthProgressResult(boolean z) {
            this.isAuthProgressResult = z;
            return this;
        }

        public boolean isAuthDevice() {
            return this.isAuthDevice;
        }

        public AuthTask setAuthDevice(boolean z) {
            this.isAuthDevice = z;
            return this;
        }

        public byte[] getRandomData() {
            return this.randomData;
        }

        public AuthTask setRandomData(byte[] bArr) {
            this.randomData = bArr;
            return this;
        }

        public int getRetryNum() {
            return this.retryNum;
        }

        public AuthTask setRetryNum(int i) {
            this.retryNum = i;
            return this;
        }

        public String toString() {
            return "AuthTask{device=" + BluetoothUtil.printBtDeviceInfo(this.device) + ", protocol=" + this.protocol + ", isAuthProgressResult=" + this.isAuthProgressResult + ", isAuthDevice=" + this.isAuthDevice + ", randomData=" + CHexConver.byte2HexStr(this.randomData) + ", retryNum=" + this.retryNum + '}';
        }
    }
}
