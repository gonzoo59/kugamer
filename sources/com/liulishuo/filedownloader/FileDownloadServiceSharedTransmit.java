package com.liulishuo.filedownloader;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.liulishuo.filedownloader.event.DownloadServiceConnectChangedEvent;
import com.liulishuo.filedownloader.model.FileDownloadHeader;
import com.liulishuo.filedownloader.services.FDServiceSharedHandler;
import com.liulishuo.filedownloader.services.FileDownloadService;
import com.liulishuo.filedownloader.util.DownloadServiceNotConnectedHelper;
import com.liulishuo.filedownloader.util.ExtraKeys;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.util.ArrayList;
import java.util.List;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class FileDownloadServiceSharedTransmit implements IFileDownloadServiceProxy, FDServiceSharedHandler.FileDownloadServiceSharedConnection {
    private static final Class<?> SERVICE_CLASS = FileDownloadService.SharedMainProcessService.class;
    private FDServiceSharedHandler handler;
    private boolean runServiceForeground = false;
    private final ArrayList<Runnable> connectedRunnableList = new ArrayList<>();

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public boolean start(String str, String str2, boolean z, int i, int i2, int i3, boolean z2, FileDownloadHeader fileDownloadHeader, boolean z3) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.start(str, str2, z);
        }
        this.handler.start(str, str2, z, i, i2, i3, z2, fileDownloadHeader, z3);
        return true;
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public boolean pause(int i) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.pause(i);
        }
        return this.handler.pause(i);
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public boolean isDownloading(String str, String str2) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.isDownloading(str, str2);
        }
        return this.handler.checkDownloading(str, str2);
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public long getSofar(int i) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.getSofar(i);
        }
        return this.handler.getSofar(i);
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public long getTotal(int i) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.getTotal(i);
        }
        return this.handler.getTotal(i);
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public byte getStatus(int i) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.getStatus(i);
        }
        return this.handler.getStatus(i);
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public void pauseAllTasks() {
        if (!isConnected()) {
            DownloadServiceNotConnectedHelper.pauseAllTasks();
        } else {
            this.handler.pauseAllTasks();
        }
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public boolean isIdle() {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.isIdle();
        }
        return this.handler.isIdle();
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public boolean isConnected() {
        return this.handler != null;
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public void bindStartByContext(Context context) {
        bindStartByContext(context, null);
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public void bindStartByContext(Context context, Runnable runnable) {
        if (runnable != null && !this.connectedRunnableList.contains(runnable)) {
            this.connectedRunnableList.add(runnable);
        }
        Intent intent = new Intent(context, SERVICE_CLASS);
        boolean needMakeServiceForeground = FileDownloadUtils.needMakeServiceForeground(context);
        this.runServiceForeground = needMakeServiceForeground;
        intent.putExtra(ExtraKeys.IS_FOREGROUND, needMakeServiceForeground);
        if (this.runServiceForeground) {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "start foreground service", new Object[0]);
            }
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent);
                return;
            }
            return;
        }
        context.startService(intent);
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public void unbindByContext(Context context) {
        context.stopService(new Intent(context, SERVICE_CLASS));
        this.handler = null;
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public void startForeground(int i, Notification notification) {
        if (!isConnected()) {
            DownloadServiceNotConnectedHelper.startForeground(i, notification);
        } else {
            this.handler.startForeground(i, notification);
        }
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public void stopForeground(boolean z) {
        if (!isConnected()) {
            DownloadServiceNotConnectedHelper.stopForeground(z);
            return;
        }
        this.handler.stopForeground(z);
        this.runServiceForeground = false;
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public boolean setMaxNetworkThreadCount(int i) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.setMaxNetworkThreadCount(i);
        }
        return this.handler.setMaxNetworkThreadCount(i);
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public boolean clearTaskData(int i) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.clearTaskData(i);
        }
        return this.handler.clearTaskData(i);
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public void clearAllTaskData() {
        if (!isConnected()) {
            DownloadServiceNotConnectedHelper.clearAllTaskData();
        } else {
            this.handler.clearAllTaskData();
        }
    }

    @Override // com.liulishuo.filedownloader.IFileDownloadServiceProxy
    public boolean isRunServiceForeground() {
        return this.runServiceForeground;
    }

    @Override // com.liulishuo.filedownloader.services.FDServiceSharedHandler.FileDownloadServiceSharedConnection
    public void onConnected(FDServiceSharedHandler fDServiceSharedHandler) {
        this.handler = fDServiceSharedHandler;
        this.connectedRunnableList.clear();
        for (Runnable runnable : (List) this.connectedRunnableList.clone()) {
            runnable.run();
        }
        FileDownloadEventPool.getImpl().asyncPublishInNewThread(new DownloadServiceConnectChangedEvent(DownloadServiceConnectChangedEvent.ConnectStatus.connected, SERVICE_CLASS));
    }

    @Override // com.liulishuo.filedownloader.services.FDServiceSharedHandler.FileDownloadServiceSharedConnection
    public void onDisconnected() {
        this.handler = null;
        FileDownloadEventPool.getImpl().asyncPublishInNewThread(new DownloadServiceConnectChangedEvent(DownloadServiceConnectChangedEvent.ConnectStatus.disconnected, SERVICE_CLASS));
    }
}
