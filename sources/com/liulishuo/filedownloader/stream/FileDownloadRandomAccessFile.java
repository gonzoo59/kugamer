package com.liulishuo.filedownloader.stream;

import com.liulishuo.filedownloader.util.FileDownloadHelper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
/* loaded from: classes2.dex */
public class FileDownloadRandomAccessFile implements FileDownloadOutputStream {
    private final FileDescriptor fd;
    private final BufferedOutputStream out;
    private final RandomAccessFile randomAccess;

    FileDownloadRandomAccessFile(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        this.randomAccess = randomAccessFile;
        this.fd = randomAccessFile.getFD();
        this.out = new BufferedOutputStream(new FileOutputStream(randomAccessFile.getFD()));
    }

    @Override // com.liulishuo.filedownloader.stream.FileDownloadOutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
    }

    @Override // com.liulishuo.filedownloader.stream.FileDownloadOutputStream
    public void flushAndSync() throws IOException {
        this.out.flush();
        this.fd.sync();
    }

    @Override // com.liulishuo.filedownloader.stream.FileDownloadOutputStream
    public void close() throws IOException {
        this.out.close();
        this.randomAccess.close();
    }

    @Override // com.liulishuo.filedownloader.stream.FileDownloadOutputStream
    public void seek(long j) throws IOException {
        this.randomAccess.seek(j);
    }

    @Override // com.liulishuo.filedownloader.stream.FileDownloadOutputStream
    public void setLength(long j) throws IOException {
        this.randomAccess.setLength(j);
    }

    /* loaded from: classes2.dex */
    public static class Creator implements FileDownloadHelper.OutputStreamCreator {
        @Override // com.liulishuo.filedownloader.util.FileDownloadHelper.OutputStreamCreator
        public boolean supportSeek() {
            return true;
        }

        @Override // com.liulishuo.filedownloader.util.FileDownloadHelper.OutputStreamCreator
        public FileDownloadOutputStream create(File file) throws IOException {
            return new FileDownloadRandomAccessFile(file);
        }
    }
}
