package com.baidu.kwgames;

import java.lang.reflect.Array;
/* loaded from: classes.dex */
public class RingArray {
    public static final String TAG = "RingArr";
    private final int BUFFER_SIZE = 5120;
    private int dwBufLen;
    private int dwRd;
    private int dwWr;
    private final byte[][] mBuffer;
    private final int[] m_arrDataLen;

    /* loaded from: classes.dex */
    public static class SRingArrayPair {
        public byte[] m_arrBuf;
        public int m_nBufLen;
    }

    public RingArray(int i) {
        Units.checkArgumentPositive(i, "A ThroneRingBuffer cannot have 0 capacity");
        this.mBuffer = (byte[][]) Array.newInstance(byte.class, i, 5120);
        this.m_arrDataLen = new int[i];
        this.dwRd = 0;
        this.dwWr = 0;
        this.dwBufLen = i;
    }

    public boolean isEmpty() {
        return this.dwRd == this.dwWr;
    }

    public void clear() {
        this.dwWr = 0;
        this.dwRd = 0;
    }

    public synchronized void write(byte[] bArr) {
        int i = this.dwWr;
        int i2 = 0;
        System.arraycopy(bArr, 0, this.mBuffer[i], 0, bArr.length);
        this.m_arrDataLen[i] = bArr.length;
        int i3 = i + 1;
        if (i3 < this.dwBufLen) {
            i2 = i3;
        }
        this.dwWr = i2;
    }

    public void read(SRingArrayPair sRingArrayPair) {
        int i = this.dwWr;
        int i2 = this.dwRd;
        if (i != i2) {
            sRingArrayPair.m_arrBuf = this.mBuffer[i2];
            sRingArrayPair.m_nBufLen = this.m_arrDataLen[this.dwRd];
            int i3 = this.dwRd + 1;
            this.dwRd = i3 < this.dwBufLen ? i3 : 0;
            return;
        }
        sRingArrayPair.m_nBufLen = 0;
    }
}
