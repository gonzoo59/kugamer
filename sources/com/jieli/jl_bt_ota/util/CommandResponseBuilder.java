package com.jieli.jl_bt_ota.util;

import com.jieli.jl_bt_ota.model.base.BaseParameter;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.base.CommandWithParamAndResponse;
import com.jieli.jl_bt_ota.model.command.FirmwareUpdateBlockCmd;
import com.jieli.jl_bt_ota.model.parameter.FirmwareUpdateBlockResponseParam;
/* loaded from: classes2.dex */
public class CommandResponseBuilder {
    private static String TAG = "CommandResponseBuilder";

    public static CommandBase buildFirmwareUpdateBlockCmdResponse(FirmwareUpdateBlockCmd firmwareUpdateBlockCmd, FirmwareUpdateBlockResponseParam firmwareUpdateBlockResponseParam) {
        return buildCommandResponse(firmwareUpdateBlockCmd, firmwareUpdateBlockResponseParam);
    }

    public static CommandBase buildCommandResponse(CommandBase commandBase, BaseParameter baseParameter) {
        if (commandBase != null) {
            if (commandBase.getType() == 2) {
                ((CommandWithParamAndResponse) commandBase).setParam(baseParameter);
                return commandBase;
            }
            return commandBase;
        }
        return null;
    }
}
