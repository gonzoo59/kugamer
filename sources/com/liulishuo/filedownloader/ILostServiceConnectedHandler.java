package com.liulishuo.filedownloader;

import com.liulishuo.filedownloader.BaseDownloadTask;
/* loaded from: classes2.dex */
public interface ILostServiceConnectedHandler {
    boolean dispatchTaskStart(BaseDownloadTask.IRunningTask iRunningTask);

    boolean isInWaitingList(BaseDownloadTask.IRunningTask iRunningTask);

    void taskWorkFine(BaseDownloadTask.IRunningTask iRunningTask);
}
