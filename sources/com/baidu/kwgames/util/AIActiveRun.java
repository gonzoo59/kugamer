package com.baidu.kwgames.util;

import com.baidu.kwgames.AppInstance;
import com.baidu.kwgames.Constants;
import com.blankj.utilcode.util.FileIOUtils;
import java.io.File;
/* loaded from: classes.dex */
public class AIActiveRun {
    private static byte[] s_arrGunOnOff = new byte[66];

    public static boolean get_onoff(int i) {
        return i >= 0 && i < 66 && s_arrGunOnOff[i] != 0;
    }

    public static void set_onoff(int i, boolean z) {
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
        byte[] bArr = s_arrGunOnOff;
        int i2 = 1;
        bArr[1] = 1;
        bArr[2] = 1;
        bArr[5] = 1;
        bArr[6] = 1;
        bArr[7] = 1;
        bArr[8] = 1;
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
        bArr[4] = 1;
        bArr[16] = 1;
        bArr[23] = 1;
        bArr[24] = 1;
        bArr[25] = 1;
        bArr[43] = 1;
        bArr[46] = 1;
        bArr[56] = 1;
        bArr[57] = 1;
        bArr[58] = 1;
        bArr[59] = 1;
        bArr[60] = 1;
        bArr[61] = 1;
        bArr[63] = 1;
        bArr[64] = 1;
        bArr[65] = 1;
        byte[] readFile2BytesByChannel = FileIOUtils.readFile2BytesByChannel(AppInstance.get().getExternalFilesDir(null) + Constants.get_cur_AI_kit().m_strAIActiveRunFileName);
        if (readFile2BytesByChannel != null) {
            int min = Math.min((int) readFile2BytesByChannel[0], 66);
            while (i < min) {
                s_arrGunOnOff[i] = readFile2BytesByChannel[i2];
                i++;
                i2++;
            }
        }
    }

    public static synchronized boolean save() {
        boolean writeFileFromBytesByChannel;
        synchronized (AIActiveRun.class) {
            byte[] bArr = new byte[67];
            bArr[0] = 66;
            int i = 0;
            int i2 = 1;
            while (i < 66) {
                int i3 = i2 + 1;
                bArr[i2] = s_arrGunOnOff[i];
                i++;
                i2 = i3;
            }
            try {
                File externalFilesDir = AppInstance.get().getExternalFilesDir(null);
                externalFilesDir.mkdirs();
                writeFileFromBytesByChannel = FileIOUtils.writeFileFromBytesByChannel(externalFilesDir + Constants.get_cur_AI_kit().m_strAIActiveRunFileName, bArr, true);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return writeFileFromBytesByChannel;
    }
}
