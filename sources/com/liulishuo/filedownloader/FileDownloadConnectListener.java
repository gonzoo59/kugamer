package com.liulishuo.filedownloader;

import com.liulishuo.filedownloader.event.DownloadServiceConnectChangedEvent;
import com.liulishuo.filedownloader.event.IDownloadEvent;
import com.liulishuo.filedownloader.event.IDownloadListener;
/* loaded from: classes2.dex */
public abstract class FileDownloadConnectListener extends IDownloadListener {
    private DownloadServiceConnectChangedEvent.ConnectStatus mConnectStatus;

    public abstract void connected();

    public abstract void disconnected();

    @Override // com.liulishuo.filedownloader.event.IDownloadListener
    public boolean callback(IDownloadEvent iDownloadEvent) {
        if (iDownloadEvent instanceof DownloadServiceConnectChangedEvent) {
            DownloadServiceConnectChangedEvent.ConnectStatus status = ((DownloadServiceConnectChangedEvent) iDownloadEvent).getStatus();
            this.mConnectStatus = status;
            if (status == DownloadServiceConnectChangedEvent.ConnectStatus.connected) {
                connected();
                return false;
            }
            disconnected();
            return false;
        }
        return false;
    }

    public DownloadServiceConnectChangedEvent.ConnectStatus getConnectStatus() {
        return this.mConnectStatus;
    }
}
