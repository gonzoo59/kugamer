package com.jieli.jl_bt_ota.model.parameter;

import com.jieli.jl_bt_ota.model.base.BaseParameter;
/* loaded from: classes2.dex */
public class NotifyCommunicationWayParam extends BaseParameter {
    private int way;

    public NotifyCommunicationWayParam(int i) {
        this.way = i;
    }

    public int getWay() {
        return this.way;
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter, com.jieli.jl_bt_ota.interfaces.command.IParamBase
    public byte[] getParamData() {
        return new byte[]{(byte) this.way};
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter
    public String toString() {
        return "NotifyCommunicationWayParam{way=" + this.way + '}';
    }
}
