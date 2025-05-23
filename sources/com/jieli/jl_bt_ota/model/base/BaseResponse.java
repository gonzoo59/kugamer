package com.jieli.jl_bt_ota.model.base;

import java.util.Arrays;
/* loaded from: classes2.dex */
public class BaseResponse {
    private byte[] rawData;

    public void setRawData(byte[] bArr) {
        this.rawData = bArr;
    }

    public byte[] getRawData() {
        return this.rawData;
    }

    public String toString() {
        return "BaseResponse{rawData=" + Arrays.toString(this.rawData) + '}';
    }
}
