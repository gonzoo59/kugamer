package com.baidu.kwgames.util;

import com.baidu.kwgames.AppInstance;
import com.baidu.kwgames.Constants;
import com.baidu.kwgames.Units;
import com.blankj.utilcode.util.FileIOUtils;
import java.io.File;
import java.lang.reflect.Array;
/* loaded from: classes.dex */
public class AICrosshair {
    public static final int E_DEF_CROSS = 1;
    public static final int E_SCOPE_2X = 4;
    public static final int E_SCOPE_3X = 5;
    public static final int E_SCOPE_4X = 6;
    public static final int E_SCOPE_6X = 7;
    public static final int E_SCOPE_8X = 8;
    public static final int E_SCOPE_CNT = 9;
    public static final int E_SCOPE_HONGDIAN = 2;
    public static final int E_SCOPE_JIMIAO = 1;
    public static final int E_SCOPE_QUANXI = 3;
    public static final int E_SCOPE_YAOSHE = 0;
    public static int m_nXOffsetLimit;
    public static int m_nYOffsetLimit;
    public static byte[][] s_arrGunCrosshairStyle = (byte[][]) Array.newInstance(byte.class, 66, 9);
    private static int[][] s_arrScopeOffsetX = (int[][]) Array.newInstance(int.class, 66, 9);
    private static int[][] s_arrScopeOffsetY = (int[][]) Array.newInstance(int.class, 66, 9);
    public static int[] s_arrAIScope2CrosshairScope = {1, 2, 3, 4, 5, 6, 7, 8, 2, 2, 3, 3, 4, 5, 8};

    public static int get_scope_offset_x(int i, int i2) {
        if (i < 0 || i >= 66 || i2 < 0 || i2 >= 9) {
            return 0;
        }
        return s_arrScopeOffsetX[i][i2];
    }

    public static int get_scope_offset_y(int i, int i2) {
        if (i < 0 || i >= 66 || i2 < 0 || i2 >= 9) {
            return 0;
        }
        return s_arrScopeOffsetY[i][i2];
    }

    public static void scope_offset_x_plus(int i, int i2) {
        if (i < 0 || i >= 66 || i2 < 0 || i2 >= 9) {
            return;
        }
        int i3 = (AppInstance.s_nScreenW - FloatMgr.get_aim_float_size()) >> 1;
        m_nXOffsetLimit = i3;
        int[][] iArr = s_arrScopeOffsetX;
        if (iArr[i][i2] < i3) {
            int[] iArr2 = iArr[i];
            iArr2[i2] = iArr2[i2] + 1;
            save();
        }
    }

    public static void scope_offset_x_minus(int i, int i2) {
        if (i < 0 || i >= 66 || i2 < 0 || i2 >= 9) {
            return;
        }
        int i3 = (AppInstance.s_nScreenH - FloatMgr.get_aim_float_size()) >> 1;
        m_nXOffsetLimit = i3;
        int[][] iArr = s_arrScopeOffsetX;
        if (iArr[i][i2] > (-i3)) {
            int[] iArr2 = iArr[i];
            iArr2[i2] = iArr2[i2] - 1;
            save();
        }
    }

    public static void scope_offset_y_plus(int i, int i2) {
        if (i < 0 || i >= 66 || i2 < 0 || i2 >= 9) {
            return;
        }
        int i3 = (AppInstance.s_nScreenH - FloatMgr.get_aim_float_size()) >> 1;
        m_nYOffsetLimit = i3;
        int[][] iArr = s_arrScopeOffsetY;
        if (iArr[i][i2] < i3) {
            int[] iArr2 = iArr[i];
            iArr2[i2] = iArr2[i2] + 1;
            save();
        }
    }

    public static void scope_offset_y_minus(int i, int i2) {
        if (i < 0 || i >= 66 || i2 < 0 || i2 >= 9) {
            return;
        }
        int i3 = (AppInstance.s_nScreenH - FloatMgr.get_aim_float_size()) >> 1;
        m_nYOffsetLimit = i3;
        int[][] iArr = s_arrScopeOffsetY;
        if (iArr[i][i2] > (-i3)) {
            int[] iArr2 = iArr[i];
            iArr2[i2] = iArr2[i2] - 1;
            save();
        }
    }

