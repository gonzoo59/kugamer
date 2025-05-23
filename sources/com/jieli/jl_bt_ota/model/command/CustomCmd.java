package com.jieli.jl_bt_ota.model.command;

import com.jieli.jl_bt_ota.model.base.CommandWithParamAndResponse;
import com.jieli.jl_bt_ota.model.parameter.CustomParam;
import com.jieli.jl_bt_ota.model.response.CustomResponse;
/* loaded from: classes2.dex */
public class CustomCmd extends CommandWithParamAndResponse<CustomParam, CustomResponse> {
    public CustomCmd(CustomParam customParam) {
        super(255, "CustomCmd", customParam);
    }
}
