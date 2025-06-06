package com.jieli.jl_bt_ota.model.cmdHandler;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.base.CommonResponse;
import com.jieli.jl_bt_ota.model.command.EnterUpdateModeCmd;
import com.jieli.jl_bt_ota.model.command.ExitUpdateModeCmd;
import com.jieli.jl_bt_ota.model.command.FirmwareUpdateBlockCmd;
import com.jieli.jl_bt_ota.model.command.FirmwareUpdateStatusCmd;
import com.jieli.jl_bt_ota.model.command.GetUpdateFileOffsetCmd;
import com.jieli.jl_bt_ota.model.command.InquireUpdateCmd;
import com.jieli.jl_bt_ota.model.command.NotifyUpdateContentSizeCmd;
import com.jieli.jl_bt_ota.model.command.RebootDeviceCmd;
import com.jieli.jl_bt_ota.model.parameter.FirmwareUpdateBlockParam;
import com.jieli.jl_bt_ota.model.parameter.InquireUpdateParam;
import com.jieli.jl_bt_ota.model.parameter.NotifyUpdateContentSizeParam;
import com.jieli.jl_bt_ota.model.parameter.RebootDeviceParam;
import com.jieli.jl_bt_ota.model.response.EnterUpdateModeResponse;
import com.jieli.jl_bt_ota.model.response.ExitUpdateModeResponse;
import com.jieli.jl_bt_ota.model.response.FirmwareUpdateBlockResponse;
import com.jieli.jl_bt_ota.model.response.FirmwareUpdateStatusResponse;
import com.jieli.jl_bt_ota.model.response.InquireUpdateResponse;
import com.jieli.jl_bt_ota.model.response.RebootDeviceResponse;
import com.jieli.jl_bt_ota.model.response.UpdateFileOffsetResponse;
import com.jieli.jl_bt_ota.tool.CommandHelper;
import com.jieli.jl_bt_ota.util.CHexConver;
/* loaded from: classes2.dex */
public class OtaCmdHandler implements ICmdHandler {
    @Override // com.jieli.jl_bt_ota.interfaces.command.ICmdHandler
    public CommandBase parseDataToCmd(BasePacket basePacket) {
        int i;
        int i2;
        int i3;
        if (basePacket == null) {
            return null;
        }
        int opCode = basePacket.getOpCode();
        if (opCode == 225 || opCode == 226 || opCode == 227 || opCode == 228 || opCode == 229 || opCode == 230 || opCode == 231 || opCode == 232) {
            byte[] paramData = basePacket.getParamData();
            int i4 = 0;
            if (basePacket.getType() == 1) {
                switch (opCode) {
                    case Command.CMD_OTA_GET_DEVICE_UPDATE_FILE_INFO_OFFSET /* 225 */:
                        return new GetUpdateFileOffsetCmd().setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_OTA_INQUIRE_DEVICE_IF_CAN_UPDATE /* 226 */:
                        byte[] bArr = new byte[0];
                        if (paramData == null || paramData.length <= 0) {
                            paramData = bArr;
                        }
                        return new InquireUpdateCmd(new InquireUpdateParam(paramData)).setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_OTA_ENTER_UPDATE_MODE /* 227 */:
                        return new EnterUpdateModeCmd().setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_OTA_EXIT_UPDATE_MODE /* 228 */:
                        return new ExitUpdateModeCmd().setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_OTA_SEND_FIRMWARE_UPDATE_BLOCK /* 229 */:
                        if (paramData == null || paramData.length < 6) {
                            i2 = 0;
                        } else {
                            byte[] bArr2 = new byte[4];
                            System.arraycopy(paramData, 0, bArr2, 0, 4);
                            i4 = CHexConver.bytesToInt(bArr2);
                            i2 = CHexConver.bytesToInt(paramData[4], paramData[5]);
                        }
                        return new FirmwareUpdateBlockCmd(new FirmwareUpdateBlockParam(i4, i2)).setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_OTA_GET_DEVICE_REFRESH_FIRMWARE_STATUS /* 230 */:
                        return new FirmwareUpdateStatusCmd().setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_REBOOT_DEVICE /* 231 */:
                        if (paramData != null && paramData.length > 0) {
                            i4 = CHexConver.byteToInt(paramData[0]);
                        }
                        return new RebootDeviceCmd(new RebootDeviceParam(i4)).setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_OTA_NOTIFY_UPDATE_CONTENT_SIZE /* 232 */:
                        if (paramData != null && paramData.length >= 4) {
                            byte[] bArr3 = new byte[4];
                            System.arraycopy(paramData, 0, bArr3, 0, 4);
                            int bytesToInt = CHexConver.bytesToInt(bArr3);
                            if (paramData.length >= 8) {
                                System.arraycopy(paramData, 4, bArr3, 0, 4);
                                i3 = CHexConver.bytesToInt(bArr3);
                                i4 = bytesToInt;
                                return new NotifyUpdateContentSizeCmd(new NotifyUpdateContentSizeParam(i4).setCurrentProgress(i3)).setOpCodeSn(basePacket.getOpCodeSn());
                            }
                            i4 = bytesToInt;
                        }
                        i3 = 0;
                        return new NotifyUpdateContentSizeCmd(new NotifyUpdateContentSizeParam(i4).setCurrentProgress(i3)).setOpCodeSn(basePacket.getOpCodeSn());
                    default:
                        return null;
                }
            }
            CommandBase command = CommandHelper.getInstance().getCommand(opCode, basePacket.getOpCodeSn());
            int i5 = -1;
            switch (opCode) {
                case Command.CMD_OTA_GET_DEVICE_UPDATE_FILE_INFO_OFFSET /* 225 */:
                    if (paramData == null || paramData.length < 6) {
                        i = 0;
                    } else {
                        byte[] bArr4 = new byte[4];
                        System.arraycopy(paramData, 0, bArr4, 0, 4);
                        i4 = CHexConver.bytesToInt(bArr4);
                        i = CHexConver.bytesToInt(paramData[4], paramData[5]);
                    }
                    UpdateFileOffsetResponse updateFileOffsetResponse = new UpdateFileOffsetResponse();
                    updateFileOffsetResponse.setRawData(paramData);
                    updateFileOffsetResponse.setUpdateFileFlagOffset(i4);
                    updateFileOffsetResponse.setUpdateFileFlagLen(i);
                    if (command != null) {
                        GetUpdateFileOffsetCmd getUpdateFileOffsetCmd = (GetUpdateFileOffsetCmd) command;
                        getUpdateFileOffsetCmd.setStatus(basePacket.getStatus());
                        getUpdateFileOffsetCmd.setResponse(updateFileOffsetResponse);
                        return getUpdateFileOffsetCmd;
                    }
                    GetUpdateFileOffsetCmd getUpdateFileOffsetCmd2 = new GetUpdateFileOffsetCmd();
                    getUpdateFileOffsetCmd2.setOpCodeSn(basePacket.getOpCodeSn());
                    getUpdateFileOffsetCmd2.setStatus(basePacket.getStatus());
                    getUpdateFileOffsetCmd2.setResponse(updateFileOffsetResponse);
                    return getUpdateFileOffsetCmd2;
                case Command.CMD_OTA_INQUIRE_DEVICE_IF_CAN_UPDATE /* 226 */:
                    if (paramData != null && paramData.length > 0) {
                        i5 = CHexConver.byteToInt(paramData[0]);
                    }
                    InquireUpdateResponse inquireUpdateResponse = new InquireUpdateResponse(i5);
                    inquireUpdateResponse.setRawData(paramData);
                    if (command != null) {
                        InquireUpdateCmd inquireUpdateCmd = (InquireUpdateCmd) command;
                        inquireUpdateCmd.setStatus(basePacket.getStatus());
                        inquireUpdateCmd.setResponse(inquireUpdateResponse);
                        return inquireUpdateCmd;
                    }
                    InquireUpdateCmd inquireUpdateCmd2 = new InquireUpdateCmd(new InquireUpdateParam(new byte[0]));
                    inquireUpdateCmd2.setOpCodeSn(basePacket.getOpCodeSn());
                    inquireUpdateCmd2.setStatus(basePacket.getStatus());
                    inquireUpdateCmd2.setResponse(inquireUpdateResponse);
                    return inquireUpdateCmd2;
                case Command.CMD_OTA_ENTER_UPDATE_MODE /* 227 */:
                    if (paramData != null && paramData.length > 0) {
                        i5 = CHexConver.byteToInt(paramData[0]);
                    }
                    EnterUpdateModeResponse enterUpdateModeResponse = new EnterUpdateModeResponse(i5);
                    enterUpdateModeResponse.setRawData(paramData);
                    if (command != null) {
                        EnterUpdateModeCmd enterUpdateModeCmd = (EnterUpdateModeCmd) command;
                        enterUpdateModeCmd.setStatus(basePacket.getStatus());
                        enterUpdateModeCmd.setResponse(enterUpdateModeResponse);
                        return enterUpdateModeCmd;
                    }
                    EnterUpdateModeCmd enterUpdateModeCmd2 = new EnterUpdateModeCmd();
                    enterUpdateModeCmd2.setOpCodeSn(basePacket.getOpCodeSn());
                    enterUpdateModeCmd2.setStatus(basePacket.getStatus());
                    enterUpdateModeCmd2.setResponse(enterUpdateModeResponse);
                    return enterUpdateModeCmd2;
                case Command.CMD_OTA_EXIT_UPDATE_MODE /* 228 */:
                    if (paramData != null && paramData.length > 0) {
                        i4 = CHexConver.byteToInt(paramData[0]);
                    }
                    ExitUpdateModeResponse exitUpdateModeResponse = new ExitUpdateModeResponse(i4);
                    exitUpdateModeResponse.setRawData(paramData);
                    if (command != null) {
                        ExitUpdateModeCmd exitUpdateModeCmd = (ExitUpdateModeCmd) command;
                        exitUpdateModeCmd.setStatus(basePacket.getStatus());
                        exitUpdateModeCmd.setResponse(exitUpdateModeResponse);
                        return exitUpdateModeCmd;
                    }
                    ExitUpdateModeCmd exitUpdateModeCmd2 = new ExitUpdateModeCmd();
                    exitUpdateModeCmd2.setOpCodeSn(basePacket.getOpCodeSn());
                    exitUpdateModeCmd2.setStatus(basePacket.getStatus());
                    exitUpdateModeCmd2.setResponse(exitUpdateModeResponse);
                    return exitUpdateModeCmd2;
                case Command.CMD_OTA_SEND_FIRMWARE_UPDATE_BLOCK /* 229 */:
                    byte[] bArr5 = new byte[0];
                    if (paramData != null && paramData.length > 0) {
                        bArr5 = paramData;
                    }
                    FirmwareUpdateBlockResponse firmwareUpdateBlockResponse = new FirmwareUpdateBlockResponse();
                    firmwareUpdateBlockResponse.setRawData(paramData);
                    firmwareUpdateBlockResponse.setFirmwareUpdateBlockData(bArr5);
                    if (command != null) {
                        FirmwareUpdateBlockCmd firmwareUpdateBlockCmd = (FirmwareUpdateBlockCmd) command;
                        firmwareUpdateBlockCmd.setStatus(basePacket.getStatus());
                        firmwareUpdateBlockCmd.setResponse(firmwareUpdateBlockResponse);
                        return firmwareUpdateBlockCmd;
                    }
                    FirmwareUpdateBlockCmd firmwareUpdateBlockCmd2 = new FirmwareUpdateBlockCmd(new FirmwareUpdateBlockParam(0, 0));
                    firmwareUpdateBlockCmd2.setOpCodeSn(basePacket.getOpCodeSn());
                    firmwareUpdateBlockCmd2.setStatus(basePacket.getStatus());
                    firmwareUpdateBlockCmd2.setResponse(firmwareUpdateBlockResponse);
                    return firmwareUpdateBlockCmd2;
                case Command.CMD_OTA_GET_DEVICE_REFRESH_FIRMWARE_STATUS /* 230 */:
                    if (paramData != null && paramData.length > 0) {
                        i5 = CHexConver.byteToInt(paramData[0]);
                    }
                    FirmwareUpdateStatusResponse firmwareUpdateStatusResponse = new FirmwareUpdateStatusResponse(i5);
                    firmwareUpdateStatusResponse.setRawData(paramData);
                    if (command != null) {
                        FirmwareUpdateStatusCmd firmwareUpdateStatusCmd = (FirmwareUpdateStatusCmd) command;
                        firmwareUpdateStatusCmd.setStatus(basePacket.getStatus());
                        firmwareUpdateStatusCmd.setResponse(firmwareUpdateStatusResponse);
                        return firmwareUpdateStatusCmd;
                    }
                    FirmwareUpdateStatusCmd firmwareUpdateStatusCmd2 = new FirmwareUpdateStatusCmd();
                    firmwareUpdateStatusCmd2.setOpCodeSn(basePacket.getOpCodeSn());
                    firmwareUpdateStatusCmd2.setStatus(basePacket.getStatus());
                    firmwareUpdateStatusCmd2.setResponse(firmwareUpdateStatusResponse);
                    return firmwareUpdateStatusCmd2;
                case Command.CMD_REBOOT_DEVICE /* 231 */:
                    if (paramData != null && paramData.length > 0) {
                        i5 = CHexConver.byteToInt(paramData[0]);
                    }
                    RebootDeviceResponse rebootDeviceResponse = new RebootDeviceResponse(i5);
                    rebootDeviceResponse.setRawData(paramData);
                    if (command != null) {
                        RebootDeviceCmd rebootDeviceCmd = (RebootDeviceCmd) command;
                        rebootDeviceCmd.setStatus(basePacket.getStatus());
                        rebootDeviceCmd.setResponse(rebootDeviceResponse);
                        return rebootDeviceCmd;
                    }
                    RebootDeviceCmd rebootDeviceCmd2 = new RebootDeviceCmd(new RebootDeviceParam(0));
                    rebootDeviceCmd2.setOpCodeSn(basePacket.getOpCodeSn());
                    rebootDeviceCmd2.setStatus(basePacket.getStatus());
                    rebootDeviceCmd2.setResponse(rebootDeviceResponse);
                    return rebootDeviceCmd2;
                case Command.CMD_OTA_NOTIFY_UPDATE_CONTENT_SIZE /* 232 */:
                    CommonResponse commonResponse = new CommonResponse();
                    commonResponse.setRawData(paramData);
                    if (command != null) {
                        NotifyUpdateContentSizeCmd notifyUpdateContentSizeCmd = (NotifyUpdateContentSizeCmd) command;
                        notifyUpdateContentSizeCmd.setStatus(basePacket.getStatus());
                        notifyUpdateContentSizeCmd.setResponse(commonResponse);
                        return notifyUpdateContentSizeCmd;
                    }
                    NotifyUpdateContentSizeCmd notifyUpdateContentSizeCmd2 = new NotifyUpdateContentSizeCmd(new NotifyUpdateContentSizeParam(0));
                    notifyUpdateContentSizeCmd2.setOpCodeSn(basePacket.getOpCodeSn());
                    notifyUpdateContentSizeCmd2.setStatus(basePacket.getStatus());
                    notifyUpdateContentSizeCmd2.setResponse(commonResponse);
                    return notifyUpdateContentSizeCmd2;
                default:
                    return null;
            }
        }
        return null;
    }
}