    public static boolean set_gun_crosshair(int i, int i2, int i3) {
        if (i < 0 || i >= 66 || i2 < 0 || i2 >= 9) {
            return false;
        }
        byte[][] bArr = s_arrGunCrosshairStyle;
        byte b = (byte) i3;
        if (bArr[i][i2] != b) {
            bArr[i][i2] = b;
            save();
        }
        return true;
    }

    public static int get_gun_crosshair(int i, int i2) {
        if (i < 0 || i >= 66 || i2 < 0 || i2 >= 9) {
            return 0;
        }
        return s_arrGunCrosshairStyle[i][i2];
    }

    public static void set_def_gun_style(int i, int i2, int i3) {
        if (i >= 66 || i < 0) {
            return;
        }
        s_arrGunCrosshairStyle[i][0] = (byte) i2;
        for (int i4 = 1; i4 < 9; i4++) {
            s_arrGunCrosshairStyle[i][i4] = (byte) i3;
        }
    }

    public static void set_def_gun_style(int i, int i2, int i3, int i4) {
        if (i >= 66 || i < 0) {
            return;
        }
        s_arrGunCrosshairStyle[i][0] = (byte) i2;
        for (int i5 = 1; i5 < 9; i5++) {
            if (i5 == 6) {
                s_arrGunCrosshairStyle[i][i5] = (byte) i4;
            } else {
                s_arrGunCrosshairStyle[i][i5] = (byte) i3;
            }
        }
    }

    public static void set_all_gun_def_style(int i, int i2, int i3, int i4) {
        for (int i5 = 0; i5 < 66; i5++) {
            byte[][] bArr = s_arrGunCrosshairStyle;
            bArr[i5][0] = (byte) i;
            bArr[i5][1] = (byte) i2;
            for (int i6 = 2; i6 < 9; i6++) {
                if (i6 == 6) {
                    s_arrGunCrosshairStyle[i5][i6] = (byte) i3;
                } else {
                    s_arrGunCrosshairStyle[i5][i6] = (byte) i4;
                }
            }
        }
    }

    public static void set_def_gun_style(int i, int i2, int i3, int i4, int i5) {
        if (i >= 66 || i < 0) {
            return;
        }
        byte[][] bArr = s_arrGunCrosshairStyle;
        bArr[i][0] = (byte) i2;
        bArr[i][1] = (byte) i3;
        for (int i6 = 2; i6 < 9; i6++) {
            if (i6 == 6) {
                s_arrGunCrosshairStyle[i][i6] = (byte) i4;
            } else {
                s_arrGunCrosshairStyle[i][i6] = (byte) i5;
            }
        }
    }

