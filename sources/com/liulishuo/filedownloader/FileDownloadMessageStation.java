package com.liulishuo.filedownloader;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.liulishuo.filedownloader.util.FileDownloadExecutors;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
/* loaded from: classes2.dex */
public class FileDownloadMessageStation {
    public static final int DEFAULT_INTERVAL = 10;
    public static final int DEFAULT_SUB_PACKAGE_SIZE = 5;
    static final int DISPOSE_MESSENGER_LIST = 2;
    static final int HANDOVER_A_MESSENGER = 1;
    private final ArrayList<IFileDownloadMessenger> disposingList;
    private final Handler handler;
    private final Object queueLock;
    private final LinkedBlockingQueue<IFileDownloadMessenger> waitingQueue;
    private static final Executor BLOCK_COMPLETED_POOL = FileDownloadExecutors.newDefaultThreadPool(5, "BlockCompleted");
    static int INTERVAL = 10;
    static int SUB_PACKAGE_SIZE = 5;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class HolderClass {
        private static final FileDownloadMessageStation INSTANCE = new FileDownloadMessageStation();

        private HolderClass() {
        }
    }

    public static FileDownloadMessageStation getImpl() {
        return HolderClass.INSTANCE;
    }

    private FileDownloadMessageStation() {
        this.queueLock = new Object();
        this.disposingList = new ArrayList<>();
        this.handler = new Handler(Looper.getMainLooper(), new UIHandlerCallback());
        this.waitingQueue = new LinkedBlockingQueue<>();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void requestEnqueue(IFileDownloadMessenger iFileDownloadMessenger) {
        requestEnqueue(iFileDownloadMessenger, false);
    }

    void requestEnqueue(IFileDownloadMessenger iFileDownloadMessenger, boolean z) {
        if (iFileDownloadMessenger.handoverDirectly()) {
            iFileDownloadMessenger.handoverMessage();
        } else if (interceptBlockCompleteMessage(iFileDownloadMessenger)) {
        } else {
            if (!isIntervalValid() && !this.waitingQueue.isEmpty()) {
                synchronized (this.queueLock) {
                    if (!this.waitingQueue.isEmpty()) {
                        Iterator<IFileDownloadMessenger> it = this.waitingQueue.iterator();
                        while (it.hasNext()) {
                            handoverInUIThread(it.next());
                        }
                    }
                    this.waitingQueue.clear();
                }
            }
            if (!isIntervalValid() || z) {
                handoverInUIThread(iFileDownloadMessenger);
            } else {
                enqueue(iFileDownloadMessenger);
            }
        }
    }

    private void handoverInUIThread(IFileDownloadMessenger iFileDownloadMessenger) {
        Handler handler = this.handler;
        handler.sendMessage(handler.obtainMessage(1, iFileDownloadMessenger));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean interceptBlockCompleteMessage(final IFileDownloadMessenger iFileDownloadMessenger) {
        if (iFileDownloadMessenger.isBlockingCompleted()) {
            BLOCK_COMPLETED_POOL.execute(new Runnable() { // from class: com.liulishuo.filedownloader.FileDownloadMessageStation.1
                @Override // java.lang.Runnable
                public void run() {
                    IFileDownloadMessenger.this.handoverMessage();
                }
            });
            return true;
        }
        return false;
    }

    private void enqueue(IFileDownloadMessenger iFileDownloadMessenger) {
        synchronized (this.queueLock) {
            this.waitingQueue.offer(iFileDownloadMessenger);
        }
        push();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void push() {
        synchronized (this.queueLock) {
            if (this.disposingList.isEmpty()) {
                if (this.waitingQueue.isEmpty()) {
                    return;
                }
                int i = 0;
                if (!isIntervalValid()) {
                    this.waitingQueue.drainTo(this.disposingList);
                } else {
                    int i2 = INTERVAL;
                    int min = Math.min(this.waitingQueue.size(), SUB_PACKAGE_SIZE);
                    while (i < min) {
                        this.disposingList.add(this.waitingQueue.remove());
                        i++;
                    }
                    i = i2;
                }
                Handler handler = this.handler;
                handler.sendMessageDelayed(handler.obtainMessage(2, this.disposingList), i);
            }
        }
    }

    /* loaded from: classes2.dex */
    private static class UIHandlerCallback implements Handler.Callback {
        private UIHandlerCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                ((IFileDownloadMessenger) message.obj).handoverMessage();
            } else if (message.what == 2) {
                dispose((ArrayList) message.obj);
                FileDownloadMessageStation.getImpl().push();
            }
            return true;
        }

        private void dispose(ArrayList<IFileDownloadMessenger> arrayList) {
            Iterator<IFileDownloadMessenger> it = arrayList.iterator();
            while (it.hasNext()) {
                IFileDownloadMessenger next = it.next();
                if (!FileDownloadMessageStation.interceptBlockCompleteMessage(next)) {
                    next.handoverMessage();
                }
            }
            arrayList.clear();
        }
    }

    public static boolean isIntervalValid() {
        return INTERVAL > 0;
    }
}
