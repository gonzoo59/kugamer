package com.liulishuo.filedownloader.message;

import android.os.Parcel;
import com.liulishuo.filedownloader.message.MessageSnapshot;
/* loaded from: classes2.dex */
public abstract class LargeMessageSnapshot extends MessageSnapshot {
    LargeMessageSnapshot(int i) {
        super(i);
        this.isLargeFile = true;
    }

    LargeMessageSnapshot(Parcel parcel) {
        super(parcel);
    }

    @Override // com.liulishuo.filedownloader.message.MessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
    public int getSmallSofarBytes() {
        if (getLargeSofarBytes() > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) getLargeSofarBytes();
    }

    @Override // com.liulishuo.filedownloader.message.MessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
    public int getSmallTotalBytes() {
        if (getLargeTotalBytes() > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) getLargeTotalBytes();
    }

    /* loaded from: classes2.dex */
    public static class PendingMessageSnapshot extends LargeMessageSnapshot {
        private final long sofarBytes;
        private final long totalBytes;

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
        public byte getStatus() {
            return (byte) 1;
        }

        PendingMessageSnapshot(PendingMessageSnapshot pendingMessageSnapshot) {
            this(pendingMessageSnapshot.getId(), pendingMessageSnapshot.getLargeSofarBytes(), pendingMessageSnapshot.getLargeTotalBytes());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public PendingMessageSnapshot(int i, long j, long j2) {
            super(i);
            this.sofarBytes = j;
            this.totalBytes = j2;
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
        public long getLargeSofarBytes() {
            return this.sofarBytes;
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
        public long getLargeTotalBytes() {
            return this.totalBytes;
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeLong(this.sofarBytes);
            parcel.writeLong(this.totalBytes);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public PendingMessageSnapshot(Parcel parcel) {
            super(parcel);
            this.sofarBytes = parcel.readLong();
            this.totalBytes = parcel.readLong();
        }
    }

    /* loaded from: classes2.dex */
    public static class ConnectedMessageSnapshot extends LargeMessageSnapshot {
        private final String etag;
        private final String fileName;
        private final boolean resuming;
        private final long totalBytes;

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
        public byte getStatus() {
            return (byte) 2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public ConnectedMessageSnapshot(int i, boolean z, long j, String str, String str2) {
            super(i);
            this.resuming = z;
            this.totalBytes = j;
            this.etag = str;
            this.fileName = str2;
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeByte(this.resuming ? (byte) 1 : (byte) 0);
            parcel.writeLong(this.totalBytes);
            parcel.writeString(this.etag);
            parcel.writeString(this.fileName);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public ConnectedMessageSnapshot(Parcel parcel) {
            super(parcel);
            this.resuming = parcel.readByte() != 0;
            this.totalBytes = parcel.readLong();
            this.etag = parcel.readString();
            this.fileName = parcel.readString();
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
        public String getFileName() {
            return this.fileName;
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
        public boolean isResuming() {
            return this.resuming;
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
        public long getLargeTotalBytes() {
            return this.totalBytes;
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
        public String getEtag() {
            return this.etag;
        }
    }

    /* loaded from: classes2.dex */
    public static class ProgressMessageSnapshot extends LargeMessageSnapshot {
        private final long sofarBytes;

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
        public byte getStatus() {
            return (byte) 3;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public ProgressMessageSnapshot(int i, long j) {
            super(i);
            this.sofarBytes = j;
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
        public long getLargeSofarBytes() {
            return this.sofarBytes;
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeLong(this.sofarBytes);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public ProgressMessageSnapshot(Parcel parcel) {
            super(parcel);
            this.sofarBytes = parcel.readLong();
        }
    }

    /* loaded from: classes2.dex */
    public static class CompletedFlowDirectlySnapshot extends CompletedSnapshot implements IFlowDirectly {
        /* JADX INFO: Access modifiers changed from: package-private */
        public CompletedFlowDirectlySnapshot(int i, boolean z, long j) {
            super(i, z, j);
        }

        CompletedFlowDirectlySnapshot(Parcel parcel) {
            super(parcel);
        }
    }

    /* loaded from: classes2.dex */
    public static class CompletedSnapshot extends LargeMessageSnapshot {
        private final boolean reusedDownloadedFile;
        private final long totalBytes;

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
        public byte getStatus() {
            return (byte) -3;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public CompletedSnapshot(int i, boolean z, long j) {
            super(i);
            this.reusedDownloadedFile = z;
            this.totalBytes = j;
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeByte(this.reusedDownloadedFile ? (byte) 1 : (byte) 0);
            parcel.writeLong(this.totalBytes);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public CompletedSnapshot(Parcel parcel) {
            super(parcel);
            this.reusedDownloadedFile = parcel.readByte() != 0;
            this.totalBytes = parcel.readLong();
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
        public long getLargeTotalBytes() {
            return this.totalBytes;
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
        public boolean isReusedDownloadedFile() {
            return this.reusedDownloadedFile;
        }
    }

    /* loaded from: classes2.dex */
    public static class ErrorMessageSnapshot extends LargeMessageSnapshot {
        private final long sofarBytes;
        private final Throwable throwable;

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
        public byte getStatus() {
            return (byte) -1;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public ErrorMessageSnapshot(int i, long j, Throwable th) {
            super(i);
            this.sofarBytes = j;
            this.throwable = th;
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
        public long getLargeSofarBytes() {
            return this.sofarBytes;
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
        public Throwable getThrowable() {
            return this.throwable;
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeLong(this.sofarBytes);
            parcel.writeSerializable(this.throwable);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public ErrorMessageSnapshot(Parcel parcel) {
            super(parcel);
            this.sofarBytes = parcel.readLong();
            this.throwable = (Throwable) parcel.readSerializable();
        }
    }

    /* loaded from: classes2.dex */
    public static class RetryMessageSnapshot extends ErrorMessageSnapshot {
        private final int retryingTimes;

        @Override // com.liulishuo.filedownloader.message.LargeMessageSnapshot.ErrorMessageSnapshot, com.liulishuo.filedownloader.message.MessageSnapshot, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // com.liulishuo.filedownloader.message.LargeMessageSnapshot.ErrorMessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
        public byte getStatus() {
            return (byte) 5;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public RetryMessageSnapshot(int i, long j, Throwable th, int i2) {
            super(i, j, th);
            this.retryingTimes = i2;
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
        public int getRetryingTimes() {
            return this.retryingTimes;
        }

        @Override // com.liulishuo.filedownloader.message.LargeMessageSnapshot.ErrorMessageSnapshot, com.liulishuo.filedownloader.message.MessageSnapshot, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.retryingTimes);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public RetryMessageSnapshot(Parcel parcel) {
            super(parcel);
            this.retryingTimes = parcel.readInt();
        }
    }

    /* loaded from: classes2.dex */
    public static class WarnFlowDirectlySnapshot extends WarnMessageSnapshot implements IFlowDirectly {
        /* JADX INFO: Access modifiers changed from: package-private */
        public WarnFlowDirectlySnapshot(int i, long j, long j2) {
            super(i, j, j2);
        }

        WarnFlowDirectlySnapshot(Parcel parcel) {
            super(parcel);
        }
    }

    /* loaded from: classes2.dex */
    public static class WarnMessageSnapshot extends PendingMessageSnapshot implements MessageSnapshot.IWarnMessageSnapshot {
        @Override // com.liulishuo.filedownloader.message.LargeMessageSnapshot.PendingMessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
        public byte getStatus() {
            return (byte) -4;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public WarnMessageSnapshot(int i, long j, long j2) {
            super(i, j, j2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public WarnMessageSnapshot(Parcel parcel) {
            super(parcel);
        }

        @Override // com.liulishuo.filedownloader.message.MessageSnapshot.IWarnMessageSnapshot
        public MessageSnapshot turnToPending() {
            return new PendingMessageSnapshot(this);
        }
    }

    /* loaded from: classes2.dex */
    public static class PausedSnapshot extends PendingMessageSnapshot {
        @Override // com.liulishuo.filedownloader.message.LargeMessageSnapshot.PendingMessageSnapshot, com.liulishuo.filedownloader.message.IMessageSnapshot
        public byte getStatus() {
            return (byte) -2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public PausedSnapshot(int i, long j, long j2) {
            super(i, j, j2);
        }
    }
}
