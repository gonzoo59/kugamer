package com.liulishuo.filedownloader.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
/* loaded from: classes2.dex */
public class FileDownloadTaskAtom implements Parcelable {
    public static final Parcelable.Creator<FileDownloadTaskAtom> CREATOR = new Parcelable.Creator<FileDownloadTaskAtom>() { // from class: com.liulishuo.filedownloader.model.FileDownloadTaskAtom.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FileDownloadTaskAtom createFromParcel(Parcel parcel) {
            return new FileDownloadTaskAtom(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FileDownloadTaskAtom[] newArray(int i) {
            return new FileDownloadTaskAtom[i];
        }
    };
    private int id;
    private String path;
    private long totalBytes;
    private String url;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FileDownloadTaskAtom(String str, String str2, long j) {
        setUrl(str);
        setPath(str2);
        setTotalBytes(j);
    }

    public int getId() {
        int i = this.id;
        if (i != 0) {
            return i;
        }
        int generateId = FileDownloadUtils.generateId(getUrl(), getPath());
        this.id = generateId;
        return generateId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public long getTotalBytes() {
        return this.totalBytes;
    }

    public void setTotalBytes(long j) {
        this.totalBytes = j;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.url);
        parcel.writeString(this.path);
        parcel.writeLong(this.totalBytes);
    }

    protected FileDownloadTaskAtom(Parcel parcel) {
        this.url = parcel.readString();
        this.path = parcel.readString();
        this.totalBytes = parcel.readLong();
    }
}
