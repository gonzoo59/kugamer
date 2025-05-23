package com.jieli.jl_bt_ota.model.cmdHandler;

import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.command.GetTargetFeatureMapCmd;
import com.jieli.jl_bt_ota.model.response.TargetFeatureMapResponse;
import com.jieli.jl_bt_ota.tool.CommandHelper;
import com.jieli.jl_bt_ota.util.CHexConver;
/* loaded from: classes2.dex */
public class GetTargetFeatureMapCmdHandler implements ICmdHandler {
    @Override // com.jieli.jl_bt_ota.interfaces.command.ICmdHandler
    public CommandBase parseDataToCmd(BasePacket basePacket) {
        if (basePacket != null && basePacket.getOpCode() == 2) {
            byte[] paramData = basePacket.getParamData();
            if (basePacket.getType() == 1) {
                return new GetTargetFeatureMapCmd().setOpCodeSn(basePacket.getOpCodeSn());
            }
            CommandBase command = CommandHelper.getInstance().getCommand(basePacket.getOpCode(), basePacket.getOpCodeSn());
            TargetFeatureMapResponse targetFeatureMapResponse = new TargetFeatureMapResponse();
            targetFeatureMapResponse.setRawData(paramData);
            if (paramData != null && paramData.length >= 4) {
                byte[] bArr = new byte[4];
                System.arraycopy(paramData, 0, bArr, 0, 4);
                targetFeatureMapResponse.setMask(CHexConver.bytesToInt(bArr));
            }
            if (command != null) {
                GetTargetFeatureMapCmd getTargetFeatureMapCmd = (GetTargetFeatureMapCmd) command;
                getTargetFeatureMapCmd.setStatus(basePacket.getStatus());
                getTargetFeatureMapCmd.setResponse(targetFeatureMapResponse);
                return getTargetFeatureMapCmd;
            }
            GetTargetFeatureMapCmd getTargetFeatureMapCmd2 = new GetTargetFeatureMapCmd();
            getTargetFeatureMapCmd2.setOpCodeSn(basePacket.getOpCodeSn());
            getTargetFeatureMapCmd2.setStatus(basePacket.getStatus());
            getTargetFeatureMapCmd2.setResponse(targetFeatureMapResponse);
            return getTargetFeatureMapCmd2;
        }
        return null;
    }
}
