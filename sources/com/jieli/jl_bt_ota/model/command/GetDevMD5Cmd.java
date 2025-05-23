package com.jieli.jl_bt_ota.model.command;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.model.base.CommandWithResponse;
import com.jieli.jl_bt_ota.model.response.GetDevMD5Response;
/* loaded from: classes2.dex */
public class GetDevMD5Cmd extends CommandWithResponse<GetDevMD5Response> {
    public GetDevMD5Cmd() {
        super(Command.CMD_GET_DEV_MD5, "GetDevMD5Cmd");
    }
}
