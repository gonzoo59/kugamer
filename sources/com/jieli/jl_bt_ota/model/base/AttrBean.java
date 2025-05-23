package com.jieli.jl_bt_ota.model.base;

import com.jieli.jl_bt_ota.util.CHexConver;
/* loaded from: classes2.dex */
public class AttrBean {
    private byte[] attrData;
    private byte type;

    public byte getType() {
        return this.type;
    }

    public AttrBean setType(byte b) {
        this.type = b;
        return this;
    }

    public AttrBean setAttrData(byte[] bArr) {
        if (bArr == null) {
            bArr = new byte[0];
        }
        this.attrData = bArr;
        return this;
    }

    public byte[] getAttrData() {
        return this.attrData;
    }

    public byte[] getData() {
        byte[] bArr = this.attrData;
        int length = bArr.length + 2;
        byte[] bArr2 = new byte[length];
        bArr2[0] = (byte) (length - 1);
        bArr2[1] = this.type;
        System.arraycopy(bArr, 0, bArr2, 2, bArr.length);
        return bArr2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AttrBean{type=");
        sb.append((int) this.type);
        sb.append(", attrData=");
        byte[] bArr = this.attrData;
        sb.append(bArr == null ? "null" : CHexConver.byte2HexStr(bArr, bArr.length));
        sb.append('}');
        return sb.toString();
    }
}
