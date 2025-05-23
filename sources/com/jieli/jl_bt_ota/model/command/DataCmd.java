package com.jieli.jl_bt_ota.model.command;

import com.jieli.jl_bt_ota.model.base.CommandWithParam;
import com.jieli.jl_bt_ota.model.parameter.DataParam;
/* loaded from: classes2.dex */
public class DataCmd extends CommandWithParam<DataParam> {
    public DataCmd(DataParam dataParam) {
        super(1, "DataCmd", dataParam);
    }
}
