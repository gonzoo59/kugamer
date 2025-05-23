package com.liulishuo.filedownloader.model;

import com.liulishuo.filedownloader.BaseDownloadTask;
/* loaded from: classes2.dex */
public class FileDownloadStatus {
    public static final byte INVALID_STATUS = 0;
    public static final byte blockComplete = 4;
    public static final byte completed = -3;
    public static final byte connected = 2;
    public static final byte error = -1;
    public static final byte paused = -2;
    public static final byte pending = 1;
    public static final byte progress = 3;
    public static final byte retry = 5;
    public static final byte started = 6;
    public static final byte toFileDownloadService = 11;
    public static final byte toLaunchPool = 10;
    public static final byte warn = -4;

    public static boolean isIng(int i) {
        return i > 0;
    }

    public static boolean isOver(int i) {
        return i < 0;
    }

    public static boolean isKeepAhead(int i, int i2) {
        if ((i == 3 || i == 5 || i != i2) && !isOver(i)) {
            if (i < 1 || i > 6 || i2 < 10 || i2 > 11) {
                if (i == 1) {
                    return i2 != 0;
                } else if (i == 2) {
                    return (i2 == 0 || i2 == 1 || i2 == 6) ? false : true;
                } else if (i == 3) {
                    return (i2 == 0 || i2 == 1 || i2 == 2 || i2 == 6) ? false : true;
                } else if (i == 5) {
                    return (i2 == 1 || i2 == 6) ? false : true;
                } else if (i != 6) {
                    return true;
                } else {
                    return (i2 == 0 || i2 == 1) ? false : true;
                }
            }
            return false;
        }
        return false;
    }

    public static boolean isKeepFlow(int i, int i2) {
        if ((i == 3 || i == 5 || i != i2) && !isOver(i)) {
            if (i2 == -2 || i2 == -1) {
                return true;
            }
            if (i == 0) {
                return i2 == 10;
            } else if (i == 1) {
                return i2 == 6;
            } else if (i == 2 || i == 3) {
                return i2 == -3 || i2 == 3 || i2 == 5;
            } else if (i == 5 || i == 6) {
                return i2 == 2 || i2 == 5;
            } else if (i == 10) {
                return i2 == 11;
            } else if (i != 11) {
                return false;
            } else {
                return i2 == -4 || i2 == -3 || i2 == 1;
            }
        }
        return false;
    }

    public static boolean isMoreLikelyCompleted(BaseDownloadTask baseDownloadTask) {
        return baseDownloadTask.getStatus() == 0 || baseDownloadTask.getStatus() == 3;
    }
}
