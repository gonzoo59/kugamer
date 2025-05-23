package com.jieli.jl_bt_ota.model.base;

import com.jieli.jl_bt_ota.model.base.BaseParameter;
/* loaded from: classes2.dex */
public class CommandWithParam<P extends BaseParameter> extends CommandBase {
    private P param;

    public CommandWithParam(int i, String str, P p) {
        super(i, str);
        setType(1);
        this.param = p;
    }

    public CommandWithParam setParam(P p) {
        this.param = p;
        return this;
    }

    public P getParam() {
        return this.param;
    }

    @Override // com.jieli.jl_bt_ota.model.base.CommandBase
    public String toString() {
        return "CommandWithParam{param=" + this.param + '}';
    }
}
