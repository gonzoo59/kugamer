package com.jieli.jl_bt_ota.model.cmdHandler;

import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.command.SettingsMtuCmd;
import com.jieli.jl_bt_ota.model.parameter.SettingsMtuParam;
import com.jieli.jl_bt_ota.model.response.SettingsMtuResponse;
import com.jieli.jl_bt_ota.tool.CommandHelper;
import com.jieli.jl_bt_ota.tool.ParseHelper;
import com.jieli.jl_bt_ota.util.CHexConver;
/* loaded from: classes2.dex */
public class SettingsMtuCmdHandler implements ICmdHandler {
    @Override // com.jieli.jl_bt_ota.interfaces.command.ICmdHandler
    public CommandBase parseDataToCmd(BasePacket basePacket) {
        if (basePacket != null && basePacket.getOpCode() == 209) {
            byte[] paramData = basePacket.getParamData();
            if (basePacket.getType() == 1) {
                int maxCommunicationMtu = ParseHelper.getMaxCommunicationMtu();
                if (paramData != null && paramData.length >= 2) {
                    maxCommunicationMtu = CHexConver.bytesToInt(paramData[0], paramData[1]);
                }
                return new SettingsMtuCmd(new SettingsMtuParam(maxCommunicationMtu)).setOpCodeSn(basePacket.getOpCodeSn());
            }
            CommandBase command = CommandHelper.getInstance().getCommand(basePacket.getOpCode(), basePacket.getOpCodeSn());
            SettingsMtuResponse settingsMtuResponse = new SettingsMtuResponse(ParseHelper.getMaxCommunicationMtu());
            settingsMtuResponse.setRawData(paramData);
            if (paramData != null && paramData.length >= 2) {
                settingsMtuResponse.setRealMtu(CHexConver.bytesToInt(paramData[0], paramData[1]));
            }
            if (command != null) {
                SettingsMtuCmd settingsMtuCmd = (SettingsMtuCmd) command;
                settingsMtuCmd.setStatus(basePacket.getStatus());
                settingsMtuCmd.setResponse(settingsMtuResponse);
                return settingsMtuCmd;
            }
            SettingsMtuCmd settingsMtuCmd2 = new SettingsMtuCmd(new SettingsMtuParam(ParseHelper.getMaxCommunicationMtu()));
            settingsMtuCmd2.setOpCodeSn(basePacket.getOpCodeSn());
            settingsMtuCmd2.setStatus(basePacket.getStatus());
            settingsMtuCmd2.setResponse(settingsMtuResponse);
            return settingsMtuCmd2;
        }
        return null;
    }
}
