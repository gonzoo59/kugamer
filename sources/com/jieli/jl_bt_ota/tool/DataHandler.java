package com.jieli.jl_bt_ota.tool;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.jieli.jl_bt_ota.constant.ErrorCode;
import com.jieli.jl_bt_ota.interfaces.CommandCallback;
import com.jieli.jl_bt_ota.interfaces.IBluetoothManager;
import com.jieli.jl_bt_ota.model.DataInfo;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
/* loaded from: classes2.dex */
public class DataHandler {
    private static String TAG = "DataHandler";
    private static volatile DataHandler instance;
    private IBluetoothManager mBluetoothManager;
    private DataHandlerThread mDataHandlerThread;
    private Handler mUIHandler = new Handler(Looper.getMainLooper());
    private WorkThread mWorkThread;

    /* loaded from: classes2.dex */
    public interface ThreadStateListener {
        void onFinish(long j);

        void onStart(long j);
    }

    private DataHandler(IBluetoothManager iBluetoothManager) {
        this.mBluetoothManager = iBluetoothManager;
        startDataHandlerThread();
    }

    public static DataHandler getInstance(IBluetoothManager iBluetoothManager) {
        if (instance == null) {
            synchronized (DataHandler.class) {
                if (instance == null) {
                    instance = new DataHandler(iBluetoothManager);
                }
            }
        }
        return instance;
    }

    private void startWorkHandler() {
        if (this.mWorkThread == null) {
            this.mWorkThread = new WorkThread("Work_Thread");
        }
        this.mWorkThread.start();
    }

    private void stopWorkHandler() {
        WorkThread workThread = this.mWorkThread;
        if (workThread != null) {
            workThread.quitSafely();
            this.mWorkThread = null;
        }
    }

    private void startDataHandlerThread() {
        if (this.mDataHandlerThread == null) {
            DataHandlerThread dataHandlerThread = new DataHandlerThread();
            this.mDataHandlerThread = dataHandlerThread;
            dataHandlerThread.start();
            startWorkHandler();
        }
    }

    private void stopDataHandlerThread() {
        DataHandlerThread dataHandlerThread = this.mDataHandlerThread;
        if (dataHandlerThread != null) {
            dataHandlerThread.stopThread();
        }
        stopWorkHandler();
    }

    public void addSendData(DataInfo dataInfo) {
        if (this.mWorkThread == null) {
            startDataHandlerThread();
        }
        this.mWorkThread.tryToAddSendData(dataInfo);
    }

    public void addRecvData(DataInfo dataInfo) {
        if (this.mWorkThread == null) {
            startDataHandlerThread();
        }
        this.mWorkThread.tryToAddRecvData(dataInfo);
    }

