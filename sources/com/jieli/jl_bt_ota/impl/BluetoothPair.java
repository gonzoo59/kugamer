package com.jieli.jl_bt_ota.impl;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.jieli.jl_bt_ota.constant.ErrorCode;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.concurrent.LinkedBlockingQueue;
/* loaded from: classes2.dex */
public abstract class BluetoothPair extends BluetoothDiscovery {
    private BluetoothPairReceiver mBluetoothPairReceiver;
    private final Handler mHandler;
    private PairBtDeviceThread mPairBtDeviceThread;
    private PairTaskTimeOut mPairTaskTimeOut;

    public BluetoothPair(Context context) {
        super(context);
        this.mHandler = new Handler(Looper.getMainLooper());
        registerReceiver();
        startPairTaskThread();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.jieli.jl_bt_ota.impl.BluetoothDiscovery, com.jieli.jl_bt_ota.impl.BluetoothBase
    public void finalize() throws Throwable {
        unregisterReceiver();
        stopPairTaskThread();
        super.finalize();
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothDiscovery, com.jieli.jl_bt_ota.impl.BluetoothBase, com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void release() {
        super.release();
        unregisterReceiver();
        stopPairTaskThread();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public boolean isPaired(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice != null && 12 == bluetoothDevice.getBondState();
    }

    protected boolean isPairing(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice != null && 11 == bluetoothDevice.getBondState();
    }

    public int pair(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            JL_Log.w(this.TAG, "-pair- device is null");
            return 2;
        }
        if (isBleScanning()) {
            stopBLEScan();
        }
        if (isDeviceScanning()) {
            stopDeviceScan();
        }
        boolean createBond = BluetoothUtil.createBond(bluetoothDevice);
        String str = this.TAG;
        JL_Log.w(str, "-pair- createBond ret = " + createBond);
        if (createBond) {
            startPairTimeOut(bluetoothDevice);
            return 0;
        }
        return 2;
    }

    public int unPair(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            JL_Log.e(this.TAG, "-unPair- error : 4097");
            return 4097;
        }
        boolean removeBond = BluetoothUtil.removeBond(bluetoothDevice);
        String str = this.TAG;
        JL_Log.w(str, "-unPair- result : " + removeBond);
        if (removeBond) {
            startPairTimeOut(bluetoothDevice);
            return 0;
        }
        return ErrorCode.SUB_ERR_BLUETOOTH_UN_PAIR_FAILED;
    }

    public boolean tryToPair(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice != null) {
            return addPairTask(new PairTask(0, bluetoothDevice));
        }
        return false;
    }

