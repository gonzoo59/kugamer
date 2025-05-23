package com.liulishuo.filedownloader.download;
/* loaded from: classes2.dex */
public interface ProcessCallback {
    boolean isRetry(Exception exc);

    void onCompleted(DownloadRunnable downloadRunnable, long j, long j2);

    void onError(Exception exc);

    void onProgress(long j);

    void onRetry(Exception exc);

    void syncProgressFromCache();
}
