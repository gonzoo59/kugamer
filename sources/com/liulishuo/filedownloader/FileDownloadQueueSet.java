package com.liulishuo.filedownloader;

import com.liulishuo.filedownloader.BaseDownloadTask;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class FileDownloadQueueSet {
    private Integer autoRetryTimes;
    private Integer callbackProgressMinIntervalMillis;
    private Integer callbackProgressTimes;
    private String directory;
    private Boolean isForceReDownload;
    private boolean isSerial;
    private Boolean isWifiRequired;
    private Boolean syncCallback;
    private Object tag;
    private FileDownloadListener target;
    private List<BaseDownloadTask.FinishListener> taskFinishListenerList;
    private BaseDownloadTask[] tasks;

    public FileDownloadQueueSet(FileDownloadListener fileDownloadListener) {
        if (fileDownloadListener == null) {
            throw new IllegalArgumentException("create FileDownloadQueueSet must with valid target!");
        }
        this.target = fileDownloadListener;
    }

    public FileDownloadQueueSet downloadTogether(BaseDownloadTask... baseDownloadTaskArr) {
        this.isSerial = false;
        this.tasks = baseDownloadTaskArr;
        return this;
    }

    public FileDownloadQueueSet downloadTogether(List<BaseDownloadTask> list) {
        this.isSerial = false;
        BaseDownloadTask[] baseDownloadTaskArr = new BaseDownloadTask[list.size()];
        this.tasks = baseDownloadTaskArr;
        list.toArray(baseDownloadTaskArr);
        return this;
    }

    public FileDownloadQueueSet downloadSequentially(BaseDownloadTask... baseDownloadTaskArr) {
        this.isSerial = true;
        this.tasks = baseDownloadTaskArr;
        return this;
    }

    public FileDownloadQueueSet downloadSequentially(List<BaseDownloadTask> list) {
        this.isSerial = true;
        BaseDownloadTask[] baseDownloadTaskArr = new BaseDownloadTask[list.size()];
        this.tasks = baseDownloadTaskArr;
        list.toArray(baseDownloadTaskArr);
        return this;
    }

    public void reuseAndStart() {
        for (BaseDownloadTask baseDownloadTask : this.tasks) {
            baseDownloadTask.reuse();
        }
        start();
    }

    public void start() {
        BaseDownloadTask[] baseDownloadTaskArr;
        for (BaseDownloadTask baseDownloadTask : this.tasks) {
            baseDownloadTask.setListener(this.target);
            Integer num = this.autoRetryTimes;
            if (num != null) {
                baseDownloadTask.setAutoRetryTimes(num.intValue());
            }
            Boolean bool = this.syncCallback;
            if (bool != null) {
                baseDownloadTask.setSyncCallback(bool.booleanValue());
            }
            Boolean bool2 = this.isForceReDownload;
            if (bool2 != null) {
                baseDownloadTask.setForceReDownload(bool2.booleanValue());
            }
            Integer num2 = this.callbackProgressTimes;
            if (num2 != null) {
                baseDownloadTask.setCallbackProgressTimes(num2.intValue());
            }
            Integer num3 = this.callbackProgressMinIntervalMillis;
            if (num3 != null) {
                baseDownloadTask.setCallbackProgressMinInterval(num3.intValue());
            }
            Object obj = this.tag;
            if (obj != null) {
                baseDownloadTask.setTag(obj);
            }
            List<BaseDownloadTask.FinishListener> list = this.taskFinishListenerList;
            if (list != null) {
                for (BaseDownloadTask.FinishListener finishListener : list) {
                    baseDownloadTask.addFinishListener(finishListener);
                }
            }
            String str = this.directory;
            if (str != null) {
                baseDownloadTask.setPath(str, true);
            }
            Boolean bool3 = this.isWifiRequired;
            if (bool3 != null) {
                baseDownloadTask.setWifiRequired(bool3.booleanValue());
            }
            baseDownloadTask.asInQueueTask().enqueue();
        }
        FileDownloader.getImpl().start(this.target, this.isSerial);
    }

    public FileDownloadQueueSet setDirectory(String str) {
        this.directory = str;
        return this;
    }

    public FileDownloadQueueSet setAutoRetryTimes(int i) {
        this.autoRetryTimes = Integer.valueOf(i);
        return this;
    }

    public FileDownloadQueueSet setSyncCallback(boolean z) {
        this.syncCallback = Boolean.valueOf(z);
        return this;
    }

    public FileDownloadQueueSet setForceReDownload(boolean z) {
        this.isForceReDownload = Boolean.valueOf(z);
        return this;
    }

    public FileDownloadQueueSet setCallbackProgressTimes(int i) {
        this.callbackProgressTimes = Integer.valueOf(i);
        return this;
    }

    public FileDownloadQueueSet setCallbackProgressMinInterval(int i) {
        this.callbackProgressMinIntervalMillis = Integer.valueOf(i);
        return this;
    }

    public FileDownloadQueueSet ignoreEachTaskInternalProgress() {
        setCallbackProgressTimes(-1);
        return this;
    }

    public FileDownloadQueueSet disableCallbackProgressTimes() {
        return setCallbackProgressTimes(0);
    }

    public FileDownloadQueueSet setTag(Object obj) {
        this.tag = obj;
        return this;
    }

    public FileDownloadQueueSet addTaskFinishListener(BaseDownloadTask.FinishListener finishListener) {
        if (this.taskFinishListenerList == null) {
            this.taskFinishListenerList = new ArrayList();
        }
        this.taskFinishListenerList.add(finishListener);
        return this;
    }

    public FileDownloadQueueSet setWifiRequired(boolean z) {
        this.isWifiRequired = Boolean.valueOf(z);
        return this;
    }
}
