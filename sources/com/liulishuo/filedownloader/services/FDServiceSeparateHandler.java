package com.liulishuo.filedownloader.services;

import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import com.liulishuo.filedownloader.i.IFileDownloadIPCCallback;
import com.liulishuo.filedownloader.i.IFileDownloadIPCService;
import com.liulishuo.filedownloader.message.MessageSnapshot;
import com.liulishuo.filedownloader.message.MessageSnapshotFlow;
import com.liulishuo.filedownloader.model.FileDownloadHeader;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import java.lang.ref.WeakReference;
/* loaded from: classes2.dex */
public class FDServiceSeparateHandler extends IFileDownloadIPCService.Stub implements MessageSnapshotFlow.MessageReceiver, IFileDownloadServiceHandler {
    private final RemoteCallbackList<IFileDownloadIPCCallback> callbackList = new RemoteCallbackList<>();
    private final FileDownloadManager downloadManager;
    private final WeakReference<FileDownloadService> wService;

    @Override // com.liulishuo.filedownloader.services.IFileDownloadServiceHandler
    public IBinder onBind(Intent intent) {
        return this;
    }

    @Override // com.liulishuo.filedownloader.services.IFileDownloadServiceHandler
    public void onStartCommand(Intent intent, int i, int i2) {
    }

    private synchronized int callback(MessageSnapshot messageSnapshot) {
        int beginBroadcast;
        RemoteCallbackList<IFileDownloadIPCCallback> remoteCallbackList;
        beginBroadcast = this.callbackList.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.callbackList.getBroadcastItem(i).callback(messageSnapshot);
            } catch (RemoteException e) {
                FileDownloadLog.e(this, e, "callback error", new Object[0]);
                remoteCallbackList = this.callbackList;
            }
        }
        remoteCallbackList = this.callbackList;
        remoteCallbackList.finishBroadcast();
        return beginBroadcast;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FDServiceSeparateHandler(WeakReference<FileDownloadService> weakReference, FileDownloadManager fileDownloadManager) {
        this.wService = weakReference;
        this.downloadManager = fileDownloadManager;
        MessageSnapshotFlow.getImpl().setReceiver(this);
    }

    @Override // com.liulishuo.filedownloader.i.IFileDownloadIPCService
    public void registerCallback(IFileDownloadIPCCallback iFileDownloadIPCCallback) throws RemoteException {
        this.callbackList.register(iFileDownloadIPCCallback);
    }

    @Override // com.liulishuo.filedownloader.i.IFileDownloadIPCService
    public void unregisterCallback(IFileDownloadIPCCallback iFileDownloadIPCCallback) throws RemoteException {
        this.callbackList.unregister(iFileDownloadIPCCallback);
    }

    @Override // com.liulishuo.filedownloader.i.IFileDownloadIPCService
    public boolean checkDownloading(String str, String str2) throws RemoteException {
        return this.downloadManager.isDownloading(str, str2);
    }

    @Override // com.liulishuo.filedownloader.i.IFileDownloadIPCService
    public void start(String str, String str2, boolean z, int i, int i2, int i3, boolean z2, FileDownloadHeader fileDownloadHeader, boolean z3) throws RemoteException {
        this.downloadManager.start(str, str2, z, i, i2, i3, z2, fileDownloadHeader, z3);
    }

    @Override // com.liulishuo.filedownloader.i.IFileDownloadIPCService
    public boolean pause(int i) throws RemoteException {
        return this.downloadManager.pause(i);
    }

    @Override // com.liulishuo.filedownloader.i.IFileDownloadIPCService
    public void pauseAllTasks() throws RemoteException {
        this.downloadManager.pauseAll();
    }

    @Override // com.liulishuo.filedownloader.i.IFileDownloadIPCService
    public boolean setMaxNetworkThreadCount(int i) throws RemoteException {
        return this.downloadManager.setMaxNetworkThreadCount(i);
    }

    @Override // com.liulishuo.filedownloader.i.IFileDownloadIPCService
    public long getSofar(int i) throws RemoteException {
        return this.downloadManager.getSoFar(i);
    }

    @Override // com.liulishuo.filedownloader.i.IFileDownloadIPCService
    public long getTotal(int i) throws RemoteException {
        return this.downloadManager.getTotal(i);
    }

    @Override // com.liulishuo.filedownloader.i.IFileDownloadIPCService
    public byte getStatus(int i) throws RemoteException {
        return this.downloadManager.getStatus(i);
    }

    @Override // com.liulishuo.filedownloader.i.IFileDownloadIPCService
    public boolean isIdle() throws RemoteException {
        return this.downloadManager.isIdle();
    }

    @Override // com.liulishuo.filedownloader.i.IFileDownloadIPCService
    public void startForeground(int i, Notification notification) throws RemoteException {
        WeakReference<FileDownloadService> weakReference = this.wService;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        this.wService.get().startForeground(i, notification);
    }

    @Override // com.liulishuo.filedownloader.i.IFileDownloadIPCService
    public void stopForeground(boolean z) throws RemoteException {
        WeakReference<FileDownloadService> weakReference = this.wService;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        this.wService.get().stopForeground(z);
    }

    @Override // com.liulishuo.filedownloader.i.IFileDownloadIPCService
    public boolean clearTaskData(int i) throws RemoteException {
        return this.downloadManager.clearTaskData(i);
    }

    @Override // com.liulishuo.filedownloader.i.IFileDownloadIPCService
    public void clearAllTaskData() throws RemoteException {
        this.downloadManager.clearAllTaskData();
    }

    @Override // com.liulishuo.filedownloader.services.IFileDownloadServiceHandler
    public void onDestroy() {
        MessageSnapshotFlow.getImpl().setReceiver(null);
    }

    @Override // com.liulishuo.filedownloader.message.MessageSnapshotFlow.MessageReceiver
    public void receive(MessageSnapshot messageSnapshot) {
        callback(messageSnapshot);
    }
}
