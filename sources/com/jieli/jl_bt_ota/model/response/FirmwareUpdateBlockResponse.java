package com.jieli.jl_bt_ota.model.response;

import com.jieli.jl_bt_ota.model.base.CommonResponse;
import java.util.Arrays;
/* loaded from: classes2.dex */
public class FirmwareUpdateBlockResponse extends CommonResponse {
    private byte[] firmwareUpdateBlockData;

    public byte[] getFirmwareUpdateBlockData() {
        return this.firmwareUpdateBlockData;
    }

    public FirmwareUpdateBlockResponse setFirmwareUpdateBlockData(byte[] bArr) {
        this.firmwareUpdateBlockData = bArr;
        return this;
    }

    @Override // com.jieli.jl_bt_ota.model.base.CommonResponse, com.jieli.jl_bt_ota.model.base.BaseResponse
    public String toString() {
        return "FirmwareUpdateBlockResponse{firmwareUpdateBlockData=" + Arrays.toString(this.firmwareUpdateBlockData) + '}';
    }
}
