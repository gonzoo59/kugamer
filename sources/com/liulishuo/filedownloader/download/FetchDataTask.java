package com.liulishuo.filedownloader.download;

import android.os.SystemClock;
import com.liulishuo.filedownloader.connection.FileDownloadConnection;
import com.liulishuo.filedownloader.database.FileDownloadDatabase;
import com.liulishuo.filedownloader.stream.FileDownloadOutputStream;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.IOException;
/* loaded from: classes2.dex */
public class FetchDataTask {
    static final int BUFFER_SIZE = 4096;
    private final ProcessCallback callback;
    private final FileDownloadConnection connection;
    private final int connectionIndex;
    private final long contentLength;
    long currentOffset;
    private final FileDownloadDatabase database;
    private final int downloadId;
    private final long endOffset;
    private final DownloadRunnable hostRunnable;
    private final boolean isWifiRequired;
    private volatile long lastSyncBytes;
    private volatile long lastSyncTimestamp;
    private FileDownloadOutputStream outputStream;
    private final String path;
    private volatile boolean paused;
    private final long startOffset;

    public void pause() {
        this.paused = true;
    }

    private FetchDataTask(FileDownloadConnection fileDownloadConnection, ConnectionProfile connectionProfile, DownloadRunnable downloadRunnable, int i, int i2, boolean z, ProcessCallback processCallback, String str) {
        this.lastSyncBytes = 0L;
        this.lastSyncTimestamp = 0L;
        this.callback = processCallback;
        this.path = str;
        this.connection = fileDownloadConnection;
        this.isWifiRequired = z;
        this.hostRunnable = downloadRunnable;
        this.connectionIndex = i2;
        this.downloadId = i;
        this.database = CustomComponentHolder.getImpl().getDatabaseInstance();
        this.startOffset = connectionProfile.startOffset;
        this.endOffset = connectionProfile.endOffset;
        this.currentOffset = connectionProfile.currentOffset;
        this.contentLength = connectionProfile.contentLength;
    }

    /* JADX WARN: Code restructure failed: missing block: B:122:0x01f9, code lost:
        throw new com.liulishuo.filedownloader.exception.FileDownloadNetworkPolicyException();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void run() throws java.io.IOException, java.lang.IllegalAccessException, java.lang.IllegalArgumentException, com.liulishuo.filedownloader.exception.FileDownloadGiveUpRetryException {
        /*
            Method dump skipped, instructions count: 589
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.liulishuo.filedownloader.download.FetchDataTask.run():void");
    }

    private void checkAndSync() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (FileDownloadUtils.isNeedSync(this.currentOffset - this.lastSyncBytes, elapsedRealtime - this.lastSyncTimestamp)) {
            sync();
            this.lastSyncBytes = this.currentOffset;
            this.lastSyncTimestamp = elapsedRealtime;
        }
    }

    private void sync() {
        boolean z;
        long uptimeMillis = SystemClock.uptimeMillis();
        try {
            this.outputStream.flushAndSync();
            z = true;
        } catch (IOException e) {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "Because of the system cannot guarantee that all the buffers have been synchronized with physical media, or write to filefailed, we just not flushAndSync process to database too %s", e);
            }
            z = false;
        }
        if (z) {
            int i = this.connectionIndex;
            if (i >= 0) {
                this.database.updateConnectionModel(this.downloadId, i, this.currentOffset);
            } else {
                this.callback.syncProgressFromCache();
            }
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "require flushAndSync id[%d] index[%d] offset[%d], consume[%d]", Integer.valueOf(this.downloadId), Integer.valueOf(this.connectionIndex), Long.valueOf(this.currentOffset), Long.valueOf(SystemClock.uptimeMillis() - uptimeMillis));
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        ProcessCallback callback;
        FileDownloadConnection connection;
        Integer connectionIndex;
        ConnectionProfile connectionProfile;
        Integer downloadId;
        DownloadRunnable downloadRunnable;
        Boolean isWifiRequired;
        String path;

        public Builder setConnection(FileDownloadConnection fileDownloadConnection) {
            this.connection = fileDownloadConnection;
            return this;
        }

        public Builder setConnectionProfile(ConnectionProfile connectionProfile) {
            this.connectionProfile = connectionProfile;
            return this;
        }

        public Builder setCallback(ProcessCallback processCallback) {
            this.callback = processCallback;
            return this;
        }

        public Builder setPath(String str) {
            this.path = str;
            return this;
        }

        public Builder setWifiRequired(boolean z) {
            this.isWifiRequired = Boolean.valueOf(z);
            return this;
        }

        public Builder setHost(DownloadRunnable downloadRunnable) {
            this.downloadRunnable = downloadRunnable;
            return this;
        }

        public Builder setConnectionIndex(int i) {
            this.connectionIndex = Integer.valueOf(i);
            return this;
        }

        public Builder setDownloadId(int i) {
            this.downloadId = Integer.valueOf(i);
            return this;
        }

        public FetchDataTask build() throws IllegalArgumentException {
            FileDownloadConnection fileDownloadConnection;
            ConnectionProfile connectionProfile;
            Integer num;
            if (this.isWifiRequired == null || (fileDownloadConnection = this.connection) == null || (connectionProfile = this.connectionProfile) == null || this.callback == null || this.path == null || (num = this.downloadId) == null || this.connectionIndex == null) {
                throw new IllegalArgumentException();
            }
            return new FetchDataTask(fileDownloadConnection, connectionProfile, this.downloadRunnable, num.intValue(), this.connectionIndex.intValue(), this.isWifiRequired.booleanValue(), this.callback, this.path);
        }
    }
}
