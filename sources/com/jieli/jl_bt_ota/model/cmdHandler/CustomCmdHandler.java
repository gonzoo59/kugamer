package com.jieli.jl_bt_ota.model.cmdHandler;

import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.command.CustomCmd;
import com.jieli.jl_bt_ota.model.parameter.CustomParam;
import com.jieli.jl_bt_ota.model.response.CustomResponse;
import com.jieli.jl_bt_ota.tool.CommandHelper;
/* loaded from: classes2.dex */
public class CustomCmdHandler implements ICmdHandler {
    @Override // com.jieli.jl_bt_ota.interfaces.command.ICmdHandler
    public CommandBase parseDataToCmd(BasePacket basePacket) {
        if (basePacket != null && basePacket.getOpCode() == 255) {
            byte[] paramData = basePacket.getParamData();
            if (basePacket.getType() == 1) {
                byte[] bArr = new byte[0];
                if (paramData == null || paramData.length <= 0) {
                    paramData = bArr;
                }
                CustomCmd customCmd = new CustomCmd((CustomParam) new CustomParam(paramData).setXmOpCode(basePacket.getXmOpCode()));
                if (basePacket.getHasResponse() == 0) {
                    customCmd.setType(1);
                }
                customCmd.setOpCodeSn(basePacket.getOpCodeSn());
                return customCmd;
            }
            CommandBase command = CommandHelper.getInstance().getCommand(basePacket.getOpCode(), basePacket.getOpCodeSn());
            CustomResponse customResponse = new CustomResponse();
            customResponse.setRawData(paramData);
            customResponse.setData(paramData);
            customResponse.setXmOpCode(basePacket.getXmOpCode());
            if (command != null) {
                CustomCmd customCmd2 = (CustomCmd) command;
                customCmd2.setStatus(basePacket.getStatus());
                customCmd2.setResponse(customResponse);
                return customCmd2;
            }
            CustomCmd customCmd3 = new CustomCmd(new CustomParam(new byte[0]));
            customCmd3.setOpCodeSn(basePacket.getOpCodeSn());
            customCmd3.setStatus(basePacket.getStatus());
            customCmd3.setResponse(customResponse);
            return customCmd3;
        }
        return null;
    }
}
