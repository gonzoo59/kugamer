package com.baidu.kwgames.util;

import com.baidu.kwgames.AppInstance;
import com.baidu.kwgames.Constants;
import com.baidu.kwgames.Units;
import com.blankj.utilcode.util.FileIOUtils;
import java.io.File;
/* loaded from: classes.dex */
public class AIXScope {
    private static byte[] s_arrGunOnOff = new byte[66];
    public static int s_nAutoLoopDelayMS = 0;
    public static int s_nAutoTriggerDelayMS = 0;
    public static int s_nDunEnable = 0;
    public static int s_nPaEnable = 0;
    public static int s_nReserved2 = 0;
    public static int s_nScopeTriggerMode = 0;
    public static int s_nXScopeHoldTime = 60;

    public static boolean get_gun_x_scope_onoff(int i) {
        return i >= 0 && i < 66 && s_arrGunOnOff[i] != 0;
    }

    public static void set_gun_x_scope_onoff(int i, boolean z) {
        if (i < 0 || i >= 66) {
            return;
        }
        s_arrGunOnOff[i] = z ? (byte) 1 : (byte) 0;
    }

    public static void set_all_gun_def_onoff(boolean z) {
        byte b = z ? (byte) 1 : (byte) 0;
        for (int i = 0; i < 66; i++) {
            s_arrGunOnOff[i] = b;
        }
    }

    public static void init() {
        int i = 0;
        set_all_gun_def_onoff(false);
        s_nScopeTriggerMode = 1;
        s_nAutoTriggerDelayMS = 800;
        s_nAutoLoopDelayMS = 0;
        s_nXScopeHoldTime = 60;
        byte[] bArr = s_arrGunOnOff;
        bArr[1] = 1;
        bArr[2] = 1;
        bArr[5] = 1;
        bArr[6] = 1;
        bArr[7] = 1;
        bArr[8] = 1;
        int i2 = 10;
        bArr[10] = 1;
        bArr[11] = 1;
        bArr[12] = 1;
        bArr[13] = 1;
        bArr[14] = 1;
        bArr[15] = 1;
        bArr[17] = 1;
        bArr[19] = 1;
        bArr[20] = 1;
        bArr[26] = 1;
        bArr[27] = 1;
        bArr[28] = 1;
        bArr[29] = 1;
        bArr[30] = 1;
        bArr[31] = 1;
        bArr[34] = 1;
        bArr[37] = 1;
        bArr[39] = 1;
        bArr[40] = 1;
        bArr[42] = 1;
        bArr[44] = 1;
        bArr[49] = 1;
        bArr[54] = 1;
        bArr[57] = 1;
        bArr[58] = 1;
        bArr[61] = 1;
        bArr[63] = 1;
        bArr[64] = 1;
        bArr[65] = 1;
        byte[] readFile2BytesByChannel = FileIOUtils.readFile2BytesByChannel(AppInstance.get().getExternalFilesDir(null) + Constants.get_cur_AI_kit().m_strAIXScopeFileName);
        if (readFile2BytesByChannel != null) {
            int min = Math.min((int) readFile2BytesByChannel[0], 66);
            s_nScopeTriggerMode = readFile2BytesByChannel[1];
            s_nAutoTriggerDelayMS = Units.Short2Int(readFile2BytesByChannel[2], readFile2BytesByChannel[3]);
            s_nAutoLoopDelayMS = Units.Short2Int(readFile2BytesByChannel[4], readFile2BytesByChannel[5]);
            s_nXScopeHoldTime = Units.Short2Int(readFile2BytesByChannel[6], readFile2BytesByChannel[7]);
            s_nDunEnable = readFile2BytesByChannel[8];
            s_nPaEnable = readFile2BytesByChannel[9];
            while (i < min) {
                s_arrGunOnOff[i] = readFile2BytesByChannel[i2];
                i++;
                i2++;
            }
        }
    }

    public static synchronized boolean save() {
        boolean writeFileFromBytesByChannel;
        synchronized (AIXScope.class) {
            byte[] bArr = new byte[76];
            bArr[0] = 66;
            bArr[1] = (byte) s_nScopeTriggerMode;
            bArr[2] = Units.LOBYTE(s_nAutoTriggerDelayMS);
            bArr[3] = Units.HIBYTE(s_nAutoTriggerDelayMS);
            bArr[4] = Units.LOBYTE(s_nAutoLoopDelayMS);
            bArr[5] = Units.HIBYTE(s_nAutoLoopDelayMS);
            bArr[6] = Units.LOBYTE(s_nXScopeHoldTime);
            bArr[7] = Units.HIBYTE(s_nXScopeHoldTime);
            bArr[8] = (byte) s_nDunEnable;
            int i = 10;
            bArr[9] = 0;
            int i2 = 0;
            while (i2 < 66) {
                int i3 = i + 1;
                bArr[i] = s_arrGunOnOff[i2];
                i2++;
                i = i3;
            }
            try {
                File externalFilesDir = AppInstance.get().getExternalFilesDir(null);
                externalFilesDir.mkdirs();
                writeFileFromBytesByChannel = FileIOUtils.writeFileFromBytesByChannel(externalFilesDir + Constants.get_cur_AI_kit().m_strAIXScopeFileName, bArr, true);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return writeFileFromBytesByChannel;
    }
}
