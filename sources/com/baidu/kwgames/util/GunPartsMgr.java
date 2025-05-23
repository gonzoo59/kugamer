package com.baidu.kwgames.util;

import com.baidu.kwgames.AppInstance;
import com.baidu.kwgames.Constants;
import com.baidu.kwgames.R;
import com.blankj.utilcode.util.FileIOUtils;
import java.io.File;
import java.lang.reflect.Array;
/* loaded from: classes.dex */
public class GunPartsMgr {
    public static final int GUN_HANDLE_CHUIZHI_MASK = 16384;
    public static final int GUN_HANDLE_JIGUANG = 32768;
    public static final int GUN_HANDLE_VICTOR_MASK = 118784;
    public static final int GUN_HEAD_CF_MASK = 480;
    public static final int GUN_HEAD_CF_XIAOYIUN = 64;
    public static final int GUN_HEAD_JUJI_MASK = 280;
    public static final int GUN_TAIL_UZI_MASK = 201326592;
    private static String m_strGunPartsNone;
    private static String m_strGunPartsText = new String();
    public static byte[][][][] s_arrGunReduce = (byte[][][][]) Array.newInstance(byte.class, 66, 9, 7, 4);
    public static final int GUN_TAIL_BUQIANG_MASK = 150994944;
    public static final int GUN_TAIL_JUJI_MASK = 167772160;
    public static final int GUN_HANDLE_ALL_MASK = 520192;
    public static final int GUN_HANDLE_CHUIZHI_SJ_MASK = 24576;
    public static final int GUN_HEAD_BUQIANG_MASK = 263;
    public static final int[] s_arrGunPartsAvailableMask = {167772440, 327, 520519, 167772440, 0, 0, 520519, 2, 150995271, 167772440, GUN_TAIL_BUQIANG_MASK, 151515463, 520519, 0, 351, 167772511, 151515463, 151515616, 0, 351, 520519, 0, 0, 327, 0, 0, 520519, 168292703, 167772511, 520672, 201327072, GUN_TAIL_JUJI_MASK, 0, 0, 16864, 0, 0, 151114208, 167772440, 119264, 480, 0, GUN_HANDLE_ALL_MASK, 0, 0, 0, 520543, 185069919, 0, 327, 119264, 168292703, 0, 351, GUN_HANDLE_CHUIZHI_SJ_MASK, 0, 327, 327, 480, 0, 167772440, GUN_HEAD_BUQIANG_MASK, 0, 151515463, 480, 32832};
    public static final int[][] s_arrGunHandleOffsetPad = {new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{17, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}};
    public static final int[][] s_arrGunHandleOffsetPhone = {new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{-3, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0}};

    public static String get_gun_parts_name(int i, int i2, int i3, int i4) {
        if (i < 0 || i >= 66) {
            return "";
        }
        m_strGunPartsText = "";
        if (-1 == i2) {
            i2 = 8;
        }
        if (-1 == i3) {
            i3 = 6;
        }
        if (-1 == i4) {
            i4 = 3;
        }
        int i5 = s_arrGunPartsAvailableMask[i];
        if (((1 << i2) & i5) == 0) {
            i2 = 8;
        }
        if (((1 << (i3 + 12)) & i5) == 0) {
            i3 = 6;
        }
        if ((i5 & (1 << (i4 + 24))) == 0) {
            i4 = 3;
        }
        if (i2 != 8) {
            m_strGunPartsText += Constants.m_arrGunHeadName[i2];
        }
        if (i3 != 6) {
            m_strGunPartsText += '|' + Constants.m_arrGunHandleName[i3];
        }
        if (i4 != 3) {
            m_strGunPartsText += '|' + Constants.m_arrGunTailName[i4];
        }
        if (!m_strGunPartsText.isEmpty()) {
            String str = m_strGunPartsText + ":-" + ((int) get_gun_parts_reduce(i, i2, i3, i4)) + '%';
            m_strGunPartsText = str;
            return str;
        }
        return m_strGunPartsNone;
    }

    public static int append_gun_parts_name_and_value(int i, int i2, int i3, int i4, StringBuilder sb) {
        if (i < 0 || i >= 66) {
            return 0;
        }
        if (-1 == i2) {
            i2 = 8;
        }
        if (-1 == i3) {
            i3 = 6;
        }
        if (-1 == i4) {
            i4 = 3;
        }
        int i5 = s_arrGunPartsAvailableMask[i];
        if (((1 << i2) & i5) == 0) {
            i2 = 8;
        }
        if (((1 << (i3 + 12)) & i5) == 0) {
            i3 = 6;
        }
        if ((i5 & (1 << (i4 + 24))) == 0) {
            i4 = 3;
        }
        if (i2 != 8) {
            sb.append(Constants.m_arrGunHeadName[i2] + '|');
        }
        if (i3 != 6) {
            sb.append(Constants.m_arrGunHandleName[i3] + '|');
        }
        if (i4 != 3) {
            sb.append(Constants.m_arrGunTailName[i4] + '|');
        }
        return s_arrGunReduce[i][i2][i3][i4];
    }

