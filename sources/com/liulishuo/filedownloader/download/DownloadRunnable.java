package com.liulishuo.filedownloader.download;

import android.os.Process;
import com.liulishuo.filedownloader.connection.FileDownloadConnection;
import com.liulishuo.filedownloader.database.FileDownloadDatabase;
import com.liulishuo.filedownloader.download.ConnectTask;
import com.liulishuo.filedownloader.download.FetchDataTask;
import com.liulishuo.filedownloader.exception.FileDownloadGiveUpRetryException;
import com.liulishuo.filedownloader.model.ConnectionModel;
import com.liulishuo.filedownloader.model.FileDownloadHeader;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.IOException;
import java.net.SocketException;
/* loaded from: classes2.dex */
public class DownloadRunnable implements Runnable {
    private final ProcessCallback callback;
    private final ConnectTask connectTask;
    final int connectionIndex;
    private final int downloadId;
    private FetchDataTask fetchDataTask;
    private final boolean isWifiRequired;
    private final String path;
    private volatile boolean paused;

    private DownloadRunnable(int i, int i2, ConnectTask connectTask, ProcessCallback processCallback, boolean z, String str) {
        this.downloadId = i;
        this.connectionIndex = i2;
        this.paused = false;
        this.callback = processCallback;
        this.path = str;
        this.connectTask = connectTask;
        this.isWifiRequired = z;
    }

    public void pause() {
        this.paused = true;
        FetchDataTask fetchDataTask = this.fetchDataTask;
        if (fetchDataTask != null) {
            fetchDataTask.pause();
        }
    }

    public void discard() {
        pause();
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean z;
        Exception e;
        FetchDataTask.Builder builder;
        Process.setThreadPriority(10);
        long j = this.connectTask.getProfile().currentOffset;
        FileDownloadConnection fileDownloadConnection = null;
        boolean z2 = false;
        while (!this.paused) {
            try {
                try {
                    fileDownloadConnection = this.connectTask.connect();
                    int responseCode = fileDownloadConnection.getResponseCode();
                    if (FileDownloadLog.NEED_LOG) {
                        FileDownloadLog.d(this, "the connection[%d] for %d, is connected %s with code[%d]", Integer.valueOf(this.connectionIndex), Integer.valueOf(this.downloadId), this.connectTask.getProfile(), Integer.valueOf(responseCode));
                    }
                    if (responseCode != 206 && responseCode != 200) {
                        throw new SocketException(FileDownloadUtils.formatString("Connection failed with request[%s] response[%s] http-state[%d] on task[%d-%d], which is changed after verify connection, so please try again.", this.connectTask.getRequestHeader(), fileDownloadConnection.getResponseHeaderFields(), Integer.valueOf(responseCode), Integer.valueOf(this.downloadId), Integer.valueOf(this.connectionIndex)));
                        break;
                    }
                } catch (FileDownloadGiveUpRetryException | IOException | IllegalAccessException | IllegalArgumentException e2) {
                    e = e2;
                    z = false;
                }
            } catch (FileDownloadGiveUpRetryException | IOException | IllegalAccessException | IllegalArgumentException e3) {
                z = z2;
                e = e3;
            }
            try {
                builder = new FetchDataTask.Builder();
            } catch (FileDownloadGiveUpRetryException | IOException | IllegalAccessException | IllegalArgumentException e4) {
                e = e4;
                z = true;
                try {
                    if (!this.callback.isRetry(e)) {
                        this.callback.onError(e);
                        if (fileDownloadConnection == null) {
                            return;
                        }
                    } else if (z && this.fetchDataTask == null) {
                        FileDownloadLog.w(this, "it is valid to retry and connection is valid but create fetch-data-task failed, so give up directly with %s", e);
                        this.callback.onError(e);
                        if (fileDownloadConnection == null) {
                            return;
                        }
                    } else {
                        if (this.fetchDataTask != null) {
                            long downloadedOffset = getDownloadedOffset();
                            if (downloadedOffset > 0) {
                                this.connectTask.updateConnectionProfile(downloadedOffset);
                            }
                        }
                        this.callback.onRetry(e);
                        if (fileDownloadConnection != null) {
                            fileDownloadConnection.ending();
                        }
                        z2 = z;
                    }
                    return;
                } finally {
                    if (fileDownloadConnection != null) {
                        fileDownloadConnection.ending();
                    }
                }
            }
            if (this.paused) {
                if (fileDownloadConnection != null) {
                    fileDownloadConnection.ending();
                    return;
                }
                return;
            }
            FetchDataTask build = builder.setDownloadId(this.downloadId).setConnectionIndex(this.connectionIndex).setCallback(this.callback).setHost(this).setWifiRequired(this.isWifiRequired).setConnection(fileDownloadConnection).setConnectionProfile(this.connectTask.getProfile()).setPath(this.path).build();
            this.fetchDataTask = build;
            build.run();
            if (this.paused) {
                this.fetchDataTask.pause();
            }
            if (fileDownloadConnection == null) {
                return;
            }
            return;
        }
        if (fileDownloadConnection != null) {
            fileDownloadConnection.ending();
        }
    }

    private long getDownloadedOffset() {
        FileDownloadDatabase databaseInstance = CustomComponentHolder.getImpl().getDatabaseInstance();
        if (this.connectionIndex >= 0) {
            for (ConnectionModel connectionModel : databaseInstance.findConnectionModel(this.downloadId)) {
                if (connectionModel.getIndex() == this.connectionIndex) {
                    return connectionModel.getCurrentOffset();
                }
            }
            return 0L;
        }
        FileDownloadModel find = databaseInstance.find(this.downloadId);
        if (find != null) {
            return find.getSoFar();
        }
        return 0L;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private ProcessCallback callback;
        private final ConnectTask.Builder connectTaskBuilder = new ConnectTask.Builder();
        private Integer connectionIndex;
        private Boolean isWifiRequired;
        private String path;

        public Builder setCallback(ProcessCallback processCallback) {
            this.callback = processCallback;
            return this;
        }

        public Builder setId(int i) {
            this.connectTaskBuilder.setDownloadId(i);
            return this;
        }

        public Builder setUrl(String str) {
            this.connectTaskBuilder.setUrl(str);
            return this;
        }

        public Builder setEtag(String str) {
            this.connectTaskBuilder.setEtag(str);
            return this;
        }

        public Builder setHeader(FileDownloadHeader fileDownloadHeader) {
            this.connectTaskBuilder.setHeader(fileDownloadHeader);
            return this;
        }

        public Builder setConnectionModel(ConnectionProfile connectionProfile) {
            this.connectTaskBuilder.setConnectionProfile(connectionProfile);
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

        public Builder setConnectionIndex(Integer num) {
            this.connectionIndex = num;
            return this;
        }

        public DownloadRunnable build() {
            if (this.callback == null || this.path == null || this.isWifiRequired == null || this.connectionIndex == null) {
                throw new IllegalArgumentException(FileDownloadUtils.formatString("%s %s %B", this.callback, this.path, this.isWifiRequired));
            }
            ConnectTask build = this.connectTaskBuilder.build();
            return new DownloadRunnable(build.downloadId, this.connectionIndex.intValue(), build, this.callback, this.isWifiRequired.booleanValue(), this.path);
        }

        DownloadRunnable buildForTest(ConnectTask connectTask) {
            return new DownloadRunnable(connectTask.downloadId, 0, connectTask, this.callback, false, "");
        }
    }
}
