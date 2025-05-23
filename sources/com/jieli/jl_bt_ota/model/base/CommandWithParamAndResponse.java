package com.jieli.jl_bt_ota.model.base;

import com.jieli.jl_bt_ota.model.base.BaseParameter;
import com.jieli.jl_bt_ota.model.base.CommonResponse;
/* loaded from: classes2.dex */
public class CommandWithParamAndResponse<P extends BaseParameter, R extends CommonResponse> extends CommandWithResponse<R> {
    protected P param;
    protected R response;

    public CommandWithParamAndResponse(int i, String str, P p) {
        super(i, str);
        setType(2);
        this.param = p;
    }

    public void setParam(P p) {
        this.param = p;
    }

    public P getParam() {
        return this.param;
    }

    @Override // com.jieli.jl_bt_ota.model.base.CommandWithResponse
    public void setResponse(R r) {
        this.response = r;
    }

    @Override // com.jieli.jl_bt_ota.model.base.CommandWithResponse
    public R getResponse() {
        return this.response;
    }

    @Override // com.jieli.jl_bt_ota.model.base.CommandWithResponse, com.jieli.jl_bt_ota.model.base.CommandBase
    public String toString() {
        return "CommandWithParamAndResponse{opCode : " + getId() + "sn : " + getOpCodeSn() + ", param=" + this.param + ", response=" + this.response + "}\n" + super.toString();
    }
}
