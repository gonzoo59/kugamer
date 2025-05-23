package com.jieli.jl_bt_ota.model.base;

import com.jieli.jl_bt_ota.model.base.CommonResponse;
/* loaded from: classes2.dex */
public class CommandWithResponse<R extends CommonResponse> extends CommandBase {
    private R response;

    public CommandWithResponse(int i, String str) {
        super(i, str);
        setType(3);
    }

    public void setResponse(R r) {
        this.response = r;
    }

    public R getResponse() {
        return this.response;
    }

    @Override // com.jieli.jl_bt_ota.model.base.CommandBase
    public String toString() {
        return "CommandWithResponse{response=" + this.response + "}\n" + super.toString();
    }
}
