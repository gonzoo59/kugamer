package com.liulishuo.filedownloader;

import com.liulishuo.filedownloader.event.DownloadEventPoolImpl;
/* loaded from: classes2.dex */
public class FileDownloadEventPool extends DownloadEventPoolImpl {

    /* loaded from: classes2.dex */
    private static class HolderClass {
        private static final FileDownloadEventPool INSTANCE = new FileDownloadEventPool();

        private HolderClass() {
        }
    }

    private FileDownloadEventPool() {
    }

    public static FileDownloadEventPool getImpl() {
        return HolderClass.INSTANCE;
    }
}
