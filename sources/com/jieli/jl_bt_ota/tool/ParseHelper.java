package com.jieli.jl_bt_ota.tool;

import android.text.TextUtils;
import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.base.CommandWithParam;
import com.jieli.jl_bt_ota.model.base.CommandWithParamAndResponse;
import com.jieli.jl_bt_ota.model.base.CommandWithResponse;
import com.jieli.jl_bt_ota.model.cmdHandler.RcspCmdHandler;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.Map;
/* loaded from: classes2.dex */
public class ParseHelper {
    private static final byte END_FLAG = -17;
    private static int MAX_COMMUNICATION_MTU = 520;
    private static final byte PREFIX_FLAG_FIRST = -2;
    private static final byte PREFIX_FLAG_SECOND = -36;
    private static final byte PREFIX_FLAG_THIRD = -70;
    private static String TAG = "ParseHelper";
    private static byte[] cacheBuf;
    private static int cacheLen;
    private static final char[] mChars = "0123456789ABCDEF".toCharArray();

    public static void setMaxCommunicationMtu(int i) {
        if (i <= 0 || MAX_COMMUNICATION_MTU == i) {
            return;
        }
        MAX_COMMUNICATION_MTU = i;
    }

    public static int getMaxCommunicationMtu() {
        return MAX_COMMUNICATION_MTU;
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00af, code lost:
        r9 = r9 + 4;
        r5 = new byte[r9];
        java.lang.System.arraycopy(r4, r6, r5, 0, r9);
        r5 = parsePacketData(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ba, code lost:
        if (r5 == null) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00bc, code lost:
        r12.add(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00bf, code lost:
        if (r8 != r1) goto L50;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.ArrayList<com.jieli.jl_bt_ota.model.base.BasePacket> findPacketData(byte[] r12) {
        /*
            Method dump skipped, instructions count: 238
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.tool.ParseHelper.findPacketData(byte[]):java.util.ArrayList");
    }

    private static void putDataInCache(byte[] bArr, int i, int i2) {
        if (bArr == null || bArr.length <= 0 || i < 0 || i2 <= 0 || i + i2 > bArr.length) {
            return;
        }
        byte[] bArr2 = new byte[i2];
        cacheBuf = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, i2);
        cacheLen = i2;
    }

    private static BasePacket parsePacketData(byte[] bArr) {
        if (bArr == null || bArr.length < 4) {
            return null;
        }
        byte[] booleanArrayBig = CHexConver.getBooleanArrayBig(bArr[0]);
        int byteToInt = CHexConver.byteToInt(bArr[1]);
        byte[] bArr2 = new byte[2];
        System.arraycopy(bArr, 2, bArr2, 0, 2);
        int bytesToInt = CHexConver.bytesToInt(bArr2[0], bArr2[1]);
        BasePacket basePacket = new BasePacket();
        int i = 7;
        int byteToInt2 = CHexConver.byteToInt(booleanArrayBig[7]);
        int i2 = 6;
        int byteToInt3 = CHexConver.byteToInt(booleanArrayBig[6]);
        basePacket.setType(byteToInt2);
        basePacket.setHasResponse(byteToInt3);
        basePacket.setOpCode(byteToInt);
        basePacket.setParamLen(bytesToInt);
        if (bytesToInt > 0) {
            if (byteToInt2 == 0) {
                byte[] bArr3 = new byte[1];
                System.arraycopy(bArr, 4, bArr3, 0, 1);
                basePacket.setStatus(CHexConver.byteToInt(bArr3[0]));
                byte[] bArr4 = new byte[1];
                System.arraycopy(bArr, 5, bArr4, 0, 1);
                basePacket.setOpCodeSn(CHexConver.byteToInt(bArr4[0]));
                if (byteToInt == 1) {
                    byte[] bArr5 = new byte[1];
                    System.arraycopy(bArr, 6, bArr5, 0, 1);
                    basePacket.setXmOpCode(CHexConver.byteToInt(bArr5[0]));
                } else {
                    i = 6;
                }
                i2 = i;
            } else {
                byte[] bArr6 = new byte[1];
                System.arraycopy(bArr, 4, bArr6, 0, 1);
                basePacket.setOpCodeSn(CHexConver.byteToInt(bArr6[0]));
                if (byteToInt == 1) {
                    byte[] bArr7 = new byte[1];
                    System.arraycopy(bArr, 5, bArr7, 0, 1);
                    basePacket.setXmOpCode(CHexConver.byteToInt(bArr7[0]));
                } else {
                    i2 = 5;
                }
            }
            int i3 = bytesToInt - (i2 - 4);
            byte[] bArr8 = new byte[i3];
            System.arraycopy(bArr, i2, bArr8, 0, i3);
            basePacket.setParamData(bArr8);
            String str = TAG;
            JL_Log.w(str, "-parsePacketData- packet type : " + basePacket.getType() + ",opCode : " + basePacket.getOpCode() + ",opCodeSn : " + basePacket.getOpCodeSn());
            return basePacket;
        }
        return basePacket;
    }

    public static CommandBase convert2Command(BasePacket basePacket) {
        if (basePacket != null) {
            CommandBase packCommandObject = packCommandObject(basePacket);
            return packCommandObject != null ? packCommandObject : new RcspCmdHandler().parseDataToCmd(basePacket);
        }
        return null;
    }

    public static BasePacket convert2BasePacket(CommandBase commandBase, int i) {
        if (commandBase != null) {
            int type = commandBase.getType();
            int i2 = 2;
            if (type == 0) {
                BasePacket basePacket = new BasePacket();
                basePacket.setType(i).setHasResponse(0).setOpCode(commandBase.getId()).setOpCodeSn(commandBase.getOpCodeSn());
                if (basePacket.getType() == 0) {
                    basePacket.setStatus(commandBase.getStatus());
                } else {
                    i2 = 1;
                }
                if (basePacket.getOpCode() == 1) {
                    i2++;
                }
                basePacket.setParamLen(i2);
                return basePacket;
            } else if (type == 1) {
                CommandWithParam commandWithParam = (CommandWithParam) commandBase;
                BasePacket basePacket2 = new BasePacket();
                basePacket2.setType(i).setHasResponse(0).setOpCode(commandWithParam.getId()).setOpCodeSn(commandWithParam.getOpCodeSn());
                if (basePacket2.getType() == 0) {
                    basePacket2.setStatus(commandWithParam.getStatus());
                } else {
                    i2 = 1;
                }
                if (basePacket2.getOpCode() == 1) {
                    i2++;
                }
                if (commandWithParam.getParam() != null) {
                    byte[] paramData = commandWithParam.getParam().getParamData();
                    basePacket2.setXmOpCode(commandWithParam.getParam().getXmOpCode());
                    if (paramData != null) {
                        basePacket2.setParamData(paramData);
                        i2 += paramData.length;
                    }
                }
                basePacket2.setParamLen(i2);
                return basePacket2;
            } else if (type == 2) {
                CommandWithParamAndResponse commandWithParamAndResponse = (CommandWithParamAndResponse) commandBase;
                BasePacket basePacket3 = new BasePacket();
                basePacket3.setType(i).setHasResponse(1).setOpCode(commandWithParamAndResponse.getId()).setOpCodeSn(commandWithParamAndResponse.getOpCodeSn());
                if (basePacket3.getType() == 0) {
                    basePacket3.setHasResponse(0);
                    basePacket3.setStatus(commandBase.getStatus());
                } else {
                    i2 = 1;
                }
                if (basePacket3.getOpCode() == 1) {
                    i2++;
                }
                if (commandWithParamAndResponse.getParam() != null) {
                    byte[] paramData2 = commandWithParamAndResponse.getParam().getParamData();
                    basePacket3.setXmOpCode(commandWithParamAndResponse.getParam().getXmOpCode());
                    if (paramData2 != null) {
                        basePacket3.setParamData(paramData2);
                        i2 += paramData2.length;
                    }
                }
                basePacket3.setParamLen(i2);
                return basePacket3;
            } else if (type == 3) {
                CommandWithResponse commandWithResponse = (CommandWithResponse) commandBase;
                BasePacket basePacket4 = new BasePacket();
                basePacket4.setType(i).setHasResponse(1).setOpCode(commandWithResponse.getId()).setOpCodeSn(commandWithResponse.getOpCodeSn());
                if (basePacket4.getType() == 0) {
                    basePacket4.setHasResponse(0);
                    basePacket4.setStatus(commandBase.getStatus());
                } else {
                    i2 = 1;
                }
                if (basePacket4.getOpCode() == 1) {
                    i2++;
                }
                basePacket4.setParamLen(i2);
                return basePacket4;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static byte[] packSendBasePacket(com.jieli.jl_bt_ota.model.base.BasePacket r16) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.tool.ParseHelper.packSendBasePacket(com.jieli.jl_bt_ota.model.base.BasePacket):byte[]");
    }

    private static CommandBase packCommandObject(BasePacket basePacket) {
        Map<Integer, ICmdHandler> validCommandList;
        ICmdHandler iCmdHandler;
        if (basePacket == null || (validCommandList = Command.getValidCommandList()) == null || (iCmdHandler = validCommandList.get(Integer.valueOf(basePacket.getOpCode()))) == null) {
            return null;
        }
        return iCmdHandler.parseDataToCmd(basePacket);
    }

    public static String hexDataCovetToAddress(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr != null && bArr.length == 6) {
            for (int i = 0; i < bArr.length; i++) {
                char[] cArr = mChars;
                sb.append(cArr[(bArr[i] & 255) >> 4]);
                sb.append(cArr[bArr[i] & 15]);
                if (i != bArr.length - 1) {
                    sb.append(":");
                }
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:116:0x0403, code lost:
        com.jieli.jl_bt_ota.util.JL_Log.e(com.jieli.jl_bt_ota.tool.ParseHelper.TAG, java.lang.String.format(java.util.Locale.getDefault(), "parseTargetInfo :: data length[%d] over MAX_COMMUNICATION_MTU[%d], cast away", java.lang.Integer.valueOf(r6), java.lang.Integer.valueOf(com.jieli.jl_bt_ota.tool.ParseHelper.MAX_COMMUNICATION_MTU)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0422, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void parseTargetInfo(com.jieli.jl_bt_ota.model.response.TargetInfoResponse r16, com.jieli.jl_bt_ota.model.base.BasePacket r17) {
        /*
            Method dump skipped, instructions count: 1108
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.tool.ParseHelper.parseTargetInfo(com.jieli.jl_bt_ota.model.response.TargetInfoResponse, com.jieli.jl_bt_ota.model.base.BasePacket):void");
    }

    private static int binaryBytes2Int(byte[] bArr) {
        if (bArr == null || bArr.length != 8) {
            return 0;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(b & 255);
        }
        try {
            return Integer.valueOf(sb.toString(), 2).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int convertVersionByString(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        String str2 = TAG;
        JL_Log.i(str2, "convertVersionByString :: version = " + str);
        String[] split = str.replace("V", "").replace("v", "").split("\\.");
        int length = split.length;
        int[] iArr = new int[length];
        for (int i = 0; i < split.length; i++) {
            String str3 = split[i];
            if (TextUtils.isDigitsOnly(str3)) {
                iArr[i] = Integer.parseInt(str3);
            }
        }
        if (length == 4) {
            byte[] booleanArray = CHexConver.getBooleanArray((byte) iArr[0]);
            byte[] booleanArray2 = CHexConver.getBooleanArray((byte) iArr[1]);
            byte[] bArr = new byte[8];
            System.arraycopy(booleanArray, 4, bArr, 0, 4);
            System.arraycopy(booleanArray2, 4, bArr, 4, 4);
            byte[] booleanArray3 = CHexConver.getBooleanArray((byte) iArr[2]);
            byte[] booleanArray4 = CHexConver.getBooleanArray((byte) iArr[3]);
            byte[] bArr2 = new byte[8];
            System.arraycopy(booleanArray3, 4, bArr2, 0, 4);
            System.arraycopy(booleanArray4, 4, bArr2, 4, 4);
            String str4 = TAG;
            JL_Log.i(str4, "convertVersionByString :: versionCode : 0, heightValue : " + CHexConver.byte2HexStr(bArr, 8) + ", lowValue : " + CHexConver.byte2HexStr(bArr2, 8));
            return CHexConver.bytesToInt((byte) binaryBytes2Int(bArr), (byte) binaryBytes2Int(bArr2));
        }
        return 0;
    }
}
