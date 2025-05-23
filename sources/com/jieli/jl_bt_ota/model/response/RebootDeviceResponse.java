package com.jieli.jl_bt_ota.model.response;

import com.jieli.jl_bt_ota.model.base.CommonResponse;
/* loaded from: classes2.dex */
public class RebootDeviceResponse extends CommonResponse {
    private int result;

    public RebootDeviceResponse(int i) {
        this.result = i;
    }

    public int getResult() {
        return this.result;
    }

    @Override // com.jieli.jl_bt_ota.model.base.CommonResponse, com.jieli.jl_bt_ota.model.base.BaseResponse
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RebootDeviceResponse{rawData size =");
        sb.append(getRawData() == null ? 0 : getRawData().length);
        sb.append("\nxmOpCode=");
        sb.append(getXmOpCode());
        sb.append("\nresult=");
        sb.append(this.result);
        sb.append('}');
        return sb.toString();
    }
}
