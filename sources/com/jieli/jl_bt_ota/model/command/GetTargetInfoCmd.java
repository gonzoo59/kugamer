package com.jieli.jl_bt_ota.model.command;

import com.jieli.jl_bt_ota.model.base.CommandWithParamAndResponse;
import com.jieli.jl_bt_ota.model.parameter.GetTargetInfoParam;
import com.jieli.jl_bt_ota.model.response.TargetInfoResponse;
/* loaded from: classes2.dex */
public class GetTargetInfoCmd extends CommandWithParamAndResponse<GetTargetInfoParam, TargetInfoResponse> {
    public GetTargetInfoCmd(GetTargetInfoParam getTargetInfoParam) {
        super(3, "GetTargetInfoCmd", getTargetInfoParam);
    }
}
