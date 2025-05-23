package com.liulishuo.filedownloader.message;

import android.os.Parcel;
import android.os.Parcelable;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
/* loaded from: classes2.dex */
public abstract class MessageSnapshot implements IMessageSnapshot, Parcelable {
    public static final Parcelable.Creator<MessageSnapshot> CREATOR = new Parcelable.Creator<MessageSnapshot>() { // from class: com.liulishuo.filedownloader.message.MessageSnapshot.1
        /* JADX WARN: Can't rename method to resolve collision */
        /* JADX WARN: Removed duplicated region for block: B:46:0x0094  */
        /* JADX WARN: Removed duplicated region for block: B:48:0x0097  */
        @Override // android.os.Parcelable.Creator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public com.liulishuo.filedownloader.message.MessageSnapshot createFromParcel(android.os.Parcel r5) {
            /*
                r4 = this;
                byte r0 = r5.readByte()
                r1 = 1
                if (r0 != r1) goto L9
                r0 = 1
                goto La
            L9:
                r0 = 0
            La:
                byte r2 = r5.readByte()
                r3 = -4
                if (r2 == r3) goto L84
                r3 = -3
                if (r2 == r3) goto L76
                r3 = -1
                if (r2 == r3) goto L68
                if (r2 == r1) goto L5a
                r1 = 2
                if (r2 == r1) goto L4c
                r1 = 3
                if (r2 == r1) goto L3e
                r1 = 5
                if (r2 == r1) goto L30
                r1 = 6
                if (r2 == r1) goto L28
                r5 = 0
                goto L92
            L28:
                com.liulishuo.filedownloader.message.MessageSnapshot$StartedMessageSnapshot r1 = new com.liulishuo.filedownloader.message.MessageSnapshot$StartedMessageSnapshot
                r1.<init>(r5)
            L2d:
                r5 = r1
                goto L92
            L30:
                if (r0 == 0) goto L38
                com.liulishuo.filedownloader.message.LargeMessageSnapshot$RetryMessageSnapshot r1 = new com.liulishuo.filedownloader.message.LargeMessageSnapshot$RetryMessageSnapshot
                r1.<init>(r5)
                goto L2d
            L38:
                com.liulishuo.filedownloader.message.SmallMessageSnapshot$RetryMessageSnapshot r1 = new com.liulishuo.filedownloader.message.SmallMessageSnapshot$RetryMessageSnapshot
                r1.<init>(r5)
                goto L2d
            L3e:
                if (r0 == 0) goto L46
                com.liulishuo.filedownloader.message.LargeMessageSnapshot$ProgressMessageSnapshot r1 = new com.liulishuo.filedownloader.message.LargeMessageSnapshot$ProgressMessageSnapshot
                r1.<init>(r5)
                goto L2d
            L46:
                com.liulishuo.filedownloader.message.SmallMessageSnapshot$ProgressMessageSnapshot r1 = new com.liulishuo.filedownloader.message.SmallMessageSnapshot$ProgressMessageSnapshot
                r1.<init>(r5)
                goto L2d
            L4c:
                if (r0 == 0) goto L54
                com.liulishuo.filedownloader.message.LargeMessageSnapshot$ConnectedMessageSnapshot r1 = new com.liulishuo.filedownloader.message.LargeMessageSnapshot$ConnectedMessageSnapshot
                r1.<init>(r5)
                goto L2d
            L54:
                com.liulishuo.filedownloader.message.SmallMessageSnapshot$ConnectedMessageSnapshot r1 = new com.liulishuo.filedownloader.message.SmallMessageSnapshot$ConnectedMessageSnapshot
                r1.<init>(r5)
                goto L2d
            L5a:
                if (r0 == 0) goto L62
                com.liulishuo.filedownloader.message.LargeMessageSnapshot$PendingMessageSnapshot r1 = new com.liulishuo.filedownloader.message.LargeMessageSnapshot$PendingMessageSnapshot
                r1.<init>(r5)
                goto L2d
            L62:
                com.liulishuo.filedownloader.message.SmallMessageSnapshot$PendingMessageSnapshot r1 = new com.liulishuo.filedownloader.message.SmallMessageSnapshot$PendingMessageSnapshot
                r1.<init>(r5)
                goto L2d
            L68:
                if (r0 == 0) goto L70
                com.liulishuo.filedownloader.message.LargeMessageSnapshot$ErrorMessageSnapshot r1 = new com.liulishuo.filedownloader.message.LargeMessageSnapshot$ErrorMessageSnapshot
                r1.<init>(r5)
                goto L2d
            L70:
                com.liulishuo.filedownloader.message.SmallMessageSnapshot$ErrorMessageSnapshot r1 = new com.liulishuo.filedownloader.message.SmallMessageSnapshot$ErrorMessageSnapshot
                r1.<init>(r5)
                goto L2d
            L76:
                if (r0 == 0) goto L7e
                com.liulishuo.filedownloader.message.LargeMessageSnapshot$CompletedSnapshot r1 = new com.liulishuo.filedownloader.message.LargeMessageSnapshot$CompletedSnapshot
                r1.<init>(r5)
                goto L2d
            L7e:
                com.liulishuo.filedownloader.message.SmallMessageSnapshot$CompletedSnapshot r1 = new com.liulishuo.filedownloader.message.SmallMessageSnapshot$CompletedSnapshot
                r1.<init>(r5)
                goto L2d
            L84:
                if (r0 == 0) goto L8c
                com.liulishuo.filedownloader.message.LargeMessageSnapshot$WarnMessageSnapshot r1 = new com.liulishuo.filedownloader.message.LargeMessageSnapshot$WarnMessageSnapshot
                r1.<init>(r5)
                goto L2d
            L8c:
                com.liulishuo.filedownloader.message.SmallMessageSnapshot$WarnMessageSnapshot r1 = new com.liulishuo.filedownloader.message.SmallMessageSnapshot$WarnMessageSnapshot
                r1.<init>(r5)
                goto L2d
            L92:
                if (r5 == 0) goto L97
                r5.isLargeFile = r0
                return r5
            L97:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "Can't restore the snapshot because unknown status: "
                r0.append(r1)
                r0.append(r2)
                java.lang.String r0 = r0.toString()
                r5.<init>(r0)
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.liulishuo.filedownloader.message.MessageSnapshot.AnonymousClass1.createFromParcel(android.os.Parcel):com.liulishuo.filedownloader.message.MessageSnapshot");
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MessageSnapshot[] newArray(int i) {
            return new MessageSnapshot[i];
        }
    };
    private final int id;
    protected boolean isLargeFile;

    /* loaded from: classes2.dex */
    public interface IWarnMessageSnapshot {
        MessageSnapshot turnToPending();
    }

    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MessageSnapshot(int i) {
        this.id = i;
    }

    @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
    public int getId() {
        return this.id;
    }

    @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
    public Throwable getThrowable() {
        throw new NoFieldException("getThrowable", this);
    }

    @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
    public int getRetryingTimes() {
        throw new NoFieldException("getRetryingTimes", this);
    }

    @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
    public boolean isResuming() {
        throw new NoFieldException("isResuming", this);
    }

    @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
    public String getEtag() {
        throw new NoFieldException("getEtag", this);
    }

    @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
    public long getLargeSofarBytes() {
        throw new NoFieldException("getLargeSofarBytes", this);
    }

    @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
    public long getLargeTotalBytes() {
        throw new NoFieldException("getLargeTotalBytes", this);
    }

    @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
    public int getSmallSofarBytes() {
        throw new NoFieldException("getSmallSofarBytes", this);
    }

    @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
    public int getSmallTotalBytes() {
        throw new NoFieldException("getSmallTotalBytes", this);
    }

    @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
    public boolean isReusedDownloadedFile() {
        throw new NoFieldException("isReusedDownloadedFile", this);
    }

    @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
    public String getFileName() {
        throw new NoFieldException("getFileName", this);
    }

    @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
    public boolean isLargeFile() {
        return this.isLargeFile;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.isLargeFile ? (byte) 1 : (byte) 0);
        parcel.writeByte(getStatus());
        parcel.writeInt(this.id);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MessageSnapshot(Parcel parcel) {
        this.id = parcel.readInt();
    }

    /* loaded from: classes2.dex */
    public static class NoFieldException extends IllegalStateException {
        NoFieldException(String str, MessageSnapshot messageSnapshot) {
            super(FileDownloadUtils.formatString("There isn't a field for '%s' in this message %d %d %s", str, Integer.valueOf(messageSnapshot.getId()), Byte.valueOf(messageSnapshot.getStatus()), messageSnapshot.getClass().getName()));
        }
    }

    /* loaded from: classes2.dex */
    public static class StartedMessageSnapshot extends MessageSnapshot {
        @Override // com.liulishuo.filedownloader.message.IMessageSnapshot
        public byte getStatus() {
            return (byte) 6;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public StartedMessageSnapshot(int i) {
            super(i);
        }

        StartedMessageSnapshot(Parcel parcel) {
            super(parcel);
        }
    }
}
