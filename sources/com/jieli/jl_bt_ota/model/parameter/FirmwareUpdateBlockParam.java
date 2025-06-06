package com.jieli.jl_bt_ota.model.parameter;

import com.jieli.jl_bt_ota.model.base.BaseParameter;
import com.jieli.jl_bt_ota.util.CHexConver;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
/* loaded from: classes2.dex */
public class FirmwareUpdateBlockParam extends BaseParameter {
    private int nextUpdateBlockLen;
    private int nextUpdateBlockOffsetAddr;

    public FirmwareUpdateBlockParam() {
    }

    public FirmwareUpdateBlockParam(int i, int i2) {
        setNextUpdateBlockOffsetAddr(i).setNextUpdateBlockLen(i2);
    }

    public int getNextUpdateBlockOffsetAddr() {
        return this.nextUpdateBlockOffsetAddr;
    }

    public FirmwareUpdateBlockParam setNextUpdateBlockOffsetAddr(int i) {
        this.nextUpdateBlockOffsetAddr = i;
        return this;
    }

    public int getNextUpdateBlockLen() {
        return this.nextUpdateBlockLen;
    }

    public FirmwareUpdateBlockParam setNextUpdateBlockLen(int i) {
        this.nextUpdateBlockLen = i;
        return this;
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter, com.jieli.jl_bt_ota.interfaces.command.IParamBase
    public byte[] getParamData() {
        ByteBuffer allocate = ByteBuffer.allocate(6);
        allocate.order(ByteOrder.BIG_ENDIAN);
        allocate.put(CHexConver.intToBigBytes(this.nextUpdateBlockOffsetAddr));
        allocate.put(CHexConver.int2byte2(this.nextUpdateBlockLen));
        return allocate.array();
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter
    public String toString() {
        return "FirmwareUpdateBlockParam{nextUpdateBlockOffsetAddr=" + this.nextUpdateBlockOffsetAddr + ", nextUpdateBlockLen=" + this.nextUpdateBlockLen + '}';
    }
}
