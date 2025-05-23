package com.jieli.jl_bt_ota.model.base;

import com.jieli.jl_bt_ota.interfaces.command.IParamBase;
import java.util.Arrays;
/* loaded from: classes2.dex */
public class BaseParameter implements IParamBase {
    private byte[] paramData;
    private int xmOpCode;

    public BaseParameter setXmOpCode(int i) {
        this.xmOpCode = i;
        return this;
    }

    public int getXmOpCode() {
        return this.xmOpCode;
    }

    @Override // com.jieli.jl_bt_ota.interfaces.command.IParamBase
    public byte[] getParamData() {
        byte[] bArr = this.paramData;
        if (bArr != null) {
            int length = bArr.length;
            int i = this.xmOpCode;
            if (i > 0) {
                length++;
            }
            byte[] bArr2 = new byte[length];
            int i2 = 1;
            if (i > 0) {
                System.arraycopy(new byte[]{(byte) i}, 0, bArr2, 0, 1);
            } else {
                i2 = 0;
            }
            byte[] bArr3 = this.paramData;
            System.arraycopy(bArr3, 0, bArr2, i2, bArr3.length);
            return bArr2;
        }
        return null;
    }

    public BaseParameter setParamData(byte[] bArr) {
        this.paramData = bArr;
        return this;
    }

    public String toString() {
        return "BaseParameter{xmOpCode=" + this.xmOpCode + ", paramData=" + Arrays.toString(this.paramData) + '}';
    }
}