    public void release() {
        JL_Log.e(TAG, "-release-");
        ParseHelper.setMaxCommunicationMtu(520);
        CommandHelper.getInstance().release();
        stopDataHandlerThread();
        instance = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class DataHandlerThread extends Thread {
        private final List<DataInfo> haveResponseDataList;
        private volatile boolean isSendData;
        private volatile boolean isWaiting;
        private final LinkedBlockingQueue<DataInfo> mQueue;
        private ArrayList<BasePacket> mReceiveDataList;
        private TimerThread mTimer;
        private final List<DataInfo> noResponseDataList;

        public DataHandlerThread() {
            super("DataHandlerThread");
            this.mQueue = new LinkedBlockingQueue<>();
            this.noResponseDataList = Collections.synchronizedList(new ArrayList());
            this.haveResponseDataList = Collections.synchronizedList(new ArrayList());
        }

        @Override // java.lang.Thread
        public synchronized void start() {
            this.isSendData = true;
            super.start();
            JL_Log.i(DataHandler.TAG, "DataHandlerThread start....");
        }

        public void stopThread() {
            JL_Log.w(DataHandler.TAG, "-stopThread-");
            this.isSendData = false;
            wakeUpThread();
        }

        private void startTimer(int i) {
            TimerThread timerThread = this.mTimer;
            if (timerThread == null) {
                TimerThread timerThread2 = new TimerThread(i, new ThreadStateListener() { // from class: com.jieli.jl_bt_ota.tool.DataHandler.DataHandlerThread.1
                    @Override // com.jieli.jl_bt_ota.tool.DataHandler.ThreadStateListener
                    public void onStart(long j) {
                    }

                    @Override // com.jieli.jl_bt_ota.tool.DataHandler.ThreadStateListener
                    public void onFinish(long j) {
                        if (DataHandlerThread.this.mTimer == null || DataHandlerThread.this.mTimer.getId() != j) {
                            return;
                        }
                        DataHandlerThread.this.mTimer = null;
                    }
                });
                this.mTimer = timerThread2;
                timerThread2.start();
            } else if (timerThread.isRunning) {
            } else {
                this.mTimer.isRunning = true;
            }
        }

        private void stopTimer() {
            TimerThread timerThread = this.mTimer;
            if (timerThread == null || !timerThread.isRunning) {
                return;
            }
            JL_Log.i(DataHandler.TAG, "-stopTimer- >>> ");
            this.mTimer.stopThread();
        }

        public void tryToAddSendData(DataInfo dataInfo) {
            boolean addData = addData(dataInfo);
            String str = DataHandler.TAG;
            JL_Log.d(str, "-tryToAddSendData-  ret : " + addData + ",isWaiting = " + this.isWaiting);
        }

        public void tryToAddRecvData(DataInfo dataInfo) {
            boolean addData = addData(dataInfo);
            String str = DataHandler.TAG;
            JL_Log.d(str, "-tryToAddRecvData-  ret : " + addData + ",isWaiting = " + this.isWaiting);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            synchronized (this.mQueue) {
                while (this.isSendData) {
                    if (this.mQueue.isEmpty()) {
                        this.isWaiting = true;
                        handlerData();
                        JL_Log.d(DataHandler.TAG, "DataHandlerThread is waiting...");
                        try {
                            this.mQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        this.isWaiting = false;
                        handlerQueue(this.mQueue.poll());
                        handlerData();
                    }
                }
            }
            JL_Log.e(DataHandler.TAG, "-DataHandlerThread- exit...");
            this.noResponseDataList.clear();
            this.haveResponseDataList.clear();
            this.mQueue.clear();
            this.isSendData = false;
            stopTimer();
            DataHandler.this.mDataHandlerThread = null;
        }

        private void handlerData() {
            checkResponseList();
            DataInfo obtainSendInfo = obtainSendInfo();
            if (obtainSendInfo == null) {
                if (this.haveResponseDataList.size() > 0) {
                    startTimer(500);
                    return;
                } else if (this.noResponseDataList.size() > 0) {
                    startTimer(500);
                    return;
                } else {
                    stopTimer();
                    return;
                }
            }
            sendData(obtainSendInfo);
        }

        private void sendData(DataInfo dataInfo) {
            byte[] packSendBasePacket = ParseHelper.packSendBasePacket(dataInfo.getBasePacket());
            if (packSendBasePacket == null) {
                JL_Log.i(DataHandler.TAG, "send data :: pack data error.");
                onSendFailed(dataInfo);
                return;
            }
            String str = DataHandler.TAG;
            JL_Log.i(str, "send data : [" + CHexConver.byte2HexStr(packSendBasePacket, packSendBasePacket.length) + "]");
            if (packSendBasePacket.length > ParseHelper.getMaxCommunicationMtu() + 8) {
                String str2 = DataHandler.TAG;
                JL_Log.e(str2, "send data over communication mtu [" + ParseHelper.getMaxCommunicationMtu() + "] limit.");
                onSendFailed(dataInfo);
                return;
            }
            boolean z = false;
            for (int i = 0; i < 3; i++) {
                if (DataHandler.this.mBluetoothManager != null) {
                    z = DataHandler.this.mBluetoothManager.sendDataToDevice(DataHandler.this.mBluetoothManager.getConnectedDevice(), packSendBasePacket);
                }
                if (z) {
                    break;
                }
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String str3 = DataHandler.TAG;
            JL_Log.i(str3, "send ret : " + z);
            if (!z) {
                onSendFailed(dataInfo);
            } else if (dataInfo.getBasePacket().getHasResponse() == 1) {
                dataInfo.setSend(true);
                dataInfo.setSendTime(Calendar.getInstance().getTimeInMillis());
            } else {
                final CommandCallback callback = dataInfo.getCallback();
                if (DataHandler.this.mUIHandler != null && callback != null) {
                    DataHandler.this.mUIHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.tool.DataHandler.DataHandlerThread.2
                        @Override // java.lang.Runnable
                        public void run() {
                            callback.onCommandResponse(null);
                        }
                    });
                }
                this.noResponseDataList.remove(dataInfo);
            }
        }

        private void onSendFailed(DataInfo dataInfo) {
            final BasePacket basePacket = dataInfo.getBasePacket();
            if (basePacket == null) {
                return;
            }
            if (basePacket.getHasResponse() == 1) {
                this.haveResponseDataList.remove(dataInfo);
            } else {
                this.noResponseDataList.remove(dataInfo);
            }
            final CommandCallback callback = dataInfo.getCallback();
            if (DataHandler.this.mUIHandler != null) {
                DataHandler.this.mUIHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.tool.DataHandler.DataHandlerThread.3
                    @Override // java.lang.Runnable
                    public void run() {
                        BaseError baseError = new BaseError(3, ErrorCode.SUB_ERR_SEND_FAILED, "send data failed.");
                        baseError.setOpCode(basePacket.getOpCode());
                        CommandCallback commandCallback = callback;
                        if (commandCallback != null) {
                            commandCallback.onErrCode(baseError);
                        }
                        DataHandler.this.mBluetoothManager.errorEventCallback(baseError);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void wakeUpThread() {
            if (this.isWaiting) {
                synchronized (this.mQueue) {
                    if (this.isWaiting) {
                        JL_Log.i(DataHandler.TAG, "wakeUpThread:: notifyAll");
                        this.mQueue.notifyAll();
                    }
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:9:0x0014  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private boolean addData(com.jieli.jl_bt_ota.model.DataInfo r5) {
            /*
                r4 = this;
                if (r5 == 0) goto L11
                java.util.concurrent.LinkedBlockingQueue<com.jieli.jl_bt_ota.model.DataInfo> r0 = r4.mQueue     // Catch: java.lang.InterruptedException -> Ld
                r1 = 3
                java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.SECONDS     // Catch: java.lang.InterruptedException -> Ld
                boolean r5 = r0.offer(r5, r1, r3)     // Catch: java.lang.InterruptedException -> Ld
                goto L12
            Ld:
                r5 = move-exception
                r5.printStackTrace()
            L11:
                r5 = 0
            L12:
                if (r5 == 0) goto L17
                r4.wakeUpThread()
            L17:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.tool.DataHandler.DataHandlerThread.addData(com.jieli.jl_bt_ota.model.DataInfo):boolean");
        }

        private void handlerQueue(DataInfo dataInfo) {
            if (dataInfo != null) {
                if (dataInfo.getType() == 1) {
                    ArrayList<BasePacket> findPacketData = ParseHelper.findPacketData(dataInfo.getRecvData());
                    if (findPacketData == null) {
                        JL_Log.e(DataHandler.TAG, "-handlerQueue- findPacketData not found. ");
                        return;
                    }
                    ArrayList<BasePacket> arrayList = this.mReceiveDataList;
                    if (arrayList == null || arrayList.size() == 0) {
                        this.mReceiveDataList = findPacketData;
                    } else {
                        this.mReceiveDataList.addAll(findPacketData);
                    }
                    Iterator<BasePacket> it = findPacketData.iterator();
                    while (it.hasNext()) {
                        String str = DataHandler.TAG;
                        JL_Log.d(str, "-handlerQueue- opCode : " + it.next().getOpCode());
                    }
                    wakeUpThread();
                } else if (dataInfo.getBasePacket() != null) {
                    if (dataInfo.getBasePacket().getHasResponse() == 1) {
                        if (this.haveResponseDataList.size() >= 30) {
                            JL_Log.i(DataHandler.TAG, "-handlerQueue- haveResponseDataList is busy. ");
                            DataHandler.this.mBluetoothManager.errorEventCallback(new BaseError(3, ErrorCode.SUB_ERR_SEND_FAILED, "System is busy"));
                            return;
                        }
                        this.haveResponseDataList.add(dataInfo);
                    } else if (this.noResponseDataList.size() >= 60) {
                        JL_Log.i(DataHandler.TAG, "-handlerQueue- noResponseDataList is busy. ");
                        DataHandler.this.mBluetoothManager.errorEventCallback(new BaseError(3, ErrorCode.SUB_ERR_SEND_FAILED, "System is busy"));
                    } else {
                        this.noResponseDataList.add(dataInfo);
                    }
                }
            }
        }

        private void checkResponseList() {
            ArrayList<BasePacket> arrayList = new ArrayList<>();
            ArrayList<BasePacket> arrayList2 = this.mReceiveDataList;
            if (arrayList2 != null && arrayList2.size() > 0) {
                ArrayList arrayList3 = new ArrayList();
                ArrayList arrayList4 = new ArrayList();
                Iterator it = new ArrayList(this.mReceiveDataList).iterator();
                while (it.hasNext()) {
                    BasePacket basePacket = (BasePacket) it.next();
                    byte[] packSendBasePacket = ParseHelper.packSendBasePacket(basePacket);
                    if (packSendBasePacket != null) {
                        String str = DataHandler.TAG;
                        JL_Log.d(str, "recv data : [" + CHexConver.byte2HexStr(packSendBasePacket, packSendBasePacket.length) + "]");
                        if (DataHandler.this.mBluetoothManager != null) {
                            DataHandler.this.mBluetoothManager.receiveDataFromDevice(DataHandler.this.mBluetoothManager.getConnectedDevice(), packSendBasePacket);
                        }
                        if (basePacket.getType() == 1) {
                            arrayList3.add(basePacket);
                        } else {
                            arrayList.add(basePacket);
                        }
                    } else {
                        arrayList4.add(basePacket);
                    }
                }
                if (!arrayList3.isEmpty()) {
                    this.mReceiveDataList.removeAll(arrayList3);
                }
                if (arrayList4.size() > 0) {
                    this.mReceiveDataList.removeAll(arrayList4);
                }
                checkHaveResponseList(arrayList);
                return;
            }
            checkHaveResponseList(null);
        }

        private void checkHaveResponseList(ArrayList<BasePacket> arrayList) {
            if (this.haveResponseDataList.size() > 0) {
                ArrayList<DataInfo> waitResponseList = getWaitResponseList();
                String str = DataHandler.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("-checkHaveResponseList- waitList size : ");
                sb.append(waitResponseList == null ? 0 : waitResponseList.size());
                JL_Log.w(str, sb.toString());
                if (waitResponseList == null || waitResponseList.size() <= 0) {
                    return;
                }
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                long timeInMillis = Calendar.getInstance().getTimeInMillis();
                if (arrayList != null && arrayList.size() > 0) {
                    Iterator<BasePacket> it = arrayList.iterator();
                    while (it.hasNext()) {
                        final BasePacket next = it.next();
                        String str2 = DataHandler.TAG;
                        JL_Log.w(str2, "-checkHaveResponseList- opCode : " + next.getOpCode() + ", sn : " + next.getOpCodeSn());
                        Iterator<DataInfo> it2 = waitResponseList.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            DataInfo next2 = it2.next();
                            final BasePacket basePacket = next2.getBasePacket();
                            if (basePacket != null) {
                                String str3 = DataHandler.TAG;
                                JL_Log.w(str3, "-checkHaveResponseList- packet opCode : " + basePacket.getOpCode() + ", packet sn : " + basePacket.getOpCodeSn());
                            }
                            if (basePacket != null && basePacket.getOpCode() == next.getOpCode() && basePacket.getOpCodeSn() == next.getOpCodeSn()) {
                                JL_Log.w(DataHandler.TAG, "-checkHaveResponseList- callback");
                                final CommandCallback callback = next2.getCallback();
                                if (DataHandler.this.mUIHandler != null) {
                                    DataHandler.this.mUIHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.tool.DataHandler.DataHandlerThread.4
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            if (callback != null) {
                                                CommandBase convert2Command = ParseHelper.convert2Command(next);
                                                if (convert2Command == null) {
                                                    callback.onErrCode(new BaseError(3, ErrorCode.SUB_ERR_PARSE_DATA, "parse data failed."));
                                                } else {
                                                    callback.onCommandResponse(convert2Command);
                                                }
                                            }
                                            CommandHelper.getInstance().removeCommandBase(basePacket);
                                        }
                                    });
                                }
                                arrayList2.add(next);
                                arrayList3.add(next2);
                            } else {
                                if (next2.getTimeoutMs() < 500) {
                                    next2.setTimeoutMs(500);
                                }
                                Iterator<BasePacket> it3 = it;
                                if (timeInMillis - next2.getSendTime() > next2.getTimeoutMs()) {
                                    int reSendCount = next2.getReSendCount();
                                    String str4 = DataHandler.TAG;
                                    JL_Log.e(str4, "wait for response timeout !!! reSend count : " + reSendCount + ", data : " + next2);
                                    if (reSendCount >= 3) {
                                        JL_Log.e(DataHandler.TAG, "retry count over time, callbackTimeOutError.");
                                        callbackTimeOutError(next2);
                                        arrayList2.add(next);
                                        arrayList3.add(next2);
                                    } else {
                                        next2.setReSendCount(reSendCount + 1);
                                        next2.setSend(false);
                                    }
                                }
                                it = it3;
                            }
                        }
                        it = it;
                    }
                    if (arrayList2.size() > 0 && this.mReceiveDataList != null) {
                        arrayList.removeAll(arrayList2);
                        this.mReceiveDataList.removeAll(arrayList2);
                    }
                    if (arrayList.size() > 0 && this.mReceiveDataList != null) {
                        JL_Log.e(DataHandler.TAG, "-checkHaveResponseList- remove unused response.");
                        this.mReceiveDataList.removeAll(arrayList);
                    }
                    if (arrayList3.size() > 0) {
                        this.haveResponseDataList.removeAll(arrayList3);
                        arrayList3.clear();
                        waitResponseList = getWaitResponseList();
                    }
                }
                if (waitResponseList == null || waitResponseList.size() <= 0) {
                    return;
                }
                Iterator<DataInfo> it4 = waitResponseList.iterator();
                while (it4.hasNext()) {
                    DataInfo next3 = it4.next();
                    if (next3.getTimeoutMs() < 500) {
                        next3.setTimeoutMs(500);
                    }
                    if (timeInMillis - next3.getSendTime() > next3.getTimeoutMs()) {
                        int reSendCount2 = next3.getReSendCount();
                        String str5 = DataHandler.TAG;
                        JL_Log.e(str5, "wait for response timeout 222222 !!! reSend count : " + reSendCount2 + ", data : " + next3);
                        if (reSendCount2 >= 3) {
                            JL_Log.e(DataHandler.TAG, "retry count over time 222222, callbackTimeOutError.");
                            callbackTimeOutError(next3);
                            arrayList3.add(next3);
                        } else {
                            next3.setReSendCount(reSendCount2 + 1);
                            next3.setSend(false);
                        }
                    }
                }
                if (arrayList3.size() > 0) {
                    this.haveResponseDataList.removeAll(arrayList3);
                }
            } else if (arrayList == null || arrayList.size() <= 0 || this.mReceiveDataList == null) {
            } else {
                JL_Log.e(DataHandler.TAG, "-checkHaveResponseList- 22222 remove unused response.");
                this.mReceiveDataList.removeAll(arrayList);
            }
        }

        private DataInfo obtainSendInfo() {
            DataInfo dataInfo;
            int i = 0;
            if (this.noResponseDataList.size() > 0) {
                while (i < this.noResponseDataList.size()) {
                    dataInfo = this.noResponseDataList.get(i);
                    if (dataInfo.isSend()) {
                        i++;
                    }
                }
                return null;
            } else if (this.haveResponseDataList.size() > 0) {
                while (i < this.haveResponseDataList.size()) {
                    dataInfo = this.haveResponseDataList.get(i);
                    if (dataInfo.isSend()) {
                        i++;
                    }
                }
                return null;
            } else {
                return null;
            }
            return dataInfo;
        }

        private ArrayList<DataInfo> getWaitResponseList() {
            if (this.haveResponseDataList.size() > 0) {
                ArrayList<DataInfo> arrayList = new ArrayList<>();
                for (DataInfo dataInfo : this.haveResponseDataList) {
                    if (dataInfo.isSend()) {
                        arrayList.add(dataInfo);
                    }
                }
                return arrayList;
            }
            return null;
        }

        private void callbackTimeOutError(DataInfo dataInfo) {
            final CommandCallback callback = dataInfo.getCallback();
            CommandHelper.getInstance().removeCommandBase(dataInfo.getBasePacket());
            if (DataHandler.this.mUIHandler != null) {
                DataHandler.this.mUIHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.tool.DataHandler.DataHandlerThread.5
                    @Override // java.lang.Runnable
                    public void run() {
                        BaseError baseError = new BaseError(3, ErrorCode.SUB_ERR_SEND_TIMEOUT, "waiting for response timeout.");
                        CommandCallback commandCallback = callback;
                        if (commandCallback != null) {
                            commandCallback.onErrCode(baseError);
                        }
                        DataHandler.this.mBluetoothManager.errorEventCallback(baseError);
                    }
                });
            }
        }
    }

    /* loaded from: classes2.dex */
    public class WorkThread extends HandlerThread implements Handler.Callback {
        private static final int MSG_ADD_RECV_DATA = 2;
        private static final int MSG_ADD_SEND_DATA = 1;
        private Handler mWorkHandler;

        public WorkThread(String str) {
            super(str, 10);
        }

        public Handler getWorkHandler() {
            if (this.mWorkHandler == null) {
                this.mWorkHandler = new Handler(getLooper(), this);
            }
            return this.mWorkHandler;
        }

        @Override // android.os.HandlerThread
        protected void onLooperPrepared() {
            super.onLooperPrepared();
            this.mWorkHandler = new Handler(getLooper(), this);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                DataInfo dataInfo = (DataInfo) message.obj;
                if (DataHandler.this.mDataHandlerThread != null) {
                    DataHandler.this.mDataHandlerThread.tryToAddSendData(dataInfo);
                    return false;
                }
                return false;
            } else if (i != 2) {
                return false;
            } else {
                DataInfo dataInfo2 = (DataInfo) message.obj;
                if (DataHandler.this.mDataHandlerThread == null || dataInfo2 == null) {
                    return false;
                }
                DataHandler.this.mDataHandlerThread.tryToAddRecvData(dataInfo2);
                return false;
            }
        }

        public void tryToAddSendData(DataInfo dataInfo) {
            if (this.mWorkHandler == null) {
                this.mWorkHandler = new Handler(getLooper(), this);
            }
            Message obtainMessage = this.mWorkHandler.obtainMessage();
            obtainMessage.what = 1;
            obtainMessage.obj = dataInfo;
            this.mWorkHandler.sendMessage(obtainMessage);
        }

        public void tryToAddRecvData(DataInfo dataInfo) {
            if (this.mWorkHandler == null) {
                this.mWorkHandler = new Handler(getLooper(), this);
            }
            Message obtainMessage = this.mWorkHandler.obtainMessage();
            obtainMessage.what = 2;
            obtainMessage.obj = dataInfo;
            this.mWorkHandler.sendMessage(obtainMessage);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class TimerThread extends Thread {
        private volatile boolean isRunning;
        private ThreadStateListener mListener;
        private long time;

        TimerThread(long j, ThreadStateListener threadStateListener) {
            super("TimerThread");
            this.time = j;
            this.mListener = threadStateListener;
        }

        @Override // java.lang.Thread
        public synchronized void start() {
            this.isRunning = true;
            super.start();
            String str = DataHandler.TAG;
            JL_Log.w(str, "TimerThread is start....name : " + getName());
            ThreadStateListener threadStateListener = this.mListener;
            if (threadStateListener != null) {
                threadStateListener.onStart(getId());
            }
        }

        synchronized void stopThread() {
            this.isRunning = false;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (this.isRunning) {
                try {
                    Thread.sleep(this.time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (DataHandler.this.mDataHandlerThread == null) {
                    break;
                }
                DataHandler.this.mDataHandlerThread.wakeUpThread();
            }
            this.isRunning = false;
            String str = DataHandler.TAG;
            JL_Log.w(str, "TimerThread is end....name : " + getName());
            ThreadStateListener threadStateListener = this.mListener;
            if (threadStateListener != null) {
                threadStateListener.onFinish(getId());
            }
        }
    }
}
