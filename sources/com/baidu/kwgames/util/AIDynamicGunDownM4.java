package com.baidu.kwgames.util;

import android.os.Handler;
import android.util.Log;
import com.baidu.kwgames.AppInstance;
import com.baidu.kwgames.Constants;
import com.blankj.utilcode.util.FileIOUtils;
import java.io.File;
import java.lang.reflect.Array;
import org.opencv.videoio.Videoio;
/* loaded from: classes.dex */
public class AIDynamicGunDownM4 {
    public static final int E_STAGE_CNT = 12;
    private static final String TAG = "AIDynamicGunDownM4";
    public static Runnable m_runableSaveAIStage;
    public static Handler m_timerHandler;
    public static int[][][] s_arrAIGunStage = (int[][][]) Array.newInstance(int.class, 66, 2, 12);
    public static int[][][] s_arrAIGunStageTime = (int[][][]) Array.newInstance(int.class, 66, 2, 12);

    public static synchronized boolean save() {
        boolean writeFileFromBytesByChannel;
        synchronized (AIDynamicGunDownM4.class) {
            byte[] bArr = new byte[3169];
            bArr[0] = 66;
            int i = 1;
            for (int i2 = 0; i2 < 66; i2++) {
                for (int i3 = 0; i3 < 2; i3++) {
                    for (int i4 = 0; i4 < 12; i4++) {
                        int i5 = i + 1;
                        bArr[i] = (byte) (s_arrAIGunStage[i2][i3][i4] & 255);
                        i = i5 + 1;
                        bArr[i5] = (byte) (s_arrAIGunStageTime[i2][i3][i4] & 255);
                    }
                }
            }
            try {
                File externalFilesDir = AppInstance.get().getExternalFilesDir(null);
                externalFilesDir.mkdirs();
                writeFileFromBytesByChannel = FileIOUtils.writeFileFromBytesByChannel(externalFilesDir + Constants.get_cur_AI_kit().m_strAIGunStageFileNameM4, bArr, true);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return writeFileFromBytesByChannel;
    }

    public static void set_gun_stage(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13) {
        for (int i14 = 0; i14 < 2; i14++) {
            int[][][] iArr = s_arrAIGunStage;
            iArr[i][i14][0] = i2;
            iArr[i][i14][1] = i3;
            iArr[i][i14][2] = i4;
            iArr[i][i14][3] = i5;
            iArr[i][i14][4] = i6;
            iArr[i][i14][5] = i7;
            iArr[i][i14][6] = i8;
            iArr[i][i14][7] = i9;
            iArr[i][i14][8] = i10;
            iArr[i][i14][9] = i11;
            iArr[i][i14][10] = i12;
            iArr[i][i14][11] = i13;
        }
    }

    public static void set_gun_stage_time(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13) {
        for (int i14 = 0; i14 < 2; i14++) {
            int[][][] iArr = s_arrAIGunStageTime;
            iArr[i][i14][0] = i2 / 10;
            iArr[i][i14][1] = i3 / 10;
            iArr[i][i14][2] = i4 / 10;
            iArr[i][i14][3] = i5 / 10;
            iArr[i][i14][4] = i6 / 10;
            iArr[i][i14][5] = i7 / 10;
            iArr[i][i14][6] = i8 / 10;
            iArr[i][i14][7] = i9 / 10;
            iArr[i][i14][8] = i10 / 10;
            iArr[i][i14][9] = i11 / 10;
            iArr[i][i14][10] = i12 / 10;
            iArr[i][i14][11] = i13 / 10;
        }
    }

    public static void set_gun_stage_qinxing(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13) {
        int[][][] iArr = s_arrAIGunStage;
        iArr[i][1][0] = i2;
        iArr[i][1][1] = i3;
        iArr[i][1][2] = i4;
        iArr[i][1][3] = i5;
        iArr[i][1][4] = i6;
        iArr[i][1][5] = i7;
        iArr[i][1][6] = i8;
        iArr[i][1][7] = i9;
        iArr[i][1][8] = i10;
        iArr[i][1][9] = i11;
        iArr[i][1][10] = i12;
        iArr[i][1][11] = i13;
    }

    public static void set_gun_stage_time_qinxing(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13) {
        int[][][] iArr = s_arrAIGunStageTime;
        iArr[i][1][0] = i2 / 10;
        iArr[i][1][1] = i3 / 10;
        iArr[i][1][2] = i4 / 10;
        iArr[i][1][3] = i5 / 10;
        iArr[i][1][4] = i6 / 10;
        iArr[i][1][5] = i7 / 10;
        iArr[i][1][6] = i8 / 10;
        iArr[i][1][7] = i9 / 10;
        iArr[i][1][8] = i10 / 10;
        iArr[i][1][9] = i11 / 10;
        iArr[i][1][10] = i12 / 10;
        iArr[i][1][11] = i13 / 10;
    }

    public static void init() {
        for (int i = 0; i < 66; i++) {
            for (int i2 = 0; i2 < 2; i2++) {
                for (int i3 = 0; i3 < 12; i3++) {
                    s_arrAIGunStage[i][i2][i3] = 100;
                    if (i3 < 6) {
                        s_arrAIGunStageTime[i][i2][i3] = 80;
                    } else {
                        s_arrAIGunStageTime[i][i2][i3] = 0;
                    }
                }
            }
        }
        if (NEAT.is_pad()) {
            set_gun_stage(61, 75, 78, 83, 93, 95, 105, 109, 109, 109, 109, 109, 109);
            set_gun_stage_time(61, 90, 100, 100, 100, 200, 300, 500, 500, 2200, 2550, 2550, 2550);
            set_gun_stage(63, 175, 78, 77, 78, 78, 79, 85, 85, 86, 88, 88, 109);
            set_gun_stage_time(63, 70, 180, 100, 100, 200, 300, 500, 500, 2200, 2550, 2550, 2550);
            set_gun_stage_qinxing(63, Constants.E_BLE_BUF_TYPE_CUSTOMIZE_PARAM, 77, 79, 83, 90, 90, 90, 90, 93, 93, 93, 93);
            set_gun_stage_time_qinxing(63, 60, 180, 100, 100, 200, 300, 500, 500, 2200, 2550, 2550, 2550);
            set_gun_stage(64, 75, 78, 83, 93, 95, 105, 109, 109, 109, 109, 109, 109);
            set_gun_stage_time(64, 80, 100, 100, 100, 200, 300, 500, 500, 2200, 2550, 2550, 2550);
            set_gun_stage(65, 161, 109, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103);
            set_gun_stage_time(65, 80, Videoio.CAP_PROP_XI_CC_MATRIX_23, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800);
        } else {
            set_gun_stage(61, 75, 81, 87, 95, 95, 105, 109, 110, 110, 110, 110, 110);
            set_gun_stage_time(61, 90, 100, 100, 100, 200, 300, 500, 500, 2200, 2550, 2550, 2550);
            set_gun_stage(63, 201, 80, 81, 83, 84, 83, 83, 86, 91, 91, 91, 91);
            set_gun_stage_time(63, 60, 180, 100, 100, 200, 300, 500, 500, 2200, 2550, 2550, 2550);
            set_gun_stage_qinxing(63, 153, 85, 88, 94, 95, 105, 109, 106, 105, 109, 109, 109);
            set_gun_stage_time_qinxing(63, 80, 100, 100, 100, 200, 300, 500, 500, 2200, 2550, 2550, 2550);
            set_gun_stage(64, 205, 69, 77, 87, 86, 86, 91, 99, 103, 108, 109, 109);
            set_gun_stage_time(64, 60, 100, 100, 100, 200, 300, 500, 500, 2200, 2550, 2550, 2550);
            set_gun_stage(65, 161, 109, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103);
            set_gun_stage_time(65, 80, Videoio.CAP_PROP_XI_CC_MATRIX_23, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800);
        }
        byte[] readFile2BytesByChannel = FileIOUtils.readFile2BytesByChannel(AppInstance.get().getExternalFilesDir(null) + Constants.get_cur_AI_kit().m_strAIGunStageFileNameM4);
        if (readFile2BytesByChannel == null) {
            readFile2BytesByChannel = Util.read_assets(NEAT.is_pad() ? "AIStageM41" : "AIStageM41-Phone");
        }
        int i4 = 1;
        if (readFile2BytesByChannel == null) {
            byte[] bArr = new byte[6];
            for (int i5 = 0; i5 < 66; i5++) {
                AgileMgr.get_AI_gun_stage(i5, bArr);
                for (int i6 = 0; i6 < 6; i6++) {
                    s_arrAIGunStage[i5][0][i6] = bArr[i6] & 255;
                    s_arrAIGunStageTime[i5][1][i6] = bArr[i6] & 255;
                }
            }
            return;
        }
        int min = Math.min((int) readFile2BytesByChannel[0], 66);
        for (int i7 = 0; i7 < min; i7++) {
            for (int i8 = 0; i8 < 2; i8++) {
                int i9 = 0;
                while (i9 < 12) {
                    int i10 = i4 + 1;
                    s_arrAIGunStage[i7][i8][i9] = readFile2BytesByChannel[i4] & 255;
                    s_arrAIGunStageTime[i7][i8][i9] = readFile2BytesByChannel[i10] & 255;
                    i9++;
                    i4 = i10 + 1;
                }
            }
        }
    }

    public static boolean set_AI_gun_stage(int i, int i2, int i3, int i4) {
        if (i < 0 || i >= 66 || i3 < 0 || i3 >= 12) {
            return false;
        }
        s_arrAIGunStage[i][i2 == 0 ? (char) 1 : (char) 0][i3] = i4;
        return true;
    }

    public static boolean set_AI_gun_stage_time(int i, int i2, int i3, int i4) {
        if (i < 0 || i >= 66 || i3 < 0 || i3 >= 12) {
            return false;
        }
        s_arrAIGunStageTime[i][i2 == 0 ? (char) 1 : (char) 0][i3] = i4;
        return true;
    }

    public static void set_AI_gun_stage(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        if (i < 0 || i >= 66) {
            return;
        }
        for (int i8 = 0; i8 < 2; i8++) {
            int[][][] iArr = s_arrAIGunStage;
            iArr[i][i8][0] = i2;
            iArr[i][i8][1] = i3;
            iArr[i][i8][2] = i4;
            iArr[i][i8][3] = i5;
            iArr[i][i8][4] = i6;
            iArr[i][i8][5] = i7;
        }
    }

    public static void get_AI_gun_stage(int i, int i2, byte[] bArr) {
        if (i < 0 || i >= 66) {
            return;
        }
        char c = i2 == 0 ? (char) 1 : (char) 0;
        for (int i3 = 0; i3 < 12; i3++) {
            bArr[i3] = (byte) (s_arrAIGunStage[i][c][i3] & 255);
        }
    }

    public static void get_AI_gun_stage_time(int i, int i2, byte[] bArr) {
        if (i < 0 || i >= 66) {
            return;
        }
        char c = i2 == 0 ? (char) 1 : (char) 0;
        for (int i3 = 0; i3 < 12; i3++) {
            bArr[i3] = (byte) (s_arrAIGunStageTime[i][c][i3] & 255);
        }
    }

    public static int get_AI_gun_stage(int i, int i2, int i3) {
        if (i < 0 || i >= 66 || i3 < 0 || i3 >= 12) {
            return 100;
        }
        return s_arrAIGunStage[i][i2 == 0 ? (char) 1 : (char) 0][i3];
    }

    public static int get_AI_gun_stage_time(int i, int i2, int i3) {
        if (i < 0 || i >= 66 || i3 < 0 || i3 >= 12) {
            return 0;
        }
        return s_arrAIGunStageTime[i][i2 == 0 ? (char) 1 : (char) 0][i3];
    }

    public static void copy_AI_gun_dynamic(int i, int i2) {
        if (i < 0 || i >= 66 || i2 < 0 || i2 >= 66) {
            return;
        }
        for (int i3 = 0; i3 < 12; i3++) {
            for (int i4 = 0; i4 < 2; i4++) {
                int[][][] iArr = s_arrAIGunStage;
                iArr[i2][i4][i3] = iArr[i][i4][i3];
                int[][][] iArr2 = s_arrAIGunStageTime;
                iArr2[i2][i4][i3] = iArr2[i][i4][i3];
            }
        }
    }

    public static void begin_save_timer() {
        if (m_timerHandler == null) {
            m_timerHandler = new Handler();
        }
        if (m_runableSaveAIStage == null) {
            m_runableSaveAIStage = new AnonymousClass1();
        }
        m_timerHandler.removeCallbacks(m_runableSaveAIStage);
        m_timerHandler.postDelayed(m_runableSaveAIStage, 5000L);
    }

    /* renamed from: com.baidu.kwgames.util.AIDynamicGunDownM4$1  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Log.d(AIDynamicGunDownM4.TAG, "save_AI_gun_stage@@@@\n");
            new Thread(new Runnable() { // from class: com.baidu.kwgames.util.AIDynamicGunDownM4$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AIDynamicGunDownM4.save();
                }
            }).start();
        }
    }
}
