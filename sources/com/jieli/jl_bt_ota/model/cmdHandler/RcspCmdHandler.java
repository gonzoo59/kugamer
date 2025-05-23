package com.jieli.jl_bt_ota.model.cmdHandler;

import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.BaseParameter;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.base.CommandWithParam;
import com.jieli.jl_bt_ota.model.base.CommandWithParamAndResponse;
import com.jieli.jl_bt_ota.model.base.CommandWithResponse;
import com.jieli.jl_bt_ota.model.base.CommonResponse;
import com.jieli.jl_bt_ota.tool.CommandHelper;
import com.jieli.jl_bt_ota.tool.ParseHelper;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.JL_Log;
/* loaded from: classes2.dex */
public class RcspCmdHandler implements ICmdHandler {
    private static final String TAG = "RcspCmdHandler";

    @Override // com.jieli.jl_bt_ota.interfaces.command.ICmdHandler
    public CommandBase parseDataToCmd(BasePacket basePacket) {
        int i;
        CommandBase command = basePacket.getType() == 0 ? CommandHelper.getInstance().getCommand(basePacket.getOpCode(), basePacket.getOpCodeSn()) : null;
        if (command != null) {
            i = command.getType();
            JL_Log.d(TAG, " commandType:::: " + i);
        } else {
            if (basePacket.getType() == 1) {
                if (basePacket.getHasResponse() == 1) {
                    i = basePacket.getParamData() != null ? 2 : 3;
                } else if (basePacket.getParamData() != null) {
                    i = 1;
                }
            } else if (basePacket.getParamData() != null) {
                byte[] packSendBasePacket = ParseHelper.packSendBasePacket(basePacket);
                if (packSendBasePacket != null) {
                    JL_Log.w(TAG, " unknown data ::::::: " + CHexConver.byte2HexStr(packSendBasePacket, packSendBasePacket.length));
                }
                return null;
            }
            i = 0;
        }
        if (i == 0) {
            CommandBase commandBase = new CommandBase(basePacket.getOpCode(), "CommandBase");
            commandBase.setType(i);
            commandBase.setOpCodeSn(basePacket.getOpCodeSn());
            commandBase.setStatus(basePacket.getStatus());
            return commandBase;
        } else if (i == 1) {
            BaseParameter baseParameter = new BaseParameter();
            baseParameter.setXmOpCode(basePacket.getXmOpCode());
            baseParameter.setParamData(basePacket.getParamData());
            CommandWithParam commandWithParam = new CommandWithParam(basePacket.getOpCode(), "CommandWithParam", baseParameter);
            commandWithParam.setType(i);
            commandWithParam.setOpCodeSn(basePacket.getOpCodeSn());
            commandWithParam.setStatus(basePacket.getStatus());
            return commandWithParam;
        } else if (i != 2) {
            if (i != 3) {
                return null;
            }
            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setRawData(basePacket.getParamData());
            commonResponse.setXmOpCode(basePacket.getXmOpCode());
            if (command != null) {
                CommandWithResponse commandWithResponse = (CommandWithResponse) command;
                commandWithResponse.setType(i);
                commandWithResponse.setOpCodeSn(basePacket.getOpCodeSn());
                commandWithResponse.setStatus(basePacket.getStatus());
                commandWithResponse.setResponse(commonResponse);
                return commandWithResponse;
            }
            CommandWithResponse commandWithResponse2 = new CommandWithResponse(basePacket.getOpCode(), "CommandWithResponse");
            commandWithResponse2.setType(i);
            commandWithResponse2.setOpCodeSn(basePacket.getOpCodeSn());
            commandWithResponse2.setStatus(basePacket.getStatus());
            commandWithResponse2.setResponse(commonResponse);
            return commandWithResponse2;
        } else if (command != null) {
            CommonResponse commonResponse2 = new CommonResponse();
            commonResponse2.setRawData(basePacket.getParamData());
            ((CommandWithParamAndResponse) command).setResponse(commonResponse2);
            return command;
        } else {
            BaseParameter baseParameter2 = new BaseParameter();
            CommandWithParamAndResponse commandWithParamAndResponse = new CommandWithParamAndResponse(basePacket.getOpCode(), "CommandWithParamAndResponse", baseParameter2);
            if (basePacket.getType() == 1) {
                baseParameter2.setXmOpCode(basePacket.getXmOpCode());
                baseParameter2.setParamData(basePacket.getParamData());
            } else {
                CommonResponse commonResponse3 = new CommonResponse();
                commonResponse3.setRawData(basePacket.getParamData());
                commonResponse3.setXmOpCode(basePacket.getXmOpCode());
                commandWithParamAndResponse.setResponse(commonResponse3);
            }
            commandWithParamAndResponse.setType(i);
            commandWithParamAndResponse.setOpCodeSn(basePacket.getOpCodeSn());
            commandWithParamAndResponse.setStatus(basePacket.getStatus());
            return commandWithParamAndResponse;
        }
    }
}
