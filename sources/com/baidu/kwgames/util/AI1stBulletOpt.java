package com.baidu.kwgames.util;

import com.baidu.kwgames.AppInstance;
import com.blankj.utilcode.util.FileIOUtils;
import java.io.File;
/* loaded from: classes.dex */
public class AI1stBulletOpt {
    private static byte[] s_arrGunOnOff = new byte[66];

    public static boolean get_onoff(int i) {
        return i >= 0 && i < 66 && s_arrGunOnOff[i] != 0;
    }

    public static byte get_onoff_ble_send(int i) {
        if (i < 0 || i >= 66) {
            return (byte) 0;
        }
        return s_arrGunOnOff[i];
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
        int i = 1;
        set_all_gun_def_onoff(true);
        byte[] readFile2BytesByChannel = FileIOUtils.readFile2BytesByChannel(AppInstance.get().getExternalFilesDir(null) + "/AI1stBulletOpt" + AppInstance.s_nAIKit);
        if (readFile2BytesByChannel != null) {
            int i2 = 0;
            int min = Math.min((int) readFile2BytesByChannel[0], 66);
            while (i2 < min) {
                s_arrGunOnOff[i2] = readFile2BytesByChannel[i];
                i2++;
                i++;
            }
        }
    }

    public static synchronized boolean save() {
        boolean writeFileFromBytesByChannel;
        synchronized (AI1stBulletOpt.class) {
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
                writeFileFromBytesByChannel = FileIOUtils.writeFileFromBytesByChannel(externalFilesDir + "/AI1stBulletOpt" + AppInstance.s_nAIKit, bArr, true);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return writeFileFromBytesByChannel;
    }
}
