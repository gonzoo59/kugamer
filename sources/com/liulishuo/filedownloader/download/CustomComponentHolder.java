package com.liulishuo.filedownloader.download;

import com.liulishuo.filedownloader.connection.FileDownloadConnection;
import com.liulishuo.filedownloader.database.FileDownloadDatabase;
import com.liulishuo.filedownloader.services.DownloadMgrInitialParams;
import com.liulishuo.filedownloader.services.ForegroundServiceConfig;
import com.liulishuo.filedownloader.stream.FileDownloadOutputStream;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import java.io.File;
import java.io.IOException;
/* loaded from: classes2.dex */
public class CustomComponentHolder {
    private FileDownloadHelper.ConnectionCountAdapter connectionCountAdapter;
    private FileDownloadHelper.ConnectionCreator connectionCreator;
    private FileDownloadDatabase database;
    private ForegroundServiceConfig foregroundServiceConfig;
    private FileDownloadHelper.IdGenerator idGenerator;
    private DownloadMgrInitialParams initialParams;
    private FileDownloadHelper.OutputStreamCreator outputStreamCreator;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class LazyLoader {
        private static final CustomComponentHolder INSTANCE = new CustomComponentHolder();

        private LazyLoader() {
        }
    }

    public static CustomComponentHolder getImpl() {
        return LazyLoader.INSTANCE;
    }

    public void setInitCustomMaker(DownloadMgrInitialParams.InitCustomMaker initCustomMaker) {
        synchronized (this) {
            this.initialParams = new DownloadMgrInitialParams(initCustomMaker);
            this.connectionCreator = null;
            this.outputStreamCreator = null;
            this.database = null;
            this.idGenerator = null;
        }
    }

    public FileDownloadConnection createConnection(String str) throws IOException {
        return getConnectionCreator().create(str);
    }

    public FileDownloadOutputStream createOutputStream(File file) throws IOException {
        return getOutputStreamCreator().create(file);
    }

    public FileDownloadHelper.IdGenerator getIdGeneratorInstance() {
        FileDownloadHelper.IdGenerator idGenerator = this.idGenerator;
        if (idGenerator != null) {
            return idGenerator;
        }
        synchronized (this) {
            if (this.idGenerator == null) {
                this.idGenerator = getDownloadMgrInitialParams().createIdGenerator();
            }
        }
        return this.idGenerator;
    }

    public FileDownloadDatabase getDatabaseInstance() {
        FileDownloadDatabase fileDownloadDatabase = this.database;
        if (fileDownloadDatabase != null) {
            return fileDownloadDatabase;
        }
        synchronized (this) {
            if (this.database == null) {
                FileDownloadDatabase createDatabase = getDownloadMgrInitialParams().createDatabase();
                this.database = createDatabase;
                maintainDatabase(createDatabase.maintainer());
            }
        }
        return this.database;
    }

    public ForegroundServiceConfig getForegroundConfigInstance() {
        ForegroundServiceConfig foregroundServiceConfig = this.foregroundServiceConfig;
        if (foregroundServiceConfig != null) {
            return foregroundServiceConfig;
        }
        synchronized (this) {
            if (this.foregroundServiceConfig == null) {
                this.foregroundServiceConfig = getDownloadMgrInitialParams().createForegroundServiceConfig();
            }
        }
        return this.foregroundServiceConfig;
    }

    public int getMaxNetworkThreadCount() {
        return getDownloadMgrInitialParams().getMaxNetworkThreadCount();
    }

    public boolean isSupportSeek() {
        return getOutputStreamCreator().supportSeek();
    }

    public int determineConnectionCount(int i, String str, String str2, long j) {
        return getConnectionCountAdapter().determineConnectionCount(i, str, str2, j);
    }

    private FileDownloadHelper.ConnectionCountAdapter getConnectionCountAdapter() {
        FileDownloadHelper.ConnectionCountAdapter connectionCountAdapter = this.connectionCountAdapter;
        if (connectionCountAdapter != null) {
            return connectionCountAdapter;
        }
        synchronized (this) {
            if (this.connectionCountAdapter == null) {
                this.connectionCountAdapter = getDownloadMgrInitialParams().createConnectionCountAdapter();
            }
        }
        return this.connectionCountAdapter;
    }

    private FileDownloadHelper.ConnectionCreator getConnectionCreator() {
        FileDownloadHelper.ConnectionCreator connectionCreator = this.connectionCreator;
        if (connectionCreator != null) {
            return connectionCreator;
        }
        synchronized (this) {
            if (this.connectionCreator == null) {
                this.connectionCreator = getDownloadMgrInitialParams().createConnectionCreator();
            }
        }
        return this.connectionCreator;
    }

    private FileDownloadHelper.OutputStreamCreator getOutputStreamCreator() {
        FileDownloadHelper.OutputStreamCreator outputStreamCreator = this.outputStreamCreator;
        if (outputStreamCreator != null) {
            return outputStreamCreator;
        }
        synchronized (this) {
            if (this.outputStreamCreator == null) {
                this.outputStreamCreator = getDownloadMgrInitialParams().createOutputStreamCreator();
            }
        }
        return this.outputStreamCreator;
    }

    private DownloadMgrInitialParams getDownloadMgrInitialParams() {
        DownloadMgrInitialParams downloadMgrInitialParams = this.initialParams;
        if (downloadMgrInitialParams != null) {
            return downloadMgrInitialParams;
        }
        synchronized (this) {
            if (this.initialParams == null) {
                this.initialParams = new DownloadMgrInitialParams();
            }
        }
        return this.initialParams;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:37:0x00bf
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:81)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:47)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
        */
    private static void maintainDatabase(com.liulishuo.filedownloader.database.FileDownloadDatabase.Maintainer r25) {
        /*
            Method dump skipped, instructions count: 457
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.liulishuo.filedownloader.download.CustomComponentHolder.maintainDatabase(com.liulishuo.filedownloader.database.FileDownloadDatabase$Maintainer):void");
    }
}
