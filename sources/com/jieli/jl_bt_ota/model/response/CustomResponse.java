package com.jieli.jl_bt_ota.model.response;

import com.jieli.jl_bt_ota.model.base.CommonResponse;
import com.jieli.jl_bt_ota.util.CHexConver;
/* loaded from: classes2.dex */
public class CustomResponse extends CommonResponse {
    private byte[] data;

    public CustomResponse(byte[] bArr) {
        this.data = bArr;
    }

    public CustomResponse() {
    }

    public void setData(byte[] bArr) {
        this.data = bArr;
    }

    public byte[] getData() {
        return this.data;
    }

    @Override // com.jieli.jl_bt_ota.model.base.CommonResponse, com.jieli.jl_bt_ota.model.base.BaseResponse
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CustomParam{data=");
        byte[] bArr = this.data;
        sb.append(CHexConver.byte2HexStr(bArr, bArr == null ? 0 : bArr.length));
        sb.append('}');
        return sb.toString();
    }
}
