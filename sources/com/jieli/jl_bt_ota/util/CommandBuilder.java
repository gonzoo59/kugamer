package com.jieli.jl_bt_ota.util;

import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.command.CustomCmd;
import com.jieli.jl_bt_ota.model.command.DataCmd;
import com.jieli.jl_bt_ota.model.command.DataHasResponseCmd;
import com.jieli.jl_bt_ota.model.command.GetTargetInfoCmd;
import com.jieli.jl_bt_ota.model.parameter.CustomParam;
import com.jieli.jl_bt_ota.model.parameter.DataParam;
import com.jieli.jl_bt_ota.model.parameter.GetTargetInfoParam;
/* loaded from: classes2.dex */
public class CommandBuilder {
    public static DataCmd buildDataCmd(CommandBase commandBase, byte[] bArr) {
        if (commandBase == null || bArr == null) {
            return null;
        }
        DataParam dataParam = new DataParam(bArr);
        dataParam.setXmOpCode(commandBase.getId());
        return new DataCmd(dataParam);
    }

    public static DataHasResponseCmd buildDataCmdWithResponse(CommandBase commandBase, byte[] bArr) {
        if (commandBase == null || bArr == null || bArr.length <= 0) {
            return null;
        }
        DataParam dataParam = new DataParam(bArr);
        dataParam.setXmOpCode(commandBase.getId());
        return new DataHasResponseCmd(dataParam);
    }

    public static GetTargetInfoCmd buildGetTargetInfoCmdForAll() {
        return buildGetTargetInfoCmd(-1, (byte) 0);
    }

    public static GetTargetInfoCmd buildGetTargetInfoCmd(int i) {
        return buildGetTargetInfoCmd(i, (byte) 0);
    }

    public static GetTargetInfoCmd buildGetTargetInfoCmd(int i, byte b) {
        return new GetTargetInfoCmd(new GetTargetInfoParam(i).setPlatform(b));
    }

    public static CustomCmd buildCustomCmd(byte[] bArr) {
        return new CustomCmd(new CustomParam(bArr));
    }
}
