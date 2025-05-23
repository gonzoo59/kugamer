package com.liulishuo.filedownloader.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.liulishuo.filedownloader.BaseDownloadTask;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/* loaded from: classes2.dex */
public class FileDownloadSerialQueue {
    public static final int ID_INVALID = 0;
    private static final int WHAT_NEXT = 1;
    final SerialFinishCallback finishCallback;
    private final Handler mHandler;
    private final HandlerThread mHandlerThread;
    volatile BaseDownloadTask workingTask;
    private final Object operationLock = new Object();
    private final BlockingQueue<BaseDownloadTask> mTasks = new LinkedBlockingQueue();
    private final List<BaseDownloadTask> pausedList = new ArrayList();
    volatile boolean paused = false;

    public FileDownloadSerialQueue() {
        HandlerThread handlerThread = new HandlerThread(FileDownloadUtils.getThreadPoolName("SerialDownloadManager"));
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper(), new SerialLoop());
        this.finishCallback = new SerialFinishCallback(new WeakReference(this));
        sendNext();
    }

    public void enqueue(BaseDownloadTask baseDownloadTask) {
        synchronized (this.finishCallback) {
            if (this.paused) {
                this.pausedList.add(baseDownloadTask);
                return;
            }
            try {
                this.mTasks.put(baseDownloadTask);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        synchronized (this.finishCallback) {
            if (this.paused) {
                FileDownloadLog.w(this, "require pause this queue(remain %d), but it has already been paused", Integer.valueOf(this.mTasks.size()));
                return;
            }
            this.paused = true;
            this.mTasks.drainTo(this.pausedList);
            if (this.workingTask != null) {
                this.workingTask.removeFinishListener(this.finishCallback);
                this.workingTask.pause();
            }
        }
    }

    public void resume() {
        synchronized (this.finishCallback) {
            if (!this.paused) {
                FileDownloadLog.w(this, "require resume this queue(remain %d), but it is still running", Integer.valueOf(this.mTasks.size()));
                return;
            }
            this.paused = false;
            this.mTasks.addAll(this.pausedList);
            this.pausedList.clear();
            if (this.workingTask == null) {
                sendNext();
            } else {
                this.workingTask.addFinishListener(this.finishCallback);
                this.workingTask.start();
            }
        }
    }

    public int getWorkingTaskId() {
        if (this.workingTask != null) {
            return this.workingTask.getId();
        }
        return 0;
    }

    public int getWaitingTaskCount() {
        return this.mTasks.size() + this.pausedList.size();
    }

    public List<BaseDownloadTask> shutdown() {
        ArrayList arrayList;
        synchronized (this.finishCallback) {
            if (this.workingTask != null) {
                pause();
            }
            arrayList = new ArrayList(this.pausedList);
            this.pausedList.clear();
            this.mHandler.removeMessages(1);
            this.mHandlerThread.interrupt();
            this.mHandlerThread.quit();
        }
        return arrayList;
    }

    /* loaded from: classes2.dex */
    private class SerialLoop implements Handler.Callback {
        private SerialLoop() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                try {
                    if (FileDownloadSerialQueue.this.paused) {
                        return false;
                    }
                    FileDownloadSerialQueue fileDownloadSerialQueue = FileDownloadSerialQueue.this;
                    fileDownloadSerialQueue.workingTask = (BaseDownloadTask) fileDownloadSerialQueue.mTasks.take();
                    FileDownloadSerialQueue.this.workingTask.addFinishListener(FileDownloadSerialQueue.this.finishCallback).start();
                } catch (InterruptedException unused) {
                    return false;
                }
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SerialFinishCallback implements BaseDownloadTask.FinishListener {
        private final WeakReference<FileDownloadSerialQueue> mQueueWeakReference;

        SerialFinishCallback(WeakReference<FileDownloadSerialQueue> weakReference) {
            this.mQueueWeakReference = weakReference;
        }

        @Override // com.liulishuo.filedownloader.BaseDownloadTask.FinishListener
        public synchronized void over(BaseDownloadTask baseDownloadTask) {
            baseDownloadTask.removeFinishListener(this);
            WeakReference<FileDownloadSerialQueue> weakReference = this.mQueueWeakReference;
            if (weakReference == null) {
                return;
            }
            FileDownloadSerialQueue fileDownloadSerialQueue = weakReference.get();
            if (fileDownloadSerialQueue == null) {
                return;
            }
            fileDownloadSerialQueue.workingTask = null;
            if (fileDownloadSerialQueue.paused) {
                return;
            }
            fileDownloadSerialQueue.sendNext();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendNext() {
        this.mHandler.sendEmptyMessage(1);
    }
}