    public static void init() {
        set_all_gun_def_style(7, 1, 0, 0);
        set_def_gun_style(61, 7, 0, 0);
        byte[] readFile2BytesByChannel = FileIOUtils.readFile2BytesByChannel(AppInstance.get().getExternalFilesDir(null) + Constants.get_cur_AI_kit().m_strAICrosshairFileName);
        if (readFile2BytesByChannel != null) {
            int min = Math.min((int) readFile2BytesByChannel[0], 66);
            byte b = readFile2BytesByChannel[1];
            int i = 2;
            for (int i2 = 0; i2 < min; i2++) {
                for (int i3 = 0; i3 < b; i3++) {
                    int i4 = i + 1;
                    s_arrGunCrosshairStyle[i2][i3] = readFile2BytesByChannel[i];
                    s_arrScopeOffsetX[i2][i3] = Units.Short2Int(readFile2BytesByChannel[i4], readFile2BytesByChannel[i4 + 1]);
                    int i5 = i4 + 2;
                    s_arrScopeOffsetY[i2][i3] = Units.Short2Int(readFile2BytesByChannel[i5], readFile2BytesByChannel[i5 + 1]);
                    i = i5 + 2;
                }
            }
            return;
        }
        set_def_gun_style(42, 7, 1, 0, 0);
        set_def_gun_style(34, 7, 1, 0, 0);
        set_def_gun_style(29, 7, 1, 0, 0);
        set_def_gun_style(30, 7, 1, 0, 0);
        set_def_gun_style(31, 7, 0);
        set_def_gun_style(17, 7, 1, 0, 0);
        set_def_gun_style(37, 7, 1, 0, 0);
        set_def_gun_style(43, 7, 1, 0, 0);
        set_def_gun_style(44, 7, 0);
        set_def_gun_style(39, 7, 1, 0, 0);
        set_def_gun_style(40, 7, 1, 0, 0);
        set_def_gun_style(14, 1, 1, 0, 0);
        set_def_gun_style(32, 1, 0);
        set_def_gun_style(27, 1, 1, 0, 0);
        set_def_gun_style(28, 1, 1, 0, 0);
        set_def_gun_style(9, 1, 1, 0, 0);
        set_def_gun_style(3, 1, 1, 0, 0);
        set_def_gun_style(41, 1, 1, 0, 0);
        set_def_gun_style(0, 1, 1, 0, 0);
        set_def_gun_style(38, 1, 1, 0, 0);
        if (AppInstance.is_hpjy_AI_kit()) {
            set_def_gun_style(53, 1, 1, 0, 0);
            set_def_gun_style(56, 7, 1, 0, 0);
        } else {
            set_def_gun_style(53, 7, 1, 0, 0);
        }
        set_def_gun_style(23, 7, 1, 0, 0);
        set_def_gun_style(4, 7, 1, 0, 0);
        set_def_gun_style(24, 7, 1, 0, 0);
        set_def_gun_style(25, 7, 1, 0, 0);
        set_def_gun_style(18, 1, 1, 0, 0);
        set_def_gun_style(22, 1, 1, 0, 0);
        set_def_gun_style(33, 1, 1, 0, 0);
        set_def_gun_style(35, 1, 1, 0, 0);
        set_def_gun_style(36, 1, 1, 0, 0);
        set_def_gun_style(21, 1, 0, 0);
        set_def_gun_style(45, 1, 1, 0, 0);
        set_def_gun_style(50, 1, 1, 0, 0);
        set_def_gun_style(52, 1, 1, 0, 0);
        set_def_gun_style(58, 7, 1, 0, 0);
        set_def_gun_style(59, 1, 1, 0, 0);
        set_def_gun_style(60, 1, 1, 0, 0);
    }

    public static synchronized boolean save() {
        boolean writeFileFromBytesByChannel;
        synchronized (AICrosshair.class) {
            byte[] bArr = new byte[2972];
            bArr[0] = 66;
            int i = 2;
            bArr[1] = 9;
            for (int i2 = 0; i2 < 66; i2++) {
                int i3 = 0;
                while (i3 < 9) {
                    int i4 = i + 1;
                    bArr[i] = s_arrGunCrosshairStyle[i2][i3];
                    int i5 = i4 + 1;
                    bArr[i4] = Units.LOBYTE(s_arrScopeOffsetX[i2][i3]);
                    int i6 = i5 + 1;
                    bArr[i5] = Units.HIBYTE(s_arrScopeOffsetX[i2][i3]);
                    int i7 = i6 + 1;
                    bArr[i6] = Units.LOBYTE(s_arrScopeOffsetY[i2][i3]);
                    int i8 = i7 + 1;
                    bArr[i7] = Units.HIBYTE(s_arrScopeOffsetY[i2][i3]);
                    i3++;
                    i = i8;
                }
            }
            try {
                File externalFilesDir = AppInstance.get().getExternalFilesDir(null);
                externalFilesDir.mkdirs();
                writeFileFromBytesByChannel = FileIOUtils.writeFileFromBytesByChannel(externalFilesDir + Constants.get_cur_AI_kit().m_strAICrosshairFileName, bArr, true);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return writeFileFromBytesByChannel;
    }

    public static int ai_scope_to_crosshair_scope(int i, boolean z) {
        if (z) {
            if (i < 0) {
                return 1;
            }
            int[] iArr = s_arrAIScope2CrosshairScope;
            if (i < iArr.length) {
                return iArr[i];
            }
            return 1;
        }
        return 0;
    }
}
