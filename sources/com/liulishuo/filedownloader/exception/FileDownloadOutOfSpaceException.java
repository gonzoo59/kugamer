package com.liulishuo.filedownloader.exception;

import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.IOException;
/* loaded from: classes2.dex */
public class FileDownloadOutOfSpaceException extends IOException {
    private long breakpointBytes;
    private long freeSpaceBytes;
    private long requiredSpaceBytes;

    public FileDownloadOutOfSpaceException(long j, long j2, long j3, Throwable th) {
        super(FileDownloadUtils.formatString("The file is too large to store, breakpoint in bytes:  %d, required space in bytes: %d, but free space in bytes: %d", Long.valueOf(j3), Long.valueOf(j2), Long.valueOf(j)), th);
        init(j, j2, j3);
    }

    public FileDownloadOutOfSpaceException(long j, long j2, long j3) {
        super(FileDownloadUtils.formatString("The file is too large to store, breakpoint in bytes:  %d, required space in bytes: %d, but free space in bytes: %d", Long.valueOf(j3), Long.valueOf(j2), Long.valueOf(j)));
        init(j, j2, j3);
    }

    private void init(long j, long j2, long j3) {
        this.freeSpaceBytes = j;
        this.requiredSpaceBytes = j2;
        this.breakpointBytes = j3;
    }

    public long getFreeSpaceBytes() {
        return this.freeSpaceBytes;
    }

    public long getRequiredSpaceBytes() {
        return this.requiredSpaceBytes;
    }

    public long getBreakpointBytes() {
        return this.breakpointBytes;
    }
}
