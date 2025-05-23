package com.liulishuo.filedownloader;
/* loaded from: classes2.dex */
public interface IDownloadSpeed {

    /* loaded from: classes2.dex */
    public interface Lookup {
        int getSpeed();

        void setMinIntervalUpdateSpeed(int i);
    }

    /* loaded from: classes2.dex */
    public interface Monitor {
        void end(long j);

        void reset();

        void start(long j);

        void update(long j);
    }
}
