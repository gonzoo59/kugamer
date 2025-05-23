package com.baidu.kwgames.util;

import com.baidu.kwgames.AppInstance;
import com.baidu.kwgames.Constants;
import com.blankj.utilcode.util.FileIOUtils;
import java.io.File;
import java.lang.reflect.Array;
/* loaded from: classes.dex */
public class AgileMgr {
    private static final int s_nCurGunSensVer = 2;
    private static final int[] g_arrGunDownConvert = new int[65];
    public static byte[][][] s_arrGunPress = (byte[][][]) Array.newInstance(byte.class, 66, 3, 7);
    public static byte[][] s_arrAIGunStage = (byte[][]) Array.newInstance(byte.class, 66, 6);

    public static boolean set_gun_press(int i, int i2, int i3, int i4, boolean z) {
        if (i < 0 || i >= 66 || i2 < 0 || i2 >= 3 || i3 < 0 || i3 >= 7) {
            return false;
        }
        if (!z || i3 == 0 || 6 == i3) {
            s_arrGunPress[i][i2][i3] = (byte) i4;
            return true;
        }
        for (int i5 = 1; i5 < 6; i5++) {
            s_arrGunPress[i][i2][i5] = (byte) i4;
        }
        return true;
    }

    public static int get_gun_press_for_send(int i, int i2, int i3, int i4) {
        if (i < 0 || i >= 66 || i2 < 0 || i2 >= 3 || i3 < 0 || i3 >= 7) {
            return 0;
        }
        byte[][][] bArr = s_arrGunPress;
        return (bArr[i][i2][i4] << 8) | bArr[i][i2][i3];
    }

    public static void set_def_gun_press(int i, int i2, int i3, int i4) {
        if (i >= 66 || i < 0) {
            return;
        }
        for (int i5 = 1; i5 < 7; i5++) {
            s_arrGunPress[i][0][i5] = (byte) i2;
        }
        for (int i6 = 1; i6 < 7; i6++) {
            s_arrGunPress[i][1][i6] = (byte) i3;
        }
        for (int i7 = 1; i7 < 7; i7++) {
            s_arrGunPress[i][2][i7] = (byte) i4;
        }
    }

    public static void set_def_gun_off_press(int i, int i2, int i3, int i4) {
        if (i >= 66 || i < 0) {
            return;
        }
        byte[][][] bArr = s_arrGunPress;
        bArr[i][0][0] = (byte) i2;
        bArr[i][1][0] = (byte) i3;
        bArr[i][2][0] = (byte) i4;
    }

    public static boolean set_AI_gun_stage(int i, byte[] bArr) {
        if (i < 0 || i >= 66 || bArr.length != 6) {
            return false;
        }
        for (int i2 = 0; i2 < 6; i2++) {
            s_arrAIGunStage[i][i2] = bArr[i2];
        }
        return true;
    }

    public static boolean set_AI_gun_stage(int i, int i2, byte b) {
        if (i < 0 || i >= 66 || i2 < 0 || i2 >= 6) {
            return false;
        }
        s_arrAIGunStage[i][i2] = b;
        return true;
    }

    public static void set_AI_gun_stage(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        if (i < 0 || i >= 66) {
            return;
        }
        byte[][] bArr = s_arrAIGunStage;
        bArr[i][0] = (byte) i2;
        bArr[i][1] = (byte) i3;
        bArr[i][2] = (byte) i4;
        bArr[i][3] = (byte) i5;
        bArr[i][4] = (byte) i6;
        bArr[i][5] = (byte) i7;
    }

    public static byte[] get_AI_gun_stage(int i) {
        if (i < 0 || i >= 66) {
            return null;
        }
        byte[] bArr = new byte[6];
        for (int i2 = 0; i2 < 6; i2++) {
            bArr[i2] = s_arrAIGunStage[i][i2];
        }
        return bArr;
    }

    public static void get_AI_gun_stage(int i, byte[] bArr) {
        if (i < 0 || i >= 66) {
            return;
        }
        for (int i2 = 0; i2 < 6; i2++) {
            bArr[i2] = s_arrAIGunStage[i][i2];
        }
    }

    public static byte get_AI_gun_stage(int i, int i2) {
        return (i < 0 || i >= 66 || i2 < 0 || i2 >= 6) ? Constants.KEY_102ND : s_arrAIGunStage[i][i2];
    }

