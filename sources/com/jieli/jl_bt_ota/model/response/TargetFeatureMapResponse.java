package com.jieli.jl_bt_ota.model.response;

import com.jieli.jl_bt_ota.model.base.CommonResponse;
/* loaded from: classes2.dex */
public class TargetFeatureMapResponse extends CommonResponse {
    private int mask;

    public TargetFeatureMapResponse() {
    }

    public TargetFeatureMapResponse(int i) {
        setMask(i);
    }

    public void setMask(int i) {
        this.mask = i;
    }

    public int getMask() {
        return this.mask;
    }

    @Override // com.jieli.jl_bt_ota.model.base.CommonResponse, com.jieli.jl_bt_ota.model.base.BaseResponse
    public String toString() {
        return "GetTargetFeatureMapResponse{mask=" + this.mask + '}';
    }
}
