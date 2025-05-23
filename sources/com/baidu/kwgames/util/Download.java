package com.baidu.kwgames.util;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;
import java.io.File;
/* loaded from: classes.dex */
public class Download {
    public static BaseDownloadTask createTask(String str, String str2) {
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        return FileDownloader.getImpl().create(str).setCallbackProgressTimes(300).setMinIntervalUpdateSpeed(400).setPath(str2, true);
    }
}