    public static void load_AI_gun_stage() {
        int i = 0;
        while (true) {
            if (i >= 66) {
                break;
            }
            for (int i2 = 0; i2 < 6; i2++) {
                s_arrAIGunStage[i][i2] = Constants.KEY_102ND;
            }
            i++;
        }
        if (NEAT.is_pad()) {
            set_AI_gun_stage(58, 85, 100, 100, 100, 100, 0);
        } else {
            set_AI_gun_stage(58, 82, 95, 98, 97, 97, 0);
        }
        byte[] readFile2BytesByChannel = FileIOUtils.readFile2BytesByChannel(AppInstance.get().getExternalFilesDir(null) + Constants.get_cur_AI_kit().m_strAIGunStageFileName);
        if (readFile2BytesByChannel != null) {
            int length = readFile2BytesByChannel.length / 6;
            int i3 = length <= 66 ? length : 66;
            int i4 = 0;
            for (int i5 = 0; i5 < i3; i5++) {
                int i6 = 0;
                while (i6 < 6) {
                    s_arrAIGunStage[i5][i6] = readFile2BytesByChannel[i4];
                    i6++;
                    i4++;
                }
            }
        } else if (NEAT.is_pad()) {
            set_AI_gun_stage(42, 78, 78, 78, 0, 0, 0);
            set_AI_gun_stage(5, 93, 95, 97, 80, 80, 81);
            set_AI_gun_stage(34, 97, 100, 100, 100, 100, 100);
            set_AI_gun_stage(29, 70, 87, 94, 94, 94, 0);
            set_AI_gun_stage(30, 56, 88, 100, 100, 0, 0);
            set_AI_gun_stage(31, 87, 100, 100, 0, 0, 0);
            set_AI_gun_stage(17, 95, 95, 92, 91, 0, 0);
            set_AI_gun_stage(37, 71, 94, 95, 0, 0, 0);
            set_AI_gun_stage(40, 79, 90, 100, 100, 100, 100);
            set_AI_gun_stage(2, 95, 97, 98, 100, 98, 0);
            set_AI_gun_stage(6, 83, 98, 100, 97, 92, 100);
            set_AI_gun_stage(14, 100, 100, 99, 92, 92, 92);
            set_AI_gun_stage(20, 87, 99, 100, 99, 99, 0);
            set_AI_gun_stage(26, 86, 95, 95, 95, 93, 100);
            set_AI_gun_stage(15, 100, 90, 90, 0, 0, 0);
            set_AI_gun_stage(1, 90, 96, 100, 95, 98, 100);
            set_AI_gun_stage(7, 75, 100, 87, 65, 68, 0);
            set_AI_gun_stage(10, 100, 100, 100, 95, 93, 94);
            set_AI_gun_stage(12, 72, 91, 99, 100, 99, 100);
        } else {
            set_AI_gun_stage(42, 78, 78, 78, 0, 0, 0);
            set_AI_gun_stage(5, 93, 95, 97, 82, 82, 82);
            set_AI_gun_stage(34, 91, 100, 100, 100, 100, 100);
            set_AI_gun_stage(29, 70, 87, 94, 94, 94, 0);
            set_AI_gun_stage(30, 56, 88, 100, 100, 0, 0);
            set_AI_gun_stage(31, 87, 100, 100, 0, 0, 0);
            set_AI_gun_stage(17, 95, 95, 92, 91, 0, 0);
            set_AI_gun_stage(37, 71, 94, 95, 0, 0, 0);
            set_AI_gun_stage(40, 79, 90, 100, 100, 100, 100);
            set_AI_gun_stage(2, 95, 97, 98, 100, 98, 0);
            set_AI_gun_stage(6, 83, 98, 100, 97, 92, 100);
            set_AI_gun_stage(14, 100, 100, 99, 92, 92, 92);
            set_AI_gun_stage(20, 79, 99, 100, 99, 99, 99);
            set_AI_gun_stage(26, 86, 95, 95, 95, 93, 100);
            set_AI_gun_stage(8, 100, 100, 82, 87, 100, 100);
            set_AI_gun_stage(15, 100, 90, 90, 0, 0, 0);
            set_AI_gun_stage(1, 72, 82, 90, 88, 84, 88);
            set_AI_gun_stage(7, 75, 100, 87, 65, 68, 0);
            set_AI_gun_stage(10, 100, 100, 100, 85, 80, 80);
            set_AI_gun_stage(12, 72, 96, 100, 100, 100, 100);
            set_AI_gun_stage(57, 83, 94, 96, 100, 100, 100);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x019d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void loadInfo() {
        /*
            Method dump skipped, instructions count: 2070
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.kwgames.util.AgileMgr.loadInfo():void");
    }

    public static synchronized boolean save() {
        boolean writeFileFromBytesByChannel;
        synchronized (AgileMgr.class) {
            byte[] bArr = new byte[1386];
            int i = 0;
            for (int i2 = 0; i2 < 66; i2++) {
                for (int i3 = 0; i3 < 3; i3++) {
                    int i4 = 0;
                    while (i4 < 7) {
                        int i5 = i + 1;
                        bArr[i] = s_arrGunPress[i2][i3][i4];
                        i4++;
                        i = i5;
                    }
                }
            }
            try {
                File externalFilesDir = AppInstance.get().getExternalFilesDir(null);
                externalFilesDir.mkdirs();
                writeFileFromBytesByChannel = FileIOUtils.writeFileFromBytesByChannel(externalFilesDir + Constants.get_cur_AI_kit().m_strAIGunDownFileName, bArr, true);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return writeFileFromBytesByChannel;
    }

    public static synchronized boolean save_AI_gun_stage() {
        boolean writeFileFromBytesByChannel;
        synchronized (AgileMgr.class) {
            byte[] bArr = new byte[396];
            int i = 0;
            for (int i2 = 0; i2 < 66; i2++) {
                int i3 = 0;
                while (i3 < 6) {
                    int i4 = i + 1;
                    bArr[i] = s_arrAIGunStage[i2][i3];
                    i3++;
                    i = i4;
                }
            }
            try {
                File externalFilesDir = AppInstance.get().getExternalFilesDir(null);
                externalFilesDir.mkdirs();
                writeFileFromBytesByChannel = FileIOUtils.writeFileFromBytesByChannel(externalFilesDir + Constants.get_cur_AI_kit().m_strAIGunStageFileName, bArr, true);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return writeFileFromBytesByChannel;
    }
}
