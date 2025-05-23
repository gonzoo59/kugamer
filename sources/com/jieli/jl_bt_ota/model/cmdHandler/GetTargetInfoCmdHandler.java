package com.jieli.jl_bt_ota.model.cmdHandler;

import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.command.GetTargetInfoCmd;
import com.jieli.jl_bt_ota.model.parameter.GetTargetInfoParam;
import com.jieli.jl_bt_ota.model.response.TargetInfoResponse;
import com.jieli.jl_bt_ota.tool.CommandHelper;
import com.jieli.jl_bt_ota.tool.ParseHelper;
import com.jieli.jl_bt_ota.util.CHexConver;
/* loaded from: classes2.dex */
public class GetTargetInfoCmdHandler implements ICmdHandler {
    @Override // com.jieli.jl_bt_ota.interfaces.command.ICmdHandler
    public CommandBase parseDataToCmd(BasePacket basePacket) {
        byte b;
        if (basePacket != null && basePacket.getOpCode() == 3) {
            byte[] paramData = basePacket.getParamData();
            int i = 0;
            if (basePacket.getType() == 1) {
                if (paramData != null && paramData.length >= 4) {
                    byte[] bArr = new byte[4];
                    System.arraycopy(paramData, 0, bArr, 0, 4);
                    int bytesToInt = CHexConver.bytesToInt(bArr);
                    if (paramData.length >= 5) {
                        b = paramData[4];
                        i = bytesToInt;
                        return new GetTargetInfoCmd(new GetTargetInfoParam(i).setPlatform(b)).setOpCodeSn(basePacket.getOpCodeSn());
                    }
                    i = bytesToInt;
                }
                b = 0;
                return new GetTargetInfoCmd(new GetTargetInfoParam(i).setPlatform(b)).setOpCodeSn(basePacket.getOpCodeSn());
            }
            CommandBase command = CommandHelper.getInstance().getCommand(basePacket.getOpCode(), basePacket.getOpCodeSn());
            TargetInfoResponse targetInfoResponse = new TargetInfoResponse();
            targetInfoResponse.setRawData(paramData);
            ParseHelper.parseTargetInfo(targetInfoResponse, basePacket);
            if (command != null) {
                GetTargetInfoCmd getTargetInfoCmd = (GetTargetInfoCmd) command;
                getTargetInfoCmd.setStatus(basePacket.getStatus());
                getTargetInfoCmd.setResponse(targetInfoResponse);
                return getTargetInfoCmd;
            }
            GetTargetInfoCmd getTargetInfoCmd2 = new GetTargetInfoCmd(new GetTargetInfoParam(0).setPlatform((byte) 0));
            getTargetInfoCmd2.setOpCodeSn(basePacket.getOpCodeSn());
            getTargetInfoCmd2.setStatus(basePacket.getStatus());
            getTargetInfoCmd2.setResponse(targetInfoResponse);
            return getTargetInfoCmd2;
        }
        return null;
    }
}
