package com.jieli.jl_bt_ota.model;

import com.jieli.jl_bt_ota.util.CHexConver;
import java.io.Serializable;
/* loaded from: classes2.dex */
public class BleScanMessage implements Serializable {
    private boolean isEnableConnect = true;
    private byte[] rawData;
    private int rssi;

    public byte[] getRawData() {
        return this.rawData;
    }

    public BleScanMessage setRawData(byte[] bArr) {
        this.rawData = bArr;
        return this;
    }

    public int getRssi() {
        return this.rssi;
    }

    public BleScanMessage setRssi(int i) {
        this.rssi = i;
        return this;
    }

    public boolean isEnableConnect() {
        return this.isEnableConnect;
    }

    public BleScanMessage setEnableConnect(boolean z) {
        this.isEnableConnect = z;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BleScanMessage{rawData=");
        byte[] bArr = this.rawData;
        sb.append(bArr == null ? "null" : CHexConver.byte2HexStr(bArr, bArr.length));
        sb.append(", rssi=");
        sb.append(this.rssi);
        sb.append(", isEnableConnect=");
        sb.append(this.isEnableConnect);
        sb.append('}');
        return sb.toString();
    }
}
