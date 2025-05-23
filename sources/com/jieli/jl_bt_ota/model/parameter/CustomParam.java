package com.jieli.jl_bt_ota.model.parameter;

import com.jieli.jl_bt_ota.model.base.BaseParameter;
import com.jieli.jl_bt_ota.util.CHexConver;
/* loaded from: classes2.dex */
public class CustomParam extends BaseParameter {
    private byte[] data;

    public CustomParam(byte[] bArr) {
        this.data = bArr;
    }

    public CustomParam() {
    }

    public void setData(byte[] bArr) {
        if (bArr == null) {
            bArr = new byte[0];
        }
        this.data = bArr;
    }

    public byte[] getData() {
        return this.data;
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter, com.jieli.jl_bt_ota.interfaces.command.IParamBase
    public byte[] getParamData() {
        byte[] bArr = this.data;
        return bArr == null ? new byte[0] : bArr;
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CustomParam{data=");
        byte[] bArr = this.data;
        sb.append(bArr == null ? "null" : CHexConver.byte2HexStr(bArr, bArr.length));
        sb.append('}');
        return sb.toString();
    }
}