    public static Boolean is_gun_parts_empty(int i, int i2, int i3, int i4) {
        boolean z = true;
        if (i < 0 || i >= 66) {
            return true;
        }
        if (-1 == i2) {
            i2 = 8;
        }
        if (-1 == i3) {
            i3 = 6;
        }
        if (-1 == i4) {
            i4 = 3;
        }
        int i5 = s_arrGunPartsAvailableMask[i];
        if (((1 << i2) & i5) == 0) {
            i2 = 8;
        }
        if (((1 << (i3 + 12)) & i5) == 0) {
            i3 = 6;
        }
        if ((i5 & (1 << (i4 + 24))) == 0) {
            i4 = 3;
        }
        return Boolean.valueOf((i2 == 8 && i3 == 6 && i4 == 3) ? false : false);
    }

    public static boolean set_gun_parts_reduce(int i, int i2, int i3, int i4, int i5) {
        if (i < 0 || i >= 66) {
            return false;
        }
        if (-1 == i2) {
            i2 = 8;
        }
        if (-1 == i3) {
            i3 = 6;
        }
        if (-1 == i4) {
            i4 = 3;
        }
        int i6 = s_arrGunPartsAvailableMask[i];
        int i7 = ((1 << i2) & i6) != 0 ? i2 : 8;
        s_arrGunReduce[i][i7][((1 << (i3 + 12)) & i6) != 0 ? i3 : 6][((1 << (i4 + 24)) & i6) != 0 ? i4 : 3] = (byte) i5;
        return true;
    }

    public static byte get_gun_parts_reduce(int i, int i2, int i3, int i4) {
        if (i < 0 || i >= 66) {
            return (byte) 0;
        }
        if (-1 == i2) {
            i2 = 8;
        }
        if (-1 == i3) {
            i3 = 6;
        }
        if (-1 == i4) {
            i4 = 3;
        }
        int i5 = s_arrGunPartsAvailableMask[i];
        int i6 = ((1 << i2) & i5) != 0 ? i2 : 8;
        return s_arrGunReduce[i][i6][((1 << (i3 + 12)) & i5) != 0 ? i3 : 6][((1 << (i4 + 24)) & i5) != 0 ? i4 : 3];
    }

    public static void set_def_gun_parts_reduce_detail(int i, int i2, int i3) {
        for (int i4 = 0; i4 <= 6; i4++) {
            for (int i5 = 0; i5 <= 3; i5++) {
                s_arrGunReduce[i][i2][i4][i5] = (byte) i3;
            }
        }
    }

    public static void set_def_gun_parts_reduce_detail() {
        boolean is_pad = NEAT.is_pad();
        int[] iArr = {10, 3, 8, 10, 8, 20, 3, 8, 0};
        int[] iArr2 = {3, 0, 15, 0, 8, 3, 0};
        for (int i = 0; i < 66; i++) {
            for (int i2 = 0; i2 <= 8; i2++) {
                for (int i3 = 0; i3 <= 6; i3++) {
                    for (int i4 = 0; i4 <= 3; i4++) {
                        if (58 == i && 5 == i3) {
                            s_arrGunReduce[i][i2][i3][i4] = 20;
                        } else if (is_pad) {
                            s_arrGunReduce[i][i2][i3][i4] = (byte) (iArr[i2] + iArr2[i3] + s_arrGunHandleOffsetPad[i][i3]);
                        } else {
                            s_arrGunReduce[i][i2][i3][i4] = (byte) (iArr[i2] + iArr2[i3] + s_arrGunHandleOffsetPhone[i][i3]);
                        }
                    }
                }
            }
        }
        set_def_gun_parts_reduce_detail(61, 1, 0);
    }

    public static void loadInfo() {
        if (AppInstance.is_pubg_AI_kit()) {
            int[] iArr = s_arrGunPartsAvailableMask;
            iArr[48] = 263;
            iArr[54] = 151515399;
        }
        set_def_gun_parts_reduce_detail();
        byte[] readFile2BytesByChannel = FileIOUtils.readFile2BytesByChannel(AppInstance.get().getExternalFilesDir(null) + Constants.get_cur_AI_kit().m_strAIGunPartsReduceFileName);
        if (readFile2BytesByChannel != null) {
            int min = Math.min(readFile2BytesByChannel.length / 252, 66);
            int i = 0;
            for (int i2 = 0; i2 < min; i2++) {
                for (int i3 = 0; i3 <= 8; i3++) {
                    for (int i4 = 0; i4 <= 6; i4++) {
                        int i5 = 0;
                        while (i5 <= 3) {
                            s_arrGunReduce[i2][i3][i4][i5] = readFile2BytesByChannel[i];
                            i5++;
                            i++;
                        }
                    }
                }
            }
        }
        m_strGunPartsNone = AppInstance.s_context.getString(R.string.gun_parts_none);
    }

    public static synchronized boolean save() {
        boolean writeFileFromBytesByChannel;
        synchronized (GunPartsMgr.class) {
            byte[] bArr = new byte[16632];
            int i = 0;
            for (int i2 = 0; i2 < 66; i2++) {
                for (int i3 = 0; i3 <= 8; i3++) {
                    for (int i4 = 0; i4 <= 6; i4++) {
                        int i5 = 0;
                        while (i5 <= 3) {
                            int i6 = i + 1;
                            bArr[i] = s_arrGunReduce[i2][i3][i4][i5];
                            i5++;
                            i = i6;
                        }
                    }
                }
            }
            try {
                File externalFilesDir = AppInstance.get().getExternalFilesDir(null);
                externalFilesDir.mkdirs();
                writeFileFromBytesByChannel = FileIOUtils.writeFileFromBytesByChannel(externalFilesDir + Constants.get_cur_AI_kit().m_strAIGunPartsReduceFileName, bArr, true);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return writeFileFromBytesByChannel;
    }
}
