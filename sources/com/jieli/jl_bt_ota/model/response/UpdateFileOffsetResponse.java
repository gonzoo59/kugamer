package com.jieli.jl_bt_ota.model.response;

import com.jieli.jl_bt_ota.model.base.CommonResponse;
/* loaded from: classes2.dex */
public class UpdateFileOffsetResponse extends CommonResponse {
    private int updateFileFlagLen;
    private int updateFileFlagOffset;

    public int getUpdateFileFlagOffset() {
        return this.updateFileFlagOffset;
    }

    public UpdateFileOffsetResponse setUpdateFileFlagOffset(int i) {
        this.updateFileFlagOffset = i;
        return this;
    }

    public int getUpdateFileFlagLen() {
        return this.updateFileFlagLen;
    }

    public UpdateFileOffsetResponse setUpdateFileFlagLen(int i) {
        this.updateFileFlagLen = i;
        return this;
    }

    @Override // com.jieli.jl_bt_ota.model.base.CommonResponse, com.jieli.jl_bt_ota.model.base.BaseResponse
    public String toString() {
        return "UpdateFileOffsetResponse{updateFileFlagOffset=" + this.updateFileFlagOffset + ", updateFileFlagLen=" + this.updateFileFlagLen + '}';
    }
}
