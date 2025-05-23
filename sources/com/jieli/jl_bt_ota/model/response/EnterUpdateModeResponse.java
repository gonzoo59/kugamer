package com.jieli.jl_bt_ota.model.response;

import com.jieli.jl_bt_ota.model.base.CommonResponse;
/* loaded from: classes2.dex */
public class EnterUpdateModeResponse extends CommonResponse {
    private int canUpdateFlag;

    public EnterUpdateModeResponse(int i) {
        this.canUpdateFlag = i;
    }

    public int getCanUpdateFlag() {
        return this.canUpdateFlag;
    }

    @Override // com.jieli.jl_bt_ota.model.base.CommonResponse, com.jieli.jl_bt_ota.model.base.BaseResponse
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EnterUpdateModeResponse{rawData size =");
        sb.append(getRawData() == null ? 0 : getRawData().length);
        sb.append("\nxmOpCode=");
        sb.append(getXmOpCode());
        sb.append("\ncanUpdateFlag=");
        sb.append(this.canUpdateFlag);
        sb.append('}');
        return sb.toString();
    }
}
