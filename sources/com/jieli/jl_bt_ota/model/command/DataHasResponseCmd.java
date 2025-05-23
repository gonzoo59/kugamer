package com.jieli.jl_bt_ota.model.command;

import com.jieli.jl_bt_ota.model.base.CommandWithParamAndResponse;
import com.jieli.jl_bt_ota.model.base.CommonResponse;
import com.jieli.jl_bt_ota.model.parameter.DataParam;
/* loaded from: classes2.dex */
public class DataHasResponseCmd extends CommandWithParamAndResponse<DataParam, CommonResponse> {
    public DataHasResponseCmd(DataParam dataParam) {
        super(1, "DataHasResponseCmd", dataParam);
    }
}
