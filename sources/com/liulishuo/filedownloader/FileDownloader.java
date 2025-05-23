package com.liulishuo.filedownloader;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.download.CustomComponentHolder;
import com.liulishuo.filedownloader.event.DownloadServiceConnectChangedEvent;
import com.liulishuo.filedownloader.model.FileDownloadTaskAtom;
import com.liulishuo.filedownloader.services.DownloadMgrInitialParams;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.File;
import java.util.List;
/* loaded from: classes2.dex */
public class FileDownloader {
    private ILostServiceConnectedHandler mLostConnectedHandler;
    private IQueuesHandler mQueuesHandler;
    private static final Object INIT_QUEUES_HANDLER_LOCK = new Object();
    private static final Object INIT_LOST_CONNECTED_HANDLER_LOCK = new Object();

    public static void setup(Context context) {
        FileDownloadHelper.holdContext(context.getApplicationContext());
    }

    public static DownloadMgrInitialParams.InitCustomMaker setupOnApplicationOnCreate(Application application) {
        FileDownloadHelper.holdContext(application.getApplicationContext());
        DownloadMgrInitialParams.InitCustomMaker initCustomMaker = new DownloadMgrInitialParams.InitCustomMaker();
        CustomComponentHolder.getImpl().setInitCustomMaker(initCustomMaker);
        return initCustomMaker;
    }

