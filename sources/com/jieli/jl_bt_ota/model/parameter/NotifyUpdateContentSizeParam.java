package com.jieli.jl_bt_ota.model.parameter;

import com.jieli.jl_bt_ota.model.base.BaseParameter;
import com.jieli.jl_bt_ota.util.CHexConver;
/* loaded from: classes2.dex */
public class NotifyUpdateContentSizeParam extends BaseParameter {
    private int contentSize;
    private int currentProgress = 0;

    public NotifyUpdateContentSizeParam(int i) {
        this.contentSize = i;
    }

    public int getContentSize() {
        return this.contentSize;
    }

    public NotifyUpdateContentSizeParam setContentSize(int i) {
        this.contentSize = i;
        return this;
    }

    public int getCurrentProgress() {
        return this.currentProgress;
    }

    public NotifyUpdateContentSizeParam setCurrentProgress(int i) {
        this.currentProgress = i;
        return this;
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter, com.jieli.jl_bt_ota.interfaces.command.IParamBase
    public byte[] getParamData() {
        if (this.currentProgress <= 0) {
            return CHexConver.intToBigBytes(this.contentSize);
        }
        byte[] bArr = new byte[8];
        byte[] intToBigBytes = CHexConver.intToBigBytes(this.contentSize);
        byte[] intToBigBytes2 = CHexConver.intToBigBytes(this.currentProgress);
        System.arraycopy(intToBigBytes, 0, bArr, 0, intToBigBytes.length);
        System.arraycopy(intToBigBytes2, 0, bArr, intToBigBytes.length, intToBigBytes2.length);
        return bArr;
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter
    public String toString() {
        return "NotifyUpdateContentSizeParam{contentSize=" + this.contentSize + ", currentProgress=" + this.currentProgress + "} " + super.toString();
    }
}
