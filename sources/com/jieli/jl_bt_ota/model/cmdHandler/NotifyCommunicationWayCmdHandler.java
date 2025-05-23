package com.jieli.jl_bt_ota.model.cmdHandler;

import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.base.CommonResponse;
import com.jieli.jl_bt_ota.model.command.NotifyCommunicationWayCmd;
import com.jieli.jl_bt_ota.model.parameter.NotifyCommunicationWayParam;
import com.jieli.jl_bt_ota.tool.CommandHelper;
import com.jieli.jl_bt_ota.util.CHexConver;
/* loaded from: classes2.dex */
public class NotifyCommunicationWayCmdHandler implements ICmdHandler {
    @Override // com.jieli.jl_bt_ota.interfaces.command.ICmdHandler
    public CommandBase parseDataToCmd(BasePacket basePacket) {
        if (basePacket != null && basePacket.getOpCode() == 11) {
            byte[] paramData = basePacket.getParamData();
            int i = 0;
            if (basePacket.getType() == 1) {
                if (paramData != null && paramData.length > 0) {
                    i = CHexConver.byteToInt(paramData[0]);
                }
                return new NotifyCommunicationWayCmd(new NotifyCommunicationWayParam(i)).setOpCodeSn(basePacket.getOpCodeSn());
            }
            CommandBase command = CommandHelper.getInstance().getCommand(basePacket.getOpCode(), basePacket.getOpCodeSn());
            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setRawData(paramData);
            if (command != null) {
                NotifyCommunicationWayCmd notifyCommunicationWayCmd = (NotifyCommunicationWayCmd) command;
                notifyCommunicationWayCmd.setStatus(basePacket.getStatus());
                notifyCommunicationWayCmd.setResponse(commonResponse);
                return notifyCommunicationWayCmd;
            }
            NotifyCommunicationWayCmd notifyCommunicationWayCmd2 = new NotifyCommunicationWayCmd(new NotifyCommunicationWayParam(0));
            notifyCommunicationWayCmd2.setOpCodeSn(basePacket.getOpCodeSn());
            notifyCommunicationWayCmd2.setStatus(basePacket.getStatus());
            notifyCommunicationWayCmd2.setResponse(commonResponse);
            return notifyCommunicationWayCmd2;
        }
        return null;
    }
}