    public static void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("the provided context must not be null!");
        }
        setup(context);
    }

    public static void init(Context context, DownloadMgrInitialParams.InitCustomMaker initCustomMaker) {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(FileDownloader.class, "init Downloader with params: %s %s", context, initCustomMaker);
        }
        if (context == null) {
            throw new IllegalArgumentException("the provided context must not be null!");
        }
        FileDownloadHelper.holdContext(context.getApplicationContext());
        CustomComponentHolder.getImpl().setInitCustomMaker(initCustomMaker);
    }

    /* loaded from: classes2.dex */
    private static final class HolderClass {
        private static final FileDownloader INSTANCE = new FileDownloader();

        private HolderClass() {
        }
    }

    public static FileDownloader getImpl() {
        return HolderClass.INSTANCE;
    }

    public static void setGlobalPost2UIInterval(int i) {
        FileDownloadMessageStation.INTERVAL = i;
    }

    public static void setGlobalHandleSubPackageSize(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("sub package size must more than 0");
        }
        FileDownloadMessageStation.SUB_PACKAGE_SIZE = i;
    }

    public static void enableAvoidDropFrame() {
        setGlobalPost2UIInterval(10);
    }

    public static void disableAvoidDropFrame() {
        setGlobalPost2UIInterval(-1);
    }

    public static boolean isEnabledAvoidDropFrame() {
        return FileDownloadMessageStation.isIntervalValid();
    }

    public BaseDownloadTask create(String str) {
        return new DownloadTask(str);
    }

    public boolean start(FileDownloadListener fileDownloadListener, boolean z) {
        if (fileDownloadListener == null) {
            FileDownloadLog.w(this, "Tasks with the listener can't start, because the listener provided is null: [null, %B]", Boolean.valueOf(z));
            return false;
        } else if (z) {
            return getQueuesHandler().startQueueSerial(fileDownloadListener);
        } else {
            return getQueuesHandler().startQueueParallel(fileDownloadListener);
        }
    }

    public void pause(FileDownloadListener fileDownloadListener) {
        FileDownloadTaskLauncher.getImpl().expire(fileDownloadListener);
        for (BaseDownloadTask.IRunningTask iRunningTask : FileDownloadList.getImpl().copy(fileDownloadListener)) {
            iRunningTask.getOrigin().pause();
        }
    }

    public void pauseAll() {
        FileDownloadTaskLauncher.getImpl().expireAll();
        for (BaseDownloadTask.IRunningTask iRunningTask : FileDownloadList.getImpl().copy()) {
            iRunningTask.getOrigin().pause();
        }
        if (FileDownloadServiceProxy.getImpl().isConnected()) {
            FileDownloadServiceProxy.getImpl().pauseAllTasks();
        } else {
            PauseAllMarker.createMarker();
        }
    }

    public int pause(int i) {
        List<BaseDownloadTask.IRunningTask> downloadingList = FileDownloadList.getImpl().getDownloadingList(i);
        if (downloadingList == null || downloadingList.isEmpty()) {
            FileDownloadLog.w(this, "request pause but not exist %d", Integer.valueOf(i));
            return 0;
        }
        for (BaseDownloadTask.IRunningTask iRunningTask : downloadingList) {
            iRunningTask.getOrigin().pause();
        }
        return downloadingList.size();
    }

    public boolean clear(int i, String str) {
        pause(i);
        if (FileDownloadServiceProxy.getImpl().clearTaskData(i)) {
            File file = new File(FileDownloadUtils.getTempPath(str));
            if (file.exists()) {
                file.delete();
            }
            File file2 = new File(str);
            if (file2.exists()) {
                file2.delete();
                return true;
            }
            return true;
        }
        return false;
    }

    public void clearAllTaskData() {
        pauseAll();
        FileDownloadServiceProxy.getImpl().clearAllTaskData();
    }

    public long getSoFar(int i) {
        BaseDownloadTask.IRunningTask iRunningTask = FileDownloadList.getImpl().get(i);
        if (iRunningTask == null) {
            return FileDownloadServiceProxy.getImpl().getSofar(i);
        }
        return iRunningTask.getOrigin().getLargeFileSoFarBytes();
    }

    public long getTotal(int i) {
        BaseDownloadTask.IRunningTask iRunningTask = FileDownloadList.getImpl().get(i);
        if (iRunningTask == null) {
            return FileDownloadServiceProxy.getImpl().getTotal(i);
        }
        return iRunningTask.getOrigin().getLargeFileTotalBytes();
    }

    public byte getStatusIgnoreCompleted(int i) {
        return getStatus(i, (String) null);
    }

    public byte getStatus(String str, String str2) {
        return getStatus(FileDownloadUtils.generateId(str, str2), str2);
    }

    public byte getStatus(int i, String str) {
        byte status;
        BaseDownloadTask.IRunningTask iRunningTask = FileDownloadList.getImpl().get(i);
        if (iRunningTask == null) {
            status = FileDownloadServiceProxy.getImpl().getStatus(i);
        } else {
            status = iRunningTask.getOrigin().getStatus();
        }
        if (str != null && status == 0 && FileDownloadUtils.isFilenameConverted(FileDownloadHelper.getAppContext()) && new File(str).exists()) {
            return (byte) -3;
        }
        return status;
    }

    public int replaceListener(String str, FileDownloadListener fileDownloadListener) {
        return replaceListener(str, FileDownloadUtils.getDefaultSaveFilePath(str), fileDownloadListener);
    }

    public int replaceListener(String str, String str2, FileDownloadListener fileDownloadListener) {
        return replaceListener(FileDownloadUtils.generateId(str, str2), fileDownloadListener);
    }

    public int replaceListener(int i, FileDownloadListener fileDownloadListener) {
        BaseDownloadTask.IRunningTask iRunningTask = FileDownloadList.getImpl().get(i);
        if (iRunningTask == null) {
            return 0;
        }
        iRunningTask.getOrigin().setListener(fileDownloadListener);
        return iRunningTask.getOrigin().getId();
    }

    public void bindService() {
        if (isServiceConnected()) {
            return;
        }
        FileDownloadServiceProxy.getImpl().bindStartByContext(FileDownloadHelper.getAppContext());
    }

    public void bindService(Runnable runnable) {
        if (isServiceConnected()) {
            runnable.run();
        } else {
            FileDownloadServiceProxy.getImpl().bindStartByContext(FileDownloadHelper.getAppContext(), runnable);
        }
    }

    public void unBindService() {
        if (isServiceConnected()) {
            FileDownloadServiceProxy.getImpl().unbindByContext(FileDownloadHelper.getAppContext());
        }
    }

    public boolean unBindServiceIfIdle() {
        if (isServiceConnected() && FileDownloadList.getImpl().isEmpty() && FileDownloadServiceProxy.getImpl().isIdle()) {
            unBindService();
            return true;
        }
        return false;
    }

    public boolean isServiceConnected() {
        return FileDownloadServiceProxy.getImpl().isConnected();
    }

    public void addServiceConnectListener(FileDownloadConnectListener fileDownloadConnectListener) {
        FileDownloadEventPool.getImpl().addListener(DownloadServiceConnectChangedEvent.ID, fileDownloadConnectListener);
    }

    public void removeServiceConnectListener(FileDownloadConnectListener fileDownloadConnectListener) {
        FileDownloadEventPool.getImpl().removeListener(DownloadServiceConnectChangedEvent.ID, fileDownloadConnectListener);
    }

    public void startForeground(int i, Notification notification) {
        FileDownloadServiceProxy.getImpl().startForeground(i, notification);
    }

    public void stopForeground(boolean z) {
        FileDownloadServiceProxy.getImpl().stopForeground(z);
    }

    public boolean setTaskCompleted(String str, String str2, long j) {
        FileDownloadLog.w(this, "If you invoked this method, please remove it directly feel free, it doesn't need any longer", new Object[0]);
        return true;
    }

    public boolean setTaskCompleted(List<FileDownloadTaskAtom> list) {
        FileDownloadLog.w(this, "If you invoked this method, please remove it directly feel free, it doesn't need any longer", new Object[0]);
        return true;
    }

    public boolean setMaxNetworkThreadCount(int i) {
        if (!FileDownloadList.getImpl().isEmpty()) {
            FileDownloadLog.w(this, "Can't change the max network thread count, because there are actively executing tasks in FileDownloader, please try again after all actively executing tasks are completed or invoking FileDownloader#pauseAll directly.", new Object[0]);
            return false;
        }
        return FileDownloadServiceProxy.getImpl().setMaxNetworkThreadCount(i);
    }

    public FileDownloadLine insureServiceBind() {
        return new FileDownloadLine();
    }

    public FileDownloadLineAsync insureServiceBindAsync() {
        return new FileDownloadLineAsync();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public IQueuesHandler getQueuesHandler() {
        if (this.mQueuesHandler == null) {
            synchronized (INIT_QUEUES_HANDLER_LOCK) {
                if (this.mQueuesHandler == null) {
                    this.mQueuesHandler = new QueuesHandler();
                }
            }
        }
        return this.mQueuesHandler;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ILostServiceConnectedHandler getLostConnectedHandler() {
        if (this.mLostConnectedHandler == null) {
            synchronized (INIT_LOST_CONNECTED_HANDLER_LOCK) {
                if (this.mLostConnectedHandler == null) {
                    LostServiceConnectedHandler lostServiceConnectedHandler = new LostServiceConnectedHandler();
                    this.mLostConnectedHandler = lostServiceConnectedHandler;
                    addServiceConnectListener(lostServiceConnectedHandler);
                }
            }
        }
        return this.mLostConnectedHandler;
    }
}
