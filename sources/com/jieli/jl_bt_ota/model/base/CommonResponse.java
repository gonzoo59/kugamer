package com.jieli.jl_bt_ota.model.base;

import java.util.Arrays;
/* loaded from: classes2.dex */
public class CommonResponse extends BaseResponse {
    private int xmOpCode = -1;

    public int getXmOpCode() {
        return this.xmOpCode;
    }

    public void setXmOpCode(int i) {
        this.xmOpCode = i;
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseResponse
    public String toString() {
        return "CommonResponse{rawData=" + Arrays.toString(getRawData()) + "xmOpCode=" + this.xmOpCode + '}';
    }
}
