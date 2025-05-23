package com.jieli.jl_bt_ota.model.cmdHandler;

import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.base.CommonResponse;
import com.jieli.jl_bt_ota.model.command.DisconnectClassicBluetoothCmd;
import com.jieli.jl_bt_ota.tool.CommandHelper;
/* loaded from: classes2.dex */
public class DisconnectClassicBluetoothCmdHandler implements ICmdHandler {
    @Override // com.jieli.jl_bt_ota.interfaces.command.ICmdHandler
    public CommandBase parseDataToCmd(BasePacket basePacket) {
        if (basePacket != null && basePacket.getOpCode() == 6) {
            byte[] paramData = basePacket.getParamData();
            if (basePacket.getType() == 1) {
                return new DisconnectClassicBluetoothCmd().setOpCodeSn(basePacket.getOpCodeSn());
            }
            CommandBase command = CommandHelper.getInstance().getCommand(basePacket.getOpCode(), basePacket.getOpCodeSn());
            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setRawData(paramData);
            if (command != null) {
                DisconnectClassicBluetoothCmd disconnectClassicBluetoothCmd = (DisconnectClassicBluetoothCmd) command;
                disconnectClassicBluetoothCmd.setStatus(basePacket.getStatus());
                disconnectClassicBluetoothCmd.setResponse(commonResponse);
                return disconnectClassicBluetoothCmd;
            }
            DisconnectClassicBluetoothCmd disconnectClassicBluetoothCmd2 = new DisconnectClassicBluetoothCmd();
            disconnectClassicBluetoothCmd2.setOpCodeSn(basePacket.getOpCodeSn());
            disconnectClassicBluetoothCmd2.setStatus(basePacket.getStatus());
            disconnectClassicBluetoothCmd2.setResponse(commonResponse);
            return disconnectClassicBluetoothCmd2;
        }
        return null;
    }
}