    public boolean tryToUnPair(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice != null) {
            return addPairTask(new PairTask(1, bluetoothDevice));
        }
        return false;
    }

    private void registerReceiver() {
        if (this.mBluetoothPairReceiver == null) {
            this.mBluetoothPairReceiver = new BluetoothPairReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
            if (Build.VERSION.SDK_INT >= 19) {
                intentFilter.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
            }
            CommonUtil.getMainContext().registerReceiver(this.mBluetoothPairReceiver, intentFilter);
        }
    }

    private void unregisterReceiver() {
        if (this.mBluetoothPairReceiver != null) {
            CommonUtil.getMainContext().unregisterReceiver(this.mBluetoothPairReceiver);
            this.mBluetoothPairReceiver = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class BluetoothPairReceiver extends BroadcastReceiver {
        private BluetoothPairReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            BluetoothDevice bluetoothDevice;
            if (intent != null) {
                String action = intent.getAction();
                if (TextUtils.isEmpty(action) || !"android.bluetooth.device.action.BOND_STATE_CHANGED".equals(action) || (bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")) == null) {
                    return;
                }
                int bondState = bluetoothDevice.getBondState();
                String str = BluetoothPair.this.TAG;
                JL_Log.i(str, "recv action :ACTION_BOND_STATE_CHANGED ... device : " + BluetoothUtil.printBtDeviceInfo(bluetoothDevice) + " ,bound : " + bondState);
                if (bondState == 10 || bondState == 12) {
                    BluetoothPair.this.stopPairTimeout(bluetoothDevice);
                    if (BluetoothPair.this.mPairBtDeviceThread != null) {
                        BluetoothPair.this.mPairBtDeviceThread.wakeUp(bluetoothDevice);
                    }
                }
                BluetoothPair.this.onBondStatus(bluetoothDevice, bondState);
            }
        }
    }

    private void startPairTaskThread() {
        if (this.mPairBtDeviceThread == null) {
            PairBtDeviceThread pairBtDeviceThread = new PairBtDeviceThread();
            this.mPairBtDeviceThread = pairBtDeviceThread;
            pairBtDeviceThread.start();
        }
    }

    private boolean addPairTask(PairTask pairTask) {
        if (pairTask != null) {
            startPairTaskThread();
            return this.mPairBtDeviceThread.addPairTask(pairTask);
        }
        return false;
    }

    private void stopPairTaskThread() {
        PairBtDeviceThread pairBtDeviceThread = this.mPairBtDeviceThread;
        if (pairBtDeviceThread != null) {
            pairBtDeviceThread.stopThread();
            this.mPairBtDeviceThread = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class PairBtDeviceThread extends Thread {
        private boolean isThreadRunning;
        private boolean isWaiting;
        private boolean isWaitingForResult;
        private BluetoothDevice mPairTaskDevice;
        private final LinkedBlockingQueue<PairTask> mPairTaskQueue;

        private PairBtDeviceThread() {
            super("PairBtDeviceThread");
            this.mPairTaskQueue = new LinkedBlockingQueue<>();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:25:0x001e A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean addPairTask(com.jieli.jl_bt_ota.impl.BluetoothPair.PairTask r4) {
            /*
                r3 = this;
                r0 = 0
                if (r4 == 0) goto Le
                java.util.concurrent.LinkedBlockingQueue<com.jieli.jl_bt_ota.impl.BluetoothPair$PairTask> r1 = r3.mPairTaskQueue     // Catch: java.lang.InterruptedException -> La
                r1.put(r4)     // Catch: java.lang.InterruptedException -> La
                r4 = 1
                goto Lf
            La:
                r4 = move-exception
                r4.printStackTrace()
            Le:
                r4 = 0
            Lf:
                if (r4 == 0) goto L31
                boolean r1 = r3.isWaiting
                if (r1 == 0) goto L31
                boolean r1 = r3.isWaitingForResult
                if (r1 != 0) goto L31
                r3.isWaiting = r0
                java.util.concurrent.LinkedBlockingQueue<com.jieli.jl_bt_ota.impl.BluetoothPair$PairTask> r0 = r3.mPairTaskQueue
                monitor-enter(r0)
                com.jieli.jl_bt_ota.impl.BluetoothPair r1 = com.jieli.jl_bt_ota.impl.BluetoothPair.this     // Catch: java.lang.Throwable -> L2e
                java.lang.String r1 = r1.TAG     // Catch: java.lang.Throwable -> L2e
                java.lang.String r2 = "=PairBtDeviceThread= -addPairTask- notify"
                com.jieli.jl_bt_ota.util.JL_Log.i(r1, r2)     // Catch: java.lang.Throwable -> L2e
                java.util.concurrent.LinkedBlockingQueue<com.jieli.jl_bt_ota.impl.BluetoothPair$PairTask> r1 = r3.mPairTaskQueue     // Catch: java.lang.Throwable -> L2e
                r1.notify()     // Catch: java.lang.Throwable -> L2e
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L2e
                goto L31
            L2e:
                r4 = move-exception
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L2e
                throw r4
            L31:
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.impl.BluetoothPair.PairBtDeviceThread.addPairTask(com.jieli.jl_bt_ota.impl.BluetoothPair$PairTask):boolean");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void wakeUp(BluetoothDevice bluetoothDevice) {
            if (bluetoothDevice == null || BluetoothUtil.deviceEquals(this.mPairTaskDevice, bluetoothDevice)) {
                synchronized (this.mPairTaskQueue) {
                    if (this.isWaitingForResult) {
                        if (this.isWaiting) {
                            this.mPairTaskQueue.notifyAll();
                        } else {
                            this.mPairTaskQueue.notify();
                        }
                    } else if (this.isWaiting) {
                        this.mPairTaskQueue.notify();
                    }
                }
            }
        }

        public synchronized void stopThread() {
            JL_Log.i(BluetoothPair.this.TAG, "---stopThread---");
            this.isThreadRunning = false;
            this.mPairTaskDevice = null;
            wakeUp(null);
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x0060, code lost:
            if (r6.this$0.pair(r6.mPairTaskDevice) == 0) goto L20;
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 246
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.impl.BluetoothPair.PairBtDeviceThread.run():void");
        }
    }

    /* loaded from: classes2.dex */
    public static class PairTask {
        public static final int OP_CANCEL_PAIR = 1;
        public static final int OP_PAIR = 0;
        private BluetoothDevice mDevice;
        private int mOp;

        public PairTask(int i, BluetoothDevice bluetoothDevice) {
            this.mOp = i;
            this.mDevice = bluetoothDevice;
        }

        public int getOp() {
            return this.mOp;
        }

        public BluetoothDevice getDevice() {
            return this.mDevice;
        }

        public String toString() {
            return "PairTask{mOp=" + this.mOp + ", mDevice=" + this.mDevice + '}';
        }
    }

    /* loaded from: classes2.dex */
    public class PairTaskTimeOut implements Runnable {
        private BluetoothDevice mDevice;

        PairTaskTimeOut(BluetoothDevice bluetoothDevice) {
            this.mDevice = bluetoothDevice;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mDevice != null) {
                if (BluetoothPair.this.mPairBtDeviceThread != null) {
                    BluetoothPair.this.mPairBtDeviceThread.wakeUp(this.mDevice);
                }
                BluetoothPair.this.errorEventCallback(new BaseError(3, ErrorCode.SUB_ERR_PAIR_TIMEOUT, this.mDevice.getAddress()));
            }
        }
    }

    private void startPairTimeOut(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice != null) {
            PairTaskTimeOut pairTaskTimeOut = this.mPairTaskTimeOut;
            if (pairTaskTimeOut != null) {
                this.mHandler.removeCallbacks(pairTaskTimeOut);
                this.mPairTaskTimeOut = null;
            }
            PairTaskTimeOut pairTaskTimeOut2 = new PairTaskTimeOut(bluetoothDevice);
            this.mPairTaskTimeOut = pairTaskTimeOut2;
            this.mHandler.postDelayed(pairTaskTimeOut2, 30000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopPairTimeout(BluetoothDevice bluetoothDevice) {
        PairTaskTimeOut pairTaskTimeOut = this.mPairTaskTimeOut;
        if (pairTaskTimeOut == null || !BluetoothUtil.deviceEquals(bluetoothDevice, pairTaskTimeOut.mDevice)) {
            return;
        }
        this.mHandler.removeCallbacks(this.mPairTaskTimeOut);
        this.mPairTaskTimeOut = null;
    }
}
